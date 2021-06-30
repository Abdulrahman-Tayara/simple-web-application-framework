package cg.callmapper.prerender;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.CPModelAttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.html.HTMLTagNode;
import cg.callmapper.CallMapper;

import java.util.ArrayList;
import java.util.List;

import static cg.template.FunctionCalls.*;

public class CpModelPreRenderCallMapper implements CallMapper {

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

        //todo: cpModelAttributeNode.getValue() is not the way to go, it is of type T and we need to get the runtime value.
        mappedCalls.add(THIS_DOT + bindCpModelToVar + "(\"" + idValue + "\", \"" + cpModelAttributeNode.getValue() + "\");");

        return mappedCalls;
    }
}
