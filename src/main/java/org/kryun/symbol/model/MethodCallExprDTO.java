package org.kryun.symbol.model;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MethodCallExprDTO {
    private String name;
    private Long blockId;
    private Long methodCallExprId;
    private Long fullQualifiedNameId;
    private Position position;
    private Expression nameExprNode;

    private Long nameExprFullQualifiedNameId;
    private List<ArgumentDTO> arguments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFullQualifiedNameId() {
        return fullQualifiedNameId;
    }

    public void setFullQualifiedNameId(Long fullQualifiedNameId) {
        this.fullQualifiedNameId = fullQualifiedNameId;
    }

    public Long getBlockId() {
        return blockId;
    }


    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getMethodCallExprId() {
        return methodCallExprId;
    }


    public void setMethodCallExprId(Long methodCallExprId) {
        this.methodCallExprId = methodCallExprId;
    }
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ArgumentDTO> getArguments() {
        return arguments;
    }

    public void setArguments(List<ArgumentDTO> arguments) {
        this.arguments = arguments;
    }

    /**
     * Todo. methodCall Chaining 이슈 해결
     * @return
     */
    public String getScopeSimpleName() {
        Expression expression = this.getNameExprNode();
        if(expression != null) {
            if (expression instanceof MethodCallExpr) {
                MethodCallExpr scopeMethodCallExpr = (MethodCallExpr) expression;
                return scopeMethodCallExpr.getName().toString();
            }
            return expression.toString();
        }
        return null;
    }

    @Override
    public String toString() {
        return "MethodCallExprDTO : {" +
            "methodCallExprId : " + methodCallExprId +
            ", blockId : " + blockId +
            ", fullQualifiedNameId : " + fullQualifiedNameId +
            ", name : '" + name + '\'' +
            ", arguments : '" + arguments + '\'' +
            ", Position : '" + position +
            ", NameExpr : " + nameExprNode +
            "}\n";
    }


}