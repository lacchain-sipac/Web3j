package com.everis.blockchain.honduras.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import com.everis.blockchain.honduras.IOwner;
import com.everis.blockchain.honduras.contract.Flow;
import com.everis.blockchain.honduras.contract.IdentityManager;
import com.everis.blockchain.honduras.util.EthCore;
import com.everis.blockchain.honduras.util.EthCoreParams;

import io.reactivex.annotations.Nullable;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OwnerImpl implements IOwner {

	private String flowAddress;
    private EthCore ethCore;
    private Flow flowContract;

    public OwnerImpl(EthCoreParams ethCoreParams) {
        ethCore = new EthCore(ethCoreParams);
    }

    public String addOwner(String addressContract, String newOwner, Boolean isThroughIM, @Nullable String addressUser, @Nullable String addressIM) throws Exception {
        return addOwnerFlow(addressContract, newOwner, isThroughIM, addressUser, addressIM);
    }

    public String renounce(String addressContract, Boolean isThroughIM, @Nullable String addressUser, @Nullable String addressIM) throws Exception {
        return renounceFlow(addressContract, isThroughIM, addressUser, addressIM);
    }

    public Boolean isOwner(String addressContract, String addressUser) throws Exception {
        return isOwnerFlow(addressContract, addressUser);
    }

    private String renounceFlow(String addressContract, Boolean isThroughIM, String addressUser, String addressIM) throws Exception {
        TransactionReceipt receipt;
        if (isThroughIM) {
            if (!addressUser.isEmpty() && !addressIM.isEmpty()) throw new Error("required parameters: addressUser, addressIM");
            IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
            receipt = imContract.forwardTo(addressUser, addressContract, BigInteger.valueOf(0), getDataRenounce()).send();
        } else {
            Flow contract = getContractFlow(addressContract);
            receipt = contract.renounce().send();
        }
        return receipt.getTransactionHash();
    }

    private String addOwnerFlow(String addressContract, String newOwner, Boolean isThroughIM, String addressUser, String addressIM) throws Exception {
        TransactionReceipt receipt;
        if (isThroughIM) {
            if (!addressUser.isEmpty() && !addressIM.isEmpty()) throw new Error("required parameters: addressUser, addressIM");
            IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
            receipt = imContract.forwardTo(addressUser, addressContract, BigInteger.valueOf(0), getDataAddOnwer(newOwner)).send();
        } else {
            Flow contract = getContractFlow(addressContract);
            receipt = contract.addOwner(newOwner).send();
        }
        return receipt.getTransactionHash();
    }

    private byte[] getDataAddOnwer(String newOwner) {
        Function function = new Function("addOwner", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }
    
    private byte[] getDataRenounce() {
        Function function = new Function(
            "renounce", 
            Arrays.<Type>asList(), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

    private Boolean isOwnerFlow(String addressContract, String addressUser) throws Exception {
        Flow contract = getContractFlow(addressContract);
        return contract.isOwner(addressUser).send();
    }

    private Flow getContractFlow(String address) throws IOException {
        return Flow.load(address, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
    }

}