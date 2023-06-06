package raf.project.app.query_mapper.util;

import org.bson.Document;
import org.bson.conversions.Bson;

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
}
