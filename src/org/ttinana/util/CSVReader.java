package org.ttinana.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ttinana.soap.PrivredniSubjekat;

public class CSVReader {
	public List<PrivredniSubjekat> getValues(){
		
		String csvFile = "F:\\a\\apr.csv";
        String line = "";
        String cvsSplitBy = ",";
        List<PrivredniSubjekat> psList= new ArrayList<PrivredniSubjekat>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
            while ((line = br.readLine()) != null) {
            	PrivredniSubjekat myPS = new PrivredniSubjekat();
                // use comma as separator
                String[] ps = line.split(cvsSplitBy);
                myPS.setMaticniBroj(ps[0]);
                myPS.setTip(ps[1]);
                //System.out.println("mb= " + ps[0] + " , tip=" + ps[1]);
                psList.add(myPS);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return psList;

	}

}
