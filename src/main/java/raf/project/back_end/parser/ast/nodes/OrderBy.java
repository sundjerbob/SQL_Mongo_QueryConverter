package raf.project.back_end.parser.ast.nodes;
import raf.project.back_end.lexer.LexerAPI.TokenTable;
public class OrderBy extends Clause{

    OrderBy(TokenTable keyword) {
        super(keyword);
    }
}
