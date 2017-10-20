import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset; 

public class BarCha {



	public static void main(String[] args) throws MalformedURLException, IOException{
             

		Scanner s = new Scanner(new File("currencies.txt"));
		double[] b = new double[5];
		// loop to determine currency values based on currencies.txt
	    for(int i=0; s.hasNextLine( );i++) {
	        String sourceCurrency = getCurrency(s);
	        String url = getURLString(sourceCurrency);
	        double exchangeRate = getExchangeRate(url);
	        System.out.println(exchangeRate);
	        //assigns each exchangerate value into an array
	        b[i]= exchangeRate;
	    }
	    s.close( );
	 // Define data for barchart.
	    DefaultCategoryDataset barChartDataset = new DefaultCategoryDataset();
	    
	    //bar for Swiss Franc
        barChartDataset.addValue(b[0], "total", "CHF"); 
        //bar for Nigerian Naira
        barChartDataset.addValue(b[1],  "total", "NGN");
        
        //United Arab Emirates dirham
        barChartDataset.addValue(b[2],  "total", "AED");  
        //Lebanise pounds
        barChartDataset.addValue(b[3], "total", "LBP");
        //British pounds
        barChartDataset.addValue(b[4], "total", "GBP");    
        // Define JFreeChart object that creates line chart.
        JFreeChart barChartObject = ChartFactory.createBarChart(
            "Value", "Name of Currencies", "USD", barChartDataset,
            PlotOrientation.VERTICAL, 
            false,  // Include legend.
            false,  // Include tooltips.
            false); // Include URLs.               
                      
         // Write line chart to a file.               
         int imageWidth = 640;
         int imageHeight = 480;                
         File barChart = new File("sales.png");              
         ChartUtilities.saveChartAsPNG(
             barChart, barChartObject, imageWidth, imageHeight);
	    
	}

	private static double getExchangeRate(String url) throws IOException, MalformedURLException {
		//Scanner to find URL input and open it
		Scanner fromWeb = new Scanner((new URL(url)).openStream( ));
		String line = fromWeb.nextLine( );
		fromWeb.close( );
		//splits exchangeRate
		double exchangeRate = Double.parseDouble(line.split(",")[1]);
		return exchangeRate;
	}

	private static String getCurrency(Scanner s) {
		//reads from scanner in main method to determine if there's a nextline available
		String sourceCurrency = s.nextLine( );
		return sourceCurrency;
	}

	private static String getURLString(String sourceCurrency) {
		//Uses Suffix and prefix to find and calculate currency value 
		//compared with the target currency
		String prefix = "http://download.finance.yahoo.com/d/quotes.csv?s=";
		String suffix = "=X&f=sl1d1t1ba&e=.csv";
		String targetCurrency = "USD";
		String url = prefix + sourceCurrency + targetCurrency + suffix;
		return url;
	}	

}
