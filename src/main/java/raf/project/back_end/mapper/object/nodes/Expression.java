package raf.project.back_end.mapper.object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;

public class Expression extends ASTNode {
    public Expression(){}

    public Expression(Expression leftExpression, MathOperation operation, Expression rightExpression) {
        addChild(leftExpression);
        addChild(operation);
        addChild(rightExpression);
    }

    public Expression(ASTNode ... children) {
        if(children.length == 1)
            addChild(children[0]);
        else if(children.length == 3) {
            addChild((Expression)children[0]);
            addChild((Expression) children[2]);
            addChild((MathOperation) children[1]);
        }
    }


}
