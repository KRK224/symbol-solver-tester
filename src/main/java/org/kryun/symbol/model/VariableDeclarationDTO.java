package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.Type;
import java.util.Optional;

public class VariableDeclarationDTO{
    private Long variableId;
    private Long blockId;
    private Long importId;
    private Long fullQualifiedNameId;
    private String name;
    private String modifier;
    private String accessModifier;
    private String type;
    private Type variableType;
    private Node node;
    private Optional<Node> initializer;
    private Position position;

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public Long getBlockId() {
        return blockId;
    }


    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getFullQualifiedNameId() {
        return fullQualifiedNameId;
    }

    public void setFullQualifiedNameId(Long fullQualifiedNameId) {
        this.fullQualifiedNameId = fullQualifiedNameId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Type getVariableType() {
        return variableType;
    }

    public void setVariableType(Type variableType) {
        this.variableType = variableType;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Optional<Node> getInitializer() {
        return initializer;
    }

    public void setInitializer(Optional<Node> initializer) {
        this.initializer = initializer;
    }

    public Long getImportId() {
        return importId;
    }

    public void setImportId(Long importId) {
        this.importId = importId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "VariableDeclarationDTO : {" +
                "variableId : " + variableId +
                ", blockId : " + blockId +
                ", importId : " + importId +
                ", name : '" + name + '\'' +
                "', nodeType: '" + node.getMetaModel().getTypeName() +
                ", modifier : '" + modifier + '\'' +
                ", accessModifier : '" + accessModifier + '\'' +
                ", type : '" + type + '\'' +
                // ", variableType : " + variableType.getClass().getSimpleName() +
                ", initializer : " + initializer +
                ", Position : '" + position +
                "}\n";
    }

}
