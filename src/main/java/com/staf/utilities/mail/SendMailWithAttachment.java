package com.staf.utilities.mail;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Class to send Email with attachments
 */
public class SendMailWithAttachment {

    /**
     * Logger
     */
    Logger log = Logger.getLogger(SendMailWithAttachment.class);

    /**
     * Send email with attachment
     *
     * @param mailSubject             - Mail subject
     * @param recipientMailTo         - To Recipients
     * @param recipientMailCc         - Cc Recipients
     * @param recipientMailBcc        - Bcc Recipients
     * @param attachmentFilePath      - File path of the attachment
     * @param messageBodyTextFilePath - File path that contains Message Body
     * @throws Exception - Exception
     */
    public void sendEmail
    (String mailSubject, String recipientMailTo,
     String recipientMailCc, String recipientMailBcc,
     String attachmentFilePath, String messageBodyTextFilePath)
            throws Exception {

        String[] crdntl = crdntls();
        String[] to, cc, bcc;

        final String sourceMail = crdntl[0];
        final String psd = crdntl[1];
        final String mailHost = crdntl[2];

        Session session = createSmtpSession(sourceMail, psd, mailHost);
        session.setDebug(true);

        // Define message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sourceMail));

        if (!recipientMailTo.equals("")) {
            recipientMailTo = recipientMailTo.trim();
            recipientMailTo = recipientMailTo.replaceAll(";", ",");

            if (recipientMailTo.contains(",")) {
                recipientMailTo = recipientMailTo.replaceAll(" ", "");

                to = recipientMailTo.split(",");

                for (String temp : to) {
                    if (temp.contains("@")) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(temp));
                    } else {
                        log.info(temp + " provided in the TO Field is not a valid e-mail address. " +
                                "Please enter a valid address.");
                    }
                }
            } else {
                if (recipientMailTo.contains("@")) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientMailTo));
                } else {
                    log.info(recipientMailTo + " provided in the TO Field is not a valid e-mail address. " +
                            "Please enter a valid address.");
                }
            }
        } else {
            // REPORT ERROR MESSAGE IF TO FIELD IS NULL
            log.info("TO Field is mandatory for an e-mail to be sent");
        }
        log.info("E Mail - To - Content Read successfully !!");

        if (!recipientMailCc.equals("")) {
            recipientMailCc = recipientMailCc.trim();
            recipientMailCc = recipientMailCc.replaceAll(";", ",");

            if (recipientMailCc.contains(",")) {
                recipientMailCc = recipientMailCc.replaceAll(" ", "");
                cc = recipientMailCc.split(",");

                for (String temp : cc) {
                    if (temp.contains("@")) {
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(temp));
                    } else {
                        log.info(temp + " provided in the cc Field is not a valid e-mail address. " +
                                "Please enter a valid address.");
                    }
                }
            } else {
                if (recipientMailCc.contains("@")) {
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipientMailCc));
                } else {
                    log.info(recipientMailCc + " provided in the cc Field is not a valid e-mail address. " +
                            "Please enter a valid address.");
                }
            }
        }
        log.info("E Mail - Cc - Content Read successfully !!");

        if (!recipientMailBcc.equals("")) {
            recipientMailBcc = recipientMailBcc.trim();
            recipientMailBcc = recipientMailBcc.replaceAll(";", ",");

            if (recipientMailBcc.contains(",")) {
                recipientMailBcc = recipientMailBcc.replaceAll(" ", "");

                bcc = recipientMailBcc.split(",");

                for (String temp : bcc) {
                    if (temp.contains("@")) {
                        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(temp));
                    } else {
                        log.info(temp + " provided in the bcc Field is not a valid e-mail address. " +
                                "Please enter a valid address.");
                    }
                }
            } else {
                if (recipientMailBcc.contains("@")) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipientMailBcc));
                } else {
                    log.info(recipientMailBcc + " provided in the bcc Field is not a valid e-mail address. " +
                            "Please enter a valid address.");
                }
            }
        }
        log.info("E Mail - Bcc - Content Read successfully !!");

        message.setSubject(mailSubject);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        //Fill the message
        String mailBodyContent = readFile(messageBodyTextFilePath);
        messageBodyPart.setContent(mailBodyContent, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentFilePath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(attachmentFilePath);
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);

        log.info("E Mail is successfully sent to the recipients specified....");
    }

    /**
     * Creates SMTP Session
     *
     * @param em       - Email address
     * @param psd      - Password
     * @param mailHost - Mail Host
     * @return the SMTP session
     */
    private Session createSmtpSession(final String em, final String psd, final String mailHost) {
        final Properties props = new Properties();
        props.setProperty("mail.host", mailHost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "" + 587);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        // props.setProperty("mail.debug", "true");

        return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(em, psd);
            }
        });
    }

    /**
     * Return the Credentials array
     *
     * @return credentials as String array
     * @throws Exception - Exception
     */
    public String[] crdntls()
            throws Exception {

        String[] crdntls = new String[5];
        String em = "", psd = "", mailHostAddress = "";
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ClassLoader.getSystemClassLoader()
                .getResourceAsStream("com/staf/security/Source.xml"));

        // Normalize text representation
        doc.getDocumentElement().normalize();

        NodeList listOfPersons = doc.getElementsByTagName("person");
        int totalPersons = listOfPersons.getLength();
        log.info("Total no of people : " + totalPersons);

        for (int s = 0; s < listOfPersons.getLength(); s++) {
            Node firstPersonNode = listOfPersons.item(s);
            if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
                Element firstPersonElement = (Element) firstPersonNode;

                NodeList firstNameList = firstPersonElement.getElementsByTagName("em");
                Element firstNameElement = (Element) firstNameList.item(0);
                NodeList textFNList = firstNameElement.getChildNodes();
                em = new com.security.AESDecrypt(textFNList.item(0).getNodeValue().trim()).decrypt();

                NodeList lastNameList = firstPersonElement.getElementsByTagName("psd");
                Element lastNameElement = (Element) lastNameList.item(0);
                NodeList textLNList = lastNameElement.getChildNodes();
                psd = new com.security.AESDecrypt(textLNList.item(0).getNodeValue().trim()).decrypt();

                NodeList mailHost = firstPersonElement.getElementsByTagName("mailhost");
                Element mailHostElement = (Element) mailHost.item(0);
                NodeList textLNList1 = mailHostElement.getChildNodes();
                mailHostAddress = textLNList1.item(0).getNodeValue().trim();
                log.info("Mail Host Address : " + mailHostAddress);
            }
        }
        crdntls[0] = em;
        crdntls[1] = psd;
        crdntls[2] = mailHostAddress;

        return crdntls;
    }

    /**
     * Reads the file
     *
     * @param filePath - File path
     * @return the contents of the file as String
     */
    public String readFile(String filePath) {
        String strLine = "";
        String currLineRead;

        try {
            // Open the file that is the first
            // Command line parameter
            FileInputStream fstream = new FileInputStream(filePath);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            //Read File Line By Line
            while ((currLineRead = br.readLine()) != null) {
                strLine = strLine + currLineRead;
            }

            //Close the input stream
            in.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return strLine;
    }
}