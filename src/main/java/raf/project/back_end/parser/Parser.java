package raf.project.back_end.parser;

import org.jetbrains.annotations.NotNull;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.parser.ast.ASTNode;
import raf.project.back_end.parser.ast.nodes.*;
import raf.project.back_end.parser.symbol.Symbol;
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
            SelectClause select = new SelectClause(stack.swallow().getTokenType());

            if (stack.nextUp().tokenType == ID) {

                while (stack.nextUp().tokenType == ID) {
                    System.out.println("uvatio select argument " + stack.nextUp().getValue());

                    select.addChild(stack.swallow().getValue());

                    if (stack.nextUp().tokenType == COMMA) {
                        stack.swallow();

                        if (stack.nextUp().tokenType != ID)
                            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected another column name after \",\" as SELECT clause argument.");

                        continue;
                    }
                    break;
                }

                return select;
            } else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected at least one column name or \"*\" as SELECT clause argument.");

        } else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword SELECT.");
    },

    //todo: t1
    fromClause = stack -> {

        if (stack.nextUp().getTokenType() == FROM) {
            System.out.println("uvatio from");
            FromClause from = new FromClause(stack.swallow().tokenType);

            if (stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                from.addChild(stack.swallow().getValue());
            } else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected column name or alias as FROM clause argument.");

            if (stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                from.addChild(stack.swallow().getValue());
            }
            return from;
        } else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword FROM.");


    },

    //todo: t1
    joinClause = stack -> {
        if (stack.nextUp().getTokenType() == JOIN) {
            System.out.println(" Uvation " + stack.nextUp().tokenType );
            JoinClause join = new JoinClause(stack.swallow().tokenType);

            if (stack.nextUp().tokenType == ID) {
                System.out.println(" Uvation  ID " + stack.nextUp().getValue() );

                join.addChild(stack.swallow().getValue());
            }
            //balias
            if (stack.nextUp().tokenType == ID) {
                System.out.println(" Uvation  BALIAS " + stack.nextUp().getValue());
                join.addChild(stack.swallow().getValue());

            }
            return join;
        }
        else
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected one table name or table name followed by alias as JOIN clause.");

    },


    inArgumentList = stack -> {

        if(stack.swallow().tokenType == LEFT_PAR) {

            InArgumentList argList = new InArgumentList();

            if (stack.nextUp().tokenType != INT_CONST && stack.nextUp().tokenType != STR_CONST)
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected constant of type String or Integer, an argument list must contain at leas one element.");


            argList.addChild(stack.swallow());


            while(stack.nextUp().tokenType == COMMA)
            {
                stack.swallow();

                if(stack.nextUp().tokenType == RIGHT_PAR)
                    throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected constant of type " + argList.getListType() + " instead of \")\" after \",\" .");

                if (stack.nextUp().tokenType == argList.getListType())
                        argList.addChild(stack.swallow());
                else
                    throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected constant of type " + argList.getListType() +" instead of " + stack.nextUp().tokenType + " after \",\" .");
            }


            if(stack.nextUp().tokenType == RIGHT_PAR){
                stack.swallow();
                return argList;
            }
            else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \")\" at the end of array list.");

        }

        else

            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \"(\" as a beginning of list after IN operation.");

    },
    //todo: t1
    //MUCHO IMPORTANTE!!! DOGOVOR/KONVENCIJA da bi uzljebili algoritam da radi;
    // kljucne reci saljemo u konstruktor clause-a i saljemo TableToken tokenType ondosno tip terminalnog simbola,
    // id-eve parsiramo tako sto ih dodajemo kao dete u clause dodajemo stringovnu vrendonst simbola ondosno .getValue()
    // operatore parsiramo tako sto dodajemo njihov TableToken tokenType odnosno tip terminalnog simbola operatora (<,<=,>,>=,=,!=)
    whereClause = stack -> {

        if (stack.nextUp().tokenType == WHERE) {
            WhereClause where = new WhereClause(stack.swallow().tokenType);

            if (stack.nextUp().tokenType == ID)
                where.addChild(stack.swallow().getValue());
            else
                throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected an id after keyword WHERE.");

            TokenTable operator = stack.nextUp().tokenType;
            if (operator == LESS_THAN || operator == LESS_THAN_OR_EQUAL
                    || operator == GREATER || operator == GREATER_OR_EQUAL || operator == EQUAL || operator == NOT_EQUAL) {


                if (stack.nextUp().tokenType == SELECT) {
                    //podupit u where vracamo ne zavrseni where clause ***
                    where.addChild(selectClause.parse(stack)).addChild(operator);
                    return where;
                }
                else if(stack.nextUp().tokenType == INT_CONST) {
                   where.addChild(Integer.parseInt(stack.swallow().getValue()));
                }
                else if (stack.nextUp().tokenType == STR_CONST) {
                    where.addChild(stack.swallow().getValue());
                }
                else
                    throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected int or string type constant or a sub-query");
            }

            else if(operator == LIKE) {
                if(stack.nextUp().tokenType == STR_CONST)
                {
                    where.addChild(operator);
                    where.addChild(stack.swallow().getValue());
                }
                else
                    throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected string type constant as an argument of LIKE operation.");

            } else if (operator == IN) {

            }

        }
        return null;

    },


    //todo: t1
    myQuery = stack -> {

        MyQuery myQuery = (MyQuery) new MyQuery().addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

        if (stack.nextUp().tokenType == JOIN) {
            myQuery.addChild(joinClause.parse(stack));
        }
        if(stack.nextUp().tokenType == WHERE){
            ASTNode where = whereClause.parse(stack);
            //if(stack.nextUp() == )
        }

    //tacka zarez fali
        if (stack.swallow().tokenType != SEMI_COLUMN)
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \";\".");
    //nesto posle tacke zareza
        if(!stack.isEmpty())
            throw new SyntaxError("Unexpected argument: " + stack.nextUp().getValue() + ". Nothing expected after query ending token. \";\".");


        return myQuery;

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
    //todo: UGLOBLJENO ZA TES 1
    @Override
    public ASTNode parse(@NotNull SymbolStack stack) {
        try {
           return inArgumentList.parse(stack);
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