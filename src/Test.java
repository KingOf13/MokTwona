import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Test {
   public static void main(String [] args){
       //Utils.informationDialog("Ceci est une information");
       testDatePrint();
   }


    public static void testTabbedPaneExample(){
        JFrame f;
        f=new JFrame();
        JPanel p1=new JPanel();
        p1.add(new AddCredit(f).rootPanel);
        JPanel p2=new JPanel();
        p2.add(new ModifyCaution(f).rootPanel);
        JPanel p3=new JPanel();
        p3.add(new CreateClient(f).rootPanel);
        JTabbedPane tp=new JTabbedPane();
        tp.setBounds(50,0,500,500);
        tp.add("main",p1);
        tp.add("visit",p2);
        tp.add("help",p3);
        f.add(tp);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void testFormatDate() {
       LocalDateTime n = LocalDateTime.now();
       OffsetDateTime odt = OffsetDateTime.now( ZoneId.systemDefault() ) ;
       DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
               .withZone(odt.getOffset());
       System.out.println(n.format(f));
   }

    public static void saveProperties() {
        Preferences prefs = Preferences.userNodeForPackage(MokTwona.class);
        prefs.put("mail", "kotmanga@kapuclouvain.be");
        prefs.put("password", "kotmanga");
        prefs.put("host", "send.one.com");
        prefs.put("port", "587");
        try {
            prefs.sync();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public static void testSendMail() {
        String to = "julien.aigret@gmail.com";//change accordingly
        String pw = "NarutoLol";
        String subject = "Test";
        String text = "Ceci est un test pour envoyer des mails via une application java\nSi vous vous posez des questions, demandez à Julien ;)\nDes bisous :D";
        if (SendMail.getSender().send(to, subject, text)) System.out.println("Message sent");
        else System.out.println("Sent failed");
    }

    public static void testDatePrint() {
        LocalDate date =  LocalDate.of(2020, 2, 2);
        System.out.println(Utils.ecartDate(date));
    }
}