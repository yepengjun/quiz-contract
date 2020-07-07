package com.conflux.contract.dao;

import com.conflux.contract.entity.QuizLastEpochInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * QuizLastEpochInfoDAO继承基类
 */
@Repository
public interface QuizLastEpochInfoDao extends MyBatisBaseDao<QuizLastEpochInfo, Long> {


    /**
     * 查询合约当前空投的高度
     * @param contractAddress
     * @return
     */
    QuizLastEpochInfo getQuizLastEpochInfoByAddress (@Param("contractAddress") String contractAddress);
}

