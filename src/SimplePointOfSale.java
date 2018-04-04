import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.NamedNodeMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


/*------------------------------ 		INFORMACJE DOTYCZACE OBSLUGI PROGRAMU		--------------------------------- 
 *																													* 
 * 		NA POCZATKU PROGRAMU WYSWIETLONA ZOSTAJE LISTA WSZYSTKICH PRODUKTOW JAKIE ZNAJDUJA SIE W BAZIE DANYCH		*
 * 																													*
 * 		AKTUALNIE BAZA KODÓW KRESKOWYCH JEST W PRZEDZIALE OD 1 DO 100	[ baza w formie listy ]						*
 * 																													*
 * 		CZYTANIE KODOW KRESKOWYCH POLEGA NA WPISANU PRZEZ UZYTKOWNIKA KODU KRESKOWEGO 								*
 * 																													*	
 * 		POPRAWNY KOD KRESKOWY TO LICZBA DOWOLNA LICZBA CAŁKOWITA													*
 * 																													*
---------------------------------------------------------------------------------------------------------------------
 */



public class SimplePointOfSale
{
	 static List<Product> productsList = new ArrayList<Product>();
	 static List<Product> purchasedProductsList = new ArrayList<Product>();
	 static Random gll = new Random();
	

	 
	 


	 static void createCon()
	 {
		 System.out.println("funkcja createCon");
		 DataBaseConnector dbCon = new DataBaseConnector();
		 dbCon.database_Conection();
		 try
			{
				String query = "SELECT * FROM products";
				dbCon.my_resultSet = dbCon.my_statement.executeQuery(query);
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				System.out.println("id  Product_name  price  code");
				while(dbCon.my_resultSet.next())
				{
					System.out.print(dbCon.my_resultSet.getString("Product_id"));System.out.print("  ");
					System.out.print(dbCon.my_resultSet.getString("Product_name"));System.out.print("  ");
					System.out.print(dbCon.my_resultSet.getString("Product_price"));System.out.print("  ");
					System.out.println(dbCon.my_resultSet.getString("Product_code"));System.out.print("  ");
				}
				System.out.println();
			} 
			catch (SQLException e2)
			{
				e2.printStackTrace();
			}
		 
		 
	 }
	 
	 
	 
	 
	 
