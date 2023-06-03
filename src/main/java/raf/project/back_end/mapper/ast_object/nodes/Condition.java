package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;

public class Condition extends ASTNode {


    public Condition() {
        super();
    }

    public Condition(Expression expression) {
        addChild(expression);

    }



}
