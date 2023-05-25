package raf.project;

import raf.project.back_end.query_parser.QueryParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        StringBuilder query = new StringBuilder();
        String line;
        while (scanner.hasNextLine()) {

           if((line = scanner.nextLine()).equals(""))
               break;
           query.append(line).append("\n");
        }
        System.out.println(query.toString());

        System.out.println(QueryParser.parseQuery(query.toString()).toString());
        scanner.close();

    }
}