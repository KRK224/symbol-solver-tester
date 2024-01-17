package org.kryun.global.enums;

public enum SymbolInfoTypeEnum {
    CLASS("class"), ENUM("enum"), INTERFACE("interface"), METHOD_DECL("methodDecl"), METHOD_CALL_EXPR("methodCallExpr"),
    STMT_VAR_DECL("stmtVarDecl"),
    MEMBER_VAR_DECL("memberVarDecl"), PARAMETER("parameter"), RETURN_MAPPER("returnMapper");

    private String type;

    SymbolInfoTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return type;
    }
}
