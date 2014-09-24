package com.staf.utilities.report;

import com.staf.service.DriverScript;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ReportEvent {

    /**
     * Report Event writer
     */
    BufferedWriter reportEventWriter = null;

    /**
     * Logger
     */
    Logger log = Logger.getLogger(ReportEvent.class);

    public void createReportEventHTML(String workFlowtitle) throws IOException, ClassNotFoundException, SQLException {
        File f = new File(DriverScript.resFolderHTMLFile
                + "\\" + workFlowtitle + "\\" + DriverScript.testCaseName + ".htm");
        reportEventWriter = new BufferedWriter(new FileWriter(f));

        // ----------------------    CURRENT TIME   ------------------------
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // Local Time Zone
        df.setTimeZone(TimeZone.getDefault());
        String currentTime = df.format(date);
        // -----------------------------------------------------------------------

        reportEventWriter.write("<html>");
        reportEventWriter.newLine();

        reportEventWriter.write("<head>");
        reportEventWriter.newLine();

        reportEventWriter.write("<meta http-equiv= Content-Language content= en-us>");
        reportEventWriter.newLine();

        reportEventWriter.write("<meta http-equiv= Content-Type content= text/html; charset=windows-1252>");
        reportEventWriter.newLine();

        reportEventWriter.write("<center>");
        reportEventWriter.newLine();

        // IMAGE CAPTURED FROM INTERNET - USEFUL WHEN OPENING THE FOLDER FROM OUTSIDE THE LOCAL SYSTEM
        // IMAGE CAPTURED FROM WITHING THE FOLDER STRUCTURE
        reportEventWriter.write("<a href=\"http://www.gotomeeting.com\">" +
                "<img src=\"../Company.jpg\" width=\"677\" " +
                "height=\"198\"" +
                " alt=\"Test Automation Execution Results\" " +
                "align=\"middle\" border=\"0\"></a>");

        reportEventWriter.newLine();

        reportEventWriter.write("</center>");
        reportEventWriter.newLine();

//*******************************************************************************************************

        reportEventWriter.write("<title>"
                + DriverScript.testCaseName + " - Test Automation Execution Results"
                + "</title>");
        reportEventWriter.newLine();

        reportEventWriter.write("</head>");
        reportEventWriter.newLine();

        reportEventWriter.write("<body>");
        reportEventWriter.newLine();

        reportEventWriter.write("<blockquote>");
        reportEventWriter.newLine();

        reportEventWriter.write("<table align=center " +
                "border=2 bordercolor=#000000 " +
                "id=table1 width=100% " +
                "height=31 " +
                "bordercolorlight=#000000>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr bgcolor = aliceblue>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td COLSPAN = 6");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=center><font " +
                "color=#000080 " +
                "size=4 " +
                "face= \"Copperplate Gothic Bold\">&nbsp;" + DriverScript.testCaseName
                + " - Automation Test Execution Results"
                + "</font><font face= \"Copperplate Gothic Bold\"></font> </p>");
        reportEventWriter.newLine();

        reportEventWriter.write("</td>");
        reportEventWriter.newLine();

        reportEventWriter.write("</tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr bgcolor = aliceblue>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td COLSPAN = 6 >");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=center><b><font color=#000080 size=2 " +
                "face= Courier New>" + "&nbsp;" + "DATE & TIME :&nbsp;&nbsp;" + currentTime + "&nbsp;");
        reportEventWriter.newLine();

        reportEventWriter.write("</td>");
        reportEventWriter.newLine();

        reportEventWriter.write("</tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr bgcolor= #1560BD>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width= 5%");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size=2 >"
                + "S No" + "</b></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width= 25%");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New size=2>"
                + "Description" + "</b></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width= 25%");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New size=2>"
                + "Expected Result" + "</b></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width= 25%");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New size=2>"
                + "Actual Result" + "</b></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width= 5%>");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=center><b><font color = #FFFFFF face= Courier New size=2>"
                + "Status" + "</b></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width= 15%>");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New size=2>"
                + "Execution Time" + "</b></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("</tr>");
        reportEventWriter.newLine();
    }

    public void completeReportEvent(String workFlowTitle) throws IOException {
        reportEventWriter.write("<tr bgcolor =aliceblue>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td colspan= 6 align=right>");
        reportEventWriter.newLine();

        reportEventWriter.write("<table width=150 border=0 cellspacing =1 cellpadding=1>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr><td width=30%><b><font color= #000080 size=2 face= Courier New>"
                + "Total" + "</td><b></font><td width=45%><b><font color= #000080 size=2 face= Courier New>: "
                + DriverScript.totalNum + "</b></font></td></tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr><td width=30%><b><font color= green face= Courier New size=2>" + "Passed"
                + "</b></font></td><td width=45%><b><font color= green face= Courier New size=2>: "
                + DriverScript.passNum + "</b></font></td></tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr><td width=30%><b><font color= #ff3333 face= Courier New size=2>" + "Failed"
                + "</b></font></td><td width=45%><b><font color= ff3333 face= Courier New size=2>: "
                + DriverScript.failNum + "</b></font></td></tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("</table>");
        reportEventWriter.newLine();

        reportEventWriter.write("</td>");
        reportEventWriter.newLine();

        reportEventWriter.write("</tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("<table align=center border=0 width=75% height=31>");
        reportEventWriter.newLine();

        reportEventWriter.write("<tr><td width=100% align=right><a href='../" + workFlowTitle + "_Summary.htm'><font color= #000080 size=2 " +
                "face= Courier New><b>"
                + "<- BACK" + "</b></a></font></td></tr>");
        reportEventWriter.newLine();

        reportEventWriter.write("</table>");
        reportEventWriter.newLine();

        reportEventWriter.write("</blockquote>");
        reportEventWriter.newLine();

        reportEventWriter.write("</body>");
        reportEventWriter.newLine();

        reportEventWriter.write("</html>");
        reportEventWriter.newLine();

        reportEventWriter.close();
        reportEventWriter = null;

        // Reintialiazing the Serial number to 1
        DriverScript.sNoReportEvent = 1;
        DriverScript.passNum = 0;
        DriverScript.failNum = 0;
        DriverScript.totalNum = 0;
    }

    public void prepareReportEventHTML(String sNum, String description, String expected_Result, String actual_Result,
                                       String execution_Time, String status, String screenshot_Path)
            throws IOException {

        reportEventWriter.write("<tr bgcolor = aliceblue >");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width=5%>");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=center><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + sNum + "</font></div></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width=25%>");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face= Courier New size=2>"
                + description + "</font></div></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width=25%>");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face= Courier New size=2>"
                + expected_Result + "</font></div></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td width=25%>");
        reportEventWriter.newLine();

        reportEventWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face= Courier New size=2>"
                + actual_Result + "</font></div></td>");
        reportEventWriter.newLine();

        reportEventWriter.write("<td height=23 width=5%>");
        reportEventWriter.newLine();

        if (status.equals("Pass")) {
            DriverScript.passNum = DriverScript.passNum + 1;
            reportEventWriter.write("<p align = center><b><font face= Courier New size= 2 color= #008000>" + "PASS"
                    + "</font></b></td>");
            reportEventWriter.newLine();

        } else if (status.equals("Fail")) {
            DriverScript.failNum = DriverScript.failNum + 1;
            reportEventWriter.write("<p align = center><b><font face= Courier New  size= 2 color= #FF0000>" + "FAIL"
                    + "</font></b></td>");
            reportEventWriter.newLine();

        }

        reportEventWriter.write("<td width=15%>");
        reportEventWriter.newLine();

        if (screenshot_Path.trim().length() > 0) {
            reportEventWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><a href='file:///"
                    + screenshot_Path + "'><b><font face=Courier New size=2>" + execution_Time + "</font></div></td>");
        } else {
            reportEventWriter.write("<p align=left><div style=\"word-wrap: break-word;\">" +
                    "<b><font face=Courier New size=2>" + execution_Time + "</font></div></td>");
        }
        reportEventWriter.newLine();

        reportEventWriter.write("</tr>");
        reportEventWriter.newLine();

        DriverScript.totalNum = DriverScript.passNum + DriverScript.failNum;
    }


    public void reportEvent(String description, String expected_Result,
                            String actual_Result, String status, String workFlowtitle)
            throws AWTException, IOException, ClassNotFoundException, SQLException {

        // -------------------------        S.NO.      ---------------------------
        String sNo = "" + DriverScript.sNoReportEvent;
        DriverScript.sNoReportEvent = DriverScript.sNoReportEvent + 1;
        // -----------------------------------------------------------------------

        DriverScript.testCasestatus = status;

        // -------------------------- GET CURRENT DATE AND TIME -------------------
        int timeSort = DriverScript.timeSorterReportEvent;
        if (DriverScript.timeSorterReportEvent >= 9) {
            DriverScript.timeSorterReportEvent = 0;
        } else {
            DriverScript.timeSorterReportEvent = DriverScript.timeSorterReportEvent + 1;
        }

        //Get CURRENT DATE and TIME
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        // Local Time Zone
        df.setTimeZone(TimeZone.getDefault());
        String currentDateAndTime = df.format(date);
        // -----------------------------------------------------------------------

        // ----------------------- CREATE SCREENSHOT -----------------------------
        String screenshotName = DriverScript.testCaseName + "_" + currentDateAndTime + "_" + timeSort;
        String screenshotPath = DriverScript.resFolderScreenShots + "/" + workFlowtitle + "/"
                + DriverScript.testCaseName + "/" + screenshotName + ".jpg";

        if (DriverScript.screenshotType.trim().length() > 0) {
            try {
                File scrFile;
                Robot robot = new Robot();
                if ((new File(DriverScript.resFolderScreenShots + "/" + workFlowtitle + "/"
                        + DriverScript.testCaseName)).mkdirs()) {
                    log.info("Screenshots folder created for the test - " + DriverScript.testCaseName);
                }

                if (DriverScript.screenshotType.equalsIgnoreCase("Print_Full_Web_Page")) {
                    scrFile = ((TakesScreenshot) DriverScript.driver).getScreenshotAs(OutputType.FILE);
                    // Now you can do whatever you need to do with it, for example copy somewhere
                    FileUtils.copyFile(scrFile, new File(screenshotPath));
                } else if (DriverScript.screenshotType.equalsIgnoreCase("Print_Screen")) {
                    BufferedImage bufferedImage = robot.createScreenCapture
                            (new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    // Save as PNG
                    scrFile = new File(screenshotPath);
                    ImageIO.write(bufferedImage, "jpg", scrFile);
                }
            } catch (IOException e) {
                log.info("Could not save the screenshot.\n");
                e.printStackTrace();
            }
        } else {
            screenshotPath = "";
        }
        prepareReportEventHTML(sNo, description, expected_Result,
                actual_Result, currentDateAndTime, status, screenshotPath);
        log.info(actual_Result + " - reported");
        log.info("Test step status : " + status);
    }
}
