package com.everis.blockchain.honduras.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import com.everis.blockchain.honduras.util.ContractFast;
import com.everis.blockchain.honduras.util.Web3;

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
public class Roles extends ContractFast {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516106fe3803806106fe8339818101604052604081101561003357600080fd5b50805160209182015160028290556003553360009081528083526040808220928252919092529020805460ff19166001179055610689806100756000396000f3fe608060405234801561001057600080fd5b50600436106100875760003560e01c8063a3e6f9171161005b578063a3e6f9171461015e578063be62da0e1461018a578063dd484a9c146101a4578063e58378bb146101d657610087565b8062da9bdd1461008c578063482a97f3146100ba57806379c768d6146100e6578063a0dfd0d214610118575b600080fd5b6100b8600480360360408110156100a257600080fd5b50803590602001356001600160a01b03166101de565b005b6100b8600480360360408110156100d057600080fd5b50803590602001356001600160a01b03166102d3565b6100b8600480360360608110156100fc57600080fd5b508035906001600160a01b0360208201351690604001356103bb565b61014a6004803603606081101561012e57600080fd5b508035906001600160a01b0360208201351690604001356104c6565b604080519115158252519081900360200190f35b61014a6004803603604081101561017457600080fd5b50803590602001356001600160a01b03166104fb565b610192610521565b60408051918252519081900360200190f35b6100b8600480360360608110156101ba57600080fd5b508035906001600160a01b036020820135169060400135610527565b610192610626565b6002543360009081526020818152604080832084845290915290205460ff166102385760405162461bcd60e51b815260040180806020018281038252602881526020018061062d6028913960400191505060405180910390fd5b6001600160a01b03821660009081526020818152604080832086845290915290205460ff16156102a6576040805162461bcd60e51b81526020600482015260146024820152731c9bdb19481dd85cc8185b1c9958591e481cd95d60621b604482015290519081900360640190fd5b506001600160a01b031660009081526020818152604080832093835292905220805460ff19166001179055565b6002543360009081526020818152604080832084845290915290205460ff1661032d5760405162461bcd60e51b815260040180806020018281038252602881526020018061062d6028913960400191505060405180910390fd5b6001600160a01b03821660009081526020818152604080832086845290915290205460ff16610391576040805162461bcd60e51b815260206004820152600b60248201526a1b9bc81c9bdb19481cd95d60aa1b604482015290519081900360640190fd5b506001600160a01b031660009081526020818152604080832093835292905220805460ff19169055565b6003543360009081526020818152604080832084845290915290205460ff166104155760405162461bcd60e51b815260040180806020018281038252602881526020018061062d6028913960400191505060405180910390fd5b60008281526001602090815260408083206001600160a01b0387168452825280832087845290915290205460ff161561048c576040805162461bcd60e51b81526020600482015260146024820152731c9bdb19481dd85cc8185b1c9958591e481cd95d60621b604482015290519081900360640190fd5b5060009081526001602081815260408084206001600160a01b0390951684529381528383209483529390935220805460ff19169091179055565b60008181526001602090815260408083206001600160a01b0386168452825280832086845290915290205460ff169392505050565b6001600160a01b0316600090815260208181526040808320938352929052205460ff1690565b60035481565b6003543360009081526020818152604080832084845290915290205460ff166105815760405162461bcd60e51b815260040180806020018281038252602881526020018061062d6028913960400191505060405180910390fd5b60008281526001602090815260408083206001600160a01b0387168452825280832087845290915290205460ff166105ee576040805162461bcd60e51b815260206004820152600b60248201526a1b9bc81c9bdb19481cd95d60aa1b604482015290519081900360640190fd5b5060009081526001602090815260408083206001600160a01b039094168352928152828220938252929092529020805460ff19169055565b6002548156fe7573657220646f6573206e6f7420686176652073756666696369656e742070726976696c65676573a265627a7a72315820c5d71c1703e5499f9ecb06efcb99f4f95f9547bbe6f36c11e4f30bdda126746864736f6c63430005100032";

    public static final String FUNC_OWNER_ROLE = "OWNER_ROLE";

    public static final String FUNC_PROJECT_OWNER_ROLE = "PROJECT_OWNER_ROLE";

    public static final String FUNC_hasRoleUser = "hasRoleUser";

    public static final String FUNC_removeRoleUser = "removeRoleUser";

    public static final String FUNC_setRoleUser = "setRoleUser";

  

    protected Roles(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, (Web3)web3j, credentials, contractGasProvider);
    }

   

    public RemoteFunctionCall<byte[]> OWNER_ROLE() {
        final Function function = new Function(FUNC_OWNER_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> PROJECT_OWNER_ROLE() {
        final Function function = new Function(FUNC_PROJECT_OWNER_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> hasRoleUser(byte[] role, String user, byte[] projectCode) {
        final Function function = new Function(FUNC_hasRoleUser, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> hasRoleUser(byte[] role, String user) {
        final Function function = new Function(FUNC_hasRoleUser, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeRoleUser(byte[] role, String user) {
        final Function function = new Function(
                FUNC_removeRoleUser, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeRoleUser(byte[] role, String user, byte[] projectCode) {
        final Function function = new Function(
                FUNC_removeRoleUser, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setRoleUser(byte[] role, String user) {
        final Function function = new Function(
                FUNC_setRoleUser, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setRoleUser(byte[] role, String user, byte[] projectCode) {
        final Function function = new Function(
                FUNC_setRoleUser, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }


    public static Roles load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Roles(contractAddress, (Web3)web3j, credentials, contractGasProvider);
    }


    public static RemoteCall<Roles> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, byte[] ownerRole, byte[] projectOwnerRole) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(ownerRole), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectOwnerRole)));
        return deployRemoteCall(Roles.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Roles> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, byte[] ownerRole, byte[] projectOwnerRole) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(ownerRole), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectOwnerRole)));
        return deployRemoteCall(Roles.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Roles> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, byte[] ownerRole, byte[] projectOwnerRole) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(ownerRole), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectOwnerRole)));
        return deployRemoteCall(Roles.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Roles> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, byte[] ownerRole, byte[] projectOwnerRole) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(ownerRole), 
                new org.web3j.abi.datatypes.generated.Bytes32(projectOwnerRole)));
        return deployRemoteCall(Roles.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
