package raf.project.app;

import raf.project.app.service.QueryService;
import raf.project.desktop_view.MainFrame;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Test.runTest();
        /*LexerAPI lexer = new Lexer();

        String input = readFromConsole();
        SymbolStack stack = lexer.tokenize(input);

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
        //Test.runTest();*/
        //Test.runTest();
        MainFrame mainFrame = new MainFrame();
        String input = readFromConsole();
        try {
            mainFrame.getTable().displayResultSet(QueryService.MY_INSTANCE.runQuery(input));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Test.runTest();
        // RepositoryService.executeMQLQuery(null);



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
