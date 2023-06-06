package raf.project.app.parser;

import org.jetbrains.annotations.NotNull;
import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.*;
import raf.project.app.parser.symbol.SymbolStack;
import raf.project.app.error.GrammarError;

import static raf.project.app.lexer.LexerAPI.TokenTable;
import static raf.project.app.lexer.LexerAPI.TokenTable.*;


public class Parser implements ParserAPI {

    LexerAPI lexer;
    //todo: t1
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
            else if(stack.nextUp().tokenType == ASTERISK) {
                if(stack.nextUp().getValue().equals("*"))
                    select.addChild(stack.swallow().getValue());
                return select;
            }
            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected at least one column name or \"*\" as SELECT clause argument.");

        } else
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

    //todo: t1
    joinClause = stack -> {
        if (stack.nextUp().getTokenType() == JOIN) {
            System.out.println(" Uvation " + stack.nextUp().tokenType );

            stack.swallow();
            JoinClause join = new JoinClause();

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
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected one table name or table name followed by alias as JOIN clause.");

    },


    orderBy = stack -> {
        if(stack.nextUp().tokenType == ORDER){
            stack.swallow();
            OrderByClause order = new OrderByClause();
            if(stack.nextUp().tokenType == BY) {

            }
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected one table name or table name followed by alias as JOIN clause.");
        }
        return null;
    },

    inArgumentList = stack -> {

        if(stack.swallow().tokenType == LEFT_PAR) {

            InArgumentList argList = new InArgumentList();

            if (stack.nextUp().tokenType != INT_CONST && stack.nextUp().tokenType != STR_CONST)
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected constant of type String or Integer, an argument list must contain at leas one element.");


            argList.addChild(stack.swallow());


            while(stack.nextUp().tokenType == COMMA)
            {
                stack.swallow();

                if(stack.nextUp().tokenType == RIGHT_PAR)
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected constant of type " + argList.getListType() + " instead of \")\" after \",\" .");

                if (stack.nextUp().tokenType == argList.getListType())
                        argList.addChild(stack.swallow());
                else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected constant of type " + argList.getListType() +" instead of " + stack.nextUp().tokenType + " after \",\" .");
            }


            if(stack.nextUp().tokenType == RIGHT_PAR) {
                stack.swallow();
                return argList;
            }
            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \")\" at the end of array list.");

        }

        else

            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \"(\" as a beginning of list after IN operation.");

    },
    //todo: t1
    //MUCHO IMPORTANTE!!! DOGOVOR/KONVENCIJA da bi uzljebili algoritam da radi;
    // kljucne reci saljemo u konstruktor clause-a i saljemo TableToken tokenType ondosno tip terminalnog simbola,
    // id-eve parsiramo tako sto ih dodajemo kao dete u clause dodajemo stringovnu vrendonst simbola ondosno .getValue()
    // operatore parsiramo tako sto dodajemo njihov TableToken tokenType odnosno tip terminalnog simbola operatora (<,<=,>,>=,=,!=)
    whereClause = stack -> {


        if (stack.nextUp().tokenType == WHERE) {//zgutaj where prvo

            stack.swallow();
            WhereClause where = new WhereClause();

            if (stack.nextUp().tokenType == ID)
                where.addChild(stack.swallow().getValue());
            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected an id after keyword WHERE.");

            TokenTable operator = stack.nextUp().tokenType;
            if ((operator == LESS_THAN) || (operator == LESS_THAN_OR_EQUAL)
                    || operator == GREATER || operator == GREATER_OR_EQUAL || operator == EQUAL || operator == NOT_EQUAL) {

                where.addChild(stack.swallow().tokenType);// swallow <, >, <=, >= , ==, != as operator

                if (stack.nextUp().tokenType == SELECT) {
                    System.out.println("vratio ga na dupli upit");
                    return where; //vrati se na parsiranje selekta
                }

                else if(stack.nextUp().tokenType == INT_CONST)
                    where.addChild(Integer.parseInt(stack.swallow().getValue()));

                else if (stack.nextUp().tokenType == STR_CONST)
                    where.addChild(stack.swallow().getValue());

                else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected int or string type constant or a sub-query.");

                return where;
            }

            else if(operator == LIKE) {

                where.addChild(stack.swallow().tokenType);// swallow LIKE as operator

                if(stack.nextUp().tokenType == STR_CONST)
                    where.addChild(stack.swallow().getValue());// swallow string constant as LIKE argument

                else
                    throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected string type constant as an argument of LIKE operation.");

            }
            else if (operator == IN) {
                where.addChild(stack.swallow().tokenType);// swallow IN operator
                where.addChild(inArgumentList.parse(stack)); // continue with parsing inArgumentList array.... ad those
            }
            else
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected WHERE clause operator: (<, >, >=, <=, =, !=, IN, LIKE");

            return where;
        }
        throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected keyword WHERE.");

    },


    //todo: t1
    myQuery = stack -> {

        //OBAVEZNI DEO SELECT FROM
        MyQuery myQuery = (MyQuery) new MyQuery().addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));

        //OPCIONI DEO JOIN
        if (stack.nextUp().tokenType == JOIN) {
            myQuery.addChild(joinClause.parse(stack));
        }
        //OPCIONI DEO WHERE PRVI
        if(stack.nextUp().tokenType == WHERE) {
            ASTNode firstWhere = whereClause.parse(stack);

            if(stack.nextUp().tokenType == SELECT) {
                System.out.println("u my kveriju poceo da parsira sledeci wheree");
                firstWhere.addChild(selectClause.parse(stack)).addChild(fromClause.parse(stack));//progutano sve do whera

                if(stack.nextUp().tokenType == WHERE)
                    firstWhere.addChild(whereClause.parse(stack));//shere ce da zguta sve do eventualnog logickog vezznika

                if(stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {//veznik za 2
                    stack.swallow(); //zgutaj and ili or veznik
                    firstWhere.addChild(whereClause.parse(stack));
                }
            }

            if(stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {
                stack.swallow(); //zgutaj and ili or veznik
                firstWhere.addChild(whereClause.parse(stack));
            }
            myQuery.addChild(firstWhere);


            if(stack.nextUp().tokenType == WHERE)
                throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected a logic operator ( AND , OR ) if you want to chain where clauses.");

            //OPCIONI DEO WHERE DRUGI
            if(stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {
                myQuery.addChild(stack.swallow().tokenType);
                ASTNode secondWhere = whereClause.parse(stack);

                if(stack.nextUp().tokenType == SELECT) {

                    secondWhere.addChild(selectClause).addChild(fromClause);//zgutalo je sve do wherea opcionog

                    if(stack.nextUp().tokenType == WHERE)//
                        secondWhere.addChild(whereClause.parse(stack));

                    if(stack.nextUp().tokenType == AND || stack.nextUp().tokenType == OR) {//second sub-query 2 chained where statements
                        myQuery.addChild(stack.swallow().tokenType); //zgutaj and ili or
                        secondWhere.addChild(whereClause.parse(stack));
                    }

                }
                myQuery.addChild(secondWhere);
            }
        }

    //tacka zarez fali
        if (stack.swallow().tokenType != SEMI_COLUMN)
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Expected \";\".");
    //nesto posle tacke zareza
        if(!stack.isEmpty())
            throw new GrammarError("Unexpected argument: " + stack.nextUp().getValue() + ". Nothing expected after query ending token. \";\".");


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
    public ASTNode parse(@NotNull SymbolStack stack) throws GrammarError {
        try {
           return myQuery.parse(stack);

        } catch (GrammarError error) {
            error.printStackTrace();
        }
      return null;
    }

    //reduction lambda function returns newly parsed abstract syntax tree node
    interface Reduction {
        ASTNode parse(SymbolStack stack) throws GrammarError;
    }




}