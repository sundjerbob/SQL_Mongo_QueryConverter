package raf.project.back_end.parser;

import org.jetbrains.annotations.NotNull;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.mapper.ast_object.nodes.Clause;
import raf.project.back_end.mapper.ast_object.nodes.MyQuery;
import raf.project.back_end.parser.symbol.Symbol;
import raf.project.back_end.parser.symbol.SymbolStack;
import raf.project.error.SyntaxError;

import static raf.project.back_end.lexer.LexerAPI.TokenTable.*;


public class Parser implements ParserAPI {

    //todo: t1
    private final Reduction selectClause = (stack) -> {

        if (stack.nextUp().tokenType == SELECT) {
            System.out.println("uvatio select");
            Clause selectClause = new Clause(stack.swallow().getTokenType());

            if(stack.nextUp().tokenType == ID) {

                while(stack.nextUp().tokenType == ID) {
                    System.out.println("uvatio select argument " + stack.nextUp().getValue());

                    selectClause.addChild(stack.swallow().getValue());

                    if(stack.nextUp().tokenType == COMMA) {
                        stack.swallow();

                        if(stack.nextUp().tokenType != ID)
                            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected another column name after \",\" as SELECT clause argument.");

                        continue;
                    }
                    break;
                }

                return selectClause;
            }
            else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected at least one column name or \"*\" as SELECT clause argument.");

        }
        else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword SELECT.");
    },

    //todo: t1
    fromClause = (stack) -> {

        if (stack.nextUp().getTokenType() == FROM) {
            System.out.println("uvatio from");
            Clause fromClause = new Clause(stack.swallow().tokenType);
            if(stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                fromClause.addChild(stack.swallow().getValue());
            }
            else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected column name or alias as FROM clause argument.");

            if(stack.nextUp().getTokenType() == ID)
                fromClause.addChild(stack.swallow().getValue());

            return fromClause;
        }
        else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword FROM.");


    },

    //todo: t1
    myQuery = (stack) -> {

        MyQuery query = (MyQuery) new MyQuery().addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

        if(stack.isEmpty())
            throw new SyntaxError("Missing \";\" at the end of the statement.");

        if(stack.nextUp().tokenType != SEMI_COLUMN)
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \";\".");
        return query;

    };





    LexerAPI lexer;
    /**
     * @apiNote <code>grammarRules</code> mapping of defined grammar rules.
     * Every rule is represented by a row of the matrix that is an array.
     * So grammarRules is an array of arrays, one array per each rule.
     * Every row-rule array consists of elements where the elements at the last
     * index of that array-<code>grammarRules[ruleIndex][grammarRules[ruleIndex].length - 1]</code> represents the
     * rule reduction result and elements at indexes before the last represents an array of symbols that are mirroring
     * to the non-terminal symbol reduction result; <code>ASTNode</code> object as a result of successful parsing of respective rule.
     */
    private Object[][] grammarRules;

    public Parser() {

    }


    @Override
    public ASTNode parse(@NotNull SymbolStack stack) {
        return myQuery.parse(stack);
    }

    //reduction lambda function returns newly parsed abstract syntax tree node
    interface Reduction {
        ASTNode parse(SymbolStack stack);
    }


}