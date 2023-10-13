
package com.HotFactury.CWSFH.Prod;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para wsResponseBO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="wsResponseBO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="acuse" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="arregloAcuse" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="cadenaOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cadenaOriginalTimbre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codigoError" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="fechaHoraTimbrado" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="folioUDDI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isError" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PDF" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="rutaDescargaPDF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rutaDescargaXML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="selloDigitalEmisor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="selloDigitalTimbreSAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="XML" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsResponseBO", propOrder = {
    "acuse",
    "arregloAcuse",
    "cadenaOriginal",
    "cadenaOriginalTimbre",
    "codigoError",
    "fechaHoraTimbrado",
    "folioUDDI",
    "isError",
    "message",
    "pdf",
    "rutaDescargaPDF",
    "rutaDescargaXML",
    "selloDigitalEmisor",
    "selloDigitalTimbreSAT",
    "xml"
})
public class WsResponseBO {

    protected byte[] acuse;
    @XmlElement(nillable = true)
    protected List<byte[]> arregloAcuse;
    protected String cadenaOriginal;
    protected String cadenaOriginalTimbre;
    protected int codigoError;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaHoraTimbrado;
    protected String folioUDDI;
    protected Boolean isError;
    protected String message;
    @XmlElement(name = "PDF")
    protected byte[] pdf;
    protected String rutaDescargaPDF;
    protected String rutaDescargaXML;
    protected String selloDigitalEmisor;
    protected String selloDigitalTimbreSAT;
    @XmlElement(name = "XML")
    protected byte[] xml;

    /**
     * Obtiene el valor de la propiedad acuse.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getAcuse() {
        return acuse;
    }

    /**
     * Define el valor de la propiedad acuse.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setAcuse(byte[] value) {
        this.acuse = value;
    }

    /**
     * Gets the value of the arregloAcuse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arregloAcuse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArregloAcuse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * byte[]
     * 
     */
    public List<byte[]> getArregloAcuse() {
        if (arregloAcuse == null) {
            arregloAcuse = new ArrayList<byte[]>();
        }
        return this.arregloAcuse;
    }

    /**
     * Obtiene el valor de la propiedad cadenaOriginal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * Define el valor de la propiedad cadenaOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadenaOriginal(String value) {
        this.cadenaOriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad cadenaOriginalTimbre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadenaOriginalTimbre() {
        return cadenaOriginalTimbre;
    }

    /**
     * Define el valor de la propiedad cadenaOriginalTimbre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadenaOriginalTimbre(String value) {
        this.cadenaOriginalTimbre = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoError.
     * 
     */
    public int getCodigoError() {
        return codigoError;
    }

    /**
     * Define el valor de la propiedad codigoError.
     * 
     */
    public void setCodigoError(int value) {
        this.codigoError = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaHoraTimbrado.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaHoraTimbrado() {
        return fechaHoraTimbrado;
    }

    /**
     * Define el valor de la propiedad fechaHoraTimbrado.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaHoraTimbrado(XMLGregorianCalendar value) {
        this.fechaHoraTimbrado = value;
    }

    /**
     * Obtiene el valor de la propiedad folioUDDI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioUDDI() {
        return folioUDDI;
    }

    /**
     * Define el valor de la propiedad folioUDDI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioUDDI(String value) {
        this.folioUDDI = value;
    }

    /**
     * Obtiene el valor de la propiedad isError.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsError() {
        return isError;
    }

    /**
     * Define el valor de la propiedad isError.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsError(Boolean value) {
        this.isError = value;
    }

    /**
     * Obtiene el valor de la propiedad message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Obtiene el valor de la propiedad pdf.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPDF() {
        return pdf;
    }

    /**
     * Define el valor de la propiedad pdf.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPDF(byte[] value) {
        this.pdf = value;
    }

    /**
     * Obtiene el valor de la propiedad rutaDescargaPDF.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutaDescargaPDF() {
        return rutaDescargaPDF;
    }

    /**
     * Define el valor de la propiedad rutaDescargaPDF.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutaDescargaPDF(String value) {
        this.rutaDescargaPDF = value;
    }

    /**
     * Obtiene el valor de la propiedad rutaDescargaXML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutaDescargaXML() {
        return rutaDescargaXML;
    }

    /**
     * Define el valor de la propiedad rutaDescargaXML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutaDescargaXML(String value) {
        this.rutaDescargaXML = value;
    }

    /**
     * Obtiene el valor de la propiedad selloDigitalEmisor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelloDigitalEmisor() {
        return selloDigitalEmisor;
    }

    /**
     * Define el valor de la propiedad selloDigitalEmisor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelloDigitalEmisor(String value) {
        this.selloDigitalEmisor = value;
    }

    /**
     * Obtiene el valor de la propiedad selloDigitalTimbreSAT.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelloDigitalTimbreSAT() {
        return selloDigitalTimbreSAT;
    }

    /**
     * Define el valor de la propiedad selloDigitalTimbreSAT.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelloDigitalTimbreSAT(String value) {
        this.selloDigitalTimbreSAT = value;
    }

    /**
     * Obtiene el valor de la propiedad xml.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXML() {
        return xml;
    }

    /**
     * Define el valor de la propiedad xml.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXML(byte[] value) {
        this.xml = value;
    }

}
