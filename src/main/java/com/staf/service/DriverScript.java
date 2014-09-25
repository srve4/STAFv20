package com.staf.service;

//TODO: Check for unwanted logs and remove those lines

//TODO: The Line number should be read as number and it should read in the seq 1,2,3,4..... and not as 10, 11, 12, ....
//TODO: Try getting the string. Parse it as Integer... but how to read it while capturing resultset in SQL query
//TODO: Fail the build if a test case fails
//TODO: Dont allow user to change the column names at the appropriate columns

//TODO: Formatting, Comments, Sysouts, Within page, Code review

//TODO: Try to execute the code review from eclipse or as per some standard and resolve all
//TODO: check if any warnings or other appear during the maven build process

//TODO: The log should inform clearly how many test ran and failed along with the reason for failure.

//TODO: Give a very high projected outlook for the Excel sheet and most mainly a neat and perfect HTML template, EMAIL template
//TODO: Add the points like Build Server, logging, EMailing, etc... Change the screenshots in the doc and display the whole document as the readme doc. Try to find a way to display the doc in a link rather than copyingg it

//TODO: System exit not happening in some weird scenarios
//TODO: Handle switching to multiple frames in a single statement
//TODO: Perform Code review. Add comments and Java Doc.
//TODO: Use Data structures wherever possible
//TODO: Try to create a test scenario which utilizes all the methods

//TODO: Have some knind of Data provider that gives the info to run in which browser and so on
//TODO: Know more about TestNG groups

//TODO: How to fail a test case in case of any error. It should log the problem as well

//TODO: GETTERS and SETTERS instead of static variables

import com.staf.utilities.STAFUtilities;
import com.staf.utilities.automation.AutomationUtilities;
import com.staf.utilities.errorreporting.ErrorReporter;
import com.staf.utilities.filehandling.FileHandle;
import com.staf.utilities.mail.SendMailWithAttachment;
import com.staf.utilities.pagehandling.PageHandler;
import com.staf.utilities.parser.Parser;
import com.staf.utilities.report.ReportEvent;
import com.staf.utilities.report.ReportSummary;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Driver Script - Engine of the STAF
 * This class reads the Scenarios to be executed and converts the keywords mentioned in the Business Flow
 * (for each scenario) to real action and automate the scenarios.
 *
 * @author Ashwanth Gunasekaran
 */
public class DriverScript {

    /**
     * WebDriver
     */
    public static WebDriver driver;

    /**
     * Serial Number for logging Report Event steps for every test case
     */
    public static int sNoReportEvent = 1;

    /**
     * Serial Number for logging Report Summary steps
     */
    public static int sNoReportSummary = 1;

    /**
     * Time sorter - Report Event
     */
    public static int timeSorterReportEvent = 0;

    /**
     * Number of steps passed in the test case
     */
    public static int passNum = 0;

    /**
     * Number of steps failed in the test case
     */
    public static int failNum = 0;

    /**
     * Total number of steps in the test case
     */
    public static int totalNum = 0;

    /**
     * Number of test cases passed
     */
    public static int passNumReportSummary = 0;

    /**
     * Number of test cases failed
     */
    public static int failNumReportSummary = 0;

    /**
     * Total number of test cases
     */
    public static int totalNumReportSummary = 0;

    /**
     * Test case Name
     */
    public static String testCaseName;

    /**
     * Project Name
     */
    public static String prjctName;

    /**
     * Function Name
     */
    private String functionName = "";

    /**
     * Data Sheet Name
     */
    private String dataSheetName = "";

    /**
     * Test Case execution status
     */
    public static String testCasestatus = "";

    /**
     * Directory Name
     */
    public static String directoryName;

    /**
     * Work flow folder name
     */
    public String workFlowFolderName;

    /**
     * Main Work flow folder name
     */
    public String mainWorkFlowFolderName;

    /**
     * Work flow title
     */
    public static String workFlowTitle;

    /**
     * Test Execution Result Folder Name
     */
    public static String testExecResultFolderName;

    /**
     * HTML File - Result folder
     */
    public static String resFolderHTMLFile;

    /**
     * Screenshots - Result folder
     */
    public static String resFolderScreenShots;

    /**
     * Email Body content - text file path
     */
    public static String eMailBodyTextFilePath;

    /**
     * Test Case - Start Time
     */
    public static String testCaseStartTime;

    /**
     * Test Case End Time
     */
    public static String testCaseEndTime;

    /**
     * Test Case execution - elapsed time
     */
    public static String testCaseElapsedTime;

    /**
     * Window title
     */
    private String windowTitle;

    /**
     * Window Handle
     */
    private String windowHandle;

    /**
     * Frame title
     */
    private String frameTitle;

    /**
     * Text in the Page
     */
    private String textInThePage;

    /**
     * Implicit Error message - from system
     */
    public static String implicitErrorMsg = "";

    /**
     * Explicit Error message - from framework
     */
    public static String explicitErrorMsg = "";

    /**
     * Line number
     */
    private String lineNo;

    /**
     * Instruction type
     */
    private String instrType;

    /**
     * Object Description
     */
    public static String objDesc;

    /**
     * Object Type
     */
    private String objType;

    /**
     * Object Reference - Name of Object
     */
    private String objReference;

    /**
     * Class Name where screen objects are present
     */
    private String screenObjClassName;

    /**
     * Parameters for the test step
     */
    private String data;

    /**
     * Field to Capture
     */
    private String fieldAttributeToCapture;

    /**
     * Data Sheet column where the captured value has to be stored
     */
    private String dataSheetOutputColumn;

    /**
     * Screenshot Type
     */
    public static String screenshotType;

    /**
     * Browser
     */
    public static String browser;

    /**
     * Update Data sheet connection
     */
    private Connection workFlowExcelUpdateDataConnection;

    /**
     * Update data sheet statement
     */
    private Statement workFlowExcelUpdateDataStatement;

    /**
     * Function sheet statement
     */
    private Statement workFlowExcelFunctionStatement;

    /**
     * Data sheet - select query result set
     */
    private ResultSet dataSelectQueryResultSet;

    /**
     * Instance of ReportEvent
     */
    private ReportEvent reportEvent = new ReportEvent();

    /**
     * Instance of Report Summary
     */
    private ReportSummary reportSummary = new ReportSummary();

    /**
     * File Handler
     */
    private FileHandle fileHandle = new FileHandle();

    /**
     * Instance of STAF Utilities
     */
    private STAFUtilities stafUtilities = new STAFUtilities();

    /**
     * Automation Utilities
     */
    private AutomationUtilities automationUtilities = new AutomationUtilities();

    /**
     * SendMailWithAttachment
     */
    private SendMailWithAttachment sendMailWithAttachment = new SendMailWithAttachment();

    /**
     * Error Reporting
     */
    private ErrorReporter errorReporter = new ErrorReporter();

    /**
     * Logger
     */
    private Logger log = Logger.getLogger(DriverScript.class);

