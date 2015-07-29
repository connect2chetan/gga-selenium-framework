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
package com.epam.ui_test_framework.elements.complex;

import com.epam.ui_test_framework.elements.base.SetValue;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.interfaces.complex.IDropDown;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Dropdown<TEnum extends Enum> extends Selector<TEnum> implements IDropDown<TEnum> {
    public Dropdown() { super(); }
    public Dropdown(By selectLocator) { this.selectLocator = selectLocator; }
    public Dropdown(By selectLocator, By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); this.selectLocator = selectLocator;}
    public Dropdown(By selectLocator, By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator); this.selectLocator = selectLocator;
    }
    private By selectLocator;
    private Button field() { return new Button(selectLocator); }

    @Override
    protected void selectAction(String name) { field().click(); super.selectAction(name); }
    @Override
    protected void selectByIndexAction(int index) { field().click(); super.selectByIndexAction(index); }
    @Override
    protected SetValue setValue() { return  new SetValue(() -> field().getText(), super.setValue()); }
    @Override
    protected boolean waitSelectedAction(String value) { return field().waitText(value).equals(value); }

    @Override
    public boolean waitDisplayed() {  return field().waitDisplayed(); }
    @Override
    public boolean waitVanished()  { return field().waitVanished(); }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return field().wait(resultFunc);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return field().wait(resultFunc, condition);
    }
    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return field().wait(resultFunc, timeoutSec);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        return field().wait(resultFunc, condition, timeoutSec);
    }
    
    public final String getText() { return field().getText(); }
    public final String waitText(String text) { return field().waitText(text); }
    public final String waitMatchText(String regEx) { return field().waitText(regEx); }

    public void setAttribute(String attributeName, String value) {
        field().setAttribute(attributeName, value);
    }
    public WebElement getWebElement() { return field().getWebElement(); }

    public boolean waitAttribute(String name, String value) {
        return field().waitAttribute(name, value);
    }

}
