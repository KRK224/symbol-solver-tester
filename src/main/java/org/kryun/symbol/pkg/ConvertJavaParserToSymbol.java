package org.kryun.symbol.pkg;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.kryun.config.GeneratorIdentifier;
import org.kryun.symbol.model.ArgumentDTO;
import org.kryun.symbol.model.AssignExprDTO;
import org.kryun.symbol.model.BlockDTO;
import org.kryun.symbol.model.ClassDTO;
import org.kryun.symbol.model.FullQualifiedNameDTO;
import org.kryun.symbol.model.ImportDTO;
import org.kryun.symbol.model.MemberVariableDeclarationDTO;
import org.kryun.symbol.model.MethodCallExprDTO;
import org.kryun.symbol.model.MethodDeclarationDTO;
import org.kryun.symbol.model.PackageDTO;
import org.kryun.symbol.model.ParameterDTO;
import org.kryun.symbol.model.ReturnMapperDTO;
import org.kryun.symbol.model.StmtVariableDeclarationDTO;
import org.kryun.symbol.model.SymbolReferenceDTO;
import org.kryun.symbol.model.interfaces.FQNReferable;
import org.kryun.symbol.model.interfaces.NameExprFQNReferable;
import org.kryun.symbol.pkg.management.BlockManager;
import org.kryun.symbol.pkg.management.ClassManager;
import org.kryun.symbol.pkg.management.ExprManager;
import org.kryun.symbol.pkg.management.FullQualifiedNameManager;
import org.kryun.symbol.pkg.management.ImportManager;
import org.kryun.symbol.pkg.management.MethodManager;
import org.kryun.symbol.pkg.management.PackageManager;
import org.kryun.symbol.pkg.management.SymbolReferenceManager;
import org.kryun.symbol.pkg.management.VariableManager;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.ReferableTypeResolver;

public class ConvertJavaParserToSymbol {

    private final LastSymbolDetector lastSymbolDetector = new LastSymbolDetector();
    private final Long symbolStatusId;
    private final Boolean isDependency;
    private Connection conn;
    private Map<String, Long> symbolIds;
    private TypeResolverManager typeResolverManager = new TypeResolverManager();

    private final SymbolReferenceManager symbolReferenceManager = new SymbolReferenceManager();
    private final BlockManager blockManager = new BlockManager();
    private final PackageManager packageManager = new PackageManager();
    private final ImportManager importManager = new ImportManager();
    private final ClassManager classManager = new ClassManager();

    private final VariableManager variableManager = new VariableManager();
    private final MethodManager methodManager = new MethodManager();
    private final ExprManager exprManager = new ExprManager();
    private final GeneratorIdentifier generatorIdentifier = new GeneratorIdentifier();
    private final FullQualifiedNameManager fullQualifiedNameManager = new FullQualifiedNameManager();

    private Boolean hasPackage = false;

    public GeneratorIdentifier getGeneratorIdentifier() {
        return generatorIdentifier;
    }


    public ConvertJavaParserToSymbol(Long symbolStatusId, Boolean isDependency) {
        this.isDependency = isDependency;
        this.symbolStatusId = symbolStatusId;
        this.symbolIds = generatorIdentifier.getSymbolIds();
    }

    public List<SymbolReferenceDTO> getSymbolReferenceDTOList() {
        return symbolReferenceManager.getSymbolReferenceDTOList();
    }

    public List<ArgumentDTO> getArgumentDTOList() {
        return methodManager.getArgumentDTOList();
    }

    public List<BlockDTO> getBlockDTOList() {
        return blockManager.getBlockDTOList();
    }

    public List<ClassDTO> getClassDTOList() {
        return classManager.getClassDTOList();
    }

    public List<PackageDTO> getPackageDTOList() {
        return packageManager.getPackageDTOList();
    }

    public List<ImportDTO> getImportDTOList() {
        return importManager.getImportDTOList();
    }

    public List<ParameterDTO> getParameterDTOList() {
        return methodManager.getParameterDTOList();
    }

    public List<ReturnMapperDTO> getReturnMapperDTOList() {
        return methodManager.getReturnMapperDTOList();
    }

    public List<AssignExprDTO> getAssignExprDTOList() {
        return exprManager.getAssignExprDTOList();
    }

    public List<MemberVariableDeclarationDTO> getMemberVariableDeclarationDTOList() {
        return variableManager.getMemberVariableDeclarationDTOList();
    }

    public List<StmtVariableDeclarationDTO> getStmtVariableDeclarationDTOList() {
        return variableManager.getStmtVariableDeclarationDTOList();
    }

