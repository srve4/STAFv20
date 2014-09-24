package com.staf.utilities;

import com.staf.service.DriverScript;
import com.staf.utilities.filehandling.FileHandle;
import com.staf.utilities.report.ReportEvent;
import jxl.write.WriteException;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class to hold the utilities related to STAF framework
 */
public class STAFUtilities {
    /**
     * Instance of ReportEvent
     */
    ReportEvent reportEvent = new ReportEvent();

    /**
     * File Handler
     */
    FileHandle fileHandle = new FileHandle();

    /**
     * Logger
     */
    Logger log = Logger.getLogger(STAFUtilities.class);

    /**
     * Creates the Result folders
     *
     * @throws IOException    - IO Exception
     * @throws WriteException - Write Exception
     */
    public void createResultFolders() throws IOException, WriteException {
        // ----------------------- CREATE NEW FOLDERS if not existing --------------------------------
        File directory = new File(".");
        log.info("Current directory's canonical path: " + directory.getCanonicalPath());

        DriverScript.directoryName = directory.getCanonicalPath();
        String resFolder = directory.getCanonicalPath() + "/Results";

        if ((new File(resFolder)).mkdirs()) {
            log.info("Results folder is created");
        }
        // -------------------------------------------------------------------------------------------

        // ----------------------------- CREATE THE TEST RESULT FOLDER NAME --------------------------
        //Get CURRENT DATE and TIME
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        // Local Time Zone
        df.setTimeZone(TimeZone.getDefault());
        DriverScript.testCaseStartTime = df.format(date);
        String currentDateAndTime = df.format(date);

        DriverScript.testExecResultFolderName = DriverScript.prjctName
                + "_" + currentDateAndTime + "_" + "Test Automation Result";

        DriverScript.eMailBodyTextFilePath = directory.getCanonicalPath()
                + "/Results/" + DriverScript.testExecResultFolderName + "/Email";
        DriverScript.resFolderHTMLFile = directory.getCanonicalPath()
                + "/Results/" + DriverScript.testExecResultFolderName + "/Automation_Test_Execution_Results";
        DriverScript.resFolderScreenShots = directory.getCanonicalPath()
                + "/Results/" + DriverScript.testExecResultFolderName + "/ScreenShots";

        if ((new File(DriverScript.eMailBodyTextFilePath)).mkdirs()) {
            log.info("Email folder created");
        }

        // The Folders that are to be zipped and mailed
        if ((new File(DriverScript.resFolderHTMLFile)).mkdirs()) {
            log.info("Automation_Test_Execution_Results folder created");
        }
        if ((new File(DriverScript.resFolderScreenShots)).mkdirs()) {
            log.info("ScreenShots folder created");
        }
        // -------------------------------------------------------------------------------------------

        // Copy the Image file in to the Image Folder from the Image folder available in the Framework folder
        String imageSource = directory.getCanonicalPath() + "/src/main/resources/com/staf/logo/Company.jpg";
        log.info("IMAGE SOURCE : " + imageSource);
        String imageDest = DriverScript.resFolderHTMLFile + "/Company.jpg";

        fileHandle.copyFile(imageSource, imageDest);
        // --------------------------------------------------------------------------------------------------
    }

    /**
     * Returns the current time
     *
     * @return current time
     */
    public String getTime() {
        // ---------------------    TEST CASE START TIME   -----------------------
        Date testCaseStartDate = new Date();
        DateFormat dfTestCaseStartTime = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

        // Local Time Zone
        dfTestCaseStartTime.setTimeZone(TimeZone.getDefault());
        // -----------------------------------------------------------------------

        return dfTestCaseStartTime.format(testCaseStartDate);
    }

    /**
     * Returns the time difference
     *
     * @param startTime - Start time
     * @param endTime   - End time
     * @return Time difference
     * @throws java.text.ParseException - Parse Exception
     */
    public String getTimeDifference(String startTime, String endTime) throws java.text.ParseException {
        long remainder;
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        Date d1;
        Date d2;

        d1 = format.parse(startTime);
        d2 = format.parse(endTime);

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        remainder = diff % (60 * 60 * 1000);
        long diffMinutes = remainder / (60 * 1000);
        long diffSeconds = remainder % (60 * 1000) / 1000;

        return diffHours + " Hours, " + diffMinutes + " Minutes, " + diffSeconds + " Seconds";
    }

