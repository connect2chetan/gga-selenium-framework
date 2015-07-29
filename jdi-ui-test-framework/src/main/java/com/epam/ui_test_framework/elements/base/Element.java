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
package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.interfaces.common.IButton;
import com.epam.ui_test_framework.elements.interfaces.common.IText;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions;
import com.epam.ui_test_framework.logger.base.LogSettings;
import com.epam.ui_test_framework.utils.common.Timer;
import com.epam.ui_test_framework.settings.HighlightSettings;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.getFindByLocator;
import static com.epam.ui_test_framework.logger.enums.LogInfoTypes.BUSINESS;
import static com.epam.ui_test_framework.logger.enums.LogLevels.DEBUG;
import static com.epam.ui_test_framework.settings.FrameworkSettings.driverFactory;
import static com.epam.ui_test_framework.utils.common.LinqUtils.first;
import static com.epam.ui_test_framework.utils.common.LinqUtils.select;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFieldValue;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFields;
import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.settings.FrameworkSettings.timeouts;
import static java.lang.String.format;

/**
 * Base Element control implementation
 *
 * @author Alexeenko Yan
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Element extends BaseElement implements IElement {
    public Element() { super(); }
    public Element(By byLocator) { super(byLocator); }

    public WebElement getWebElement() {
        return doJActionResult("Get web element " + this.toString(), avatar::getElement, new LogSettings(DEBUG, BUSINESS));
    }

    public static <T extends Element> T copy(T element, By newLocator) {
        try {
            T result = (T) element.getClass().newInstance();
            result.setAvatar(newLocator, element.getAvatar());
            return result;
        } catch (Exception ex) { asserter.exception("Can't copy element: " + element); return null; }
    }

    protected Button getButton(String buttonName) {
        List<Field> fields = getFields(this, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), this);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, this));
        Button button = first(buttons, b -> b.getName().equals(getElementName(buttonName.toLowerCase() + "Button")));
        if (button == null) {
            asserter.exception(format("Can't find button '%s' for element '%s'", buttonName, toString()));
            return null;
        }
        return button;
    }

    protected Button getButton(Functions funcName) {
        List<Field> fields = getFields(this, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), this);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, this));
        Button button = first(buttons, b -> b.function.equals(funcName));
        if (button == null) {
            asserter.exception(format("Can't find button '%s' for element '%s'", funcName, toString()));
            return null;
        }
        return button;
    }

    protected Text getTextElement() {
        Field textField = first(getClass().getDeclaredFields(), f -> (f.getType() == Text.class) || (f.getType() == IText.class));
        if (textField!= null) return (Text) getFieldValue(textField, this);
        asserter.exception(format("Can't find Text element '%s'", toString()));
        return null;
    }

    public boolean waitAttribute(String name, String value) {
        return wait(el -> el.getAttribute(name).equals(value));
    }
    public void setAttribute(String attributeName, String value) {
        doJAction(format("Set Attribute '%s'='%s'", attributeName, value),
                () -> jsExecutor().executeScript(format("arguments[0].setAttribute('%s',arguments[1]);", attributeName),
                        getWebElement(), value));
    }
    public boolean waitDisplayed() {
        return wait(WebElement::isDisplayed);
    }

    public boolean waitVanished()  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = timer().wait(() -> {
                try { if (getWebElement().isDisplayed()) return false; }
                catch (Exception ignore) { }
                return false;
            });
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return wait(resultFunc, result -> result);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
    }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return wait(resultFunc, result -> result, timeoutSec);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public void highlight() { driverFactory.highlight(this); }
    public void highlight(HighlightSettings highlightSettings) {
        driverFactory.highlight(this, highlightSettings);
    }

    public void clickWithKeys(Keys... keys) {
        doJAction("Ctrl click on element",
                () -> {
                    Actions action = new Actions(getDriver());
                    for (Keys key : keys)
                        action = action.keyDown(key);
                    action = action.moveToElement(getWebElement()).click();
                    for (Keys key : keys)
                        action = action.keyUp(key);
                    action.perform();
                });
    }
    public void doubleClick() {
        doJAction("Couble click on element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.doubleClick();
        });
    }
    public void rightClick() {
        doJAction("Right click on element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.contextClick(getWebElement()).perform();
        });
    }
    public void clickCenter() {
        doJAction("Click in Center of element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.click(getWebElement()).perform();
        });
    }
    public void mouseOver() {
        doJAction("Move mouse over element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.moveToElement(getWebElement()).build().perform();
        });
    }
    public void focus() {
        doJAction("Focus on element", () -> {
            Dimension size = getWebElement().getSize(); //for scroll to object
            new Actions(getDriver()).moveToElement(getWebElement(), size.width / 2, size.height / 2).build().perform();
        });
    }

    public void selectArea(int x1, int y1, int x2, int y2) {
        doJAction(format("Select area: from %d,%d;to %d,%d", x1, y1, x2, y2), () -> {
            WebElement element = getWebElement();
            new Actions(getDriver()).moveToElement(element, x1, y1).clickAndHold()
                    .moveToElement(element, x2, y2).release().build().perform();
        });
    }
    public void dragAndDropBy(int x, int y) {
        doJAction(format("Drag and drop element: (x,y)=(%s,%s)", x, y), () ->
            new Actions(getDriver()).dragAndDropBy(getWebElement(), x, y).build().perform());
    }

}
