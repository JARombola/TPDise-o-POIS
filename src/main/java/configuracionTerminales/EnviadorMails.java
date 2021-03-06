package configuracionTerminales;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import procesos.Proceso;

public class EnviadorMails {
	
	private static EnviadorMails instancia;
	private JavaMailSender mailSender;
	public SimpleMailMessage mail;
	private Administrador adminInterno;
	
	public EnviadorMails(){
		mailSender=new JavaMailSenderImpl();
	}
	
	public static EnviadorMails getInstancia(){
		if (instancia==null) instancia=new EnviadorMails();
		return instancia;
	}
	
	
	public EnviadorMails(Administrador admin){
		adminInterno=admin;
		mailSender=new JavaMailSenderImpl();
	}
	
	
	
	public void mailBusquedaLenta(){
		this.mail=new SimpleMailMessage();
		this.mail.setFrom("Terminal");			//CHAN...
		this.mail.setSubject("Busqueda lenta");
		this.mail.setText("La busqueda tard� demasiado");
		this.mail.setTo(adminInterno.getEmail());
	}
	
	public void mailFallaProceso(Proceso proceso) {
	    String nombre = proceso.getClass().getName();
		mail=new SimpleMailMessage();
		mail.setFrom("Manejo de Resultados de los Procesos");			
		mail.setSubject("Error en el proceso "+nombre);
		mail.setText("El proceso "+nombre+" ejecutado el dia "+ proceso.getFecha()+ " Fallo su ejecucion.");
		mail.setTo(adminInterno.getEmail()); 
	}	
	
	
	public void enviarMail(){
		MimeMessage message = mailSender.createMimeMessage();			 			
		MimeMessageHelper helper;
		 try {		
			 helper = new MimeMessageHelper(message, true);
		 	 helper.setFrom(mail.getFrom());
		 	 helper.setTo(mail.getTo());
		 	 helper.setSubject(mail.getSubject());
		 	 helper.setText(mail.getText()); 
		 //	 mailSender.send(message);
		     System.out.println("Mail enviado");
		 					
		 	} catch (MessagingException e) {
		 		// TODO Auto-generated catch block
		 		e.printStackTrace();
		 	}
	}
	
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.mail = simpleMailMessage;
		}
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public static void setInstancia(EnviadorMails instancia) {
		EnviadorMails.instancia = instancia;
	}
}
