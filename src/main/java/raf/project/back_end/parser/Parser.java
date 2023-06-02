package raf.project.back_end.parser;

import org.jetbrains.annotations.NotNull;
import raf.project.back_end.lexer.LexerAPI;
import raf.project.back_end.lexer.LexerAPI.TokenTable;
import raf.project.back_end.mapper.ast.ASTNode;
import raf.project.back_end.mapper.ast_object.nodes.*;
import raf.project.back_end.parser.symbol.Symbol;
import raf.project.back_end.parser.symbol.SymbolStack;
import raf.project.error.SyntaxError;

import java.util.ArrayList;
import java.util.List;

import static raf.project.back_end.lexer.LexerAPI.TokenTable.*;


public class Parser implements ParserAPI {

    /**
     * @apiNote <code>grammarRules</code> mapping of defined grammar rules.
     * Every rule is represented by a row of the matrix that is an array.
     * So grammarRules is an array of arrays- one array per each rule.
     * Every row-rule array consists of elements where the elements at the last
     * index of that array-<code>grammarRules[ruleIndex][grammarRules[ruleIndex].length - 1]</code> represents the
     * rule reduction result and elements at indexes before the last represents an array of symbols that are mirroring
     * to the non-terminal symbol reduction result; <code>ASTNode</code> object as a result of successful parsing of respective rule.
     */
    private Object[][] grammarRules;

    private Reduction  selectClause, fromClause, nameList;

    private static ParserAPI instance;
    LexerAPI lexer;
    private Parser() {




        selectClause = (parseInto, stack) -> {
            ASTNode child = null;
            if(stack.nextUp().getTokenType() == SELECT) {
                Symbol keyword = stack.swallow() ;
                return new Clause(keyword).addChild(nameList.parse(NameList.class, stack ));
            }
            throw new SyntaxError("pukla selectClause redukcija");
        };


        nameList = (parseInto, stack) -> {
            Symbol keyword;
            if(stack.nextUp().getTokenType() == FROM)
                keyword = stack.swallow();
            else
                throw new SyntaxError("From keyword missing from statement.");

            if(stack.nextUp().getTokenType() == TABLE_ID)
                return new Clause(keyword).addChild(nameList.parse(NameList.class, stack ));
            else
                throw new SyntaxError("Table name missing after keyword from in statement.");



        };





        grammarRules = new Object[][] {
                /*ruleIndex*/

                /*0*/       {},// Query

                /*1*/       {null, SEMI_COLUMN, Statement.class, null},// Query ::= Statement SEMI_COLUMN

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
    }

    public static ParserAPI getInstance() {
        if (instance == null)
            instance = new Parser();
        return instance;
    }



    @Override
    public ASTNode parse(@NotNull SymbolStack stack) {
       /* reductionMap
        //result of parsing - abstract syntax tree, constantly trying to map new objects from input
        List<Object> syntaxTree = new ArrayList<>();

        //get the first one
        Symbol curr = stack.swallow();
        //start
        while (!stack.isEmpty() && curr != null) {
            for (int ruleIndex = 0; ruleIndex < grammarRules.length; ruleIndex++) {
                for (int symbolIndex = 0; symbolIndex < grammarRules[ruleIndex].length; symbolIndex++) {
                    if (grammarRules[ruleIndex][symbolIndex] instanceof TokenTable) {

                        if (grammarRules[ruleIndex][syntaxTree.get(ruleIndex).size()] == curr.getTokenType()) {
                            syntaxTree.get(ruleIndex).add(curr);//added terminal symbol in the map changed the state
                            curr = stack.swallow();
                        }

                    } else {

                    }

                }


            }
            return (ASTNode) syntaxTree;

        }

*/
        Reduction red = new Reduction() {
            @Override
            public ASTNode parse(Class<?> parseInto, SymbolStack stack) {
                return null;
            }
        };
        return null;
    }


    //reduction lambda function returns newly parsed abstract syntax tree node
    interface Reduction {
        ASTNode parse(Class<?> parseInto, SymbolStack stack);
    }


}