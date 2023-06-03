package raf.project.back_end.parser;

import org.jetbrains.annotations.NotNull;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.ast.ASTNode;
import raf.project.back_end.parser.ast.nodes.Clause;
import raf.project.back_end.parser.ast.nodes.MyQuery;
import raf.project.back_end.parser.symbol.SymbolStack;
import raf.project.error.SyntaxError;

import static raf.project.back_end.lexer.LexerAPI.TokenTable;
import static raf.project.back_end.lexer.LexerAPI.TokenTable.*;


public class Parser implements ParserAPI {

    LexerAPI lexer;
    //todo: t1
    private final Reduction


            selectClause = stack -> {

        if (stack.nextUp().tokenType == SELECT) {
            System.out.println("uvatio select");
            Clause selectClause = new Clause(stack.swallow().getTokenType());

            if (stack.nextUp().tokenType == ID) {

                while (stack.nextUp().tokenType == ID) {
                    System.out.println("uvatio select argument " + stack.nextUp().getValue());

                    selectClause.addChild(stack.swallow().getValue());

                    if (stack.nextUp().tokenType == COMMA) {
                        stack.swallow();

                        if (stack.nextUp().tokenType != ID)
                            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected another column name after \",\" as SELECT clause argument.");

                        continue;
                    }
                    break;
                }

                return selectClause;
            } else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected at least one column name or \"*\" as SELECT clause argument.");

        } else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword SELECT.");
    },

    //todo: t1
    fromClause = stack -> {

        if (stack.nextUp().getTokenType() == FROM) {
            System.out.println("uvatio from");
            Clause fromClause = new Clause(stack.swallow().tokenType);
            if (stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                fromClause.addChild(stack.swallow().getValue());
            } else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected column name or alias as FROM clause argument.");

            if (stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                fromClause.addChild(stack.swallow().getValue());
            }
            return fromClause;
        } else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword FROM.");


    },

    //todo: t1
    joinClause = stack -> {
        if (stack.nextUp().getTokenType() == JOIN) {
            System.out.println(" Uvation " + stack.nextUp().tokenType );
            Clause joinClause = new Clause(stack.swallow().tokenType);

            if (stack.nextUp().tokenType == ID) {
                System.out.println(" Uvation  ID " + stack.nextUp().getValue() );

                joinClause.addChild(stack.swallow().getValue());
            }
            //balias
            if (stack.nextUp().tokenType == ID) {
                System.out.println(" Uvation  BALIAS " + stack.nextUp().getValue());
                joinClause.addChild(stack.swallow().getValue());

            }
            return joinClause;
        }
        else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected one table name or table name followed by alias as JOIN clause.");

    },

    //todo: t1
    myQuery = stack -> {

        MyQuery myQuery = (MyQuery) new MyQuery().addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

        if (stack.nextUp().tokenType == JOIN) {
            myQuery.addChild(joinClause.parse(stack));
        }

        if (stack.swallow().tokenType != SEMI_COLUMN)
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \";\".");

        if(!stack.isEmpty())
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Nothing expected after query ending token. \";\".");

        return myQuery;

    },



    //todo: t1
    //MUCHO IMPORTANTE!!! DOGOVOR/KONVENCIJA da bi uzljebili algoritam da radi;
    // kljucne reci saljemo u konstruktor clause-a i saljemo TableToken tokenType ondosno tip terminalnog simbola,
    // id-eve parsiramo tako sto ih dodajemo kao dete u clause dodajemo stringovnu vrendonst simbola ondosno .getValue()
    // operatore parsiramo tako sto dodajemo njihov TableToken tokenType odnosno tip terminalnog simbola operatora (<,<=,>,>=,=,!=)
    whereClause = stack -> {
        if (stack.nextUp().tokenType == WHERE) {
            Clause whereClause = new Clause(stack.swallow().tokenType);

            if (stack.nextUp().tokenType == ID)
                whereClause.addChild(stack.swallow().getValue());

            else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected an id as a WHERE clause argument.");
            TokenTable sym = null;
            if ((sym = stack.nextUp().tokenType) == LESS_THAN || sym == LESS_THAN_OR_EQUAL
                    || sym == GREATER || sym == GREATER_OR_EQUAL || sym == EQUAL || sym == NOT_EQUAL) {
                if (stack.nextUp().tokenType == SELECT)
                    whereClause.addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

            } else if (sym == IN) {
            }
            //else if(sym == LIKE)


        }
        return null;


    };
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
        try {
           return myQuery.parse(stack);
        } catch (SyntaxError error) {
            error.printStackTrace();
        }
        return null;
    }

    //reduction lambda function returns newly parsed abstract syntax tree node
    interface Reduction {
        ASTNode parse(SymbolStack stack) throws SyntaxError;
    }




}