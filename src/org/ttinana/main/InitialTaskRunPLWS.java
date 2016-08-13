/**
 * 
 */
package org.ttinana.main;

import java.io.IOException;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ttinana.soap.PrivredniSubjekat;
import org.ttinana.util.CSVReader;
import org.ttinana.util.ReadExcelFile;

/**
 * @author tijana.pavicic
 *
 */
public class InitialTaskRunPLWS //implements ApplicationContextAware 
{
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
	static Logger logger = LoggerFactory.getLogger(ScheduledTaskRunPLWS.class);

	public void startInitialTask() {

		logger.info(" |INFO| Starting INITIAL client PLWS by Tijana.");
		try {
			executeInitialPLWS();
		} catch (IOException | DatatypeConfigurationException e) {
			logger.error("|ERROR| Greska pri inicijalnom punjenju!");
			e.printStackTrace();
		}
		System.out.println("|INFO| Starting INITIAL client PLWS.");

	}

	protected static void executeInitialPLWS() throws IOException, DatatypeConfigurationException {
		// TODO
		//List<PrivredniSubjekat> pSInicijalno = runInitially(getApplicationContext());
		List<PrivredniSubjekat> pSInicijalno = runInitiallyCSV(getApplicationContext());
		for (PrivredniSubjekat ps : pSInicijalno) {
			System.out.println(ps.getMaticniBroj() + " " + ps.getTip());
		}

		/*
		 * JdbcTemplate jdbcTemplate =
		 * applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
		 * 
		 * EntitySOAPeDAOServiceImpl entitySOAPeDAOServiceImpl =
		 * (EntitySOAPeDAOServiceImpl) applicationContext
		 * .getBean("entitySOAPService");
		 */
		/*
		 * entitySOAPeDAOServiceImpl.insertListInitially(pSInicijalno,
		 * jdbcTemplate);
		 * 
		 * logger.info(
		 * " |INFO| Insertovano u tabelu Promene je uspešno izvršen. ");
		 * 
		 * // entitySOAPeDAOServiceImpl.insertZaJMBInit(pSInicijalno, //
		 * jdbcTemplate);
		 * 
		 * ArrayOfPrivredniSubjekat psMBarray = null; for (PrivredniSubjekat ps
		 * : pSInicijalno) { String mb = ps.getMaticniBroj(); String tip =
		 * ps.getTip(); psMBarray = podaciZaMaticniBroj(mb, tip, plStub);
		 * entitySOAPeDAOServiceImpl.insertZaJMB(psMBarray, jdbcTemplate); }
		 */

		System.out.println("executeInitialPLWS");

	}

	public static List<PrivredniSubjekat> runInitially(ApplicationContext applicationContext)
			throws IOException, DatatypeConfigurationException {

		ReadExcelFile readExcelFile = applicationContext.getBean("readExcelFile", ReadExcelFile.class);
		List<PrivredniSubjekat> listOfPSInicijalno = readExcelFile.readPrivredniSubjekatFromExcel();
		return listOfPSInicijalno;

	}
	
	public static List<PrivredniSubjekat> runInitiallyCSV(ApplicationContext applicationContext)
			throws IOException, DatatypeConfigurationException {

		CSVReader reader = applicationContext.getBean("cSVReader", CSVReader.class);		
		List<PrivredniSubjekat> listOfPSInicijalno = reader.getValues();
		return listOfPSInicijalno;

	}

/*	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		InitialTaskRunPLWS.applicationContext = context;

	}*/

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
