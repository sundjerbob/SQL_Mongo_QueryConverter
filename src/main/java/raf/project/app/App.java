package raf.project.app;

import raf.project.back_end.lexer.Lexer;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.Parser;
import raf.project.back_end.parser.ParserAPI;
import raf.project.error.SyntaxError;
import raf.project.front_end.MainFrame;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Test.runTest();
        LexerAPI lexer = new Lexer();
        String input = readFromConsole();

           System.out.println(lexer.tokenize(input));
           System.out.println("--------------------");
           System.out.println(lexer.tokenize(input).popAllToStr());


        System.out.println("clean");
        Test.runTest();

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
