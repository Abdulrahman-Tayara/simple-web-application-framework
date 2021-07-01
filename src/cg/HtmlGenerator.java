package cg;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.CPAttributeNode;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLScriptNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;
import cg.callmapper.CallMapper;
import cg.callmapper.prerender.CpModelPreRenderCallMapper;

import java.util.ArrayList;
import java.util.List;

public class HtmlGenerator {

    private List<String> usedClasses = new ArrayList<>();
    private String generatedHtmlText = "";
    private List<String> scripts = new ArrayList<>();
    private List<String> templatePreRenderCalls = new ArrayList<>();
    private List<String> templatePostRenderCalls = new ArrayList<>();
    private List<CallMapper> preRenderCallMappers = new ArrayList<>();
    private List<CallMapper> postRenderCallMappers = new ArrayList<>();

    public void generateHtmlTextFromAst(HtmlDocumentNode doc) {
        IdsGenerator.injectRandomIds(doc);
        initCallMappers();
        iterate(doc);
        generateInitialFieldsScript();
        generateTemplateScripts();
        generateRenderScript();
        attachScriptNodesToAst(doc);
        convertAstToHtmlDocument(doc);
    }

    private void initCallMappers() {
        //pre render
        preRenderCallMappers.add(new CpModelPreRenderCallMapper());

        //post render
    }

    private void iterate(Node node) {
        if (node instanceof HtmlDocumentNode) {
            HtmlDocumentNode htmlDocNode = ((HtmlDocumentNode) node);
            for (HTMLElementNode elementNode : htmlDocNode.getElements()) {
                iterate(elementNode);
            }
        } else if (node instanceof HTMLTagNode) {
            HTMLTagNode tagNode = ((HTMLTagNode) node);
            examineTag(tagNode);

            if (tagNode.hasContent()) {
                for (HTMLElementNode elementNode : tagNode.getContent()) {
                    iterate(elementNode);
                }
            }
        } else if (node instanceof HTMLScriptNode) {
            scripts.add(((HTMLScriptNode) node).getScriptBody());
        }
    }

    private void examineTag(HTMLTagNode tagNode) {
        for (AttributeNode<?> attributeNode : tagNode.getAttributes()) {
            if (attributeNode instanceof CPAttributeNode) {

                if (!usedClasses.contains(attributeNode.getClass().getSimpleName()))
                    usedClasses.add(attributeNode.getClass().getSimpleName());

                templatePreRenderCalls.addAll(examineAttributeAndConvertToPreFunctionCalls(tagNode, attributeNode));
                templatePostRenderCalls.addAll(examineAttributeAndConvertToPostFunctionCalls(tagNode, attributeNode));
            }
        }
    }

    private List<String> examineAttributeAndConvertToPreFunctionCalls(HTMLTagNode tagNode, AttributeNode<?> attributeNode) {
        List<String> calls = new ArrayList<>();
        preRenderCallMappers.forEach(callMapper -> calls.addAll(callMapper.map(tagNode, attributeNode)));
        return calls;
    }

    private List<String> examineAttributeAndConvertToPostFunctionCalls(HTMLTagNode tagNode, AttributeNode<?> attributeNode) {
        List<String> calls = new ArrayList<>();
        postRenderCallMappers.forEach(callMapper -> calls.addAll(callMapper.map(tagNode, attributeNode)));
        return calls;
    }

    private void generateInitialFieldsScript() {
        InitialFieldsScriptGenerator generator = new InitialFieldsScriptGenerator();
        scripts.add(generator.generateScript(usedClasses));
    }

    private void generateTemplateScripts() {
        TemplateScriptsGenerator generator = new TemplateScriptsGenerator();
        scripts.addAll(generator.generateScripts(usedClasses));
    }

    private void generateRenderScript() {
        RenderScriptGenerator renderScriptGenerator = new RenderScriptGenerator();
        scripts.add(renderScriptGenerator.generateRenderScripts(templatePreRenderCalls, templatePostRenderCalls));
    }

    private void attachScriptNodesToAst(Node node) {
        List<HTMLScriptNode> scriptNodes = new ArrayList<>();
        for (String script : scripts) {
            HTMLScriptNode scriptNode = new HTMLScriptNode();
            scriptNode.setScriptBody(script);
            scriptNodes.add(scriptNode);
        }

        injectScriptNode(node, scriptNodes);
    }

    private void injectScriptNode(Node node, List<HTMLScriptNode> scriptNodes) {
        if (node instanceof HtmlDocumentNode) {
            HtmlDocumentNode htmlDocNode = ((HtmlDocumentNode) node);
            for (HTMLElementNode elementNode : htmlDocNode.getElements())
                injectScriptNode(elementNode, scriptNodes);
        } else if (node instanceof HTMLTagNode) {
            HTMLTagNode tagNode = ((HTMLTagNode) node);
            if (tagNode.getName().equals("body")) {
                scriptNodes.forEach(scriptNode -> tagNode.getContent().add(scriptNode));
                return;
            }
            if (tagNode.hasContent()) {
                for (HTMLElementNode elementNode : tagNode.getContent()) {
                    injectScriptNode(elementNode, scriptNodes);
                }
            }
        }
    }

    private void convertAstToHtmlDocument(Node node) {
        this.generatedHtmlText = node.toHtml();
    }

    public String getGeneratedHtmlText() {
        return generatedHtmlText;
    }
}
