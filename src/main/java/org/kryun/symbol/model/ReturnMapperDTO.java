package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import lombok.Getter;
import lombok.Setter;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.model.interfaces.FQNReferable;

@Getter
@Setter
public class ReturnMapperDTO implements FQNReferable {
    private Long returnMapperId;
    private Long methodDeclId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private String type;
    private Node node;
    private Position position;
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
