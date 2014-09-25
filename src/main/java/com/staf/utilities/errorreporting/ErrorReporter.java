package com.staf.utilities.errorreporting;

import com.staf.service.DriverScript;
import org.apache.log4j.Logger;

public class ErrorReporter {

    /**
     * Logger
     */
    Logger log = Logger.getLogger(ErrorReporter.class);

    public void errorReporting() {
        log.info("Test Case : " + DriverScript.testCaseName + "  Failed !!");

        if ((!DriverScript.explicitErrorMsg.equals(null)) && (!DriverScript.explicitErrorMsg.equals(""))) {
            if (DriverScript.explicitErrorMsg.length() > 0) {
                log.error(DriverScript.explicitErrorMsg);
                DriverScript.explicitErrorMsg = "";
            }
        }
        if ((!DriverScript.implicitErrorMsg.equals(null)) && (!DriverScript.implicitErrorMsg.equals(""))) {
            if (DriverScript.implicitErrorMsg.length() > 0) {
                log.error(DriverScript.implicitErrorMsg);
                DriverScript.implicitErrorMsg = "";
            }
        }
        //TODO: Place this in DriverScript to confirm if testcase failed and then proceed further. If so complete report event and throw this exception
        //throw new Exception("The test case failed. Please refer the logs (target/STAF.log) for more information.");
    }
}
