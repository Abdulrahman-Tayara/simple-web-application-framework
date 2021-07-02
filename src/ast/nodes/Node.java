package ast.nodes;


public class Node implements Htmlable {

    transient private int line;
    transient private int col;

    public void setCol(int col) {
        this.col = col;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toHtml() {
        return "";
    }
}
