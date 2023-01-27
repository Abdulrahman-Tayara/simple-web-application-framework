package cg.scripting;

import ast.nodes.html.HTMLTagNode;

public interface ScriptableNode {
    ScriptCode generateScript(HTMLTagNode tag);
}
