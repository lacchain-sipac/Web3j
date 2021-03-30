package com.everis.blockchain.honduras.main;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.main.model.Constants;
import com.everis.blockchain.honduras.main.model.GenerateDataFlow;
import com.everis.blockchain.honduras.main.model.GenerateIdentityManager;
import com.everis.blockchain.honduras.main.model.GeneratePrivateKey;
import com.everis.blockchain.honduras.main.model.GenerateStepManager;
import com.everis.blockchain.honduras.util.UtilYaml;

public class InitLoad {
	private static final Logger log = LoggerFactory.getLogger("InitLoad");

	public static void main(String[] args) throws Exception {

		Map<String, String> header = null;

		String fileOri = args.length > 0 && args[0] != null ? args[0] : UtilYaml.NAME_FILE;
		String fileDest = args.length > 1 && args[1] != null ? args[1] : UtilYaml.NAME_FILE_JS;

		LinkedHashMap<String, String> map = UtilYaml.readFile(fileOri);

		// mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies
		// java -cp blockchain-honduras-0.0.1-SNAPSHOT-jar-with-dependencies.jar
		// com.everis.blockchain.honduras.main.InitLoad
		log.info("---INIT--");

		new GeneratePrivateKey(map, header).create();
		new GenerateIdentityManager(map, header).create();
		new GenerateDataFlow(map, header).create();
		new GenerateStepManager(map, header).create();

		map.put(Constants.NETWORK_ID, Constants.NETWORK_ID_VALUE);

		UtilYaml.saveFile(map, UtilYaml.NAME_FILE);
//		
//			UtilYaml.saveFileSpring(map, fileDest); 
//			
//			UtilYaml.saveFileDeployment(map, fileDest); 
		UtilYaml.saveFileJS(map, fileDest);

		log.info("---FINISH OK--");

	}

}
