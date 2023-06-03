package raf.project.back_end.parser.ast.nodes;


import raf.project.back_end.parser.ast.ASTNode;

public class MyQuery extends ASTNode {



    public MyQuery() {
super();    }

    @Override
    public String toString() {
        StringBuilder toStr = new StringBuilder("Query");
        for(Object child : children) {
            toStr.append(child.toString());
        }
        return toStr.toString();
    }
}
