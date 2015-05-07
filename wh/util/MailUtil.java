/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wh.util;

/**
 *
 * @author mathan
 */
import java.util.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
import java.io.*;

//WORKHTML IMPORT
import wh.util.CommonUtil;

public class MailUtil implements DataSource {

    private ByteArrayOutputStream baos;
    private String contenttype;

    public MailUtil(){
	}
    
    public MailUtil(ByteArrayOutputStream bos, String type) {
        baos = bos;
        contenttype = type;
    }

//    public String despatch(String subject, String email, String content,
//            String contentType, String sender) {
//        String result = despatch(subject, email, content, contentType, sender,
//                null, null, null);
//        return result;
//    }
//
//    public String despatch(String subject, String email, String content,
//            String contentType, String sender) {
//        String result = despatch(subject, email, content, contentType, sender,
//                (ByteArrayOutputStream) null, (String) null, false, (String) null);
//        return result;
//    }
//
//    public String despatch(String subject, String email, String content,
//            String contentType, String sender, ByteArrayOutputStream bos,
//            String filename, String senderDesc) {
//        String result = despatch(subject, email, content, contentType, sender, bos, filename, false, senderDesc);
//        return result;
//    }
//
//    public String despatch(String subject, String email, String content,
//            String contentType, String sender, ByteArrayOutputStream bos,
//            String filename, boolean isBcc, String senderDesc) {
//        String result = despatch(subject, email, content, contentType, sender, bos, filename, isBcc, senderDesc, (String) null, (ArrayList) null);
//        return result;
//    }

    public String despatch(String subject, String email, String content,
            String contentType, String sender, ByteArrayOutputStream bos,
            String filename, boolean isBcc, String senderDesc, String ccMailIds, ArrayList fileDetails) {
        //contentType is not used here  
        //System.out.println("=== === === email === === " + email);
        String[] emailIds = new String[]{};
        if (email != null) {
            emailIds = email.split(",");
        }
        String result = "";
        String isLocalbuild = (String) CommonUtil.getInstance().getWHPropertiesVal("isLocalbuild");
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (isLocalbuild != null && Boolean.valueOf(isLocalbuild)) {
            logger.info("\n***********************\n" + content + "\n***********************\n");//No I18N
            return "success";//No I18N
        }
        try {

            //GET MAIL SEVER DETAILS FROM PROPERTIES FILE WORKHTML.PROPERTIES(path: /workhtml/workhtml.properties)
//            String serverName = (String) CommonUtil.getInstance().getWHPropertiesVal("smtp_host");
//            String userName = (String) CommonUtil.getInstance().getWHPropertiesVal("smtp_user_name");
//            String fromAddress = (String) CommonUtil.getInstance().getWHPropertiesVal("deafultFromAddress");
//            String password = (String) CommonUtil.getInstance().getWHPropertiesVal("smtp_user_password");
//            System.out.println("BEFORE PORT ::::"+CommonUtil.getInstance().getWHPropertiesVal("smtp_port"));
//            int portId = Integer.valueOf((String)CommonUtil.getInstance().getWHPropertiesVal("smtp_port"));
//          
            //PREDEFAULT VARIABLE SET
            //TODO : HAVE TO GET DETAIL FROM PROP FILE AND SET TO prop()
            String serverName = "";
            String userName = "";
            String password = "";
            String fromAddress = "support@workhtml.com";
            int portId = 25;
            
            System.out.println("SENDING PORT >>>>>"+portId);

            //String server = (String) CommonUtil.getInstance().getWHPropertiesVal("mail_server_name");
            System.out.println("CommonUtil.getInstance().getWHPropertiesVal(\"deafultFromAddress\") ::::"+CommonUtil.getInstance().getWHPropertiesVal("deafultFromAddress"));
            Properties props = new Properties();
            props.put("mail.smtp.host", serverName);
            props.put("mail.smtp.port", portId);
            props.put("mail.smtp.localhost", "localhost");//Setting HELO
            props.put("mail.smtp.from", fromAddress);//Setting MAIL_FROM
            //props.put("mail.smtp.from", (String) CommonUtil.getInstance().getWHPropertiesVal("deafultFromAddress"));//Setting MAIL_FROM
            props.put("mail.smtp.timeout", 10000);
            System.out.println("props >>>>>>"+props);

            Authenticator authen = null;

            if (userName != null) {
                authen = new WHAuthenticator(userName, password);
                props.put("mail.smtp.auth", new Boolean(true));
                props.put("mail.smtp.user", userName);
            }
            Session session = Session.getDefaultInstance(props, authen);
            logger.info("Mail Session Properties :::" + session.getProperties());//No I18N

            Message msg = new MimeMessage(session);
            String senderName = sender;

            msg.setSubject(subject);
            InternetAddress fromAddr = null;
            if (senderName == null || senderName.equals("")) {
                fromAddr = new InternetAddress((String) CommonUtil.getInstance().getWHPropertiesVal("deafultFromAddress"), (String) CommonUtil.getInstance().getWHPropertiesVal("deafultMailSubject"));
            } else {
                fromAddr = new InternetAddress(senderName, senderDesc);
            }
            msg.setFrom(fromAddr);
            msg.setSentDate(new java.util.Date());

            InternetAddress[] toAdd = new InternetAddress[emailIds.length];
            for (int i = 0; i < emailIds.length; i++) {
                toAdd[i] = new InternetAddress(emailIds[i]);
            }
            //System.out.println("=== === === toAdd[0] === === " + toAdd[0]);
            if (isBcc == true) {
                msg.addRecipients(Message.RecipientType.BCC, toAdd);
            } else {
                msg.addRecipients(Message.RecipientType.TO, toAdd);
            }
            if (ccMailIds != null && !ccMailIds.trim().equals("")) {
                msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMailIds));
            }
            msg.addHeader("Content-Transfer-Encoding", "base64");

