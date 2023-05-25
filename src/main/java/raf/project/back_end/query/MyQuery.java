package raf.project.back_end.query;

import org.bson.Document;

import java.util.*;

public abstract class MyQuery extends Token {

    protected Map<String, List<Object>> tokens;

    public enum KeyWords {
        SELECT("select"), FROM("from"),
        WHERE("where"), JOIN("join"),
        ON("on"), GROUP_BY("group by"),
        HAVING("having"), ORDER_BY("ordered by");
        private final String key_word;

        KeyWords(String key_word){ this.key_word = key_word;}

        public static KeyWords isKeyWord(String possibleKeyword) {
            for(KeyWords curr : values()){
                if(curr.key_word.equals(possibleKeyword) || curr.key_word.toUpperCase().equals(possibleKeyword))
                    return curr;
            }
            return null;
        }
        @Override
        public String toString() {
            return key_word;
        }
    }


    public MyQuery() {
        tokens = new HashMap<>();
        for(KeyWords key : KeyWords.values()) {
            tokens.put(key.key_word, new ArrayList<>());
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : tokens.keySet()) {
            if(!tokens.get(key).isEmpty())
                stringBuilder.append("KEYWORD:    ").append(key).append("\n");
            for(Object curr : tokens.get(key))
                stringBuilder.append((String) curr).append("\n");

        }
        return stringBuilder.toString();
    }

    protected abstract Document[] translateToMongo();

    public Map<String, List<Object>> getTokens() {
        return tokens;
    }
}
