package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.lexer.LexerAPI.TokenTable;

public class JoinClause extends Clause {
    public JoinClause(TokenTable keyword) {
        super(keyword);
    }
}
