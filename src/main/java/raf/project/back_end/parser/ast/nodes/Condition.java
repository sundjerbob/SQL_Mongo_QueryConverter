package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.parser.ast.ASTNode;

public class Condition extends ASTNode {


    public Condition() {
        super();
    }

    public Condition(Expression expression) {
        addChild(expression);

    }



}
