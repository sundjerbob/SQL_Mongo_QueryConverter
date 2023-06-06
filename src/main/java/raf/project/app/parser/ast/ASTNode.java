package raf.project.app.parser.ast;

import raf.project.app.lexer.LexerAPI.TokenTable;
import raf.project.app.error.GrammarError;

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

    public ASTNode addChild(Object child) throws GrammarError {
        children.add(child);
        return this;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nClause").append(this.getClass().getName()).append('\n').append("{").append('\n');

        for (Object child : children) {
            if(child instanceof TokenTable)
                stringBuilder.append("operetor: ").append(child).append('\n');
            else if(child instanceof Integer)
                stringBuilder.append("Int konstanta: ").append(child).append('\n');
            else if(child instanceof String)
            {
                if( ((String) child).charAt(0) == '\'' && ((String) child).charAt(((String) child).length() - 1) == '\'' )
                    stringBuilder.append("String konstanta: ").append(child).append('\n');
                else
                    stringBuilder.append("ID : ").append(child).append('\n');
            }
            else if(child instanceof ASTNode)
                stringBuilder.append(child);
        }
        return stringBuilder.append("   } ").toString();
    }


}
