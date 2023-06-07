package raf.project.app.parser.ast.clauses;

import raf.project.app.error.GrammarError;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.functions.OnFunction;
import raf.project.app.parser.ast.functions.UsingFunction;

public class JoinClause extends Clause {

    String joinedTable;
    public JoinClause() {

    }


    @Override
    public ASTNode addChild(Object child) throws GrammarError {
        if(joinedTable == null && child instanceof String)
            joinedTable = (String) child;
        return super.addChild(child);
    }

    public OnFunction getOnFunction () {
        for(Object child :children)
        {
            if(child instanceof OnFunction)
                return (OnFunction) child;
        }
        return null;
    }

    public UsingFunction getUsingFunction () {
        for(Object child :children)
        {
            if(child instanceof UsingFunction)
                return (UsingFunction) child;
        }
        return null;
    }


    public String getJoinedTable() {
        return joinedTable;
    }
}
