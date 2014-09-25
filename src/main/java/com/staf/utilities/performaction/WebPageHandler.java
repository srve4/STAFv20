package com.staf.utilities.performaction;

import com.staf.service.DriverScript;
import com.staf.utilities.automation.AutomationUtilities;
import com.staf.utilities.errorreporting.ErrorReporter;
import com.staf.utilities.report.ReportEvent;
import org.apache.log4j.Logger;
import org.openqa.selenium.Point;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Utility class to open a web page as per user's need
 */
public class WebPageHandler {

    /**
     * Automation Utilities
     */
    private AutomationUtilities m_automationUtilities = new AutomationUtilities();

    /**
     * Logger
     */
    private Logger m_logger = Logger.getLogger(WebPageHandler.class);

    /**
     * Error Reporting
     */
    private ErrorReporter m_errorReporter = new ErrorReporter();

    /**
     * Instance of ReportEvent
     */
    private ReportEvent m_reportEvent = new ReportEvent();

    public void openBrowser(String url)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            if (!url.isEmpty()) {
                DriverScript.driver.get(url);
                m_automationUtilities.windowsOpened.add(DriverScript.driver.getWindowHandle());
                //--------------------- Maximize the Window ---------------------------------------------------
                DriverScript.driver.manage().window().setPosition(new Point(0, 0));
                java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                org.openqa.selenium.Dimension dim = new org.openqa.selenium.Dimension((int) screenSize.getWidth(),
                        (int) screenSize.getHeight());
                DriverScript.driver.manage().window().setSize(dim);
                // --------------------------------------------------------------------------------------------

                DriverScript.testCasestatus = "Pass";
                Thread.sleep(2000L);
                m_reportEvent.reportEvent("Enter the URL in to the Browser Window"
                        , "URL should be enterd successfully", "URL - " + url + " - is used", "Pass");
                m_logger.info("Opened new window");
            } else {
                m_logger.warn("No url is entered in the Data column of the Business/functional flow");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = e.getMessage();
            DriverScript.explicitErrorMsg = "Problem in opening the browser";
            m_reportEvent.reportEvent("Enter the URL in to the Browser Window",
                    "URL should be enterd successfully",
                    "Unable to access the requested URL  :  " + url + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void openNewWindow(String url)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            if (!url.isEmpty()) {
                m_automationUtilities.openTab(url);
                m_automationUtilities.switchTotheWindowOpenedNow();

                DriverScript.testCasestatus = "Pass";
                Thread.sleep(2000L);
                m_reportEvent.reportEvent("Enter the URL in a New Tab", "URL should be enterd successfully"
                        , "URL - " + url + " - is opened in a New Tab", "Pass");
                m_logger.info("Opened new tab");
            } else {
                m_logger.warn("No url is entered in the Data column of the Business/functional flow");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = e.getMessage();
            DriverScript.explicitErrorMsg = "Problem in opening a new window";
            m_reportEvent.reportEvent("Enter the URL in a New Tab",
                    "URL should be enterd successfully",
                    "Unable to access the requested URL  :  " + url + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void navigateToPage(String url) {
        DriverScript.driver.navigate().to(url);
    }

    public void closeAllPagesOpenedByTheWebDriver() {
        DriverScript.driver.quit();
    }
}
