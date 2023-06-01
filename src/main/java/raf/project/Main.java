package raf.project;

import raf.project.back_end.lexer.Lexer;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.Parser;
import raf.project.back_end.parser.ParserAPI;
import raf.project.back_end.parser.symbol.Symbol;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input = readFromConsole();
        LexerAPI lexer = new Lexer();
        ParserAPI parser = new Parser(lexer);
        List<Symbol> inputTokens = lexer.tokenize(input);
        System.out.println(inputTokens);
        System.out.println("THAT WAS LEXER NOW LES TRY PARSING");

        try{
            System.out.println(parser.parse(inputTokens));
        }catch (Exception e){
            e.printStackTrace();
        }


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