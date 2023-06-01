package raf.project.back_end.parser;

import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.parser.symbol.Symbol;

import java.util.List;

public interface ParserAPI {
    public ASTNode parse(List<Symbol> tokens) throws Exception;

}
