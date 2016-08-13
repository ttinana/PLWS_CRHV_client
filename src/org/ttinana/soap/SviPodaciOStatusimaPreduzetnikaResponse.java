
package org.ttinana.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sifarnik" type="{http://BusinessEntityDataMediationModule/serviceExport/BusinessEntityDataInterface/}Sifarnik" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sifarnik"
})
@XmlRootElement(name = "SviPodaciOStatusimaPreduzetnikaResponse")
public class SviPodaciOStatusimaPreduzetnikaResponse {

    protected Sifarnik sifarnik;

    /**
     * Gets the value of the sifarnik property.
     * 
     * @return
     *     possible object is
     *     {@link Sifarnik }
     *     
     */
    public Sifarnik getSifarnik() {
        return sifarnik;
    }

    /**
     * Sets the value of the sifarnik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sifarnik }
     *     
     */
    public void setSifarnik(Sifarnik value) {
        this.sifarnik = value;
    }

}
