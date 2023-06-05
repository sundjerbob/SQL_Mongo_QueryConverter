package raf.project.app.db_connector;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public class DBController implements ConnectionAPI{


    private static String user = "writer";
    private static String database = "bp_tim86";
    private static String password = "hzclBqAznRVxJsQ0";


    /**
     * @apiNote Please close provided MongoClient instances and all active MongoCursor instances that are queried.
     * @return <code>MongoClient instance</code> , database handler.
     *
     */
    @Override
    public  MongoClient getConnection(){
        MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress("134.209.239.154", 27017), Arrays.asList(credential));

        System.out.println ("Mongo Database connection established");

        return mongoClient;

    }
}
