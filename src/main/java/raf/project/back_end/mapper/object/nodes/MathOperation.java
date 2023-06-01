package raf.project.back_end.mapper.object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class MathOperation extends ASTNode {
    public MathOperation(){

    }

    Symbol myOperation;
    public MathOperation(Symbol ... symbols){
        myOperation = symbols[0];
    }


}
