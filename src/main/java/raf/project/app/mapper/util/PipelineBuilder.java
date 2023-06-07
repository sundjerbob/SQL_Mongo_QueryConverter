package raf.project.app.mapper.util;

import org.bson.Document;
import org.bson.conversions.Bson;
import raf.project.app.lexer.LexerAPI;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.JoinClause;
import raf.project.app.parser.ast.clauses.SelectClause;
import raf.project.app.parser.ast.query.MyQuery;

import java.util.ArrayList;
import java.util.List;

public class PipelineBuilder {
    private final List<Bson> stages;

    public PipelineBuilder() {
        stages = new ArrayList<>();
    }

    public PipelineBuilder match(Bson filter) {
        stages.add(filter);
        return this;
    }

    public PipelineBuilder project(Bson projection) {
        stages.add(projection);
        return this;
    }

    public PipelineBuilder unwind(String field) {
        stages.add(new Document("$unwind", field));
        return this;
    }

    // Add other methods for different pipeline stages

    public List<Bson> build() {
        return stages;
    }


    public List<Document> buildPipeLine(MyQuery myQuery) {

        List<Document> pipeLine = new ArrayList<>();

        // array ensures placement of stages
        Document[] documents = new Document[6];

        SelectClause selectClause = myQuery.getSelectClause();

        Document projectDocument = new Document("$project", new Document());

        if(selectClause.getChildren().size() == 1 && selectClause.getChildren().get(0).equals("*")) {
            documents[5] = projectDocument;
        }
        else {
            for (Object selectArg : selectClause.getChildren()) {
                if (selectArg instanceof String) {
                    projectDocument.get("$project", Document.class).append((String) selectArg, 1);
                } else
                    throw new RuntimeException("..0,0'");
            }
            documents[5] = projectDocument;
        }
        if(myQuery.getJoinClause() != null) {

            JoinClause joinClause = myQuery.getJoinClause();
            if(joinClause.getOnFunction() != null) {

                documents[1] = new Document("$lookup",
                        new Document("from", joinClause.getChildren().get(0))
                                .append("localField", joinClause.getOnFunction().getFirstArg() )
                                .append("foreignField", joinClause.getOnFunction().getSecondArg() )
                                .append("as", myQuery.getFromTable() + "_" + myQuery.getJoinClause().getJoinedTable()));;
            }

            documents[5].get("$project", Document.class).append( myQuery.getFromTable() + "_" + myQuery.getJoinClause().getJoinedTable() + "." + selectClause.getChildren().get(0) , 1 );
        }

        documents[4] = new Document("$unwind", '$' + myQuery.getFromTable() + "_" + myQuery.getJoinClause().getJoinedTable());
        //transfer to pipeline
        for(int i = 0; i < 6 ; i++) {
            if(documents[i] != null)
                pipeLine.add(documents[i]);
        }

        return pipeLine;

    }










}
