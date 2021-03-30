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
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Numeric;

import com.everis.blockchain.honduras.IRoles;
import com.everis.blockchain.honduras.contract.IdentityManager;
import com.everis.blockchain.honduras.contract.Roles;
import com.everis.blockchain.honduras.util.EthCore;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.everis.blockchain.honduras.util.Utils;

import io.reactivex.annotations.Nullable;

public class RolesImpl implements IRoles {

	private String rolesAddress;
    private EthCore ethCore;
    private Roles rolesContract;
    private static final Logger log = LoggerFactory.getLogger(RolesImpl.class);

    public RolesImpl(@Nullable String contractAddress, @Nullable String ownerRole, String projectOwnerRole, EthCoreParams ethCoreParams) throws Exception {
        ethCore = new EthCore(ethCoreParams);

            rolesAddress = contractAddress == null ? deployRoles(ownerRole, projectOwnerRole) : contractAddress;
      
        updateRolesContract();
    }

    public String deployRoles(String ownerRole, String projectOwnerRole) throws Exception {
        Roles contract = Roles.deploy(ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider(), Utils.stringToBytes32Solidity(ownerRole), Utils.stringToBytes32Solidity(projectOwnerRole)).send();
        return contract.getContractAddress();
    }

    private void updateRolesContract() {
        try {
            rolesContract = Roles.load(rolesAddress, ethCore.getWeb3jInstance(), ethCore.getCredentials(),
                    ethCore.getDefaultContractGasProvider());
        } catch (IOException e) {
            log.error("Error.updateRolesContract", e);
        }
    }

    public String setRoleUser(String role, String user) throws Exception {
        try {
            TransactionReceipt transactionReceipt = rolesContract.setRoleUser(Utils.stringToBytes32Solidity(role), user).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.setRoleUser", e);
            throw e;
        }
    }

    public String setRoleUser(String role, String user, String projectCodeHash) throws Exception {
        try {
            TransactionReceipt transactionReceipt = rolesContract.setRoleUser(Utils.stringToBytes32Solidity(role), user, Utils.getHashInByte(projectCodeHash)).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.setRoleUserProject", e);
            throw e;
        }
    }

    public String setRoleUserIM(String role, String user, String addressUser, String addressIM) throws Exception {
        IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        TransactionReceipt receipt = imContract.forwardTo(addressUser, rolesAddress, BigInteger.valueOf(0), getDataSetRoleUser(Utils.stringToBytes32Solidity(role), user)).send();
        return receipt.getTransactionHash();
    }
    
    public String setRoleUserIM(String role, String user, String projectCodeHash, String addressUser, String addressIM) throws Exception {
        IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        TransactionReceipt receipt = imContract.forwardTo(addressUser, rolesAddress, BigInteger.valueOf(0), getDataSetRoleUser(Utils.stringToBytes32Solidity(role), user, Utils.getHashInByte(projectCodeHash))).send();
        return receipt.getTransactionHash();
    }

    public String removeRoleUser(String role, String user) throws Exception {
        try {
            TransactionReceipt transactionReceipt = rolesContract.removeRoleUser(Utils.stringToBytes32Solidity(role), user).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.removeRoleUser", e);
            throw e;
        }
    }
    
    public String removeRoleUser(String role, String user, String projectCodeHash) throws Exception {
        try {
            TransactionReceipt transactionReceipt = rolesContract.removeRoleUser(Utils.stringToBytes32Solidity(role), user, Utils.getHashInByte(projectCodeHash)).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.removeRoleUser", e);
            throw e;
        }
    }

    public String removeRoleUserIM(String role, String user, String addressUser, String addressIM) throws Exception {
        IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        TransactionReceipt receipt = imContract.forwardTo(addressUser, rolesAddress, BigInteger.valueOf(0), getDataRemoveRoleUser(Utils.stringToBytes32Solidity(role), user)).send();
        return receipt.getTransactionHash();
    }
    
    public String removeRoleUserIM(String role, String user, String projectCodeHash, String addressUser, String addressIM) throws Exception {
        IdentityManager imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider());
        TransactionReceipt receipt = imContract.forwardTo(addressUser, rolesAddress, BigInteger.valueOf(0), getDataRemoveRoleUser(Utils.stringToBytes32Solidity(role), user, Utils.getHashInByte(projectCodeHash))).send();
        return receipt.getTransactionHash();
    }

    public Boolean hasRoleUser(String role, String user) throws Exception {
        Boolean hasRole = rolesContract.hasRoleUser(Utils.stringToBytes32Solidity(role), user).send();
        return hasRole;
    }
    
    public Boolean hasRoleUser(String role, String user, String projectCodeHash) throws Exception {
        Boolean hasRole = rolesContract.hasRoleUser(Utils.stringToBytes32Solidity(role), user, Utils.getHashInByte(projectCodeHash)).send();
        return hasRole;
    }

    private byte[] getDataRemoveRoleUser(byte[] role, String user) {
        Function function = new Function(
            "removeRoleUser", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
            new org.web3j.abi.datatypes.Address(160, user)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }
    
    private byte[] getDataRemoveRoleUser(byte[] role, String user, byte[] projectCodeHash) {
        Function function = new Function(
            "removeRoleUser", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
            new org.web3j.abi.datatypes.Address(160, user), 
            new org.web3j.abi.datatypes.generated.Bytes32(projectCodeHash)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

    private byte[] getDataSetRoleUser(byte[] role, String user) {
        Function function = new Function(
            "setRoleUser", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
            new org.web3j.abi.datatypes.Address(160, user)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }
    
    private byte[] getDataSetRoleUser(byte[] role, String user, byte[] projectCodeHash) {
        Function function = new Function(
            "setRoleUser", 
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
            new org.web3j.abi.datatypes.Address(160, user), 
            new org.web3j.abi.datatypes.generated.Bytes32(projectCodeHash)), 
            Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
        return dataBytes;
    }

}