package raf.project.app.service;

import raf.project.app.error.GrammarError;
import raf.project.app.parser.Parser;
import raf.project.app.parser.ParserAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.symbol.SymbolStack;

public enum ParserService {

    MY_INSTANCE;

    final ParserAPI myGrammarParsingComponent;

    ParserService() {
        myGrammarParsingComponent = new Parser();
    }

    public ASTNode performAbstractSyntaxTreeParsing(SymbolStack tokenizedInputStream) throws GrammarError {
        return myGrammarParsingComponent.parse(tokenizedInputStream);
    }
}
