package in.gadgethub.ulility;


public class MailMessage {

    public static void registrationSuccess(String recipientMailId, String name) throws javax.mail.MessagingException {
        String subject = "Registration Successfull";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Welcome to " + Appinfo.appName + "</h2>" + "" + "Hi " + name + ","
                + "<br><br>Thanks for singing up with " + Appinfo.appName + ".<br>"
                + "We are glad that you choose us. We invite you to check out our latest collection of new electonics appliances."
                + "<br>We are providing upto 60% OFF on most of the electronic gadgets. So please visit our site and explore the collections."
                + "<br><br>Our Online electronics is growing in a larger amount these days and we are in high demand so we thanks all of you for "
                + "making us up to that level. We Deliver Product to your house with no extra delivery charges and we also have collection of most of the"
                + "branded items.<br><br>As a Welcome gift for our New Customers we are providing additional 10% OFF Upto 500 Rs for the first product purchase. "
                + "<br>To avail this offer you only have "
                + "to enter the promo code given below.<br><br><br> PROMO CODE: " + "" + Appinfo.appName.toUpperCase() + "500<br><br><br>"
                + "Have a good day!<br>" + "" + "</body>" + "</html>";
        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }
    
     public static void transactionSuccess(String recipientMailId, String name,double paidAmount) throws javax.mail.MessagingException {
        String subject = "Payment Successfull";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Payment of Rs "+paidAmount+ " is successfull and order confirmed </br> Thankyou for choosing " + Appinfo.appName + "</h2>" +" "+ name + ","
                + "You will recieve a shipping update once your order are on the way . " +  "<br>"
                +"If you have any questions , fell free to contact our support team."
                + "Have a good day!<br>  REGARDS," +  Appinfo.appName.toUpperCase()  + "</body>" + "</html>";
        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }
    
    
}