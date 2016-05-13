package utliitaires;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCVS {

	/* Utilisation*/ 
	public static void main(String[] args) {
		
		
		
		
	}	
	
	private String csvFile;
	private String cvsSplitBy;
	
	public ReadCVS(String csvFile, String cvsSplitBy){
		this.csvFile = csvFile;
		this.cvsSplitBy = cvsSplitBy;
	}
	
	 public List<String[]> run() {

		 	ArrayList<String[]> retour = new ArrayList<String[]>();
			BufferedReader br = null;
			String line = "";

			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {

					String[] voisins = line.split(cvsSplitBy);
					retour.add(voisins);
					

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
			
			return(retour);
		  }


}
