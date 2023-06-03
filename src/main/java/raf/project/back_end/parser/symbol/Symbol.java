package raf.project.back_end.parser.symbol;


import raf.project.back_end.lexer.LexerAPI.*;

public class Symbol {

    public TokenTable tokenType;
    public Symbol next;

    private String value;

    protected Symbol() {}

    public Symbol(TokenTable tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }


    public TokenTable getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "tokenType : " + tokenType +'\n' + "value : " + value + '\n';
    }
}
