package raf.project.app.mapper.util;

import org.bson.Document;
import org.bson.conversions.Bson;
import raf.project.app.parser.ast.ASTNode;
import raf.project.app.parser.ast.clauses.FromClause;
import raf.project.app.parser.ast.clauses.SelectClause;

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

    /*
    public List<Document> buildPipeLine(ASTNode astNode) {
        List<Document> pipeLine = new ArrayList<>();

        for(Object node : astNode.getChildren()) {
            if(node instanceof ASTNode)
                pipeLine.addAll(buildPipeLine((ASTNode) node));
        }

        PipelineBuilder pipelineBuilder = new PipelineBuilder();
        if(astNode instanceof SelectClause) {
            pipelineBuilder.project()
        }

    }*/

}
