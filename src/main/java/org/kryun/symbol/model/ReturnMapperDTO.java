package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import org.kryun.symbol.model.interfaces.ClassReferable;

public class ReturnMapperDTO {
    private Long returnMapperId;
    private Long methodDeclId;
    private Long fullQualifiedNameId;
    private String type;
    private Node node;
    private Position position;

    public Long getReturnMapperId() {
        return returnMapperId;
    }

    public void setReturnMapperId(Long returnMapperId) {
        this.returnMapperId = returnMapperId;
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
        return "ReturnMapperDTO{" +
            "returnMapperId: " + returnMapperId +
            ", methodDeclId: " + methodDeclId +
            ", fullQualifiedNameId : " + fullQualifiedNameId +
            ", type: '" + type + '\'' +
            ", position: " + position +
            '}';
    }
}
