import ast.nodes.html.HtmlDocumentNode;
import ast.visitor.BaseVisitor;
import com.google.gson.GsonBuilder;
import gen.HTMLLexer;
import gen.HTMLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Main {

    public static void main(String[] args) {
        try {
            String source = "src/samples/sample1.txt";
            CharStream cs = fromFileName(source);
            HTMLLexer lexer = new HTMLLexer(cs);
            CommonTokenStream token = new CommonTokenStream(lexer);
            HTMLParser parser = new HTMLParser(token);
            ParseTree tree = parser.htmlDocument();
            HtmlDocumentNode doc = (HtmlDocumentNode) new BaseVisitor().visit(tree);

            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(doc));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
