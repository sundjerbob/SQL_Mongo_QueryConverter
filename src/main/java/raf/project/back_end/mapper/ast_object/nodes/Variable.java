package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class Variable extends ASTNode {

    Symbol symbol;

    public Variable(Symbol symbol) {
        this.symbol = symbol;
    }

    public Variable() {

    }


    public Symbol getSymbol() {
        return symbol;
    }



}
