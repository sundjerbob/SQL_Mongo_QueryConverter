package raf.project.app.parser.ast.clauses;


import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;

public class MyQuery extends ASTNode {



    public MyQuery() {

super();    }

    @Override
    public String toString() {
        StringBuilder toStr = new StringBuilder("Query \n");
        for(Object child : children) {
            if(child instanceof LexerAPI.TokenTable)
                toStr.append("\n").append("OPERATOR :").append(child).append("\n");
            else
                toStr.append(child.toString());
        }
        return toStr.toString();
    }
}
