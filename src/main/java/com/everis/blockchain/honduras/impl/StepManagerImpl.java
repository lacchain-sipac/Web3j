package com.everis.blockchain.honduras.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.utils.Numeric;

import com.everis.blockchain.honduras.Change;
import com.everis.blockchain.honduras.IStepManager;
import com.everis.blockchain.honduras.Project;
import com.everis.blockchain.honduras.contract.IdentityManager;
import com.everis.blockchain.honduras.contract.StepManager;
import com.everis.blockchain.honduras.util.EthCore;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.everis.blockchain.honduras.util.Utils;

import io.reactivex.annotations.Nullable;

public class StepManagerImpl implements IStepManager {

	private String stepManagerAddress;
    private EthCore ethCore;
    private StepManager stepManagerContract;
    private static final Logger log = LoggerFactory.getLogger(FlowImpl.class);

    public StepManagerImpl(@Nullable String contractAddress, EthCoreParams ethCoreParams) throws Exception {
        ethCore = new EthCore(ethCoreParams);
     
            stepManagerAddress = contractAddress == null ? deployStepManager() : contractAddress;
       
        updateStepManagerContract();
    }

    public String deployStepManager() throws Exception {
        StepManager contract = StepManager
                .deploy(ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider())
                .send();
        return contract.getContractAddress();
    }

    private void updateStepManagerContract() {
        try {
            stepManagerContract = StepManager.load(stepManagerAddress, ethCore.getWeb3jInstance(),
                    ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        } catch (IOException e) {
            log.error("Error.updateStepManagerContract", e);
        }
    }

    public String initProject(String projectCodeHash, String firstStep, String flowContract, String rolesContract, String userRole) throws Exception {
     
            TransactionReceipt transactionReceipt = stepManagerContract.initProject(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(firstStep), flowContract, rolesContract, Utils.stringToBytes32Solidity(userRole)).send();
            return transactionReceipt.getTransactionHash();
       
    }

    public String initProjectIM(String projectCodeHash, String firstStep, String flowContract, String rolesContract, String userRole, String addressUser, String addressIM) throws Exception {
       
            IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
            TransactionReceipt receipt = imContract.forwardTo(addressUser, stepManagerAddress, BigInteger.valueOf(0),
                    getDataInitProject(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(firstStep), flowContract, rolesContract, Utils.stringToBytes32Solidity(userRole)))
                    .send();
            return receipt.getTransactionHash();
       
    }

    public String changeStep(String projectCodeHash, String targetStep, String userRole) throws Exception {
      
            TransactionReceipt transactionReceipt = stepManagerContract
                    .changeStep(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(targetStep),
                            Utils.stringToBytes32Solidity(userRole))
                    .send();
            return transactionReceipt.getTransactionHash();
       
    }

    public String changeStepIM(String projectCodeHash, String targetStep, String userRole, String addressUser, String addressIM) throws Exception {
       
            IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(),
                    ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
            TransactionReceipt receipt = imContract
                    .forwardTo(addressUser, stepManagerAddress, BigInteger.valueOf(0),
                            getDataChangeStep(Utils.getHashInByte(projectCodeHash),
                                    Utils.stringToBytes32Solidity(targetStep), Utils.stringToBytes32Solidity(userRole)))
                    .send();
            return receipt.getTransactionHash();
       
    }

    public String setDocumentType(String projectCodeHash, String documentType, String stepId, String userRole, String accreditDocumentHash, String revokeDocumentHash, String accreditCommentHash, Boolean isFinal) throws Exception {
       
            TransactionReceipt transactionReceipt = stepManagerContract.setDocumentType(
                    Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType),
                    Utils.stringToBytes32Solidity(stepId), Utils.stringToBytes32Solidity(userRole),
                    Utils.getHashInByte(accreditDocumentHash), Utils.getHashInByte(revokeDocumentHash),
                    Utils.getHashInByte(accreditCommentHash), isFinal).send();
            return transactionReceipt.getTransactionHash();
       
    }

    public String setDocumentTypeIM(String projectCodeHash, String documentType, String stepId, String userRole, String accreditDocumentHash, String revokeDocumentHash, String accreditCommentHash, Boolean isFinal, String addressUser, String addressIM) throws Exception {
     
            IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
            TransactionReceipt receipt = imContract.forwardTo(addressUser, stepManagerAddress, BigInteger.valueOf(0),
                    getDataSetDocument(Utils.getHashInByte(projectCodeHash),
                            Utils.stringToBytes32Solidity(documentType), Utils.stringToBytes32Solidity(stepId), Utils.stringToBytes32Solidity(userRole),
                            Utils.getHashInByte(accreditDocumentHash), Utils.getHashInByte(revokeDocumentHash),
                            Utils.getHashInByte(accreditCommentHash), isFinal))
                    .send();
            return receipt.getTransactionHash();
        
    }

