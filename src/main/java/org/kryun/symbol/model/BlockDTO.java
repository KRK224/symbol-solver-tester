package org.kryun.symbol.model;

import com.github.javaparser.ast.Node;

public class BlockDTO {
    private Long blockId;
    private Long parentBlockId;
    private Long symbolReferenceId;
    private Integer depth;
    private String blockType;
    private Node node;
    private Position position;
    private Position bracketPosition;

    public Long getSymbolReferenceId() {
        return symbolReferenceId;
    }

    public void setSymbolReferenceId(Long symbolReferenceId) {
        this.symbolReferenceId = symbolReferenceId;
    }

    public Long getBlockId() {
        return blockId;
    }


    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getParentBlockId() {
        return parentBlockId;
    }

    public void setParentBlockId(Long parentBlockId) {
        this.parentBlockId = parentBlockId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getBracketPosition() {
        return bracketPosition;
    }

    public void setBracketPosition(Position bracketPosition) {
        this.bracketPosition = bracketPosition;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }


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
