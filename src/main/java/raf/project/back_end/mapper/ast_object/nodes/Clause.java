package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.lexer.LexerAPI.TokenTable;
import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

import java.util.ArrayList;

public class Clause extends ASTNode {

    TokenTable keyword;

    public Clause() {super();
    }

    public Clause(TokenTable keyword) {
        this.keyword = keyword;
    }


    @Override
    public String toString() {

        return "\nClause{ My keyword is: " + keyword;
    }
}
