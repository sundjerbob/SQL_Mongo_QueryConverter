package raf.project.app.query_mapper.packager;

import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author Mina
 * @apiNote this class is used for extracting data from a mongo db result set,
 * packaging its data in its respective string form using java util List,
 * since we lose the mongo db query result wrapper instances when the
 * MongoClient connection is closed.
 */
public class ResultExtractor {

    /**
     * This method extracts data from the result set <code>MongoCursor<Document></></code> into a List of result rows
     * and parse its values into string representative of respective data.
     *
     * @param resultSetCursor is result of desired <code>MongoCollection</code> method execution.
     * @return an <code>List<List<String></></List>></></code> object where each list object represents one row of
     * a result set and the list at the first index in the "parent"
     * list is a list of result sets collection fields names since the every document in the result collection has the same
     * attributes this structure is a table representation of a mongo collection of json documents.
     * @author Tadija
     */
    private List<List<String>> extractResultSet(MongoCursor<Document> resultSetCursor) {

        List<List<String>> resultSet = new ArrayList<>();
        Set<String> keys = null;


        if (resultSetCursor.hasNext()) {
            Document document = resultSetCursor.next();
            List<String> columnNames = new ArrayList<>();

            for (String key : document.keySet()) {
                if (key.equals("_id"))
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