    public List<MethodDeclarationDTO> getMethodDeclarationDTOList() {
        return methodManager.getMethodDeclarationDTOList();
    }

    public List<MethodCallExprDTO> getMethodCallExprDTOList() {
        return methodManager.getMethodCallExprDTOList();
    }

    public List<FullQualifiedNameDTO> getFullQualifiedNameDTOList() {
        return fullQualifiedNameManager.getFullQualifiedNameDTOList();
    }

    public void clear() {
        symbolReferenceManager.symbolReferenceListClear();
        blockManager.blockListClear();
        packageManager.packageListClear();
        importManager.importListClear();
        classManager.classListClear();
        variableManager.variableDeclarationListClear();
        methodManager.methodDeclarationListClear();
        methodManager.methodCallExprListClear();
        methodManager.argumentDTOListClear();
        methodManager.parameterDTOListClear();
        methodManager.returnMapperDTOListClear();
        exprManager.assignExprDTOListClear();
        fullQualifiedNameManager.fullQualifiedNameListClear();
        typeResolverManager.clear();
    }
    public String printLastSymbol() {
        return lastSymbolDetector.toString();
    }

    public void visit(CompilationUnit cu, String srcPath) {
        Map<String, ImportDTO> typeImportMapper = new HashMap<>();

        String nodeType = cu.getMetaModel().getTypeName();
        Long currentSymbolReferenceId = symbolIds.get("symbol_reference");
        SymbolReferenceDTO symbolReferenceDTO = symbolReferenceManager
            .buildSymbolReference(currentSymbolReferenceId, symbolStatusId, srcPath);

        symbolIds.put("symbol_reference", currentSymbolReferenceId + 1);
        Long symbolReferenceId = symbolReferenceDTO.getSymbolReferenceId();
        BlockDTO rootBlock = blockManager.buildBlock(symbolIds.get("block"), 1, null, nodeType, cu,
            symbolReferenceId);
        symbolIds.put("block", symbolIds.get("block") + 1);
        lastSymbolDetector.setSrcPath(srcPath);
        visitAndBuild(cu, rootBlock, isDependency, typeImportMapper);
    }

