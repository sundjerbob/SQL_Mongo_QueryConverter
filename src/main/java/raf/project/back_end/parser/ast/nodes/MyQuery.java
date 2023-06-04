package raf.project.back_end.parser.ast.nodes;


import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.ast.ASTNode;

public class MyQuery extends ASTNode {



    public MyQuery() {
super();    }

    @Override
    public String toString() {
        StringBuilder toStr = new StringBuilder("Query");
        for(Object child : children) {
            if(child instanceof LexerAPI.TokenTable)
                toStr.append("\n").append("OPERATOR :").append(child).append("\n");
            else
                toStr.append(child.toString());
        }
        return toStr.toString();
    }
}
