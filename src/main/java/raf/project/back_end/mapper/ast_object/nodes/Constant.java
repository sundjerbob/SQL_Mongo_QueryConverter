package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class Constant extends ASTNode {


    private Symbol symbol;

    public Constant(Symbol symbol) {
        this.symbol = symbol;
    }

    public Constant(){}




    public Symbol getSymbol() {
        return symbol;
    }
}
