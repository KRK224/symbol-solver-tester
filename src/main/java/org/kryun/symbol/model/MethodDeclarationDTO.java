package org.kryun.symbol.model;


import com.github.javaparser.ast.Node;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.kryun.symbol.model.interfaces.FQNReferable;

@Getter
@Setter
public class MethodDeclarationDTO implements FQNReferable {
    private Long methodDeclId;
    private Long blockId;
    private Long belongedClassId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private String name;
    private String modifier;
    private String accessModifier;
    private ReturnMapperDTO returnMapper;
    private List<ParameterDTO> parameters;
    private Node node;
    private Position position;

    @Override
    public String toString() {
        return "MethodDeclarationDTO{" +
            "methodDeclarationId: " + methodDeclId +
            ", blockId: " + blockId +
            ", belongedClassId: " + belongedClassId +
            ", fullQualifiedNameId : " + fullQualifiedNameId +
            ", name: '" + name + '\'' +
            "', nodeType: '" + node.getMetaModel().getTypeName() +
            ", modifier: '" + modifier + '\'' +
            ", accessModifier: '" + accessModifier + '\'' +
            ", returnMappers: " + returnMapper +
            ", parameters: " + parameters +
            ", position: " + position +
            "}\n";
    }
}
