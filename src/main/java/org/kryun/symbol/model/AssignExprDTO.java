package org.kryun.symbol.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignExprDTO {

    private Long assignExprId;
    private String target;
    private String value;
    private Long blockId;
    private Position position;

    @Override
    public String toString() {
        return "AssignExprDTO : {" +
            "assignExprId : " + assignExprId +
            ", blockId : " + blockId +
            ", target : " + target +
            ", value : " + value +
            ", Position : '" + position + '\'' +
            "}\n";
    }
}

