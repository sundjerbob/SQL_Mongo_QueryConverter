package raf.project.app.service;

import raf.project.app.parser.ast.clauses.MyQuery;
import raf.project.app.query_mapper.Mapper;
import raf.project.app.query_mapper.MapperAPI;

public enum MapperService {

    MY_INSTANCE;

    final MapperAPI mySqlToMongoMappingUnit;
    MapperService() {
        mySqlToMongoMappingUnit = new Mapper();

    }

    MapperAPI.MyMongoQuery mapQueryToMongo(MyQuery myQuery){

        return mySqlToMongoMappingUnit.mapQueryToMongo(myQuery);
    }



}
