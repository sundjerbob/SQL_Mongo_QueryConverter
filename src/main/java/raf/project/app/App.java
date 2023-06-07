package raf.project.app;

import raf.project.app.parser.Parser;
import raf.project.app.parser.ast.query.MyQuery;
import raf.project.app.parser.symbol.SymbolStack;
import raf.project.app.service.LexerService;
import raf.project.app.service.ParserService;
import raf.project.app.service.QueryService;
import raf.project.desktop_view.MainFrame;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {



        /*String input = readFromConsole();




        try {

            SymbolStack stack = LexerService.MY_INSTANCE.performLexicalAnalysis(input);
            System.out.println(stack);
            MyQuery myQuery = (MyQuery) ParserService.MY_INSTANCE.performAbstractSyntaxTreeParsing(stack);
            System.out.println(myQuery);



        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("clean");*/

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
