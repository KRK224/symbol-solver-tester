package org.kryun.symbol.model.interfaces;

public interface NameExprFQNReferable extends FQNReferable {

    public abstract Long getNameExprFullQualifiedNameId();

    public abstract void setNameExprFullQualifiedNameId(Long nameExprFullQualifiedNameId);

    public abstract Boolean getIsNameExprFullQualifiedNameIdFromDB();

    public abstract void setIsNameExprFullQualifiedNameIdFromDB(
        Boolean isNameExprFullQualifiedNameIdFromDB);

}
