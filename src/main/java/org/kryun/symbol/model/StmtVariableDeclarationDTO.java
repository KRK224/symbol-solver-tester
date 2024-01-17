package org.kryun.symbol.model;

public class StmtVariableDeclarationDTO extends VariableDeclarationDTO {

    @Override
    public String toString() {
        return "StmtVariableDeclarationDTO : {" +
                "variableId : " + getVariableId() +
                ", blockId : " + getBlockId() +
                ", typeClassId : " + getTypeClassIdImpl() +
                ", importId : " + getImportId() +
                ", name : '" + getName() + '\'' +
                "', nodeType: '" + getNode().getMetaModel().getTypeName() +
                ", modifier : '" + getModifier() + '\'' +
                ", accessModifier : '" + getAccessModifier() + '\'' +
                ", type : '" + getType() + '\'' +
                // ", variableType : " + variableType.getClass().getSimpleName() +
                ", initializer : " + getInitializer() +
                ", Position : '" + getPosition() +
                "}\n";
    }
}
