package cg.callmapper;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.CPAttributeNode;
import ast.nodes.html.HTMLTagNode;

import java.util.List;

public interface CallMapper {
    List<String> map(HTMLTagNode tagNode, AttributeNode<?> attributeNode);
}
