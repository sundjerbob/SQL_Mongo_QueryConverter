package raf.project.app.db_connector;

import com.mongodb.MongoClient;

public interface ConnectionAPI {


    MongoClient getConnection();
}
