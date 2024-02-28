package org.kryun.symbol.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportDTO {
    private Long importId;
    private Long blockId;
    private String name;
    private Position position;

    @Override
    public String toString() {
        return "ImportDTO :{" +
                "importId : " + importId +
                ", blockId : " + blockId +
                ", name : '" + name + '\'' +
                ", Position : '" + position +
                "}\n";
    }
}