            if ((bos != null && filename != null && filename != "") || (fileDetails != null && !fileDetails.isEmpty())) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                messageBodyPart.setContent(content, "text/html");
                multipart.addBodyPart(messageBodyPart);

                //To attach Direct output stream
                if (bos != null && filename != null && filename != "") {
                    messageBodyPart = new MimeBodyPart();
                    MailUtil maildatasource = new MailUtil(bos, "application/octet-stream");//No I18N
                    messageBodyPart.setDataHandler(new javax.activation.DataHandler(maildatasource));
                    messageBodyPart.setFileName(filename);
                    multipart.addBodyPart(messageBodyPart);
                    msg.setContent(multipart);

                }

                // To Attach more than one file
                if (fileDetails != null && !fileDetails.isEmpty()) {
                    for (int i = 0; i < fileDetails.size(); i++) {
                        HashMap fileDetailsMap = (HashMap) fileDetails.get(i);
                        Object[] keys = ((Set) fileDetailsMap.keySet()).toArray();
                        String attachmentFileName = (String) keys[0];
                        ByteArrayOutputStream attachmentBos = (ByteArrayOutputStream) fileDetailsMap.get(attachmentFileName);
                        messageBodyPart = new MimeBodyPart();
                        MailUtil maildatasource2 = new MailUtil(attachmentBos, "application/octet-stream");//No I18N
                        messageBodyPart.setDataHandler(new javax.activation.DataHandler(maildatasource2));
                        messageBodyPart.setFileName(attachmentFileName);
                        multipart.addBodyPart(messageBodyPart);
                    }
                    msg.setContent(multipart);
                }
            } else {
                if (content.startsWith("<HTML>") || content.startsWith("<html>")) {
                    msg.setContent(content, "text/html" + "; charset=UTF-8");
                } else {
                    msg.setContent(content, "text/plain" + "; charset=UTF-8");
                }
            }

            Transport transport = session.getTransport("smtp"); // No
            // Internationalization
            //System.out.println("Before send :::");
            logger.info("Before send :::");//No I18N
            transport.send(msg);
            result = "success";//No I18N
            return result;
        } catch (SendFailedException smex) {
            logger.info("Error while Sending Mail :::: :::: :::: :::: " + smex.getMessage());//No I18N
            smex.printStackTrace();
            Address[] failedAddresses = smex.getInvalidAddresses();
            logger.info("=== === === === " + failedAddresses);//No I18N
            String failedAddrStr = null;//No I18N
            for (int i = 0; i < failedAddresses.length; i++) {
                if (failedAddrStr == null) {
                    failedAddrStr = failedAddresses[i].toString();//No I18N
                } else {
                    failedAddrStr = failedAddrStr + "," + failedAddresses[i].toString();//No I18N
                }
            }
             logger.severe("Error while sending email, Invalid Email Addresses - " + failedAddrStr);
            result = "Error while sending email, Invalid Email Addresses - " + failedAddrStr;//No I18N
            return result;
        } catch (Exception exp) {
            exp.printStackTrace();
            logger.severe("Error while Sending Mail." + exp.getMessage());//No I18N
            result = exp.getMessage();
            return result;
        }
    }

    public OutputStream getOutputStream() {
        return baos;
    }

    public String getName() {
        return null;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public String getContentType() {
        return contenttype;
    }

    class WHAuthenticator extends Authenticator {

        String userName = null;
        String password = null;

        public WHAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }
}
