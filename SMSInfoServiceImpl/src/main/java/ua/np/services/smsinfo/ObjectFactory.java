
package ua.np.services.smsinfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ua.np.smsinfo.client package. 
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

    private final static QName _Root_QNAME = new QName("http://goldentele.com/cpa", "root");
    private final static QName _Report_QNAME = new QName("http://goldetele.com/cpa", "report");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ua.np.smsinfo.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KyivstarSendRequest }
     * 
     */
    public KyivstarSendRequest createKyivstarSendRequest() {
        return new KyivstarSendRequest();
    }

    /**
     * Create an instance of {@link KyivstarMessageBody }
     * 
     */
    public KyivstarMessageBody createBodyType() {
        return new KyivstarMessageBody();
    }

    /**
     * Create an instance of {@link KyivstarMessage }
     * 
     */
    public KyivstarMessage createMessageType() {
        return new KyivstarMessage();
    }

    /**
     * Create an instance of {@link KyivstarMessagesType }
     * 
     */
    public KyivstarMessagesType createMessagesType() {
        return new KyivstarMessagesType();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link KyivstarSendRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://goldentele.com/cpa", name = "root")
    public JAXBElement<KyivstarSendRequest> createKyivstarSendRequest( KyivstarSendRequest value ) {
        return new JAXBElement<KyivstarSendRequest>(_Root_QNAME, KyivstarSendRequest.class, null, value);
    }

    /**
     * Create an instance of {@link KyivstarAcceptanceResponse }
     *
     */
    public KyivstarAcceptanceResponse createKyivstarAcceptanceResponse() {
        return new KyivstarAcceptanceResponse();
    }

    /**
     * Create an instance of {@link KyivstarAcceptanceStatus }
     *
     */
    public KyivstarAcceptanceStatus createStatusType() {
        return new KyivstarAcceptanceStatus();
    }

    public KyivstarAcceptanceStatus createStatusType(String mid, String clid, String value) {
        return new KyivstarAcceptanceStatus( mid, clid, value );
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KyivstarAcceptanceResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://goldetele.com/cpa", name = "report")
    public JAXBElement<KyivstarAcceptanceResponse> createKyivstarAcceptanceResponse( KyivstarAcceptanceResponse value ) {
        return new JAXBElement<KyivstarAcceptanceResponse>(_Report_QNAME, KyivstarAcceptanceResponse.class, null, value);
    }

}
