package raf.project.app.service;

import raf.project.app.lexer.Lexer;
import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.symbol.SymbolStack;
import raf.project.error.SyntaxError;

public enum LexerService {

    MY_INSTANCE();



    final LexerAPI myLexicalAnalysisUnit;

    LexerService() {
        myLexicalAnalysisUnit = new Lexer();
    }

    SymbolStack performLexicalAnalysis(String inputStream) throws SyntaxError {
        return myLexicalAnalysisUnit.tokenize(inputStream);
    };


}