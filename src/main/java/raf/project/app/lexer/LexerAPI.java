package raf.project.app.lexer;

import raf.project.app.parser.ParserAPI;
import raf.project.app.parser.symbol.SymbolStack;

/**
 * @author Tadija
 * @apiNote Lexical analyzer unit interface defines functions are used by a
 * parsing unit defined in <code>ParserAPI</code> for breaking down a raw
 * input stream into words-lexemes then a list of parsable symbols
 * @see ParserAPI
 */
public interface LexerAPI {


    SymbolStack tokenize(String input);


    /**
     * Defines the syntax (language rules), wrapped into a list of acceptable lexemes-words.
     *
     * @author Tadija
     * @apiNote <code>enum TokenTable</code> enumeration array of terminal symbols - tokens,
     * each with its regex respectfully.
     * It stores a list of terminal symbols each with its regex pattern that defines a set acceptable lexemes.
     * Every lexeme from input needs to be matched in order for the input to be tokenized
     * into a list of parsable terminal symbols.
     * When the token is matched it is wrapped in a <code>class Symbol</code> class instance
     * and passed forward to a parsing unit.
     * <p>
     */
    enum TokenTable {

        SELECT("\\b(?i)SELECT\\b"),
        DISTINCT("\\b(?i)DISTINCT\\b"),
        FROM("\\b(?i)FROM\\b"),
        WHERE("\\b(?i)WHERE\\b"),
        LIKE("\\b(?i)LIKE\\b"),
        IN("\\b(?i)IN\\b"),
        JOIN("\\b(?i)JOIN\\b"),
        ON("\\b(?i)ON\\b"),
        USING("\\b(?i)USING\\b"),
        GROUP("\\b(?i)GROUP\\b"),
        BY("\\b(?i)BY\\b"),
        ORDER("\\b(?i)ORDER\\b"),
        AND("\\b(?i)AND\\b"),
        OR("\\b(?i)OR\\b"),
        LEFT_PAR("\\("),
        RIGHT_PAR("\\)"),
        SEMI_COLUMN(";"),
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        GREATER(">"),
        GREATER_OR_EQUAL(">="),
        EQUAL("="),
        NOT_EQUAL("!="),
        COMMA(","),
        ASTERISK("\\*"),
        INT_CONST("\\d+"),
        STR_CONST("'[^']+'"),
        //ids go after keywords because id regex can catch any keyword, so keywords have priority and go first
        ID("(?=.*[a-zA-Z])[a-zA-Z0-9_]*[a-zA-Z][a-zA-Z0-9_]*");

        private final String lexemeRegex;

        TokenTable(String regex) {
            lexemeRegex = regex;
        }

        /**
         * Method used to try to match a word-lexeme with a <code>TokenTable</code> enumeration instance based on
         * if that instances regex pattern accepts the lexeme or not.
         *
         * @param lexeme the word that we want to try to match with any existing symbol regex requirements.
         * @return an instance of <code>enum TokenTable</code> witch signifies the type
         * of token that was matched with the lexeme from input stream.
         * Returns is <code>null</code> , if the lexeme couldn't be matched to any pattern from the table.
         * @author Tadija
         */
        public static TokenTable matchToken(String lexeme) {

            for (TokenTable curr : TokenTable.values()) {
                if (lexeme.matches(curr.lexemeRegex)) {
                   // System.out.println("matched");
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
