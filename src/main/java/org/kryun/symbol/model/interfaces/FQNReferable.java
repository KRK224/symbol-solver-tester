package org.kryun.symbol.model.interfaces;

public interface FQNReferable extends Referable {

    public abstract Long getFullQualifiedNameId();

    public abstract void setFullQualifiedNameId(Long fullQualifiedNameId);

    public abstract Boolean getIsFullQualifiedNameIdFromDB();

    public abstract void setIsFullQualifiedNameIdFromDB(Boolean isFullQualifiedNameIdFromDB);
}
