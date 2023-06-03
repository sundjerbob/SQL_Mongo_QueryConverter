package raf.project.back_end.mapper.ast_object.nodes;


import raf.project.back_end.mapper.ast.ASTNode;

import java.util.ArrayList;

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
