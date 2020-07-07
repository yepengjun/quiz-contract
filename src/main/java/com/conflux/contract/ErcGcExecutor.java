package com.conflux.contract;

import org.conflux.web3j.Account;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

/**
 * @author xiaolong
 * @description:
 * @date 2020-07-05-03:55
 */
public class ErcGcExecutor {

    private Account account;
    private String contract;

    public ErcGcExecutor(Account account, String ercGcAddress) {
        this.account = account;
        this.contract = ercGcAddress;
    }

    public String transfer(BigInteger gasLimit, String recipient, BigInteger amount,String transactionHash) throws Exception {
        return this.account.call(this.contract, gasLimit, "airdrop", new Address(recipient), new Uint256(amount),new Utf8String(transactionHash));
    }
}
