package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockDTO {
    private Long blockId;
    private Long parentBlockId;
    private Long symbolReferenceId;
    private Integer depth;
    private String blockType;
    private Node node;
    private Position position;
    private Position bracketPosition;
    @Override
    public String toString() {
        return "BlockDTO : {" +
                "blockId : " + blockId +
                ", parentBlockId : " + parentBlockId +
                ", depth : " + depth +
                ", blockType : '" + blockType + '\'' +
                // ", srcPath : '" + srcPath + "\"" +
                ", Position : '" + position + '\'' +
                // "}\n";
                // ", \nblockNode : \n" + blockNode +
                ", symbolReferenceId : " + symbolReferenceId +
                "}\n";
    }
}
