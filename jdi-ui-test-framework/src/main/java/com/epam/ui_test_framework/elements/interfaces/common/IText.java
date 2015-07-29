package com.epam.ui_test_framework.elements.interfaces.common;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IText extends IHaveValue, IElement {
    /** Get element’s text */
    String getText();
    /** Wait while element’s text contains expected text. Returns element’s text */
    @JDIAction
    String waitText(String text);
    /** Wait while element’s text matches regEx. Returns element’s text */
    @JDIAction
    String waitMatchText(String regEx);
}
