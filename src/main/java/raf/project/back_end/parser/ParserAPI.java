package raf.project.back_end.parser;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.SymbolStack;

public interface ParserAPI {
    public ASTNode parse(SymbolStack stack) throws Exception;

    public static ParserAPI getInstance(){
        return Parser.getInstance();
    };
}
