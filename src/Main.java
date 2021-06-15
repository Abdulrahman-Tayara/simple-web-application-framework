import SymbolTable.DefSymbols;
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
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import semantic_check.SymantecChecker;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Main {

    public static void main(String[] args) throws IOException {
            String source = "src/samples/input.txt";
            String sourceSYM = "src/samples/input-symbol.txt";
            String symbolTableResultFile = "src/samples/symbol-table-result.json";
            String astOutputFile = "src/samples/ast-output.json";

            CharStream cs = fromFileName(source);
            HTMLLexer lexer = new HTMLLexer(cs);
            CommonTokenStream token = new CommonTokenStream(lexer);
            HTMLParser parser = new HTMLParser(token);
            ParseTree tree = parser.htmlDocument();
            HtmlDocumentNode doc = (HtmlDocumentNode) new BaseVisitor().visit(tree);

            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .setPrettyPrinting()
                    .create();
            String json = gson.toJson(doc);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

//            System.out.println(json);

            System.out.println();


            FileWriter writer = new FileWriter(astOutputFile);
            writer.write(jsonObject.toString());
            writer.close();

            SymantecChecker checker = new SymantecChecker(doc);
            checker.execute();

            System.out.println("exceptions:");
            System.out.println(checker.getTotalExceptions());

            ParseTreeWalker walker = new ParseTreeWalker();
            DefSymbols def = new DefSymbols();
            walker.walk(def, tree);


            String jsonDef = gson.toJson(def);
            FileWriter writerDef = new FileWriter(symbolTableResultFile);
            writerDef.write(jsonDef);
            writerDef.close();
            System.out.println(def.globalScope.toString());

    }
}