    /**
     * driver() method reads the Business flow and converts the keywords to real action resulting in automation
     * and publish the result summary
     *
     * @throws Exception - Exception
     */
    public void driver()
            throws Exception, NoSuchMethodException, InvocationTargetException {

        Properties properties = new Properties();
        properties.setProperty("delayedClose", "0");

        // Create required folders to capture the results
        stafUtilities.createResultFolders();

        File directory = new File(".");
        mainWorkFlowFolderName =
                directory.getCanonicalPath() + "/src/test/resources/com/staf/workflow/Main_Workflow.xls";

        File mainWorkflow = new File(mainWorkFlowFolderName);

        // Take a backup of the Main workflow sheet
        fileHandle.backUpFile(mainWorkFlowFolderName, mainWorkflow.getParent() + "/"
                + mainWorkflow.getName().split("\\.")[0] + "_Copy.xls");

        // ------------------------  E MAIL   ------------------------------
        //TODO: Bring this from something like maven properties. Consider every test class as report events and at end give a report
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setSuppressWarnings(true);
        Workbook wrkBk = Workbook.getWorkbook(new File(directory.getCanonicalPath()
                + "/src/test/resources/com/staf/workflow/Main_Workflow.xls"), wbSettings);

        Sheet sht = wrkBk.getSheet("EMail");

        // Get the Parameters from Excel Sheet
        // Email flag
        String needAnEMail = sht.getCell(1, 0).getContents();

        String recepientEMailIdTo = sht.getCell(1, 3).getContents();
        String recepientEMailIdCc = sht.getCell(1, 4).getContents();
        String recepientEMailIdBcc = sht.getCell(1, 5).getContents();

        recepientEMailIdTo = Pattern.compile(";").matcher(recepientEMailIdTo).replaceAll(",");
        recepientEMailIdTo = Pattern.compile(" ").matcher(recepientEMailIdTo).replaceAll("");
        recepientEMailIdTo = Pattern.compile(",").matcher(recepientEMailIdTo).replaceAll(", ");

        recepientEMailIdCc = Pattern.compile(";").matcher(recepientEMailIdCc).replaceAll(",");
        recepientEMailIdCc = Pattern.compile(" ").matcher(recepientEMailIdCc).replaceAll("");
        recepientEMailIdCc = Pattern.compile(",").matcher(recepientEMailIdCc).replaceAll(", ");

        recepientEMailIdBcc = Pattern.compile(";").matcher(recepientEMailIdBcc).replaceAll(",");
        recepientEMailIdBcc = Pattern.compile(" ").matcher(recepientEMailIdBcc).replaceAll("");
        recepientEMailIdBcc = Pattern.compile(",").matcher(recepientEMailIdBcc).replaceAll(", ");

        wrkBk.close();
        // ---------------------------------------------------------------------

        // ----  Set up the connection to access the main work flow sheet ------
        Class.forName("com.hxtt.sql.excel.ExcelDriver").getConstructor().newInstance();
        Connection mainWorkFlowExcelConnection = DriverManager.getConnection("jdbc:excel:/"
                + mainWorkFlowFolderName + "?WRITE=true", properties);
        Statement mainWorkFlowExcelStatement = mainWorkFlowExcelConnection.createStatement();
        // ---------------------------------------------------------------------

        String workFlowSelectQuery = "SELECT * FROM WorkFlowsToExecute WHERE UPPER(Execution_Flag) = 'YES'";
        log.info(workFlowSelectQuery);
        ResultSet workFlowsResultSet = mainWorkFlowExcelStatement.executeQuery(workFlowSelectQuery);

        workFlowsResultSet.beforeFirst();
        while (workFlowsResultSet.next()) {
            workFlowTitle = String.valueOf(workFlowsResultSet.getString(1)).trim();
            workFlowFolderName = directory.getCanonicalPath() + "/src/test/resources/com/staf/workflow/modules/"
                    + workFlowTitle
                    + ".xlsx";

            File workFlow = new File(workFlowFolderName);
            // Take a Backup of the Work Flow inside module folder
            fileHandle.backUpFile(mainWorkFlowFolderName, workFlow.getParent() + '/'
                    + Pattern.compile("\\.").split(workFlow.getName())[0] + "_Copy.xlsx");

            //Create a folder under the title of the Work Flow title
            if ((new File(DriverScript.resFolderHTMLFile + '/' + workFlowTitle)).mkdirs()) {
                log.info("Automation Test Result folder created at "
                        + DriverScript.resFolderHTMLFile + '/' + workFlowTitle);
            }
            if ((new File(DriverScript.resFolderScreenShots + '/' + workFlowTitle)).mkdirs()) {
                log.info("Automation Test Result folder created at "
                        + DriverScript.resFolderScreenShots + '/' + workFlowTitle);
            }

            // ----  SET UP THE CONNECTION TO ACCESS THE RUN MANAGER EXCEL SHEET -----
            Class.forName("com.hxtt.sql.excel.ExcelDriver").getConstructor().newInstance();
            Connection workFlowExcelRunManagerConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            Statement workFlowExcelRunManagerStatement = workFlowExcelRunManagerConnection.createStatement();
            // -----------------------------------------------------------------------

            // ---------  SET UP THE CONNECTION TO ACCESS THE MAIN EXCEL SHEET -------
            Class.forName("com.hxtt.sql.excel.ExcelDriver").newInstance();
            Connection workFlowExcelMainConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            Statement workFlowExcelMainStatement = workFlowExcelMainConnection.createStatement();
            // -----------------------------------------------------------------------

            // - SET UP THE CONNECTION TO ACCESS THE UPDATION PART OF MAIN EXCEL SHEET -
            Class.forName("com.hxtt.sql.excel.ExcelDriver").newInstance();
            Connection workFlowExcelUpdateMainConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            Statement workFlowExcelUpdateMainStatement = workFlowExcelUpdateMainConnection.createStatement();
            // -------------------------------------------------------------------------

            // -----------  SET UP THE CONNECTION TO ACCESS THE DATA SHEET -------------
            Class.forName("com.hxtt.sql.excel.ExcelDriver").newInstance();
            Connection workFlowExcelDataSheetConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            Statement workFlowExcelDataSheetStatement = workFlowExcelDataSheetConnection.createStatement();
            // -------------------------------------------------------------------------

            // -  SET UP THE CONNECTION TO ACCESS THE UPDATION PART OF DATA EXCEL SHEET -
            Class.forName("com.hxtt.sql.excel.ExcelDriver").newInstance();
            workFlowExcelUpdateDataConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            workFlowExcelUpdateDataStatement = workFlowExcelUpdateDataConnection.createStatement();
            // --------------------------------------------------------------------------

            // -----  SET UP THE CONNECTION TO ACCESS THE INSTRUCTION FLOW SHEET ------
            Class.forName("com.hxtt.sql.excel.ExcelDriver").newInstance();
            Connection workFlowExcelBusinessFlowConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            Statement workFlowExcelBusinessFlowStatement = workFlowExcelBusinessFlowConnection.createStatement();
            // ------------------------------------------------------------------------

            // ---------  SET UP THE CONNECTION TO ACCESS THE FUNCTION SHEET ----------
            Class.forName("com.hxtt.sql.excel.ExcelDriver").newInstance();
            Connection workFlowExcelFunctionConnection = DriverManager.getConnection("jdbc:excel:/" + workFlowFolderName
                    + "?WRITE=true", properties);
            workFlowExcelFunctionStatement = workFlowExcelFunctionConnection.createStatement();
            // ------------------------------------------------------------------------

            // Create the report summary
            reportSummary.createReportSummary(workFlowTitle);

            int loopCount = 0;
            // -----------------------------  RUN MANAGER QUERY STARTS HERE  -----------------------------------------
            String runManagerSelectQuery = "SELECT * FROM RunManager WHERE UPPER(Execution_Flag) = 'YES'";
            log.info(runManagerSelectQuery);
            ResultSet runManagerSelectQueryResultSet
                    = workFlowExcelRunManagerStatement.executeQuery(runManagerSelectQuery);

            runManagerSelectQueryResultSet.beforeFirst();
            while (runManagerSelectQueryResultSet.next()) {
                String testCaseSequenceNo = String.valueOf(runManagerSelectQueryResultSet.getString(1));
                testCaseName = String.valueOf(runManagerSelectQueryResultSet.getString(2));
                testCasestatus = "Pass";

                browser = String.valueOf(runManagerSelectQueryResultSet.getString(5));
                if (browser.equalsIgnoreCase("null")) {
                    browser = "";
                }
                if (loopCount >= 1) {
                    try {
                        driver.quit();
                    } catch (NullPointerException exception) {
                        exception.printStackTrace();
                    }
                }
                loopCount = loopCount + 1;

                directoryName = directory.getCanonicalPath();
                String libFolder = directoryName + "\\lib\\";

                // ------------------------------   WEB DRIVER INITIALIZATION   ----------------------------------
                String s = browser.toUpperCase();
                try {
                    if (s.equals("FIREFOX")) {
                        // ---------- FIREFOX WEB DRIBVER ------------
                        driver = new FirefoxDriver();
                        driver.manage().window().maximize();
                        log.info("Firefox driver initialized");
                    } else if (s.equals("CHROME")) {
                        // ---------- CHROME WEB DRIVER --------------
                        File file = new File(libFolder + "chromedriver.exe");
                        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                        DesiredCapabilities capabilities;
                        capabilities = DesiredCapabilities.chrome();
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("test-type", "start-maximized",
                                "no-default-browser-check");
                        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                        driver = new ChromeDriver(capabilities);
                        log.info("Chrome driver initialized");
                    } else if (s.equals("IE")) {
                        // ------------ INTERNET EXPLORER -------------
                        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                        capabilities.setCapability(InternetExplorerDriver
                                .INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                        File file = new File(libFolder + "IEDriverServer.exe");
                        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

                        driver = new InternetExplorerDriver(capabilities);
                        driver.manage().window().maximize();
                        log.info("Internet Explorer driver initialized");
                    } else {
                        // ---------------- FIREFOX  -----------------
                        driver = new FirefoxDriver();
                        driver.manage().window().maximize();
                        log.info("Please enter the browser type " +
                                "in Runmanager sheet corresponding to each testcase.");
                        log.info("Firefox driver initialized");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    testCasestatus = "Fail";
                    log.info("Test Case : " + testCaseName + " - failed");
                    continue;
                }

                // Delete All Cookies
                driver.manage().deleteAllCookies();
                log.info("Cookies deleted");

                driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(60L, TimeUnit.SECONDS);

                // -----------------------------  MAIN Sheet QUERY  ---------------------------------------
                String mainSelectQuery = "SELECT * FROM Main WHERE Test_Case_Name = '"
                        + testCaseName + "' AND Sequence = '" + testCaseSequenceNo + '\'';
                log.info(mainSelectQuery);

                ResultSet mainSelectQueryResultSet = workFlowExcelMainStatement.executeQuery(mainSelectQuery);
                testCaseStartTime = stafUtilities.getTime();

                mainSelectQueryResultSet.beforeFirst();
                while (mainSelectQueryResultSet.next()) {
                    String testCaseDataSequenceNo = String.valueOf(mainSelectQueryResultSet.getString(3));
                    dataSheetName = String.valueOf(mainSelectQueryResultSet.getString(4));

                    // --------------------  DATA SHEET QUERY  ---------------------------------------------
                    String dateSheetSelectQuery = "SELECT * FROM " + dataSheetName + " WHERE Test_Case_Name = '"
                            + testCaseName + "' AND SequenceNumber = '" + testCaseDataSequenceNo + '\'';
                    log.info(dateSheetSelectQuery);
                    dataSelectQueryResultSet = workFlowExcelDataSheetStatement.executeQuery(dateSheetSelectQuery);

                    dataSelectQueryResultSet.beforeFirst();
                    dataSelectQueryResultSet.next();
                    // -------------------------------------------------------------------------------------

                    // ---------------------  TEST CASE INSTRUCTION FLOW  ----------------------------------
                    reportEvent.createReportEventHTML(workFlowTitle);
                    String testScriptInstrFlowSelectQuery = "SELECT * FROM BusinessFlow WHERE Test_Case_Name = '"
                            + testCaseName
                            + "' AND ((Comment = '') OR (Comment IS Null)) ORDER BY Line_Number";
                    log.info(testScriptInstrFlowSelectQuery);

                    ResultSet testCaseBusinessFlowSelectQueryResultSet
                            = workFlowExcelBusinessFlowStatement.executeQuery(testScriptInstrFlowSelectQuery);

                    testCaseBusinessFlowSelectQueryResultSet.beforeFirst();
                    while (testCaseBusinessFlowSelectQueryResultSet.next()) {
                        try {
                            if (testCasestatus.equalsIgnoreCase("Fail")) {
                                testCasestatus = "Fail";
                                break;
                            }

                            lineNo = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(2));
                            if (lineNo.equalsIgnoreCase("null")) {
                                lineNo = "";
                            }

                            instrType = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(3));
                            if (instrType.equalsIgnoreCase("null")) {
                                instrType = "";
                            }

                            objDesc = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(4));
                            if (objDesc.equalsIgnoreCase("null")) {
                                objDesc = "";
                            }

                            objType = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(5));
                            if (objType.equalsIgnoreCase("null")) {
                                objType = "";
                            }

                            objReference = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(6));
                            if (objReference.equalsIgnoreCase("null")) {
                                objReference = "";
                            }

                            data = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(7));
                            if (data.equalsIgnoreCase("null")) {
                                data = "";
                            }
                            data = getData(data);

                            fieldAttributeToCapture =
                                    String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(8));
                            if (fieldAttributeToCapture.equalsIgnoreCase("null")) {
                                fieldAttributeToCapture = "";
                            }

                            screenObjClassName = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(9));
                            if (screenObjClassName.equalsIgnoreCase("null")) {
                                screenObjClassName = "";
                            }

