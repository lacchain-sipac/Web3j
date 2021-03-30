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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class Flow extends ContractFast {
    public static final String BINARY = "608060405234801561001057600080fd5b5033600081815260208190526040808220805460ff191660011790555182917f994a936646fe87ffe4f1e469d3d6aa417d6b855598397f323de5b449f765f0c391a25061108c806100626000396000f3fe608060405234801561001057600080fd5b50600436106101215760003560e01c8063b8bcf8d6116100ad578063dbf84c0b11610071578063dbf84c0b14610712578063e5fa76cc1461072f578063e8022449146107de578063eb9ba16d14610888578063fca5035a146108a557610121565b8063b8bcf8d614610351578063b8d04321146103fb578063c006466e14610418578063d37e62c91461043b578063d428b15d1461045857610121565b80637065cb48116100f45780637065cb481461023157806382c07a111461025957806385d6e8a21461027c5780638dd2de7c1461029f578063b15be2f51461034957610121565b8063022914a7146101265780632018543f146101605780632f54bf6e146101cd57806342765029146101f3575b600080fd5b61014c6004803603602081101561013c57600080fd5b50356001600160a01b03166108ce565b604080519115158252519081900360200190f35b61017d6004803603602081101561017657600080fd5b50356108e3565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156101b95781810151838201526020016101a1565b505050509050019250505060405180910390f35b61014c600480360360208110156101e357600080fd5b50356001600160a01b0316610948565b6102166004803603604081101561020957600080fd5b5080359060200135610966565b60408051921515835290151560208301528051918290030190f35b6102576004803603602081101561024757600080fd5b50356001600160a01b031661099b565b005b61014c6004803603604081101561026f57600080fd5b50803590602001356109f9565b61014c6004803603604081101561029257600080fd5b5080359060200135610a20565b610257600480360360608110156102b557600080fd5b81359190810190604081016020820135600160201b8111156102d657600080fd5b8201836020820111156102e857600080fd5b803590602001918460208302840111600160201b8311171561030957600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295505050503515159050610a43565b610257610abd565b6102576004803603606081101561036757600080fd5b81359190810190604081016020820135600160201b81111561038857600080fd5b82018360208201111561039a57600080fd5b803590602001918460208302840111600160201b831117156103bb57600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295505050503515159050610b0e565b61014c6004803603602081101561041157600080fd5b5035610b82565b61014c6004803603604081101561042e57600080fd5b5080359060200135610b9a565b61017d6004803603602081101561045157600080fd5b5035610c61565b610257600480360361010081101561046f57600080fd5b81359190810190604081016020820135600160201b81111561049057600080fd5b8201836020820111156104a257600080fd5b803590602001918460208302840111600160201b831117156104c357600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295949360208101935035915050600160201b81111561051257600080fd5b82018360208201111561052457600080fd5b803590602001918460208302840111600160201b8311171561054557600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295949360208101935035915050600160201b81111561059457600080fd5b8201836020820111156105a657600080fd5b803590602001918460208302840111600160201b831117156105c757600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295949360208101935035915050600160201b81111561061657600080fd5b82018360208201111561062857600080fd5b803590602001918460208302840111600160201b8311171561064957600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295949360208101935035915050600160201b81111561069857600080fd5b8201836020820111156106aa57600080fd5b803590602001918460208302840111600160201b831117156106cb57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550505050803515159150602001351515610cc1565b61014c6004803603602081101561072857600080fd5b5035610e13565b6102576004803603608081101561074557600080fd5b813591602081013591810190606081016040820135600160201b81111561076b57600080fd5b82018360208201111561077d57600080fd5b803590602001918460208302840111600160201b8311171561079e57600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295505050503515159050610e30565b610257600480360360608110156107f457600080fd5b81359190810190604081016020820135600160201b81111561081557600080fd5b82018360208201111561082757600080fd5b803590602001918460208302840111600160201b8311171561084857600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295505050503515159050610f1d565b6102166004803603602081101561089e57600080fd5b5035610f91565b610216600480360360608110156108bb57600080fd5b5080359060208101359060400135610fb2565b60006020819052908152604090205460ff1681565b60008181526001602081815260409283902090910180548351818402810184019094528084526060939283018282801561093c57602002820191906000526020600020905b815481526020019060010190808311610928575b50505050509050919050565b6001600160a01b031660009081526020819052604090205460ff1690565b60009182526001602090815260408084209284526002830182528084205460049093019091529091205460ff91821692911690565b6109a433610948565b6109ad57600080fd5b6001600160a01b038116600081815260208190526040808220805460ff19166001179055517f994a936646fe87ffe4f1e469d3d6aa417d6b855598397f323de5b449f765f0c39190a250565b600082815260016020908152604080832084845260040190915290205460ff165b92915050565b600090815260016020908152604080832093835260059093019052205460ff1690565b610a4c33610948565b610a5557600080fd5b60005b8251811015610ab75781600160008681526020019081526020016000206004016000858481518110610a8657fe5b6020908102919091018101518252810191909152604001600020805460ff1916911515919091179055600101610a58565b50505050565b610ac633610948565b610acf57600080fd5b33600081815260208190526040808220805460ff19169055517f58619076adf5bb0943d100ef88d52d7c3fd691b19d3a9071b555b651fbf418da9190a2565b610b1733610948565b610b2057600080fd5b60005b8251811015610ab75781600160008681526020019081526020016000206005016000858481518110610b5157fe5b6020908102919091018101518252810191909152604001600020805460ff1916911515919091179055600101610b23565b60009081526001602052604090206006015460ff1690565b6000805b600084815260016020526040902054811015610bf4576000848152600160205260409020805484919083908110610bd157fe5b90600052602060002001541415610bec576001915050610a1a565b600101610b9e565b5060005b60008481526001602081905260409091200154811015610c575782600160008681526020019081526020016000206001018281548110610c3457fe5b90600052602060002001541415610c4f576001915050610a1a565b600101610bf8565b5060009392505050565b60008181526001602090815260409182902080548351818402810184019094528084526060939283018282801561093c57602002820191906000526020600020908154815260200190600101908083116109285750505050509050919050565b610cca33610948565b610cd357600080fd5b60008881526001602090815260409091208851610cf2928a0190610fef565b5060008881526001602081815260409092208851610d1893919092019190890190610fef565b50845115610d2c57610d2c88866001610f1d565b835115610d3f57610d3f88856001610b0e565b825115610d5257610d5288846001610a43565b6000888152600160209081526040808320600601805460ff19168615151761ff0019166101008615150217905580518b815233918101829052606081840181815289519183019190915288517fb7c52f7454ba6017ee954db0ee8bdd8119dd745f3079ba0ff3ea32effe167c16958e958b9594939260808501928783019291909102908190849084905b83811015610df4578181015183820152602001610ddc565b5050505090500194505050505060405180910390a15050505050505050565b600090815260016020526040902060060154610100900460ff1690565b610e3933610948565b610e4257600080fd5b60005b8251811015610f1657600085815260016020908152604080832087845260030190915281208451849290869085908110610e7b57fe5b6020026020010151815260200190815260200160002060006101000a81548160ff0219169083151502179055507f18b97f0499ff71e246eebb5ce490702004ccc0de30c5a8804e79d8c152b0eb178585858481518110610ed757fe5b602002602001015185604051808581526020018481526020018381526020018215151515815260200194505050505060405180910390a1600101610e45565b5050505050565b610f2633610948565b610f2f57600080fd5b60005b8251811015610ab75781600160008681526020019081526020016000206002016000858481518110610f6057fe5b6020908102919091018101518252810191909152604001600020805460ff1916911515919091179055600101610f32565b60016020526000908152604090206006015460ff8082169161010090041682565b600092835260016020908152604080852093855260038401825280852092855291815281842054600490930190529091205460ff91821692911690565b82805482825590600052602060002090810192821561102a579160200282015b8281111561102a57825182559160200191906001019061100f565b5061103692915061103a565b5090565b61105491905b808211156110365760008155600101611040565b9056fea265627a7a723158206c8db12164929c72383d17f79fdf76c0a117c5eb13679bcb415d5126c24b3bf264736f6c63430005100032";

    public static final String FUNC_ADDOWNER = "addOwner";

    public static final String FUNC_ADDSTEP = "addStep";

    public static final String FUNC_DOCUMENTROLEAUTHORIZATION = "documentRoleAuthorization";

    public static final String FUNC_EXISTSDOCUMENTKEYINSTEP = "existsDocumentKeyInStep";

    public static final String FUNC_GETMANDATORYDOCUMENTKEYSSTEP = "getMandatorydocumentKeysStep";

    public static final String FUNC_GETOPTIONALDOCUMENTKEYSSTEP = "getOptionaldocumentKeysStep";

    public static final String FUNC_ISFINALSTEP = "isFinalStep";

    public static final String FUNC_ISINITIALSTEP = "isInitialStep";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_ISPROJECTSPECIFICROLE = "isProjectSpecificRole";

    public static final String FUNC_ISVALIDSTEPCHANGE = "isValidStepChange";

    public static final String FUNC_OWNERS = "owners";

    public static final String FUNC_RENOUNCE = "renounce";

    public static final String FUNC_ROLEAUTHORIZATION = "roleAuthorization";

    public static final String FUNC_SETPREVIOUSVALIDSTEP = "setPreviousValidStep";

    public static final String FUNC_SETPROJECTSPECIFICROLES = "setProjectSpecificRoles";

    public static final String FUNC_SETSTATUSAUTHORIZEDDOCUMENTROLE = "setStatusAuthorizedDocumentRole";

    public static final String FUNC_SETSTATUSAUTHORIZEDROLESSTEP = "setStatusAuthorizedRolesStep";

    public static final String FUNC_STEPS = "steps";

    public static final Event OWNERADDED_EVENT = new Event("OwnerAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERREMOVED_EVENT = new Event("OwnerRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event STATUSAUTHORIZEDDOCUMENTUSER_EVENT = new Event("StatusAuthorizedDocumentUser", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event STEPADDED_EVENT = new Event("StepAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<Address>() {}));
    ;

    protected Flow(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, (Web3)web3j, credentials, contractGasProvider);
    }

    public List<OwnerAddedEventResponse> getOwnerAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERADDED_EVENT, transactionReceipt);
        ArrayList<OwnerAddedEventResponse> responses = new ArrayList<OwnerAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerAddedEventResponse typedResponse = new OwnerAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerAddedEventResponse> ownerAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnerAddedEventResponse>() {
            @Override
            public OwnerAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERADDED_EVENT, log);
                OwnerAddedEventResponse typedResponse = new OwnerAddedEventResponse();
                typedResponse.log = log;
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerAddedEventResponse> ownerAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERADDED_EVENT));
        return ownerAddedEventFlowable(filter);
    }

    public List<OwnerRemovedEventResponse> getOwnerRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERREMOVED_EVENT, transactionReceipt);
        ArrayList<OwnerRemovedEventResponse> responses = new ArrayList<OwnerRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerRemovedEventResponse typedResponse = new OwnerRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.formerOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerRemovedEventResponse> ownerRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnerRemovedEventResponse>() {
            @Override
            public OwnerRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERREMOVED_EVENT, log);
                OwnerRemovedEventResponse typedResponse = new OwnerRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.formerOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerRemovedEventResponse> ownerRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERREMOVED_EVENT));
        return ownerRemovedEventFlowable(filter);
    }

    public List<StatusAuthorizedDocumentUserEventResponse> getStatusAuthorizedDocumentUserEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(STATUSAUTHORIZEDDOCUMENTUSER_EVENT, transactionReceipt);
        ArrayList<StatusAuthorizedDocumentUserEventResponse> responses = new ArrayList<StatusAuthorizedDocumentUserEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StatusAuthorizedDocumentUserEventResponse typedResponse = new StatusAuthorizedDocumentUserEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.step = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.documentType = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.role = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.status = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StatusAuthorizedDocumentUserEventResponse> statusAuthorizedDocumentUserEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StatusAuthorizedDocumentUserEventResponse>() {
            @Override
            public StatusAuthorizedDocumentUserEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(STATUSAUTHORIZEDDOCUMENTUSER_EVENT, log);
                StatusAuthorizedDocumentUserEventResponse typedResponse = new StatusAuthorizedDocumentUserEventResponse();
                typedResponse.log = log;
                typedResponse.step = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.documentType = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.role = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.status = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StatusAuthorizedDocumentUserEventResponse> statusAuthorizedDocumentUserEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STATUSAUTHORIZEDDOCUMENTUSER_EVENT));
        return statusAuthorizedDocumentUserEventFlowable(filter);
    }

    public List<StepAddedEventResponse> getStepAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(STEPADDED_EVENT, transactionReceipt);
        ArrayList<StepAddedEventResponse> responses = new ArrayList<StepAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StepAddedEventResponse typedResponse = new StepAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.step = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.previousValidSteps = (List<byte[]>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StepAddedEventResponse> stepAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StepAddedEventResponse>() {
            @Override
            public StepAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(STEPADDED_EVENT, log);
                StepAddedEventResponse typedResponse = new StepAddedEventResponse();
                typedResponse.log = log;
                typedResponse.step = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.previousValidSteps = (List<byte[]>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StepAddedEventResponse> stepAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STEPADDED_EVENT));
        return stepAddedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addOwner(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addStep(byte[] stepId, List<byte[]> _mandatoryDocumentKeys, List<byte[]> _optionalDocumentKeys, List<byte[]> _authorizedRoles, List<byte[]> _previousValidSteps, List<byte[]> _projectSpecificRoles, Boolean _isFinalStep, Boolean _isInitialStep) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_mandatoryDocumentKeys, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_optionalDocumentKeys, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_authorizedRoles, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_previousValidSteps, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_projectSpecificRoles, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.Bool(_isFinalStep), 
                new org.web3j.abi.datatypes.Bool(_isInitialStep)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<Boolean, Boolean>> documentRoleAuthorization(byte[] stepId, byte[] documentType, byte[] userRole) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DOCUMENTROLEAUTHORIZATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, Boolean>>(function,
                new Callable<Tuple2<Boolean, Boolean>>() {
                    @Override
                    public Tuple2<Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, Boolean>(
                                (Boolean) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> existsDocumentKeyInStep(byte[] stepId, byte[] documentType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXISTSDOCUMENTKEYINSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<List> getMandatorydocumentKeysStep(byte[] stepId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMANDATORYDOCUMENTKEYSSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getOptionaldocumentKeysStep(byte[] stepId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETOPTIONALDOCUMENTKEYSSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isFinalStep(byte[] stepId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISFINALSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isInitialStep(byte[] stepId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISINITIALSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isOwner(String addr) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isProjectSpecificRole(byte[] stepId, byte[] userRole) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISPROJECTSPECIFICROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isValidStepChange(byte[] currentStep, byte[] targetStep) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISVALIDSTEPCHANGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(currentStep), 
                new org.web3j.abi.datatypes.generated.Bytes32(targetStep)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> owners(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounce() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<Boolean, Boolean>> roleAuthorization(byte[] targetStep, byte[] userRole) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ROLEAUTHORIZATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(targetStep), 
                new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, Boolean>>(function,
                new Callable<Tuple2<Boolean, Boolean>>() {
                    @Override
                    public Tuple2<Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, Boolean>(
                                (Boolean) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setPreviousValidStep(byte[] stepId, List<byte[]> previousValidSteps, Boolean status) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPREVIOUSVALIDSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(previousValidSteps, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.Bool(status)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setProjectSpecificRoles(byte[] stepId, List<byte[]> _projectSpecificRoles, Boolean status) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPROJECTSPECIFICROLES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_projectSpecificRoles, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.Bool(status)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setStatusAuthorizedDocumentRole(byte[] stepId, byte[] documentType, List<byte[]> userRoles, Boolean status) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTATUSAUTHORIZEDDOCUMENTROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(userRoles, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.Bool(status)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setStatusAuthorizedRolesStep(byte[] stepId, List<byte[]> roles, Boolean status) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTATUSAUTHORIZEDROLESSTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(roles, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.Bool(status)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<Boolean, Boolean>> steps(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STEPS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, Boolean>>(function,
                new Callable<Tuple2<Boolean, Boolean>>() {
                    @Override
                    public Tuple2<Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, Boolean>(
                                (Boolean) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue());
                    }
                });
    }


    public static Flow load(String contractAddress, Web3 web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Flow(contractAddress, web3j, credentials, contractGasProvider);
    }



    public static RemoteCall<Flow> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Flow.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Flow> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Flow.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Flow> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Flow.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Flow> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Flow.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnerAddedEventResponse extends BaseEventResponse {
        public String newOwner; //NOSONAR
    }

    public static class OwnerRemovedEventResponse extends BaseEventResponse {
        public String formerOwner;//NOSONAR
    }

    public static class StatusAuthorizedDocumentUserEventResponse extends BaseEventResponse {
        public byte[] step;//NOSONAR

        public byte[] documentType;//NOSONAR

        public byte[] role;//NOSONAR

        public Boolean status;//NOSONAR
    }

    public static class StepAddedEventResponse extends BaseEventResponse {
        public byte[] step;//NOSONAR

        public List<byte[]> previousValidSteps;//NOSONAR

        public String user;//NOSONAR
    }
}
