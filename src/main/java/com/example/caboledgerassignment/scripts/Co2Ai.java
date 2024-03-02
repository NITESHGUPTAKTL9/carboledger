package com.example.caboledgerassignment.scripts;

import ch.qos.logback.core.joran.sanity.Pair;
import com.example.caboledgerassignment.service.Utils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class Co2Ai {
    public Co2Ai() {

    }

    public void processUrl(String linkUrl, XSSFSheet sheet, String path, int count) {
        System.out.println("Running");
        WebDriver driver = Utils.createWebDriver();
        driver.manage().window().maximize();

        try {

            HashMap<String, Boolean> mp = new HashMap<>();
            HashMap<String, Boolean> mp2 = new HashMap<>();
            mp.put(linkUrl, true);
            mp2.put(linkUrl, true);

            while (mp.size() != 0) {
                Map.Entry<String, Boolean> mapSet = mp.entrySet().iterator().next();
                String url = mapSet.getKey();
                if (url != null) {
                    mp.remove(url);
                    Thread.sleep(2000);
                    driver.get(url);
                    Thread.sleep(2000);

                    List<WebElement> linkElements = driver.findElements(By.tagName("a"));

                    for (WebElement linkElement : linkElements) {
                        String link = linkElement.getAttribute("href");
                        if (!mp2.containsKey(link)) {
                            if (link != null && link.toUpperCase().contains("LINKEDIN")) {
                                System.out.println("Outside Url Linkedin");
                            } else if (link != null && link.contains("YOUTUBE")) {
                                System.out.println("Outside Url Youtube");
                            } else if (link != null && link.contains("PDF")) {
                                System.out.println("Outside Url Pdf");
                            } else if (link != null && link.contains("mailto")) {
                                System.out.println("Outside Url Mail");
                            } else {
                                if (link != null && link.contains(linkUrl)) {
                                    mp.put(link, true);
                                }
                            }
                            mp2.put(link, true);
                        }
                    }

                    System.out.println("URL ->>>>  " + url);
                    int x = getNumberOfUniqueImages(driver);
                    int y = getWordCount(driver);
                    int[] z = getSpecificWordCount(driver);
                    count++;
                    String[] headers = {String.valueOf(count), url, String.valueOf(x), String.valueOf(y), String.valueOf(z[0]), String.valueOf(z[1])};
                   Utils.populateSheet(sheet,headers);

                }
            }
            Utils.saveWorkbook(path, "results" + ".xlsx", sheet);
            System.out.println("---------------------------------------------------------------------");

            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public int getNumberOfUniqueImages(WebDriver driver) {
        List<WebElement> imageElements = driver.findElements(By.tagName("img"));
        Set<String> images = new HashSet<>();
        for (WebElement imageElement : imageElements) {
            String src = imageElement.getAttribute("src");
            images.add(src);
        }
        System.out.println("Number of image on the page: " + images.size());
        return images.size();
    }

    public int getWordCount(WebDriver driver) {
        String pageText = driver.findElement(By.tagName("body")).getText();
        String[] words = pageText.split("\\s+");
        int wordCount = words.length;
        System.out.println("Number of words on the page: " + wordCount);
        return wordCount;
    }

    public int[] getSpecificWordCount(WebDriver driver) {
        String pageText = driver.findElement(By.tagName("body")).getText();

        int occurrencesAI = countOccurrences(pageText, "AI");
        int occurrencesCarbon = countOccurrences(pageText, "carbon");

        System.out.println("Occurrences of 'AI': " + occurrencesAI);
        System.out.println("Occurrences of 'carbon': " + occurrencesCarbon);
        int[] pair = new int[2];
        pair[0] = occurrencesAI;
        pair[1] = occurrencesCarbon;
        return pair;
    }

    private static int countOccurrences(String text, String word) {
        String[] words = text.split("\\s+");
        int count = 0;
        for (String w : words) {
            if (w.equalsIgnoreCase(word)) {
                count++;
            }
        }
        return count;
    }
}
