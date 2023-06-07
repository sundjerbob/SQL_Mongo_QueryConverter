package raf.project.app.mapper.util;

import org.bson.Document;

import java.util.List;

public class DocumentBuilder {
    private final Document document;

    public DocumentBuilder() {
        document = new Document();
    }

    public DocumentBuilder unwind(String field) {
        document.append("$unwind", "$" + field);
        return this;
    }

    public DocumentBuilder project(Document fields) {
        document.append("$project", fields);
        return this;
    }

    public DocumentBuilder match(Document filter) {
        document.append("$match", filter);
        return this;
    }

    public DocumentBuilder sort(Document sortFields) {
        document.append("$sort", sortFields);
        return this;
    }

    public DocumentBuilder lookup(String from, String localField, String foreignField, String as) {
        Document lookup = new Document();
        lookup.append("from", from);
        lookup.append("localField", localField);
        lookup.append("foreignField", foreignField);
        lookup.append("as", as);

        document.append("$lookup", lookup);
        return this;
    }

    public DocumentBuilder group(Document id, Document... fields) {
        Document group = new Document();
        group.append("_id", id);
        for (Document field : fields) {
            group.putAll(field);
        }

        document.append("$group", group);
        return this;
    }

    public DocumentBuilder group(Document id, List<Document> fields) {
        Document group = new Document();
        group.append("_id", id);
        for (Document field : fields) {
            group.putAll(field);
        }

        document.append("$group", group);
        return this;
    }

    // Add other methods for additional MongoDB keyword actions and parameters

    public Document build() {
        return document;
    }
}
