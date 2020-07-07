package com.conflux.contract.dao;

import com.conflux.contract.entity.QuizRecordInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * QuizRecordInfoDao继承基类
 */
@Repository
public interface QuizRecordInfoDao extends MyBatisBaseDao<QuizRecordInfo, Long> {


    /**
     * 根据交易hash获取空头记录
     * @param transHash
     * @return
     */
    QuizRecordInfo getQuizRecordInfoByTranHash (@Param("transHash") String transHash);
}