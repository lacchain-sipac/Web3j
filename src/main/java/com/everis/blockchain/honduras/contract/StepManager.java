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
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
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
 * <p>Generated with web3j version 4.5.9.
 */
@SuppressWarnings("rawtypes")
public class StepManager extends ContractFast {
    private static final String BINARY = "608060405234801561001057600080fd5b50611a1d806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80638928a9d3116100715780638928a9d3146101c15780638a59e6b8146101f6578063b638f9981461021f578063b96ea12d14610269578063c17f8ae7146102c7578063c72c46ef146102f0576100a9565b806317a26fb6146100ae5780633ea80bef146100df57806360d437e01461010a57806373fb9a7d1461017557806382490d701461019e575b600080fd5b6100cb600480360360208110156100c457600080fd5b5035610332565b604080519115158252519081900360200190f35b610108600480360360608110156100f557600080fd5b50803590602081013590604001356104b0565b005b6101336004803603606081101561012057600080fd5b5080359060208101359060400135610700565b604080519788526020880196909652868601949094526001600160a01b0390921660608601526080850152151560a084015260c0830152519081900360e00190f35b6101336004803603606081101561018b57600080fd5b50803590602081013590604001356107a6565b6100cb600480360360408110156101b457600080fd5b508035906020013561081c565b6101e4600480360360408110156101d757600080fd5b5080359060200135610841565b60408051918252519081900360200190f35b6101336004803603606081101561020c57600080fd5b5080359060208101359060400135610908565b610108600480360361010081101561023657600080fd5b5080359060208101359060408101359060608101359060808101359060a08101359060c08101359060e0013515156109e0565b6102866004803603602081101561027f57600080fd5b5035610c40565b604080516001600160a01b0397881681529587166020870152931515858501529115156060850152608084015290921660a082015290519081900360c00190f35b610108600480360360608110156102dd57600080fd5b5080359060208101359060400135610c89565b610108600480360360a081101561030657600080fd5b508035906020810135906001600160a01b03604082013581169160608101359091169060800135610e00565b60008181526020819052604080822080546002820154835163d37e62c960e01b81526004810191909152925191926060926001600160a01b039092169163d37e62c9916024808201928892909190829003018186803b15801561039457600080fd5b505afa1580156103a8573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156103d157600080fd5b81019080805160405193929190846401000000008211156103f157600080fd5b90830190602082018581111561040657600080fd5b825186602082028301116401000000008211171561042357600080fd5b82525081516020918201928201910280838360005b83811015610450578181015183820152602001610438565b50505050905001604052505050905060008090505b81518110156104a35761048b8583838151811061047e57fe5b6020026020010151610ffe565b61049b57600093505050506104ab565b600101610465565b506001925050505b919050565b600083815260208190526040902060010154839083908390600160a01b900460ff161561050e5760405162461bcd60e51b815260040180806020018281038252602a815260200180611995602a913960400191505060405180910390fd5b61051783610332565b6105525760405162461bcd60e51b81526004018080602001828103825260238152602001806119726023913960400191505060405180910390fd5b61055c83836110f7565b6105ad576040805162461bcd60e51b815260206004820152601e60248201527f796f752063616e6e6f74206368616e676520746f207468697320737465700000604482015290519081900360640190fd5b6105b98382338561118d565b6000868152602081815260409182902060028101548351908152918201889052338284015291517f6943905da49a23d18636f783b8ee86a739bec44d5aa3bb71da9ff9a3b9f06c9d9181900360600190a1600281018054600090815260048084016020908152604092839020805460ff19166001179055928990558354825163b8d0432160e01b81529182018a905291516001600160a01b039092169263b8d0432192602480840193829003018186803b15801561067657600080fd5b505afa15801561068a573d6000803e3d6000fd5b505050506040513d60208110156106a057600080fd5b5051156106f75760018101805460ff60a01b1916600160a01b1790556040805188815233602082015281517fdd78d06357a0596a7488c197007e9f00e78e231c61a37cb645e0dc8444b63e1e929181900390910190a15b50505050505050565b6000806000806000806000806107168b8b610841565b90506000806107278d8d8d86611432565b915091508161077d576040805162461bcd60e51b815260206004820152601c60248201527f646f63756d656e74207479706520646f6573206e6f7420657869737400000000604482015290519081900360640190fd5b6107888d8d83610908565b99509950995099509950995099505050509397509397509397909450565b6000806000806000806000806107bc8b8b610841565b90506000806107cd8d8d8d86611512565b915091508161077d576040805162461bcd60e51b815260206004820152601660248201527518dbdb5b595b9d08191bd95cc81b9bdd08195e1a5cdd60521b604482015290519081900360640190fd5b60008281526020818152604080832084845260040190915290205460ff165b92915050565b600082815260208181526040808320848452600581018352818420805483518186028101860190945280845291936060939290869084015b828210156108f95760008481526020908190206040805160e08101825260078602909201805483526001808201548486015260028201549284019290925260038101546001600160a01b0316606084015260048101546080840152600581015460ff16151560a08401526006015460c08301529083529092019101610879565b50509151979650505050505050565b600083815260208190526040812081908190819081908190819061092a6118b4565b60008b8152600583016020526040902080548b90811061094657fe5b60009182526020918290206040805160e081018252600790930290910180548084526001820154948401859052600282015492840183905260038201546001600160a01b031660608501819052600483015460808601819052600584015460ff16151560a0870181905260069094015460c0909601869052919e50949c50919a509298509650909450925050509397509397509397909450565b60008881526020818152604091829020548251636003233760e11b8152600481018a9052602481018b905292518b938b938b938b936001600160a01b039091169263c006466e926044808301939192829003018186803b158015610a4357600080fd5b505afa158015610a57573d6000803e3d6000fd5b505050506040513d6020811015610a6d57600080fd5b5051610aaa5760405162461bcd60e51b815260040180806020018281038252602781526020018061194b6027913960400191505060405180910390fd5b6000848152602081815260408083208684526005018252808320805482518185028101850190935280835260609492939192909184015b82821015610b615760008481526020908190206040805160e08101825260078602909201805483526001808201548486015260028201549284019290925260038101546001600160a01b0316606084015260048101546080840152600581015460ff16151560a08401526006015460c08301529083529092019101610ae1565b505050509050600081511115610bd657610b796118b4565b81600183510381518110610b8957fe5b602002602001015190508060a0015115610bd45760405162461bcd60e51b815260040180806020018281038252602a8152602001806119bf602a913960400191505060405180910390fd5b505b8515610be757610be78d8d8c6115d9565b610bef6118b4565b506040805160e0810182528a8152602081018a9052908101889052336060820152608081018b905286151560a08201524260c0820152610c308e8e8361176f565b5050505050505050505050505050565b60006020819052908152604090208054600182015460028301546003909301546001600160a01b03928316938284169360ff600160a01b8504811694600160a81b900416921686565b828282610c978383836115d9565b60008381526020819052604090206002810154610cb99085908490339061118d565b60008781526020818152604080832089845260050190915290208054600019810190829082908110610ce757fe5b600091825260209091206005600790920201015460ff16610d45576040805162461bcd60e51b815260206004820152601360248201527231b430b733b29030b63932b0b23c9037b832b760691b604482015290519081900360640190fd5b6000828281548110610d5357fe5b906000526020600020906007020160050160006101000a81548160ff0219169083151502179055507f91ed9a77d087d4b9c350d329cf31abd849129ce38273739f4ba89a7cf764720f338a8a858581548110610dab57fe5b600091825260209182902060056007909202010154604080516001600160a01b039096168652918501939093528381019190915260ff90911615156060830152519081900360800190a1505050505050505050565b60008581526020819052604090206001810154600160a81b900460ff1615610e6f576040805162461bcd60e51b815260206004820152601f60248201527f70726f6a65637420616c72656164792077617320696e697469616c697a656400604482015290519081900360640190fd5b60018101805460ff60a81b1916600160a81b1790556002810185905580546001600160a01b038086166001600160a01b0319909216919091178083556040805163dbf84c0b60e01b8152600481018990529051919092169163dbf84c0b916024808301926020929190829003018186803b158015610eec57600080fd5b505afa158015610f00573d6000803e3d6000fd5b505050506040513d6020811015610f1657600080fd5b5051610f60576040805162461bcd60e51b8152602060048201526014602482015273199a5c9cdd081cdd195c081b9bdd081d985b1a5960621b604482015290519081900360640190fd5b6001810180546001600160a01b0319166001600160a01b038516179055610f898683338861118d565b6003810180546001600160a01b0319163390811790915560408051918252602082018890528181018790526001600160a01b03868116606084015285166080830152517f83ecca1f5d3a87c38a44c77e3c34701f5f8968a0e0fb428efcce554f66d12edc9181900360a00190a1505050505050565b600082815260208181526040808320848452600581018352818420805483518186028101860190945280845291936060939290869084015b828210156110b65760008481526020908190206040805160e08101825260078602909201805483526001808201548486015260028201549284019290925260038101546001600160a01b0316606084015260048101546080840152600581015460ff16151560a08401526006015460c08301529083529092019101611036565b5050505090508051600014156110d15760009250505061083b565b806001825103815181106110e157fe5b602002602001015160a001519250505092915050565b6000828152602081815260408083208054600282015483516342eb745160e11b8152600481019190915260248101879052925191936001600160a01b03909116926385d6e8a29260448083019392829003018186803b15801561115957600080fd5b505afa15801561116d573d6000803e3d6000fd5b505050506040513d602081101561118357600080fd5b5051949350505050565b600084815260208190526040808220548151634276502960e01b81526004810185905260248101879052825184936001600160a01b039093169263427650299260448082019391829003018186803b1580156111e857600080fd5b505afa1580156111fc573d6000803e3d6000fd5b505050506040513d604081101561121257600080fd5b508051602090910151909250905081611272576040805162461bcd60e51b815260206004820152601c60248201527f726f6c65206e6f742070726573656e7420696e20746865207374657000000000604482015290519081900360640190fd5b801561134c576000868152602081815260409182902060010154825163506fe86960e11b8152600481018990526001600160a01b038881166024830152604482018b90529351939091169263a0dfd0d292606480840193919291829003018186803b1580156112e057600080fd5b505afa1580156112f4573d6000803e3d6000fd5b505050506040513d602081101561130a57600080fd5b50516113475760405162461bcd60e51b815260040180806020018281038252602d81526020018061191e602d913960400191505060405180910390fd5b61142a565b6000868152602081815260409182902060010154825163a3e6f91760e01b8152600481018990526001600160a01b0388811660248301529351939091169263a3e6f91792604480840193919291829003018186803b1580156113ad57600080fd5b505afa1580156113c1573d6000803e3d6000fd5b505050506040513d60208110156113d757600080fd5b505161142a576040805162461bcd60e51b815260206004820152601c60248201527f7573657220646f6573206e6f742068617665207468697320726f6c6500000000604482015290519081900360640190fd5b505050505050565b60008481526020819052604081208190815b84811015611507576114546118b4565b6000888152600584016020526040902080548390811061147057fe5b60009182526020918290206040805160e0810182526007909302909101805480845260018201549484019490945260028101549183019190915260038101546001600160a01b0316606083015260048101546080830152600581015460ff16151560a08301526006015460c082015291508714806114f15750868160200151145b156114fe57819350600194505b50600101611444565b505094509492505050565b60008481526020819052604081208190815b84811015611507576115346118b4565b6000888152600584016020526040902080548390811061155057fe5b60009182526020918290206040805160e081018252600790930290910180548352600181015493830193909352600283015490820181905260038301546001600160a01b0316606083015260048301546080830152600583015460ff16151560a083015260069092015460c082015291508714156115d057819350600194505b50600101611524565b600083815260208190526040808220805460028201548351637e5281ad60e11b815260048101919091526024810187905260448101869052835192949384936001600160a01b039093169263fca5035a926064808201939291829003018186803b15801561164657600080fd5b505afa15801561165a573d6000803e3d6000fd5b505050506040513d604081101561167057600080fd5b5080516020909101519092509050816116ba5760405162461bcd60e51b815260040180806020018281038252602d8152602001806118f1602d913960400191505060405180910390fd5b801561171b5760018301546040805163506fe86960e11b8152600481018790523360248201526044810189905290516001600160a01b039092169163a0dfd0d291606480820192602092909190829003018186803b1580156112e057600080fd5b60018301546040805163a3e6f91760e01b81526004810187905233602482015290516001600160a01b039092169163a3e6f91791604480820192602092909190829003018186803b1580156113ad57600080fd5b60008381526020818152604080832085845260058082018452828520805460018082018355828852868820895160079093020191825588870151908201558785015160028201556060808901516003830180546001600160a01b0319166001600160a01b039092169190911790556080808a0151600484015560a08a0151948301805460ff19169515159590951790945560c08901516006909201919091559588905254835133815294850189905284840188905294840194909452905190927f72bf4abdc9cb658c0d87507965b611521d3aeaa476a51b18a06404a20eb015f2928290030190a18160a00151156118ae5760a082015160408051338152602081018790528082018690529115156060830152517f91ed9a77d087d4b9c350d329cf31abd849129ce38273739f4ba89a7cf764720f9181900360800190a15b50505050565b6040805160e081018252600080825260208201819052918101829052606081018290526080810182905260a0810182905260c08101919091529056fe726f6c65206e6f7420617574686f72697a656420746f20736574207468697320646f63756d656e7420747970657573657220646f6573206e6f742068617665207468697320726f6c6520666f7220746869732070726f6a656374646f63756d656e74206b6579206973206e6f742070726573656e7420696e207468652073746570646f63756d656e7473207265717569726564206f72206e6f742066696e616c697a6564796f752063616e6e6f7420646f20746869733a2070726f6a65637420616c726561647920636c6f736564796f752063616e2774206d616b65206368616e6765733a20646f63756d656e742066696e616c697a6564a265627a7a72315820c92d72495f6477cdde25339fead85aa6c4d2c334c810c67b96e8ebbd3d39cc9064736f6c63430005100032";

