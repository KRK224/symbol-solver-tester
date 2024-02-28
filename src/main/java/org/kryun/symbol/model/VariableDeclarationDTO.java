package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.Type;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.kryun.symbol.model.interfaces.FQNReferable;

@Getter
@Setter
public class VariableDeclarationDTO implements FQNReferable {
    private Long variableId;
    private Long blockId;
    private Long importId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private String name;
    private String modifier;
    private String accessModifier;
    private String type;
    private Type variableType;
    private Node node;
    private Optional<Node> initializer;
    private Position position;
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
