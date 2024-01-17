package org.kryun.symbol.model;

import java.util.List;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.model.interfaces.MethodReferable;

public class MethodCallExprDTO implements MethodReferable, ClassReferable {
    private String name;
    private Long blockId;
    private Long methodCallExprId;
    private Long nameExprTypeClassId;
    private Position position;
    private String nameExpr;
    private List<ArgumentDTO> arguments;
    private Long methodDeclId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBlockId() {
        return blockId;
    }


    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getMethodCallExprId() {
        return methodCallExprId;
    }


    public void setMethodCallExprId(Long methodCallExprId) {
        this.methodCallExprId = methodCallExprId;
    }

    private Long getNameExprTypeClassId() {
        return nameExprTypeClassId;
    }

    private void setNameExprTypeClassId(Long nameExprTypeClassId) {
        this.nameExprTypeClassId = nameExprTypeClassId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ArgumentDTO> getArguments() {
        return arguments;
    }

    public void setArguments(List<ArgumentDTO> arguments) {
        this.arguments = arguments;
    }

    public String getNameExpr() {
        return nameExpr;
    }

    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
    }

    private Long getMethodDeclId() {
        return methodDeclId;
    }

    private void setMethodDeclId(Long methodDeclId) {
        this.methodDeclId = methodDeclId;
    }

    @Override
    public String toString() {
        return "MethodCallExprDTO : {" +
                "methodCallExprId : " + methodCallExprId +
                ", blockId : " + blockId +
                ", nameExprTypeClassId : " + nameExprTypeClassId +
                ", name : '" + name + '\'' +
                ", arguments : '" + arguments + '\'' +
                ", Position : '" + position +
                ", NameExpr : " + nameExpr +
                ", methodDeclId : " + methodDeclId +
                "}\n";
    }

    @Override
    public Long getMethodDeclIdImpl() {
        return getMethodDeclId();
    }

    @Override
    public void setMethodDeclIdImpl(Long methodDeclId) {
        setMethodDeclId(methodDeclId);
    }

    @Override
    public Long getTypeClassIdImpl() {
        return getNameExprTypeClassId();
    }

    @Override
    public void setTypeClassIdImpl(Long classId) {
        setNameExprTypeClassId(classId);
    }

}