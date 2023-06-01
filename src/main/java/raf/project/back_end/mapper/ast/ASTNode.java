package raf.project.back_end.mapper.ast;

import raf.project.back_end.lexer.LexerAPI.TokenTable;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private TokenTable tokenType;
    private String value;
    protected List<ASTNode> children;

    public ASTNode(TokenTable tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public ASTNode addChild(ASTNode child) {
        children.add(child);
        return this;
    }

    @Override
    public String toString() {
        return "majmunina";
    }
}
