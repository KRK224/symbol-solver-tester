package org.kryun.symbol.pkg.management;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.kryun.config.GeneratorIdentifier;
import org.kryun.symbol.model.ArgumentDTO;
import org.kryun.symbol.model.MethodCallExprDTO;
import org.kryun.symbol.model.MethodDeclarationDTO;
import org.kryun.symbol.model.ParameterDTO;
import org.kryun.symbol.model.Position;
import org.kryun.symbol.model.ReturnMapperDTO;

public class MethodManager {

    private final List<MethodDeclarationDTO> methodDeclarationDTOList;
    private final List<MethodCallExprDTO> methodCallExprDTOList;

    private final List<ParameterDTO> parameterDTOList;
    private final List<ArgumentDTO> argumentDTOList;
    private final List<ReturnMapperDTO> returnMapperDTOList;
    // private final Map<String, Long> symbolIds;

    public MethodManager() {
        this.methodDeclarationDTOList = new ArrayList<>();
        this.methodCallExprDTOList = new ArrayList<>();
        this.parameterDTOList = new ArrayList<>();
        this.argumentDTOList = new ArrayList<>();
        this.returnMapperDTOList = new ArrayList<>();
    }

    public List<MethodDeclarationDTO> getMethodDeclarationDTOList() {
        return this.methodDeclarationDTOList;
    }

    public List<MethodCallExprDTO> getMethodCallExprDTOList() {
        return this.methodCallExprDTOList;
    }

    public List<ParameterDTO> getParameterDTOList() {
        return this.parameterDTOList;
    }

    public List<ArgumentDTO> getArgumentDTOList() {
        return this.argumentDTOList;
    }

    public List<ReturnMapperDTO> getReturnMapperDTOList() {
        return this.returnMapperDTOList;
    }

    public void methodDeclarationListClear() {
        this.methodDeclarationDTOList.clear();
    }

    public void methodCallExprListClear() {
        this.methodCallExprDTOList.clear();
    }

    public void parameterDTOListClear() {
        this.parameterDTOList.clear();
    }

    public void argumentDTOListClear() {
        this.argumentDTOList.clear();
    }

    public void returnMapperDTOListClear() {
        this.returnMapperDTOList.clear();
    }

