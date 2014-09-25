package com.staf.utilities.performaction;

import com.staf.service.DriverScript;
import com.staf.utilities.automation.AutomationUtilities;
import com.staf.utilities.errorreporting.ErrorReporter;
import com.staf.utilities.pagehandling.PageHandler;
import com.staf.utilities.report.ReportEvent;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class to handle the contents of a web page
 */
public class WebContentHandler {

    /**
     * Error Reporting
     */
    private ErrorReporter m_errorReporter = new ErrorReporter();

    /**
     * Automation Utilities
     */
    private AutomationUtilities m_automationUtilities = new AutomationUtilities();

    /**
     * Object found - status
     */
    private boolean m_objFound = false;

    /**
     * Instance of ReportEvent
     */
    private ReportEvent m_reportEvent = new ReportEvent();

    /**
     * Error description
     */
    private String m_errorDesc;

    /**
     * Logger
     */
    private Logger m_logger = Logger.getLogger(WebPageHandler.class);

    public boolean findTextInsidePage(String textToSearch) {
        boolean txtFound = false;

        try {
            txtFound = m_automationUtilities.findTextInsidePage(textToSearch);
            if (txtFound) {
                m_objFound = true;
                m_reportEvent.reportEvent("Verify if the text " + textToSearch
                                + " is present in the web page - " + DriverScript.driver.getTitle(),
                        "Text to be verified should be visible in the page",
                        "Text is present in the Web Page",
                        "Pass");
            } else {
                m_reportEvent.reportEvent("Verify if the text " + textToSearch
                                + " is present in the web page - " + DriverScript.driver.getTitle(),
                        "Text to be verified should be visible in the page",
                        "Text is not present in the Web Page",
                        "Fail");
            }
        } catch (Exception e) {
            m_objFound = false;
            m_errorDesc = e.getMessage();
            e.printStackTrace();
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            m_errorReporter.errorReporting();
        }

        return txtFound;
    }

    public void validateAllLinksInPage() throws AWTException, ClassNotFoundException, SQLException, IOException {
        PageHandler pageHandler = new PageHandler();
        List<WebElement> links = pageHandler.findAllLinks(DriverScript.driver);
        Set<String> allLinks = new LinkedHashSet<String>(links.size());
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url.startsWith("http")) {
                allLinks.add(url);
            }
        }
        m_logger.info("Total number of Links found " + allLinks.size());
        boolean result = true;

        if (allLinks.isEmpty()) {
            m_reportEvent.reportEvent("Validate all the links in the Web Page",
                    "No links in the page should be broken",
                    "No links are present in the web page",
                    "Fail");
            result = false;
        }

        for (String link : allLinks) {
            try {
                String returnMessage = pageHandler.isLinkBroken(new URL(link));
                if (returnMessage.equalsIgnoreCase("OK")) {
                    m_reportEvent.reportEvent("Validate that the link is not broken",
                            "Link should not be broken",
                            "URL: " + link + " returned " + returnMessage,
                            "Pass");
                } else {
                    m_reportEvent.reportEvent("Validate that the link is not broken",
                            "Link should not be broken",
                            "URL: " + link + " returned " + returnMessage,
                            "Fail");
                    result = false;
                }
            } catch (Exception exp) {
                m_reportEvent.reportEvent("Validate that the link is not broken",
                        "Link should not be broken",
                        "At " + link + " Exception occurred: " + exp.getMessage(),
                        "Fail");
                m_errorDesc = exp.getMessage();
                exp.printStackTrace();
                result = false;
            }

            if (!result) {
                DriverScript.testCasestatus = "Fail";
                DriverScript.implicitErrorMsg = m_errorDesc;
                DriverScript.explicitErrorMsg = "";
                m_errorReporter.errorReporting();
            }
        }
    }
}
