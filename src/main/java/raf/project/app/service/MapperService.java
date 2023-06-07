package raf.project.app.service;

import raf.project.app.parser.ast.query.MyQuery;
import raf.project.app.mapper.Mapper;
import raf.project.app.mapper.MapperAPI.MyMongoQuery;
import raf.project.app.mapper.MapperAPI;


public enum MapperService {

    MY_INSTANCE;



    MyMongoQuery mapQueryToMongo(MyQuery myQuery){

        return  new Mapper().mapQueryToMongo(myQuery);
    }



}
