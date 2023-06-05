package raf.project.app.parser.symbol;


import raf.project.app.lexer.LexerAPI;

public class Symbol {

    public LexerAPI.TokenTable tokenType;
    public Symbol next;

    private String value;

    protected Symbol() {}

    public Symbol(LexerAPI.TokenTable tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }


    public LexerAPI.TokenTable getTokenType() {
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
