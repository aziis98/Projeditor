package com.aziis98.input;

// Copyright 2016 Antonio De Lucreziis

public class Time {

    public static long milliTime() {
        return System.nanoTime() / 1000000;
    }

}
