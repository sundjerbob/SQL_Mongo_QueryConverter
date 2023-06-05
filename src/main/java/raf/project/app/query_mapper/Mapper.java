package raf.project.app.query_mapper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.MyQuery;

import java.util.List;

public class Mapper implements MapperAPI {

    public static String MY_DB = "bp_tim86";

    @Override
    public MyMongoQuery mapQueryToMongo(MyQuery myQuery) {
        return null;
    }


    private MyMongoQuery selectAllFromTable(String collectionName) {
        return mongoClient -> {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MY_DB);
            MongoCollection<Document> documentMongoCollection = mongoDatabase.getCollection(collectionName);
            return extractResultSet(documentMongoCollection.find().cursor());
        };
    }

    /**
     * This method extracts data from the result set <code>MongoCursor<Document></></code> into a List of result rows
     * and parse its values into string representative of respective data.
     *
     * @param resultSetCursor is result of desired <code>MongoCollection</code> method execution.
     * @return an <code>List<List<String></></List>></></code> object where each list object represents one row of
     * a result set and the list at the first index in the "parent"
     * list is a list of result sets collection fields names since the every document in the result collection has the same
     * attributes this structure is a table representation of a mongo collection of json documents.
     */
    private List<List<String>> extractResultSet(MongoCursor<Document> resultSetCursor) {
        return null;
    }

}


