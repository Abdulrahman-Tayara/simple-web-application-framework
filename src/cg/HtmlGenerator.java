package cg;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.CPAttributeNode;
import ast.nodes.html.*;
import cg.scripting.ScriptCode;
import cg.scripting.ScriptableNode;

import java.util.ArrayList;
import java.util.List;

public class HtmlGenerator {

    private String generatedHtmlText = "";
    private List<String> scripts = new ArrayList<>();
    private List<String> scriptCalls = new ArrayList<>();

    public void generateHtmlTextFromAst(HtmlDocumentNode doc) {
        IdsGenerator.injectRandomIds(doc);
        iterate(doc);
        generateInitialFieldsScript();
        generateTemplateScripts();
        generateRenderScript();
        attachScriptNodesToAst(doc);
        convertAstToHtmlDocument(doc);
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
        }
    }

    private void examineTag(HTMLTagNode tagNode) {
        for (AttributeNode<?> attributeNode : tagNode.getAttributes()) {
            if (attributeNode instanceof ScriptableNode) {
                ScriptableNode scriptableNode = (ScriptableNode) attributeNode;
                ScriptCode scriptCode = scriptableNode.generateScript(tagNode);
                scriptCalls.addAll(scriptCode.getLines());
            }
        }
        if (tagNode.hasContent()) {
            for (HTMLElementNode elementNode: tagNode.getContent()) {
                if (elementNode instanceof ScriptableNode) {
                    scriptCalls.addAll(((ScriptableNode) elementNode).generateScript(tagNode).getLines());
                }
            }
        }
    }

    private void generateInitialFieldsScript() {
        InitialFieldsScriptGenerator generator = new InitialFieldsScriptGenerator();
        scripts.add(generator.generateScript());
    }

    private void generateTemplateScripts() {
        TemplateScriptsGenerator generator = new TemplateScriptsGenerator();
        scripts.addAll(generator.generateScripts());
    }

    private void generateRenderScript() {
        RenderScriptGenerator renderScriptGenerator = new RenderScriptGenerator();
        String renderFunction = renderScriptGenerator.getRenderScript();
        StringBuilder builder = new StringBuilder();
        scriptCalls.forEach(scriptCall -> builder.append(scriptCall).append('\n'));
        builder.append(renderFunction);
        scripts.add(builder.toString());
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
