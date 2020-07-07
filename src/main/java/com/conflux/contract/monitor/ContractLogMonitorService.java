package com.conflux.contract.monitor;

import com.alibaba.fastjson.JSON;
import com.conflux.contract.ErcGcExecutor;
import com.conflux.contract.dao.QuizLastEpochInfoDao;
import com.conflux.contract.dao.QuizRecordInfoDao;
import com.conflux.contract.entity.QuizLastEpochInfo;
import com.conflux.contract.entity.QuizRecordInfo;
import com.conflux.contract.enums.QuizStatusEnum;
import com.conflux.contract.util.ConvertHexs;
import org.conflux.web3j.Account;
import org.conflux.web3j.Cfx;
import org.conflux.web3j.Request;
import org.conflux.web3j.RpcException;
import org.conflux.web3j.request.Epoch;
import org.conflux.web3j.request.LogFilter;
import org.conflux.web3j.response.BigIntResponse;
import org.conflux.web3j.response.Log;
import org.conflux.web3j.response.LogsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.abi.*;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaolong
 * @description:
 * @date 2020-07-04-14:57
 */
@Service
public class ContractLogMonitorService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private QuizRecordInfoDao quizRecordInfoDao;
    @Resource
    private QuizLastEpochInfoDao quizLastEpochInfoDao;

    private static Cfx cfx = Cfx.create("http://mainnet-jsonrpc.conflux-chain.org:12537");
    private static String privateKey = "0xEF7D9A15879D6941B51E19AAC2E15AEA72A93337F92CEF32FAAD677DC849A89F";
    private static String gcContractAddress = "0x8ad759dd30a5ca03e29b5bdab423560c30394783";
    private static String fcContractAddress = "0x88a8f9b1835ae66b6f1da3c930b7d11220bebf78";
    private static Account account = Account.create(cfx,privateKey);
    private static Long lastMonitorLogNum = 6308615L;

    /**
     * 监听押注日志、并空投代币
     */
    public void monitorNftLog() {
        QuizLastEpochInfo quizLastEpochInfo = quizLastEpochInfoDao.getQuizLastEpochInfoByAddress(gcContractAddress);
        if (quizLastEpochInfo == null) {
            quizLastEpochInfo = new QuizLastEpochInfo();
            quizLastEpochInfo.setContractAddress(gcContractAddress);
            quizLastEpochInfo.setEpochNumber(lastMonitorLogNum);
            quizLastEpochInfoDao.insertSelective(quizLastEpochInfo);
        }else {
            lastMonitorLogNum = quizLastEpochInfo.getEpochNumber()+1;
        }
        ErcGcExecutor ercgcExecutor = new ErcGcExecutor(account,gcContractAddress);
        //event Transfer(address indexed from, address indexed to, uint256 value);
        List<TypeReference<?>> transactionEventType = Arrays.asList(
                TypeReference.create(Address.class),
                TypeReference.create(Address.class),
                TypeReference.create(Uint256.class));
        Event transactionEvent = new Event("Transfer", transactionEventType);
        String transactionEventEncode = EventEncoder.encode(transactionEvent);
        LogFilter logFilter = new LogFilter();
        List<List<String>> topics = Arrays.asList(Arrays.asList(transactionEventEncode));
        logFilter.setTopics(topics);
        logFilter.setAddress(Arrays.asList(fcContractAddress));
        List<TypeReference<?>> addressEventType = Arrays.asList(TypeReference.create(Address.class));
        List<TypeReference<Type>> convert = Utils.convert(addressEventType);
        try {
            Request<BigInteger, BigIntResponse> epochNumber = cfx.getEpochNumber();
            BigInteger current = epochNumber.sendAndGet();
            if (lastMonitorLogNum.compareTo(current.longValue()) >= 0) {
                return;
            }
            Long minInterval = 200L;
            if (current.longValue() - lastMonitorLogNum > minInterval) {
                current = BigInteger.valueOf(lastMonitorLogNum + minInterval);
            }
            logFilter.setFromEpoch(Epoch.numberOf(BigInteger.valueOf(lastMonitorLogNum)));
            logFilter.setToEpoch(Epoch.numberOf(current));

            Request<List<Log>, LogsResponse> logs = cfx.getLogs(logFilter);
            List<Log> logList = logs.sendAndGet();
            logger.info("logList:{}",JSON.toJSONString(logList));
            if (!CollectionUtils.isEmpty(logList)) {
                for (int i = 0; i < logList.size(); i++) {
                    Log log = logList.get(i);
                    logger.info("空投开始日志为log:{}",JSON.toJSONString(log));
                    List<Type> toAddressDecode = FunctionReturnDecoder.decode(log.getTopics().get(2), convert);
                    String toAddress = toAddressDecode.get(0).toString();
                    if (gcContractAddress.equals(toAddress)) {
                        String data = log.getData();
                        BigInteger amount = ConvertHexs.covertBigInteger(data);
                        String transactionHash = log.getTransactionHash().get();
                        QuizRecordInfo quizRecordInfo = quizRecordInfoDao.getQuizRecordInfoByTranHash(transactionHash);
                        if (quizRecordInfo == null) {
                            Long currEpochNumber = log.getEpochNumber().get().longValue();
                            quizRecordInfo = new QuizRecordInfo();
                            quizRecordInfo.setAmount(amount.toString());
                            quizRecordInfo.setEpochNumber(currEpochNumber);
                            quizRecordInfo.setFromAddress(log.getAddress());
                            quizRecordInfo.setToAddress(toAddress);
                            quizRecordInfo.setLog(JSON.toJSONString(log));
                            quizRecordInfo.setTranHash(log.getTransactionHash().get());
                            quizRecordInfo.setStatus(QuizStatusEnum.PROCESS.getCode());
                            quizRecordInfoDao.insertSelective(quizRecordInfo);
                        }
                        if (QuizStatusEnum.DONE.getCode().equals(quizRecordInfo.getStatus())) {
                            continue;
                        }
                        String transfer = ercgcExecutor.transfer(BigInteger.valueOf(10000000), toAddress, amount,transactionHash);
                        QuizRecordInfo quizRecordInfoUpdate = new QuizRecordInfo();
                        quizRecordInfoUpdate.setId(quizRecordInfo.getId());
                        quizRecordInfoUpdate.setQuizTranHash(transfer);
                        quizRecordInfoUpdate.setStatus(QuizStatusEnum.DONE.getCode());
                        quizRecordInfoDao.updateByPrimaryKeySelective(quizRecordInfoUpdate);
                        logger.info("空投头结束成功的交易哈希 transfer:{}",transfer);
                    }
                }
            }
            quizLastEpochInfo.setEpochNumber(current.longValue());
            quizLastEpochInfoDao.updateByPrimaryKeySelective(quizLastEpochInfo);
        }catch (RpcException rp) {
            logger.error("monitorNftLog RpcException ex:{}",rp);
        }catch (Exception ex) {
            logger.error("monitorNftLog exception ex:{}",ex);
        }
    }

    public static void main(String[] args) {
        List<TypeReference<?>> transactionEventType = Arrays.asList(TypeReference.create(Address.class));
        List<TypeReference<Type>> convert = Utils.convert(transactionEventType);

        List<Type> decode1 = FunctionReturnDecoder.decode("0x000000000000000000000000164eddb6d5dd633e29e927147974606b2b5e66af", convert);
        List<Type> decode2 = FunctionReturnDecoder.decode("0x0000000000000000000000001ce1283b59e7b3e17b6289c53bd6184c2c8cff5b", convert);
        System.out.println(decode1.get(0));
        System.out.println(decode2.get(0));
        String encode = "0x00000000000000000000000000000000000000000000002be4fb8c854544b555";
        BigDecimal x = ConvertHexs.covertBigDecimal(encode);
        System.out.println(x);

    }
}
