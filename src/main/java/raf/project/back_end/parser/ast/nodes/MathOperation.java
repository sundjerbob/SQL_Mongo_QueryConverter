package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.parser.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class MathOperation extends ASTNode {
    public MathOperation(){

    }

    Symbol myOperation;
    public MathOperation(Symbol ... symbols){
        myOperation = symbols[0];
    }


}