                            functionName = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(10));
                            if (functionName.equalsIgnoreCase("null")) {
                                functionName = "";
                            }

                            dataSheetOutputColumn =
                                    String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(11));
                            if (dataSheetOutputColumn.equalsIgnoreCase("null")) {
                                dataSheetOutputColumn = "";
                            }

                            windowTitle = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(12));
                            if (windowTitle.equalsIgnoreCase("null")) {
                                windowTitle = "";
                            }

                            windowHandle = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(13));
                            if (windowHandle.equalsIgnoreCase("null")) {
                                windowHandle = "";
                            }

                            frameTitle = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(14));
                            if (frameTitle.equalsIgnoreCase("null")) {
                                frameTitle = "";
                            }

                            textInThePage = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(15));
                            if (textInThePage.equalsIgnoreCase("null")) {
                                textInThePage = "";
                            }

                            screenshotType = String.valueOf(testCaseBusinessFlowSelectQueryResultSet.getString(16));
                            if (screenshotType.equalsIgnoreCase("null")) {
                                screenshotType = "";
                            }

                            log.info("Read line num " + lineNo + " from the Business flow - " + testCaseName);
                            automationUtilities
                                    .switchtoUserRequestedWindow(windowTitle, windowHandle, frameTitle, textInThePage);

                            executeStmnt();
                        } catch (Exception e) {
                            testCasestatus = "Fail";
                            log.error(e.getMessage());
                            break;
                        }
                    }

                    implicitErrorMsg = "";
                    explicitErrorMsg = "";

                    testCaseEndTime = stafUtilities.getTime();
                    testCaseElapsedTime = stafUtilities.getTimeDifference(testCaseStartTime, testCaseEndTime);

                    String updateMainSheetQuery = "UPDATE Main SET Result = '"
                            + testCasestatus + "', Elapsed_Time = '"
                            + testCaseElapsedTime + "', Execution_Start_Date_Time = '"
                            + testCaseStartTime + "', Execution_End_Date_Time = '" + testCaseEndTime
                            + "' WHERE Test_Case_Name = '" + testCaseName + '\'';
                    log.info(updateMainSheetQuery);
                    workFlowExcelUpdateMainStatement.execute(updateMainSheetQuery);
                    workFlowExcelUpdateMainConnection.commit();
                    log.info("Updated successfully");

                    reportEvent.completeReportEvent(workFlowTitle);
                    reportSummary.reportSummary();

                    testCasestatus = "";
                }
            }
            // -----------------------------  RUN MANAGER QUERY ENDS HERE  -------------------------------------------

            // Close all the Statements and Connections
            workFlowExcelBusinessFlowStatement.close();
            workFlowExcelBusinessFlowConnection.close();
            workFlowExcelDataSheetStatement.close();
            workFlowExcelDataSheetConnection.close();
            workFlowExcelMainStatement.close();
            workFlowExcelMainConnection.close();
            workFlowExcelUpdateDataStatement.close();
            workFlowExcelUpdateDataConnection.close();
            workFlowExcelUpdateMainStatement.close();
            workFlowExcelUpdateMainConnection.close();
            workFlowExcelRunManagerStatement.close();
            workFlowExcelRunManagerConnection.close();

            // Complete the Result Summary
            reportSummary.completeReportSummary(workFlowTitle);

            // ------------------------------  E MAIL THE RESULT FOLDER IF OPTED  --------------------------------
            if (needAnEMail.equalsIgnoreCase("Yes")) {
                String textExecResultFolderPath = resFolderHTMLFile;
                String zipFilePath = directory.getCanonicalPath() + "/Results/" + testExecResultFolderName + ".zip";

                if (!recepientEMailIdTo.isEmpty()) {
                    if (recepientEMailIdTo.contains("@")) {
                        fileHandle.zipThisFolder(textExecResultFolderPath, zipFilePath);

                        String mailSubject = prjctName + " - " + workFlowTitle + " - " +
                                "Test Automation Execution Results" + " - "
                                + stafUtilities.getTime();

                        sendMailWithAttachment.sendEmail(mailSubject, recepientEMailIdTo,
                                recepientEMailIdCc, recepientEMailIdBcc,
                                zipFilePath, eMailBodyTextFilePath);

                        log.info("Results for " + workFlowTitle + " are emailed to the appropriate group.");
                        fileHandle.deleteFile(zipFilePath);
                    }
                }
            }
            // ----------------------------------------------------------------------------------------------------

            try {
                driver.close();
                log.info("Web Driver is closed");
                driver.quit();
                log.info("Web Driver quited");
                log.info("Total Scenarios executed  : " + totalNumReportSummary);
                log.info("Passed Scenarios          : " + passNumReportSummary);
                log.info("Failed Scenarios          : " + failNumReportSummary);
                assert totalNumReportSummary == passNumReportSummary : "\n" +
                        failNumReportSummary + " Test Scenarios Failed. \nPlease check the Result summary\n";
            } catch (NullPointerException exception) {
                log.warn("No Driver Exists");
            }

            log.info("Execution Completed");
        }

        // Main work flow connection and statement are closed
        mainWorkFlowExcelStatement.close();
        mainWorkFlowExcelConnection.close();
    }

    /**
     * Execute based on the option mentioned in the Instruction Type column of the Work flow sheet
     *
     * @throws java.sql.SQLException                       - SQLException
     * @throws java.io.IOException                         - IO Exception
     * @throws jxl.read.biff.BiffException                 - Biff Exception
     * @throws jxl.write.WriteException                    - Write Exception
     * @throws ClassNotFoundException                      - Class Not Found Exception
     * @throws java.awt.AWTException                       - AWT Exception
     * @throws InterruptedException                        - Interrupted Exception
     * @throws InstantiationException                      - Instantiation Exception
     * @throws IllegalAccessException                      - Illegal Access Exception
     * @throws NoSuchMethodException                       - No Such Method Exception
     * @throws NoSuchFieldException                        - No Such Field Exception
     * @throws SecurityException                           - Security Exception
     * @throws IllegalArgumentException                    - Illegal Argument Exception
     * @throws java.lang.reflect.InvocationTargetException - Invocation Target Exception
     */
    public void executeStmnt()
            throws Exception {

        instrType = instrType.trim().toUpperCase();

        if (instrType.equals("STATEMENT")) {
            performAction();

        } else if (instrType.equals("FUNCTION")) {
            log.info("Function Part:   " + instrType + "  " + functionName);
            // ---------------------  TEST CASE INSTRUCTION FLOW STARTS HERE ---------------------------------------

            String functionInstrFlowSelectQuery = "SELECT * FROM FunctionInstrFlow WHERE Function_Name = '"
                    + functionName + "' AND ((Comment = '') OR (Comment IS Null)) ORDER BY Line_Number";
            log.info(functionInstrFlowSelectQuery);
            ResultSet functionFlowSelectQueryResultSet
                    = workFlowExcelFunctionStatement.executeQuery(functionInstrFlowSelectQuery);

            functionFlowSelectQueryResultSet.beforeFirst();
            while (functionFlowSelectQueryResultSet.next()) {

                if (testCasestatus.equalsIgnoreCase("Fail")) {
                    break;
                }

                lineNo = String.valueOf(functionFlowSelectQueryResultSet.getString(2));
                if (lineNo.equalsIgnoreCase("null")) {
                    lineNo = "";
                }

                instrType = String.valueOf(functionFlowSelectQueryResultSet.getString(3));
                if (instrType.equalsIgnoreCase("null")) {
                    instrType = "";
                }

                objDesc = String.valueOf(functionFlowSelectQueryResultSet.getString(4));
                if (objDesc.equalsIgnoreCase("null")) {
                    objDesc = "";
                }

                objType = String.valueOf(functionFlowSelectQueryResultSet.getString(5));
                if (objType.equalsIgnoreCase("null")) {
                    objType = "";
                }

                objReference = String.valueOf(functionFlowSelectQueryResultSet.getString(6));
                if (objReference.equalsIgnoreCase("null")) {
                    objReference = "";
                }

                data = String.valueOf(functionFlowSelectQueryResultSet.getString(7));
                if (data.equalsIgnoreCase("null")) {
                    data = "";
                }
                data = getData(data);

                fieldAttributeToCapture = String.valueOf(functionFlowSelectQueryResultSet.getString(8));
                if (fieldAttributeToCapture.equalsIgnoreCase("null")) {
                    fieldAttributeToCapture = "";
                }

                screenObjClassName = String.valueOf(functionFlowSelectQueryResultSet.getString(9));
                if (screenObjClassName.equalsIgnoreCase("null")) {
                    screenObjClassName = "";
                }

                functionName = String.valueOf(functionFlowSelectQueryResultSet.getString(10));
                if (functionName.equalsIgnoreCase("null")) {
                    functionName = "";
                }

                dataSheetOutputColumn = String.valueOf(functionFlowSelectQueryResultSet.getString(11));
                if (dataSheetOutputColumn.equalsIgnoreCase("null")) {
                    dataSheetOutputColumn = "";
                }

                windowTitle = String.valueOf(functionFlowSelectQueryResultSet.getString(12));
                if (windowTitle.equalsIgnoreCase("null")) {
                    windowTitle = "";
                }

                windowHandle = String.valueOf(functionFlowSelectQueryResultSet.getString(13));
                if (windowHandle.equalsIgnoreCase("null")) {
                    windowHandle = "";
                }

                frameTitle = String.valueOf(functionFlowSelectQueryResultSet.getString(14));
                if (frameTitle.equalsIgnoreCase("null")) {
                    frameTitle = "";
                }

                textInThePage = String.valueOf(functionFlowSelectQueryResultSet.getString(15));
                if (textInThePage.equalsIgnoreCase("null")) {
                    textInThePage = "";
                }

                screenshotType = String.valueOf(functionFlowSelectQueryResultSet.getString(16));
                if (screenshotType.equalsIgnoreCase("null")) {
                    screenshotType = "";
                }

                log.info("Read line num " + lineNo + " from the Functional flow - " + functionName);
                automationUtilities.switchtoUserRequestedWindow(windowTitle, windowHandle, frameTitle, textInThePage);

                log.info(objReference + "  " + objReference + " from " + screenObjClassName);

                if (testCasestatus.equalsIgnoreCase("Fail")) {
                    break;
                }

                functionName = String.valueOf(functionFlowSelectQueryResultSet.getString(11));
                if (functionName.equalsIgnoreCase("null")) {
                    functionName = "";
                }
                executeStmnt();
            }
            // -----------------------------------------------------------------------------------------------------

            functionName = "";

        } else if (instrType.equals("GENERIC_FUNCTION")) {
            log.info("Trying to execute generic function - " + functionName);
            // --------------- TO HANDLE THE GENERIC FUNCTION PART -----------------
            try {
                String className = screenObjClassName;

                Class<?> cls = Class.forName(className);
                Object obj = cls.getConstructor().newInstance();

                String[] inputs = Pattern.compile(",").split(data);
                for (int i = 0; i < inputs.length; i++) {
                    inputs[i] = inputs[i].trim();
                }

                String methodName = functionName;
                for (Method method : cls.getDeclaredMethods()) {
                    if (method.getName().equalsIgnoreCase(methodName)) {
                        Class<?>[] paremeters = method.getParameterTypes();

                        int numberOfParam = paremeters.length;
                        if (inputs.length == numberOfParam) {
                            cls.getDeclaredMethod(method.getName(), paremeters);
                            method.setAccessible(true);

                            Object[] inputObj = new Object[inputs.length];

                            for (int i = 0; i < inputObj.length; i++) {
                                inputObj[i] = Parser.parseType(inputs[i], paremeters[i]);
                                (inputObj[i].getClass()).cast(inputObj[i]);
                            }
                            Object output = method.invoke(obj, inputObj);

                            log.info("Generic function - " + functionName + " executed");
                            if (output != null) {
                                String updateDataSheetQuery = "update  " + dataSheetName
                                        + " set " + dataSheetOutputColumn + " = '" + output
                                        + "' where Test_Case_Name = '" + testCaseName + '\'';
                                log.info(updateDataSheetQuery);
                                log.info("The return value - " + output + " from " + functionName
                                        + " (" + className + ") " + " loaded in to " + dataSheetOutputColumn
                                        + " column (corresponding to the test case - " + testCaseName
                                        + " of the " + dataSheetName + " sheet in the work flow "
                                        + workFlowTitle);
                                workFlowExcelUpdateDataStatement.executeUpdate(updateDataSheetQuery);
                                workFlowExcelUpdateDataConnection.commit();
                                log.info("Updated successfully");
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage());
                testCasestatus = "Fail";
                log.info("Test Case : " + testCaseName + " - failed");
            }

        } else if (instrType.equals("WAIT")) {
            log.info("Waiting for " + data + " seconds");
            // Wait for the Value given
            Thread.sleep((long) ((Integer.parseInt(data)) * 1000));
        }
    }

    /**
     * Performs the action on appropriate UI based on the value passed in the Object Type.
     *
     * @throws java.sql.SQLException       - SQLException
     * @throws java.io.IOException         - IO Exception
     * @throws jxl.read.biff.BiffException - Biff Exception
     * @throws jxl.write.WriteException    - Write Exception
     * @throws ClassNotFoundException      - Class Not Found Exception
     * @throws SecurityException           - Security Exception
     * @throws IllegalArgumentException    - Illegal Argument Exception
     * @throws IllegalAccessException      - Illegal Access Exception
     * @throws java.awt.AWTException       - AWT Exception
     * @throws InstantiationException      - Instantiation Exception
     * @throws NoSuchFieldException        - No Such Field Exception
     */
    public void performAction()
            throws Exception {

        objType = objType.toUpperCase();
        WebElement webElement = null;
        Select slctwebElement;
        Field field;
        Class<?> screenObjClass;
        String errorDesc = "";

        try {
            assert screenObjClassName != null;
            assert objReference != null;
            if (!(screenObjClassName.equalsIgnoreCase("") || objReference.equalsIgnoreCase(""))) {
                screenObjClass = Class.forName(screenObjClassName);
                field = screenObjClass.getDeclaredField(objReference);
                field.setAccessible(true);
                webElement = null;
                webElement = (WebElement) field.get(screenObjClass.getConstructor().newInstance());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            testCasestatus = "Fail";
            log.info("Test Case : " + testCaseName + " - failed");
        }
        boolean m_objFound = false;

        if (objType.equals("OPEN_BROWSER")) {
            try {
                if (!data.isEmpty()) {
                    driver.get(data);
                    automationUtilities.windowsOpened.add(driver.getWindowHandle());
                    //--------------------- Maximize the Window ---------------------------------------------------
                    driver.manage().window().setPosition(new Point(0, 0));
                    java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
                    driver.manage().window().setSize(dim);
                    // --------------------------------------------------------------------------------------------

                    testCasestatus = "Pass";
                    Thread.sleep(2000L);
                    reportEvent.reportEvent("Enter the URL in to the Browser Window"
                            , "URL should be enterd successfully", "URL - " + data + " - is used", "Pass");
                    log.info("Opened new window");
                } else {
                    log.warn("No url is entered in the Data column of the Business/functional flow");
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                testCasestatus = "Fail";
                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Problem in opening the browser";
                reportEvent.reportEvent("Enter the URL in to the Browser Window",
                        "URL should be enterd successfully",
                        "Unable to access the requested URL  :  " + data + " - Please open the log " +
                                "to know more about the cause for Test Case failure",
                        "Fail");
                errorReporter.errorReporting();
            }

        } else if (objType.equals("OPEN_NEW_WINDOW")) {
            try {
                if (!data.isEmpty()) {
                    automationUtilities.openTab(data);
                    automationUtilities.switchTotheWindowOpenedNow();

                    testCasestatus = "Pass";
                    Thread.sleep(2000L);
                    reportEvent.reportEvent("Enter the URL in a New Tab", "URL should be enterd successfully"
                            , "URL - " + data + " - is opened in a New Tab", "Pass");
                    log.info("Opened new tab");
                } else {
                    log.warn("No url is entered in the Data column of the Business/functional flow");
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                testCasestatus = "Fail";
                reportEvent.reportEvent("Enter the URL in a New Tab",
                        "URL should be enterd successfully",
                        "Unable to access the requested URL  :  " + data + " - Please open the log " +
                                "to know more about the cause for Test Case failure",
                        "Fail");
                errorReporter.errorReporting();
            }

        } else if (objType.equals("CAPTURE_CURRENT_WINDOW_TITLE_IN_TO_DATA_SHEET")) {
            String currentWindowTitle = driver.getTitle();
            String updateDataSheetQueryCaptureWndwTitle = "UPDATE " + dataSheetName + ' ' +
                    "SET " + data + " = '" + currentWindowTitle
                    + "' WHERE Test_Case_Name = '" + testCaseName + '\'';
            log.info(updateDataSheetQueryCaptureWndwTitle);
            workFlowExcelUpdateDataStatement.executeUpdate(updateDataSheetQueryCaptureWndwTitle);
            workFlowExcelUpdateDataConnection.commit();
            log.info("Updated successfully");

            if (!data.isEmpty()) {
                testCasestatus = "Pass";
            } else {
                testCasestatus = "Fail";
                explicitErrorMsg = "Unable to capture the Window Title";
                errorReporter.errorReporting();
            }

        } else if (objType.equals("CAPTURE_CURRENT_WINDOW_HANDLE_IN_TO_DATA_SHEET")) {
            String updateDataSheetQueryCaptureWndwHndl = "UPDATE " + dataSheetName + ' ' +
                    "SET " + data + " = '" + driver.getWindowHandle() +
                    "' WHERE Test_Case_Name = '" + testCaseName + '\'';
            log.info(updateDataSheetQueryCaptureWndwHndl);
            workFlowExcelUpdateDataStatement.executeUpdate(updateDataSheetQueryCaptureWndwHndl);
            workFlowExcelUpdateDataConnection.commit();
            log.info("Updated successfully");

            if (!data.isEmpty()) {
                testCasestatus = "Pass";
            } else {
                testCasestatus = "Fail";

                explicitErrorMsg = "Unable to capture the Window Handle";

                errorReporter.errorReporting();
            }

        } else if (objType.equals("REFRESH_CURRENT_WINDOW")) {
            driver.navigate().refresh();

        } else if (objType.equals("MOVE_FORWARD_FROM_CURRENT_WINDOW")) {
            driver.navigate().forward();

        } else if (objType.equals("MOVE_BACKWARD_FROM_CURRENT_WINDOW")) {
            driver.navigate().back();

        } else if (objType.equals("SWITCH_TO_FRAME")) {
            driver.switchTo().frame(data);
            //updateCurrentAndPreviousWindowHandle();

        } else if (objType.equals("SWITCH_TO_WINDOW_HANDLE")) {
            automationUtilities.switchToWindowHandle(data);
            //updateCurrentAndPreviousWindowHandle();

        } else if (objType.equals("SWITCH_TO_WINDOW_TITLE")) {
            automationUtilities.switchToWindowTitle(data);
            //updateCurrentAndPreviousWindowHandle();

        } else if (objType.equals("SWITCH_TO_NEXT_WINDOW")) {
            automationUtilities.switchWindow();
            //updateCurrentAndPreviousWindowHandle();

        } else if (objType.equals("SWITCH_REQUESTED_WINDOW")) {
            automationUtilities.switchtoUserRequestedWindow(windowTitle, windowHandle, frameTitle, textInThePage);

        } else if (objType.equals("NAVIGATE_TO_URL")) {
            URL url = new URL(data);
            driver.navigate().to(url);

        } else if (objType.equals("SWITCH_TO_PREVIOUS_WINDOW")) {
            if (!automationUtilities.previousWindowHandle.isEmpty()) {
                automationUtilities.switchToWindowHandle(automationUtilities.previousWindowHandle);
            }
            //updateCurrentAndPreviousWindowHandle();

        } else if (objType.equals("MOVE_FOCUS_TO_ELEMENT")) {
            try {
                assert webElement != null;
                if (webElement.isEnabled()) {
                    new Actions(driver).moveToElement(webElement);
                }
            } catch (Exception e) {
                implicitErrorMsg = e.getMessage();
                e.printStackTrace();
                errorReporter.errorReporting();
            }

        } else if (objType.equals("LINK")) {
            try {

                assert webElement != null;
                if (webElement.isEnabled()) {
                    webElement.click();
                    m_objFound = true;
                }
            } catch (NoSuchElementException e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Link : " + objDesc,
                            "Link should be clicked successfully",
                            "Link - " + objDesc + " (" + objReference + " from " + screenObjClassName + ") "
                                    + " is clicked successfully",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Click on the appopriate Link",
                            "Link should be clicked successfully",
                            "Link - " + objReference + " from " + screenObjClassName + " - is clicked successfully",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";
                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Link provided" + "  -  " + objReference
                        + " from " + screenObjClassName;
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Link : " + objDesc,
                            "Link should be clicked successfully",
                            "Link - " + objDesc + " (" + objReference + " from " + screenObjClassName + ") "
                                    + " is not clicked"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Click on the appopriate Link",
                            "Link should be clicked successfully",
                            "Link - " + objReference + " from " + screenObjClassName + " - is not clicked"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }
                errorReporter.errorReporting();
            }

        } else if (objType.equals("PARTIAL_LINK")) {
            try {

                assert webElement != null;
                if (webElement.isEnabled()) {
                    webElement.click();
                    m_objFound = true;
                }
            } catch (NoSuchElementException e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }
            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Link : " + objDesc,
                            "Link should be clicked successfully",
                            "Link - " + objDesc + " (" + objReference + " from " + screenObjClassName + ") "
                                    + " is clicked successfully",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Click on the appopriate Link", "Link should be clicked successfully"
                            , "Link - " + objReference + " from " + screenObjClassName + " - is clicked successfully",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";
                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Link provided" + "  -  " + objReference
                        + " from " + screenObjClassName;
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Link : " + objDesc,
                            "Link should be clicked successfully",
                            "Link - " + objDesc + " (" + objReference + " from " + screenObjClassName + ") "
                                    + " is not clicked"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Click on the appopriate Link",
                            "Link should be clicked successfully",
                            "Link - " + objReference + " from " + screenObjClassName + " - is not clicked"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }
                errorReporter.errorReporting();
            }

        } else if (objType.equals("TEXT")) {
            try {
                assert webElement != null;
                if (webElement.isEnabled()) {
                    m_objFound = true;
                    webElement.clear();
                    webElement.sendKeys(data);
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Enter a valid value in to the Text Field : " + objDesc,
                            "Value should be entered successfully",
                            data + " is entered in to the Text Field - " + objDesc,
                            "Pass");
                } else {
                    reportEvent.reportEvent("Enter a valid value in to the Text Field",
                            "Appropriate Value should be entered successfully",
                            data + " is entered in to the Text Field",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Text field provided"
                        + "  -  " + objReference + " from " + screenObjClassName;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Enter a valid value in to the Text Field : " + objDesc,
                            "Value should be entered successfully",
                            "Text Field " + objDesc + " is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Enter a valid value in to the Text Field",
                            "Appropriate Value should be entered successfully",
                            "Text Field is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("RADIO")) {
            try {
                assert webElement != null;
                if (webElement.isEnabled()) {
                    webElement.click();
                    m_objFound = true;
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Radio Button : " + objDesc,
                            "Radio Button should be clicked successfully",
                            objDesc + " - Radio Button clicked successfully",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Click on the Radio Button"
                            , "Radio Button should be clicked successfully"
                            , "Button clicked successfully"
                            , "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Radio button provided"
                        + "  -  " + objReference + " from " + screenObjClassName;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Radio Button : " + objDesc,
                            "Radio Button should be clicked successfully",
                            objDesc + " - Radio Button not clicked."
                                    + "Reason: Radio Button not displayed / enabled"
                                    + " - Please open the log "
                                    + "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Click on the Radio Button",
                            "Radio Button should be clicked successfully",
                            "Radio Button not clicked."
                                    + "Reason: Radio Button not displayed / enabled"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("CHECKBOX_SELECT")) {
            try {

                assert webElement != null;
                if (webElement.isEnabled()) {
                    if (webElement.getAttribute("checked") == null) {
                        webElement.click();
                    }
                    m_objFound = true;
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select the Check Box : " + objDesc
                            , "Check Box should be selected successfully"
                            , "Check Box - " + objDesc + " - is selected"
                            , "Pass");
                } else {
                    reportEvent.reportEvent("Select the Check Box"
                            , "Appropriate Check Box should be selected successfully"
                            , "Check Box is selected"
                            , "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Check Box entered " + "  -  " + objReference
                        + " from " + screenObjClassName;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select the Check Box : " + objDesc,
                            "Check Box should be selected successfully",
                            "Check Box " + objDesc + " is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Select the Check Box",
                            "Appropriate Check Box should be selected successfully",
                            "Check Box is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("CHECKBOX_UNSELECT")) {
            try {

                assert webElement != null;
                if (webElement.isEnabled()) {
                    if (webElement.getAttribute("checked") != null) {
                        webElement.click();
                    }
                    m_objFound = true;
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select the Check Box : " + objDesc,
                            "Check Box should be selected successfully",
                            "Check Box - " + objDesc + " - is selected",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Select the Check Box",
                            "Appropriate Check Box should be selected successfully",
                            "Check Box is selected",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Check Box entered " + "  -  " + objReference
                        + " from " + screenObjClassName;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select the Check Box : " + objDesc,
                            "Check Box should be selected successfully",
                            "Check Box " + objDesc + " is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Select the Check Box",
                            "Appropriate Check Box should be selected successfully",
                            "Check Box is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("SELECT_BY_VALUE")) {
            try {

                assert webElement != null;
                if (webElement.isEnabled()) {
                    slctwebElement = new Select(webElement);
                    slctwebElement.selectByValue(data);
                    webElement.sendKeys(Keys.ENTER);
                    m_objFound = true;
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select a value from the Drop Down : " + objDesc,
                            "Value should be selected successfully",
                            data + " is selected in to the Drop Down - " + objDesc,
                            "Pass");
                } else {
                    reportEvent.reportEvent("Select a value from the Drop Down",
                            "Appropriate Value should be selected successfully",
                            data + " is selected in to the Drop Down",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Drop Down entered " + "  -  " + objReference;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select a value from the Drop Down : " + objDesc,
                            "Value should be selected successfully",
                            "Drop Down " + objDesc + " is neither enabled nor displayed" +
                                    " - Please open the Error Sheet" +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Select a value from the Drop Down",
                            "Appropriate Value should be selected successfully",
                            "Drop Down is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("SELECT_BY_INDEX")) {
            int valueToInt = Integer.parseInt(data);
            try {

                assert webElement != null;
                if (webElement.isEnabled()) {
                    slctwebElement = new Select(webElement);
                    slctwebElement.selectByIndex(valueToInt);
                    webElement.sendKeys(Keys.ENTER);
                    m_objFound = true;
                }

            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select a value from the Drop Down : " + objDesc,
                            "Value should be selected successfully",
                            data + " is selected in to the Drop Down - " + objDesc,
                            "Pass");
                } else {
                    reportEvent.reportEvent("Select a value from the Drop Down",
                            "Appropriate Value should be selected successfully",
                            data + " is selected in to the Drop Down",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Drop Down entered " + "  -  " + objReference;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Select a value from the Drop Down : " + objDesc,
                            "Value should be selected successfully",
                            "Drop Down " + objDesc + " is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Select a value from the Drop Down",
                            "Appropriate Value should be selected successfully",
                            "Drop Down is neither enabled nor displayed"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("BUTTON")) {
            try {
                assert webElement != null;
                if (webElement.isEnabled()) {
                    webElement.click();
                    m_objFound = true;
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Button : " + objDesc,
                            "Button should be clicked successfully",
                            objDesc + " Button clicked successfully",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Click on the Button",
                            "Button should be clicked successfully",
                            "Button clicked successfully",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Web Button entered " + "  -  " + objReference
                        + " from " + screenObjClassName;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Button : " + objDesc,
                            "Button should be clicked successfully",
                            objDesc + " Button not clicked."
                                    + "Reason: Button not displayed / enabled"
                                    + " - Please open the log to know more " +
                                    "about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Click on the Button",
                            "Button should be clicked successfully",
                            "Button not clicked." + "Reason: Button not displayed / enabled"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("SUBMIT")) {
            try {
                assert webElement != null;
                if (webElement.isEnabled()) {
                    webElement.submit();
                    m_objFound = true;
                }
            } catch (Exception e) {
                errorDesc = e.getMessage();
                e.printStackTrace();
                m_objFound = false;
            }

            if (m_objFound) {
                testCasestatus = "Pass";
                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Button : " + objDesc,
                            "Button should be clicked successfully",
                            objDesc + " Button clicked successfully",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Click on the Button",
                            "Button should be clicked successfully",
                            "Button clicked successfully",
                            "Pass");
                }
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to access the Submit Button  entered " + "  -  " + objReference
                        + " from " + screenObjClassName;

                if (!objDesc.isEmpty()) {
                    reportEvent.reportEvent("Click on the Button : " + objDesc,
                            "Button should be clicked successfully",
                            objDesc + " Button not clicked."
                                    + "Reason: Button not displayed / enabled"
                                    + " - Please open the log "
                                    + "to know more about the cause for Test Case failure",
                            "Fail");
                } else {
                    reportEvent.reportEvent("Click on the Button",
                            "Button should be clicked successfully",
                            "Button not clicked." + "Reason: Button not displayed / enabled"
                                    + " - Please open the log " +
                                    "to know more about the cause for Test Case failure",
                            "Fail");
                }

                errorReporter.errorReporting();
            }

        } else if (objType.equals("CAPTURE_ANY_ATTRIBUTE_VALUE_IN_FIELD")) {
            String tempCapturedValue;
            String updateDataSheetQuery;

            try {
                assert webElement != null;
                tempCapturedValue = webElement.getAttribute(fieldAttributeToCapture);
                updateDataSheetQuery = "UPDATE " + dataSheetName + ' ' +
                        "SET " + dataSheetOutputColumn + " = '" + tempCapturedValue +
                        "' WHERE Test_Case_Name = '" + testCaseName + '\'';

                workFlowExcelUpdateDataStatement.executeUpdate(updateDataSheetQuery);
                workFlowExcelUpdateDataConnection.commit();

                m_objFound = true;
            } catch (Exception e) {
                m_objFound = false;
                errorDesc = e.getMessage();
                e.printStackTrace();
            }
            if (m_objFound) {
                testCasestatus = "Pass";
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to capture the value in to the Data Sheet";

                errorReporter.errorReporting();
            }

        } else if (objType.equals("CAPTURE_TEXT_FROM_FIELD")) {
            String tempCapturedValue1;
            String updateDataSheetQuery1;

            try {

                assert webElement != null;
                tempCapturedValue1 = webElement.getText();
                updateDataSheetQuery1 = "UPDATE " + dataSheetName + ' ' +
                        "SET " + dataSheetOutputColumn + " = '" + tempCapturedValue1 +
                        "' WHERE Test_Case_Name = '" + testCaseName + '\'';

                workFlowExcelUpdateDataStatement.executeUpdate(updateDataSheetQuery1);
                workFlowExcelUpdateDataConnection.commit();

                m_objFound = true;
            } catch (Exception e) {
                m_objFound = false;
                errorDesc = e.getMessage();
                e.printStackTrace();
            }
            if (m_objFound) {
                testCasestatus = "Pass";
            } else {
                testCasestatus = "Fail";

                implicitErrorMsg = errorDesc;
                explicitErrorMsg = "Unable to capture the value in to the Data Sheet";

                errorReporter.errorReporting();
            }

        } else if (objType.equals("VERIFY_TEXT_INSIDE_IN_A_PAGE")) {
            try {
                boolean txtFound = automationUtilities.findTextInsidePage(data);
                if (txtFound) {
                    m_objFound = true;
                    reportEvent.reportEvent("Verify if the text " + data
                                    + " is present in the web page - " + driver.getTitle(),
                            "Text to be verified should be visible in the page",
                            "Text is present in the Web Page",
                            "Pass");
                } else {
                    reportEvent.reportEvent("Verify if the text " + data
                                    + " is present in the web page - " + driver.getTitle(),
                            "Text to be verified should be visible in the page",
                            "Text is not present in the Web Page",
                            "Fail");
                }
            } catch (Exception e) {
                m_objFound = false;
                errorDesc = e.getMessage();
                e.printStackTrace();
            }

            if (m_objFound) {
                testCasestatus = "Pass";
            } else {
                testCasestatus = "Fail";
                implicitErrorMsg = errorDesc;
                errorReporter.errorReporting();
            }


        } else if (objType.equals("CLOSE_ALL_WINDOWS_ASSOCIATED_WITH_THE_DRIVER")) {
            //Close the browser
            driver.quit();

        } else if (objType.equals("VALIDATE_ALL_LINKS_ARE_NOT_BROKEN")) {
            PageHandler pageHandler = new PageHandler();
            List<WebElement> links = pageHandler.findAllLinks(driver);
            Set<String> allLinks = new LinkedHashSet<String>(links.size());
            for (WebElement link : links) {
                String url = link.getAttribute("href");
                if (url.startsWith("http")) {
                    allLinks.add(url);
                }
            }
            log.info("Total number of Links found " + allLinks.size());
            boolean result = true;

            if (allLinks.isEmpty()) {
                reportEvent.reportEvent("Validate all the links in the Web Page",
                        "No links in the page should be broken",
                        "No links are present in the web page",
                        "Fail");
                result = false;
            }

            for (String link : allLinks) {
                try {
                    String returnMessage = pageHandler.isLinkBroken(new URL(link));
                    reportEvent.reportEvent("Validate that the link is not broken",
                            "Link should not be broken",
                            "URL: " + link + " returned " + returnMessage,
                            "Pass");
                } catch (Exception exp) {
                    reportEvent.reportEvent("Validate that the link is not broken",
                            "Link should not be broken",
                            "At " + link + " Exception occurred; " + exp.getMessage(),
                            "Fail");
                    errorDesc = exp.getMessage();
                    exp.printStackTrace();
                    result = false;
                }

                if (!result) {
                    testCasestatus = "Fail";
                    implicitErrorMsg = errorDesc;
                    errorReporter.errorReporting();
                }
            }

        } else {
            testCasestatus = "Fail";
            explicitErrorMsg = "Enter Apropriate OBJECT TYPE in to the TestScriptInstrFlow Sheet";
            log.info("Enter Apropriate OBJECT TYPE in to the TestScriptInstrFlow Sheet");
            errorReporter.errorReporting();
        }

        if (errorDesc.length() > 0) {
            testCasestatus = "Fail";
        }
    }

    public String getData(String data) throws Exception {
        String dataIdentified = "";

        if (!data.isEmpty()) {
            String firstChar = data.substring(0, 1);

            if ((firstChar.trim()).equalsIgnoreCase("#")) {
                dataIdentified = data.substring(1);

                dataIdentified = dataIdentified.replaceAll("\'", "\\\'");
                dataIdentified = dataIdentified.replaceAll("\"", "\\\"");

                return dataIdentified;
            } else {
                try {
                    dataIdentified = String.valueOf(dataSelectQueryResultSet.getString(data));
                    data = dataIdentified;
                    return dataIdentified;
                } catch (Exception e) {
                    log.info("No column in data sheet is identified using the name " + data
                            + ". If you are planning to use a data sheet value " +
                            "from a particular column corresponding to the test case, " +
                            "then please enter the column name exactly as givrn in the Data sheet " +
                            "(without a hash sign #). " +
                            "If you are planning to enter a value to be used directly in to the code, " +
                            "then please use a hash sign # before the value you intend to pass.");
                    DriverScript.explicitErrorMsg = "No column in data sheet is identified using the name " + data
                            + ". If you are planning to use a data sheet value " +
                            "from a particular column corresponding to the test case, " +
                            "then please enter the column name exactly as givrn in the Data sheet " +
                            "(without a hash sign #). " +
                            "If you are planning to enter a value to be used directly in to the code, " +
                            "then please use a hash sign # before the value you intend to pass.";
                    DriverScript.implicitErrorMsg = e.getMessage();
                    DriverScript.testCasestatus = "Fail";
                    e.printStackTrace();
                    errorReporter.errorReporting();
                }
            }
        }
        return dataIdentified;
    }
}
