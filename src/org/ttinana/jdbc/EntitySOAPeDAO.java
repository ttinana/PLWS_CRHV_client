package org.ttinana.jdbc;

/**
 * @author tijana.pavicic
 *
 */

import java.text.ParseException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ttinana.soap.ArrayOfPrivredniSubjekat;
import org.ttinana.soap.PrivredniSubjekat;

public interface EntitySOAPeDAO {

	public void insertListPromene(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate) throws ParseException;

	public void insertListAll(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate);
	
	public void insertZaJMB(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate);

}
