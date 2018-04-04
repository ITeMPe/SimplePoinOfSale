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
	 
	 static List<Integer> codeList = new ArrayList<Integer>();
	 
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

static void initDataBase()
{
	initProductsList();
	
	DataBaseConnector dbCon = new DataBaseConnector();
	dbCon.database_Conection();
	for(int i=0; i<productsList.size(); i++)
	{
		String query = "INSERT INTO `products`(`Product_name`, `Product_price`, `Product_code`) VALUES ('"+productsList.get(i).name+"',"+productsList.get(i).price+","+productsList.get(i).code+")";
		try
		{
			dbCon.my_statement.executeUpdate(query);
		} 
		catch (SQLException e2)
		{
			e2.printStackTrace();
		}
	}
}

		
static void showProductFrom_DataBase()
{

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
			System.out.println(" id  Product_name  price  code");System.out.print("   ");
			while(dbCon.my_resultSet.next())
			{
				System.out.print(dbCon.my_resultSet.getString("Product_id"));System.out.print("   ");
				System.out.print(dbCon.my_resultSet.getString("Product_name"));System.out.print("   ");
				System.out.print(dbCon.my_resultSet.getString("Product_price"));System.out.print("   ");
				System.out.println(dbCon.my_resultSet.getString("Product_code"));System.out.print("   ");
			}
			System.out.println();
		} 
		catch (SQLException e2)
		{
			e2.printStackTrace();
		}

	dbCon.database_DisConection();
}

static boolean findProductCode(int code)
{
	DataBaseConnector dbCon = new DataBaseConnector();
	 dbCon.database_Conection();
	try
	{
		String query = "SELECT Product_code FROM `products` WHERE Product_code = '"+code+"'";
		dbCon.my_resultSet = dbCon.my_statement.executeQuery(query);
		if(dbCon.my_resultSet.next())
		{
				return true;
		}
	} 
	catch (SQLException e1)
	{
		e1.printStackTrace();
	}
	return false;
}

static double showAdderCodes()
{
	double sum = 0.0;
	DataBaseConnector dbCon = new DataBaseConnector();
	 dbCon.database_Conection();
	try
	{
		String query = "SELECT * FROM `products` WHERE ";
		for(int i=0; i<codeList.size(); i++)
		{
			query = query +"Product_code ='"+ codeList.get(i)+"'";
			if(i <codeList.size()-1){ query = query + " OR ";}
		}
		System.out.println(query);
		dbCon.my_resultSet = dbCon.my_statement.executeQuery(query);
		System.out.println();
		while(dbCon.my_resultSet.next())
		{
			System.out.print(dbCon.my_resultSet.getString("Product_id"));System.out.print("   ");
			System.out.print(dbCon.my_resultSet.getString("Product_name"));System.out.print("   ");
			System.out.print(dbCon.my_resultSet.getString("Product_price"));System.out.print("   ");
			System.out.println(dbCon.my_resultSet.getString("Product_code"));System.out.print("   ");
			sum = sum + Double.parseDouble(dbCon.my_resultSet.getString("Product_price"));
		}
	} 
	catch (SQLException e1)
	{
		e1.printStackTrace();
	}
	return sum;
}



static void addNewProductTo_DataBase()
{
	int state = 0;
	String newName=null;
	double newPrice=0;
	int newCode=-1;
	Scanner reader = new Scanner(System.in);
	
	DataBaseConnector dbCon = new DataBaseConnector();
	dbCon.database_Conection();
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
					dbCon.my_resultSet = dbCon.my_statement.executeQuery(query);
				} 
				catch (SQLException e2)
				{
					e2.printStackTrace();
				}
				try
				{
					if(dbCon.my_resultSet.next())
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
					dbCon.my_resultSet = dbCon.my_statement.executeQuery(query);
				} 
				catch (SQLException e2)
				{
					e2.printStackTrace();
				}
				try
				{
					if(dbCon.my_resultSet.next())
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
				dbCon.my_statement.executeUpdate(query);
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

	dbCon.database_DisConection();
}	 
	 

/* ----------------------------------------------------------------------------------------------------
 * 																										*
 *  								MAIN 	FUNCTION 													*
 *  																									*
 * -----------------------------------------------------------------------------------------------------
 */
	public static void main(String[] args) 
	{
		//initDataBase(); --> ta funkcja jest tylko po to zeby wygenerowac 100 produktow do bazy danych
		
		showProductFrom_DataBase();
		int tempCode = -1;
		int state = 0;
		while(true)
		{
			Scanner reader = new Scanner(System.in); 
			double suma=0;
			switch (state)
			{
			case 0:	//stan poczatkowy [klien podchodzi do punktu sprzedarzy i zczytuje kod produku]
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
				findProduct = findProductCode(tempCode);
				codeList.add(tempCode);
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
					}
					catch (Exception e)
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
				suma = showAdderCodes();	
				System.out.println("Kwota do zapłaty: "+suma);
				codeList.clear();
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

