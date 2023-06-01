package raf.project.back_end.lexer;

import org.jetbrains.annotations.NotNull;
import raf.project.back_end.parser.symbol.Symbol;
import raf.project.error.GenericError;

import java.util.ArrayList;
import java.util.List;

import static raf.project.back_end.lexer.LexerAPI.TokenTable.*;

public class Lexer implements LexerAPI {

    @Override
    public List<Symbol> tokenize(@NotNull String input) {

        ArrayList<Symbol> tokenizedInput = new ArrayList<>();
        StringBuilder lexemeBuilder = new StringBuilder();
        TokenTable token = null;

        //Iterating through input strings chars
        for (int curr =  0;  curr < input.length() ; curr++) {

            //asking if currently iterated char value is white space
            if(input.charAt(curr) == ' ' || input.charAt(curr) == '\n' || input.charAt(curr) == '\r') {

                /* if whe came to white space we check if that signifies the ending of the word before or there was nothing to be red */
                if(lexemeBuilder.length() != 0) {

                    /* since we have the word to read before whitespace we ask if it can be matched with any syntax regex from token table */
                    if((token = matchToken(lexemeBuilder.toString())) != null) {

                        // we parsed-tokenized a word into a token-symbol and matched it with its type, now we can add a new symbol for the parser input stream
                        tokenizedInput.add( new Symbol(token,lexemeBuilder.toString()) );

                        // since we tokenized-swallowed a word we need to restart the buffer in lexemeBuilder, so we can parse upcoming word
                        lexemeBuilder.delete(0, lexemeBuilder.length());
                    }

                    //if we read a word, and it does not match any of the syntax table tokens than we throw an error
                    else
                        GenericError.getInstance().addError("Unrecognized token: " + lexemeBuilder.toString());
                }

                //we don't build words out of whitespaces if we got in this if branch we skip the char adding to lexemeBuilder part that's underneath
                continue;
            }

            //since the thread came to this line that means the currently iterated char is not whitespace, so we build a word
            lexemeBuilder.append(input.charAt(curr));
        }

        // Check if there is a remaining word at the beginning or end of the input depending on the direction in witch we iterate
        if(lexemeBuilder.length() != 0) {

            if( ( token = matchToken(lexemeBuilder.toString()) ) != null)
                tokenizedInput.add(new Symbol(token, lexemeBuilder.toString()) );
            else
                GenericError.getInstance().addError("Unrecognized word: " + lexemeBuilder);
        }

        return  tokenizedInput;
    }
}
