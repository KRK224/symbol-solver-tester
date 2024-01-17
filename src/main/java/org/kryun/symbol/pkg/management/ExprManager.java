package org.kryun.symbol.pkg.management;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.SimpleName;
import java.util.ArrayList;
import java.util.List;
import org.kryun.symbol.model.AssignExprDTO;
import org.kryun.symbol.model.Position;

public class ExprManager {
    private final List<AssignExprDTO> assignExprDTOList;

    public ExprManager() {
        this.assignExprDTOList = new ArrayList<>();
    }

    public List<AssignExprDTO> getAssignExprDTOList() {
        return this.assignExprDTOList;
    }

    public void assignExprDTOListClear() {
        this.assignExprDTOList.clear();
    }

    public void buildAssignExpr(Long assignExprId, Long blockId, Node node) {
        AssignExprDTO assignExprDTO = new AssignExprDTO();
        List<Node> childNodes = node.getChildNodes();
        AssignExpr assignExpr = (AssignExpr) node;

        String target = null;
        String value = null;

        // target name 찾기
        for (Node child : assignExpr.getTarget().getChildNodes()) {
            if (child.getMetaModel().getTypeName().equals("SimpleName")) {
                SimpleName simpleName = (SimpleName) child;
                target = simpleName.asString();
            }
        }

        // value name 찾기
        for (Node child : assignExpr.getValue().getChildNodes()) {
            if (child.getMetaModel().getTypeName().equals("SimpleName")) {
                SimpleName simpleName = (SimpleName) child;
                value = simpleName.asString();
            }
        }
        assignExprDTO.setPosition(
                new Position(
                        node.getRange().get().begin.line,
                        node.getRange().get().begin.column,
                        node.getRange().get().end.line,
                        node.getRange().get().end.column));
        assignExprDTO.setAssignExprId(assignExprId);
        assignExprDTO.setBlockId(blockId);
        assignExprDTO.setTarget(target);
        assignExprDTO.setValue(value);

        assignExprDTOList.add(assignExprDTO);

        return;
    }
}
