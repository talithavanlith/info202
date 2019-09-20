
import org.apache.commons.mail.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vanta342
 */
public class EmailMain {

    /**
     * @param args the command line arguments
     * @throws org.apache.commons.mail.EmailException
     */
    public static void main(String[] args) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("localhost");
        email.setSmtpPort(2525);
        email.setFrom("user@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("foo@bar.com");
        email.send();
    }
    
}