    private void visitAndBuild(Node node, BlockDTO parentBlockDTO,        Boolean isDependency, Map<String, ImportDTO> typeImportMapper) {
        BlockDTO blockDTO;
        String nodeType = node.getMetaModel().getTypeName();

        if (nodeType.equals("PackageDeclaration")) {
            blockDTO = parentBlockDTO;
            packageManager.buildPackage(symbolIds.get("package"), blockDTO.getBlockId(), node);
            symbolIds.put("package", symbolIds.get("package") + 1);
            hasPackage = true;
        } else if (nodeType.equals("ImportDeclaration")) {
            blockDTO = parentBlockDTO;
            ImportDTO importDTO = importManager.buildImport(symbolIds.get("import"),
                blockDTO.getBlockId(),node);
            symbolIds.put("import", symbolIds.get("import") + 1);

            if(!(importDTO.getIsAsterisk() || importDTO.getIsStatic())){
                // static도 아니고, asterisk도 아닌 경우
                typeImportMapper.put(importDTO.getClassName(), importDTO);
            }

        } else if (nodeType.equals("ClassOrInterfaceDeclaration")) {
            blockDTO = parentBlockDTO;
            ClassDTO classDTO = classManager.buildClass(symbolIds.get("class"),
                blockDTO.getBlockId(),
                getCurrentPackageId(symbolIds), node);
            symbolIds.put("class", symbolIds.get("class") + 1);

            lastSymbolDetector.setSymbolType("ClassOrInterfaceDeclaration");
            lastSymbolDetector.setSymbolName(classDTO.getName());
            lastSymbolDetector.setSymbolPostion(classDTO.getPosition());

            if(classDTO.getName().equals("SaveGatewaySsl")) {
                System.out.println("break point");
            }
            typeResolverManager.generateClassFullQualifiedName(
                    (ClassOrInterfaceDeclaration) node)
                .ifPresent(fqn -> updateClassFQNReferableDTO(fqn, classDTO));

        } else if (nodeType.equals("EnumDeclaration")) {
            blockDTO = parentBlockDTO;
            ClassDTO enumDTO = classManager.buildEnum(symbolIds.get("class"),
                blockDTO.getBlockId(),
                getCurrentPackageId(symbolIds), node);
            symbolIds.put("class", symbolIds.get("class") + 1);

            lastSymbolDetector.setSymbolType("EnumDeclaration");
            lastSymbolDetector.setSymbolName(enumDTO.getName());
            lastSymbolDetector.setSymbolPostion(enumDTO.getPosition());

            typeResolverManager.generateEnumFullQualifiedName(
                (EnumDeclaration) node).ifPresent(fqn -> updateClassFQNReferableDTO(fqn, enumDTO));

        } else if (nodeType.equals("AnnotationDeclaration") || nodeType.equals(
            "RecordDeclaration")) {
            blockDTO = blockManager.buildBlock(symbolIds.get("block"),
                parentBlockDTO.getDepth() + 1,
                parentBlockDTO.getBlockId(), nodeType, node, parentBlockDTO.getSymbolReferenceId());
            symbolIds.put("block", symbolIds.get("block") + 1);
        } else if (node.getChildNodes().stream()
            .anyMatch(n -> n.getMetaModel().getTypeName().equals("BlockStmt"))) {
            if (nodeType.equals("MethodDeclaration") || nodeType.equals("ConstructorDeclaration")) {
                // 내부에 BlockStmt 가 존재하여 별도의 Block 을 생성하지는 않음
                // 현재 상위 block 에서 선언되는 것들.
                blockDTO = parentBlockDTO;
                // 함수 및 생성자 선언 시 build
                ClassDTO belongedClassDTO = classManager.getClassDTOList()
                    .get(classManager.getClassDTOList().size() - 1);
                MethodDeclarationDTO mdDto = methodManager.buildMethodDeclaration(
                    symbolIds.get("method_decl"),
                    blockDTO.getBlockId(),
                    belongedClassDTO.getClassId(), node, nodeType, generatorIdentifier);
                symbolIds.put("method_decl", symbolIds.get("method_decl") + 1);

                if (nodeType.equals("MethodDeclaration")) {
                    ReturnMapperDTO rmDto = mdDto.getReturnMapper();
                    List<ParameterDTO> parameterDTOList = mdDto.getParameters();

                    lastSymbolDetector.setSymbolType("MethodDeclaration");
                    lastSymbolDetector.setSymbolName(mdDto.getName());
                    lastSymbolDetector.setSymbolPostion(mdDto.getPosition());

                    typeResolverManager.generateMethodDeclFullQualifiedName(
                            (MethodDeclaration) node)
                        .ifPresent(fqn -> updateMethodFQNReferableDTO(fqn, mdDto,
                            mdDto.getName()));

                    typeResolverManager.generateReturnFullQualifiedName(
                            (MethodDeclaration) node)
                        .ifPresent(fqn -> updateClassFQNReferableDTO(fqn, rmDto));

                    parameterDTOList.forEach(paramDto -> {
                        Parameter parameterNode = (Parameter) paramDto.getNode();
                        typeResolverManager.generateParameterFullQualifiedName(parameterNode)
                            .ifPresent(fqn -> updateClassFQNReferableDTO(fqn, paramDto));
                    });
                }
            }
            blockDTO = blockManager.buildBlock(symbolIds.get("block"),
                parentBlockDTO.getDepth() + 1,
                parentBlockDTO.getBlockId(), nodeType, node, parentBlockDTO.getSymbolReferenceId());
            symbolIds.put("block", symbolIds.get("block") + 1);
        }
        // 클래스 바로 아래에서 변수를 선언하는 멤버 필드
        else if (nodeType.equals("FieldDeclaration")) {
            blockDTO = parentBlockDTO;
            ClassDTO belongedClassDTO = classManager.getClassDTOList()
                .get(classManager.getClassDTOList().size() - 1);
            MemberVariableDeclarationDTO mvdDto = variableManager.buildVariableDeclInMemberField(
                symbolIds.get("member_var_decl"), blockDTO.getBlockId(),
                belongedClassDTO.getClassId(), node);

            symbolIds.put("member_var_decl", symbolIds.get("member_var_decl") + 1);

            lastSymbolDetector.setSymbolType("FieldDeclaration");
            lastSymbolDetector.setSymbolName(mvdDto.getName());
            lastSymbolDetector.setSymbolPostion(mvdDto.getPosition());

            Optional<String> fqn = typeResolverManager.generateFieldDeclFullQualifiedName((FieldDeclaration) node);
            if(fqn.isPresent()){
                updateClassFQNReferableDTO(fqn.get(), mvdDto);
            } else { // fqn 생성 실패 시에 직접 찾기
                String type = mvdDto.getType();
                ImportDTO importDTO = typeImportMapper.get(type);
                if (importDTO != null){
                    mvdDto.setImportId(importDTO.getImportId());
                    updateClassFQNReferableDTO(importDTO.getName(), mvdDto);
                }
            }

        }
        // 함수 내에서 선언하는 변수
        else if (nodeType.equals("VariableDeclarationExpr")) {
            blockDTO = parentBlockDTO;
            StmtVariableDeclarationDTO stmtDto = variableManager.buildVariableDeclInMethod(
                symbolIds.get("stmt_var_decl"), blockDTO.getBlockId(), node);
            symbolIds.put("stmt_var_decl", symbolIds.get("stmt_var_decl") + 1);

            lastSymbolDetector.setSymbolType("VariableDeclarationExpr");
            lastSymbolDetector.setSymbolName(stmtDto.getName());
            lastSymbolDetector.setSymbolPostion(stmtDto.getPosition());

            Optional<String> fqn = typeResolverManager.generateVariableDeclFullQualifiedName(
                (VariableDeclarationExpr) node);
            if(fqn.isPresent()) {
                updateClassFQNReferableDTO(fqn.get(), stmtDto);
            } else {
                String type = stmtDto.getType();
                ImportDTO importDTO = typeImportMapper.get(type);
                if (importDTO != null) {
                   stmtDto.setImportId(importDTO.getImportId());
                   updateClassFQNReferableDTO(importDTO.getName(), stmtDto);
                }
            }

        } else if (nodeType.equals("MethodDeclaration") || nodeType.equals(
            "ConstructorDeclaration")) {
            // 내부에 BlockStmt 가 존재하여 별도의 Block 을 생성하지는 않음
            // 현재 상위 block 에서 선언되는 것들.
            blockDTO = parentBlockDTO;
            // 함수 및 생성자 선언 시 build
            ClassDTO belongedClassDTO = classManager.getClassDTOList()
                .get(classManager.getClassDTOList().size() - 1);
            MethodDeclarationDTO mdDto = methodManager.buildMethodDeclaration(
                symbolIds.get("method_decl"),
                blockDTO.getBlockId(),
                belongedClassDTO.getClassId(), node, nodeType, generatorIdentifier);
            symbolIds.put("method_decl", symbolIds.get("method_decl") + 1);

            if (nodeType.equals("MethodDeclaration")) {
                ReturnMapperDTO rmDto = mdDto.getReturnMapper();
                List<ParameterDTO> parameterDTOList = mdDto.getParameters();

                lastSymbolDetector.setSymbolType("MethodDeclaration");
                lastSymbolDetector.setSymbolName(mdDto.getName());
                lastSymbolDetector.setSymbolPostion(mdDto.getPosition());

                typeResolverManager.generateMethodDeclFullQualifiedName(
                        (MethodDeclaration) node)
                    .ifPresent(fqn -> updateMethodFQNReferableDTO(fqn, mdDto,
                        mdDto.getName()));

                Optional<String> rmFqn = typeResolverManager.generateReturnFullQualifiedName(
                    (MethodDeclaration) node);
                if(rmFqn.isPresent()) {
                    updateClassFQNReferableDTO(rmFqn.get(), rmDto);
                } else {
                    String type = rmDto.getType();
                    ImportDTO importDTO = typeImportMapper.get(type);
                    if (importDTO !=null) {
                        rmDto.setImportId(importDTO.getImportId());
                        updateClassFQNReferableDTO(importDTO.getName(), rmDto);
                    }
                }


                parameterDTOList.forEach(paramDto -> {
                    Parameter parameterNode = (Parameter) paramDto.getNode();
                    Optional<String> paramFqn = typeResolverManager.generateParameterFullQualifiedName(
                        parameterNode);
                    if(paramFqn.isPresent()) {
                        updateClassFQNReferableDTO(paramFqn.get(), paramDto);
                    } else {
                        String type = paramDto.getType();
                        ImportDTO importDTO = typeImportMapper.get(type);
                        if(importDTO != null) {
                            paramDto.setImportId(importDTO.getImportId());
                            updateClassFQNReferableDTO(importDTO.getName(), paramDto);
                        }
                    }

                });
            }

        } else if (nodeType.equals("MethodCallExpr")) {
            blockDTO = parentBlockDTO;
            MethodCallExprDTO mceDto = methodManager.buildMethodCallExpr(
                symbolIds.get("method_call_expr"),
                blockDTO.getBlockId(), node, nodeType, generatorIdentifier);
            symbolIds.put("method_call_expr", symbolIds.get("method_call_expr") + 1);
            if (!isDependency) {
                // Dependency가 아닌 경우에만 동작

                lastSymbolDetector.setSymbolType("MethodCallExpr");
                lastSymbolDetector.setSymbolName(mceDto.getName());
                lastSymbolDetector.setSymbolPostion(mceDto.getPosition());

                typeResolverManager.generateMethodCallExprFullQualifiedName(
                        (MethodCallExpr) node)
                    .ifPresent(fqn -> updateMethodFQNReferableDTO(fqn, mceDto, mceDto.getName()));

                if (mceDto.getNameExprNode() != null) {
                    typeResolverManager.generateNameExprFullQualifiedName(
                            mceDto.getNameExprNode())
                        .ifPresent(fqn -> updateNameExprFQNReferableDTO(fqn, mceDto));
                }
            }

        } else if (nodeType.equals("AssignExpr")) {
            blockDTO = parentBlockDTO;
            exprManager.buildAssignExpr(symbolIds.get("assign_expr"), blockDTO.getBlockId(), node);
            symbolIds.put("assign_expr", symbolIds.get("assign_expr") + 1);
        } else {
            blockDTO = parentBlockDTO;
        }
        List<Node> childNodes = node.getChildNodes();
        for (Node childNode : childNodes) {
            if (!childNode.getMetaModel().getTypeName().equals("SimpleName") ||
                !childNode.getMetaModel().getTypeName().equals("Modifier")) {
                visitAndBuild(childNode, blockDTO, isDependency, typeImportMapper);
            }
        }
    }

