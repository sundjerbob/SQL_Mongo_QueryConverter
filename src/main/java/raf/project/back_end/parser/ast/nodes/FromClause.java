package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.lexer.LexerAPI;

public class FromClause extends Clause{
    public FromClause(LexerAPI.TokenTable keyWord) {
      super(keyWord);
    }
}
