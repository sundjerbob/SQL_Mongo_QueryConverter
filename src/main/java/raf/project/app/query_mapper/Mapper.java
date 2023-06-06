package raf.project.app.query_mapper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import raf.project.app.parser.ast.clauses.FromClause;
import raf.project.app.parser.ast.clauses.MyQuery;
import raf.project.app.parser.ast.clauses.SelectClause;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mapper implements MapperAPI {

    public static String MY_DB = "bp_tim86";

    @Override
    public MyMongoQuery mapQueryToMongo(MyQuery myQuery) {
        String fromTableName = null;

        if (myQuery.getChildren().size() == 2) {
            boolean selectCheck = false, fromCheck = false;
            for (Object child : myQuery.getChildren()) {
                if (child instanceof SelectClause) {
                    if (((SelectClause) child).getChildren().size() == 1 && ((SelectClause) child).getChildren().get(0).equals("*"))
                        selectCheck = true;
                }
                if (child instanceof FromClause) {
                    if (((FromClause) child).getChildren().size() == 1 || ((FromClause) child).getChildren().size() == 2) {
                        fromTableName = (((FromClause) child).getChildren().get(0) instanceof String) ? (String) ((FromClause) child).getChildren().get(0) : "";
                        fromCheck = true;
                    }
                }
            }
        }
        return selectAllFromTable(fromTableName);
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

        List<List<String>> resultSet = new ArrayList<>();
        Set<String> keys = null;


        if (resultSetCursor.hasNext()) {
            Document document = resultSetCursor.next();
            List<String> columnNames = new ArrayList<>();

            for(String key : document.keySet()) {
                if(key.equals("_id"))
                    continue;
                columnNames.add(key);
            }

            resultSet.add(columnNames);// first row of the table for column names

            while (resultSetCursor.hasNext()) {
                List<String> row = new ArrayList<>();


                System.out.println(document.toJson());

                for (String key : columnNames) {
                    if (document.get(key) instanceof Document) {
                        Document doc = (Document) document.get(key);
                        System.out.println("OVO JE DOKUMENT U DOKUMENTU");
                        row.add(doc.toJson());
                    } else if (document.get(key) instanceof String) {
                        System.out.println(document.getString(key));
                        row.add(document.getString(key));
                    } else if (document.get(key) instanceof Integer) {
                        System.out.println(document.get(key));
                        row.add(((Integer) document.get(key)).toString());
                    }
                }

                resultSet.add(row);// adding each row of a result set collection
                document = resultSetCursor.next();
            }
        }
        resultSetCursor.close();
        return resultSet;
    }

}


