package org.kryun.symbol.model;

public class AssignExprDTO {
    private Long assignExprId;
    private String target;
    private String value;
    private Long blockId;
    private Position position;

    public Long getAssignExprId() {
        return assignExprId;
    }


    public void setAssignExprId(Long assignExprId) {
        this.assignExprId = assignExprId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "AssignExprDTO : {" +
                "assignExprId : " + assignExprId +
                ", blockId : " + blockId +
                ", target : " + target +
                ", value : " + value +
                ", Position : '" + position + '\'' +
                "}\n";
    }
}
