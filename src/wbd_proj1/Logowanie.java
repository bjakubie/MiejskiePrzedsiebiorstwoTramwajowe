package wbd_proj1;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Logowanie extends javax.swing.JFrame 
{

    String login;
    
    public Logowanie() 
    {
        initComponents();
    }
    
    public void close()
    {
        WindowEvent WinClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(WinClosingEvent);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tytulLabel = new javax.swing.JLabel();
        loginLabel = new javax.swing.JLabel();
        hasloLabel = new javax.swing.JLabel();
        loginText = new javax.swing.JTextField();
        hasloText = new javax.swing.JPasswordField();
        zalogujButton = new javax.swing.JButton();
        tloLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1015, 745));
        getContentPane().setLayout(null);

        tytulLabel.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        tytulLabel.setForeground(new java.awt.Color(255, 255, 255));
        tytulLabel.setText("Witamy w bazie danych Miejskiego Przedsiębiorstwa Tramwajowego");
        getContentPane().add(tytulLabel);
        tytulLabel.setBounds(30, 30, 940, 80);

        loginLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        loginLabel.setForeground(new java.awt.Color(255, 255, 255));
        loginLabel.setText("Login:");
        getContentPane().add(loginLabel);
        loginLabel.setBounds(280, 230, 110, 60);

        hasloLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        hasloLabel.setForeground(new java.awt.Color(255, 255, 255));
        hasloLabel.setText("Hasło:");
        getContentPane().add(hasloLabel);
        hasloLabel.setBounds(280, 330, 110, 60);

        loginText.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        loginText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginTextActionPerformed(evt);
            }
        });
        getContentPane().add(loginText);
        loginText.setBounds(430, 230, 270, 50);

        hasloText.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        getContentPane().add(hasloText);
        hasloText.setBounds(430, 330, 270, 50);

        zalogujButton.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        zalogujButton.setText("Zaloguj");
        zalogujButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zalogujButtonMouseClicked(evt);
            }
        });
        zalogujButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zalogujButtonActionPerformed(evt);
            }
        });
        getContentPane().add(zalogujButton);
        zalogujButton.setBounds(370, 490, 260, 60);

        tloLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/tło.jpg"))); // NOI18N
        tloLabel.setText("jLabel1");
        getContentPane().add(tloLabel);
        tloLabel.setBounds(0, 0, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginTextActionPerformed

    private void zalogujButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zalogujButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_zalogujButtonActionPerformed

    private void zalogujButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zalogujButtonMouseClicked
        
        
        login = loginText.getText();
        String Hash = new String();    

        String proba = this.ZnajdzHashDoLoginu("\'"+loginText.getText()+"\'");
        
        if (proba==null)
        {
            Hash="";
        }
        
        else 
        {
            Hash=proba;
        }  
        String haslo = new String(hasloText.getPassword());   
        String hasloShashowane = MD5.getMD5(haslo);
        
        //System.out.println(hasloShashowane);
        //System.out.println(Hash);
        //System.out.print(login);
        
       // System.out.print(login);
            
       if(Hash.equals(hasloShashowane) && ((login.equals("K.Krawcz@mpt.com"))||(login.equals("K.Jarzyna@mpt.com"))||(login.equals("T.Norek@mpt.com"))||(login.equals("J.Nowak@mpt.com"))))
        {
            JOptionPane.showMessageDialog(null, "Logowanie udane");
            close();
            perspektywaPracownika perspPrac = new perspektywaPracownika(login);
            
            perspPrac.setVisible(true);
            
        }
        else if (loginText.getText().equals("1") && haslo.equals("1") )
        {
         this.close();
        }
        else 
            JOptionPane.showMessageDialog(null, "Logowanie nieudane, login i/lub hasło niepoprawne");
        
        
    }//GEN-LAST:event_zalogujButtonMouseClicked

    public String ZnajdzHashDoLoginu(String login)
  {
      Laczenie polacz = new Laczenie();
      try{
          String MojHash[] = new String[1];
          Connection polaczenie2 = polacz.polaczenieBaza();
          Statement a = polaczenie2.createStatement();
          ResultSet PytanieOHash = a.executeQuery("select \"Hash\" from \"Hasla\" WHERE \"login\" = "+login );
          
           while(PytanieOHash.next())
            {
                for (int i = 0; i < 1; i++) 
                {
                  MojHash[i] = PytanieOHash.getString(i+1);
                }  
            }
   
      return MojHash[0];
      }
      catch (SQLException e) {
          System.out.println("Nie udalo sie");
          e.getMessage();
      }
      
      return "Nie udalo sie";
  }
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hasloLabel;
    private javax.swing.JPasswordField hasloText;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JTextField loginText;
    private javax.swing.JLabel tloLabel;
    private javax.swing.JLabel tytulLabel;
    private javax.swing.JButton zalogujButton;
    // End of variables declaration//GEN-END:variables
}
