package com.powerdata.openpa.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Inform debug utilities that the method should be ignored for debug reporting
 * @author chris@powerdata.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Nodump
{
}
