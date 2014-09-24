package com.staf.utilities.errorreporting;

import com.staf.service.DriverScript;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class ErrorReporter {

    /**
     * Logger
     */
    Logger log = Logger.getLogger(ErrorReporter.class);

    public void errorReporting() throws ClassNotFoundException, SQLException, IOException {
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
    }
}
