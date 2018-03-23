import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Random;
import java.util.Scanner;

/*
 * 			CZYTANIE KODOW KRESKOWYCH POLEGA NA WPISANU PRZEZ UZYTKOWNIKA KODU KRESKOWEGO (DOWOLNA LICZBA)
 * 
 * 			
 * 
 * 
 * 
 */
public class SimplePointOfSale
{
	 static List<Product> productsList = new ArrayList<Product>();
	 static List<Product> purchasedProductsList = new ArrayList<Product>();
	 static Random gll = new Random();
	

	public static void main(String[] args) 
	{
		Scanner reader = new Scanner(System.in); 
		initProductsList();	// stworzenie pozorowanej bazy danych
//		System.out.println("PRODUKT	    CENA[zl]	 KOD ");
//		showProductsList();
		int tempCode=-1;
		
		int state=0;
		while(true)
		{
		switch (state)
		{
		case 0:	//stan poczatkowy [klien podchodzi do punktu sprzedarzy i zczytuje kody]
			System.out.print("PODAJ KOD KRESKOWY PRODUKTU: ");
			try
			{
				tempCode = reader.nextInt();
				state=1;
			}
			catch (InputMismatchException e) 
			{
				System.out.println("Nieprawidłwy kod kreskowy, sprubuj jeszcze raz");
				state=0;
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
			}
			System.out.println("Jesli chcesz zakonczyc zakupy wcisnij 0");
			tempCode = reader.nextInt();
			if(tempCode == 0)
			{
				state=2;
			}
			else state = 0;
			break;
		case 2:			// PODSUMOWANIE ZAKUPOW
			System.out.println("Podsumowanie zakupów: ");
			double suma=0;
			for(int i=0;i<purchasedProductsList.size(); i++)
			{
				suma+=purchasedProductsList.get(i).price;
				System.out.println(purchasedProductsList.get(i).name+" "+purchasedProductsList.get(i).price);
			}
			System.out.println("Kwota do zapłaty: "+suma);
			purchasedProductsList.clear();
			System.out.println("Jesli chcesz zakonczyc program wcisnij 0");
			tempCode = reader.nextInt();
			if(tempCode == 0)
			{
				state=3;break;
			}
			else state = 0;
			break;
		case 3:			// ZAKONCZENIE PROGRAMU	
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
		
}

