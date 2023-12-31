package raf.project.app.parser;

import org.jetbrains.annotations.NotNull;
import raf.project.app.error.GrammarError;
import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.*;
import raf.project.app.parser.ast.functions.Function;
import raf.project.app.parser.ast.functions.OnFunction;
import raf.project.app.parser.ast.functions.UsingFunction;
import raf.project.app.parser.ast.query.MyQuery;
import raf.project.app.parser.symbol.SymbolStack;

import static raf.project.app.lexer.LexerAPI.TokenTable;
import static raf.project.app.lexer.LexerAPI.TokenTable.*;


public class Parser implements ParserAPI {

    /**
     * @apiNote <code>grammarRules</code> mapping of defined grammar rules.
     * Every rule is represented by a row of the matrix that is an array.
     * So grammarRules is an array of arrays, one array per each rule.
     * Every row-rule array consists of elements where the elements at the last
     * index of that array-<code>grammarRules[ruleIndex][grammarRules[ruleIndex].length - 1]</code> represents the
     * rule reduction result and elements at indexes before the last represents an array of symbols that are mirroring
     * to the non-terminal symbol reduction result; <code>ASTNode</code> object as a result of successful parsing of respective rule.
     */




    @Override
    public ASTNode parse(@NotNull SymbolStack stack) throws GrammarError {

            return myQuery.parse(stack);

    }





