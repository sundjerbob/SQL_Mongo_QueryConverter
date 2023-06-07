package raf.project.app.mapper;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import raf.project.app.mapper.util.PipelineBuilder;
import raf.project.app.parser.ast.query.MyQuery;

import java.util.List;

import static raf.project.app.mapper.packager.ResultExtractor.extractResultSet;

public class Mapper implements MapperAPI {

    public static final String MY_DB_NAME = "bp_tim86";

    public Mapper() {

    }

    @Override
    public MyMongoQuery mapQueryToMongo(MyQuery myQuery) {

        List<Document> queryPipeLine = new PipelineBuilder().buildPipeLine(myQuery);
        System.out.println(queryPipeLine);
        return mongoClient -> extractResultSet(getMongoCollection(myQuery.getFromTable(), mongoClient).aggregate(queryPipeLine).iterator());


    }


    private MongoCollection<Document> getMongoCollection(String tableName, MongoClient mongoClient) {
        return mongoClient.getDatabase(MY_DB_NAME).getCollection(tableName);
    }


}


