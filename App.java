package deciderlab.csvtypedetect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * CSV datatypes detector by Deciderlab@margus.roo.ee
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	String filepath = args[0];
    	
    	App obj = new App();
    	String[] a = obj.detect(filepath);
    	for (int i=0; i< a.length ;i++) {
    		System.out.print(a[i]+" ");
    	}
    	System.out.println();
    	
    }
    
    /**
     * Detects CSVs field types
     */
    public String[] detect(String filepath) {
    	 
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
    	int l = 0; // how many cells in csv
    	Integer intTest = 0;
    	String[][] typesArray = null;

    	
    	try {
     
    		br = new BufferedReader(new FileReader(filepath));
    		
    		// get cells number
    		line = br.readLine();
    		String[] country = line.split(cvsSplitBy);
    		l = country.length;
    		//System.out.println(l);
    		br.close(); // if there header, then do not close it.
    		
    		// get number of lines in the file
    		int lines = 0;
    		br = new BufferedReader(new FileReader(filepath));
    		while ((line = br.readLine()) != null) lines++;
    		br.close();
    		
    		typesArray = new String[lines][l];
    		int k = 0;
    		
    		
    		br = new BufferedReader(new FileReader(filepath)); // if there is header, to not open it again
    		while ((line = br.readLine()) != null) {
    			
    		    // use comma as separator
    			country = line.split(cvsSplitBy);
    			
    			// cells loop
    			for (int i=0; i< l;i++) {
    				
					// simple type detection

    				try {
    					intTest = Integer.parseInt(country[i]);
    					//System.out.println(" is Nummeric value");
    					typesArray[k][i] = "N";
    				} catch (Exception e) {
    					// TODO: handle exception
    					//System.out.println(" is String value");
    					typesArray[k][i] = "C";
    				}
    				
    				
    				// there is better solution - mark whole cell as C and do not compare again
    				if (k > 0 && typesArray[(k-1)][i] == "C")
    				{
    					typesArray[k][i] = "C";
    				}	
    				
				}
    			k++;
    			
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	return typesArray[(typesArray.length-1)];
      }
}
