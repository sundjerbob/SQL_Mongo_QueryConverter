package raf.project.app.parser.ast.clauses;

import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.symbol.Symbol;
import raf.project.app.error.GrammarError;

public class InArgumentList extends ASTNode {

    private LexerAPI.TokenTable listType;
    public InArgumentList() {
    }

    @Override
    public ASTNode addChild(Object child) throws GrammarError {

        if (!(child instanceof Symbol))
            throw new RuntimeException("wtf.....('0,o)");

        if(children.isEmpty())
            listType = ((Symbol) child).tokenType;

        if (listType == LexerAPI.TokenTable.INT_CONST)
            children.add(Integer.parseInt(((Symbol) child).getValue()));
        else if (listType == LexerAPI.TokenTable.STR_CONST)
            children.add(((Symbol) child).getValue());


        return this;
    }

    public LexerAPI.TokenTable getListType() {
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
