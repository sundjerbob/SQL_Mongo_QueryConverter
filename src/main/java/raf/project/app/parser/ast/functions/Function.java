package raf.project.app.parser.ast.functions;

import raf.project.app.error.GrammarError;
import raf.project.app.lexer.LexerAPI.TokenTable;
import raf.project.app.parser.ast.ASTNode;

public class Function extends ASTNode {


    private final TokenTable functionName;
    String functionArgument;

    public Function(TokenTable functionName) {
        this.functionName = functionName;
    }

/*
    @Override
    public ASTNode addChild( Object child) {

            if(child instanceof String)
                functionArgument = (String) child;
            else
                throw new RuntimeException("funkcija je dobila pogresan argument.");
            return this;

    }*/


    public TokenTable getFunctionName() {
        return functionName;
    }

    public String getFunctionArgument() {
        return functionArgument;
    }
}
