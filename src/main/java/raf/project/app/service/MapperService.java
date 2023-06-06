package raf.project.app.service;

import raf.project.app.parser.ast.query.MyQuery;
import raf.project.app.query_mapper.Mapper;
import raf.project.app.query_mapper.MapperAPI.MyMongoQuery;
import raf.project.app.query_mapper.MapperAPI;


public enum MapperService {

    MY_INSTANCE;

    final MapperAPI mySqlToMongoMappingUnit;
    MapperService() {
        mySqlToMongoMappingUnit = new Mapper();

    }

    MyMongoQuery mapQueryToMongo(MyQuery myQuery){

        return mySqlToMongoMappingUnit.mapQueryToMongo(myQuery);
    }



}