    public String unfinalizeDocumentType(String projectCodeHash, String documentType, String userRole) throws Exception {
       
            TransactionReceipt transactionReceipt = stepManagerContract.unfinalizeDocumentType(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType), Utils.stringToBytes32Solidity(userRole)).send();
            return transactionReceipt.getTransactionHash();
      
    }

    public String unfinalizeDocumentTypeIM(String projectCodeHash, String documentType, String userRole, String addressUser, String addressIM) throws Exception {
        
            IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
            TransactionReceipt receipt = imContract.forwardTo(addressUser, stepManagerAddress, BigInteger.valueOf(0),
                    getDataUnfinalizeDocument(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType), Utils.stringToBytes32Solidity(userRole)))
                    .send();
            return receipt.getTransactionHash();
        
    }

    public BigInteger getDocumentTypeCount(String projectCodeHash, String documentType) throws Exception {
        BigInteger total = stepManagerContract.getDocumentTypeCount(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType)).send();
        return total;
    }

    public Change getDocumentType(String projectCodeHash, String documentType, BigInteger index) throws Exception {
        Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger> document = stepManagerContract.getDocumentType(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType), index).send();
        Change change = new Change(Utils.bytes32ToString(document.getValue1()), Utils.bytes32ToString(document.getValue2()), Utils.bytes32ToString(document.getValue3()), document.getValue4(), Utils.bytes32ToString(document.getValue5()), document.getValue6(), document.getValue7());
        return change;
    }

    public Change getDocumentTypeByComment(String projectCodeHash, String documentType, String commentHash) throws Exception {
        Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger> document = stepManagerContract.getDocumentTypeByComment(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType), Utils.getHashInByte(commentHash)).send();
        Change change = new Change(Utils.bytes32ToString(document.getValue1()), Utils.bytes32ToString(document.getValue2()), Utils.bytes32ToString(document.getValue3()), document.getValue4(), Utils.bytes32ToString(document.getValue5()), document.getValue6(), document.getValue7());
        return change;
    }

    public Change getDocumentTypeByDocument(String projectCodeHash, String documentType, String hashDocument) throws Exception {
        Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger> document = stepManagerContract.getDocumentTypeByComment(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(documentType), Utils.getHashInByte(hashDocument)).send();
        Change change = new Change(Utils.bytes32ToString(document.getValue1()), Utils.bytes32ToString(document.getValue2()), Utils.bytes32ToString(document.getValue3()), document.getValue4(), Utils.bytes32ToString(document.getValue5()), document.getValue6(), document.getValue7());
        return change;
    }

    public Boolean isStepCompletedProject(String projectCodeHash, String stepId) throws Exception {
        Boolean isStepComplete = stepManagerContract.isStepCompletedProject(Utils.getHashInByte(projectCodeHash), Utils.stringToBytes32Solidity(stepId)).send();
        return isStepComplete;
    }

    public Project getProject(String projectCodeHash) throws Exception {
        Tuple6<String, String, Boolean, Boolean, byte[], String> project = stepManagerContract.projects(Utils.getHashInByte(projectCodeHash)).send();
        Project projectObj = new Project(project.getValue1(), project.getValue2(), project.getValue3(), project.getValue4(), Utils.bytes32ToString(project.getValue5()), project.getValue6());
        return projectObj;
    }

    public Boolean hasValidDocuments(String projectCodeHash) throws Exception {
        Boolean hasValidDocuments = stepManagerContract.hasValidDocuments(Utils.getHashInByte(projectCodeHash)).send();
        return hasValidDocuments;
    }

    private byte[] getDataInitProject(byte[] projectCodeHash, byte[] firstStep, String flowContract, String rolesContract, byte[] userRole) {
        Function function = new Function(
            "initProject", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCodeHash), 
            new org.web3j.abi.datatypes.generated.Bytes32(firstStep), 
            new org.web3j.abi.datatypes.Address(160, flowContract), 
            new org.web3j.abi.datatypes.Address(160, rolesContract), 
            new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

    private byte[] getDataChangeStep(byte[] projectCodeHash, byte[] targetStep, byte[] userRole) {
        Function function = new Function("changeStep",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCodeHash),
                        new org.web3j.abi.datatypes.generated.Bytes32(targetStep),
                        new org.web3j.abi.datatypes.generated.Bytes32(userRole)),
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

    private byte[] getDataSetDocument(byte[] projectCodeHash, byte[] documentType, byte[] stepId, byte[] _userRole, byte[] accreditDocumentHash, byte[] revokeDocumentHash, byte[] accreditCommentHash, Boolean _isFinal) {
        Function function = new Function(
            "setDocumentType", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCodeHash), 
            new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
            new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
            new org.web3j.abi.datatypes.generated.Bytes32(_userRole), 
            new org.web3j.abi.datatypes.generated.Bytes32(accreditDocumentHash), 
            new org.web3j.abi.datatypes.generated.Bytes32(revokeDocumentHash), 
            new org.web3j.abi.datatypes.generated.Bytes32(accreditCommentHash), 
            new org.web3j.abi.datatypes.Bool(_isFinal)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

    private byte[] getDataUnfinalizeDocument(byte[] projectCodeHash, byte[] documentType, byte[] userRole) {
        Function function = new Function(
            "unfinalizeDocumentType", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCodeHash), 
            new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
            new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

}