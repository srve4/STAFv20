package com.staf.utilities.pagehandling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to validate the web page
 */
public class PageHandler {

    /**
     * To find all the links in a web page
     *
     * @param driver - Web Driver
     * @return the list of links in the web page
     */
    public List<WebElement> findAllLinks(WebDriver driver) {

        List<WebElement> elementList;
        elementList = driver.findElements(By.tagName("a"));
        elementList.addAll(driver.findElements(By.tagName("img")));

        List<WebElement> finalList = new ArrayList<WebElement>();

        for (WebElement element : elementList) {
            if (element.getAttribute("href") != null) {
                finalList.add(element);
            }
        }
        return finalList;
    }

    /**
     * To check if the link is broken
     *
     * @param url - the URL object that needs to be validated
     * @return the Response
     * @throws IOException - IO Exception
     */
    public String isLinkBroken(URL url) throws IOException {

        String response;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.connect();
            response = connection.getResponseMessage();
            connection.disconnect();
            return response;
        } catch (IOException ioException) {
            return ioException.getMessage();
        }
    }
}
