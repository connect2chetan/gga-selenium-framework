/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.utils;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.Elements;
import org.apache.commons.lang.ClassUtils;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Utility for level log format.
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 * @author Zharov Alexandr
 * @author Zhukov Anatoliy
 */
public class ReporterNGExt extends ReporterNG{

    public static final char BUSINESS_LEVEL = '2';
    public static final char COMPONENT_LEVEL = '1';
    public static final char TECHNICAL_LEVEL = '0';

    private static final String SDFP = new SimpleDateFormat("HH:mm:ss.SSS").toPattern();

    /**
     * Takes screenshot and adds link to business part of test LOG. Must only be used on Test level
     * @param name the name of screenshot. User alphanumeric and _
     */
    public static void logBusinessScreenshot(String name) {
        ReporterNGExt.logBusiness(ScreenShotMaker.takeScreenshotRemote(name));
    }

    /**
     * Takes screenshot and adds link to component part of test LOG. Must only be used on Component level
     * @param name the name of screenshot. User alphanumeric and _
     */
    public static void logComponentScreenshot(String name) {
        ReporterNGExt.logComponent(ScreenShotMaker.takeScreenshotRemote(name));
    }

    /**
     * Get element level for log.
     * @param element - element to get level
     * @return log level (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     */
    //TODO Improve this method
    private static char getLogLevel(Object element){
        if (ClassUtils.getPackageName(element.getClass()).contains(".panel")) {
            return COMPONENT_LEVEL;
        } else if(ClassUtils.getAllSuperclasses(element.getClass()).contains(TestBaseWebDriver.class)){
            return BUSINESS_LEVEL;
        }
        return TECHNICAL_LEVEL;
    }

