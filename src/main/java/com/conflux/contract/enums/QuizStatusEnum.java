package com.conflux.contract.enums;

/**
 * @author xiaolong
 * @description:
 * @date 2020-07-07-10:28
 */
public enum QuizStatusEnum {

    PROCESS(0, "未空投"),
    DONE(1, "已空投"),
    ;

    QuizStatusEnum(Integer code, String note) {
        this.code = code;
        this.note = note;
    }

    private Integer code;

    private String note;

    public Integer getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
