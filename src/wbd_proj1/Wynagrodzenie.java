
package wbd_proj1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Wynagrodzenie extends javax.swing.JFrame {

    String loginPrac;
    String dane_wynagrodzenia[];
    
    public Wynagrodzenie(String login) 
    {
        loginPrac = login; 
        initComponents();
        dane_wynagrodzenia = this.pobierzDaneDoWynagrodzenia();
        wpiszDane();
        
    }
    
    public String[] pobierzDaneDoWynagrodzenia()
    {
        Laczenie polacz = new Laczenie();
        
        try
        {
            Connection polo = polacz.polaczenieBaza();
            Statement stat = polo.createStatement();
            
            String Dane[] = new String [2];
            String tekst1 = new String();
            tekst1 = "SELECT \"WysokoscWynagrodzenia\", \"DataWplywyWynagrodzenia\" FROM \"Wynagrodzenia\" w, \"Pracownicy\" p WHERE w.\"IdPracownika\" = p.\"IdPracownika\" AND p.\"EMail\" = '" + loginPrac +"'";
            
            ResultSet PytanieODane = stat.executeQuery(tekst1);
            
            while(PytanieODane.next())
            {
                for(int i=0; i<2; i++)
                {
                    Dane[i]=PytanieODane.getString(i+1);
                }
            }
        
            Dane[1] =  Dane[1].substring(0, Math.min(Dane[1].length(), 10));
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
        pobranaDataLabel.setText(dane_wynagrodzenia[1]);
        kasaLabel.setText(dane_wynagrodzenia[0]);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuLabel = new javax.swing.JLabel();
        mojeKontoButton = new javax.swing.JButton();
        trasyButton = new javax.swing.JButton();
        wynagrodzeniaButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        wylogujButton = new javax.swing.JButton();
        twojeWynagrodzenieLabel = new javax.swing.JLabel();
        kasaLabel = new javax.swing.JLabel();
        tysiaceLabel = new javax.swing.JLabel();
        dataOtrzymaniaLabel = new javax.swing.JLabel();
        pobranaDataLabel = new javax.swing.JLabel();
        tloLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 745));
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
        getContentPane().add(mojeKontoButton);
        mojeKontoButton.setBounds(30, 140, 200, 50);

        trasyButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        trasyButton.setText("Trasy");
        trasyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trasyButtonMouseClicked(evt);
            }
        });
        getContentPane().add(trasyButton);
        trasyButton.setBounds(30, 220, 200, 50);

        wynagrodzeniaButton.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        wynagrodzeniaButton.setText("Wynagrodzenie");
        wynagrodzeniaButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wynagrodzeniaButtonMouseClicked(evt);
            }
        });
        getContentPane().add(wynagrodzeniaButton);
        wynagrodzeniaButton.setBounds(30, 300, 200, 50);

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dane dotyczące Twojego wynagrodzenia");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(390, 60, 480, 40);

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

        twojeWynagrodzenieLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        twojeWynagrodzenieLabel.setForeground(new java.awt.Color(255, 255, 255));
        twojeWynagrodzenieLabel.setText("Twoje wynagrodzenie:");
        getContentPane().add(twojeWynagrodzenieLabel);
        twojeWynagrodzenieLabel.setBounds(390, 190, 200, 50);

        kasaLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        kasaLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(kasaLabel);
        kasaLabel.setBounds(620, 190, 80, 50);

        tysiaceLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        tysiaceLabel.setForeground(new java.awt.Color(255, 255, 255));
        tysiaceLabel.setText("tyś. zł.");
        getContentPane().add(tysiaceLabel);
        tysiaceLabel.setBounds(720, 190, 100, 50);

        dataOtrzymaniaLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        dataOtrzymaniaLabel.setForeground(new java.awt.Color(255, 255, 255));
        dataOtrzymaniaLabel.setText("Data pierwszej wpłaty:");
        getContentPane().add(dataOtrzymaniaLabel);
        dataOtrzymaniaLabel.setBounds(390, 280, 200, 50);

        pobranaDataLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        pobranaDataLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(pobranaDataLabel);
        pobranaDataLabel.setBounds(640, 280, 180, 50);

        tloLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/tło.jpg"))); // NOI18N
        tloLabel.setText("jLabel1");
        getContentPane().add(tloLabel);
        tloLabel.setBounds(0, 0, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void wylogujButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wylogujButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wylogujButtonActionPerformed

    private void mojeKontoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mojeKontoButtonMouseClicked
        // TODO add your handling code here:
        setVisible(false);
        perspektywaPracownika perspektywka = new perspektywaPracownika(loginPrac);
        perspektywka.setVisible(true);
    }//GEN-LAST:event_mojeKontoButtonMouseClicked

    private void trasyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trasyButtonMouseClicked
        // TODO add your handling code here:
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

    private void wynagrodzeniaButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wynagrodzeniaButtonMouseClicked
        // TODO add your handling code here:
        setVisible(false);
        Wynagrodzenie wynagrodzenie = new Wynagrodzenie(loginPrac);
        wynagrodzenie.setVisible(true);
    }//GEN-LAST:event_wynagrodzeniaButtonMouseClicked

    private void wylogujButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wylogujButtonMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Wylogowano pomyślnie");
        setVisible(false);
        Logowanie logowanie = new Logowanie();
        logowanie.setVisible(true);
    }//GEN-LAST:event_wylogujButtonMouseClicked

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dataOtrzymaniaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel kasaLabel;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JButton mojeKontoButton;
    private javax.swing.JLabel pobranaDataLabel;
    private javax.swing.JLabel tloLabel;
    private javax.swing.JButton trasyButton;
    private javax.swing.JLabel twojeWynagrodzenieLabel;
    private javax.swing.JLabel tysiaceLabel;
    private javax.swing.JButton wylogujButton;
    private javax.swing.JButton wynagrodzeniaButton;
    // End of variables declaration//GEN-END:variables
}
