package raf.project.app;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import raf.project.back_end.mongo_db_connection.ConnectionAPI;
import raf.project.back_end.mongo_db_connection.DBController;

import javax.print.Doc;
import java.util.Arrays;

public class Test {
    public static void runTest() {

        MongoClient connection = ConnectionAPI.getInstance().getConnection();
        MongoDatabase database = connection.getDatabase("bp_tim86");

        MongoCursor<Document> cursor = database.getCollection("employees").aggregate(
                Arrays.asList(
                        Document.parse("{\n" +
                                "  $match: {first_name: \"Steven\", last_name: \"King\"}\n" +
                                "}"),
                        Document.parse("{\n" +
                                "  $lookup: {\n" +
                                "    from: \"employees\",\n" +
                                "    localField: \"department_id\",\n" +
                                "    foreignField: \"department_id\",\n" +
                                "    as: \"employeesInTheSameDepartment\"\n" +
                                "  }\n" +
                                "}"),
                        Document.parse("{ $unwind: \"$employeesInTheSameDepartment\" }"),
                        Document.parse("{ $project: {\n" +
                                "    \"employeesInTheSameDepartment.first_name\": 1,\n" +
                                "    \"employeesInTheSameDepartment.last_name\": 1\n" +
                                "  }\n" +
                                "}")
                )
        ).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            System.out.println(d.toJson());
            for(String k : d.keySet()) {

                if(d.get(k)  instanceof Document) {
                    Document doc = (Document) d.get(k);
                    if(doc.containsKey("first_name"))
                        System.out.println(doc.getString("first_name"));
                }
                else if(d.get(k) instanceof String)
                    System.out.println(d.getString(k));
            }

        }


        String projection = "{department_name:1, location_id:1}";
        String sort = "{department_id:-1}";

        MongoCursor<Document> cursor1 = database.getCollection("departments").find(Document.parse("{department_id:{$lt:90}}")).projection(Document.parse(projection))
                .sort(Document.parse(sort)).iterator();
        while (cursor1.hasNext()) {
            Document doc = cursor1.next();

            System.out.println(doc.toJson());
            System.out.println(doc.getInteger("location_id"));
        }


        cursor.close();
        cursor1.close();
        connection.close();

    }
}
