
package com.HotFactury.CWSFH.Prod;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.certus.facturehoy.ws4.cfdi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EmisionTimbradoException_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "EmisionTimbradoException");
    private final static QName _EmitirTimbrar_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "EmitirTimbrar");
    private final static QName _EmitirTimbrarResponse_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "EmitirTimbrarResponse");
    private final static QName _RecuperarAcuse_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "RecuperarAcuse");
    private final static QName _RecuperarAcuseCancelacion_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "RecuperarAcuseCancelacion");
    private final static QName _RecuperarAcuseCancelacionResponse_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "RecuperarAcuseCancelacionResponse");
    private final static QName _RecuperarAcuseResponse_QNAME = new QName("http://cfdi.ws4.facturehoy.certus.com/", "RecuperarAcuseResponse");
    private final static QName _EmitirTimbrarXml_QNAME = new QName("", "xml");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.certus.facturehoy.ws4.cfdi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EmisionTimbradoException }
     * 
     */
    public EmisionTimbradoException createEmisionTimbradoException() {
        return new EmisionTimbradoException();
    }

    /**
     * Create an instance of {@link EmitirTimbrar }
     * 
     */
    public EmitirTimbrar createEmitirTimbrar() {
        return new EmitirTimbrar();
    }

    /**
     * Create an instance of {@link EmitirTimbrarResponse }
     * 
     */
    public EmitirTimbrarResponse createEmitirTimbrarResponse() {
        return new EmitirTimbrarResponse();
    }

    /**
     * Create an instance of {@link RecuperarAcuse }
     * 
     */
    public RecuperarAcuse createRecuperarAcuse() {
        return new RecuperarAcuse();
    }

    /**
     * Create an instance of {@link RecuperarAcuseCancelacion }
     * 
     */
    public RecuperarAcuseCancelacion createRecuperarAcuseCancelacion() {
        return new RecuperarAcuseCancelacion();
    }

    /**
     * Create an instance of {@link RecuperarAcuseCancelacionResponse }
     * 
     */
    public RecuperarAcuseCancelacionResponse createRecuperarAcuseCancelacionResponse() {
        return new RecuperarAcuseCancelacionResponse();
    }

    /**
     * Create an instance of {@link RecuperarAcuseResponse }
     * 
     */
    public RecuperarAcuseResponse createRecuperarAcuseResponse() {
        return new RecuperarAcuseResponse();
    }

    /**
     * Create an instance of {@link WsResponseBO }
     * 
     */
    public WsResponseBO createWsResponseBO() {
        return new WsResponseBO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmisionTimbradoException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "EmisionTimbradoException")
    public JAXBElement<EmisionTimbradoException> createEmisionTimbradoException(EmisionTimbradoException value) {
        return new JAXBElement<EmisionTimbradoException>(_EmisionTimbradoException_QNAME, EmisionTimbradoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmitirTimbrar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "EmitirTimbrar")
    public JAXBElement<EmitirTimbrar> createEmitirTimbrar(EmitirTimbrar value) {
        return new JAXBElement<EmitirTimbrar>(_EmitirTimbrar_QNAME, EmitirTimbrar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmitirTimbrarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "EmitirTimbrarResponse")
    public JAXBElement<EmitirTimbrarResponse> createEmitirTimbrarResponse(EmitirTimbrarResponse value) {
        return new JAXBElement<EmitirTimbrarResponse>(_EmitirTimbrarResponse_QNAME, EmitirTimbrarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarAcuse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "RecuperarAcuse")
    public JAXBElement<RecuperarAcuse> createRecuperarAcuse(RecuperarAcuse value) {
        return new JAXBElement<RecuperarAcuse>(_RecuperarAcuse_QNAME, RecuperarAcuse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarAcuseCancelacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "RecuperarAcuseCancelacion")
    public JAXBElement<RecuperarAcuseCancelacion> createRecuperarAcuseCancelacion(RecuperarAcuseCancelacion value) {
        return new JAXBElement<RecuperarAcuseCancelacion>(_RecuperarAcuseCancelacion_QNAME, RecuperarAcuseCancelacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarAcuseCancelacionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "RecuperarAcuseCancelacionResponse")
    public JAXBElement<RecuperarAcuseCancelacionResponse> createRecuperarAcuseCancelacionResponse(RecuperarAcuseCancelacionResponse value) {
        return new JAXBElement<RecuperarAcuseCancelacionResponse>(_RecuperarAcuseCancelacionResponse_QNAME, RecuperarAcuseCancelacionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarAcuseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfdi.ws4.facturehoy.certus.com/", name = "RecuperarAcuseResponse")
    public JAXBElement<RecuperarAcuseResponse> createRecuperarAcuseResponse(RecuperarAcuseResponse value) {
        return new JAXBElement<RecuperarAcuseResponse>(_RecuperarAcuseResponse_QNAME, RecuperarAcuseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "xml", scope = EmitirTimbrar.class)
    public JAXBElement<byte[]> createEmitirTimbrarXml(byte[] value) {
        return new JAXBElement<byte[]>(_EmitirTimbrarXml_QNAME, byte[].class, EmitirTimbrar.class, ((byte[]) value));
    }

}
