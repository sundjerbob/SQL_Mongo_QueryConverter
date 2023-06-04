package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.parser.ast.ASTNode;
import raf.project.error.SyntaxError;
import static raf.project.back_end.lexer.LexerAPI.TokenTable.*;
import raf.project.back_end.lexer.LexerAPI.TokenTable;
import raf.project.back_end.parser.symbol.*;

public class InArgumentList extends ASTNode {

    private TokenTable listType;
    public InArgumentList() {

    }


    @Override
    public ASTNode addChild(Object child) throws SyntaxError {

        if (!(child instanceof Symbol))
            throw new RuntimeException("wtf.....('0,o)");

        if(children.isEmpty())
            listType = ((Symbol) child).tokenType;

        if (listType == INT_CONST)
            children.add(Integer.parseInt(((Symbol) child).getValue()));
        else
            children.add(((Symbol) child).getValue());


        return this;
    }

    public TokenTable getListType() {
        return listType;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Object curr : children)
        {
           str.append(curr).append("\n");
        }
        return str.toString();
    }
}
