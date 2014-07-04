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
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.utils.ReporterNGExt;
import org.openqa.selenium.WebElement;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckBox<ParentPanel> extends Element<ParentPanel> {

    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Checkbox name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current checkbox
     */
    public CheckBox(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    /**
     * @return True if the element is currently checked, false otherwise.
     */
    public boolean isChecked() {
        return (Boolean) ReporterNGExt.logGetter(this, getParentClassName(), "Checked", getWebElement().isSelected());
    }

    /**
     * Check if the element is not checked eat, do nothing otherwise.
     *
     * @return Parent Panel instance
     */
    public ParentPanel check() {
        ReporterNGExt.logAction(this, getParentClassName(), "Check");
        WebElement webEl = getWebElement();
        if (!webEl.isSelected()) {
            webEl.click();
        }
        return super.parent;
    }

    /**
     * Uncheck if the element is checked, do nothing otherwise.
     *
     * @return Parent Panel instance
     */
    public ParentPanel uncheck() {
        ReporterNGExt.logAction(this, getParentClassName(), "Uncheck");
        WebElement webEl = getWebElement();
        if (webEl.isSelected()) {
            webEl.click();
        }
        return super.parent;
    }

}