    private long getCurrentPackageId(Map<String, Long> symbolIds) {
        return hasPackage ? symbolIds.get("package") - 1 : -100L;
    }

    private void updateClassFQNReferableDTO(String fqn,
        FQNReferable fqnReferable) {
        if (fqn.equals("primitive")) {
            fqnReferable.setIsFullQualifiedNameIdFromDB(true);
            fqnReferable.setFullQualifiedNameId(-1L);
        } else {
            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                .orElseGet(() -> {
                    Boolean isJDK = ReferableTypeResolver.isJdkPackage(fqn);
                    Long currentId = symbolIds.get("full_qualified_name");
                    symbolIds.put("full_qualified_name", currentId + 1);
                    FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                        .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJDK);
                    typeResolverManager.registerFullQualifiedNameDTO(fqn,
                        newFullQualifiedNameDTO);
                    return newFullQualifiedNameDTO;
                });
            fqnReferable.setIsFullQualifiedNameIdFromDB(
                fullQualifiedNameDTO.getIsFullQualifiedNameIdFromDB());
            fqnReferable.setFullQualifiedNameId(
                fullQualifiedNameDTO.getFullQualifiedNameId());
        }
    }

    private void updateMethodFQNReferableDTO(String fqn,
        FQNReferable fqnReferable, String methodName) {
        if (fqn.equals("primitive")) {
            fqnReferable.setIsFullQualifiedNameIdFromDB(true);
            fqnReferable.setFullQualifiedNameId(-1L);
        } else {
            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                .orElseGet(() -> {
                    Boolean isJDK = ReferableTypeResolver.isJdkPackage(fqn, methodName);
                    Long currentId = symbolIds.get("full_qualified_name");
                    symbolIds.put("full_qualified_name", currentId + 1);
                    FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                        .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJDK);
                    typeResolverManager.registerFullQualifiedNameDTO(fqn,
                        newFullQualifiedNameDTO);
                    return newFullQualifiedNameDTO;
                });
            fqnReferable.setIsFullQualifiedNameIdFromDB(
                fullQualifiedNameDTO.getIsFullQualifiedNameIdFromDB());
            fqnReferable.setFullQualifiedNameId(
                fullQualifiedNameDTO.getFullQualifiedNameId());
        }
    }

    private void updateNameExprFQNReferableDTO(String fqn,
        NameExprFQNReferable nameExprFQNReferable) {
        if (fqn.equals("primitive")) {
            // 의도치 않은 에러를 방지하기 위한 방어 로직
            nameExprFQNReferable.setIsNameExprFullQualifiedNameIdFromDB(true);
            nameExprFQNReferable.setNameExprFullQualifiedNameId(-1L);
        } else {
            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager.getFullQualifiedNameDTOFromTypeMapper(
                fqn, symbolStatusId).orElseGet(() -> {
                Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                Long currentId = symbolIds.get("full_qualified_name");
                symbolIds.put("full_qualified_name", currentId + 1);
                FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                    .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                typeResolverManager.registerFullQualifiedNameDTO(fqn,
                    newFullQualifiedNameDTO);
                return newFullQualifiedNameDTO;
            });
            nameExprFQNReferable.setIsNameExprFullQualifiedNameIdFromDB(
                fullQualifiedNameDTO.getIsFullQualifiedNameIdFromDB());
            nameExprFQNReferable.setNameExprFullQualifiedNameId(
                fullQualifiedNameDTO.getFullQualifiedNameId());
        }
    }
}
