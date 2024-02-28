package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import lombok.Getter;
import lombok.Setter;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.model.interfaces.FQNReferable;

@Getter
@Setter
public class ParameterDTO implements FQNReferable {
    private Long parameterId;
    private Long methodDeclId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private Integer index;
    private String name;
    private String type;
    private Node node;
    private Position position;

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
