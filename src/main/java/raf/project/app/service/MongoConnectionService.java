package raf.project.app.service;

import com.mongodb.MongoClient;
import raf.project.app.db_connector.ConnectionAPI;
import raf.project.app.db_connector.DBController;

public enum MongoConnectionService {
    INSTANCE;


    final ConnectionAPI mongoDBConnection;



    MongoConnectionService() {
        mongoDBConnection = new DBController();
    }

    MongoClient provideConnection() {
        return mongoDBConnection.getConnection();
    }

}
