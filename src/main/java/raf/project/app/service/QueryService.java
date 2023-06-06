package raf.project.app.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import raf.project.app.parser.ast.clauses.MyQuery;
import raf.project.app.parser.symbol.SymbolStack;
import raf.project.app.query_mapper.MapperAPI.MyMongoQuery;
import raf.project.app.error.GrammarError;
import raf.project.app.error.SyntaxError;

import java.util.List;

/**
 *
 * @apiNote the wrapper service binding all provided functionalities for query mySQL to mongo translation,
 *  Mongo db connection, mongo db query execution, and finally result set extraction from provided result-set driver classes.
 *
 * @author Mina
 */
public enum QueryService {

    MY_INSTANCE;



    public List<List<String>> runQuery(String inputStream) throws SyntaxError, GrammarError, MongoException {

        // making stack of tokens to parse abstract tree nodes from it
        SymbolStack tokenizedInput = LexerService.MY_INSTANCE.performLexicalAnalysis(inputStream);

        // abstract syntax tree root as instance of MyQuery
        MyQuery parsedSqlQuery = (MyQuery) ParserService.MY_INSTANCE.performAbstractSyntaxTreeParsing(tokenizedInput);

        // translate parsed mySqlQuery to mongoQuery
        MyMongoQuery mappedFromSqlToMongoQuery = MapperService.MY_INSTANCE.mapQueryToMongo(parsedSqlQuery);

        // getting mongo db connection from connection service
        MongoClient mongoClient = MongoConnectionService.INSTANCE.provideConnection();

        // execute translatedQuery lambda to fetch result set
        List<List<String>> resultSet = mappedFromSqlToMongoQuery.executeMongoQuery(mongoClient);

        //closing the connection after every client request
        mongoClient.close();

        return resultSet;
    };


}
