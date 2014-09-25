package com.staf.utilities.automation;

import com.staf.service.DriverScript;
import com.staf.utilities.errorreporting.ErrorReporter;
import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Utilities for Automation using Selenium Web Driver
 */
public class AutomationUtilities {

    /**
     * Logger
     */
    Logger log = Logger.getLogger(AutomationUtilities.class);

    /**
     * Current Window Handle
     */
    private String currentWindowHandle = "";

    /**
     * Previous Window Handle
     */
    public String previousWindowHandle = "";

    /**
     * List of window handles
     */
    public ArrayList<String> windowsOpened = new ArrayList<String>();

    /**
     * Error Reporter
     */
    ErrorReporter errorReporter = new ErrorReporter();

    /**
     * Updates the current and previous window handles
     */
    public void updateCurrentAndPreviousWindowHandle() {
        String tempWindowHandle = DriverScript.driver.getWindowHandle();

        if (!currentWindowHandle.trim().equalsIgnoreCase(tempWindowHandle)) {
            previousWindowHandle = currentWindowHandle;
            currentWindowHandle = tempWindowHandle;
        }
    }

    /**
     * Switches to the window that is recently opened
     *
     * @throws NoSuchWindowException  - NoSuchWindow Exception
     * @throws ClassNotFoundException - ClassNotFound Exception
     * @throws SQLException           - SQL Exception
     * @throws IOException            - IO Exception
     */
    public void switchTotheWindowOpenedNow()
            throws NoSuchWindowException, ClassNotFoundException, SQLException, IOException {
        ArrayList<String> windowHandles = new ArrayList<String>();
        ArrayList<String> windowsOpened_Copy = new ArrayList<String>();
        windowHandles.addAll(DriverScript.driver.getWindowHandles());

        for (String aWindowsOpened : windowsOpened) {
            windowsOpened_Copy.add(aWindowsOpened);
        }

        windowHandles.removeAll(windowsOpened_Copy);
        windowsOpened.add(windowHandles.get(0));
        switchToWindowHandle(windowHandles.get(0));
    }


    /**
     * Executes a script on an element
     *
     * @param script  The script to execute
     * @param element The target of the script, referenced as arguments[0]
     */
    public void trigger(String script, WebElement element) {
        ((JavascriptExecutor) DriverScript.driver).executeScript(script, element);
    }

    /**
     * Executes a script
     *
     * @param script The script to execute
     */
    public Object trigger(String script) {
        return ((JavascriptExecutor) DriverScript.driver).executeScript(script);
    }

    /**
     * Opens a new tab for the given URL
     *
     * @param url The URL to
     * @throws net.sourceforge.htmlunit.corejs.javascript.JavaScriptException If unable to open tab
     */
    public void openTab(String url) {
        String script = "var d=document,a=d.createElement('a');" +
                "a.target='_blank';" +
                "a.href='%s';" +
                "a.innerHTML='.';" +
                "d.body.appendChild(a);" +
                "return a";
        Object element = trigger(String.format(script, url));
        if (element instanceof WebElement) {
            WebElement anchor = (WebElement) element;
            anchor.click();
            trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
        } else {
            throw new JavaScriptException(element, "Unable to open tab", 1);
        }
    }

    /**
     * Switches to the non-current window
     */
    public void switchWindow() throws NoSuchWindowException {
        Set<String> handles = DriverScript.driver.getWindowHandles();
        String current = DriverScript.driver.getWindowHandle();
        handles.remove(current);
        if (handles.iterator().hasNext()) {
            String newTab = handles.iterator().next();
            WebDriver.TargetLocator locator = DriverScript.driver.switchTo();
            locator.window(newTab);
        }

        String tempWindowHandle = DriverScript.driver.getWindowHandle();

        if (!currentWindowHandle.trim().equalsIgnoreCase(tempWindowHandle)) {
            previousWindowHandle = currentWindowHandle;
            currentWindowHandle = tempWindowHandle;
        }
    }

    /**
     * Switches to the window handle provided
     *
     * @param windowHandle - Window handle
     * @throws NoSuchWindowException - NoSuchWindow Exception
     */
    public void switchToWindowHandle(String windowHandle) throws NoSuchWindowException {
        WebDriver.TargetLocator locator = DriverScript.driver.switchTo();
        locator.window(windowHandle);
    }

