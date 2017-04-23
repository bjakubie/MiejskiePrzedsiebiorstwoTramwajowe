package wbd_proj1;

import java.sql.*;

public class Laczenie 
{
    
    public Connection polaczenieBaza()
    {
        
        
        Connection polaczenie = null;
        
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //System.out.println("Sterowniki działają!");
            polaczenie = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl12c", "c##bjakubie", "Filozof5");
            
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Błąd połączenia z bazą!");
        }
        
        return polaczenie;
        
    }
 
}
    
