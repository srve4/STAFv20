package com.staf.utilities.report;

import com.staf.service.DriverScript;
import org.apache.log4j.Logger;

import java.awt.Desktop;
import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Report result summary
 */
public class ReportSummary {

    /**
     * Report Summary Writer
     */
    BufferedWriter reportSummaryWriter = null;

    /**
     * Email writer
     */
    BufferedWriter emailWriter = null;

    /**
     * Report Summary file
     */
    File reportSummaryFile;

    /**
     * Logger
     */
    Logger log = Logger.getLogger(ReportSummary.class);

    /**
     * Create Report Summary HTML file
     *
     * @param workFlowTitle - Workflow Title
     * @throws IOException            - IO Exception
     * @throws ClassNotFoundException - ClassNotFound Exception
     * @throws SQLException           - SQL Exception
     */
    public void createReportSummary(String workFlowTitle) throws IOException, ClassNotFoundException, SQLException {

        reportSummaryFile = new File(DriverScript.resFolderHTMLFile + "\\" + workFlowTitle + "_Summary.htm");
        DriverScript.eMailBodyTextFilePath = DriverScript.eMailBodyTextFilePath + "\\Body_Content.txt";
        File f1 = new File(DriverScript.eMailBodyTextFilePath);
        reportSummaryWriter = new BufferedWriter(new FileWriter(reportSummaryFile));
        emailWriter = new BufferedWriter(new FileWriter(f1));

        // ----------------------    TEST CASE END TIME   ------------------------
        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        // Local Time Zone
        df.setTimeZone(TimeZone.getDefault());
        String currentTime = df.format(date);
        // -----------------------------------------------------------------------

        reportSummaryWriter.write("<html>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<head>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<meta http-equiv= Content-Language content= en-us>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<meta http-equiv= Content-Type content= text/html; charset=windows-1252>");
        reportSummaryWriter.newLine();

        emailWriter.write("<p><em>" + "Dear All," + "</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        reportSummaryWriter.write("<center>");
        reportSummaryWriter.newLine();

        // IMAGE CAPTURED FROM WITHING THE FOLDER STRUCTURE
        reportSummaryWriter.write("<a href=\"http://www.gotomeeting.com\"><img src=\"Company.jpg\" width=\"677\" " +
                "height=\"198\" alt=\"Test Automation Execution Summary\" align=\"middle\" border=\"0\"></a>");

        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</center>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<title> " + workFlowTitle + " -  Test Automation Execution Results </title>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</head>");
        reportSummaryWriter.newLine();

        // ----------------------------------  E MAIL HTML HEAD CONTENT  ----------------------------------------------
        emailWriter.write("<html>");
        emailWriter.newLine();

        emailWriter.write("<head>");
        emailWriter.newLine();

        emailWriter.write("<meta http-equiv= Content-Language content= en-us>");
        emailWriter.newLine();

        emailWriter.write("<meta http-equiv= Content-Type content= text/html; charset=windows-1252>");
        emailWriter.newLine();

        emailWriter.write("<center>");
        emailWriter.newLine();

        // IMAGE CAPTURED FROM WITHING THE FOLDER STRUCTURE
        emailWriter.write("<img src=\"http://deepwebtechblog.com/wp-content/uploads/2010/04/World-data.jpg\" " +
                "width=\"600\" height=\"320\" alt=\"Test Automation Execution Summary\" align=\"middle\"/>");

        emailWriter.newLine();

        emailWriter.write("</center>");
        emailWriter.newLine();

        emailWriter.write("<title> " + workFlowTitle + " -  Test Automation Execution Results </title>");
        reportSummaryWriter.newLine();

        emailWriter.write("</head>");
        emailWriter.newLine();
        // --------------------------------------------------------------------------------------------------------

        reportSummaryWriter.write("<body>");
        reportSummaryWriter.newLine();

        emailWriter.write("<body>");
        emailWriter.newLine();

        reportSummaryWriter.write("<blockquote>");

        emailWriter.write("<blockquote>");
        reportSummaryWriter.write("<table align=center border=2 " +
                "bordercolor=#000000 " +
                "id=table1 width=80%" +
                " height=31 " +
                "bordercolorlight=#000000>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<tr bgcolor = aliceblue>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td COLSPAN = 7");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=center><font color=#000080 size=4 " +
                "face= \"Copperplate Gothic Bold\">&nbsp;" + workFlowTitle + " -  Automation Test Execution Results"
                + "</font><font face= \"Copperplate Gothic Bold\"></font> </p>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</tr>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<tr bgcolor = aliceblue>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td COLSPAN = 7 >");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=center><b><font color=#000080 size=2 face= Courier New>" + "&nbsp;"
                + "DATE & TIME :&nbsp;&nbsp;" + currentTime + "&nbsp;");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</tr>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<tr bgcolor= #1560BD>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width= 5%");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size= 2 >"
                + "S.No." + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width= 25%");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size=2>"
                + "Test Case" + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=5%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=center><b><font color = #FFFFFF face= Courier New size= 2>"
                + "Browser" + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width= 20%");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=center><b><font color = #FFFFFF face= Courier New size= 2>"
                + "Start Time" + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width= 20%");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size= 2 >"
                + "End Time" + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width= 15%");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size= 2 >"
                + "Elapsed Time" + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=10%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New size= 2>" + "Status"
                + "</b></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</tr>");
        reportSummaryWriter.newLine();

        // -------------------------------------  E MAIL BODY CONTENT  -------------------------------------------
        emailWriter.write("<table align=center border=2 bordercolor=#000000 id=table1 width=80% " +
                "height=31 bordercolorlight=#000000>");
        emailWriter.newLine();

        emailWriter.write("<tr bgcolor = aliceblue>");
        emailWriter.newLine();

        emailWriter.write("<td COLSPAN = 7");
        emailWriter.newLine();

        emailWriter.write("<p align=center><font color=#000080 size=4 " +
                "face= \"Copperplate Gothic Bold\">&nbsp;" + workFlowTitle + " -  Automation Test Execution Results"
                + "</font><font face= \"Copperplate Gothic Bold\"></font> </p>");
        emailWriter.newLine();

        emailWriter.write("</td>");
        emailWriter.newLine();

        emailWriter.write("</tr>");
        emailWriter.newLine();

        emailWriter.write("<tr bgcolor = aliceblue>");
        emailWriter.newLine();

        emailWriter.write("<td COLSPAN = 7 >");
        emailWriter.newLine();

        emailWriter.write("<p align=center><b><font color=#000080 size=2 face= Courier New>" + "&nbsp;"
                + "DATE & TIME :&nbsp;&nbsp;" + currentTime + "&nbsp;");
        emailWriter.newLine();

        emailWriter.write("</td>");
        emailWriter.newLine();

        emailWriter.write("</tr>");
        emailWriter.newLine();

        emailWriter.write("<tr bgcolor= #1560BD>");
        emailWriter.newLine();

        emailWriter.write("<td width= 5%");
        emailWriter.newLine();

        emailWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size= 2 >" + "S.No."
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("<td width= 25%");
        emailWriter.newLine();

        emailWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size=2>" + "Test Case"
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=5%>");
        emailWriter.newLine();

        emailWriter.write("<p align=center><b><font color = #FFFFFF face= Courier New size= 2>" + "Browser"
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=20%>");
        emailWriter.newLine();

        emailWriter.write("<p align=center><b><font color = #FFFFFF face= Courier New size= 2>" + "Start Time"
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("<td width= 20%");
        emailWriter.newLine();

        emailWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size= 2 >" + "End Time"
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("<td width= 20%");
        emailWriter.newLine();

        emailWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New  size= 2 >" + "Elapsed Time"
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=5%>");
        emailWriter.newLine();

        emailWriter.write("<p align= center><b><font color = #FFFFFF face= Courier New size= 2>" + "Status"
                + "</b></td>");
        emailWriter.newLine();

        emailWriter.write("</tr>");
        emailWriter.newLine();
        // ------------------------------------------------------------------------------------------------------------
    }

    /**
     * Method to prepare report summary
     *
     * @param sNum                  - Serial number
     * @param test_Case_Name        - Test case name
     * @param browser               - browser
     * @param testCase_start_Time   - Test case Start time
     * @param testCase_end_Time     - Test case End time
     * @param testCase_elapsed_Time - Test case Elapsed time
     * @param testCaseStatus        - test case status
     * @param workFlowtitle         - Work flow title
     * @throws IOException - IO Exception
     */
    public void prepareReportSummary(String sNum, String test_Case_Name,
                                     String browser, String testCase_start_Time,
                                     String testCase_end_Time, String testCase_elapsed_Time,
                                     String testCaseStatus, String workFlowtitle)
            throws IOException {
        reportSummaryWriter.write("<tr bgcolor = aliceblue>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=5%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=center><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + sNum + "</font></div></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=25%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><a href=" + workFlowtitle + "/"
                + test_Case_Name + ".htm><font face= Courier New size=2>"
                + test_Case_Name + "</font></a></div></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=5%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face= Courier New size=2>"
                + browser + "</font></a></div></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=20%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + testCase_start_Time + "</font></div></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=20%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + testCase_end_Time + "</font></div></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=15%>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + testCase_elapsed_Time + "</font></div></td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td width=10%>");
        reportSummaryWriter.newLine();

        // ---------------------------------------  E MAIL BODY CONTENT  ----------------------------------------------
        emailWriter.write("<tr bgcolor = aliceblue>");
        emailWriter.newLine();

        emailWriter.write("<td width=5%>");
        emailWriter.newLine();

        emailWriter.write("<p align=center><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + sNum + "</font></div></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=25%>");
        emailWriter.newLine();

        emailWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face= Courier New size=2>"
                + test_Case_Name + "</font></div></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=5%>");
        emailWriter.newLine();

        emailWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face= Courier New size=2>"
                + browser + "</font></a></div></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=20%>");
        emailWriter.newLine();

        emailWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + testCase_start_Time + "</font></div></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=20%>");
        emailWriter.newLine();

        emailWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + testCase_end_Time + "</font></div></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=20%>");
        emailWriter.newLine();

        emailWriter.write("<p align=left><div style=\"word-wrap: break-word;\"><font face=Courier New size=2>"
                + testCase_elapsed_Time + "</font></div></td>");
        emailWriter.newLine();

        emailWriter.write("<td width=5%>");
        emailWriter.newLine();
        // ------------------------------------------------------------------------------------------------------------

        if (testCaseStatus.equals("Pass")) {
            DriverScript.passNumReportSummary = DriverScript.passNumReportSummary + 1;
            reportSummaryWriter.write("<p align = center><b><font face= Courier New size= 2 color= #008000>" + "PASS"
                    + "</font></b></td>");
            reportSummaryWriter.newLine();
            emailWriter.write("<p align = center><b><font face= Courier New size= 2 color= #008000>" + "PASS"
                    + "</font></b></td>");
            emailWriter.newLine();

        } else if (testCaseStatus.equals("Fail")) {
            DriverScript.failNumReportSummary = DriverScript.failNumReportSummary + 1;
            reportSummaryWriter.write("<p align = center><b><font face= Courier New  size= 2 color= #FF0000>" + "FAIL"
                    + "</font></b></td>");
            reportSummaryWriter.newLine();
            emailWriter.write("<p align = center><b><font face= Courier New  size= 2 color= #FF0000>" + "FAIL"
                    + "</font></b></td>");
            emailWriter.newLine();

        }

        reportSummaryWriter.write("</tr>");
        reportSummaryWriter.newLine();

        emailWriter.write("</tr>");
        emailWriter.newLine();


        DriverScript.totalNumReportSummary = DriverScript.passNumReportSummary + DriverScript.failNumReportSummary;
    }

    public void completeReportSummary(String workFlowTitle) throws IOException {
        reportSummaryWriter.write("<tr bgcolor =aliceblue>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<td colspan= 7 align=right>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<table width=150 border=0 cellspacing =1 cellpadding=1>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<tr><td width=30%><b><font color= #000080 size=2 face= Courier New>"
                + "Total"
                + "</td><b></font><td width=45%><b><font color= #000080 size=2 face= Courier New>: "
                + DriverScript.totalNumReportSummary
                + "</b></font></td></tr>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<tr><td width=30%><b><font color= green face= Courier New size=2>" + "Passed"
                + "</b></font></td><td width=45%><b><font color= green face= Courier New size=2>: "
                + DriverScript.passNumReportSummary + "</b></font></td></tr>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("<tr><td width=30%><b><font color= #ff3333 face= Courier New size=2>" + "Failed"
                + "</b></font></td><td width=45%><b><font color= ff3333 face= Courier New size=2>: "
                + DriverScript.failNumReportSummary + "</b></font></td></tr>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</table>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</td>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</tr>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</blockquote>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</body>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.write("</html>");
        reportSummaryWriter.newLine();

        reportSummaryWriter.close();


        // ------------------------------------  E MAIL BODY CONTENT  -------------------------------------------
        emailWriter.write("<tr bgcolor =aliceblue>");
        emailWriter.newLine();

        emailWriter.write("<td colspan= 7 align=right>");
        emailWriter.newLine();

        emailWriter.write("<table width=150 border=0 cellspacing =1 cellpadding=1>");
        emailWriter.newLine();

        emailWriter.write("<tr><td width=30%><b><font color= #000080 size=2 face= Courier New>" + "Total"
                + "</td><b></font><td width=45%><b><font color= #000080 size=2 face= Courier New>: "
                + DriverScript.totalNumReportSummary + "</b></font></td></tr>");
        emailWriter.newLine();

        emailWriter.write("<tr><td width=30%><b><font color= green face= Courier New size=2>" + "Passed"
                + "</b></font></td><td width=45%><b><font color= green face= Courier New size=2>: "
                + DriverScript.passNumReportSummary + "</b></font></td></tr>");
        emailWriter.newLine();

        emailWriter.write("<tr><td width=30%><b><font color= #ff3333 face= Courier New size=2>" + "Failed"
                + "</b></font></td><td width=45%><b><font color= ff3333 face= Courier New size=2>: "
                + DriverScript.failNumReportSummary + "</b></font></td></tr>");
        emailWriter.newLine();

        emailWriter.write("</table>");
        emailWriter.newLine();

        emailWriter.write("</td>");
        emailWriter.newLine();

        emailWriter.write("</tr>");
        emailWriter.newLine();

        emailWriter.write("</blockquote>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("<p><em>Please find attached a zip file with this mail.</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();
        emailWriter.write("<p><em>On extracting the folder, please use \"" + workFlowTitle + "_Summary.htm\" " +
                "to know the Test Execution Results.</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("<p><em>In order to review the complete execution history of a particular Test Case," +
                " please click on the appropriate Test Case Link provided under the column heading \"Test Case\" " +
                "(from the " + workFlowTitle + "_Summary.htm file).</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("<p><em>The Execution Time of every step will be indicated " +
                "in the Test case Execution history.</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("<p><em>In order to navigate from a Test Case Execution History to the " + workFlowTitle + "_Summary.htm, " +
                "please click on the link \"Back\" (found at the Bottom Right corner, " +
                "just above the Pass / Fail status)</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("<p><em>Thanks,</em></p>");
        emailWriter.newLine();

        emailWriter.write("<p><em>Automation Testing Team</em></p>");
        emailWriter.newLine();

        emailWriter.write("<p><em>" + DriverScript.prjctName + "</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("<p><em>-------------------------- SYSTEM GENERATED MAIL. " +
                "PLEASE DO NOT REPLY TO THIS MAIL --------------------------------</em></p>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("</br>");
        emailWriter.newLine();

        emailWriter.write("</body>");
        emailWriter.newLine();

        emailWriter.write("</html>");
        emailWriter.newLine();

        emailWriter.close();
        // -----------------------------------------------------------------------------------------------------------

        reportSummaryWriter = null;
        emailWriter = null;

        log.info("Report summary created");
        log.info("Path : DriverScript.resFolderHTMLFile + \"\\\\\" + workFlowTitle + \"_Summary.htm");
        Desktop.getDesktop().browse(reportSummaryFile.toURI());
    }

    /**
     * Report the test scenario status
     *
     * @throws AWTException           -  AWT Exception
     * @throws IOException            - IO Exception
     * @throws ClassNotFoundException - ClassNotFound Exception
     * @throws SQLException           - SQL Exception
     */
    public void reportSummary() throws AWTException, IOException, ClassNotFoundException, SQLException {
        // -------------------------        S.NO.      ---------------------------
        String sNo = "" + DriverScript.sNoReportSummary;
        DriverScript.sNoReportSummary = DriverScript.sNoReportSummary + 1;
        // -----------------------------------------------------------------------

        prepareReportSummary(sNo,
                DriverScript.testCaseName,
                DriverScript.browser,
                DriverScript.testCaseStartTime,
                DriverScript.testCaseEndTime,
                DriverScript.testCaseElapsedTime,
                DriverScript.testCasestatus,
                DriverScript.workFlowTitle);

        log.info("Report summary created for " + DriverScript.workFlowTitle);
    }
}