    public static final String FUNC_CHANGESTEP = "changeStep";

    public static final String FUNC_GETDOCUMENTTYPE = "getDocumentType";

    public static final String FUNC_GETDOCUMENTTYPEBYCOMMENT = "getDocumentTypeByComment";

    public static final String FUNC_GETDOCUMENTTYPEBYDOCUMENT = "getDocumentTypeByDocument";

    public static final String FUNC_GETDOCUMENTTYPECOUNT = "getDocumentTypeCount";

    public static final String FUNC_HASVALIDDOCUMENTS = "hasValidDocuments";

    public static final String FUNC_INITPROJECT = "initProject";

    public static final String FUNC_ISSTEPCOMPLETEDPROJECT = "isStepCompletedProject";

    public static final String FUNC_PROJECTS = "projects";

    public static final String FUNC_SETDOCUMENTTYPE = "setDocumentType";

    public static final String FUNC_UNFINALIZEDOCUMENTTYPE = "unfinalizeDocumentType";

    public static final Event CHANGEFINALIZED_EVENT = new Event("ChangeFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event CHANGESET_EVENT = new Event("ChangeSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PROJECTCHANGEDSTEP_EVENT = new Event("ProjectChangedStep", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PROJECTFINALIZED_EVENT = new Event("ProjectFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PROJECTINITIALIZED_EVENT = new Event("ProjectInitialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    protected StepManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, (Web3)web3j, credentials, contractGasProvider);
    }



    public List<ChangeFinalizedEventResponse> getChangeFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANGEFINALIZED_EVENT, transactionReceipt);
        ArrayList<ChangeFinalizedEventResponse> responses = new ArrayList<ChangeFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChangeFinalizedEventResponse typedResponse = new ChangeFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.documentType = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.isFinal = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChangeFinalizedEventResponse> changeFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChangeFinalizedEventResponse>() {
            @Override
            public ChangeFinalizedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGEFINALIZED_EVENT, log);
                ChangeFinalizedEventResponse typedResponse = new ChangeFinalizedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.documentType = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.isFinal = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChangeFinalizedEventResponse> changeFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGEFINALIZED_EVENT));
        return changeFinalizedEventFlowable(filter);
    }

    public List<ChangeSetEventResponse> getChangeSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANGESET_EVENT, transactionReceipt);
        ArrayList<ChangeSetEventResponse> responses = new ArrayList<ChangeSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChangeSetEventResponse typedResponse = new ChangeSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.documentType = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.total = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChangeSetEventResponse> changeSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChangeSetEventResponse>() {
            @Override
            public ChangeSetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGESET_EVENT, log);
                ChangeSetEventResponse typedResponse = new ChangeSetEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.documentType = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.total = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChangeSetEventResponse> changeSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGESET_EVENT));
        return changeSetEventFlowable(filter);
    }

    public List<ProjectChangedStepEventResponse> getProjectChangedStepEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROJECTCHANGEDSTEP_EVENT, transactionReceipt);
        ArrayList<ProjectChangedStepEventResponse> responses = new ArrayList<ProjectChangedStepEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProjectChangedStepEventResponse typedResponse = new ProjectChangedStepEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.actualStep = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newStep = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProjectChangedStepEventResponse> projectChangedStepEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProjectChangedStepEventResponse>() {
            @Override
            public ProjectChangedStepEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROJECTCHANGEDSTEP_EVENT, log);
                ProjectChangedStepEventResponse typedResponse = new ProjectChangedStepEventResponse();
                typedResponse.log = log;
                typedResponse.actualStep = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newStep = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProjectChangedStepEventResponse> projectChangedStepEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROJECTCHANGEDSTEP_EVENT));
        return projectChangedStepEventFlowable(filter);
    }

    public List<ProjectFinalizedEventResponse> getProjectFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROJECTFINALIZED_EVENT, transactionReceipt);
        ArrayList<ProjectFinalizedEventResponse> responses = new ArrayList<ProjectFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProjectFinalizedEventResponse typedResponse = new ProjectFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProjectFinalizedEventResponse> projectFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProjectFinalizedEventResponse>() {
            @Override
            public ProjectFinalizedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROJECTFINALIZED_EVENT, log);
                ProjectFinalizedEventResponse typedResponse = new ProjectFinalizedEventResponse();
                typedResponse.log = log;
                typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProjectFinalizedEventResponse> projectFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROJECTFINALIZED_EVENT));
        return projectFinalizedEventFlowable(filter);
    }

    public List<ProjectInitializedEventResponse> getProjectInitializedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROJECTINITIALIZED_EVENT, transactionReceipt);
        ArrayList<ProjectInitializedEventResponse> responses = new ArrayList<ProjectInitializedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProjectInitializedEventResponse typedResponse = new ProjectInitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.firstStep = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.flowContract = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.rolesContract = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProjectInitializedEventResponse> projectInitializedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProjectInitializedEventResponse>() {
            @Override
            public ProjectInitializedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROJECTINITIALIZED_EVENT, log);
                ProjectInitializedEventResponse typedResponse = new ProjectInitializedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.projectCode = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.firstStep = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.flowContract = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.rolesContract = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProjectInitializedEventResponse> projectInitializedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROJECTINITIALIZED_EVENT));
        return projectInitializedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> changeStep(byte[] projectCode, byte[] targetStep, byte[] userRole) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGESTEP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(targetStep), 
                new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>> getDocumentType(byte[] projectCode, byte[] documentType, BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDOCUMENTTYPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>>(function,
                new Callable<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>>() {
                    @Override
                    public Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>> getDocumentTypeByComment(byte[] projectCode, byte[] documentType, byte[] hashComment) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDOCUMENTTYPEBYCOMMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.generated.Bytes32(hashComment)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>>(function,
                new Callable<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>>() {
                    @Override
                    public Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>> getDocumentTypeByDocument(byte[] projectCode, byte[] documentType, byte[] hashDocument) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDOCUMENTTYPEBYDOCUMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.generated.Bytes32(hashDocument)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>>(function,
                new Callable<Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>>() {
                    @Override
                    public Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<byte[], byte[], byte[], String, byte[], Boolean, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getDocumentTypeCount(byte[] projectCode, byte[] documentType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDOCUMENTTYPECOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> hasValidDocuments(byte[] projectCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASVALIDDOCUMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initProject(byte[] projectCode, byte[] firstStep, String flowContract, String rolesContract, byte[] userRole) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITPROJECT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(firstStep), 
                new org.web3j.abi.datatypes.Address(160, flowContract), 
                new org.web3j.abi.datatypes.Address(160, rolesContract), 
                new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isStepCompletedProject(byte[] projectCode, byte[] stepId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSTEPCOMPLETEDPROJECT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(stepId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple6<String, String, Boolean, Boolean, byte[], String>> projects(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PROJECTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, Boolean, Boolean, byte[], String>>(function,
                new Callable<Tuple6<String, String, Boolean, Boolean, byte[], String>>() {
                    @Override
                    public Tuple6<String, String, Boolean, Boolean, byte[], String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, Boolean, Boolean, byte[], String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setDocumentType(byte[] projectCode, byte[] documentType, byte[] stepId, byte[] _userRole, byte[] accreditDocumentHash, byte[] revokeDocumentHash, byte[] accreditCommentHash, Boolean _isFinal) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETDOCUMENTTYPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.generated.Bytes32(stepId), 
                new org.web3j.abi.datatypes.generated.Bytes32(_userRole), 
                new org.web3j.abi.datatypes.generated.Bytes32(accreditDocumentHash), 
                new org.web3j.abi.datatypes.generated.Bytes32(revokeDocumentHash), 
                new org.web3j.abi.datatypes.generated.Bytes32(accreditCommentHash), 
                new org.web3j.abi.datatypes.Bool(_isFinal)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unfinalizeDocumentType(byte[] projectCode, byte[] documentType, byte[] userRole) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNFINALIZEDOCUMENTTYPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(projectCode), 
                new org.web3j.abi.datatypes.generated.Bytes32(documentType), 
                new org.web3j.abi.datatypes.generated.Bytes32(userRole)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }


    public static StepManager load(String contractAddress, Web3 web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StepManager(contractAddress, web3j, credentials, contractGasProvider);
    }


    public static RemoteCall<StepManager> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StepManager.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StepManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StepManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<StepManager> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StepManager.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StepManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StepManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ChangeFinalizedEventResponse extends BaseEventResponse {
        public String user;//NOSONAR

        public byte[] projectCode;//NOSONAR

        public byte[] documentType;//NOSONAR

        public Boolean isFinal;//NOSONAR
    }

    public static class ChangeSetEventResponse extends BaseEventResponse {
        public String user;//NOSONAR

        public byte[] projectCode;//NOSONAR

        public byte[] documentType;//NOSONAR

        public BigInteger total;//NOSONAR
    }

    public static class ProjectChangedStepEventResponse extends BaseEventResponse {
        public byte[] actualStep;//NOSONAR

        public byte[] newStep;//NOSONAR

        public String user;//NOSONAR
    }

    public static class ProjectFinalizedEventResponse extends BaseEventResponse {
        public byte[] projectCode;//NOSONAR

        public String user;//NOSONAR
    }

    public static class ProjectInitializedEventResponse extends BaseEventResponse {
        public String user;//NOSONAR

        public byte[] projectCode;//NOSONAR

        public byte[] firstStep;//NOSONAR

        public String flowContract;//NOSONAR

        public String rolesContract;//NOSONAR
    }
}
