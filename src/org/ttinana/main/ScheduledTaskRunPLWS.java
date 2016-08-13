/**
 * 
 */
package org.ttinana.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.ttinana.service.EntitySOAPeDAOServiceImpl;
import org.ttinana.soap.ArrayOfPrivredniSubjekat;
import org.ttinana.soap.PlServicestub;
import org.ttinana.soap.PrivredniSubjekat;

/**
 * @author tijana.pavicic
 *
 */

public class ScheduledTaskRunPLWS {

	private final int period = 20;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
	@Autowired(required = false)
	static Logger logger = LoggerFactory.getLogger(ScheduledTaskRunPLWS.class);

	public void startScheduleTask() {
		ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				try {
					logger.info(" |INFO| Starting client PLWS by Tijana.");
					System.out.println(" |INFO| Starting client PLWS.");
					executeSceduledPLWS();
				} catch (Exception ex) {
					logger.error("|ERROR| Starting client PLWS by Tijana. " + this.getClass() + ex);
					ex.printStackTrace();
				}
				endingClient();
			}
		}, 0, period, TimeUnit.MINUTES);
		logger.info(" |INFO| |TASKHANDLE| ");
		taskHandle.getDelay(TimeUnit.MINUTES);
	}

	private void executeSceduledPLWS() throws IOException {
		try {
			XTrustProvider.install();
			logger.info(" |INFO| running client side by Tijana. ");
			runClientside(getApplicationContext());
			// runClientsideARH(getApplicationContext());
		} catch (DatatypeConfigurationException ex) {
			logger.error(" |ERROR| running client side by Tijana. " + ex);
			// ex.printStackTrace();
		}
		logger.info(" |INFO| Executing services on APR.");
	}

	private void runClientside(ApplicationContext applicationContext)
			throws IOException, DatatypeConfigurationException {

		PlServicestub plStub = new PlServicestub();
		JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
		EntitySOAPeDAOServiceImpl entitySOAPeDAOServiceImpl = (EntitySOAPeDAOServiceImpl) applicationContext
				.getBean("entitySOAPService");

		// ovde pozivamo prvu funkciju
		ArrayOfPrivredniSubjekat listOfPS = preuzmiPromenePodatakaOPrivrednimSubjektima(plStub);

		try {
			// ovde insertujemo u promene
			entitySOAPeDAOServiceImpl.insertListPromene(listOfPS, jdbcTemplate);
			logger.info(" |INFO| Insertovano u tabelu Promene je uspešno izvršen. ");
			
			// ovde pozivamo drugu funkciju
			/*ArrayOfPrivredniSubjekat listOfPSAll =preuzmiSvePodatakeOPrivrednimSubjektima(plStub,listOfPS);		
			for(PrivredniSubjekat p:listOfPSAll.getPrivredniSubjekat()){				
				System.out.println(p.getMaticniBroj());				
			}
			entitySOAPeDAOServiceImpl.insertListAll(listOfPSAll, jdbcTemplate);*/

			ArrayOfPrivredniSubjekat psMBarray = null;
			for (PrivredniSubjekat ps : listOfPS.getPrivredniSubjekat()) {
				String mb = ps.getMaticniBroj();
				String tip = ps.getTip();
				psMBarray = podaciZaMaticniBroj(mb, tip, plStub);
				entitySOAPeDAOServiceImpl.insertZaJMB(psMBarray, jdbcTemplate);
			}

		} catch (ParseException e) {
			logger.error(" |ERROR| Podaci nisu insertovani u bazu. " + e);
		}
	}
	// TODO
	private void runClientsideInit(ApplicationContext applicationContext)
			throws IOException, DatatypeConfigurationException {

		PlServicestub plStub = new PlServicestub();
		JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
		EntitySOAPeDAOServiceImpl entitySOAPeDAOServiceImpl = (EntitySOAPeDAOServiceImpl) applicationContext
				.getBean("entitySOAPService");

		// ovde pozivamo prvu funkciju
		ArrayOfPrivredniSubjekat listOfPS = preuzmiPromenePodatakaOPrivrednimSubjektima(plStub);

		try {
			// ovde insertujemo u promene
			entitySOAPeDAOServiceImpl.insertListPromene(listOfPS, jdbcTemplate);
			logger.info(" |INFO| Insertovano u tabelu Promene je uspešno izvršen. ");			
		
			ArrayOfPrivredniSubjekat psMBarray = null;
			for (PrivredniSubjekat ps : listOfPS.getPrivredniSubjekat()) {
				String mb = ps.getMaticniBroj();
				String tip = ps.getTip();
				psMBarray = podaciZaMaticniBroj(mb, tip, plStub);
				entitySOAPeDAOServiceImpl.insertZaJMB(psMBarray, jdbcTemplate);
			}

		} catch (ParseException e) {
			logger.error(" |ERROR| Podaci nisu insertovani u bazu. " + e);
		}
	}
	
	public static ArrayOfPrivredniSubjekat podaciZaMaticniBroj(String mb, String tip, PlServicestub plStub) {
		ArrayOfPrivredniSubjekat psMBarray;
		psMBarray = plStub.podaciZaMaticniBroj(mb, tip, plStub);
		return psMBarray;
	}

	public void runClientsideARH(ApplicationContext applicationContext) {
		JdbcTemplate jdbcTemplate = getApplicationContext().getBean("jdbcTemplate", JdbcTemplate.class);
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("AprCRHV").withSchemaName("dbo")
				.withProcedureName("DnevnaObradaImportovanih").withoutProcedureColumnMetaDataAccess();
		jdbcCall.execute();
	}

	public static ArrayOfPrivredniSubjekat preuzmiPromenePodatakaOPrivrednimSubjektima(PlServicestub plStub) {
		ArrayOfPrivredniSubjekat listOfPS;
		try {
			listOfPS = plStub.preuzmiPromenePodatakaOPrivrednimSubjektima();
		} catch (DatatypeConfigurationException e) {
			listOfPS = null;
			logger.error(" |ERROR| Exception in sviPodaciOPrivrednimsubjektimaObj: " + e);
		}
		return listOfPS;
	}
	
	public static ArrayOfPrivredniSubjekat preuzmiSvePodatakeOPrivrednimSubjektima(PlServicestub plStub, ArrayOfPrivredniSubjekat listOfPS) {
		List<ArrayOfPrivredniSubjekat> listReturned;
		try {
			// TODO
			listReturned = plStub.preuzmiPodatkeOPrivrednomSubjektu(listOfPS);
		} catch (DatatypeConfigurationException e) {
			listOfPS = null;
			logger.error(" |ERROR| Exception in sviPodaciOPrivrednimsubjektimaObj: " + e);
		}
		return listOfPS;
	}

	public static void endingClient() {
		((AbstractApplicationContext) getApplicationContext()).registerShutdownHook();
		logger.info(" |INFO| Finishing client PLWS by Tijana.");
		System.out.println(" |INFO| Finishing client PLWS by Tijana.");
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