    /**
     * Switches to the Window title provided
     *
     * @param windowTitle - Window title
     * @throws NoSuchWindowException - NoSuchWindow Exception
     */
    public void switchToWindowTitle(String windowTitle) throws NoSuchWindowException {
        String currentWindowTitle;

        int noOfWindows;
        String tmpWndwHndl;
        ArrayList<String> windowHandles = new ArrayList<String>();
        windowHandles.addAll(DriverScript.driver.getWindowHandles());
        noOfWindows = windowHandles.size();
        windowTitle = windowTitle.toUpperCase().trim();
        log.info("Expected : " + windowTitle);

        for (int i = 0; i < noOfWindows; i++) {
            tmpWndwHndl = windowHandles.get(i);
            DriverScript.driver.switchTo().window(tmpWndwHndl);

            // CHECK THE WINDOW TITLE
            currentWindowTitle = DriverScript.driver.getTitle();
            currentWindowTitle = currentWindowTitle.toUpperCase().trim();

            if (currentWindowTitle.contains(windowTitle)) {
                // IF THE WINDOW TITLE MATCHED THE EXPECTED TITLE, EXIT IMMEDIATELY, FIXING THE DRIVER TO THIS WINDOW
                break;
            }
        }
    }

    /**
     * Fetches all the windows opened by the driver
     *
     * @throws NoSuchWindowException - NoSuchWindow Exception
     */
    public void getWindows() throws NoSuchWindowException {
        int noOfWindows;
        ArrayList<String> windowHandles = new ArrayList<String>();
        windowHandles.addAll(DriverScript.driver.getWindowHandles());
        noOfWindows = windowHandles.size();

        for (int i = 0; i < noOfWindows; i++) {
            log.info(windowHandles.get(i));
        }
    }

    /**
     * Checks if the web page contains the text provided
     *
     * @param text - Text to be searched
     * @return true if the text is present in the web page; else returns false
     */
    public boolean doesWebPageContains(String text) {
        boolean result;
        String bodyText = DriverScript.driver.findElement(By.tagName("body")).getText();
        result = bodyText.contains(text);

        if (result) {
            result = true;
            log.info("The Text Element is found !!!!");
            // Prints the starting position of the First Occurrence of the String, inside the Text.
            log.info(bodyText.indexOf(text));
            // Prints the starting position of the Last Occurrence of the String, inside the Text.
            log.info(bodyText.lastIndexOf(text));
        } else {
            result = false;
            log.info("Method Name - doesWebPageContains  : Expected Text - " + text + "- Not found!!!");
        }
        return result;
    }