    public MethodDeclarationDTO buildMethodDeclaration(Long methodDeclarationId, Long blockId,
        Long belongedClassId,
        Node node,
        String nodeType, GeneratorIdentifier generatorIdentifier) {
        MethodDeclarationDTO methodDeclarationDTO = new MethodDeclarationDTO();
        ReturnMapperDTO returnMapperDTO = new ReturnMapperDTO();
        List<Node> childNodes = node.getChildNodes();
        List<ParameterDTO> localParameterDTOList = new ArrayList<>();
        Map<String, Long> symbolIds = generatorIdentifier.getSymbolIds();

        String modifierKeyword = "";
        String accessModifierKeyword = "";
        String methodName = "";

        int parameterIndex = 1;

        for (Node childNode : childNodes) {
            String childNodeTypeName = childNode.getMetaModel().getTypeName();
            if (childNodeTypeName.equals("Modifier")) {
                Modifier modifier = (Modifier) childNode;
                // 접근 제어자 분별
                if (modifier.getKeyword().equals(Modifier.Keyword.DEFAULT) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PUBLIC) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PROTECTED) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PRIVATE)) {
                    accessModifierKeyword = modifier.getKeyword().asString();
                } else {
                    modifierKeyword = modifier.getKeyword().asString();
                }
            } else if (childNodeTypeName.equals("SimpleName")) {
                SimpleName simpleName = (SimpleName) childNode;
                methodName = simpleName.asString();
            } else if (childNodeTypeName.equals("Parameter")) {
                ParameterDTO parameterDTO = new ParameterDTO();

                Parameter parameterNode = (Parameter) childNode;

                parameterDTO.setParameterId(symbolIds.get("parameter"));
                symbolIds.put("parameter", symbolIds.get("parameter") + 1);
                parameterDTO.setMethodDeclId(methodDeclarationId);
                // parameterDTO.setTypeClassIdImpl(1L); // 일단 1로 박는다
//                parameterDTO.setImportId(-1L); // default -1L
                parameterDTO.setIndex(parameterIndex++);
                parameterDTO.setName(parameterNode.getName().asString());
                parameterDTO.setType(parameterNode.getType().asString());
                parameterDTO.setNode(parameterNode);
                parameterDTO.setPosition(
                    new Position(
                        parameterNode.getRange().get().begin.line,
                        parameterNode.getRange().get().begin.column,
                        parameterNode.getRange().get().end.line,
                        parameterNode.getRange().get().end.column));

                parameterDTOList.add(parameterDTO);
                localParameterDTOList.add(parameterDTO);

            } else if (childNodeTypeName.matches("(.*)Type")) {
                Type returnType = ((Type) childNode).clone();

                // annotation 및 comment 제거 과정
                for (AnnotationExpr annotation : returnType.getAnnotations()) {
                    returnType.remove(annotation);
                }
                returnType.getComment().ifPresent((comment) -> returnType.remove(comment));
                String returnValueTypeName = returnType.toString();

                returnMapperDTO.setReturnMapperId(symbolIds.get("return_mapper"));
                symbolIds.put("return_mapper", symbolIds.get("return_mapper") + 1);
                returnMapperDTO.setMethodDeclId(methodDeclarationId);
                // returnMapperDTO.setTypeClassIdImpl(1L); // 일단 1로 박는다
//                returnMapperDTO.setImportId(-1L); // default -1L
                returnMapperDTO.setType(returnValueTypeName);
                returnMapperDTO.setNode(childNode);
                returnMapperDTO.setPosition(
                    new Position(
                        childNode.getRange().get().begin.line,
                        childNode.getRange().get().begin.column,
                        childNode.getRange().get().end.line,
                        childNode.getRange().get().end.column));
                returnMapperDTOList.add(returnMapperDTO);
            }
        }

        methodDeclarationDTO.setMethodDeclId(methodDeclarationId);
        methodDeclarationDTO.setBlockId(blockId);
        methodDeclarationDTO.setBelongedClassId(belongedClassId);
        methodDeclarationDTO.setName(methodName);
        methodDeclarationDTO.setModifier(modifierKeyword);
        methodDeclarationDTO.setAccessModifier(accessModifierKeyword);
        // add to methodDeclarationDTO
        methodDeclarationDTO.setReturnMapper(returnMapperDTO);
        methodDeclarationDTO.setParameters(localParameterDTOList);

        methodDeclarationDTO.setNode(node);
        methodDeclarationDTO.setPosition(
            new Position(
                node.getRange().get().begin.line,
                node.getRange().get().begin.column,
                node.getRange().get().end.line,
                node.getRange().get().end.column));

        methodDeclarationDTOList.add(methodDeclarationDTO);
        return methodDeclarationDTO;
    }

    public MethodCallExprDTO buildMethodCallExpr(Long methodCallExprId, Long blockId, Node node,
        String nodeType,
        GeneratorIdentifier generatorIdentifier) {
        MethodCallExprDTO methodCallExprDTO = new MethodCallExprDTO();
        MethodCallExpr methodCallExpr = (MethodCallExpr) node;
        List<Node> childNodes = node.getChildNodes();
        List<ArgumentDTO> localArgumentDTOList = new ArrayList<>();
        Map<String, Long> symbolIds = generatorIdentifier.getSymbolIds();

        String methodName = "";
        int argumentIndex = 1;

        methodName = methodCallExpr.getName().getIdentifier();
        methodCallExpr.getScope()
            .ifPresent((expression -> methodCallExprDTO.setNameExprNode(expression)));

        NodeList<Expression> arguments = methodCallExpr.getArguments();
        for (Expression arg : arguments) {
            ArgumentDTO argumentDTO = new ArgumentDTO();
            argumentDTO.setIndex(argumentIndex++);
            argumentDTO.setName(arg.toString());
            argumentDTO.setArgumentId(symbolIds.get("argument"));
            symbolIds.put("argument", symbolIds.get("argument") + 1);
            argumentDTO.setMethodCallExprId(methodCallExprId);
            // 임시로 Node Type 으로 저장
            argumentDTO.setType(nodeType);
            argumentDTO.setPosition(
                new Position(
                    arg.getRange().get().begin.line,
                    arg.getRange().get().begin.column,
                    arg.getRange().get().end.line,
                    arg.getRange().get().end.column));
            argumentDTOList.add(argumentDTO);
            localArgumentDTOList.add(argumentDTO);
        }
        methodCallExprDTO.setPosition(
            new Position(
                node.getRange().get().begin.line,
                node.getRange().get().begin.column,
                node.getRange().get().end.line,
                node.getRange().get().end.column));

        methodCallExprDTO.setMethodCallExprId(methodCallExprId);
        methodCallExprDTO.setBlockId(blockId);
        methodCallExprDTO.setName(methodName);
        methodCallExprDTO.setArguments(localArgumentDTOList);

        methodCallExprDTOList.add(methodCallExprDTO);
        return methodCallExprDTO;
    }

    public void findVariableDTOForMethodCall() {

    }
}
