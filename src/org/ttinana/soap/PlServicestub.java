/**
 * 
 */
package org.ttinana.soap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ttinana.main.HeaderHandlerResolver;
import org.ttinana.main.ScheduledTaskRunPLWS;

/**
 * @author tijana.pavicic
 *
 */
public class PlServicestub {

	PlService myClient = new PlService();
	static Logger logger = LoggerFactory.getLogger(ScheduledTaskRunPLWS.class);

	public Sifarnik sviPodaciODelatnostima() {

		HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
		myClient.setHandlerResolver(handlerResolver);

		myClient.getCustomBindingIPlService();
		Sifarnik p = myClient.getCustomBindingIPlService().sviPodaciODelatnostima();
		return p;
	}

	public Sifarnik sviPodaciODrzavama() {

		HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
		myClient.setHandlerResolver(handlerResolver);

		QName s = myClient.getServiceName();
		String sa = s.getLocalPart();
		logger.debug(sa);
		Sifarnik p = myClient.getCustomBindingIPlService().sviPodaciODrzavama();
		return p;

	}

	// //////////////////////**********************************************************************************
	public String sviPodaciOPrivrednimsubjektimaTest() throws DatatypeConfigurationException {

		HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
		myClient.setHandlerResolver(handlerResolver);

		// Date today = new Date();

		// GregorianCalendar
		// month - the value used to set the MONTH calendar field in the
		// calendar. Month value is 0-based. e.g.,
		// *** 0 for January ****

		/***********************************************/
		/**
		 * napomena ovo u javi 7 ne radi ali radi u javi 8 potrebno je zameniti
		 * verziju jave na projektu
		 */
		LocalDate today = LocalDate.now();
		Integer year = today.getYear();
		Integer month = today.getMonthValue();
		Integer day = today.getDayOfMonth();
		System.out.println(today);
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);
		/***********************************************/

		GregorianCalendar pocetak = new GregorianCalendar(2012, 10, 05);

		// ovo je zapravo 5. Novembar tj 5.11.
		GregorianCalendar krajnji = new GregorianCalendar(2012, 10, 06);
		// ovo je zapravo 6. Novembar tj 6.11.
		XMLGregorianCalendar pocetakXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(pocetak);
		XMLGregorianCalendar krajnjiXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(krajnji);

		PrivredniSubjektiPromenePoDatumu reqDates = new PrivredniSubjektiPromenePoDatumu();
		reqDates.datumOd = pocetakXML;
		reqDates.datumDo = krajnjiXML;

		/*
		 * ArrayOfPrivredniSubjekat listOfPS =
		 * myClient.getCustomBindingIPlService()
		 * .preuzmiPromenePodatakaOPrivrednimSubjektima(reqDates);
		 */

		return (" sviPodaciOPrivrednimsubjektimaTest method");

	}

	////////////////////////////////
	public ArrayOfPrivredniSubjekat preuzmiPromenePodatakaOPrivrednimSubjektima()
			throws DatatypeConfigurationException {

		HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
		myClient.setHandlerResolver(handlerResolver);

		// GregorianCalendar
		// month - the value used to set the MONTH calendar field in the
		// calendar. Month value is 0-based. e.g.,
		// *** 0 for January ****

		LocalDate today = LocalDate.now();
		Integer year = today.getYear();
		Integer month = today.getMonthValue();
		Integer day = today.getDayOfMonth();
		/* TODO SKINUTI FIKSNE DATUME ZA PRODUKCIJU*/
		System.out.println(today + " year: " + year + " month: " + month + " day: " + day);
		year = 2012;
		month = 11;
		day = 5;
		/* TODO*/

		GregorianCalendar pocetak = new GregorianCalendar(year, month - 1, day);
		// ovo je zapravo 5.Novembar tj 5.11.
		GregorianCalendar krajnji = new GregorianCalendar(year, month - 1, day + 1);

		XMLGregorianCalendar pocetakXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(pocetak);
		XMLGregorianCalendar krajnjiXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(krajnji);

		PrivredniSubjektiPromenePoDatumu reqDates = new PrivredniSubjektiPromenePoDatumu();
		reqDates.datumOd = pocetakXML;
		reqDates.datumDo = krajnjiXML;

		ArrayOfPrivredniSubjekat listOfPS = myClient.getCustomBindingIPlService()
				.preuzmiPromenePodatakaOPrivrednimSubjektima(reqDates);
		return listOfPS;

	}

	////////////////////////////////
	public List<ArrayOfPrivredniSubjekat> preuzmiPodatkeOPrivrednomSubjektu(ArrayOfPrivredniSubjekat listOfPS)
			throws DatatypeConfigurationException {

		List<ArrayOfPrivredniSubjekat> res =null;

		ArrayOfPrivredniSubjekat psMBarray = null;
		for (PrivredniSubjekat p : listOfPS.getPrivredniSubjekat()) {
			PrivredniSubjekatMaticniBroj privredniSubjekatMaticniBroj = new PrivredniSubjekatMaticniBroj();
			privredniSubjekatMaticniBroj.setMaticniBroj(p.getMaticniBroj());
			privredniSubjekatMaticniBroj.setTip(p.getTip());
			//ArrayOfPrivredniSubjekat t =podaciZaMaticniBroj(p.getMaticniBroj(),  p.getTip(),); 
			//res.add(t);

		}

		return res;

	}

	public List<Zapis> sviPodaciODrzavamaObj() throws ParserConfigurationException {
		HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
		myClient.setHandlerResolver(handlerResolver);

		Sifarnik mySifarnik = myClient.getCustomBindingIPlService().sviPodaciODrzavama();

		Zapis zapis = new Zapis();
		List<Zapis> sifarnik = new ArrayList<Zapis>();

		for (Zapis obj : mySifarnik.getZapis()) {
			for (Podatak podatak : obj.getPodatak()) {
				Podatak prolaz = new Podatak();
				prolaz.setNaziv(podatak.getNaziv());
				prolaz.setVrednost(podatak.getVrednost());
				zapis.getPodatak().add(prolaz);

			}
			sifarnik.add(zapis);

		}
		return sifarnik;
	}

	public ArrayOfPrivredniSubjekat podaciZaMaticniBroj(String mb, String tip, PlServicestub plStub) {

		ArrayOfPrivredniSubjekat psMBarray;
		PrivredniSubjekatMaticniBroj privredniSubjekatMaticniBroj = new PrivredniSubjekatMaticniBroj();
		privredniSubjekatMaticniBroj.setMaticniBroj(mb);
		privredniSubjekatMaticniBroj.setTip(tip);

		PrivredniSubjektiUlazniPodaci privredniSubjektiUlazniPodaci = new PrivredniSubjektiUlazniPodaci();
		privredniSubjektiUlazniPodaci.setPrivredniSubjekti(privredniSubjekatMaticniBroj);

		psMBarray = myClient.getCustomBindingIPlService().preuzmiPodatkeOPrivrednomSubjektu(privredniSubjektiUlazniPodaci);

		return psMBarray;
	}

}
