package raf.project.app.lexer;

import org.jetbrains.annotations.NotNull;
import raf.project.app.error.SyntaxError;
import raf.project.app.parser.symbol.SymbolStack;
import raf.project.app.parser.symbol.Symbol;

public class Lexer implements LexerAPI {

    @Override
    public SymbolStack tokenize(@NotNull String input) throws SyntaxError {

        SymbolStack tokenizedInput = new SymbolStack();
        StringBuilder lexemeBuilder = new StringBuilder();
        TokenTable token = null;

        //Iterating through input strings chars
        for (int curr =  0;  curr < input.length() ; curr++) {
           // if(input.charAt(curr) == '(' ||  )


            //asking if currently iterated char value is white-space
            if(input.charAt(curr) == ' ' || input.charAt(curr) == '\n' || input.charAt(curr) == '\r') {

                /* if whe came to blank space we check if that signifies the ending of the word before or there was nothing to be red */
                if(lexemeBuilder.length() != 0) {

                    /* since we have the word to read before blank space we ask
                    if it can be matched with any syntax regex from token table */
                    if((token = TokenTable.matchToken(lexemeBuilder.toString())) != null) {

                        // we parsed-tokenized a word into a token-symbol and matched it with its type, now we can add a new symbol for the parser input stream
                        tokenizedInput.pushToBottom( new Symbol(token,lexemeBuilder.toString()) );

                        // since we tokenized-swallowed a word we need to restart the buffer in lexemeBuilder, so we can parse upcoming word
                        lexemeBuilder.delete(0, lexemeBuilder.length());
                    }

                    //if we read a word, and it does not match any of the syntax table tokens than we throw an error
                    else
                        throw new SyntaxError("Lexeme:  " + lexemeBuilder + " couldn't be matched with any syntax token." );
                }

                //we don't build words out of white-space if we got in this branch,
                // we skip the char adding to lexemeBuilder part that's underneath
                continue;
            }

            //since the thread came to this line,that means
            // that the currently iterated char is not blank-space,so we build a word
            lexemeBuilder.append(input.charAt(curr));
        }

        // Check if there is a remaining word at the beginning or end of the input depending on the direction in witch we iterate
        if(lexemeBuilder.length() != 0) {

            if( ( token = TokenTable.matchToken(lexemeBuilder.toString()) ) != null)
                tokenizedInput.pushToBottom(new Symbol(token, lexemeBuilder.toString()) );
            else
                throw new SyntaxError("Lexeme:  " + lexemeBuilder + "couldn't be matched with any syntax token." );
        }

        return  tokenizedInput;
    }
}
