package org.kryun.symbol.model;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.kryun.symbol.model.interfaces.NameExprFQNReferable;

@Getter
@Setter
public class MethodCallExprDTO implements NameExprFQNReferable {
    private String name;
    private Long blockId;
    private Long methodCallExprId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private Position position;
    private Expression nameExprNode;

    private Long nameExprFullQualifiedNameId;
    private Boolean isNameExprFullQualifiedNameIdFromDB = false;
    private List<ArgumentDTO> arguments;


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