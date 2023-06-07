package raf.project.app.parser.ast.functions;

import raf.project.app.error.GrammarError;
import raf.project.app.parser.ast.ASTNode;

public class OnFunction extends ASTNode {


    String firstArg;
    String secondArg;

    @Override
    public ASTNode addChild(Object child) throws GrammarError {
        if(children.size() == 0 && child instanceof String)
            firstArg = (String) child;
        else if(secondArg == null && child instanceof String)
            secondArg = (String) child;
        return super.addChild(child);
    }

    public String getFirstArg() {
        return firstArg;
    }

    public String getSecondArg() {
        return secondArg;
    }
}
