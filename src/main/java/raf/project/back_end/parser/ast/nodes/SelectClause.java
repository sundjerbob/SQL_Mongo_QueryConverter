package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.lexer.LexerAPI;

public class SelectClause extends Clause{
    public SelectClause(LexerAPI.TokenTable keyword) {
        super(keyword);
    }
}
