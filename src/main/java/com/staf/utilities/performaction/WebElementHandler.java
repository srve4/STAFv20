package com.staf.utilities.performaction;

import com.staf.service.DriverScript;
import com.staf.utilities.errorreporting.ErrorReporter;
import com.staf.utilities.report.ReportEvent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Utility class to perform action on Web elements
 */
public class WebElementHandler {

    /**
     * Error Reporting
     */
    private ErrorReporter m_errorReporter = new ErrorReporter();

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

    public void moveFocusToElement(WebElement webElement)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                new Actions(DriverScript.driver).moveToElement(webElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = e.getMessage();
            DriverScript.explicitErrorMsg = "Unable to move the focus to the element (" + webElement + ") requested";
            m_errorReporter.errorReporting();
        }
    }

    public void clickLink(WebElement webElement)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                webElement.click();
                m_objFound = true;
            }
        } catch (NoSuchElementException e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Click on the appopriate Link",
                    "Link should be clicked successfully",
                    "Link - " + webElement + " - is clicked successfully",
                    "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Link provided" + "  -  " + webElement;
            m_reportEvent.reportEvent("Click on the appopriate Link",
                    "Link should be clicked successfully",
                    "Link - " + webElement + " - is not clicked"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void setText(WebElement webElement, String text) throws Exception {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                m_objFound = true;
                webElement.clear();
                webElement.sendKeys(text);
            }
        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Enter a valid value in to the Text Field",
                    "Appropriate Value should be entered successfully",
                    webElement + " is entered in to the Text Field",
                    "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Text field provided"
                    + "  -  " + webElement;

            m_reportEvent.reportEvent("Enter a valid value in to the Text Field",
                    "Appropriate Value should be entered successfully",
                    "Text Field is neither enabled nor displayed"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void selectRadioButton(WebElement webElement)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                webElement.click();
                m_objFound = true;
            }
        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Click on the Radio Button"
                    , "Radio Button should be clicked successfully"
                    , "Button clicked successfully"
                    , "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Radio button provided"
                    + "  -  " + webElement;

            m_reportEvent.reportEvent("Click on the Radio Button",
                    "Radio Button should be clicked successfully",
                    "Radio Button not clicked."
                            + "Reason: Radio Button not displayed / enabled"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void selectCheckBox(WebElement webElement)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                if (webElement.getAttribute("checked") == null) {
                    webElement.click();
                }
                m_objFound = true;
            }
        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Select the Check Box"
                    , "Appropriate Check Box should be selected successfully"
                    , "Check Box is selected"
                    , "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Check Box entered " + "  -  " + webElement;

            m_reportEvent.reportEvent("Select the Check Box",
                    "Appropriate Check Box should be selected successfully",
                    "Check Box is neither enabled nor displayed"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
        }
        m_errorReporter.errorReporting();
    }

    public void unSelectCheckBox(WebElement webElement)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                if (webElement.getAttribute("checked") != null) {
                    webElement.click();
                }
                m_objFound = true;
            }
        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Select the Check Box",
                    "Appropriate Check Box should be selected successfully",
                    "Check Box is selected",
                    "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Check Box entered " + "  -  " + webElement;

            m_reportEvent.reportEvent("Select the Check Box",
                    "Appropriate Check Box should be selected successfully",
                    "Check Box is neither enabled nor displayed"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void selectListByValue(WebElement webElement, String value)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {

            assert webElement != null;
            if (webElement.isEnabled()) {
                Select slctwebElement = new Select(webElement);
                slctwebElement.selectByValue(value);
                webElement.sendKeys(Keys.ENTER);
                m_objFound = true;
            }
        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Select a value from the Drop Down",
                    "Appropriate Value should be selected successfully",
                    value + " is selected from the Drop Down",
                    "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Drop Down entered " + "  -  " + webElement;
            m_reportEvent.reportEvent("Select a value from the Drop Down",
                    "Appropriate Value should be selected successfully",
                    "Drop Down is neither enabled nor displayed"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void selectListByIndex(WebElement webElement, int index)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {

            assert webElement != null;
            if (webElement.isEnabled()) {
                Select slctwebElement = new Select(webElement);
                slctwebElement.selectByIndex(index);
                webElement.sendKeys(Keys.ENTER);
                m_objFound = true;
            }

        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Select a value from the Drop Down - " + webElement,
                    "Appropriate Value should be selected successfully",
                    "Value at index " + index + " selected from the Drop Down",
                    "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Drop Down entered " + "  -  " + webElement;
            m_reportEvent.reportEvent("Select a value from the Drop Down",
                    "Appropriate Value should be selected successfully",
                    "Drop Down is neither enabled nor displayed"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public void clickButton(WebElement webElement)
            throws AWTException, ClassNotFoundException, SQLException, IOException {
        try {
            assert webElement != null;
            if (webElement.isEnabled()) {
                webElement.click();
                m_objFound = true;
            }
        } catch (Exception e) {
            m_errorDesc = e.getMessage();
            e.printStackTrace();
            m_objFound = false;
        }

        if (m_objFound) {
            DriverScript.testCasestatus = "Pass";
            m_reportEvent.reportEvent("Click on the Button",
                    "Button should be clicked successfully",
                    "Button clicked successfully",
                    "Pass");
        } else {
            DriverScript.testCasestatus = "Fail";
            DriverScript.implicitErrorMsg = m_errorDesc;
            DriverScript.explicitErrorMsg = "Unable to access the Web Button entered " + "  -  " + webElement;
            m_reportEvent.reportEvent("Click on the Button",
                    "Button should be clicked successfully",
                    "Button not clicked." + "Reason: Button not displayed / enabled"
                            + " - Please open the log " +
                            "to know more about the cause for Test Case failure",
                    "Fail");
            m_errorReporter.errorReporting();
        }
    }

    public String captureAttributeFromWebElement(WebElement webElement, String attributeToCapture) {
        return webElement.getAttribute(attributeToCapture);
    }

    public String getText(WebElement webElement) {
        return webElement.getText();
    }
}