    /**
     * Switches to the user requested window
     *
     * @param windowTitle      - Window Title
     * @param windowHandle     - Window Handle
     * @param frameTitle       - Frame Title
     * @param textInsideWindow - Text to be searched inside the window
     * @throws NoSuchWindowException  - NoSuchWindow Exception
     * @throws ClassNotFoundException - ClassNotFound Exception
     * @throws SQLException           - SQL Exception
     * @throws IOException            - IO Exception
     */
    public void switchtoUserRequestedWindow(String windowTitle, String windowHandle,
                                            String frameTitle, String textInsideWindow)
            throws NoSuchWindowException, ClassNotFoundException, SQLException, IOException {

        boolean temp;
        boolean frmSwitching;
        if ((windowTitle.toUpperCase().trim()).equals("NULL")) {
            windowTitle = "";
        }

        if ((windowHandle.toUpperCase().trim()).equals("NULL")) {
            windowHandle = "";
        }

        if ((frameTitle.toUpperCase().trim()).equals("NULL")) {
            frameTitle = "";
        }

        if ((textInsideWindow.toUpperCase().trim()).equals("NULL")) {
            textInsideWindow = "";
        }

        // IF ALL THE THREE VALUES ARE EMPTY
        if (windowTitle.equalsIgnoreCase("")) {
            if (windowHandle.equalsIgnoreCase("")) {
                if (textInsideWindow.equalsIgnoreCase("")) {
                    return;
                }
            }
        }

        // IF WINDOW HANDLE IS GIVEN
        if (!windowHandle.equals("")) {
            try {
                switchToWindowHandle(windowHandle);
            } catch (Exception e) {
                log.info("Please enter a valid Window Handle");
                DriverScript.explicitErrorMsg = "Please enter a valid Window Handle";
                errorReporter.errorReporting();
                return;
            }

            if (!frameTitle.equals("")) {
                try {
                    DriverScript.driver.switchTo().frame(frameTitle);
                } catch (Exception e) {
                    log.info("Frame given - does not exist inside the Window Handle given");
                    DriverScript.explicitErrorMsg = "Frame given - does not exist inside the Window Handle given";
                    errorReporter.errorReporting();
                    return;
                }
            }

            String tempTitle = DriverScript.driver.getTitle();

            if (!windowTitle.equals("")) {
                if ((tempTitle.toUpperCase()).contains(windowTitle.toUpperCase())) {
                    if (!textInsideWindow.equals("")) {
                        boolean txtFnd = findTextInsidePage(textInsideWindow);
                        if (txtFnd) {
                            log.info("Switching Successfull !!");
                        } else {
                            log.info("Text given is not found under the Window Handle or Title provided." +
                                    " Please enter a valid Title, Handle and Text within the window!!");
                            DriverScript.explicitErrorMsg = "Text given is not found under the Window Handle or Title provided." +
                                    "Please enter a valid Title, Handle and Text within the window";
                            errorReporter.errorReporting();
                        }
                    } else {
                        log.info("Switching Successfull !!");
                    }
                } else {
                    log.info("Title given is not found under the Window Handle provided." +
                            "Please enter a valid Handle or Title !!!");
                    DriverScript.explicitErrorMsg = "Title given is not found under the Window Handle provided." +
                            "Please enter a valid Handle or Title";
                    errorReporter.errorReporting();
                }
            } else {
                if (!textInsideWindow.equals("")) {
                    boolean txtFnd = findTextInsidePage(textInsideWindow);
                    if (txtFnd) {
                        log.info("Switching Successfull !!");
                    } else {
                        log.info("Text given is not found under the Window Handle provided." +
                                "Please enter a valid Handle or Text within the window!!");
                        DriverScript.explicitErrorMsg = "Text given is not found under the Window Handle provided." +
                                "Please enter a valid Handle or Text within the window";
                        errorReporter.errorReporting();
                    }
                } else {
                    log.info("Switching successful.");
                    log.info("Switched to the appropraiate window using the handle given");
                }
            }
        } else {  // IF WINDOW HANDLE IS NOT GIVEN
            if (!windowTitle.equals("")) {
                ArrayList<String> windowHandlesWithSameTitle;
                windowHandlesWithSameTitle = getWindowHandlesWithSameTitle(windowTitle);
                int i = windowHandlesWithSameTitle.size();

                if (i == 0) {  // IF NO WINDOW IS AVAILABLE WITH THE TITLE PROVIDED
                    log.info("No window is available with the Title provided !!");
                    DriverScript.explicitErrorMsg = "No window is available with the Title provided";
                    errorReporter.errorReporting();
                } else { // IF THERE ARE ANY WINDOWS WITH THE TITLE PROVIDED
                    // IF ONLY ONE WINDOW IS AVAILABLE WITH THE TITLE PROVIDED,
                    // THEN SWITCH TO TO THE WINDOW AAND CHECK FOR THE TEXT INSIDE
                    if ((i + "").equalsIgnoreCase("1")) {

                        switchToWindowHandle(windowHandlesWithSameTitle.get(0));

                        if (!frameTitle.equals("")) {
                            try {
                                DriverScript.driver.switchTo().frame(frameTitle);
                            } catch (Exception e) {
                                log.info("Frame given - does not exist inside the Window Title given");
                                DriverScript.explicitErrorMsg = "Frame given - does not exist inside the Window Title given";
                                errorReporter.errorReporting();
                                return;
                            }
                        }

                        if (!textInsideWindow.equals("")) {
                            temp = findTextInsidePage(textInsideWindow);
                            if (temp) {
                                log.info("Switching succcessful");
                            } else {
                                log.info("Please provide a valid Text to be found inside the Page.");
                                DriverScript.explicitErrorMsg = "Please provide a valid Text to be found inside the Page";
                                errorReporter.errorReporting();
                            }
                        } else {
                            log.info("Switching succcessful");
                        }
                        // IF THERE ARE MORE THAN ONE WINDOW, CHECK FOR THE TEXT INSIDE.
                        // IF NO TEXT VALUE IS PROVIDED, END UP WITH AN ERROR REPORTING, FAILING THE CASE
                    } else {
                        temp = false;

                        if (!frameTitle.equals("")) {
                            if (!textInsideWindow.equals("")) {
                                for (String aWindowHandlesWithSameTitle : windowHandlesWithSameTitle) {
                                    try {
                                        switchToWindowHandle(aWindowHandlesWithSameTitle);
                                    } catch (Exception e) {
                                        log.info("Window is closed while trying " +
                                                "to locate the appopriate window " +
                                                "(among the windows with same title " + windowTitle + ")");
                                        DriverScript.explicitErrorMsg = "Window is closed while trying " +
                                                "to locate the appopriate window " +
                                                "(among the windows with same title " + windowTitle + ")";
                                        errorReporter.errorReporting();
                                        return;
                                    }

                                    try {
                                        DriverScript.driver.switchTo().frame(frameTitle);
                                        frmSwitching = true;
                                    } catch (Exception e) {
                                        frmSwitching = false;
                                    }

                                    if (frmSwitching) {
                                        temp = findTextInsidePage(textInsideWindow);
                                        if (temp) {
                                            break;
                                        }
                                    }
                                }

                                if (temp) {
                                    log.info("Switching succcessful");
                                } else {
                                    log.info("Please provide a valid Text or FRAME title.");
                                    DriverScript.explicitErrorMsg = "Please provide a valid Text or FRAME title";
                                    errorReporter.errorReporting();
                                }
                            } else {
                                log.info("If a FrameTitle is given without text inside the page " +
                                        "- With more than one window available with the Title given-, " +
                                        "then it is not possible to switch to the right window.");
                                DriverScript.explicitErrorMsg = "If a FrameTitle is given without text inside the page" +
                                        " - With more than one window available with the Title given-, " +
                                        "then it is not possible to switch to the right window";
                                errorReporter.errorReporting();
                            }
                        } else {
                            if (!textInsideWindow.equals("")) {
                                for (String aWindowHandlesWithSameTitle : windowHandlesWithSameTitle) {
                                    try {
                                        switchToWindowHandle(aWindowHandlesWithSameTitle);
                                    } catch (Exception ignored) {
                                    }

                                    temp = findTextInsidePage(textInsideWindow);
                                    if (temp) {
                                        break;
                                    }
                                }

                                if (temp) {
                                    log.info("Switching succcessful");
                                } else {
                                    log.info("Please provide a valid Text to be found inside the Page.");
                                    DriverScript.explicitErrorMsg = "Please provide a valid Text to be found inside the Page";
                                    errorReporter.errorReporting();
                                }
                            } else {
                                log.info("More than one window found with same title provided. " +
                                        "Please provide either a Window handle or some text inside the window " +
                                        "in order to switch to the appopriate page.");
                                DriverScript.explicitErrorMsg = "More than one window found with same title provided. " +
                                        "Please provide either a Window handle or some text inside the window " +
                                        "in order to switch to the appopriate page";
                                errorReporter.errorReporting();
                            }
                        }
                    }
                }
                // IF WINDOW HANDLE AND WINDOW TITLE IS NULL, BUT TEXT INSIDE HAS A VALUE,
                // THEN RETURN FAILING THE CASE AND REPORT THE ERROR
            } else {
                log.info("Cannot find a page with just the Text given. " +
                        "Please enter a Window Title or Handle to identify a Window.");
                DriverScript.explicitErrorMsg = "Cannot find a page with just the Text given. " +
                        "Please enter a Window Title or Handle to identify a Window";
                errorReporter.errorReporting();
            }
        }
    }

