import SymbolTable.DefSymbols;
import ast.nodes.html.HtmlDocumentNode;
import ast.visitor.BaseVisitor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import gen.HTMLLexer;
import gen.HTMLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.json.Json;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Main {

    public static void main(String[] args) {
        try {
            String source = "src/samples/sample1.txt";
            String resultFile = "src/samples/result.json";

            CharStream cs = fromFileName(source);
            HTMLLexer lexer = new HTMLLexer(cs);
            CommonTokenStream token = new CommonTokenStream(lexer);
            HTMLParser parser = new HTMLParser(token);
            ParseTree tree = parser.htmlDocument();
            HtmlDocumentNode doc = (HtmlDocumentNode) new BaseVisitor().visit(tree);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String json = gson.toJson(doc);
//            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

//            System.out.println(json);

//            FileWriter writer = new FileWriter(resultFile);
//            writer.write(jsonObject.toString());
//            writer.close();

            ParseTreeWalker walker = new ParseTreeWalker();
            DefSymbols def = new DefSymbols();
            walker.walk(def, tree);


//            String json = gson.toJson(def.currentScope);

            System.out.println();
            System.out.println(def.currentScope.toString());




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
