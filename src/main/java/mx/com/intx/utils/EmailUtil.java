/**
 * mx.com.intx.utils
 */
package mx.com.intx.utils;

import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * @author INTX
 *
 */
public class EmailUtil extends Thread{

	private Properties properties;
    private String username;
    private String password;
    private String emails;
    private String subject;
    private String messageBody;
    private String fileAttach;
    private String nameAttach;
    
    /**
     * 
     * @param properties Propiedades de configuracion para el envio del correo
     * @param username Usuario del remitente
     * @param password Password del remitente
     * @param emails
     * @param subject
     * @param messageBody
     */
    public EmailUtil(Properties properties, String username, String password, String emails, String subject, String messageBody) {
        this(properties, username, password, emails, subject, messageBody, null, null);
    }
    
    public EmailUtil(Properties properties, String username, String password, String emails, String subject, String messageBody, String fileAttach, String nameAttach) {
        this.properties = properties;
        this.username = username;
        this.password = password;
        this.emails = emails;
        this.subject = subject; 
        this.messageBody = messageBody;
        this.fileAttach = fileAttach;
        this.nameAttach = nameAttach;
    }
    
    public void run() {
    	try {
			sendMail();
		} catch (AddressException e) {
    		LoggerIntx.printError(this.getClass(), e);
		} catch (MessagingException e) {
    		LoggerIntx.printError(this.getClass(), e);
		} catch (Exception e) {
    		LoggerIntx.printError(this.getClass(), e);
		}
    }
    
    /**
     * Metodo encargado de enviar el correo al remitente establecido en el constructor
     * @param recipients Destinatarios separados por comas
     * @param subject Asunto del correo
     * @param messageBody Cuerpo del correo (puede estar en formtato hmtl)
     * @return true en caso de exito, false en cualquier otro caso
     * @throws MessagingException 
     * @throws AddressException 
     */
    public boolean sendMail() throws AddressException, MessagingException {
    	Session session = Session.getInstance(properties, new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(username, password);
    		}
    	});
    	
    	Message message = new MimeMessage(session);
    	message.setFrom(new InternetAddress(username));
    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emails));
    	message.setSubject(subject);
    	
    	MimeBodyPart mimeBodyPart = new MimeBodyPart();
    	mimeBodyPart.setContent(messageBody, "text/html");
    	
    	Multipart multipart = new MimeMultipart();
    	multipart.addBodyPart(mimeBodyPart);
    	
    	// si se recibio un archivo para adjuntar
    	if (fileAttach != null) {
    		mimeBodyPart = new MimeBodyPart();
    		DataSource source = new FileDataSource(fileAttach);
    		mimeBodyPart.setDataHandler(new DataHandler(source));
    		mimeBodyPart.setFileName(nameAttach);
    		multipart.addBodyPart(mimeBodyPart);            	
    	}
    	
    	message.setContent(multipart);
    	
    	Transport.send(message);
    	return true;
    }

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
}

