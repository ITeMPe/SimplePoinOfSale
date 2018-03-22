import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimplePointOfSale
{

	 static List<Product> productsList = new ArrayList<Product>();
	static Random gll = new Random();
	public static void main(String[] args) 
	{
		initProductsList();
		showProductsList();

	}

	
	
	
	
		static void initProductsList()
		{
			int size =100;
			for(int i=0; i<size; i++)
			{
				productsList.add(new Product("Produkt_"+i,((int)(gll.nextDouble()*10000))/100.0 ,i));
			}
		}
		
		static void showProductsList()
		{
			for(int i=0; i<productsList.size();i++)
			{
				System.out.print(productsList.get(i).name+"  ");System.out.print(productsList.get(i).price+"   ");System.out.println(productsList.get(i).code);
			}
		}
		
}