    /**
     * Get the text appearing in another text
     *
     * @param expctdTextAfterWhichYouNeedToGetText - Expected text after which you need to capture
     * @param fromWhichOccuranceOfExpctdRes        - From which occurance of the expected result
     * @param lengthOfTextToBeIdentified           - Length of the text to be identified
     * @return the captured text
     */
    public boolean getTextAppearingAfterAnotherText(String expctdTextAfterWhichYouNeedToGetText, String fromWhichOccuranceOfExpctdRes, String lengthOfTextToBeIdentified) {

        int occurance = 0;
        int lngthOfTxt = 0;
        String[] variousOccurancesOfTheExpctdTxt;

        try {
            occurance = Integer.parseInt(fromWhichOccuranceOfExpctdRes);
            lngthOfTxt = Integer.parseInt(lengthOfTextToBeIdentified);
        } catch (Exception e) {
            log.info("Get Text After the Expected Text - " + expctdTextAfterWhichYouNeedToGetText + ". " + "\n" + "fromWhichOccuranceOfExpctdRes, noOfCharsFromExpctdText and lengthOfTextToBeIdentified should be Integers");
        }

        boolean elmntFnd = DriverScript.driver.getPageSource().contains(expctdTextAfterWhichYouNeedToGetText);

        if (elmntFnd) {
            log.info("The Text Element is found !!!!");

            String text = DriverScript.driver.getPageSource();
            int noOfOccurances = countIndexOf(text, expctdTextAfterWhichYouNeedToGetText);

            int lengthOfWord = expctdTextAfterWhichYouNeedToGetText.length();
            log.info("Length of the String : " + lengthOfWord);

            if (noOfOccurances >= occurance) {
                variousOccurancesOfTheExpctdTxt = text.split(expctdTextAfterWhichYouNeedToGetText);
                int l = variousOccurancesOfTheExpctdTxt.length;

                if (l >= occurance) {
                    String temp = variousOccurancesOfTheExpctdTxt[occurance];
                    String expctd = temp.substring(0, lngthOfTxt);

                    log.info("Expected Text  -  " + expctd);
                }
            } else {
                log.info("There are only " + noOfOccurances + " of the Text - " + expctdTextAfterWhichYouNeedToGetText
                        + ". " + "\n" + "But you have asked to get text after the " + occurance + "th occurance. \n"
                        + "Please check the Page and enter a valid Occurance Number.");
            }

            elmntFnd = true;
        } else {
            log.info("Expected Text - Not found!!!");
            elmntFnd = false;
        }

        return elmntFnd;
    }

    /**
     * Return the count of the search text within the given text
     *
     * @param text   - Given Text
     * @param search - Text to search
     * @return the count of the search text within the given text
     */
    public int countIndexOf(String text, String search) {
        return text.split(search).length - 1;
    }

    /**
     * Clicks on the Ok button of the pop up
     *
     * @param expectedPopUPText - Expected Pop up text
     * @throws AWTException           - AWT Exception
     * @throws ClassNotFoundException - ClassNotFound Exception
     * @throws IOException            - IO Exception
     * @throws SQLException           - SQL Exception
     * @throws InterruptedException   - Interrupted Exception
     */
    public void clickOnOkButtonPopUp(String expectedPopUPText)
            throws AWTException, ClassNotFoundException, IOException, SQLException, InterruptedException {

        if (DriverScript.driver.switchTo().alert() != null) {
            Alert alert = DriverScript.driver.switchTo().alert();
            String alertText = alert.getText();

            if (alertText.contains(expectedPopUPText)) {
                Thread.sleep(1000);
                reportEvent.reportEvent("Validate the appearance of the Pop up dialog",
                        "Pop up dialog message should appear as expected",
                        "Pop up dialog message appeared with the text message as - \"" + alertText + "\"",
                        "Pass", DriverScript.workFlowTitle);
                alert.accept();
                Thread.sleep(1000);
                reportEvent.reportEvent("Click on the Ok button of the Pop up dialog",
                        "Pop up dialog should disappear as expected",
                        "Pop up dialog disappeared",
                        "Pass", DriverScript.workFlowTitle);
            }
        }
    }
}
