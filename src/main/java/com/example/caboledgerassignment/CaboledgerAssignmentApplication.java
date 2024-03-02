package com.example.caboledgerassignment;

import com.example.caboledgerassignment.scripts.Co2Ai;
import com.example.caboledgerassignment.service.Utils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaboledgerAssignmentApplication {

	public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir");
		String folderName = "Carboledger";
		String path = currentDir + "/" + folderName;
		Utils.createDirectory(path);
		Co2Ai carboledger = new Co2Ai();
		String link1 = "https://www.co2ai.com/";
		String link2 = "https://c3.ai/";

		XSSFSheet sheet = Utils.createXSSFWorkbook("results", path);
		String[] headers = {"No.", "url", "Number of image on the page", "Number of words on the page", "Occurrences of 'AI'", "Occurrences of 'carbon'"};
		Utils.populateSheet(sheet, headers);

		int count1 = 0;
		carboledger.processUrl(link1, sheet, path, count1);
		int count2 = 0;
	    carboledger.processUrl(link2, sheet, path, count2);
	}

}
