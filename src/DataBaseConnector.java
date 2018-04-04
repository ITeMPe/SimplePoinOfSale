import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector
{
	
	Connection my_connection = null;
	Statement my_statement = null;
	PreparedStatement my_preparedStatement = null;
	ResultSet my_resultSet = null;
	String url = "jdbc:mysql://localhost:3306/db_pointofsale?autoReconnect=true&useSSL=false";
	String username = "root";
	String password = "";
	
	public DataBaseConnector()
	{
		
	}
	
	void database_Conection()
	{
		if(my_connection == null)
		{
			System.out.println("Connecting database...");
	
			try 
			{
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("Driver loaded!");
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				my_connection = DriverManager.getConnection(url,username,password);
				
			    System.out.println("Database connected!");System.out.println();
			} 
			catch (SQLException e)
			{
			    throw new IllegalStateException("Cannot connect the database!", e);
			}			
		}
		try
		{
			my_statement = my_connection.createStatement();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	void database_DisConection()
	{
		try
		{
			my_resultSet.close();
		} 
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			my_statement.close();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			my_connection.close();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	
}
