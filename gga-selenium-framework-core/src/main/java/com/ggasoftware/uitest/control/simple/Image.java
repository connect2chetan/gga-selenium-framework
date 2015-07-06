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
package com.ggasoftware.uitest.control.simple;

import com.ggasoftware.uitest.control.base.Clickable;
import org.openqa.selenium.By;

/**
 * Image control implementation
 *
 * @author Alexeenko Yan
 */
public class Image extends Clickable {
    public Image() { }
    public Image(By byLocator) { super(byLocator); }
    public Image(String name, By byLocator) { super(name, byLocator); }

}