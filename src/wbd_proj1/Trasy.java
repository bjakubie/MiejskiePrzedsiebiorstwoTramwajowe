package wbd_proj1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Trasy extends javax.swing.JFrame {

    String login2;
    public int ile;
    public String ile1;
    public int pobrana_wartosc;
    public String tekst1;
    public String tekst2;
    public String tekst3;
    
    
    public Trasy(String login) throws SQLException {
        
        login2 = login;
        
        initComponents();
        
        dodajWierszeDoTabel("<");
        dodajWierszeDoTabel(">");
    }
    
    
    //Metoda znaleziona w necie, oby dzialala
    public class pojedynczaTrasa
    {
        public String id_trasy;
        public String nr_tramwaju;
        public String nr_boczny_tramwaju;
        public String imie;
        public String nazwisko;
        public String data;
        public String godzina_rozpoczecia;
        public String godzina_zakonczenia;
        
        
        public pojedynczaTrasa(String Id_trasy, String Nr_tramwaju, String Nr_boczny, String Imie, String Nazwisko, String Data, String Godzina_rozp, String Godzina_zak)
        {
            this.id_trasy = Id_trasy;
            this.nr_tramwaju = Nr_tramwaju;
            this.nr_boczny_tramwaju = Nr_boczny;
            this.imie = Imie;
            this.nazwisko = Nazwisko;
            this.data = Data;
            this.godzina_rozpoczecia = Godzina_rozp;
            this.godzina_zakonczenia = Godzina_zak;
        }
    }
    
    
    public ArrayList<pojedynczaTrasa> ListaTras(String znak) throws SQLException 
    {
        Laczenie laczenie = new Laczenie();
        ArrayList<pojedynczaTrasa> listaTras = new ArrayList<>();
        
        try
        {
            ile = pobierzWielkosc(znak);
        
            Connection polo = laczenie.polaczenieBaza();
            Statement stat = polo.createStatement();
        
            pojedynczaTrasa ptras[] = new pojedynczaTrasa[ile];
            
            String pojeTrasa[];
            
            
            tekst1 = "SELECT tras.\"IdTrasy\", t.\"NumerBoczny\", t.\"NumerTramwaju\", prac.\"Imie\", prac.\"Nazwisko\", p.\"Data\", p.\"GodzinaStartu\", p.\"GodzinaStopu\" ";
            tekst2 = "FROM \"Tramwaje\" t, \"Pracownicy\" prac, \"ProwadzenieTramwaju\" p, \"Motorniczowie\" m, \"Trasy\" tras, \"ObslugaTras\" o ";
            tekst3 = "WHERE o.\"IdTrasy\" = tras.\"IdTrasy\" AND o.\"IdTramwaju\" = t.\"IdTramwaju\" AND t.\"IdTramwaju\" = p.\"IdTramwaju\" AND p.\"IdPracownika\" = m.\"IdPracownika\" AND m.\"IdPracownika\" = prac.\"IdPracownika\" AND prac.\"EMail\" = '" + login2 + "' AND p.\"Data\" " + znak + " (SELECT sysdate FROM DUAL)";
            
            ResultSet PytanieODane;
            //System.out.println(tekst1+tekst2+tekst3);
            if(">".equals(znak)) //do posortowanie tabeli pierwszej (najblizsze trasy)
            {
                String tekst4 = new String();
                tekst4 = " ORDER BY p.\"Data\" ASC";
                PytanieODane= stat.executeQuery(tekst1+tekst2+tekst3+tekst4);
            }
            else
            {
                
                PytanieODane = stat.executeQuery(tekst1+tekst2+tekst3);
            }
        
            int i=0;
            
            while(PytanieODane.next())
            {
                
                pojeTrasa = new String[8]; //pojeTrasa zbiera po kolei wartosci z fora, czyli kolumn - tak bylo latwiej zapisac
                    
                for (int j=0; j<8; j++)
                {
                    pojeTrasa[j] = PytanieODane.getString(j+1);
                }
                    
                ptras[i] = new pojedynczaTrasa(pojeTrasa[0],pojeTrasa[1],pojeTrasa[2],pojeTrasa[3],pojeTrasa[4],pojeTrasa[5],pojeTrasa[6],pojeTrasa[7]);
                    
                listaTras.add(ptras[i]);
                i++;
                
            }
            
        }
        catch(SQLException e)
        {
            System.out.println("nie udalo sie w trasach");
            
            return null;
        }
        
        
        
        return listaTras;
    }
    
    
    
    public int pobierzWielkosc(String znak) throws SQLException
    {
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT COUNT(*) FROM \"ProwadzenieTramwaju\" WHERE \"IdPracownika\" = (SELECT \"IdPracownika\" FROM \"Pracownicy\" WHERE \"EMail\" = '" +login2 + "') AND \"Data\" " + znak + " (SELECT sysdate FROM DUAL)");
        
        
        while(pytanie_o_ilosc.next())
        {
            //System.out.println(pytanie_o_ilosc.getString(1));
            for(int i=0; i<1; i++)
            {
                ile1 = pytanie_o_ilosc.getString(i+1);
            }
        }
        
        pobrana_wartosc = Integer.parseInt(ile1);
        return pobrana_wartosc;
    }
    
    
    public void dodajWierszeDoTabel(String znak) throws SQLException
    {
        
        DefaultTableModel model;
        if("<".equals(znak))
        {
             model = (DefaultTableModel)mojeOstatnieTrasyTable.getModel();
        }
        else
            model = (DefaultTableModel)mojeNajblizszeTrasyTable.getModel();
        
        
        ArrayList<pojedynczaTrasa> list = ListaTras(znak);
        Object rowData[] = new Object[8];
        
        for(int i=0; i<ile; i++)
        {
            rowData[0] = list.get(i).id_trasy;
            rowData[1] = list.get(i).nr_tramwaju;
            rowData[2] = list.get(i).nr_boczny_tramwaju;
            rowData[3] = list.get(i).imie;
            rowData[4] = list.get(i).nazwisko;
            rowData[5] = list.get(i).data;
            rowData[6] = list.get(i).godzina_rozpoczecia;
            rowData[7] = list.get(i).godzina_zakonczenia;
            
            model.addRow(rowData);
            
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mojeKontoButton = new javax.swing.JButton();
        trasyButton = new javax.swing.JButton();
        wynagrodzenieButton = new javax.swing.JButton();
        wylogujButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        menuLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mojeNajblizszeTrasyTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        mojeOstatnieTrasyTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tloLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1015, 745));
        getContentPane().setLayout(null);

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
        getContentPane().add(wylogujButton);
        wylogujButton.setBounds(30, 650, 200, 30);

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dane dotyczące Twoich tras");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(460, 60, 360, 40);

        menuLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        menuLabel.setForeground(new java.awt.Color(255, 255, 255));
        menuLabel.setText("MENU");
        getContentPane().add(menuLabel);
        menuLabel.setBounds(100, 60, 80, 40);

        mojeNajblizszeTrasyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Trasy", "Nr Boczny Tramwaju", "Nr Tramwaju", "Imie", "Nazwisko", "Data", "Godzina Startu", "Godzina Stopu"
            }
        ));
        jScrollPane1.setViewportView(mojeNajblizszeTrasyTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(300, 160, 670, 150);

        mojeOstatnieTrasyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Trasy", "Nr Boczny Tramwaju", "Nr Tramwaju", "Imie", "Nazwisko", "Data", "Godzina Startu", "Godzina Stopu"
            }
        ));
        jScrollPane2.setViewportView(mojeOstatnieTrasyTable);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(300, 410, 670, 150);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Twoje najbliższe trasy:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(310, 125, 220, 30);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Twoje ostatnie Trasy:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(310, 375, 230, 30);

        tloLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/tło.jpg"))); // NOI18N
        tloLabel.setText("jLabel1");
        getContentPane().add(tloLabel);
        tloLabel.setBounds(0, 0, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mojeKontoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mojeKontoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mojeKontoButtonActionPerformed

    private void wynagrodzenieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wynagrodzenieButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wynagrodzenieButtonActionPerformed

    private void wylogujButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wylogujButtonMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Wylogowano pomyślnie");

        setVisible(false);
        Logowanie log = new Logowanie();
        log.setVisible(true);
        
    }//GEN-LAST:event_wylogujButtonMouseClicked

    private void mojeKontoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mojeKontoButtonMouseClicked
        // TODO add your handling code here:
        setVisible(false);
        perspektywaPracownika perspektywka = new perspektywaPracownika(login2);
        perspektywka.setVisible(true);
        
    }//GEN-LAST:event_mojeKontoButtonMouseClicked

    private void trasyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trasyButtonMouseClicked
        try {
            // TODO add your handling code here:
            setVisible(false);
            Trasy trasa = new Trasy(login2);
            trasa.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Trasy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_trasyButtonMouseClicked

    private void wynagrodzenieButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wynagrodzenieButtonMouseClicked
        // TODO add your handling code here:
        setVisible(false);
        Wynagrodzenie wynagrodzenie = new Wynagrodzenie(login2);
        wynagrodzenie.setVisible(true);
        
    }//GEN-LAST:event_wynagrodzenieButtonMouseClicked

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JButton mojeKontoButton;
    private javax.swing.JTable mojeNajblizszeTrasyTable;
    private javax.swing.JTable mojeOstatnieTrasyTable;
    private javax.swing.JLabel tloLabel;
    private javax.swing.JButton trasyButton;
    private javax.swing.JButton wylogujButton;
    private javax.swing.JButton wynagrodzenieButton;
    // End of variables declaration//GEN-END:variables
}
