package wbd_proj1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class WypelnienieTabel 
{
    
    String login_do_tabeli; // jeszcze nie wiadomo czy sie przyda tak samo jak w konstruktorze i...
    public int ile;
    
    public int id_trasy[];
    public int nr_tramwaju[];
    public int nr_boczny_tramwaju[];
    public String imie[];
    public String nazwisko[];
    public String data[];
    public String godzina_rozpoczecia[];
    public String godzina_zakonczenia[];
    public String tekst1;
    public String tekst2;
    public String tekst3;
            
    public WypelnienieTabel(String login) throws SQLException
    {
        login_do_tabeli = login; //...i to
        
        ile = pobierzWielkosc();
        
        Laczenie laczenie = new Laczenie();
        
        Connection polo = laczenie.polaczenieBaza();
        Statement stat = polo.createStatement();
        
        
        
        tekst1 = "SELECT tras.\"IdTrasy\", t.\"NumerBoczny\", t.\"NumerTramwaju\", prac.\"Imie\", prac.\"Nazwisko\", p.\"Data\", p.\"GodzinaStartu\", p.\"GodzinaStopu\" ";
        
        tekst2 = "FROM \"Tramwaje\" t, \"Pracownicy\" prac, \"ProwadzenieTramwaju\" p, \"Motorniczowie\" m, \"Trasy\" tras, \"ObslugaTras\" ";
        
        tekst3 = "WHERE o.\"IdTrasy\" = tras.\"IdTrasy\" AND o.\"IdTramwaju\" = t.\"IdTramwaju\" AND t.\"IdTramwaju\" = p.\"IdTramwaju\" AND p.\"IdPracownika\" = m.\"IdPracownika\" AND m.\"IdPracownika\" = prac.\"IdPracownika\" AND prac.\"EMail\" = '" + login_do_tabeli + "'";
        
        ResultSet PytanieODane = stat.executeQuery(tekst1+tekst2+tekst3);
        
        
        int j=1;
        while(PytanieODane.next())
        {
            for(int i=0; i<ile; i++)
            {
                
                id_trasy[i] = PytanieODane.getInt(j++);
                nr_boczny_tramwaju[i] = PytanieODane.getInt(j++);
                nr_tramwaju[i] = PytanieODane.getInt(j++);
                imie[i] = PytanieODane.getString(j++);
                nazwisko[i] = PytanieODane.getString(j++);
                data[i] = PytanieODane.getString(j++);
                godzina_rozpoczecia[i] = PytanieODane.getString(j++);
                godzina_zakonczenia[i] = PytanieODane.getString(j++);
                
            }
        }
        
        
        
    }
    
    public int pobierzWielkosc() throws SQLException
    {
        Laczenie lacz = new Laczenie();
        
        Connection polaczenie = lacz.polaczenieBaza();
        Statement stat = polaczenie.createStatement();
        
        ResultSet pytanie_o_ilosc = stat.executeQuery("SELECT COUNT(*) FROM \"ProwadzenieTramwaju\" WHERE \"IdPracownika\" = (SELECT \"IdPracownika\" FROM \"Pracownicy\" WHERE \"EMail\" = ' " +login_do_tabeli + "'");
        
        while(pytanie_o_ilosc.next())
        {
            ile = pytanie_o_ilosc.getInt(1);
        }
        return ile;
    }
}
