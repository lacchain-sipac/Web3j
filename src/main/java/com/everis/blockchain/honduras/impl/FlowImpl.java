package com.everis.blockchain.honduras.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tuples.generated.Tuple2;

import com.everis.blockchain.honduras.IFlow;
import com.everis.blockchain.honduras.contract.Flow;
import com.everis.blockchain.honduras.util.EthCore;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.everis.blockchain.honduras.util.Utils;

import io.reactivex.annotations.Nullable;

public class FlowImpl implements IFlow {

	private String flowAddress;
    private EthCore ethCore;
    private Flow flowContract;
    private static final Logger log = LoggerFactory.getLogger(FlowImpl.class);

    public FlowImpl(@Nullable String contractAddress, EthCoreParams ethCoreParams) throws Exception {
        ethCore = new EthCore(ethCoreParams);
   
            flowAddress = contractAddress == null ? deployFlow() : contractAddress;
      
        updateFlowContract();
    }

    public String deployFlow() throws Exception {
        Flow contract = Flow
                .deploy(ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider())
                .send();
        return contract.getContractAddress();
    }

    private void updateFlowContract() {
        try {
            flowContract = Flow.load(flowAddress, ethCore.getWeb3jInstance(), ethCore.getCredentials(),
                    ethCore.getDefaultContractGasProvider());
        } catch (IOException e) {
            log.error("Error.updateFlowContract", e);
        }
    }

    public String addStep(String stepId, String[] mandatoryDocumentKeys, String[] optionalDocumentKeys,
            String[] authorizedRoles, String[] previousValidSteps, String[] projectSpecificRoles, Boolean isFinalStep,
            Boolean isInitialStep) throws Exception {
        try {
            TransactionReceipt transactionReceipt = flowContract
                    .addStep(Utils.stringToBytes32Solidity(stepId),
                            Utils.arrayStringToListBytes32Solidity(mandatoryDocumentKeys),
                            Utils.arrayStringToListBytes32Solidity(optionalDocumentKeys),
                            Utils.arrayStringToListBytes32Solidity(authorizedRoles),
                            Utils.arrayStringToListBytes32Solidity(previousValidSteps),
                            Utils.arrayStringToListBytes32Solidity(projectSpecificRoles), isFinalStep, isInitialStep)
                    .send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.addStep", e);
            throw e;
        }
    }

    public String setStatusAuthorizedRolesStep(String step, String[] roles, Boolean status) throws Exception {
        try {
            TransactionReceipt transactionReceipt = flowContract.setStatusAuthorizedRolesStep(
                    Utils.stringToBytes32Solidity(step), Utils.arrayStringToListBytes32Solidity(roles), status).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.setStatusAuthorizedRolesStep", e);
            throw e;
        }
    }

    public String setPreviousValidStep(String step, String[] previousValidSteps, Boolean status) throws Exception {
        try {
            TransactionReceipt transactionReceipt = flowContract
                    .setPreviousValidStep(Utils.stringToBytes32Solidity(step),
                            Utils.arrayStringToListBytes32Solidity(previousValidSteps), status)
                    .send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.setPreviousValidStep", e);
            throw e;
        }
    }

    public String setProjectSpecificRoles(String stepId, String[] projectSpecificRoles, Boolean status) throws Exception {
        try {
            TransactionReceipt transactionReceipt = flowContract
                    .setProjectSpecificRoles(Utils.stringToBytes32Solidity(stepId),
                            Utils.arrayStringToListBytes32Solidity(projectSpecificRoles), status)
                    .send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.setProjectSpecificRoles", e);
            throw e;
        }
    }

    public String setStatusAuthorizedDocumentRole(String step, String documentType, String[] userRoles, Boolean status)
            throws Exception {
        try {
            TransactionReceipt transactionReceipt = flowContract.setStatusAuthorizedDocumentRole(
                    Utils.stringToBytes32Solidity(step), Utils.stringToBytes32Solidity(documentType),
                    Utils.arrayStringToListBytes32Solidity(userRoles), status).send();
            return transactionReceipt.getTransactionHash();
        } catch (TransactionException e) {
            log.error("Error.setStatusAuthorizedDocumentUser", e);
            throw e;
        }
    }

    public Authorization documentRoleAuthorization(String step, String documentType, String userRole) throws Exception {
        Tuple2<Boolean, Boolean> isDocumentRoleAuthorized = flowContract
                .documentRoleAuthorization(Utils.stringToBytes32Solidity(step),
                Utils.stringToBytes32Solidity(documentType), Utils.stringToBytes32Solidity(userRole)).send();
        Authorization authorization = new Authorization(isDocumentRoleAuthorized.getValue1(), isDocumentRoleAuthorized.getValue2());
        return authorization;
    }

    public Authorization roleAuthorization(String targetStep, String userRole) throws Exception {
        Tuple2<Boolean, Boolean> isRoleAuthorized = flowContract
                .roleAuthorization(Utils.stringToBytes32Solidity(targetStep), Utils.stringToBytes32Solidity(userRole))
                .send();
        Authorization authorization = new Authorization(isRoleAuthorized.getValue1(), isRoleAuthorized.getValue2());
        return authorization;
    }

    public Boolean isFinalStep(String stepId) throws Exception {
        Boolean isFinalStep = flowContract.isFinalStep(Utils.stringToBytes32Solidity(stepId)).send();
        return isFinalStep;
    }

    public Boolean isInitialStep(String stepId) throws Exception {
        Boolean isInitialStep = flowContract.isInitialStep(Utils.stringToBytes32Solidity(stepId)).send();
        return isInitialStep;
    }

    public Boolean isProjectSpecificRole(String targetStep, String userRole) throws Exception {
        Boolean isProjectSpecificRole = flowContract.isProjectSpecificRole(Utils.stringToBytes32Solidity(targetStep), Utils.stringToBytes32Solidity(userRole)).send();
        return isProjectSpecificRole;
    }

    public ArrayList<String> getMandatorydocumentKeysStep(String stepId) throws Exception {
        List documentKeys = flowContract.getMandatorydocumentKeysStep(Utils.stringToBytes32Solidity(stepId)).send();
        return Utils.arrayBytes32ToString(documentKeys);
    }

    public ArrayList<String> getOptionaldocumentKeysStep(String stepId) throws Exception {
        List documentKeys = flowContract.getOptionaldocumentKeysStep(Utils.stringToBytes32Solidity(stepId)).send();
        return Utils.arrayBytes32ToString(documentKeys);
    }

    public Boolean isValidStepChange(String currentStep, String targetStep) throws Exception {
        Boolean isValidStepChange = flowContract.isValidStepChange(Utils.stringToBytes32Solidity(currentStep), Utils.stringToBytes32Solidity(targetStep)).send();
        return isValidStepChange;
    }

    public Boolean existsDocumentKeyInStep(String stepId, String documentType) throws Exception {
        Boolean existsDocumentKeyInStep = flowContract.existsDocumentKeyInStep(Utils.stringToBytes32Solidity(stepId), Utils.stringToBytes32Solidity(documentType)).send();
        return existsDocumentKeyInStep;
    }

}