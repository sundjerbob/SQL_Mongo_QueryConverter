package raf.project.app.parser.ast.clauses;

import raf.project.app.lexer.LexerAPI;

public class WhereClause extends Clause {
    public WhereClause() {
    }

    public LexerAPI.TokenTable getOperator(){
        for(Object child :children)
        {
            if(child instanceof LexerAPI.TokenTable)
                return (LexerAPI.TokenTable) child;
        }
        return null;
    }

}
