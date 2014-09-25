package com.staf.utilities.performaction;

import com.staf.service.DriverScript;
import com.staf.utilities.automation.AutomationUtilities;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Utility class to handle window operations
 */
public class WindowHandler {

    /**
     * Automation Utilities
     */
    private AutomationUtilities m_automationUtilities = new AutomationUtilities();

    public String captureCurrentWindowTitle() {
        return DriverScript.driver.getTitle();
    }

    public String captureCurrentWindowHandle() {
        return DriverScript.driver.getWindowHandle();
    }

    public void refreshCurrentWebPage() {
        DriverScript.driver.navigate().refresh();
    }

    public void clickBackButtonOnCurrentPage() {
        DriverScript.driver.navigate().back();
    }

    public void clickForwardButtonOnCurrentPage() {
        DriverScript.driver.navigate().forward();
    }

    public void switchToFrame(String frame) {
        DriverScript.driver.switchTo().frame(frame);
    }

    public void switchToWindowHandle(String windowHandle) {
        m_automationUtilities.switchToWindowHandle(windowHandle);
    }

    public void switchToWindowTitle(String windowTitle) {
        m_automationUtilities.switchToWindowTitle(windowTitle);
    }

    public void switchToNextWindow() {
        m_automationUtilities.switchWindow();
    }

    public void switchToRequestedWindow(String windowTitle, String windowHandle,
                                        String frameTitle, String textInThePage)
            throws SQLException, IOException, ClassNotFoundException {
        m_automationUtilities.switchtoUserRequestedWindow(windowTitle, windowHandle, frameTitle, textInThePage);
    }

    public void switchToPreviousWindow() {
        if (!m_automationUtilities.previousWindowHandle.isEmpty()) {
            m_automationUtilities.switchToWindowHandle(m_automationUtilities.previousWindowHandle);
        }
    }
}
