package org.ttinana.service;

/**
 * @author tijana.pavicic
 *
 */

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ttinana.jdbc.EntitySOAPeDAO;
import org.ttinana.soap.ArrayOfPrivredniSubjekat;
import org.ttinana.soap.PrivredniSubjekat;

public class EntitySOAPeDAOServiceImpl implements EntitySOAPeDAOService {

	@Autowired
	EntitySOAPeDAO element;


	@Override
	public void insertListPromene(ArrayOfPrivredniSubjekat listOfPS,
			JdbcTemplate jdbcTemplate) throws ParseException{
		element.insertListPromene(listOfPS, jdbcTemplate);		
	}
	@Override
	public void insertListAll(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate) {
		element.insertListAll(listOfPS, jdbcTemplate);
		
	}
	@Override
	public void insertZaJMB(ArrayOfPrivredniSubjekat listOfPS, JdbcTemplate jdbcTemplate) {
		element.insertZaJMB(listOfPS, jdbcTemplate);
		
	}



}
