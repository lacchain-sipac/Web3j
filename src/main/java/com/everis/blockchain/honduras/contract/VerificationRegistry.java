package com.everis.blockchain.honduras.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import com.everis.blockchain.honduras.util.ContractFast;
import com.everis.blockchain.honduras.util.Web3;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.11.
 */
@SuppressWarnings("rawtypes")
public class VerificationRegistry extends ContractFast {
    public static final String BINARY = "608060405234801561001057600080fd5b5061022e806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063382262fc1461004657806395f5114c1461006b578063b75c7dc6146100b0575b600080fd5b6100696004803603604081101561005c57600080fd5b50803590602001356100cd565b005b6100976004803603604081101561008157600080fd5b50803590602001356001600160a01b0316610164565b6040805192835260208301919091528051918290030190f35b610069600480360360208110156100c657600080fd5b5035610185565b60008082116100dd5760006100e6565b81620151800242015b604080518082018252428082526020808301858152600089815280835285812033808352908452908690209451855590516001909401939093558351928352820152808201839052905191925084917fef081e4d12d9d2377ab4c9af5421f0ce40d78d2031cb2cccd1c23eb0a3c6ecce9181900360600190a2505050565b60006020818152928152604080822090935290815220805460019091015482565b604080518082018252600080825242602080840182815286845283825285842033808652908352938690209451855551600190940193909355835191825291810191909152815183927fbad7982cc5cd1310cfc1c83b1e7e9bc771332036ea180c362910e4068fc5ecf6928290030190a25056fea265627a7a72315820e866b0a3a76ee7bd64d8cb5b120d6a9f547232e92f1b92ed81b9eafb6db15d4d64736f6c63430005100032";

    public static final String FUNC_REVOKE = "revoke";

    public static final String FUNC_VERIFICATIONS = "verifications";

    public static final String FUNC_VERIFY = "verify";

    public static final Event REVOKED_EVENT = new Event("Revoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event VERIFIED_EVENT = new Event("Verified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

   

    protected VerificationRegistry(String contractAddress, Web3 web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

   
   

    public List<RevokedEventResponse> getRevokedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REVOKED_EVENT, transactionReceipt);
        ArrayList<RevokedEventResponse> responses = new ArrayList<RevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RevokedEventResponse typedResponse = new RevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.by = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.date = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RevokedEventResponse> revokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RevokedEventResponse>() {
            @Override
            public RevokedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REVOKED_EVENT, log);
                RevokedEventResponse typedResponse = new RevokedEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.by = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.date = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RevokedEventResponse> revokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVOKED_EVENT));
        return revokedEventFlowable(filter);
    }

    public List<VerifiedEventResponse> getVerifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VERIFIED_EVENT, transactionReceipt);
        ArrayList<VerifiedEventResponse> responses = new ArrayList<VerifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VerifiedEventResponse typedResponse = new VerifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.by = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.date = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.expDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VerifiedEventResponse> verifiedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VerifiedEventResponse>() {
            @Override
            public VerifiedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VERIFIED_EVENT, log);
                VerifiedEventResponse typedResponse = new VerifiedEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.by = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.date = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.expDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VerifiedEventResponse> verifiedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VERIFIED_EVENT));
        return verifiedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> revoke(byte[] hash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> verifications(byte[] param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFICATIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> verify(byte[] hash, BigInteger validDays) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash), 
                new org.web3j.abi.datatypes.generated.Uint256(validDays)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static VerificationRegistry load(String contractAddress, Web3 web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VerificationRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }


    public static RemoteCall<VerificationRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VerificationRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VerificationRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VerificationRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<VerificationRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VerificationRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VerificationRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VerificationRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RevokedEventResponse extends BaseEventResponse {
        public byte[] hash;//NOSONAR

        public String by;//NOSONAR

        public BigInteger date;//NOSONAR
    }

    public static class VerifiedEventResponse extends BaseEventResponse {
        public byte[] hash;//NOSONAR

        public String by;//NOSONAR

        public BigInteger date;//NOSONAR

        public BigInteger expDate;//NOSONAR
    }
}