    /**
     * Returns an Array List of window handles with same title
     *
     * @param windowTitle - Window Title
     * @return an Array List of window handles with same title
     * @throws NoSuchWindowException - NoSuchWindow Exception
     */
    public ArrayList<String> getWindowHandlesWithSameTitle(String windowTitle) throws NoSuchWindowException {
        int noOfWindows;
        windowTitle = windowTitle.toUpperCase();
        ArrayList<String> windowHandles = new ArrayList<String>();
        ArrayList<String> windowHandlesWithSameTitle = new ArrayList<String>();
        windowHandles.addAll(DriverScript.driver.getWindowHandles());
        noOfWindows = windowHandles.size();

        for (int i = 0; i < noOfWindows; i++) {
            switchToWindowHandle(windowHandles.get(i));
            String tempTitle = DriverScript.driver.getTitle();
            tempTitle = tempTitle.toUpperCase();
            log.info(tempTitle);

            if (tempTitle.contains(windowTitle)) {
                windowHandlesWithSameTitle.add(windowHandles.get(i));
            }
        }

        for (String aWindowHandlesWithSameTitle : windowHandlesWithSameTitle) {
            log.info(aWindowHandlesWithSameTitle);
        }

        return windowHandlesWithSameTitle;
    }

    /**
     * Check if a particular text is available within the current web page
     *
     * @param expctdText - Text to be searched
     * @return true if found; false if not found
     */
    public boolean findTextInsidePage(String expctdText) {

        Document doc = Jsoup.parse(DriverScript.driver.getPageSource());
        String pageContent = doc.text();
        boolean elmntFnd = pageContent.contains(expctdText);

        if (elmntFnd) {
            log.info("The Text Element is found !!!!");
            elmntFnd = true;
        } else {
            log.info("Expected Text - Not found!!!");
            elmntFnd = false;
        }

        return elmntFnd;
    }
}
