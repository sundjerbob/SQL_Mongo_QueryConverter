package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;

public class Condition extends ASTNode {


    public Condition() {
    }

    public Condition(Expression expression) {
        addChild(expression);

    }

    public Condition(Condition leftCondition, LogicOperation logicLink, Condition rightCondition) {
        addChild(leftCondition);
        addChild(logicLink);
        addChild(rightCondition);
    }


}
