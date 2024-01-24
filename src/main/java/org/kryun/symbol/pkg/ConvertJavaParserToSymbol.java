package org.kryun.symbol.pkg;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import java.util.List;
import java.util.Map;
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
    private final TypeResolverManager typeResolverManager = new TypeResolverManager();
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

    public GeneratorIdentifier getGeneratorIdentifier() {
        return generatorIdentifier;
    }

    public ConvertJavaParserToSymbol() throws Exception {

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

    public void visit(Node node, Long symbolStatusId, String srcPath) {
        //현재 파일 위치 저장
        lastSymbolDetector.setSrcPath(srcPath);
        BlockDTO rootBlock = visitAndBuildRoot((CompilationUnit) node, symbolStatusId, srcPath);
        visitAndBuild(node, symbolStatusId, rootBlock);
    }
    private BlockDTO visitAndBuildRoot(CompilationUnit cu, Long symbolStatusId, String srcPath) {
        Map<String, Long> symbolIds = generatorIdentifier.symbolIds;
        String nodeType = cu.getMetaModel().getTypeName();
        Long currentSymbolReferenceId = symbolIds.get("symbol_reference");
        SymbolReferenceDTO symbolReferenceDTO = symbolReferenceManager
            .buildSymbolReference(currentSymbolReferenceId, symbolStatusId, srcPath);

        symbolIds.put("symbol_reference", currentSymbolReferenceId + 1);
        Long symbolReferenceId = symbolReferenceDTO.getSymbolReferenceId();
        BlockDTO rootBlockDTO = blockManager.buildBlock(symbolIds.get("block"), 1, null, nodeType,
            cu,
            symbolReferenceId);
        symbolIds.put("block", symbolIds.get("block") + 1);
        PackageDTO packageDTO = null;

        // import, package, class dto 생성
        List<Node> childNodes = cu.getChildNodes();
        for (Node node : childNodes) {
            String childNodeType = node.getMetaModel().getTypeName();
            switch (childNodeType) {
                case "PackageDeclaration":
                    packageDTO = packageManager.buildPackage(symbolIds.get("package"),
                        rootBlockDTO.getBlockId(), node);
                    symbolIds.put("package", symbolIds.get("package") + 1);
                    break;
                case "ModuleDeclaration":
                    // 모듈이 뭔지 잘 모르겠으나 해당 준위에 포함됨.
                    break;
                case "ImportDeclaration":
                    importManager.buildImport(symbolIds.get("import"), rootBlockDTO.getBlockId(),
                        node);
                    symbolIds.put("import", symbolIds.get("import") + 1);
                    break;
                case "ClassOrInterfaceDeclaration":
                    ClassDTO classDTO = classManager.buildClass(symbolIds.get("class"),
                        rootBlockDTO.getBlockId(),
                        packageDTO != null ? packageDTO.getPackageId() : -100L, node);
                    symbolIds.put("class", symbolIds.get("class") + 1);

                    /**
                     * 테스트 용도
                     */
                    lastSymbolDetector.setSymbolType("ClassOrInterfaceDeclaration");
                    lastSymbolDetector.setSymbolName(classDTO.getName());
                    lastSymbolDetector.setSymbolPostion(classDTO.getPosition());

                    typeResolverManager.generateClassFullQualifiedName(
                            (ClassOrInterfaceDeclaration) node)
                        .ifPresent((fqn) -> {
                            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                                .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                                .orElseGet(() -> {
                                    Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                                    Long currentId = symbolIds.get("full_qualified_name");
                                    symbolIds.put("full_qualified_name", currentId + 1);
                                    FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                        .buildFullQualifiedName(currentId, symbolStatusId, fqn,
                                            isJdk);
                                    typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                        newFullQualifiedNameDTO);
                                    return newFullQualifiedNameDTO;
                                });
                            classDTO.setFullQualifiedNameId(
                                fullQualifiedNameDTO.getFullQualifiedNameId());
                        });
                    break;
                case "EnumDeclaration":
                    ClassDTO enumDTO = classManager.buildEnum(symbolIds.get("class"),
                        rootBlockDTO.getBlockId(),
                        packageDTO != null ? packageDTO.getPackageId() : -100L, node);
                    symbolIds.put("class", symbolIds.get("class") + 1);
                    /**
                     * 테스트 용도
                     */

                    lastSymbolDetector.setSymbolType("EnumDeclaration");
                    lastSymbolDetector.setSymbolName(enumDTO.getName());
                    lastSymbolDetector.setSymbolPostion(enumDTO.getPosition());

                    typeResolverManager.generateEnumFullQualifiedName(
                            (EnumDeclaration) node)
                        .ifPresent((fqn) -> {
                            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                                .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                                .orElseGet(() -> {
                                    Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                                    Long currentId = symbolIds.get("full_qualified_name");
                                    symbolIds.put("full_qualified_name", currentId + 1);
                                    FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                        .buildFullQualifiedName(currentId, symbolStatusId, fqn,
                                            isJdk);
                                    typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                        newFullQualifiedNameDTO);
                                    return newFullQualifiedNameDTO;
                                });
                            enumDTO.setFullQualifiedNameId(
                                fullQualifiedNameDTO.getFullQualifiedNameId());
                        });
                    break;
                // 커스텀 어노테이션 제작 시
                case "AnnotationDeclaration":
                    break;
                // 무슨 새로운 class type 이라는데 처음봄...
                case "RecordDeclaration":
                    break;
            }
        }
        return rootBlockDTO;
    }

    private void visitAndBuild(Node node, Long symbolStatusId, BlockDTO parentBlockDTO) {
        Map<String, Long> symbolIds = generatorIdentifier.symbolIds;
        BlockDTO blockDTO;
        String nodeType = node.getMetaModel().getTypeName();
        if (nodeType.equals("ClassOrInterfaceDeclaration") || nodeType.equals("EnumDeclaration") ||
            nodeType.equals("AnnotationDeclaration") || nodeType.equals("RecordDeclaration")) {
            // Todo. Inner Class에 대한 처리?
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
                        (MethodDeclaration) node).ifPresent((fqn) -> {
                        FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                            .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                            .orElseGet(() -> {
                                // Todo. 임시 코드임... 나중에 리팩토링해서 객체로 묶기
                                Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn,
                                    mdDto.getName());
                                Long currentId = symbolIds.get("full_qualified_name");
                                symbolIds.put("full_qualified_name", currentId + 1);
                                FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                    .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                                typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                    newFullQualifiedNameDTO);
                                return newFullQualifiedNameDTO;
                            });
                        mdDto.setFullQualifiedNameId(fullQualifiedNameDTO.getFullQualifiedNameId());
                    });

                    lastSymbolDetector.setSymbolType("ReturnMapper");
                    lastSymbolDetector.setSymbolName(rmDto.getType());
                    lastSymbolDetector.setSymbolPostion(rmDto.getPosition());

                    typeResolverManager.generateReturnFullQualifiedName((MethodDeclaration) node)
                        .ifPresent((fqn) -> {
                            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                                .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                                .orElseGet(() -> {
                                    Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                                    Long currentId = symbolIds.get("full_qualified_name");
                                    symbolIds.put("full_qualified_name", currentId + 1);
                                    FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                        .buildFullQualifiedName(currentId, symbolStatusId, fqn,
                                            isJdk);
                                    typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                        newFullQualifiedNameDTO);
                                    return newFullQualifiedNameDTO;
                                });
                            rmDto.setFullQualifiedNameId(
                                fullQualifiedNameDTO.getFullQualifiedNameId());
                        });

                    parameterDTOList.stream().forEach(paramDto -> {
                        Parameter parameterNode = (Parameter) paramDto.getNode();

                        lastSymbolDetector.setSymbolType("parameterDTO");
                        lastSymbolDetector.setSymbolName(paramDto.getName());
                        lastSymbolDetector.setSymbolPostion(paramDto.getPosition());

                        typeResolverManager.generateParameterFullQualifiedName(parameterNode)
                            .ifPresent((fqn) -> {

                                FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                                    .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                                    .orElseGet(() -> {
                                        Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                                        Long currentId = symbolIds.get("full_qualified_name");
                                        symbolIds.put("full_qualified_name", currentId + 1);
                                        FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                            .buildFullQualifiedName(currentId, symbolStatusId, fqn,
                                                isJdk);
                                        typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                            newFullQualifiedNameDTO);
                                        return newFullQualifiedNameDTO;
                                    });
                                paramDto.setFullQualifiedNameId(
                                    fullQualifiedNameDTO.getFullQualifiedNameId());
                            });
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

            typeResolverManager.generateFieldDeclFullQualifiedName((FieldDeclaration) node)
                .ifPresent((fqn) -> {
                    FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                        .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                        .orElseGet(() -> {
                            Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                            Long currentId = symbolIds.get("full_qualified_name");
                            symbolIds.put("full_qualified_name", currentId + 1);
                            FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                            typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                newFullQualifiedNameDTO);
                            return newFullQualifiedNameDTO;
                        });

                    mvdDto.setFullQualifiedNameId(fullQualifiedNameDTO.getFullQualifiedNameId());
                });

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

            typeResolverManager.generateVariableDeclFullQualifiedName(
                    (VariableDeclarationExpr) node)
                .ifPresent((fqn) -> {
                    FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                        .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                        .orElseGet(() -> {
                            Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                            Long currentId = symbolIds.get("full_qualified_name");
                            symbolIds.put("full_qualified_name", currentId + 1);
                            FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                            typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                newFullQualifiedNameDTO);
                            return newFullQualifiedNameDTO;
                        });
                    stmtDto.setFullQualifiedNameId(fullQualifiedNameDTO.getFullQualifiedNameId());
                });
            // typeResolverManager.registerVariableDecl((VariableDeclarationExpr) node,
            // stmtDto);
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

                typeResolverManager.generateMethodDeclFullQualifiedName((MethodDeclaration) node)
                    .ifPresent((fqn) -> {
                        FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                            .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                            .orElseGet(() -> {
                                Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn,
                                    mdDto.getName());
                                Long currentId = symbolIds.get("full_qualified_name");
                                symbolIds.put("full_qualified_name", currentId + 1);
                                FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                    .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                                typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                    newFullQualifiedNameDTO);
                                return newFullQualifiedNameDTO;
                            });

                        mdDto.setFullQualifiedNameId(fullQualifiedNameDTO.getFullQualifiedNameId());
                    });

                lastSymbolDetector.setSymbolType("ReturnMapper");
                lastSymbolDetector.setSymbolName(rmDto.getType());
                lastSymbolDetector.setSymbolPostion(rmDto.getPosition());

                typeResolverManager.generateReturnFullQualifiedName((MethodDeclaration) node)
                    .ifPresent((fqn) -> {
                        FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                            .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                            .orElseGet(() -> {
                                Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                                Long currentId = symbolIds.get("full_qualified_name");
                                symbolIds.put("full_qualified_name", currentId + 1);
                                FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                    .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                                typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                    newFullQualifiedNameDTO);
                                return newFullQualifiedNameDTO;
                            });

                        rmDto.setFullQualifiedNameId(fullQualifiedNameDTO.getFullQualifiedNameId());
                    });

                parameterDTOList.stream().forEach(paramDto -> {
                    Parameter parameterNode = (Parameter) paramDto.getNode();

                    lastSymbolDetector.setSymbolType("parameterDTO");
                    lastSymbolDetector.setSymbolName(paramDto.getName());
                    lastSymbolDetector.setSymbolPostion(paramDto.getPosition());

                    typeResolverManager.generateParameterFullQualifiedName(parameterNode)
                        .ifPresent((fqn) -> {
                            FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                                .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                                .orElseGet(() -> {
                                    Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn);
                                    Long currentId = symbolIds.get("full_qualified_name");
                                    symbolIds.put("full_qualified_name", currentId + 1);
                                    FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                        .buildFullQualifiedName(currentId, symbolStatusId, fqn,
                                            isJdk);
                                    typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                        newFullQualifiedNameDTO);
                                    return newFullQualifiedNameDTO;
                                });
                            paramDto.setFullQualifiedNameId(
                                fullQualifiedNameDTO.getFullQualifiedNameId());
                        });
                });
            }

        } else if (nodeType.equals("MethodCallExpr")) {
            blockDTO = parentBlockDTO;
            MethodCallExprDTO mceDto = methodManager.buildMethodCallExpr(
                symbolIds.get("method_call_expr"),
                blockDTO.getBlockId(), node, nodeType, generatorIdentifier);
            symbolIds.put("method_call_expr", symbolIds.get("method_call_expr") + 1);

            lastSymbolDetector.setSymbolType("MethodCallExpr");
            lastSymbolDetector.setSymbolPostion(mceDto.getPosition());
            lastSymbolDetector.setSymbolName(mceDto.getName());

            if(lastSymbolDetector.getSymbolName().equals("invoke") && lastSymbolDetector.getSymbolPostion().beginLine == 49) {
                System.out.println("브레이킹 포인트");
            }

            typeResolverManager.generateMethodCallExprFullQualifiedName((MethodCallExpr) node)
                .ifPresent((fqn) -> {
                    FullQualifiedNameDTO fullQualifiedNameDTO = typeResolverManager
                        .getFullQualifiedNameDTOFromTypeMapper(fqn, symbolStatusId)
                        .orElseGet(() -> {
                            Boolean isJdk = ReferableTypeResolver.isJdkPackage(fqn,
                                mceDto.getName());
                            Long currentId = symbolIds.get("full_qualified_name");
                            symbolIds.put("full_qualified_name", currentId + 1);
                            FullQualifiedNameDTO newFullQualifiedNameDTO = fullQualifiedNameManager
                                .buildFullQualifiedName(currentId, symbolStatusId, fqn, isJdk);
                            typeResolverManager.registerFullQualifiedNameDTO(fqn,
                                newFullQualifiedNameDTO);
                            return newFullQualifiedNameDTO;
                        });

                    mceDto.setFullQualifiedNameId(fullQualifiedNameDTO.getFullQualifiedNameId());
                });

            // methodCallExpr의 nameExpr의 fqn을 뽑아내는 과정
            if (mceDto.getNameExprNode() != null) {

                lastSymbolDetector.setSymbolType("methodCall NameExpr");
                lastSymbolDetector.setSymbolName(mceDto.getScopeSimpleName());

                typeResolverManager.generateNameExprFullQualifiedName(mceDto.getNameExprNode())
                    .ifPresent((fqn) -> {
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
                        mceDto.setNameExprFullQualifiedNameId(
                            fullQualifiedNameDTO.getFullQualifiedNameId());
                    });
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
                visitAndBuild(childNode, symbolStatusId, blockDTO);
            }
        }
    }


}
