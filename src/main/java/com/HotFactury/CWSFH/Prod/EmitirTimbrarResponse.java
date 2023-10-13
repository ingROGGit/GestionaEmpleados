
package com.HotFactury.CWSFH.Prod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para EmitirTimbrarResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EmitirTimbrarResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://cfdi.ws4.facturehoy.certus.com/}wsResponseBO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmitirTimbrarResponse", propOrder = {
    "_return"
})
public class EmitirTimbrarResponse {

    @XmlElement(name = "return")
    protected WsResponseBO _return;

    /**
     * Obtiene el valor de la propiedad return.
     * 
     * @return
     *     possible object is
     *     {@link WsResponseBO }
     *     
     */
    public WsResponseBO getReturn() {
        return _return;
    }

    /**
     * Define el valor de la propiedad return.
     * 
     * @param value
     *     allowed object is
     *     {@link WsResponseBO }
     *     
     */
    public void setReturn(WsResponseBO value) {
        this._return = value;
    }

}