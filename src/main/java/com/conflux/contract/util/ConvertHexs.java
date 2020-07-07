package com.conflux.contract.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author xiaolong
 * @description:
 * @date 2020-07-04-21:54
 */
public class ConvertHexs {


    /**
     * @param: [content]
     * @return: int
     * @description: 十六进制转十进制
     */
    public static BigInteger covertBigInteger(String hex){
        if (StringUtils.isEmpty(hex)) {
            return BigInteger.ZERO;
        }
        BigInteger amount = new BigInteger(hex.substring(2), 16);
        return amount;
    }

    /**
     * @param: [content]
     * @return: int
     * @description: 十六进制转十进制
     */
    public static BigDecimal covertBigDecimal(String hex){
        if (StringUtils.isEmpty(hex)) {
            return BigDecimal.ZERO;
        }
        // 18位小数
        int precision = 18;
        BigDecimal decimal = new BigDecimal(new BigInteger(hex.substring(2), 16));
        BigDecimal precisionDecimal = new BigDecimal(10).pow(precision);
        BigDecimal value = decimal.divide(precisionDecimal).setScale(precision, RoundingMode.DOWN);
        return value;
    }


    //测试程序
    public static void main(String... args) {

        String hex = "0x00000000000000000000000000000000000000000000002be4fb8c854544b555";
        System.out.println(covertBigDecimal(hex));
    }

}
