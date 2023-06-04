package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.lexer.LexerAPI;

public class WhereClause extends Clause {
    public WhereClause(LexerAPI.TokenTable keyWord) {
        super(keyWord);
    }
}