    /**
     * Log Match Asertion.
     *
     * @param value - analyzed text
     * @param regExp - regular expression
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertMatch(char logLevel, String value, String regExp, String message, boolean takePassedScreenshot) {
        String status = logAssertMatch(logLevel, value, regExp, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        }else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Not Intersect Asertion.
     *
     * @param firstArray - first text array 
     * @param secondArray - second text array
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotIntersect(char logLevel, String[] firstArray, String[] secondArray, String message, boolean takePassedScreenshot) {
        String status = logAssertNotIntersect(logLevel, firstArray, secondArray, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Equals Asertion.
     *
     * @param value - actual object
     * @param expectedValue - expected object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertEquals(char logLevel, Object value, Object expectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertEquals(logLevel, value, expectedValue, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Not Equals Asertion.
     *
     * @param value - actual object
     * @param notExpectedValue - not expected object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotEquals(char logLevel, Object value, Object notExpectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertNotEquals(logLevel, value, notExpectedValue, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Array List of String Equals Asertion.
     *
     * @param value - actual Array List of String
     * @param expectedValue - expected Array List of String
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertArrayListEquals(char logLevel, ArrayList<String> value, ArrayList expectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertArrayListEquals(logLevel, value, expectedValue, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Array Equals Asertion.
     *
     * @param value - actual text array
     * @param expectedValue - expected text array
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertEquals(char logLevel, String[] value, String[] expectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertEquals(logLevel, value, expectedValue, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Modify Text Array to String
     *
     * @param sa - Text Array
     * @return String of Text Array
     */
    public static String stringArrayToString(String[] sa) {
        StringBuilder sb = new StringBuilder("{");
        for (String aSa : sa) {
            sb.append(" ")
                    .append("\"")
                    .append(aSa)
                    .append("\"");
        }
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Log True Asertion.
     *
     * @param what - expression
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertTrue(char logLevel, boolean what, String message, boolean takePassedScreenshot) {
        String status = logAssertTrue(logLevel, what, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log False Asertion.
     *
     * @param what - expression
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertFalse(char logLevel, boolean what, String message, boolean takePassedScreenshot) {
        String status = logAssertFalse(logLevel, what, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Empty Asertion.
     *
     * @param what - analyzed text
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertEmpty(char logLevel, String what, String message, boolean takePassedScreenshot) {
        String status = logAssertEmpty(logLevel, what, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Text Contains Asertion.
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertContains(char logLevel, String toSearchIn, String whatToSearch, String message, boolean takePassedScreenshot) {
        String status = logAssertContains(logLevel, toSearchIn, whatToSearch, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Text Not Contains Asertion.
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotContains(char logLevel, String toSearchIn, String whatToSearch, String message, boolean takePassedScreenshot) {
        String status = logAssertNotContains(logLevel, toSearchIn, whatToSearch, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Null Object Asertion.
     *
     * @param what - analyzed object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNull(char logLevel, Object what, String message, boolean takePassedScreenshot) {
        String status = logAssertNull(logLevel, what, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(message + "_Passed")));
            }
        }
    }

    /**
     * Log Not Null Object Asertion.
     *
     * @param what - analyzed object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotNull(char logLevel, Object what, String message, boolean takePassedScreenshot) {
        String status = logAssertNotNull(logLevel, what, message);
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        } else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s_Passed", message))));
            }
        }
    }

    /**
     * Log getting parameter value from Element.
     *
     * @param element - element to get value
     * @param parentClassName - parent class name of element
     * @param parameterName - name of parameter to get value
     * @param value - object to get value
     * @return object to get value
     */
    public static Object logGetter(Object element, String parentClassName, String parameterName, Object value) {
        String className = getClassName(element, parentClassName);
        logGetter(getLogLevel(element), className, parameterName, value);
        return value;
    }

    private static String getClassName(Object element, String parentClassName) {
        String sClass = parentClassName;
        if (element == null) {
            return "";
        }
        try
        {
            Element elementN1 = (Element)element;
            if (elementN1.getName().length() > 0){
                sClass += "." + elementN1.getName();
            }
        }catch (Exception ignored){}
        if (sClass.length() <= 0)
        {
            try
            {
                Elements elements = (Elements)element;
                if (elements.getName().length() > 0){
                    sClass += "." + elements.getName();
                }
            } catch (Exception ignored) {}
        }
        sClass = (element.getClass().getSimpleName() + " ") + sClass;
        if (sClass.length() <=0){
            return element.getClass().getSimpleName();
        }
        return sClass;
    }

    /**
     * Log setting parameter value to Element.
     *
     * @param element - element to set value
     * @param parentClassName - parent class name of element
     * @param parameterName - name of parameter to set value
     * @param value - object to set value
     * @return object to set value
     */
    public static Object logSetter(Object element, String parentClassName, String parameterName, Object value) {
        String className = getClassName(element, parentClassName);
        logSetter(getLogLevel(element), className, parameterName, value);
        return value;
    }

    /**
     * Log action.
     *
     * @param element - element to perform action
     * @param parentClassName - parent class name of element
     * @param actionName - name of action
     */
    public static void logAction(Object element, String parentClassName, String actionName) {
        String className = getClassName(element, parentClassName);
        logAction(getLogLevel(element), className, actionName);
    }

    /**
     * Log and take screenshot at TECHNICAL_LEVEL.
     *
     * @param str - log message text
     */
    public static void logTechnicalScreenshot(String str) {
        boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
        ScreenShotMaker.hasTake(true);
        ReporterNGExt.logTechnical(ScreenShotMaker.takeScreenshotRemote(str));
        ScreenShotMaker.hasTake(hasOldValue);
    }

    /**
     * Log failed message and take screenshot.
     *
     * @param logLevel - log level (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param message - log message text
     */
    public static void logFailedScreenshot(char logLevel, String message) {
        logFailed(logLevel, message);
        boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
        ScreenShotMaker.hasTake(true);
        Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: Failed", message))));
        ScreenShotMaker.hasTake(hasOldValue);
    }
    

}
