package edu.kemahasiswaan.connection;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;

/**
 *
 * @author Theod
 */
public final class DatabaseConnection 
{    
    private Statement statement;
    private Connection connection;
    
    private static DatabaseConnection instance;
    
    private DatabaseConnection() throws IOException, SQLException, ClassNotFoundException
    {
        GenerateStatement();
    }
    
    public static DatabaseConnection GetInstance()
    {
        try
        {
            if(instance == null)
                instance = new DatabaseConnection();
        }
        catch(ClassNotFoundException | IOException | SQLException exception)
        {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            System.err.println(exception.getMessage());
            System.exit(0);
        }
        return instance;
    } 
    
    public void CloseConnection() throws SQLException
    {
        statement.close();
        connection.close();
    }
    
    public Statement GetStatement()
    {
        return statement;
    }
    
    public Connection GetConnection()
    {
        return connection;
    }
    
    private String GetPanelSetting (String panelName) throws FileNotFoundException, IOException
    {
        var myPanel = new Properties();
        myPanel.load(new FileInputStream("lib/database.ini"));
        return myPanel.getProperty(panelName);       
    }
    
    private void GenerateStatement() throws SQLException, ClassNotFoundException, IOException
    {
        var driver = GetPanelSetting("DBDriver");
        var username = GetPanelSetting("DBUsername");
        var password = GetPanelSetting("DBPassword");
        var databaseName = GetPanelSetting("DBDatabase");
        
        Class.forName(driver);
        connection = DriverManager.getConnection(databaseName, username, password);
        statement = connection.createStatement();
    }
}
