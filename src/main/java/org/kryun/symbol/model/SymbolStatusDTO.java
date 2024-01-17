package org.kryun.symbol.model;

import java.sql.Timestamp;
import org.kryun.global.enums.SymbolStatusEnum;

public class SymbolStatusDTO {
    private Long symbolStatusId;
    private Long projId;
    private Long refId;
    private Long commitId;
    private SymbolStatusEnum statusEnum;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public SymbolStatusDTO() {
        this.statusEnum = SymbolStatusEnum.NULL;
    }

    public SymbolStatusDTO(Long projId, Long refId, Long commitId) {
        this.projId = projId;
        this.refId = refId;
        this.commitId = commitId;
    }

    public Long getSymbolStatusId() {
        return symbolStatusId;
    }

    public void setSymbolStatusId(Long symbolStatusId) {
        this.symbolStatusId = symbolStatusId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

    public SymbolStatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(SymbolStatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "SymbolStatusDTO {symbolStatusId=" + symbolStatusId +
                ", projId=" + projId +
                ", refId=" + refId +
                ", commitId=" + commitId +
                ", statusEnum=" + statusEnum +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                "}\n";
    }

}
