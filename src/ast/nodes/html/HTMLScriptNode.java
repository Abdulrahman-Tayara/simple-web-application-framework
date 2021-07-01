package ast.nodes.html;

public class HTMLScriptNode extends HTMLElementNode {
    private String scriptBody;

    public String getScriptBody() {
        return scriptBody;
    }

    public void setScriptBody(String scriptBody) {
        this.scriptBody = scriptBody;
    }

    @Override
    public String toHtml() {
        return "<script>\n" + scriptBody + "\n</script>";
    }
}
