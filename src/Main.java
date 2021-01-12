import ast.nodes.html.HtmlDocumentNode;
import ast.visitor.BaseVisitor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gen.HTMLLexer;
import gen.HTMLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(doc);
            System.out.println(json);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
