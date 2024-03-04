package org.kryun.symbol.pkg.management;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.metamodel.ImportDeclarationMetaModel;
import com.github.javaparser.metamodel.PropertyMetaModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.kryun.symbol.model.ImportDTO;
import org.kryun.symbol.model.Position;


public class ImportManager {

    private final List<ImportDTO> importDTOList;

    public ImportManager() {
        this.importDTOList = new ArrayList<>();
    }

    public List<ImportDTO> getImportDTOList() {
        return this.importDTOList;
    }

    public void importListClear() {
        this.importDTOList.clear();
    }

    public ImportDTO buildImport(Long importId, Long blockId, Node node) {
        ImportDTO importDTO = new ImportDTO();
        String name = "";
        String packageName = "";
        String className = "";
        String memberName = "";

        ImportDeclaration importNode = (ImportDeclaration) node;
        Boolean isStatic = importNode.isStatic();
        Boolean isAsterisk = importNode.isAsterisk();
        Name nameNode = importNode.getName();
        name = nameNode.toString();
//        System.out.println("name = " + name);
        if(isStatic && isAsterisk) {
            memberName = "*";
            Optional<Name> packageQualifier = nameNode.getQualifier();
            packageName = packageQualifier.get().toString();
            className = name.replace(packageName+".", "");
//            System.out.println("packageName = " + packageName);
//            System.out.println("className = " + className);
//            System.out.println("memberName = " + memberName);
        } else if(isAsterisk) {
            packageName = name;
            className = "*";
//            System.out.println("packageName = " + packageName);
//            System.out.println("className = " + className);
//            System.out.println("memberName = " + memberName);
        } else if (isStatic) {
            String memeberFQN = name;
            Optional<Name> classFQNQuailfier = nameNode.getQualifier();
            String classFQN = classFQNQuailfier.get().toString();
            Optional<Name> packageQuailfier = classFQNQuailfier.get().getQualifier();
            packageName = packageQuailfier.get().toString();
            className = classFQN.replace(packageName+".", "").trim();
            memberName = memeberFQN.replace(classFQN+".", "").trim();
//            System.out.println("packageName = " + packageName);
//            System.out.println("className = " + className);
//            System.out.println("memberName = " + memberName);
        } else {
            String classFQN = name;
            Optional<Name> packageQualifier = nameNode.getQualifier();
            packageName = packageQualifier.get().toString();
            className = classFQN.replace(packageName+".", "").trim();
//            System.out.println("packageName = " + packageName);
//            System.out.println("className = " + className);
//            System.out.println("memberName = " + memberName);
        }


        importDTO.setImportId(importId);
        importDTO.setBlockId(blockId);
        importDTO.setName(name);
        importDTO.setPackageName(packageName);
        importDTO.setClassName(className);
        importDTO.setMemberName(memberName);
        importDTO.setIsAsterisk(isAsterisk);
        importDTO.setIsStatic(isStatic);
        importDTO.setPosition(
            new Position(
                node.getRange().get().begin.line,
                node.getRange().get().begin.column,
                node.getRange().get().end.line,
                node.getRange().get().end.column));

        importDTOList.add(importDTO);

        return importDTO;

    }
}
