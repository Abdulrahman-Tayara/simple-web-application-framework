package cg.callmapper.postrender;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.CPModelAttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.html.HTMLTagNode;
import cg.callmapper.CallMapper;

import java.util.ArrayList;
import java.util.List;

import static cg.template.FunctionCalls.THIS_DOT;
import static cg.template.FunctionCalls.bindCpModelToVar;

public class CpUsagePostRenderCallMapper implements CallMapper {

    @Override
    public List<String> map(HTMLTagNode tagNode, AttributeNode<?> cpModelAttributeNode) {
        if (!(cpModelAttributeNode instanceof CPModelAttributeNode))
            return new ArrayList<>();

        List<String> mappedCalls = new ArrayList<>();

        String idValue = "";
        for (AttributeNode<?> attributeNode : tagNode.getAttributes()) {
            if (attributeNode.getName().equals("id")) {
                idValue = ((HTMLAttributeNode) attributeNode).getValue();
                break;
            }
        }
        if (idValue.isEmpty())
            throw new RuntimeException("id not found in tag, tried to generate code for cp-model and no id found in tag");

        mappedCalls.add(THIS_DOT + bindCpModelToVar + "(\"" + idValue + "\", \"" + ((ValuableNode) cpModelAttributeNode.getValue()).toHtml() + "\");");

        return mappedCalls;
    }
}
