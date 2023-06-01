package raf.project.back_end.lexer;

import raf.project.back_end.parser.symbol.Symbol;

import java.util.List;

/**
 * @apiNote Lexical analyzer unit interface defines functions are used by a
 * parsing unit defined in <code>ParserAPI</code> for breaking down a raw
 * input stream into words-lexemes then a list of parsable symbols
 * @see raf.project.back_end.parser.ParserAPI
 * @author Tadija
 */
public interface LexerAPI {


    List<Symbol> tokenize(String input);


    /**
     *
     * @apiNote <code>enum TokenTable</code> enumeration array of terminal symbols - tokens,
     * each with its regex respectfully.
     * It stores list of terminal symbols each with its regex pattern that defines a set acceptable lexemes.
     * Every lexeme from input needs to be matched in order for the input to be tokenized
     * into a list of parsable terminal symbols.
     * When the token is matched it is wrapped in a <code>class Symbol</code> class instance
     * and passed forward to a parsing unit.
     * @author Tadija
     */
    enum TokenTable {
        SELECT("\\b(?i)SELECT\\b"),
        DISTINCT("\\b(?i)DISTINCT\\b"),
        FROM("\\b(?i)FROM\\b"),
        WHERE("\\b(?i)WHERE\\b"),
        JOIN("\\b(?i)JOIN\\b"),
        ON("\\b(?i)ON\\b"),
        GROUP("\\b(?i)GROUP\\b"),
        BY("\\b(?i)BY\\b"),
        HAVING("\\b(?i)HAVING\\b"),
        ORDER("\\b(?i)ORDER\\b"),
        AND("\\b(?i)AND\\b"),
        OR("\\b(?i)OR\\b"),
        //ids go after keywords because any keyword can be caught by id regex so keywords have priority and go first
        TABLE_ID("(?=.*[a-zA-Z])[a-zA-Z0-9_]*[a-zA-Z][a-zA-Z0-9_]*"),
        COLUMN_ID(TABLE_ID.lexemeRegex + "\\." + TABLE_ID.lexemeRegex),
        SEMI_COLUMN(";"),
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        GREATER(">"),
        GREATER_OR_EQUAL(">="),
        EQUAL("="),
        NOT_EQUAL("!="),
        COMMA(","),
        INT_CONST("\\d+"),
        STR_CONST("\"[^\"]+\"");

        private final String lexemeRegex;

        TokenTable(String regex) {
            lexemeRegex = regex;
        }

        /**
         * @param lexeme the word that we want to try to match with any existing symbol regex requirements.
         * @return an instance of <code>enum TokenTable</code> witch signifies the type
         * of token that was matched with the lexeme from input stream.
         * Returns is <code>null</code> , if the lexeme couldn't be matched to any pattern from the table.
         * @apiNote Method used to try to match a word-lexeme with a <code>TokenTable</code> enumeration instance based on
         * if that instances regex pattern accepts the lexeme or not.
         * @author Tadija
         */
        public static TokenTable matchToken(String lexeme) {

            for (TokenTable curr : TokenTable.values()) {
                if (lexeme.matches(curr.lexemeRegex)) {
                    System.out.println("matched");
                    return curr;

                }
            }
            return null;

        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
