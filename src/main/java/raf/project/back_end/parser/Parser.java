package raf.project.back_end.parser;

import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.mapper.ast_object.nodes.*;
import raf.project.back_end.parser.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static raf.project.back_end.lexer.LexerAPI.TokenTable.*;


public class Parser implements ParserAPI {

    LexerAPI lexer;


    /**
     * <code>grammarRules</code> mapping of defined grammar rules.
     * Every rule is represented by a row of the matrix that is an array.
     * So grammarRules is an array of arrays- one array per each rule.
     * Every row-rule array consists of elements where the elements at the last
     * index of that array-<code>grammarRules[ruleIndex][grammarRules[ruleIndex].length - 1]</code> represents the
     * rule reduction result and elements at indexes before the last represents an array of symbols that are mirroring
     * to the non-terminal symbol reduction result; <code>ASTNode</code> object as a result of successful parsing of respective rule.
     */
    Object[][] grammarRules = new Object[][]{

            /*ruleIndex*/

            /*0*/       {MyQuery.class, new MyQuery()},// Query

            /*1*/       {Statement.class, SEMI_COLUMN, new MyQuery()},// Query ::= Statement SEMI_COLUMN

            /*2*/       {Statement.class, Statement.class, SEMI_COLUMN, new MyQuery()},// Query ::= Statement Statement SEMI_COLUMN

            /*3*/       {SELECT, ArrayList.class, FROM, ArrayList.class, new Statement()},// Statement ::= SELECT NameList FROM NameList

            /*4*/       {SELECT, ArrayList.class, FROM, ArrayList.class, Clause.class, new Statement()},//Statement ::= SELECT NameList FROM NameList Clause

            /*5*/       {Variable.class, new NameList()},// NamesList ::= Variable

            /*6*/       {NameList.class, COMMA, Variable.class, new NameList()},// NameList ::= NameList COMMA Variable

            /*7*/       {WHERE, Condition.class, new Clause()},// Clause ::= WHERE Condition

            /*8*/       {Expression.class, new Condition()},// Condition ::= Expression

            /*9*/       {Condition.class, LogicOperation.class, Condition.class, new Condition()},// Condition ::= Expression LogicOperation Expression

            /*10*/      {Variable.class, new Expression()},// Expression :: = Variable

            /*11*/      {Constant.class, new Expression()},// Expression :: = Constant

            /*12*/      {Expression.class, MathOperation.class, Expression.class, new Expression()},// Expression ::= Expression MathOperation Expression

            /*13*/      {TABLE_ID, new Variable()},// Variable ::= TABLE_ID

            /*14*/      {COLUMN_ID, new Variable()},// Variable ::= COLUMN_ID

            /*15*/      {STR_CONST, new Constant()},// Constant ::= STR_CONST

            /*16*/      {INT_CONST, new Constant()},// Constant ::= INT_CONST

            /*17*/      {EQUAL, new MathOperation()},// MathOperation ::= EQUAL

            /*18*/      {NOT_EQUAL, new MathOperation()},// MathOperation ::= NOT_EQUAL

            /*19*/      {GREATER, new MathOperation()},// MathOperation ::= GREATER

            /*20*/      {GREATER_OR_EQUAL, new MathOperation()},// MathOperation ::= GREATER_OR_EQUAL

            /*21*/      {LESS_THAN, new MathOperation()},// MathOperation ::= LESS_THAN

            /*22*/      {LESS_THAN_OR_EQUAL, new MathOperation()},// MathOperation ::= LES_THAN_OR_EQUAL

            /*23*/      {AND, new LogicOperation()},// LogicOperation ::= AND

            /*24*/      {OR, new LogicOperation()}// LogicOperation ::= OR

    };

