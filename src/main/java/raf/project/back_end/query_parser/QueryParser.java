package raf.project.back_end.query_parser;

import org.bson.Document;
import raf.project.back_end.query.MyQuery;
import raf.project.back_end.query.MyQuery.*;

import java.util.regex.Pattern;

public class QueryParser {

    public static MyQuery parseQuery(String input) {

        // Regular expression pattern for delimiters: space, comma, newline, and tab
        //tokenization
        String delimiterRegex = "[\\s,]+";
        Pattern regex = Pattern.compile(delimiterRegex);
        String[] tokens = regex.split(input);

        StringBuilder result = new StringBuilder();
        for (String token : tokens) {
            result.append(token).append("\n");
        }
        if(result.charAt(result.length() - 1) == ';')
            result.setCharAt(result.length() - 1,' ');

        MyQuery query = new MyQuery() {
            @Override
            protected Document[] translateToMongo() {
                return new Document[0];
            }
        };

        KeyWords keyWord = null;
        for(int i = 0; i < tokens.length; i++) {
            if( KeyWords.isKeyWord(tokens[i])  != null) {
                keyWord = KeyWords.isKeyWord(tokens[i]);
                continue;
            }
            if(keyWord != null)
                query.getTokens().get(keyWord.toString()).add(tokens[i]);

        }
        return  query;
    }

}
