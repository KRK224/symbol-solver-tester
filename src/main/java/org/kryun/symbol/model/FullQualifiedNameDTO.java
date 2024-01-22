package org.kryun.symbol.model;

public class FullQualifiedNameDTO {

    private Long fullQualifiedNameId;
    private Long symbolStatusId;

    private Long dependencySymbolStatusId;
    private String fullQualifiedName;
    private Boolean isFullQualifiedNameIdFromDB = false;

    private Boolean isJDK = false;

    public Long getFullQualifiedNameId() {
        return fullQualifiedNameId;
    }

    public void setFullQualifiedNameId(Long fullQualifiedNameId) {
        this.fullQualifiedNameId = fullQualifiedNameId;
    }

    public Long getSymbolStatusId() {
        return symbolStatusId;
    }

    public void setSymbolStatusId(Long symbolStatusId) {
        this.symbolStatusId = symbolStatusId;
    }

    public Long getDependencySymbolStatusId() {
        return dependencySymbolStatusId;
    }

    public void setDependencySymbolStatusId(Long dependencySymbolStatusId) {
        this.dependencySymbolStatusId = dependencySymbolStatusId;
    }

    public String getFullQualifiedName() {
        return fullQualifiedName;
    }

    public void setFullQualifiedName(String fullQualifiedName) {
        this.fullQualifiedName = fullQualifiedName;
    }

    public Boolean getIsFullQualifiedNameIdFromDB() {
        return isFullQualifiedNameIdFromDB;
    }

    public void setIsFullQualifiedNameIdFromDB(Boolean isFullQualifiedNameIdFromDB) {
        this.isFullQualifiedNameIdFromDB = isFullQualifiedNameIdFromDB;
    }

    public Boolean getIsJDK() {
        return isJDK;
    }

    public void setIsJDK(Boolean isJDK) {
        this.isJDK = isJDK;
    }

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
