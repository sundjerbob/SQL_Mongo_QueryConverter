package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class Alias extends ASTNode {

    Symbol type;
    Symbol value;

    public Alias(Symbol type, Symbol value) {
        this.type = type;
        this.value = value;
    }

    public Alias() {

    }

    public Symbol getValue() {
        return value;
    }

    public Symbol getType() {
        return type;
    }
}
