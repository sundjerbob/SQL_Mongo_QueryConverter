package raf.project.app;

import raf.project.back_end.lexer.Lexer;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.ast.ASTNode;
import raf.project.back_end.parser.Parser;
import raf.project.back_end.parser.ParserAPI;
import raf.project.back_end.parser.symbol.SymbolStack;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Test.runTest();
        LexerAPI lexer = new Lexer();

        String input = readFromConsole();
        SymbolStack stack = lexer.tokenize(input);
        System.out.println(stack);

        ParserAPI parser = new Parser();

        try {
            ASTNode myQuery = parser.parse(stack);
            if(myQuery == null)
                System.out.println("MY KVERI TI JE NULA");
            System.out.println(myQuery);



        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("clean");
        //Test.runTest();

    }

    private static String readFromConsole() {
        Scanner scanner= new Scanner(System.in);
        StringBuilder query = new StringBuilder();
        String line;
        while (scanner.hasNextLine()) {

            if((line = scanner.nextLine()).trim().equals(""))
                break;
            query.append(line).append("\n");
        }
        scanner.close();
        return query.toString();
    }


}
