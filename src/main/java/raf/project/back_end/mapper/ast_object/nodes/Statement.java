package raf.project.back_end.mapper.ast_object.nodes;

import raf.project.back_end.mapper.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class Statement extends ASTNode {


    private final List<Object> selectArgs;

    private final List<Object> fromArgs;


    public Statement(ASTNode ... children) {
        selectArgs = new ArrayList<>();
        fromArgs = new ArrayList<>();

    }

    public Statement() {
        selectArgs = new ArrayList<>();
        fromArgs = new ArrayList<>();

    }

    public Statement addSelectArgs(List<?> args) {
        selectArgs.addAll(args);
        return this;
    }

    public Statement addFromArgs(List<?> args) {
        fromArgs.addAll(args);
        return this;
    }

    public List<Object> getFromArgs() {
        return fromArgs;
    }

    public List<Object> getSelectArgs() {
        return selectArgs;
    }
}

