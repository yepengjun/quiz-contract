package com.conflux.contract.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
public class QuizRecordInfo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 转账地址
     */
    private String fromAddress;

    /**
     * 目标地址
     */
    private String toAddress;

    /**
     * 金额
     */
    private String amount;

    /**
     * 转账hash
     */
    private String tranHash;

    /**
     * epoch高度
     */
    private Long epochNumber;

    /**
     * 空投hash
     */
    private String quizTranHash;

    /**
     * 空投状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 交易日志
     */
    private String log;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTranHash() {
        return tranHash;
    }

    public void setTranHash(String tranHash) {
        this.tranHash = tranHash;
    }

    public Long getEpochNumber() {
        return epochNumber;
    }

    public void setEpochNumber(Long epochNumber) {
        this.epochNumber = epochNumber;
    }

    public String getQuizTranHash() {
        return quizTranHash;
    }

    public void setQuizTranHash(String quizTranHash) {
        this.quizTranHash = quizTranHash;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        QuizRecordInfo other = (QuizRecordInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFromAddress() == null ? other.getFromAddress() == null : this.getFromAddress().equals(other.getFromAddress()))
            && (this.getToAddress() == null ? other.getToAddress() == null : this.getToAddress().equals(other.getToAddress()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getTranHash() == null ? other.getTranHash() == null : this.getTranHash().equals(other.getTranHash()))
            && (this.getEpochNumber() == null ? other.getEpochNumber() == null : this.getEpochNumber().equals(other.getEpochNumber()))
            && (this.getQuizTranHash() == null ? other.getQuizTranHash() == null : this.getQuizTranHash().equals(other.getQuizTranHash()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatTime() == null ? other.getCreatTime() == null : this.getCreatTime().equals(other.getCreatTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getLog() == null ? other.getLog() == null : this.getLog().equals(other.getLog()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFromAddress() == null) ? 0 : getFromAddress().hashCode());
        result = prime * result + ((getToAddress() == null) ? 0 : getToAddress().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getTranHash() == null) ? 0 : getTranHash().hashCode());
        result = prime * result + ((getEpochNumber() == null) ? 0 : getEpochNumber().hashCode());
        result = prime * result + ((getQuizTranHash() == null) ? 0 : getQuizTranHash().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatTime() == null) ? 0 : getCreatTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getLog() == null) ? 0 : getLog().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromAddress=").append(fromAddress);
        sb.append(", toAddress=").append(toAddress);
        sb.append(", amount=").append(amount);
        sb.append(", tranHash=").append(tranHash);
        sb.append(", epochNumber=").append(epochNumber);
        sb.append(", quizTranHash=").append(quizTranHash);
        sb.append(", status=").append(status);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", log=").append(log);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}