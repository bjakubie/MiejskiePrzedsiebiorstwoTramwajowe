package wbd_proj1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TrasyDyrektor extends javax.swing.JFrame { 
                                                        
                                                        

    public int ile;
    public String ile1;
    public int pobrana_wartosc;
    public String loginPrac;
    
    
    //Luzne Propozycje
    String minuty[] = new String[60];
    String godziny[] = new String[24];
    String dni[] = new String[31];
    String miesiace[] = new String[12];
    String rok [] = {"2017","2018"}; // wiecej chyba nie bedzie potrzeba robic
    String imiona_motorniczych[];
    String nazwiska_motorniczych[];
    int ilu_motorniczych, ilu_motorniczych1;
    String caly_motorniczy[], trasy;
    String numery_boczne[], numery_tramwajow[], caly_tramwaj[];// trasy[];
    int ile_tramw;
    
    DefaultTableModel model;
    
    
    
    public TrasyDyrektor(String login) throws SQLException 
    {
        loginPrac=login;
        initComponents();
        ilu_motorniczych1 = iluMotorniczych();
        ile_tramw = ileTramwajow();
       
        
        //trzeba po pierwsze dorobić dodawanie wierszy do tabel. Czyli jakis select tego co jest w tabeli, żeby ładnie wyrzucało w tablicy tej która jest umieszczona
        dodajWierszeDoTabeli();
        uzupelnijComboBoxGodzinIMinut();
        uzupelnijComboBoxData();
        
        sklejImieINazwiskoMotorniczego();
        sklejBocznyZNumerem();
        
        
        
    }

    
    public class pojedynczaTrasaDyr //kopia tamtego co jest w trasach zwykłych --> tutaj nie bedzie rozróżnienia co do daty, bedize ułożone od tych najnowszych wstawionych (data wykonania)
    {                               // narazie te metody ponizsze sa skopiowane z tras (ciut zmienione bo jakies bledy wywalal netbeans --> narazie nie twórz osobnej klasy zeby to dobrze wygladalo
                                    // jako kod, niech narazie bedzie tak jak jest zeby isc dalej
        public String id_trasy;
        public String nr_tramwaju;
        public String nr_boczny_tramwaju;
        public String imie;
        public String nazwisko;
        public String data;
        public String godzina_rozpoczecia;
        public String godzina_zakonczenia;
        
        
        public pojedynczaTrasaDyr(String Id_trasy, String Nr_tramwaju, String Nr_boczny, String Imie, String Nazwisko, String Data, String Godzina_rozp, String Godzina_zak)
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
    
    
    public ArrayList<TrasyDyrektor.pojedynczaTrasaDyr> ListaTras() throws SQLException
    {
        Laczenie laczenie = new Laczenie();
        ArrayList<TrasyDyrektor.pojedynczaTrasaDyr> listaTras = new ArrayList<>();
        
        try
        {
            ile = pobierzWielkosc();
        
            Connection polo = laczenie.polaczenieBaza();
            Statement stat = polo.createStatement();
        
            TrasyDyrektor.pojedynczaTrasaDyr ptras[] = new TrasyDyrektor.pojedynczaTrasaDyr[ile];
            
            String pojeTrasa[];
            String tekst1 = new String();
            
            tekst1 = "SELECT tras.\"IdTrasy\", t.\"NumerBoczny\", t.\"NumerTramwaju\", prac.\"Imie\", prac.\"Nazwisko\", p.\"Data\", p.\"GodzinaStartu\", p.\"GodzinaStopu\" \n" +
"FROM \"Tramwaje\" t, \"Pracownicy\" prac, \"ProwadzenieTramwaju\" p, \"Motorniczowie\" m, \"Trasy\" tras, \"ObslugaTras\" o\n" +
"WHERE o.\"IdTrasy\" = tras.\"IdTrasy\" AND o.\"IdTramwaju\" = t.\"IdTramwaju\" AND t.\"IdTramwaju\" = p.\"IdTramwaju\" AND p.\"IdPracownika\" = m.\"IdPracownika\" AND m.\"IdPracownika\" = prac.\"IdPracownika\"";
        
            ResultSet PytanieODane;
            
            PytanieODane= stat.executeQuery(tekst1);
            
            
            int i=0;
            
            while(PytanieODane.next())
            {
                
                pojeTrasa = new String[8]; //pojeTrasa zbiera po kolei wartosci z fora, czyli kolumn - tak bylo latwiej zapisac
                    
                for (int j=0; j<8; j++)
                {
                    pojeTrasa[j] = PytanieODane.getString(j+1);
                }
                    
                ptras[i] = new TrasyDyrektor.pojedynczaTrasaDyr(pojeTrasa[0],pojeTrasa[1],pojeTrasa[2],pojeTrasa[3],pojeTrasa[4],pojeTrasa[5],pojeTrasa[6],pojeTrasa[7]);
                    
                listaTras.add(ptras[i]);
                i++;
                
            }
            
        }
        catch(SQLException e)
        {
            System.out.println("nie udalo sie w trasachDYREKTORA");
            
            return null;
        }
            
        
        
        return listaTras;
    }
    
    
    public int pobierzWielkosc() throws SQLException
    {
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT COUNT(*) FROM \"ProwadzenieTramwaju\"");
        
        
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
    
    
    
    public void dodajWierszeDoTabeli() throws SQLException
    {
        
        //DefaultTableModel model;
        
        model = (DefaultTableModel)trasyTable.getModel();
        
        
        ArrayList<TrasyDyrektor.pojedynczaTrasaDyr> list = ListaTras();
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
    
    
    
    /*public int ZnajdzNajwiekszeID() throws SQLException
    {
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT MAX(\"IdTrasy\") FROM \"Trasy\"");
        
        
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
    } */
    
    
    public int ZnajdzNajwiekszeIDProwadzeniaTramwaju() throws SQLException
    {
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT MAX(\"IdProwadzeniaTramwaju\") FROM \"ProwadzenieTramwaju\"");
        
        
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
    
    
    
    //LUZNE PROPOZYCJE:
    
    public void uzupelnijComboBoxGodzinIMinut()
    {
        
        for(int i=0; i<60; i++)
        {
            minuty[i] = Integer.toString(i);
            
            if(i<10)
            {
                minuty[i] = "0" + minuty[i];
            }
            
            minutaStartuCBox.addItem(minuty[i]);
            minutaStopuCBox.addItem(minuty[i]);
        }
        
        for(int i=0;i<24;i++)
        {
            godziny[i]= Integer.toString(i);
            
            if (i<10)
            {
                godziny[i] = "0" + godziny[i];
            }
            
            godzinaStartuCBox.addItem(godziny[i]);
            godzinaStopuCBox.addItem(godziny[i]);
        }
        
        
    }
    
    public void uzupelnijComboBoxData()
    {
        
        for(int i = 0; i<31; i++)
        {
            dni[i] = Integer.toString(i+1);
            
            if(i<9)
            {
                dni[i] = "0" + dni[i];
            }
            
            dzienCBox.addItem(dni[i]);
        }
        
        for(int i=0; i<12; i++)
        {
            miesiace[i] = Integer.toString(i+1);
            
            if (i<9)
            {
                miesiace[i] = "0" + miesiace[i];
            }
            
            miesiacCBox.addItem(miesiace[i]);
        }
        
        rokCBox.addItem(rok[0]);
        rokCBox.addItem(rok[1]);
                
    }
    
    //ponizej metody potrzebne do motorniczych////////////////////////////////////////////////////////////////////////////////////////////////////////////////////motorniczowie
    
    public int iluMotorniczych() throws SQLException
    {
        
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT COUNT(*) FROM \"Motorniczowie\"");
        
        String ile = "0";
        
        while(pytanie_o_ilosc.next())
        {
            //System.out.println(pytanie_o_ilosc.getString(1));
            for(int i=0; i<1; i++)
            {
                ile = pytanie_o_ilosc.getString(i+1);
            }
        }
        
        ilu_motorniczych = Integer.parseInt(ile);
        return ilu_motorniczych;
        
    }
    
    
    
    
    public void pobierzImieMotorniczego() throws SQLException
    {
        
        Laczenie laczenie = new Laczenie();
        
        try
        {
            
            
        
            Connection polo = laczenie.polaczenieBaza();
            Statement stat = polo.createStatement();
            
            imiona_motorniczych = new String[ilu_motorniczych1];
            
            String tekst1 = "SELECT p.\"Imie\" FROM \"Pracownicy\" p, \"Motorniczowie\" m WHERE p.\"IdPracownika\" = m.\"IdPracownika\"";
            ResultSet PytanieODane;
            PytanieODane= stat.executeQuery(tekst1);
            
            int i=0;
            
            while(PytanieODane.next())
            {
                 
                imiona_motorniczych[i] = PytanieODane.getString(1);
                i++;
                
            }
            
            
        }
        catch(SQLException e)
        {
            System.out.println("nie udalo sie w trasachDYREKTORA");
            
            //return null;
        }
        
        
        //return imiona_motorniczych;
    }
    
    
    
    public void pobierzNazwiskoMotorniczego() throws SQLException
    {
        
        Laczenie laczenie = new Laczenie();
        
        try
        {
            
            //int ilu_motorniczych1 = iluMotorniczych();
        
            Connection polo = laczenie.polaczenieBaza();
            Statement stat = polo.createStatement();
            
            nazwiska_motorniczych = new String[ilu_motorniczych1];
            
            String tekst1 = "SELECT p.\"Nazwisko\" FROM \"Pracownicy\" p, \"Motorniczowie\" m WHERE p.\"IdPracownika\" = m.\"IdPracownika\"";
            ResultSet PytanieODane;
            PytanieODane= stat.executeQuery(tekst1);
            
            int i=0;
            
            while(PytanieODane.next())
            {
                
                    nazwiska_motorniczych[i] = PytanieODane.getString(1);
                    
                    i++;
                
            }
            
            
        }
        catch(SQLException e)
        {
            System.out.println("nie udalo sie w trasachDYREKTORA");
            
            //return null;
        }
        
        
        //return nazwiska_motorniczych;
    }
    
    
    public void sklejImieINazwiskoMotorniczego() throws SQLException
    {
        
        pobierzImieMotorniczego();
        pobierzNazwiskoMotorniczego();
        
        caly_motorniczy = new String[ilu_motorniczych];
        
        
        for(int i=0; i<ilu_motorniczych1 ; i++)
        {
            caly_motorniczy[i] = imiona_motorniczych[i] + " " + nazwiska_motorniczych[i];
            imieINazwiskoCBox.addItem(caly_motorniczy[i]);
        }
        
    }
    
    
    //ponizej metody potrzebne do tramwajow/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////tramwaje
    public int ileTramwajow() throws SQLException
    {
        
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT COUNT(*) FROM \"Tramwaje\"");
        
        String ile = "0";
        int ile_tramw = 0;
        
        while(pytanie_o_ilosc.next())
        {
            //System.out.println(pytanie_o_ilosc.getString(1));
            for(int i=0; i<1; i++)
            {
                ile = pytanie_o_ilosc.getString(i+1);
                
                
            }
        }
        
        ile_tramw = Integer.parseInt(ile);
        return ile_tramw;
        
    }
    
    
    public void pobierzNumeBocznyTramwaju() throws SQLException
    {
        
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        numery_boczne = new String[ile_tramw];
        
        String tekst1 = "SELECT \"NumerBoczny\" FROM \"Tramwaje\"";
        ResultSet PytanieODane;
        PytanieODane= stat.executeQuery(tekst1);
            
        int i=0;
            
        while(PytanieODane.next())
        {
                
            numery_boczne[i] = PytanieODane.getString(1);
            i++;
                
        }
        
        
    }
    
    
    public void pobierzNumerTramwaju() throws SQLException
    {
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        numery_tramwajow = new String[ile_tramw];
        
        String tekst1 = "SELECT \"NumerTramwaju\" FROM \"Tramwaje\"";
        ResultSet PytanieODane;
        PytanieODane= stat.executeQuery(tekst1);
            
        int i=0;
            
        while(PytanieODane.next())
        {
                
            numery_tramwajow[i] = PytanieODane.getString(1);
            i++;
                
        }
    }
    
    
    public void sklejBocznyZNumerem() throws SQLException
    {
        pobierzNumeBocznyTramwaju();
        pobierzNumerTramwaju();
        
        caly_tramwaj = new String[ile_tramw];
        
        
        for(int i=0; i<ile_tramw ; i++)
        {
            caly_tramwaj[i] = numery_boczne[i] + "/" + numery_tramwajow[i];
            tramwajCBox.addItem(caly_tramwaj[i]);
        }
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public String dopasujTrase(String nr_tramwaju) throws SQLException //tutaj jeszcze postaram sie wrocic
    {
        
        Laczenie lacz = new Laczenie();
        
        //trasy = new String[ile_tramw];
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        String tekst1 = "SELECT o.\"IdTrasy\" FROM \"ObslugaTras\" o, \"Tramwaje\" tr WHERE tr.\"IdTramwaju\" = o.\"IdTramwaju\" AND tr.\"NumerTramwaju\" = '"+ nr_tramwaju +"'";
        
        ResultSet PytanieODane;
        PytanieODane= stat.executeQuery(tekst1);
        
        
        while(PytanieODane.next())
        {
                
            trasy = PytanieODane.getString(1);
            
        }
        return trasy;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////rozdzielanie tekstow z pol
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuLabel = new javax.swing.JLabel();
        naglowekLabel = new javax.swing.JLabel();
        mojeKontoButton = new javax.swing.JButton();
        trasyButton = new javax.swing.JButton();
        wynagrodzenieButton = new javax.swing.JButton();
        wylogujButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        trasyTable = new javax.swing.JTable();
        usunButton = new javax.swing.JButton();
        modyfikujButton = new javax.swing.JButton();
        dodajButton = new javax.swing.JButton();
        godzinaStartuCBox = new javax.swing.JComboBox<>();
        minutaStartuCBox = new javax.swing.JComboBox<>();
        godzinaStopuCBox = new javax.swing.JComboBox<>();
        minutaStopuCBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rokCBox = new javax.swing.JComboBox<>();
        miesiacCBox = new javax.swing.JComboBox<>();
        dzienCBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        imieINazwiskoCBox = new javax.swing.JComboBox<>();
        tramwajCBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        tloLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1015, 745));
        setPreferredSize(new java.awt.Dimension(1015, 745));
        getContentPane().setLayout(null);

        menuLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        menuLabel.setForeground(new java.awt.Color(255, 255, 255));
        menuLabel.setText("MENU");
        getContentPane().add(menuLabel);
        menuLabel.setBounds(100, 60, 80, 40);

        naglowekLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        naglowekLabel.setForeground(new java.awt.Color(255, 255, 255));
        naglowekLabel.setText("Dane dotyczące wszystkich tras");
        getContentPane().add(naglowekLabel);
        naglowekLabel.setBounds(440, 60, 380, 40);

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
        getContentPane().add(wylogujButton);
        wylogujButton.setBounds(30, 650, 200, 30);

        trasyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Trasy", "Nr boczny tramwaju", "Nr Tramwaju", "Imie", "Nazwisko", "Data", "Godzina Startu", "Godzina Stopu"
            }
        ));
        jScrollPane1.setViewportView(trasyTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(300, 140, 670, 290);

        usunButton.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        usunButton.setText("Usuń");
        usunButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usunButtonMouseClicked(evt);
            }
        });
        getContentPane().add(usunButton);
        usunButton.setBounds(340, 600, 140, 40);

        modyfikujButton.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        modyfikujButton.setText("Modyfikuj");
        modyfikujButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modyfikujButtonMouseClicked(evt);
            }
        });
        getContentPane().add(modyfikujButton);
        modyfikujButton.setBounds(790, 600, 140, 40);

        dodajButton.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        dodajButton.setText("Dodaj");
        dodajButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dodajButtonMouseClicked(evt);
            }
        });
        dodajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajButtonActionPerformed(evt);
            }
        });
        getContentPane().add(dodajButton);
        dodajButton.setBounds(560, 600, 140, 40);

        getContentPane().add(godzinaStartuCBox);
        godzinaStartuCBox.setBounds(850, 470, 50, 24);

        minutaStartuCBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minutaStartuCBoxActionPerformed(evt);
            }
        });
        getContentPane().add(minutaStartuCBox);
        minutaStartuCBox.setBounds(920, 470, 50, 24);

        getContentPane().add(godzinaStopuCBox);
        godzinaStopuCBox.setBounds(850, 530, 50, 24);

        minutaStopuCBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minutaStopuCBoxActionPerformed(evt);
            }
        });
        getContentPane().add(minutaStopuCBox);
        minutaStopuCBox.setBounds(920, 530, 50, 24);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(":");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(910, 470, 10, 20);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText(":");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(910, 530, 10, 20);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Godzina Startu:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(850, 450, 110, 15);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Godzina Stopu:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(850, 510, 110, 15);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Imie i Nazwisko: ");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(470, 460, 100, 15);

        getContentPane().add(rokCBox);
        rokCBox.setBounds(660, 490, 60, 24);

        getContentPane().add(miesiacCBox);
        miesiacCBox.setBounds(730, 490, 40, 24);

        getContentPane().add(dzienCBox);
        dzienCBox.setBounds(780, 490, 40, 24);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Data (YYYY-MM-DD):");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(660, 460, 130, 20);

        getContentPane().add(imieINazwiskoCBox);
        imieINazwiskoCBox.setBounds(470, 490, 170, 24);

        getContentPane().add(tramwajCBox);
        tramwajCBox.setBounds(300, 490, 140, 24);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nr boczny/numer tramwaju:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(300, 460, 170, 15);

        tloLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wbd_proj1/tło.jpg"))); // NOI18N
        tloLabel.setText("jLabel1");
        getContentPane().add(tloLabel);
        tloLabel.setBounds(0, 0, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    private void trasyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trasyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trasyButtonActionPerformed

    private void wynagrodzenieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wynagrodzenieButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wynagrodzenieButtonActionPerformed

    private void dodajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajButtonActionPerformed
        // TODO add your handling code here:
        
        
        
        
    }//GEN-LAST:event_dodajButtonActionPerformed

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

    private void mojeKontoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mojeKontoButtonMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        perspektywaPracownika perspektywka = new perspektywaPracownika(loginPrac);
        perspektywka.setVisible(true);
        
    }//GEN-LAST:event_mojeKontoButtonMouseClicked

    private void wynagrodzenieButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wynagrodzenieButtonMouseClicked
        // TODO add your handling code here:
        
        setVisible(false);
        Wynagrodzenie wynagrodzenie = new Wynagrodzenie(loginPrac);
        wynagrodzenie.setVisible(true);
    }//GEN-LAST:event_wynagrodzenieButtonMouseClicked

    private void wylogujButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wylogujButtonMouseClicked
        // TODO add your handling code here:
        
        JOptionPane.showMessageDialog(null, "Wylogowano pomyślnie");

        setVisible(false);
        Logowanie log = new Logowanie();
        log.setVisible(true);
    }//GEN-LAST:event_wylogujButtonMouseClicked

    
    Object[] wiersz = new Object[8];
    Object columns[] = {"Id Trasy","Numer boczny","Numer tramwaju","Imie","Nazwisko","Data","Godzina startu","Godzina stopu"};
    //int NajwiekszeIdTras = this.ZnajdzNajwiekszeID();
    String nr_boczny, nr_tramwaju, tras;
    String tra[], s, sstartu, sstopu, nazwa[];
    String IdTramwaju, IdPracownika;
    
    
    
    private void dodajButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dodajButtonMouseClicked
        
        rodzielBocznyNumerTrasa();
        polaczDate();
        polaczGodziny();
        rozlaczImieNazwisko();
        
        try {
            wiersz[0] = dopasujTrase(tra[1]);
        } catch (SQLException ex) {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        wiersz[1] = tra[0];
        wiersz[2] = tra[1];
        wiersz[3] = nazwa[0];
        wiersz[4] = nazwa[1];
        wiersz[5] = s;
        wiersz[6] = sstartu;
        wiersz[7] = sstopu;
        
        model.addRow(wiersz);
        
        
        
        ZnajdzIdTramwajuOpodanymNumerzeTramwaju();
        try {
            ZnajdzIdPracownikaOdanymImieniuInazwisku();
        } catch (SQLException ex) {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ZnajdzNajwiekszeIDProwadzeniaTramwaju(); //pobrana_wartosc <--zmienna taka
        } catch (SQLException ex) {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
        DodajDoBazyDanych();
        
        
        
        
    }//GEN-LAST:event_dodajButtonMouseClicked

    //tutaj potrzebne metody jeszcze
    
    
    public void rodzielBocznyNumerTrasa()
    {
        String tekst = (String) tramwajCBox.getSelectedItem();
        tra = null;
        
        tra = tekst.split("/");
        
    }
    
    public void polaczDate()
    {
        String s1 = (String) rokCBox.getSelectedItem();
        String s2 = (String) miesiacCBox.getSelectedItem();
        String s3 = (String) dzienCBox.getSelectedItem();
        
        s = s1 + "-" + s2 + "-" + s3;
        
    }
    
    public void polaczGodziny()
    {
        String s1 = (String) godzinaStartuCBox.getSelectedItem();
        String s2 = (String) minutaStartuCBox.getSelectedItem();
        String s3 = (String) godzinaStopuCBox.getSelectedItem();
        String s4 = (String) minutaStopuCBox.getSelectedItem();
        
        sstartu = s1 + ":" + s2;
        sstopu = s3 + ":" + s4;
        
    }
    
    public void rozlaczImieNazwisko()
    {
        String tekst = (String) imieINazwiskoCBox.getSelectedItem();
        nazwa = null;
        nazwa = tekst.split(" ");
    }
    
    
    public void ZnajdzIdTramwajuOpodanymNumerzeTramwaju()
    {
     
        Laczenie lacz = new Laczenie();
        Connection polaczenie = lacz.polaczenieBaza();

        try 
        {
            Statement stat = polaczenie.createStatement();

            ResultSet PytanieOId = stat.executeQuery("SELECT \"IdTramwaju\" FROM \"Tramwaje\" WHERE \"NumerBoczny\" = '" + tra[0] + "'");              
            String Id;
         
        
            while(PytanieOId.next())
            {    
                Id = PytanieOId.getString(1);
                IdTramwaju = Id;
            }  
                        
        
         
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);
            //return null;
        }
        //return IdTramwaju;

    }
    
    public void ZnajdzIdPracownikaOdanymImieniuInazwisku() throws SQLException
    {
        Laczenie lacz = new Laczenie();

        
        
        Connection polaczenie = lacz.polaczenieBaza();

        
        Statement stat = polaczenie.createStatement();
        String Id;

        ResultSet PytanieOId = stat.executeQuery("SELECT \"IdPracownika\" FROM \"Pracownicy\" WHERE \"Imie\" = '"+wiersz[3]+"'"+" AND \"Nazwisko\" = '" +wiersz[4]+"'" );              
          
        while(PytanieOId.next()) 
        {
            Id = PytanieOId.getString(1);
            IdPracownika=Id;
        }  
    }
          
     
    public void DodajDoBazyDanych()
    {
         Laczenie lacz = new Laczenie();

        
              
        Connection polaczenie = lacz.polaczenieBaza();

        try 
        {
            Statement stat = polaczenie.createStatement();
            ResultSet Wstaw = stat.executeQuery("INSERT INTO \"ProwadzenieTramwaju\" (\"IdProwadzeniaTramwaju\",\"IdPracownika\",\"IdTramwaju\",\"GodzinaStartu\",\"GodzinaStopu\",\"Data\") VALUES ("
                  + (++pobrana_wartosc)+ ",'" +IdPracownika+ "','" +IdTramwaju+ "','" +sstartu+ "','" +sstopu+ "','" +s+ "')") ;
            
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);                
        }
                   
        
    }
    
        
    
    
    
    
    private void minutaStopuCBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minutaStopuCBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minutaStopuCBoxActionPerformed

    private void minutaStartuCBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minutaStartuCBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minutaStartuCBoxActionPerformed

    
    
    
    
    String IdPracownikaUSU ;
    String IdTramwajuUSU ;
    String godzinaStartuUSU ;
    String dataUSU ;
    String godzinaStopuUSU;
    
    
    
    private void usunButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usunButtonMouseClicked
        // TODO add your handling code here:
        
        String tablicaStringow [] = new String[model.getColumnCount()];
        Object zbieranieDanych [] = new Object[model.getColumnCount()]; 
        int ktoreDousuniecia = trasyTable.getSelectedRow()-1; //-1
        
        
        if (ktoreDousuniecia>=-1)
            model.removeRow(ktoreDousuniecia+1); //+1
        else
        {
            JOptionPane.showMessageDialog(null, "No ale tak to nie mozna");
        }
        
        Laczenie lacz = new Laczenie();  
        Connection polaczenie = lacz.polaczenieBaza();
       
        for (int i = 0; i < 8 ; i++) 
        {
            zbieranieDanych[i] = model.getValueAt(ktoreDousuniecia, i);
            tablicaStringow[i] = zbieranieDanych[i].toString();
        }
            
        try 
        {
            ZnajdzIdPracownikaOdanymImieniuInazwisku2(tablicaStringow[3], tablicaStringow[4]);
        } catch (SQLException ex) {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ZnajdzIdTramwajuOpodanymNumerzeTramwaju2(tablicaStringow[2]);
        } catch (SQLException ex) {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        godzinaStartuUSU = tablicaStringow[6];
        dataUSU = tablicaStringow[5];
        godzinaStopuUSU = tablicaStringow[7];
         
         

         this.UsunZBazyDanych();
        
        
        
        
        
    }//GEN-LAST:event_usunButtonMouseClicked

    
     public void ZnajdzIdPracownikaOdanymImieniuInazwisku2(String imie, String nazwisko) throws SQLException
    {
        Laczenie lacz = new Laczenie();

             
        Connection polaczenie = lacz.polaczenieBaza();

        
        Statement stat = polaczenie.createStatement();
        String Id = "nic";

        ResultSet PytanieOId = stat.executeQuery("SELECT \"IdPracownika\" FROM \"Pracownicy\" WHERE \"Imie\" = '"+imie+"'"+" AND \"Nazwisko\" = '" +nazwisko+"'" );              
          
        while(PytanieOId.next())
        {
            Id = PytanieOId.getString(1);
            IdPracownikaUSU = Id;
        }  
         
    }
    
    
    
    public void ZnajdzIdTramwajuOpodanymNumerzeTramwaju2(String numtram) throws SQLException
    {
     
        Laczenie lacz = new Laczenie();

             
        Connection polaczenie = lacz.polaczenieBaza();

        
        Statement stat = polaczenie.createStatement();
        String Id = "nic";
        
        ResultSet PytanieOId = stat.executeQuery("SELECT \"IdTramwaju\" FROM \"Tramwaje\" WHERE \"NumerTramwaju\" = '" + numtram + "'"); 
        
        while(PytanieOId.next())
        {
            Id = PytanieOId.getString(1);
            IdTramwajuUSU = Id;
                  
        }  
         
        //return Id;

    }
    
    
    
    public void UsunZBazyDanych()
    {
        Laczenie lacz = new Laczenie();
        Connection polaczenie = lacz.polaczenieBaza();

         
        try 
        {
            Statement stat = polaczenie.createStatement();
            ResultSet Usun = stat.executeQuery("DELETE FROM \"ProwadzenieTramwaju\" WHERE \"IdPracownika\" = '" + IdPracownikaUSU + "' AND \"IdTramwaju\" = '" + IdTramwajuUSU+ "' AND \"GodzinaStartu\" = '" + godzinaStartuUSU + "' AND \"GodzinaStopu\" = '" + godzinaStopuUSU + "'");
            
            System.out.println("abbdedupa");
        }
        
         catch (SQLException ex) {
            Logger.getLogger(TrasyDyrektor.class.getName()).log(Level.SEVERE, null, ex);                
        }
                   
         
         
        
    }
    
    
    
    
    
    
    
    private void modyfikujButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modyfikujButtonMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_modyfikujButtonMouseClicked


   
    
    
    
    
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dodajButton;
    private javax.swing.JComboBox<String> dzienCBox;
    private javax.swing.JComboBox<String> godzinaStartuCBox;
    private javax.swing.JComboBox<String> godzinaStopuCBox;
    private javax.swing.JComboBox<String> imieINazwiskoCBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JComboBox<String> miesiacCBox;
    private javax.swing.JComboBox<String> minutaStartuCBox;
    private javax.swing.JComboBox<String> minutaStopuCBox;
    private javax.swing.JButton modyfikujButton;
    private javax.swing.JButton mojeKontoButton;
    private javax.swing.JLabel naglowekLabel;
    private javax.swing.JComboBox<String> rokCBox;
    private javax.swing.JLabel tloLabel;
    private javax.swing.JComboBox<String> tramwajCBox;
    private javax.swing.JButton trasyButton;
    private javax.swing.JTable trasyTable;
    private javax.swing.JButton usunButton;
    private javax.swing.JButton wylogujButton;
    private javax.swing.JButton wynagrodzenieButton;
    // End of variables declaration//GEN-END:variables
}