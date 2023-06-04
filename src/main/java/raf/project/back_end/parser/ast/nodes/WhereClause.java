package raf.project.back_end.parser.ast.nodes;

import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.Parser;

public class WhereClause extends Clause {
    public WhereClause(LexerAPI.TokenTable keyWord) {
        super(keyWord);
    }

  /*  @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Object curr : children) {
            str.append(curr.toString());
        }
        return str.toString();
    }
*/

}
