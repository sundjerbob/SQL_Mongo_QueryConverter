package raf.project.back_end.db_connector;

import com.mongodb.MongoClient;

public interface ConnectionAPI {
    static ConnectionAPI instance = new DBController();

    static ConnectionAPI getInstance(){ return instance;}
    MongoClient getConnection();
}