    //reduction lambda function returns newly parsed abstract syntax tree node
    interface Reduction {
        ASTNode parseNonTerminalSymbol(int ruleIndex, Object... symbols);
    }
    Reduction[] reductions = {

            /*0*/   (ruleIndex, symbols) -> (symbols[0] instanceof MyQuery) ? (MyQuery) symbols[0] : null,

            /*1*/   (ruleIndex, symbols) -> new MyQuery().addChild((Statement) symbols[0]),

            /*2*/   (ruleIndex, symbols) -> new MyQuery().addChild((Statement) symbols[0]).addChild((Statement) symbols[1]),

            /*3*/   (ruleIndex, symbols) -> new Statement().addSelectArgs((List<?>) symbols[1]).addFromArgs((List<?>) symbols[3]),

            /*4*/   (ruleIndex, symbols) -> new Statement().addSelectArgs((List<?>) symbols[1]).addFromArgs((List<?>) symbols[3]).addChild((Clause) symbols[4]),

            /*5*/   (ruleIndex, symbols) -> new NameList().addChild((Variable) symbols[0]),

            /*6*/   (ruleIndex, symbols) -> ((NameList) symbols[0]).addChild((Variable) symbols[2]),

            /*7*/   (ruleIndex, symbols) -> new Clause().addChild((Condition) symbols[1]),

            /*8*/   (ruleIndex, symbols) -> new Condition((Expression) symbols[0]),

            /*9*/   (ruleIndex, symbols) -> new Condition((Condition) symbols[0], (LogicOperation) symbols[1], (Condition) symbols[2]),

            /*10*/  (ruleIndex, symbols) -> new Expression((Variable) symbols[0]),

            /*11*/  (ruleIndex, symbols) -> new Expression((Constant) symbols[0]),

            /*12*/  (ruleIndex, symbols) -> new Expression((Expression) symbols[0], (MathOperation) symbols[1], (Expression) symbols[2]),

            /*13*/  (ruleIndex, symbols) -> new Variable((Symbol) symbols[0]),

            /*14*/  (ruleIndex, symbols) -> new Variable((Symbol) symbols[0]),

            /*15*/  (ruleIndex, symbols) -> new Constant((Symbol) symbols[0]),

            /*16*/  (ruleIndex, symbols) -> new Constant((Symbol) symbols[0]),

            /*17*/  (ruleIndex, symbols) -> new MathOperation((Symbol) symbols[0]),

            /*18*/  (ruleIndex, symbols) -> new MathOperation((Symbol) symbols[0]),

            /*19*/  (ruleIndex, symbols) -> new MathOperation((Symbol) symbols[0]),

            /*20*/  (ruleIndex, symbols) -> new MathOperation((Symbol) symbols[0]),

            /*21*/  (ruleIndex, symbols) -> new MathOperation((Symbol) symbols[0]),

            /*22*/  (ruleIndex, symbols) -> new MathOperation((Symbol) symbols[0]),

            /*23*/  (ruleIndex, symbols) -> new LogicOperation((Symbol) symbols[0]),

            /*24*/  (ruleIndex, symbols) -> new LogicOperation((Symbol) symbols[0]),
    };


    public Parser(LexerAPI lexerUnit) {
        lexer = lexerUnit;
    }


    @Override
    public ASTNode parse(List<Symbol> tokens) throws Exception {

       /* Object[][] mat = new Object[10][];
        Stack<Object> stack = new Stack<>();
        stack.push(tokens.get(0));
        while (!(mat[0][0] instanceof MyQuery)) {
            for (int ruleIndex = 0; ruleIndex < grammarRules.length; ruleIndex++) {
                for (Object curr : stack) {
                    if (grammarRules[ruleIndex][stack.indexOf(curr)])
                }
            }
        }
           /* stack.add(token);

               /* for(Object symbol: stack) {

                    for(int ruleIndex = 0; ruleIndex < grammarRules.length; ruleIndex++) {
                        // terminal symbol and if it's on the same index of grammar rule as the index in input stream
                        if(grammarRules[ruleIndex][stack.indexOf(symbol)] instanceof TokenTable &&
                                grammarRules[ruleIndex][tokens.indexOf(curr)] == curr) {
                            cursor[ruleIndex]++;

                            if(cursor[ruleIndex] == grammarRules[ruleIndex].length - 1)
                                continue;
                        }
                        // non-terminal symbol we have one pending terminal symbol to swallow from stack,
                        // need to find the rule that maps into a current rules non-terminal symbol class and starts with
                        // pending terminal symbol from input if we cant match it's a grammar error.
                        else if (grammarRules[ruleIndex][tokens.indexOf(curr)] instanceof Class<?>) {


                            for(int i = 0; i < grammarRules.length; i++) {
                                if(grammarRules[i][grammarRules[i].length - 1].getClass() == grammarRules[ruleIndex][stack.indexOf(curr)])

                            }
                            continue;
                        }
                    }
                }
            }*/
        return null;

    }



}