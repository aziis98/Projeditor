package com.aziis98.event;

// Copyright 2016 Antonio De Lucreziis

import java.lang.annotation.*;

/**
 * An Higher value means a greater priority
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {
    int value();
}
