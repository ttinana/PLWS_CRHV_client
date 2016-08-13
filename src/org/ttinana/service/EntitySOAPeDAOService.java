package org.ttinana.service;

/**
 * @author tijana.pavicic
 *
 */

import java.text.ParseException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ttinana.soap.ArrayOfPrivredniSubjekat;

public interface EntitySOAPeDAOService {
	
	public void insertListPromene(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate) throws ParseException;
	public void insertListAll(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate);
	public void insertZaJMB(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate);

}
