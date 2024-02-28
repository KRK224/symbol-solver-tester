package org.kryun.symbol.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullQualifiedNameDTO {

    private Long fullQualifiedNameId;
    private Long symbolStatusId;

    private Long dependencySymbolStatusId;
    private String fullQualifiedName;
    private Boolean isFullQualifiedNameIdFromDB = false;

    private Boolean isJDK = false;

    @Override
    public String toString() {
        return "FullQualifiedNameDTO: {" +
            "fullQualifiedId : " + fullQualifiedNameId +
            ", fullQualifiedName : " + fullQualifiedName +
            ", symbolStatusId : " + symbolStatusId +
            ", isFullQualifiedNameIdFromDB : " + isFullQualifiedNameIdFromDB +
            ", isJDKPackage : " + isJDK +
            "}\n";
    }

}
