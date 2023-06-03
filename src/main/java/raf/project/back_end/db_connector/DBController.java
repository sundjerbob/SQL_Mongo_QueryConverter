package raf.project.back_end.db_connector;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public class DBController implements ConnectionAPI{


    private static String user = "writer"; // the username
    private static String database = "bp_tim86"; // the name of the database in which the user is defined
    private static String password = "hzclBqAznRVxJsQ0"; // the password as a character array
    @Override
    public  MongoClient getConnection(){
        MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress("134.209.239.154", 27017), Arrays.asList(credential));

        System.out.println ("Mongo Database connection established");

        return mongoClient;

    }
}
