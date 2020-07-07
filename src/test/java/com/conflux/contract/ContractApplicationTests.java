package com.conflux.contract;

import com.alibaba.fastjson.JSON;
import com.conflux.contract.dao.QuizRecordInfoDao;
import com.conflux.contract.entity.QuizRecordInfo;
import com.conflux.contract.monitor.ContractLogMonitorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@SpringBootTest
class ContractApplicationTests {

    @Resource
    private ContractLogMonitorService contractLogMonitorService;
    @Resource
    private QuizRecordInfoDao quizRecordInfoDao;

    @Test
    void monitorContract() {

        contractLogMonitorService.monitorNftLog();
        System.out.println("end");
    }

    @Test
    @Transactional
    void insertContract() {

        QuizRecordInfo quizRecordInfo = new QuizRecordInfo();
        quizRecordInfo.setAmount("122222222");
        quizRecordInfo.setEpochNumber(6378615L);
        quizRecordInfo.setFromAddress("1223333333");
        quizRecordInfo.setToAddress("2122121122");
        quizRecordInfo.setLog(JSON.toJSONString("2222222"));
        quizRecordInfo.setTranHash("12222222");
        quizRecordInfoDao.insertSelective(quizRecordInfo);
        QuizRecordInfo quizRecordInfoUpdate = new QuizRecordInfo();
        quizRecordInfoUpdate.setId(quizRecordInfo.getId());
        quizRecordInfoUpdate.setQuizTranHash("2222222222222");
        quizRecordInfoUpdate.setStatus(1);
        quizRecordInfoDao.updateByPrimaryKeySelective(quizRecordInfoUpdate);
    }

}
