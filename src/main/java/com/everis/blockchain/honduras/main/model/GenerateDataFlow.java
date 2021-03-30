package com.everis.blockchain.honduras.main.model;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.IFlow;
import com.everis.blockchain.honduras.impl.FlowImpl;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class GenerateDataFlow {

	private static final Logger log = LoggerFactory.getLogger("GenerateDataFlow");
	private EthCoreParams ethCoreParams;
	private LinkedHashMap<String, String> map;
	private IFlow iFlow;
	private String[] blank = {};

	public GenerateDataFlow(LinkedHashMap<String, String> map,  Map<String, String> headers) throws Exception {
		this.map = map;
		ethCoreParams = new EthCoreParams(map.get(Constants.BLOCKCHAIN_SERVER), map.get(Constants.PRIVATE_KEY_BACKEND),
				null, BigInteger.valueOf(0), null);
		
		ethCoreParams.setHeaders(headers);

	}

	public void create() throws Exception {
		if (StringUtils.isEmpty(map.get(Constants.CONTRACT_ADDRESS_FLOW))) {
			deploy();
			load();
		}
	}

	private void deploy() throws Exception {
		IFlow flowTemp = new FlowImpl(Constants.DUMMY, ethCoreParams);

		String addressFlow = flowTemp.deployFlow();

		this.map.put(Constants.CONTRACT_ADDRESS_FLOW, addressFlow);

		log.info("CONTRACT_ADDRESS_FLOW : " + map.get(Constants.CONTRACT_ADDRESS_FLOW));

		this.iFlow = new FlowImpl(map.get(Constants.CONTRACT_ADDRESS_FLOW), ethCoreParams);
	
	}

	private void load() throws Exception {
		log.info("-->generateLoadFlow--<<");

		firstStep("fase_01");

		secondStep("paso_02_01", "fase_01");

		thirdStep("paso_02_02", "paso_02_01");

		fourthStep("paso_02_03", "paso_02_02");

		fifthStep("paso_02_04", "paso_02_03");

		sixthStep("paso_02_05", "paso_02_04");

		seventhStep("paso_02_06", "paso_02_05");

		eighthStep("paso_02_07", "paso_02_06");

		ninethStep("paso_03_01", "paso_02_07");

		tenthStep("paso_03_02", "paso_03_01");

		eleventhStep("paso_03_03", "paso_03_02");

		twelfthStep("paso_03_04", "paso_03_03");
		
		thirteenthStep("paso_03_05", "paso_03_04");
		
		fourteenthStep("fase_03", "paso_03_05");
		
		
	}

	private void firstStep(String step) throws Exception {

		String hash = iFlow.addStep(step, blank, blank, new String[] { "ROLE_DIR_ADJ" }, blank, blank, false, true);

		log.info("step  => " + step + " : " + hash);

	}

	private void secondStep(String step, String before) throws Exception {
		//paso_02_01
		String[] docMandatory = { "doc_02_01_01",  };
		String[] docOption = { "doc_ass_02_01_01_01", "doc_02_01_02", "doc_ass_02_01_01_02", "doc_ass_02_01_01_03", "doc_02_01_03", "doc_ass_02_01_03_01",
				"doc_ass_02_01_03_02", "doc_ass_02_01_03_03" };
		String[] rolesAuh = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ", "ROLE_DIR_ADJ", "ROLE_DIR_EJE", "ROLE_ASI_DIR_EJE",
				"ROLE_COO_TEC" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		//documento de licitacion
		String[] rolesDoc = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ", "ROLE_DIR_ADJ", "ROLE_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_01_01",rolesDoc, true);
		
		//no objecion de documento de licitacion
		String[] rolesDoc1 = { "ROLE_ASI_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_01_01_01", rolesDoc1, true);
		
		//respuesta de no objecion de documento de licitacion
		String[] rolesDoc7 = { "ROLE_ASI_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_01_01_03", rolesDoc7, true);
		
		
		//pedido de aclaratoria
		String[] rolesDoc2 = { "ROLE_ESP_ADQ","ROLE_COO_TEC" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_01_02", rolesDoc2, true);
		
		//respuesta de aclaratoria
		String[] rolesDoc3 = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ","ROLE_COO_TEC", "ROLE_DIR_ADJ", "ROLE_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_01_01_02", rolesDoc3, true);
		
		//pedido de enmienda
		String[] rolesDoc4 = {"ROLE_ESP_ADQ"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_01_03", rolesDoc4, true);
		
		//respuesta de enmienda
		String[] rolesDoc5 = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ","ROLE_COO_TEC", "ROLE_DIR_ADJ", "ROLE_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_01_03_01", rolesDoc5, true);

		//no objecion enmienda
		String[] rolesDoc6 = {"ROLE_ASI_DIR_EJE"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_01_03_02", rolesDoc6, true);
		
		String[] rolesDoc8 = {"ROLE_ASI_DIR_EJE"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_01_03_03", rolesDoc8, true);
		
		
		
		log.info("step  => " + step + " : " + hash);

	}

	private void thirdStep(String step, String before) throws Exception {
		//paso_02_02
		String[] docMandatory = {};
		String[] docOption = {"doc_02_02_01"};
		String[] rolesAuh = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		String[] rolesDoc1 = { "ROLE_ESP_ADQ","ROLE_DIR_ADQ" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_02_01", rolesDoc1, true);
		//iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_02_01", "ROLE_DIR_ADQ", true);
		
		log.info("step  => " + step + " : " + hash);

	}

	private void fourthStep(String step, String before) throws Exception {
		//paso_02_03
		String[] docMandatory = {};
		String[] docOption = {};
		String[] rolesAuh = { "ROLE_DIR_ADJ" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		log.info("step  => " + step + " : " + hash);

	}

	private void fifthStep(String step, String before) throws Exception {
		//paso_02_04
		String[] docMandatory = {  };
		String[] docOption = { "doc_02_04_01", "doc_ass_02_04_01_01", "doc_ass_02_04_01_02"};
		String[] rolesAuh = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ", "ROLE_DIR_ADJ", "ROLE_DIR_EJE", "ROLE_ASI_DIR_EJE" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);
		//informe de evaluación
		String[] rolesDoc1 = { "ROLE_ESP_ADQ","ROLE_DIR_ADQ","ROLE_DIR_ADJ","ROLE_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_04_01",rolesDoc1, true);

		// Objeción BID al Informe de Evaluación
		String[] rolesDoc2 = { "ROLE_ASI_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_04_01_01", rolesDoc2, true);

		String[] rolesDoc3 = { "ROLE_ASI_DIR_EJE" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_02_04_01_02", rolesDoc3, true);

		
		log.info("step  => " + step + " : " + hash);

	}

	private void sixthStep(String step, String before) throws Exception {
		//paso_02_05
		String[] docMandatory = {};
		String[] docOption = {};
		String[] rolesAuh = { "ROLE_DIR_ADM_FIN" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		log.info("step  => " + step + " : " + hash);

	}

	private void seventhStep(String step, String before) throws Exception {
		//paso_02_06
		String[] docMandatory = {  };
		String[] docOption = {"doc_02_06_01"};
		String[] rolesAuh = { "ROLE_ESP_ADQ", "ROLE_DIR_ADQ", "ROLE_DIR_ADJ", "ROLE_DIR_EJE" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);
		
		//Resolución de Adjudicación
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_06_01", rolesAuh, true);

		log.info("step  => " + step + " : " + hash);

	}

	private void eighthStep(String step, String before) throws Exception {
		//paso_02_07
		String[] docMandatory = {  };
		String[] docOption = {"doc_02_07_01"};
		String[] rolesAuh = { "ROLE_ESP_ADQ", "ROLE_DIR_ADJ","ROLE_DIR_ADQ","ROLE_DIR_EJE" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);
		//adjunta contrato firmado
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_02_07_01", rolesAuh, true);
		
		log.info("step  => " + step + " : " + hash);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void ninethStep(String step, String before) throws Exception {
		//paso_03_01
		String[] docMandatory = {};
		String[] docOption = {};
		String[] rolesAuh = { "ROLE_COO_TEC" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		log.info("step  => " + step + " : " + hash);

	}

	private void tenthStep(String step, String before) throws Exception {
		//paso_03_02
		String[] docMandatory = {};
		String[] docOption = {};
		String[] rolesAuh = { "ROLE_COO_TEC" };
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		log.info("step  => " + step + " : " + hash);

	}

	private void eleventhStep(String step, String before) throws Exception {
		//paso_03_03
		String[] docMandatory = {};
		String[] docOption = { "doc_03_03_01", "doc_ass_03_03_01_01", "doc_ass_03_03_01_02", "doc_03_03_02",
				"doc_ass_03_03_02_01", "doc_ass_03_03_02_02", "doc_03_03_03", "doc_ass_03_03_03_01",
				"doc_ass_03_03_03_02" , "doc_03_03_04" };
		String[] rolesAuh = { "ROLE_CONT", "ROLE_COO_TEC", "ROLE_SUP", "ROLE_DIR_LEG", "ROLE_DIR_TRA",
				"ROLE_DIR_ADM_FIN" };
		
		String[] especificRolesAuth = { "ROLE_CONT", "ROLE_SUP"};
		
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, especificRolesAuth, false, false);
		//iFlow.setProjectSpecificRoles(step, rolesAuh, true );
		
		//Solicitud de Anticipo de Pago
		String[] docRole1 = {"ROLE_CONT","ROLE_DIR_LEG","ROLE_COO_TEC", "ROLE_DIR_TRA"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_03_03_01", docRole1, true);
		
		// F01 de Anticipo
		String[] docRole2 = {"ROLE_DIR_ADM_FIN"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_03_03_01_01", docRole2, true);
		
		//Comprobante de Pago de la Solicitud de Anticipo
		String[] docRole3 = {"ROLE_DIR_ADM_FIN"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_03_03_01_02", docRole3, true);
		
		//Solicitud de Estimacion de Pago
		String[] docRole4 = {"ROLE_CONT", "ROLE_SUP","ROLE_COO_TEC", "ROLE_DIR_TRA" };
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_03_03_02", docRole4, true);

		// F01 de Estimacion
		String[] docRole5 = {"ROLE_DIR_ADM_FIN"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_03_03_02_01", docRole5, true);
		//Comprobante de Pago de la Estimacion de Pago
		String[] docRole6 = {"ROLE_DIR_ADM_FIN"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_03_03_02_02", docRole6, true);
		
		
		//Solicitud de Pago Final 
		String[] docRole7 = {"ROLE_CONT","ROLE_SUP","ROLE_DIR_LEG","ROLE_COO_TEC", "ROLE_DIR_TRA"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_03_03_03", docRole7, true);

		//  F01 de Pago Final
		String[] docRole8 = {"ROLE_DIR_ADM_FIN"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_03_03_03_01", docRole8, true);
		//Comprobante de Pago del Pago Final
		String[] docRole9 = {"ROLE_DIR_ADM_FIN"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_ass_03_03_03_02", docRole9, true);

		//Modificacion de Contrato
		String[] docRole10 = {"ROLE_CONT", "ROLE_SUP","ROLE_DIR_LEG","ROLE_COO_TEC","ROLE_DIR_TRA"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_03_03_04", docRole10, true);
		
		log.info("step  => " + step + " : " + hash);

	}

	private void twelfthStep(String step, String before) throws Exception {
		//paso_03_04
		String[] docMandatory = {};
		String[] docOption = { "doc_03_04_01" };
		String[] rolesAuh = { "ROLE_CONT", "ROLE_COO_TEC", "ROLE_SUP", "ROLE_DIR_LEG", "ROLE_DIR_TRA"};
		String[] stepBefore = new String[] { before };
		String[] especificRolesAuth = { "ROLE_CONT", "ROLE_SUP"};
		
		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, especificRolesAuth, false, false);
		
	//	hash = iFlow.setProjectSpecificRoles(step, rolesAuh, true );
		
		//Modificacion de Contrato
		String[] docRole1 = {"ROLE_CONT", "ROLE_SUP","ROLE_DIR_LEG","ROLE_COO_TEC","ROLE_DIR_TRA"};
		iFlow.setStatusAuthorizedDocumentRole(step, "doc_03_04_01", docRole1, true);
		
		log.info("step  => " + step + " : " + hash);

	}
	
	private void thirteenthStep(String step, String before) throws Exception {
		//paso_03_05
		//Validar Garantia de Calidad
		String[] docMandatory = {};
		String[] docOption = {  };
		String[] rolesAuh = { "ROLE_COO_TEC"};
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, false, false);

		log.info("step  => " + step + " : " + hash);

	}
	
	
	private void fourteenthStep(String step, String before) throws Exception {
		//fase_03 
		String[] docMandatory = {};
		String[] docOption = {  };
		String[] rolesAuh = { "ROLE_COO_TEC"};
		String[] stepBefore = new String[] { before };

		String hash = iFlow.addStep(step, docMandatory, docOption, rolesAuh, stepBefore, blank, true, false);

		log.info("step  => " + step + " : " + hash);

	}

}