    private final Reduction selectClause = stack -> {

        if (stack.nextUp().tokenType == SELECT) {

            System.out.println("uvatio select");
            stack.swallow();
            SelectClause select = new SelectClause();

            if (stack.nextUp().tokenType == ID) {

                while (stack.nextUp().tokenType == ID) {
                    System.out.println("uvatio select argument " + stack.nextUp().getValue());

                    select.addChild(stack.swallow().getValue());

                    if (stack.nextUp().tokenType == COMMA) {
                        stack.swallow();

                        if (stack.nextUp().tokenType != ID)
                            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected another column name after \",\" as SELECT clause argument.");


                        continue;
                    }
                    break;
                }

                return select;
            }
            else if (stack.nextUp().tokenType == ASTERISK) {

                if (stack.nextUp().getValue().equals("*"))

                    select.addChild(stack.swallow().getValue());

                return select;
            }

            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected at least one column name or \"*\" as SELECT clause argument.");

        }
        else
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword SELECT.");
    },

    //todo: t1
    fromClause = stack -> {

        if (stack.nextUp().getTokenType() == FROM) {
            System.out.println("uvatio from");

            stack.swallow();
            FromClause from = new FromClause();

            if (stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                from.addChild(stack.swallow().getValue());
            } else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected column name or alias as FROM clause argument.");

            if (stack.nextUp().getTokenType() == ID) {
                System.out.println("uvatio from argument " + stack.nextUp().getValue());
                from.addChild(stack.swallow().getValue());
            }
            return from;

        } else
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword FROM.");


    },

    onJoinArg = stack -> {
        if(stack.nextUp().tokenType != ON)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword ON.");

        stack.swallow();

        OnFunction onFunction = new OnFunction();

        if(stack.nextUp().tokenType != LEFT_PAR)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Missing \"(\" after the keyword ON.");

        stack.swallow();

        if(stack.nextUp().tokenType != ID)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Missing an ID as the first argument of ON function. ");

        onFunction.addChild(stack.swallow().getValue());

        TokenTable operator = stack.nextUp().tokenType;

        if( operator != LESS_THAN && operator != GREATER && operator != EQUAL)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". ON function must have an operator that can be <, > or = . ");

        onFunction.addChild(stack.swallow().getTokenType());

        if(stack.nextUp().tokenType != ID)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Missing an ID as the second argument of ON function. ");

        onFunction.addChild(stack.swallow().getValue());

        if(stack.nextUp().tokenType != RIGHT_PAR)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \")\" as an enclosing token for the ON function argument.");
        stack.swallow();

        return onFunction;

    },

    usingJoinArg = stack -> {
        if(stack.nextUp().tokenType != USING)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword USING.");

        stack.swallow();

        if(stack.nextUp().tokenType != LEFT_PAR)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \"(\" after key word USING.");

        stack.swallow();

        UsingFunction usingFunction = new UsingFunction();

        if(stack.nextUp().tokenType != ID)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected an ID as USING function argument.");

        usingFunction.addChild(stack.swallow().getValue());//adding an ID

        if(stack.nextUp().tokenType != RIGHT_PAR)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \")\" as an enclosing token for the USING function argument.");

        stack.swallow();

        return usingFunction;
    },

    //todo: t1
    joinClause = stack -> {
        if (stack.nextUp().getTokenType() == JOIN) {
            System.out.println(" Uvation " + stack.nextUp().tokenType);

            stack.swallow();
            JoinClause join = new JoinClause();

            if (stack.nextUp().tokenType != ID)
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Join clause must have an argument ; table name or table name and alias.");

            join.addChild(stack.swallow().getValue());

            if (stack.nextUp().tokenType == ID)
                join.addChild(stack.swallow().getValue());

            if(stack.nextUp().tokenType == ON)
                join.addChild(onJoinArg.parse(stack));

            else if (stack.nextUp().tokenType == USING)
                join.addChild(usingJoinArg.parse(stack));

            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Join clause must have \"ON\" or \"USING\" expression as the second argument.");

            return join;
        }
        else
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected one table name or table name followed by alias as JOIN clause.");

    },

    groupByClause = stack -> {

        if(stack.nextUp().tokenType != GROUP)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword GROUP");

        stack.swallow();

        if(stack.nextUp().tokenType != BY)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword BY after the keyword GROUP so the group by action could be done.");

        stack.swallow();

        GroupByClause groupByClause = new GroupByClause();

        if(stack.nextUp().tokenType != ID)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Group by argument expected, there has to be at leas one ID as argument or a list of IDs.");

        groupByClause.addChild(stack.swallow().getValue());

        while (stack.nextUp().tokenType == COMMA)
        {
            stack.swallow();
            if(stack.nextUp().tokenType != ID)
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Group by arguments list can only be list of ID-s.");

            groupByClause.addChild(stack.swallow().getValue());
        }
        return groupByClause;

    },

    aggregationFunction = stack -> {
        TokenTable nextUp = stack.nextUp().tokenType;

        if (nextUp != AVG && nextUp != SUM && nextUp != COUNT && nextUp != MIN && nextUp != MAX)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Function name is missing.");

        Function function = new Function(stack.swallow().tokenType);

        if (stack.nextUp().tokenType != LEFT_PAR)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". An argument of an aggregation function has to be sounded by parentheses.");

        stack.swallow();

        if (stack.nextUp().tokenType != ID)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". All aggregation functions must have one ID as argument.");

        function.addChild(stack.swallow().getValue());

        if (stack.nextUp().tokenType != RIGHT_PAR)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Missing enclosing \")\" after the aggregation function argument.");

        stack.swallow(); // swallow right function enclosing parentheses

        return function;
    },


    orderByClause = stack -> {
        if (stack.nextUp().tokenType != ORDER)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Missing ORDER keyword.");

        stack.swallow();

        if (stack.nextUp().tokenType != BY)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Missing BY keyword after keyword ORDER so you can do the order by action.");

        stack.swallow();


        OrderByClause orderBy = new OrderByClause();

        TokenTable nextUp = stack.nextUp().tokenType;

        if (nextUp == ID)
            orderBy.addChild(stack.swallow().getValue());

        else if (nextUp == COUNT || nextUp == SUM || nextUp == AVG || nextUp == MAX || nextUp == MIN)
            orderBy.addChild(aggregationFunction.parse(stack));

        else
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Order by clause must have one argument and it can be aggregation function or colum id.");


        if (stack.nextUp().tokenType != ASC && stack.nextUp().tokenType != DESC)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Missing ASC or DESC operator of an ORDER BY statement.");

        orderBy.addChild(stack.swallow().tokenType);

        return orderBy;
    },

    inArgumentList = stack -> {

        if (stack.swallow().tokenType == LEFT_PAR) {

            InArgumentList argList = new InArgumentList();

            if (stack.nextUp().tokenType != INT_CONST && stack.nextUp().tokenType != STR_CONST)
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Expected constant of type String or Integer, an argument list must contain at leas one element.");


            argList.addChild(stack.swallow());


            while (stack.nextUp().tokenType == COMMA) {
                stack.swallow();

                if (stack.nextUp().tokenType == RIGHT_PAR)
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Expected constant of type " + argList.getListType() + " instead of \")\" after \",\" .");

                if (stack.nextUp().tokenType == argList.getListType())
                    argList.addChild(stack.swallow());
                else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + " . Expected constant of type " + argList.getListType() + " instead of " + stack.nextUp().tokenType + " after \",\" .");
            }


            if (stack.nextUp().tokenType == RIGHT_PAR) {
                stack.swallow();
                return argList;
            } else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \")\" at the end of array list.");

        } else

            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \"(\" as a beginning of list after IN operation.");

    },
    //todo: t1
    //MUCHO IMPORTANTE!!! DOGOVOR/KONVENCIJA da bi uzljebili algoritam da radi;
    // kljucne reci saljemo u konstruktor clause-a i saljemo TableToken tokenType ondosno tip terminalnog simbola,
    // id-eve parsiramo tako sto ih dodajemo kao dete u clause dodajemo stringovnu vrendonst simbola ondosno .getValue()
    // operatore parsiramo tako sto dodajemo njihov TableToken tokenType odnosno tip terminalnog simbola operatora (<,<=,>,>=,=,!=)
    whereClause = stack -> {

        //mandatory keyword part
        if (stack.nextUp().tokenType == WHERE) {

            stack.swallow();// swallow the keyword we don't need it will be recorded as an instance of WhereClause with its contents
            WhereClause where = new WhereClause();// instantiate new WhereClause

            if (stack.nextUp().tokenType == ID)
                where.addChild(stack.swallow().getValue());// adding where clause parameter
            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected an id after keyword WHERE.");

            TokenTable operator = stack.nextUp().tokenType;
            if ((operator == LESS_THAN) || (operator == LESS_THAN_OR_EQUAL) // we need to catch mandatory where clause part that's operator
                    || operator == GREATER || operator == GREATER_OR_EQUAL || operator == EQUAL || operator == NOT_EQUAL) {

                where.addChild(stack.swallow().tokenType);// swallow <, >, <=, >= , ==, != as operator

                if (stack.nextUp().tokenType == LEFT_PAR) {
                    System.out.println("Parsing of one where clause is stopped and the sub-query parsing began.");
                    return where; // return the WhereClause instance so the sub-query can be added to it as its child
                }

                // in case we did not encounter a sub-query, we continue parsing where clause that can be parametrized
                // with INT_CONST, STR_CONST as the right operator side expression
                else if (stack.nextUp().tokenType == INT_CONST)
                    where.addChild(Integer.parseInt(stack.swallow().getValue()));

                else if (stack.nextUp().tokenType == STR_CONST)
                    where.addChild(stack.swallow().getValue());

                else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected int or string type constant or a sub-query.");

                return where;
            }
            //
            else if (operator == LIKE) {

                where.addChild(stack.swallow().tokenType);// swallow LIKE as operator

                if (stack.nextUp().tokenType == STR_CONST)
                    where.addChild(stack.swallow().getValue());// swallow string constant as LIKE that can only be STR_CONST

                else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected string type constant as an argument of LIKE operation.");

            } else if (operator == IN) {
                where.addChild(stack.swallow().tokenType);// swallow IN operator
                where.addChild(inArgumentList.parse(stack)); // continue with parsing inArgumentList array... ad those
            } else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected WHERE clause operator: (<, >, >=, <=, =, !=, IN, LIKE");

            return where;
        } else
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword WHERE.");

    },


    //todo: t1
    myQuery = stack -> {

        // Mandatory parts of the query are select clause and from clause
        MyQuery query = (MyQuery) new MyQuery().addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

        // Optional join clause
        if (stack.nextUp().tokenType == JOIN) {
            query.addChild(joinClause.parse(stack));
        }
        // Optional where clause
        if (stack.nextUp().tokenType == WHERE) {
            System.out.println("VIDEO JE WHERE");
            // begin parsing first where clause in a parent query
            ASTNode firstWhere = whereClause.parse(stack);
            //adding to be sure
            query.addChild(firstWhere);
            // In case of sub-query appearance in where,where clause parse is stopped on
            // after the where operator is swallowed.
            // So the next token in that case would be SELECT keyword
            if (stack.nextUp().tokenType == LEFT_PAR) {
                stack.swallow();// swallow parentheses it just signifies starting of a sub query

                // like for "parent" query, the sub-query has to have mandatory select & from clause
                firstWhere.addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

                // optional first where in sub-query
                if (stack.nextUp().tokenType == WHERE) {
                    firstWhere.addChild(whereClause.parse(stack));// parse first

                    if (stack.nextUp().tokenType == LEFT_PAR)
                        throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". There cant be a sub query in another sub queries where clause.");

                    // optional logic operator which connects first where cause with second where clause (in sub-query)
                    if (stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {

                        firstWhere.addChild(stack.swallow().tokenType); // adding logic operator between first and second where clause of a sub-query
                        firstWhere.addChild(whereClause.parse(stack));// parse second in sub-query where

                        if (stack.nextUp().tokenType == LEFT_PAR)
                            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". There cant be a sub query in another sub queries where clause.");
                    }
                }
                if (stack.nextUp().tokenType == RIGHT_PAR) {
                    stack.swallow(); // swallow right parentheses that signify the ending of a sub query
                } else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". There should be \")\" at the end of a sub query.");

                //query.addChild(firstWhere); // adding the whole parent query first where clause
            }

            //end of the first where clause


            // trash can
            if (stack.nextUp().tokenType == WHERE)
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected a logic operator ( AND , OR ) if you want to chain where clauses.");


            //optional logic operator to chain first and second where clause in the parent query
            if (stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {

                query.addChild(stack.swallow().tokenType);// adding the logic operator between first and second where clause in a parent query
                // begin parsing first where clause in a parent query
                ASTNode secondWhere = whereClause.parse(stack);

                // In case of sub-query appearance in where,where clause parse is stopped on
                // after the where operator is swallowed.
                // So the next token in that case would be SELECT keyword
                if (stack.nextUp().tokenType == LEFT_PAR) {
                    stack.swallow();// swallow parentheses it just signifies starting of a sub query

                    // like for "parent" query, the sub-query has to have mandatory select & from clause
                    secondWhere.addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

                    // optional first where in sub-query
                    if (stack.nextUp().tokenType == WHERE) {
                        secondWhere.addChild(whereClause.parse(stack));// parse first

                        if (stack.nextUp().tokenType == LEFT_PAR)
                            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". There cant be a sub query in another sub queries where clause.");

                        // optional logic operator which connects first where cause with second where clause in(sub-query)
                        if (stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {

                            secondWhere.addChild(stack.swallow().tokenType); // adding logic operator between first and second where clause of a sub-query
                            secondWhere.addChild(whereClause.parse(stack));// parse second in sub-query where

                            if (stack.nextUp().tokenType == LEFT_PAR)
                                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". There cant be a sub query in another sub queries where clause.");
                        }
                    }
                    if (stack.nextUp().tokenType == RIGHT_PAR) {
                        stack.swallow(); // swallow right parentheses that signify the ending of a sub query
                    } else
                        throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". There should be \")\" at the end of a sub query.");
                }
                query.addChild(secondWhere); // adding the whole parent query first where clause

            }
        }
        if(stack.nextUp().tokenType == GROUP)
        {
            query.addChild(groupByClause.parse(stack));
        }
        if(stack.nextUp().tokenType == ORDER)
        {
            query.addChild(orderByClause.parse(stack));
        }

        //Semi-column missing from the ond of the query
        if (stack.nextUp().tokenType != SEMI_COLUMN)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \";\".");

        stack.swallow(); // swallow the last char of input that's semi_column

        //Some text is found after the query enclosing semi-column
        if (!stack.isEmpty())
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Nothing expected after query ending token. \";\".");


        return query;

    };

    //reduction lambda function returns newly parsed abstract syntax tree node
    interface Reduction {
        ASTNode parse(SymbolStack stack) throws GrammarError;
    }




}