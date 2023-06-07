package raf.project.app.parser.ast.query;


import raf.project.app.error.GrammarError;
import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.FromClause;

public class MyQuery extends ASTNode {

    String fromTable;

    public MyQuery() {
    }

    @Override
    public ASTNode addChild(Object child) throws GrammarError {
        if(child instanceof FromClause)
            fromTable =
                    (((ASTNode)child).getChildren().get(0) instanceof String) ?
                            (String) ((ASTNode)child).getChildren().get(0) : "";

        return super.addChild(child);
    }

    public String getFromTable() {
        return fromTable;
    }
}
