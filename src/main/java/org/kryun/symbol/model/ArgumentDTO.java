package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArgumentDTO {
    private Long argumentId;
    private Long methodCallExprId;
    private Integer index;
    private String name;
    private String type;
    private Node node;
    private Position position;

    @Override
    public String toString() {
        return "ArgumentDTO{" +
                "argumentId: " + argumentId +
                ", methodCallExprId: " + methodCallExprId +
                ", index: " + index +
                ", name: '" + name + '\'' +
                ", type: '" + type + '\'' +
                ", position: " + position +
                '}';
    }
}
