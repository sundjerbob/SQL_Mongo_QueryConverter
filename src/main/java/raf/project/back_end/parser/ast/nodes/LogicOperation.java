package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.parser.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class LogicOperation extends ASTNode {


    Symbol myOperation;
    public LogicOperation(Symbol symbol) {
        myOperation = symbol;
    }

    public LogicOperation() {
        super();
    }


}
