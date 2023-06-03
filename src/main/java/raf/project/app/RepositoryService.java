package raf.project.app;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import raf.project.back_end.mapper.ast_object.nodes.MyQuery;
import raf.project.back_end.mongo_db_connection.DBController;

import java.util.List;

public class RepositoryService {

    /*public static List<?> executeMQLQuery(MyQuery mq) {



        if (mq.getChildren().size() == 1){
            statement = (Statement) mq.getChildren().get(0);
        }

        assert statement != null;
        String collectionName = (String) statement.getFromArgs().get(0);

        MongoClient connection = new DBController().getConnection();
        MongoDatabase database = connection.getDatabase("bp_tim86");
        MongoCollection<Document> collection = database.getCollection(collectionName);
        MongoCursor<Document> cursor = null;

        if ( ( (String) statement.getSelectArgs().get(0)).equals("*") )
            cursor = collection.find().iterator();

        if (cursor == null)
            throw new RuntimeException("Prso cursor");

        while (cursor.hasNext()){
            Document doc = cursor.next();

        }
        cursor.close();
        connection.close();
        return null;
    }
*/
}
