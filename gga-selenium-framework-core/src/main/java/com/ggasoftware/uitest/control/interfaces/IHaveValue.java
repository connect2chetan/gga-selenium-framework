package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IHaveValue {
    @JDIAction
    String getValue() throws Exception;
}