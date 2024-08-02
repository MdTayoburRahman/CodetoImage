package com.mdtayoburrahman.codetoimage.Utils;

import android.content.Context;
import android.graphics.Color;

import com.amrdeveloper.codeview.CodeView;
import com.mdtayoburrahman.codetoimage.R;

import java.util.HashMap;
import java.util.regex.Pattern;

public class CodeViewSetupUtils {

    private Context mContext;

    public CodeViewSetupUtils(Context mContext) {
        this.mContext = mContext;
    }

    /**Languages Setup methods*/
    public void codeViewSetupForJava(CodeView codeView){
        // Define syntax patterns for java
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(class|public|protected|private|static|final|void|int|long|float|double|boolean|char|byte|new|return|if|else|for|while" +
                "|do|switch|case|default|break|continue|try|catch|finally|throw|throws|this|super|extends|implements" +
                "|import|package|interface|abstract|enum|synchronized|volatile|transient|native|instanceof|assert" +
                "|const|goto)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\""), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|null)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators
        syntaxPatterns.put(Pattern.compile("@\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), Color.parseColor("#2E8B57")); // Annotations

        codeView.setSyntaxPatternsMap(syntaxPatterns);
        // Set some initial code
        codeView.setText("public class MainActivity extends AppCompatActivity {\n" +
                "    // This is a comment\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "    private int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "}");

    }
    public void codeViewSetupForPython(CodeView codeView) {
        // Define syntax patterns for Python
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(def|class|if|elif|else|while|for|break|continue|return|try" +
                "|except|finally|with|as|import|from|lambda|yield|global|nonlocal|assert|async|await|pass|raise" +
                "|True|False|None)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("#.*"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(True|False|None)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators
        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        codeView.setText("def add(a, b):\n" +
                "    # This is a comment\n" +
                "    return a + b\n" +
                "\n" +
                "print(add(3, 4))");


    }
    public void codeViewSetupForJavaScript(CodeView codeView) {
        // Define syntax patterns for JavaScript
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(function|var|let|const|if|else|for|while|do|switch|case|" +
                "default|break|continue|return|try|catch|finally|throw|this|new|typeof|instanceof|void|delete|" +
                "in|of|await|async|class|extends|import|export|default|" +
                "true|false|null|undefined|NaN|Infinity)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'|`([^`])*`"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|null|undefined|NaN|Infinity)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);


        // Set some initial code
        codeView.setText("function add(a, b) {\n" +
                "    // This is a comment\n" +
                "    return a + b;\n" +
                "}\n" +
                "\n" +
                "console.log(add(3, 4));");

    }
    public void codeViewSetupCSharp(CodeView codeView) {
        // Define syntax patterns for C#
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(class|public|protected|private|static|void|int|long|" +
                "float|double|bool|char|byte|string|new|return|if|else|for|while|do|switch|case|default|break|" +
                "continue|try|catch|finally|throw|throws|this|base|using|namespace|enum|struct|interface|abstract" +
                "|sealed|override|virtual|async|await|true|false|null|const|readonly|get|set|event|delegate|lock|" +
                "checked|unchecked|fixed|unsafe|extern|ref|out|sizeof|typeof|stackalloc|nameof|var|dynamic|" +
                "yield|params|in|is|as)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\""), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|null)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        codeView.setText("public class Program {\n" +
                "    // This is a comment\n" +
                "    public static void Main(string[] args) {\n" +
                "        Console.WriteLine(\"Hello, World!\");\n" +
                "    }\n" +
                "    private int Add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "}");

    }
    public void codeViewSetupForHtml(CodeView codeView) {
        // Define syntax patterns for HTML
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        // HTML Tags
        syntaxPatterns.put(Pattern.compile("<!DOCTYPE>|<!DOCTYPE html>|</?\\w+\\b[^>]*>", Pattern.DOTALL),
                mContext.getColor(R.color.ligh_blue));
        // Attribute values
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\""), Color.GREEN);
        // Comments
        syntaxPatterns.put(Pattern.compile("<!--.*?-->", Pattern.DOTALL), Color.GRAY);
        // Text inside tags (not ideal for nested or multiline tags)
        syntaxPatterns.put(Pattern.compile(">([^<]+)<", Pattern.DOTALL), Color.WHITE);
        // Attributes (e.g., class, id) before the '='
        syntaxPatterns.put(Pattern.compile("\\b(class|id)\\b(?=\\s*=)"), mContext.getColor(R.color.orange));
        // Attribute values (e.g., class="value", id="value")
        syntaxPatterns.put(Pattern.compile("\\b(class|id|style|type|placeholder|required|href)\\s*=\\s*\"[^\"]*\""), mContext.getColor(R.color.orange)); // Add a new color
        codeView.setSyntaxPatternsMap(syntaxPatterns);


        // Set some initial code
        codeView.setText("<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>My Page</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <!-- This is a comment -->\n" +
                "        <h1 class=\"header\">Hello, World!</h1>\n" +
                "        <p id=\"paragraph\">This is a paragraph.</p>\n" +
                "    </body>\n" +
                "</html>");

    }
    public void codeViewSetupForRuby(CodeView codeView) {
        // Define syntax patterns for Ruby
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(def|class|module|if|elsif|else|unless|while|until|for|do|break" +
                "|next|redo|retry|return|yield|super|self|nil|true|false|and|or|not|alias|begin|rescue|ensure|" +
                "end|case|when|then|in|unless|BEGIN|END|__FILE__|__LINE__)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("#.*"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|nil)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        codeView.setText("class HelloWorld\n" +
                "    # This is a comment\n" +
                "    def initialize(name)\n" +
                "        @name = name\n" +
                "    end\n" +
                "\n" +
                "    def say_hello\n" +
                "        puts \"Hello, #{@name}!\"\n" +
                "    end\n" +
                "end\n" +
                "\n" +
                "hello = HelloWorld.new('World')\n" +
                "hello.say_hello");

    }
    public void codeViewSetupSwift(CodeView codeView) {

        // Define syntax patterns for Swift
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(class|struct|enum|protocol|extension|func|let|var|if|else|for" +
                "|while|do|switch|case|default|break|continue|return|try|catch|throw|throws|defer|guard|repeat|self" +
                "|super|nil|true|false|import|init|deinit|typealias|associatedtype|inout|static|subscript|where|as|" +
                "is|in|out|do|rethrows|await|async|some|any)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|nil)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        codeView.setText("class HelloWorld {\n" +
                "    // This is a comment\n" +
                "    var name: String\n" +
                "\n" +
                "    init(name: String) {\n" +
                "        self.name = name\n" +
                "    }\n" +
                "\n" +
                "    func sayHello() {\n" +
                "        print(\"Hello, \\(name)!\")\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "let hello = HelloWorld(name: \"World\")\n" +
                "hello.sayHello()");

    }
    public void codeViewSetupPHP(CodeView codeView) {
        // Define syntax patterns for PHP
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(abstract|and|array|as|break|case|catch|class|clone|const" +
                "|continue|declare|default|do|else|elseif|enddeclare|endfor|endforeach|endif|endswitch|endwhile|" +
                "extends|final|finally|for|foreach|function|global|goto|if|implements|include|include_once|" +
                "instanceof|insteadof|interface|namespace|new|or|print|private|protected|public|require|" +
                "require_once|return|static|switch|throw|trait|try|use|var|while|xor|yield)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), mContext.getColor(R.color.ligh_blue)); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/|#.*"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|null)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);
        // Set some initial code
        codeView.setText("<?php\n" +
                "// This is a comment\n" +
                "class HelloWorld {\n" +
                "    private $name;\n" +
                "    public function __construct($name) {\n" +
                "        $this->name = $name;\n" +
                "    }\n" +
                "    public function sayHello() {\n" +
                "        echo \"Hello, $this->name!\";\n" +
                "    }\n" +
                "}\n" +
                "$hello = new HelloWorld('World');\n" +
                "$hello->sayHello();\n" +
                "?>");

    }
    public void codeViewSetupSQL(CodeView codeView) {
        // Define syntax patterns for PHP
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        // SQL Keywords
        syntaxPatterns.put(Pattern.compile("\\b(SELECT|FROM|WHERE|JOIN|ON|GROUP BY|ORDER BY|INSERT INTO|" +
                "VALUES|UPDATE|SET|DELETE|CREATE TABLE|ALTER TABLE|DROP TABLE|INNER JOIN|LEFT JOIN|RIGHT JOIN|" +
                "FULL JOIN|AND|OR|NOT|IS NULL|IS NOT NULL|LIKE|IN|BETWEEN|EXISTS|DISTINCT|AS|UNION|ALL|ANY|CASE" +
                "|WHEN|THEN|ELSE|END)\\b", Pattern.CASE_INSENSITIVE), mContext.getColor(R.color.ligh_blue)); // Keywords

        // SQL Strings
        syntaxPatterns.put(Pattern.compile("'(\\\\.|[^'])*'", Pattern.DOTALL), Color.GREEN); // Strings

        // SQL Numbers
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers

        // SQL Comments
        syntaxPatterns.put(Pattern.compile("--.*?$", Pattern.MULTILINE), Color.GRAY); // Single-line comments
        syntaxPatterns.put(Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL), Color.GRAY); // Multi-line comments

        // SQL Operators
        syntaxPatterns.put(Pattern.compile("[=<>!]+"), Color.parseColor("#FF8C00")); // Operators

        // SQL Delimiters
        syntaxPatterns.put(Pattern.compile("[(),;.]"), mContext.getColor(R.color.orange)); // Delimiters

        // Table Names and Column Names (Identifiers)
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b(?=\\s*(\\(|,|\\)|=|<|>|!|\\+|\\-|\\*|/|AS|JOIN|WHERE|GROUP BY|ORDER BY|VALUES|SET|FROM|SELECT|INSERT INTO|UPDATE|DELETE|CREATE TABLE|ALTER TABLE|DROP TABLE|INNER JOIN|LEFT JOIN|RIGHT JOIN|FULL JOIN))", Pattern.CASE_INSENSITIVE), mContext.getColor(R.color.purple));


        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        // Set some initial SQL code
        codeView.setText("SELECT id, name, age\n" +
                "FROM users\n" +
                "WHERE age > 18\n" +
                "ORDER BY age DESC;\n" +
                "-- This is a comment\n" +
                "INSERT INTO users (name, age) VALUES ('John Doe', 30);\n" +
                "/*\n" +
                "   This is a multi-line comment\n" +
                "*/\n" +
                "UPDATE users\n" +
                "SET age = age + 1\n" +
                "WHERE name = 'John Doe';\n" +
                "DELETE FROM users\n" +
                "WHERE name = 'John Doe';");


    }
    public void codeViewSetupKotlin(CodeView codeView) {
        // Define syntax patterns for Kotlin
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(package|import|class|object|interface|enum|fun|val|var|if|" +
                "else|when|for|while|do|try|catch|finally|throw|return|break|continue|this|super|in|is|as|true|" +
                "false|null|private|protected|public|internal|open|final|abstract|override|lateinit|lazy|by|" +
                "companion|init|constructor|delegate|dynamic|field|get|set|operator|infix|inline|tailrec|" +
                "external|annotation|crossinline|noinline|reified|suspend|inner|out|sealed|data|inner|sealed|" +
                "data|enum|annotation|crossinline|noinline|reified|suspend|constructor|delegated|internal|" +
                "open|infix|inline|tailrec|external|annotation)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|null)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        codeView.setText("fun main() {\n" +
                "    // This is a comment\n" +
                "    val name = \"World\"\n" +
                "    println(\"Hello, \\$name!\")\n" +
                "}\n" +
                "\n" +
                "class HelloWorld(val name: String) {\n" +
                "    fun sayHello() {\n" +
                "        println(\"Hello, \\$name!\")\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "val hello = HelloWorld(\"World\")\n" +
                "hello.sayHello()");


    }
    public void codeViewSetupGo(CodeView codeView) {
        // Define syntax patterns for Go
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(package|import|func|var|const|type|struct|interface|if|else|" +
                "for|range|return|go|defer|break|continue|goto|switch|case|default|select|fallthrough|chan|map|" +
                "true|false|nil|iota)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|nil)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);
        // Set some initial code
        codeView.setText("package main\n" +
                "\n" +
                "// This is a comment\n" +
                "import \"fmt\"\n" +
                "\n" +
                "func main() {\n" +
                "    name := \"World\"\n" +
                "    fmt.Println(\"Hello,\", name)\n" +
                "}\n" +
                "\n" +
                "type HelloWorld struct {\n" +
                "    name string\n" +
                "}\n" +
                "\n" +
                "func (h HelloWorld) sayHello() {\n" +
                "    fmt.Println(\"Hello,\", h.name)\n" +
                "}\n" +
                "\n" +
                "hello := HelloWorld{name: \"World\"}\n" +
                "hello.sayHello()");


    }
    public void codeViewSetupRust(CodeView codeView) {
        // Define syntax patterns for Rust
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(abstract|as|async|await|const|crate|dyn|enum|extern|" +
                "false|fn|impl|in|let|loop|macro|match|mod|move|mut|pub|ref|return|self|static|struct|" +
                "super|trait|true|type|union|unsafe|use|where|while|yield|continue|break|else|for|if|" +
                "match|return|while|loop|Self)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("\\b\\d+\\b"), Color.BLUE); // Numbers
        syntaxPatterns.put(Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b(true|false|None|Some|Ok|Err)\\b"), Color.parseColor("#FF4500")); // Constants
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);
        // Set some initial code
        codeView.setText("fn main() {\n" +
                "    // This is a comment\n" +
                "    let name = \"World\";\n" +
                "    println!(\"Hello, {}!\", name);\n" +
                "}\n" +
                "\n" +
                "struct HelloWorld {\n" +
                "    name: String,\n" +
                "}\n" +
                "\n" +
                "impl HelloWorld {\n" +
                "    fn say_hello(&self) {\n" +
                "        println!(\"Hello, {}!\", self.name);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "let hello = HelloWorld { name: \"World\".to_string() };\n" +
                "hello.say_hello();");


    }
    public void codeViewSetupCSS(CodeView codeView) {
        // Define syntax patterns for CSS
        HashMap<Pattern, Integer> syntaxPatterns = new HashMap<>();
        syntaxPatterns.put(Pattern.compile("\\b(align-content|align-items|align-self|all|animation|" +
                "animation-delay|animation-direction|animation-duration|animation-fill-mode|" +
                "animation-iteration-count|animation-name|animation-play-state|animation-timing-function|" +
                "backface-visibility|background|background-attachment|background-blend-mode|background-clip|" +
                "background-color|background-image|background-origin|background-position|background-repeat|" +
                "background-size|border|border-bottom|border-bottom-color|border-bottom-left-radius|" +
                "border-bottom-right-radius|border-bottom-style|border-bottom-width|border-collapse|" +
                "border-color|border-image|border-image-outset|border-image-repeat|border-image-slice|" +
                "border-image-source|border-image-width|border-left|border-left-color|border-left-style|" +
                "border-left-width|border-radius|border-right|border-right-color|border-right-style|" +
                "border-right-width|border-spacing|border-style|border-top|border-top-color|border-top-left-radius|" +
                "border-top-right-radius|border-top-style|border-top-width|border-width|bottom|box-decoration-break|" +
                "box-shadow|box-sizing|break-after|break-before|break-inside|caption-side|caret-color|" +
                "@charset|clear|clip|color|column-count|column-fill|column-gap|column-rule|column-rule-color|" +
                "column-rule-style|column-rule-width|column-span|column-width|columns|content|counter-increment|" +
                "counter-reset|cursor|direction|display|empty-cells|filter|flex|flex-basis|flex-direction|" +
                "flex-flow|flex-grow|flex-shrink|flex-wrap|float|font|@font-face|font-family|" +
                "font-feature-settings|font-kerning|font-language-override|font-size|font-size-adjust|" +
                "font-stretch|font-style|font-synthesis|font-variant|font-variant-alternates|font-variant-caps|" +
                "font-variant-east-asian|font-variant-ligatures|font-variant-numeric|font-variant-position|" +
                "font-weight|grid|grid-area|grid-auto-columns|grid-auto-flow|grid-auto-rows|grid-column|" +
                "grid-column-end|grid-column-gap|grid-column-start|grid-gap|grid-row|grid-row-end|" +
                "grid-row-gap|grid-row-start|grid-template|grid-template-areas|grid-template-columns|" +
                "grid-template-rows|hanging-punctuation|height|hyphens|image-rendering|@import|isolation|" +
                "justify-content|left|letter-spacing|line-break|line-height|list-style|list-style-image|" +
                "list-style-position|list-style-type|margin|margin-bottom|margin-left|margin-right|margin-top|" +
                "max-height|max-width|@media|min-height|min-width|mix-blend-mode|object-fit|object-position|" +
                "opacity|order|orphans|outline|outline-color|outline-offset|outline-style|outline-width|overflow|overflow-wrap|overflow-x|overflow-y|padding|padding-bottom|padding-left|padding-right|padding-top|page-break-after|page-break-before|page-break-inside|perspective|perspective-origin|pointer-events|position|quotes|resize|right|scroll-behavior|table-layout|tab-size|text-align|text-align-last|text-combine-upright|text-decoration|text-decoration-color|text-decoration-line|text-decoration-style|text-indent|text-justify|text-orientation|text-overflow|text-shadow|text-transform|text-underline-position|top|transform|transform-origin|transform-style|transition|transition-delay|transition-duration|transition-property|transition-timing-function|unicode-bidi|user-select|vertical-align|visibility|white-space|widows|width|word-break|word-spacing|word-wrap|writing-mode|z-index|@keyframes|@supports|@namespace|@document|@page|@viewport|@counter-style|@font-feature-values|@property)\\b"), mContext.getColor(R.color.orange)); // Keywords
        syntaxPatterns.put(Pattern.compile("\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'"), Color.GREEN); // Strings
        syntaxPatterns.put(Pattern.compile("#[A-Fa-f0-9]{6}\\b|#[A-Fa-f0-9]{3}\\b"), Color.BLUE); // Hex Colors
        syntaxPatterns.put(Pattern.compile("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/"), Color.GRAY); // Comments
        syntaxPatterns.put(Pattern.compile("\\b[0-9]+(px|em|rem|%)?\\b"), Color.BLUE); // Numbers with units
        syntaxPatterns.put(Pattern.compile("[{}()\\[\\].,:;]"), mContext.getColor(R.color.orange)); // Delimiters
        syntaxPatterns.put(Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_-]*)\\b"), mContext.getColor(R.color.white)); // Identifiers
        syntaxPatterns.put(Pattern.compile("[-+*/=<>!&|~^%]+"), Color.parseColor("#FF8C00")); // Operators

        codeView.setSyntaxPatternsMap(syntaxPatterns);

        // Set some initial code
        codeView.setText("body {\n" +
                "    background-color: #ffffff; /* This is a comment */\n" +
                "    color: #333333;\n" +
                "    font-family: 'Arial', sans-serif;\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "}\n" +
                "h1 {\n" +
                "    font-size: 2em;\n" +
                "    color: #ff4500;\n" +
                "}\n" +
                ".container {\n" +
                "    width: 80%;\n" +
                "    margin: 0 auto;\n" +
                "}\n" +
                "#header {\n" +
                "    background: #000000;\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                "a {\n" +
                "    color: #0088cc;\n" +
                "    text-decoration: none;\n" +
                "}\n" +
                "a:hover {\n" +
                "    text-decoration: underline;\n" +
                "}");

    }
}
