package raf.project.app.parser.ast.query;


import raf.project.app.error.GrammarError;
import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.*;

public class MyQuery extends ASTNode {

    String fromTable;

    String joinTable;

    public MyQuery() {
    }

    @Override
    public ASTNode addChild(Object child) throws GrammarError {
        if(child instanceof FromClause)
            fromTable =
                    (((ASTNode)child).getChildren().get(0) instanceof String) ?
                            (String) ((ASTNode)child).getChildren().get(0) : "";
        if(child instanceof JoinClause)
            joinTable = (((ASTNode)child).getChildren().get(0) instanceof String) ?
                    (String) ((ASTNode)child).getChildren().get(0) : "";

        return super.addChild(child);
    }

    public String getFromTable() {
        return fromTable;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public FromClause getFromClause() {
        for (Object child :children) {
            if(child instanceof FromClause)
                return (FromClause) child;
        }
        return null;
    }
    public JoinClause getJoinClause() {
        for (Object child :children) {
            if(child instanceof JoinClause)
                return (JoinClause) child;
        }
        return null;
    }

    public SelectClause getSelectClause() {
        for (Object child :children) {
            if(child instanceof SelectClause)
                return (SelectClause) child;
        }
        return null;
    }

    public GroupByClause getGroupByClause() {
        for (Object child :children) {
            if(child instanceof GroupByClause)
                return (GroupByClause) child;
        }
        return null;
    }

    public OrderByClause getOrderByClause() {
        for (Object child : children) {
            if(child instanceof OrderByClause)
                return (OrderByClause) child;
        }
        return null;
    }

    public WhereClause getWhereClause() {
        for (Object child : children) {
            if(child instanceof WhereClause)
                return (WhereClause) child;
        }
        return null;
    }
}