	public static void main(String[] args) 
	{
		initProductsList();	// stworzenie pozorowanej bazy danych, w przyszlosci baze danych chce umiescic na localhoscie
		/*
		 *	POCZATEK KONFIGUROWANIA POLACZENIA Z BAZA DANYCH 
		 
			Connection connection = null;
			Statement statement = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String url = "jdbc:mysql://localhost:3306/db_pointofsale?autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "";
			if(connection == null)
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
					connection = DriverManager.getConnection(url,username,password);
					
				    System.out.println("Database connected!");System.out.println();
				} 
				catch (SQLException e)
				{
				    throw new IllegalStateException("Cannot connect the database!", e);
				}			
			}
			try
			{
				statement = connection.createStatement();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			*/
/*			
			for(int i=0; i<productsList.size(); i++)
			{
				String query = "INSERT INTO `products`(`Product_name`, `Product_price`, `Product_code`) VALUES ('"+productsList.get(i).name+"',"+productsList.get(i).price+","+productsList.get(i).code+")";
				try
				{
					statement.executeUpdate(query);
				} 
				catch (SQLException e2)
				{
					e2.printStackTrace();
				}
			}
*/
		/*
			try
			{
				String query = "SELECT * FROM products";
				resultSet = statement.executeQuery(query);
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				System.out.println("id  Product_name  price  code");
				while(resultSet.next())
				{
					System.out.print(resultSet.getString("Product_id"));System.out.print("  ");
					System.out.print(resultSet.getString("Product_name"));System.out.print("  ");
					System.out.print(resultSet.getString("Product_price"));System.out.print("  ");
					System.out.println(resultSet.getString("Product_code"));System.out.print("  ");
				}
				System.out.println();
			} 
			catch (SQLException e2)
			{
				e2.printStackTrace();
			}

			// closing
			try
			{
				resultSet.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				statement.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				connection.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		/*
		 *   KONIEC KONFIGURACJI 
		*/
		

	//	System.out.println("PRODUKT	    CENA[zl]	 KOD ");
		showProductFrom_DataBase();
		
		
		
	//	addNewProductTo_DataBase();
	//	showProductsList();
		int tempCode=-1;
		int state=0;
		while(true)
		{
			Scanner reader = new Scanner(System.in); 
			double suma=0;
			switch (state)
			{
			case 0:	//stan poczatkowy [klien podchodzi do punktu sprzedarzy i zczytuje kody]
				System.out.print("PODAJ KOD KRESKOWY PRODUKTU: ");
				try
				{
					tempCode = reader.nextInt();
					state=1;
				}
				catch (InputMismatchException e ) 
				{
					System.out.println("Nieprawidłwy kod kreskowy, sprubuj jeszcze raz");
					state=0;
					break;
				}
				break;
			case 1:	// ROZPOCZECIE CZYTANIA KODOW KRESKOWYCH
				boolean findProduct = false;
				for(int i=0; i<productsList.size(); i++)
				{
					if(tempCode == productsList.get(i).code)
					{
						System.out.println("Zeskanowano: "+productsList.get(i).name+"  "+productsList.get(i).price+" zl");
						purchasedProductsList.add(productsList.get(i));
						findProduct= true;
						break;
					}
				}
				if(!findProduct)
				{
					System.out.println("Nie znaleziono takiego produktu w naszej bazie danych, przepraszamy...");
					System.out.println("Jesli chcesz dodac nowy prodkukt wybierz 1");
					try
					{
						tempCode = reader.nextInt();
						if(tempCode == 1)
						{
							addNewProductTo_DataBase();
						}
					} catch (Exception e)
					{
						e.getMessage();
					}
					
					
				}
				System.out.println("Jesli chcesz zakonczyc zakupy wcisnij 0 [dowolny klawisz aby kontynuować]");
				try
				{
					tempCode = reader.nextInt();
					if(tempCode == 0)
					{
						state=2;
					}
					else state = 0;
				}
				catch (InputMismatchException e) 
				{
					System.out.println("Złe dane wejściowe !!");
					state = 0;
				}
				break;
			case 2:		// PODSUMOWANIE ZAKUPOW
				System.out.println("Podsumowanie zakupów: ");
				for(int i=0;i<purchasedProductsList.size(); i++)
				{
					suma+=purchasedProductsList.get(i).price;
					System.out.println(purchasedProductsList.get(i).name+" "+purchasedProductsList.get(i).price);
				}
				System.out.println("Kwota do zapłaty: "+suma);
				purchasedProductsList.clear();
				suma=0;
				System.out.println("Jesli chcesz zakonczyc program wcisnij 0 [dowolny klawisz aby kontynuować]");
				try
				{
					tempCode = reader.nextInt();
				}
				catch (InputMismatchException e ) 
				{
					System.out.println("Nieprawidłwy kod kreskowy, sprubuj jeszcze raz");
					state=0;
					break;
				}
				if(tempCode == 0)
				{
					state=3;break;
				}
				else state = 0;
				break;
			case 3:		// ZAKONCZENIE PROGRAMU	
				System.out.println("Program zakończony...");
				System.exit(0);
				break;
			default:
				state=0;
				break;
			}
			
		}
		
		
	}

	
	
	
	
	
	
	
		static void initProductsList()
		{
			int size =100;
			for(int i=0; i<size; i++)
			{
				productsList.add(new Product("Produkt_"+(i+1),((int)(gll.nextDouble()*10000))/100.0 ,i+1));
			}
		}
		
