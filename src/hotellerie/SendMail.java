/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ASUS
 */
public class SendMail {
    public static void sendmail(String recepient,String res) throws MessagingException
    {
        System.out.println("Envoie du Mail en Cours");
        Properties properties = new Properties();
        // les propriétés du serveur mail
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        // les coordonnées du mail qui va envoyer le msg
        String myEmailAcount = "maissa.soussi@ensi-uma.tn";
        String myPasswordAccount = "07484274";
                
        //ouvrir une session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override  
                    protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myEmailAcount,myPasswordAccount);
            }
        });
        
        Message message = prepareMessage(session,myEmailAcount, recepient,res);
        
        //transport the message 
        Transport.send(message);
        System.out.println("E-mail envoyé");
    }

    private static Message prepareMessage(Session session, String myEmailAcount, String recepient, String res){
       try 
       {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAcount));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject("Reservation");
        message.setText(res);
        return message;
       }
        catch(Exception ex)
       {
           Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE,null,ex);
       }
       return null;
    }
}
