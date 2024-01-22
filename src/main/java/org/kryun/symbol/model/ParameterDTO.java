package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import org.kryun.symbol.model.interfaces.ClassReferable;

public class ParameterDTO {
    private Long parameterId;
    private Long methodDeclId;
    private Long fullQualifiedNameId;
    private Integer index;
    private String name;
    private String type;
    private Node node;
    private Position position;

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public Long getMethodDeclId() {
        return methodDeclId;
    }

    public void setMethodDeclId(Long methodDeclId) {
        this.methodDeclId = methodDeclId;
    }
    public Long getFullQualifiedNameId() {
        return fullQualifiedNameId;
    }
    public void setFullQualifiedNameId(Long fullQualifiedNameId) {
        this.fullQualifiedNameId = fullQualifiedNameId;
    }
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ParameterDTO{" +
            "parameterId: " + parameterId +
            ", methodDeclarationId: " + methodDeclId +
            ", fullQualifiedNameId : " + fullQualifiedNameId +
            ", index: " + index +
            ", name: '" + name + '\'' +
            ", type: '" + type + '\'' +
            ", position: " + position +
            '}';
    }
}
