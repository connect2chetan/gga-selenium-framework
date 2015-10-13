package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.apiInteract;

import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.core.utils.pairs.Pair;
import com.ggasoftware.jdi_ui_tests.core.utils.pairs.Pairs;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.where;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class GetElementModule {
    public By byLocator;
    public boolean haveLocator() { return byLocator != null; }
    private String driverName = "";
    private IBaseElement element;

    public Pairs<ContextType, By> context = new Pairs<>();
    public String printContext() { return context.toString(); }

    public GetElementModule(IBaseElement element) {
        this.element = element;
        driverName = JDISettings.driverFactory.currentDriverName;
    }
    public GetElementModule(By byLocator, IBaseElement element) {
        this(element);
        this.byLocator = byLocator;
    }
    public GetElementModule(By byLocator, Pairs<ContextType, By> context, IBaseElement element) {
        this(element);
        this.byLocator = byLocator;
        this.context = context;
    }

    public WebDriver getDriver() { return JDISettings.driverFactory.getDriver(driverName); }

    public WebElement getElement() {
        JDISettings.logger.debug("Get Web Element: " + element);
        WebElement element = Timer.getByCondition(this::getElementAction, el -> el != null);
        JDISettings.logger.debug("One Element found");
        return element;
    }

    public List<WebElement> getElements() {
        JDISettings.logger.debug("Get Web elements: " + element);
        List<WebElement> elements = getElementsAction();
        JDISettings.logger.debug("Found %s elements", elements.size());
        return elements;
    }

    public Timer timer() { return new Timer(JDISettings.timeouts.currentTimeoutSec * 1000); }
    private List<WebElement> getElementsAction() {
        List<WebElement> result = timer().getResultByCondition(
                this::searchElements,
                els -> where(els, getSearchCriteria()::invoke).size() > 0);
        JDISettings.timeouts.dropTimeouts();
        if (result == null)
            throw JDISettings.exception("Can't get Web Elements");
        return LinqUtils.where(result, getSearchCriteria()::invoke);
    }
    public JFuncTT<WebElement, Boolean> localElementSearchCriteria = null;
    private JFuncTT<WebElement, Boolean> getSearchCriteria() {
        return localElementSearchCriteria != null ? localElementSearchCriteria : JDISettings.driverFactory.elementSearchCriteria;
    }
    public GetElementModule searchAll() { localElementSearchCriteria = el -> el!=null; return this; }

    private WebElement getElementAction() {
        int timeout = JDISettings.timeouts.currentTimeoutSec;
        List<WebElement> result = getElementsAction();
        if (result == null)
            throw JDISettings.exception(failedToFindElementMessage, element, timeout);
        if (result.size() > 1)
            throw JDISettings.exception(findToMuchElementsMessage, result.size(), element, timeout);
        return result.get(0);
    }

    private static final String failedToFindElementMessage = "Can't find Element '%s' during %s seconds";
    private static final String findToMuchElementsMessage = "Find %s elements instead of one for Element '%s' during %s seconds";

    private List<WebElement> searchElements() {
        if (context == null || context.size() == 0)
            return getDriver().findElements(byLocator);
        return getSearchContext(correctXPaths(context)).findElements(correctXPaths(byLocator));
    }
    private SearchContext getSearchContext(Pairs<ContextType, By> context) {
        SearchContext searchContext = getDriver().switchTo().defaultContent();
        for (Pair<ContextType, By> locator : context) {
            WebElement element = searchContext.findElement(locator.value);
            if (locator.key == ContextType.Locator)
                searchContext = element;
            else {
                getDriver().switchTo().frame(element);
                searchContext = getDriver();
            }
        }
        return searchContext;
    }

    private Pairs<ContextType, By> correctXPaths(Pairs<ContextType, By> context) {
        if (context.size() == 1) return context;
        for (Pair<ContextType, By> pair : context.subList(1)) {
            By byValue = pair.value;
            if (byValue.toString().contains("By.xpath: //"))
                pair.value = WebDriverByUtils.getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
                        .replaceFirst("/", "./"));
        }
        return context;
    }
    private By correctXPaths(By byValue) {
        return (byValue.toString().contains("By.xpath: //"))
                ? WebDriverByUtils.getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
                .replaceFirst("/", "./"))
                : byValue;
    }

    public void clearCookies() { getDriver().manage().deleteAllCookies(); }

    @Override
    public String toString() {
        return JDISettings.shortLogMessagesFormat
            ? printFullLocator()
            : format("Locator: '%s'", byLocator) +
                ((context.size() > 0)
                        ? format(", Context: '%s'", context)
                        : "");
    }

    private String printFullLocator() {
        if (byLocator == null)
             return "No Locators";
        List<String> result = new ArrayList<>();
        if (context.size() != 0)
            result = LinqUtils.select(context, el -> printShortBy(el.value));
        result.add(printShortBy(byLocator));
        return PrintUtils.print(result);
    }

    private String printShortBy(By by) {
        return String.format("%s='%s'", WebDriverByUtils.getByName(by), WebDriverByUtils.getByLocator(by));
    }
}
