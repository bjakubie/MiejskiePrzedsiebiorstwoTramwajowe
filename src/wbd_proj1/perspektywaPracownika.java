
package wbd_proj1;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;


public class perspektywaPracownika extends javax.swing.JFrame 
{

    Connection polaczenie;
    String loginPrac;
    String danePracownika[];
    String zapytanie;
    
    public perspektywaPracownika(String login) 
    {
        
        loginPrac = login;
        
        /*if("K.Krawcz@mpt.com".equals(login))
        {
            tloLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/kapec.jpg")));
        }*/
        
        Laczenie lacz = new Laczenie();
        
        polaczenie = lacz.polaczenieBaza();
        
        initComponents();
        
        if("K.Krawcz@mpt.com".equals(login))
        {
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/kapec.jpg")));
        }
        danePracownika = this.PobierzDanePracownika();
        wpiszDane();
        
    }
    
    

    
    public void close()
    {
        WindowEvent WinClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(WinClosingEvent);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuLabel = new javax.swing.JLabel();
        mojeKontoButton = new javax.swing.JButton();
        trasyButton = new javax.swing.JButton();
        wynagrodzenieButton = new javax.swing.JButton();
        wylogujButton = new javax.swing.JButton();
        tytulLabel = new javax.swing.JLabel();
        imieLabel = new javax.swing.JLabel();
        nazwiskoLabel = new javax.swing.JLabel();
        dataUrodzeniaLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        imieWpisLabel = new javax.swing.JLabel();
        nazwiskoWpisLabel = new javax.swing.JLabel();
        dataUrodzeniaWpisLabel = new javax.swing.JLabel();
        emailWpisLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tloLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1015, 745));
        getContentPane().setLayout(null);

        menuLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        menuLabel.setForeground(new java.awt.Color(255, 255, 255));
        menuLabel.setText("MENU");
        getContentPane().add(menuLabel);
        menuLabel.setBounds(100, 60, 80, 40);

        mojeKontoButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        mojeKontoButton.setText("Moje konto");
        mojeKontoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mojeKontoButtonMouseClicked(evt);
            }
        });
        mojeKontoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mojeKontoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(mojeKontoButton);
        mojeKontoButton.setBounds(30, 140, 200, 50);

        trasyButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        trasyButton.setText("Trasy");
        trasyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trasyButtonMouseClicked(evt);
            }
        });
        trasyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trasyButtonActionPerformed(evt);
            }
        });
        getContentPane().add(trasyButton);
        trasyButton.setBounds(30, 220, 200, 50);

        wynagrodzenieButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        wynagrodzenieButton.setText("Wynagrodzenie");
        wynagrodzenieButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wynagrodzenieButtonMouseClicked(evt);
            }
        });
        wynagrodzenieButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wynagrodzenieButtonActionPerformed(evt);
            }
        });
        getContentPane().add(wynagrodzenieButton);
        wynagrodzenieButton.setBounds(30, 300, 200, 50);

        wylogujButton.setFont(new java.awt.Font("Trebuchet MS", 2, 18)); // NOI18N
        wylogujButton.setText("WYLOGUJ");
        wylogujButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wylogujButtonMouseClicked(evt);
            }
        });
        wylogujButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wylogujButtonActionPerformed(evt);
            }
        });
        getContentPane().add(wylogujButton);
        wylogujButton.setBounds(30, 650, 200, 30);

        tytulLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        tytulLabel.setForeground(new java.awt.Color(255, 255, 255));
        tytulLabel.setText("Dane personalne użytkownika");
        getContentPane().add(tytulLabel);
        tytulLabel.setBounds(450, 60, 340, 40);

        imieLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imieLabel.setForeground(new java.awt.Color(255, 255, 255));
        imieLabel.setText("Imie:");
        getContentPane().add(imieLabel);
        imieLabel.setBounds(410, 150, 100, 30);

        nazwiskoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nazwiskoLabel.setForeground(new java.awt.Color(255, 255, 255));
        nazwiskoLabel.setText("Nazwisko:");
        getContentPane().add(nazwiskoLabel);
        nazwiskoLabel.setBounds(410, 200, 100, 30);

        dataUrodzeniaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dataUrodzeniaLabel.setForeground(new java.awt.Color(255, 255, 255));
        dataUrodzeniaLabel.setText("Data Urodzenia:");
        getContentPane().add(dataUrodzeniaLabel);
        dataUrodzeniaLabel.setBounds(410, 250, 100, 30);

        emailLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("e-mail: ");
        getContentPane().add(emailLabel);
        emailLabel.setBounds(410, 300, 100, 30);

        imieWpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imieWpisLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(imieWpisLabel);
        imieWpisLabel.setBounds(610, 150, 200, 30);

        nazwiskoWpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nazwiskoWpisLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(nazwiskoWpisLabel);
        nazwiskoWpisLabel.setBounds(610, 200, 200, 30);

        dataUrodzeniaWpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dataUrodzeniaWpisLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(dataUrodzeniaWpisLabel);
        dataUrodzeniaWpisLabel.setBounds(610, 250, 200, 30);

        emailWpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailWpisLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(emailWpisLabel);
        emailWpisLabel.setBounds(610, 300, 200, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/tło.jpg"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(150, 150));
        jLabel1.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 150));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(810, 110, 150, 150);

        tloLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/tło.jpg"))); // NOI18N
        tloLabel.setText("jLabel1");
        getContentPane().add(tloLabel);
        tloLabel.setBounds(0, 0, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void wylogujButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wylogujButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wylogujButtonActionPerformed

    private void wylogujButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wylogujButtonMouseClicked
        
        JOptionPane.showMessageDialog(null, "Wylogowano pomyślnie");

        setVisible(false);
        Logowanie log = new Logowanie();
        log.setVisible(true);
    }//GEN-LAST:event_wylogujButtonMouseClicked

    private void trasyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trasyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trasyButtonActionPerformed

    private void wynagrodzenieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wynagrodzenieButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wynagrodzenieButtonActionPerformed

    private void mojeKontoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mojeKontoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mojeKontoButtonActionPerformed

    private void mojeKontoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mojeKontoButtonMouseClicked
        
        this.setVisible(false);
        perspektywaPracownika perspektywka = new perspektywaPracownika(loginPrac);
        perspektywka.setVisible(true);
        //this.PobierzDanePracownika();
        
        
    }//GEN-LAST:event_mojeKontoButtonMouseClicked

    private void trasyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trasyButtonMouseClicked
        
        if (loginPrac.equals("K.Krawcz@mpt.com")||loginPrac.equals("K.Jarzyna@mpt.com") )
        try {
            Trasy trasa = new Trasy(loginPrac);
            setVisible(false);
            trasa.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(perspektywaPracownika.class.getName()).log(Level.SEVERE, null, ex);
        }
         else if (loginPrac.equals("T.Norek@mpt.com")||loginPrac.equals("J.Nowak@mpt.com"))
             try{
             TrasyDyrektor trasaDyr = new TrasyDyrektor(loginPrac);
             setVisible(false);
             trasaDyr.setVisible(true);
             }
         catch (SQLException ex){
             
                Logger.getLogger(perspektywaPracownika.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        
    }//GEN-LAST:event_trasyButtonMouseClicked

    private void wynagrodzenieButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wynagrodzenieButtonMouseClicked
       
        setVisible(false);
        Wynagrodzenie wynagrodzenie = new Wynagrodzenie(loginPrac);
        wynagrodzenie.setVisible(true);
        
    }//GEN-LAST:event_wynagrodzenieButtonMouseClicked

    
    
    public String[] PobierzDanePracownika()
    {
        Laczenie polacz = new Laczenie();
         
        try
        {
            Connection polo = polacz.polaczenieBaza();
            Statement stat = polo.createStatement();
            String Dane[] = new String [4];
            //System.out.print("aaabbb");
            //System.out.print("tutaj");
            ResultSet PytanieODane = stat.executeQuery("select \"Imie\",\"Nazwisko\",\"DataUrodzenia\",\"EMail\" from \"Pracownicy\" WHERE \"EMail\" ='"+loginPrac+"'" );
         
            while (PytanieODane.next())
            {
                for (int i = 0; i < 4; i++) 
                {
                    Dane[i]=PytanieODane.getString(i+1);
                }
                      //System.out.print("abcadsw");
            }
            //System.out.print(Dane[0]);
          
        //pozbywamy się tego 00:00:00.0
            Dane[2] =  Dane[2].substring(0, Math.min(Dane[2].length(), 10));
            return Dane;
          
        }
        catch (SQLException e) 
        {
            System.out.println("Nie udalo sie w pracowniku");
            e.getMessage();
            String nieudane[] = new String[1];
            nieudane[0] = "nieudane w pracowniku";
            return nieudane;
        }
          
    }
    
    public void wpiszDane()
    {
        imieWpisLabel.setText(danePracownika[0]);
        nazwiskoWpisLabel.setText(danePracownika[1]);
        dataUrodzeniaWpisLabel.setText(danePracownika[2]);
        emailWpisLabel.setText(danePracownika[3]);
        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/kapec.jpg")));
        
        /*if("K.Krawcz@mpt.com".equals(loginPrac))
        {
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/kapec.jpg")));
        }*/
        
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dataUrodzeniaLabel;
    private javax.swing.JLabel dataUrodzeniaWpisLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailWpisLabel;
    private javax.swing.JLabel imieLabel;
    private javax.swing.JLabel imieWpisLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JButton mojeKontoButton;
    private javax.swing.JLabel nazwiskoLabel;
    private javax.swing.JLabel nazwiskoWpisLabel;
    private javax.swing.JLabel tloLabel;
    private javax.swing.JButton trasyButton;
    private javax.swing.JLabel tytulLabel;
    private javax.swing.JButton wylogujButton;
    private javax.swing.JButton wynagrodzenieButton;
    // End of variables declaration//GEN-END:variables
}
