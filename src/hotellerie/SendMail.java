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
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author ASUS
 */
public class SendMail {

    public static void sendmail(Client e, Reservation r) {
        try {
            System.out.println("\n Envoie du Mail en Cours...");
            Properties properties = new Properties();
            // les propriétés du serveur mail
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            // les coordonnées du mail qui va envoyer le msg
            String myEmailAcount = "hotelmonacoresort@gmail.com";
            String myPasswordAccount = "ahmed1920";

            //ouvrir une session
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myEmailAcount, myPasswordAccount);
                }
            });

            Message message = prepareMessage(session, myEmailAcount, e, r);

            //transport the message 
            Transport.send(message);
            System.out.println("E-mail envoyé");
        } catch (MessagingException err) {
            err.getMessage();
        }
    }

    private static Message prepareMessage(Session session, String myEmailAcount, Client e, Reservation r) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmailAcount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
            message.setSubject("Confirmation de reservation");
            String Code = "Bonjour " + e.getNom() + " " + e.getPrenom() + ", \n Votre Num de reservation : " + r.getNum_R() + "\n Date d'arrivée : " + r.getDate_Arrivee() + " \n Votre Cin : " + r.getCin_client() + " \n Num Chambre : " + r.getNb_chambre() + " \n Nombre de semaine : " + r.getNb_semaine() + "\n Prix Total : " + r.getPrix_total() + "\n Reste à payer : " + r.getReste_payer();
            message.setText(Code);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
