package raf.project.app.parser;

import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.symbol.SymbolStack;
import raf.project.error.GrammarError;

public interface ParserAPI {
    public ASTNode parse(SymbolStack stack) throws GrammarError;


}
