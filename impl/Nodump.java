package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
