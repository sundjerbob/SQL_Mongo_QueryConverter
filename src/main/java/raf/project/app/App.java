package raf.project.app;

import raf.project.back_end.lexer.Lexer;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.Parser;
import raf.project.back_end.parser.ParserAPI;
import raf.project.back_end.parser.symbol.Symbol;
import raf.project.front_end.MainFrame;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
       // Test.runTest();
        RepositoryService.executeMQLQuery(null);
        MainFrame mainFrame = new MainFrame();
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