		static void showProductsList()
		{
			for(int i=0; i<productsList.size();i++)
			{
				System.out.print(productsList.get(i).name+"     ");System.out.print(productsList.get(i).price+"      ");System.out.println(productsList.get(i).code);
			}
		}
		
		
		static void showProductFrom_DataBase()
		{
			Connection connection = null;
			Statement statement = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String url = "jdbc:mysql://localhost:3306/db_pointofsale?autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "";
			if(connection == null)
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
					connection = DriverManager.getConnection(url,username,password);
					
				    System.out.println("Database connected!");System.out.println();
				} 
				catch (SQLException e)
				{
				    throw new IllegalStateException("Cannot connect the database!", e);
				}			
			}
			try
			{
				statement = connection.createStatement();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			
					try
					{
						String query = "SELECT * FROM products";
						resultSet = statement.executeQuery(query);
					} 
					catch (SQLException e1)
					{
						e1.printStackTrace();
					}
					try
					{
						System.out.println(" id  Product_name  price  code");System.out.print("   ");
						while(resultSet.next())
						{
							System.out.print(resultSet.getString("Product_id"));System.out.print("   ");
							System.out.print(resultSet.getString("Product_name"));System.out.print("   ");
							System.out.print(resultSet.getString("Product_price"));System.out.print("   ");
							System.out.println(resultSet.getString("Product_code"));System.out.print("   ");
						}
						System.out.println();
					} 
					catch (SQLException e2)
					{
						e2.printStackTrace();
					}
			// closing....
			try
			{
				resultSet.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				statement.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				connection.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}

		
		
		
		
		
		static void addNewProductTo_DataBase()
		{
			int state = 0;
			String newName=null;
			double newPrice=0;
			int newCode=-1;
			Scanner reader = new Scanner(System.in);
			
			Connection connection = null;
			Statement statement = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String url = "jdbc:mysql://localhost:3306/db_pointofsale?autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "";
			if(connection == null)
			{
				System.out.println("Connecting database...");

				try 
				{
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						System.out.println("Driver loaded!");
					} 
					catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					}
					connection = DriverManager.getConnection(url,username,password);
				    System.out.println("Database connected!");System.out.println();
				} 
				catch (SQLException e)
				{
				    throw new IllegalStateException("Cannot connect the database!", e);
				}			
			}
			try
			{
				statement = connection.createStatement();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			while(state != 5)
			{
				switch (state)
				{
				case 0:
						System.out.println("Podaj nazwe nowego produktu:");
						try
						{
							newName = reader.next();
						} 
						catch (Exception e)
						{
							e.getMessage();
						}
						String query = "SELECT Product_name FROM `products` WHERE Product_name = '"+newName+"'";
						try
						{
							resultSet = statement.executeQuery(query);
						} 
						catch (SQLException e2)
						{
							e2.printStackTrace();
						}
						try
						{
							if(resultSet.next())
							{
									System.out.println("Taki produkt juz istnieje !!");
							}
							else
							{
								System.out.println("Nie ma tego produktu w bazie danych, można go dodać !!");
								state = 1;
							}
						} 
						catch (SQLException e2)
						{
							e2.printStackTrace();
						}
					break;
				case 1:
						System.out.println("Podaj cene nowego produktu:");
						try
						{
							newPrice = reader.nextDouble();
							state = 2;
						} 
						catch (Exception e)
						{
							e.getMessage();
						}
					break;
				case 2:
						System.out.println("Podaj kod nowego produktu:");
						try
						{
							newCode = reader.nextInt();
						} 
						catch (Exception e)
						{
							e.getMessage();
						}
						query = "SELECT Product_code FROM `products` WHERE Product_code = '"+newCode+"'";
						try
						{
							resultSet = statement.executeQuery(query);
						} 
						catch (SQLException e2)
						{
							e2.printStackTrace();
						}
						try
						{
							if(resultSet.next())
							{
									System.out.println("Taki KOD juz istnieje, nie można dodać tego produku z takim kodem !!\nPodaj inny kod !");
							}
							else
							{
								System.out.println("Nie ma tego KODU w bazie danych, można go dodać !!");
								state = 3;
							}
							
						} 
						catch (SQLException e2)
						{
							e2.printStackTrace();
						}
					break;
				case 3:
					 query = "INSERT INTO `products`(`Product_name`, `Product_price`, `Product_code`) VALUES ('"+newName+"',"+newPrice+","+newCode+")";
					try
					{
						statement.executeUpdate(query);
						System.out.println("Dodano nowy produkt do bazy danych !! Gratulacje :)");
						productsList.add(new Product(newName, newPrice, newCode));
						state=5;
					} 
					catch (SQLException e2)
					{
						e2.printStackTrace();
					}
					break;
				default:
					break;
				}
			}			

						
						
						
			
			
			// closing....
			try
			{
				resultSet.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				statement.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				connection.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		
		
		/*
		 * 
		 * 		new main branch 
		 * 	
		 * 		
		 * 
		 */

}

