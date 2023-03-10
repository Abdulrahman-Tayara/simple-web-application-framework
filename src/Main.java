import symboltable.DefSymbols;
import ast.nodes.html.HtmlDocumentNode;
import ast.visitor.BaseVisitor;
import codegeneration.HtmlGenerator;
import codegeneration.IdsGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gen.HTMLLexer;
import gen.HTMLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import semantic.SymantecChecker;

import java.io.*;
import java.lang.reflect.Modifier;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Main {

    public static void main(String[] args) throws IOException {
        String source = "src/samples/input.txt";
        String sourceSYM = "src/samples/input-symbol.txt";
        String symbolTableResultFile = "src/samples/symbol-table-result.json";
        String astOutputFile = "src/samples/ast-output.json";
        String generatedOutputFile = "src/samples/index.html";

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


//        ParseTreeWalker walker = new ParseTreeWalker();
//        DefSymbols def = new DefSymbols();
//        walker.walk(def, tree);
//
//
//        String jsonDef = gson.toJson(def);
//        FileWriter writerDef = new FileWriter(symbolTableResultFile);
//        writerDef.write(jsonDef);
//        writerDef.close();
//        System.out.println(def.globalScope.toString());


//        SymantecChecker checker = new SymantecChecker(doc, def.globalScope);
//        checker.execute();
//
//        System.out.println("exceptions:");
//        System.out.println(checker.getTotalExceptions());




        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.generateHtmlTextFromAst(doc);
        String generatedHtmlCode = htmlGenerator.getGeneratedHtmlText();

        writer = new FileWriter(generatedOutputFile);
        writer.write(generatedHtmlCode);
        writer.close();
//
//        Tidy tidy = new Tidy();
//        tidy.setIndentContent(true);
//        tidy.setTidyMark(false);
//        Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(doc.toHtml().getBytes()), null);
//        OutputStream out = new ByteArrayOutputStream();
//        tidy.pprint(htmlDOM, out);
//        System.out.println(out.toString());
    }
}
