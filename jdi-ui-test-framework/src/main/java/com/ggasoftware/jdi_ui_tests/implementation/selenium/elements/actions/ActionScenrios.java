package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions;


import com.ggasoftware.jdi_ui_tests.core.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.core.reporting.PerformanceStatistic;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;

import static com.ggasoftware.jdi_ui_tests.core.reporting.PerformanceStatistic.addStatistic;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.Timer.alwaysDoneAction;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/10/2015.
 */
public class ActionScenrios {
    private BaseElement element;
    public ActionScenrios setElement(BaseElement element) {
        this.element = element;
        return this;
    }

    public void actionScenario(String actionName , JAction jAction, LogSettings logSettings) {
        element.logAction(actionName, logSettings);
        Timer timer = new Timer();
        alwaysDoneAction(jAction::invoke);
        JDISettings.logger.info(actionName + " done");
        PerformanceStatistic.addStatistic(timer.timePassedInMSec());
    }

    public <TResult> TResult resultScenario(String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
        element.logAction(actionName);
        Timer timer = new Timer();
        TResult result = Timer.getResultAction(jAction::invoke);
        String stringResult = (logResult == null)
                ? result.toString()
                : JDISettings.asserter.silent(() -> logResult.invoke(result));
        Long timePassed = timer.timePassedInMSec();
        PerformanceStatistic.addStatistic(timer.timePassedInMSec());
        JDISettings.logger.toLog(format("Get result '%s' in %s seconds", stringResult,
                format("%.2f", (double) timePassed / 1000)), logSettings);
        return result;
    }
}
