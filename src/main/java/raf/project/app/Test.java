package raf.project.app;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import raf.project.back_end.mongo_db_connection.DBController;

import java.util.Arrays;

public class Test {
    public static void runTest() {

        MongoClient connection = DBController.getConnection();
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
            Document a = (Document) d.get("employeesInTheSameDepartment");
            System.out.println(a.getString("first_name"));

        }

        String projection = "{department_name:1, location_id:1}";
        String sort = "{department_id:-1}";
        MongoCursor<Document> cursor1 = database.getCollection("departments").find(Document.parse("{department_id:{$lt:90}}")).projection(Document.parse(projection))
                .sort(Document.parse(sort)).iterator();

        while (cursor1.hasNext()) {
            Document d = cursor1.next();
            System.out.println(d.toJson());
            System.out.println(d.getString("department_name"));
        }

        cursor.close();
        cursor1.close();
        connection.close();

    }
}
