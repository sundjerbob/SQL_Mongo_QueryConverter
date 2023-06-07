package raf.project.app.parser.ast;

import raf.project.app.lexer.LexerAPI.TokenTable;
import raf.project.app.error.GrammarError;
import raf.project.app.parser.ast.functions.Function;
import raf.project.app.parser.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private TokenTable tokenType;
    protected List<Object> children;


    public ASTNode(){
        children = new ArrayList<>();
    }
    public TokenTable getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenTable tokenType) {
        this.tokenType = tokenType;
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
        stringBuilder.append("\n").append(this.getClass().getName()).append('\n').append("{").append('\n');

        if(this instanceof Function)
            stringBuilder.append("Function name:  ").append(((Function)this).getFunctionName().toString()).append('\n');
        for (Object child : children) {
            if(child instanceof Symbol)
                stringBuilder.append('\n').append(child.toString()).append('\n');
            if(child instanceof TokenTable)
                stringBuilder.append("\n").append("operetor: ").append(child).append('\n');
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
