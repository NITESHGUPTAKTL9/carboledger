package com.example.caboledgerassignment.service;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static WebDriver createWebDriver() {

        String currentDir = System.getProperty("user.dir");

        String defaultDownloadDirectory = currentDir + "/";
        String os = System.getProperty("os.name").toLowerCase();
        String chromeDriverPath = os.contains("mac") ? "/chromedriver" : "/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", currentDir + chromeDriverPath);
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--log-level=3");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-logging"));


        Map<String, Object> prefs = new HashMap<>();
        prefs.put("plugins.plugins_list",
                Collections.singletonList(Map.of("enabled", false, "name", "Chrome PDF Viewer")));
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("download.default_directory", defaultDownloadDirectory);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("download.extensions_to_open", "");
        prefs.put("plugins.always_open_pdf_externally", true);
        chromeOptions.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    public static WebDriver createWebDriver(String path) {

        String currentDir = System.getProperty("user.dir");
        String os = System.getProperty("os.name").toLowerCase();
        String currentDirWithSlash = os.contains("mac") ? currentDir + "/" : currentDir + "\\";
        path = path.replace(currentDirWithSlash, "");
        if (!path.contains(currentDir)) {
            path = os.contains("mac") ? currentDir + "/" + path : currentDir + "\\" + path;
        }
        String defaultDownloadDirectory = path;
        String chromeDriverPath = os.contains("mac") ? "/chromedriver" : "/chromedriver.exe";
        String finalPath = currentDir + chromeDriverPath;
        finalPath = os.contains("mac") ? finalPath : finalPath.replace("/", "\\");
        defaultDownloadDirectory = os.contains("mac") ? defaultDownloadDirectory.replace("\\", "/") : defaultDownloadDirectory.replace("/", "\\");
        System.setProperty("webdriver.chrome.driver", finalPath);
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--log-level=3");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-logging"));

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("plugins.plugins_list",
                Collections.singletonList(Map.of("enabled", false, "name", "Chrome PDF Viewer")));
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("download.default_directory", defaultDownloadDirectory);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("download.extensions_to_open", "");
        prefs.put("plugins.always_open_pdf_externally", true);
        chromeOptions.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    public static void createDirectory(String directoryPath) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static XSSFSheet createXSSFWorkbook(String filename, String directory) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        return sheet;
    }

    public static void populateSheet(XSSFSheet sheet, String[] headers) {
        // Create headers
        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

        for (int i = 0; i < headers.length; i++) {
            XSSFCell headerCell = row.createCell(i);
            headerCell.setCellValue(headers[i]);
        }
    }

    public static void saveWorkbook(String directory, String filename, XSSFSheet sheet) {
        try {

            String filePath = directory + "/" + filename;
            XSSFWorkbook workbook = sheet.getWorkbook();
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            outputStream.close();

            System.out.println("Workbook saved successfully to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
