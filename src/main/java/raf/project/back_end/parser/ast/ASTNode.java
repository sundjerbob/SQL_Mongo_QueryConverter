package raf.project.back_end.parser.ast;

import raf.project.back_end.lexer.LexerAPI.TokenTable;
import raf.project.error.SyntaxError;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private TokenTable tokenType;
    private String lexemeValue;
    protected List<Object> children;

    public ASTNode(TokenTable tokenType, String lexemeValue) {
        this.tokenType = tokenType;
        this.lexemeValue = lexemeValue;
        this.children = new ArrayList<>();
    }

    public ASTNode(){
        children = new ArrayList<>();
    }
    public TokenTable getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenTable tokenType) {
        this.tokenType = tokenType;
    }

    public String getLexemeValue() {
        return lexemeValue;
    }

    public List<?> getChildren() {
        return children;
    }

    public ASTNode addChild(Object child) throws SyntaxError {
        children.add(child);
        return this;
    }


}
