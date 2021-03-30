package com.everis.blockchain.honduras.util;

import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolver;
import org.web3j.tx.ChainId;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

@SuppressWarnings({ "deprecation" })
public abstract class ContractFast extends Contract {
	private static final int DEFAULT_POLLING_FREQUENCY = 2 * 1000;
	public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;

//	   @Deprecated
//	    protected ContractFast(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
//	        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
//	    }

	protected ContractFast(String contractBinary, String contractAddress, Web3 web3j, Credentials credentials,
			ContractGasProvider gasProvider) {
		super(new EnsResolver(web3j), contractBinary, contractAddress, web3j, // long chainId, int attempts, long
				// sleepDuration)
				new RawTransactionManagerSimple(web3j, credentials, ChainId.NONE, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH,
						DEFAULT_POLLING_FREQUENCY),
				gasProvider);

	}

//	    @Deprecated
//	    protected ContractFast(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
//	        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
//	    }

//	    protected ContractFast(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
//	        super(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
//	    }

}
