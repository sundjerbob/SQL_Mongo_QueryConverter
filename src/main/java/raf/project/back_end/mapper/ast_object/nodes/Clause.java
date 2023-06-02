package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

public class Clause extends ASTNode {

    Symbol keyword;

    public Clause()
    {

    }

    public Clause(Symbol symbol) {
        keyword = symbol;
    }




}
