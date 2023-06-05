package raf.project.app.query_mapper;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import raf.project.app.parser.ast.clauses.MyQuery;

import java.util.List;

public interface MapperAPI {

    interface MyMongoQuery {
        List<List<String>> executeMongoQuery(MongoClient mongoClient) throws MongoException;
    }

    MyMongoQuery mapQueryToMongo(MyQuery myQuery);
}
