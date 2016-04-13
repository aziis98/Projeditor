package com.aziis98.parsing;

// Copyright 2016 Antonio De Lucreziis

import java.util.*;

public class Parser {

    private String source;
    private LinkedList<Integer> carretStack = new LinkedList<>();

    public Parser(String source) {
        this.source = source;
    }

    public void start() {
        carretStack.push( 0 );
    }

    public char getNextChar() {
        return source.charAt( getCarret() );
    }

    public String getNext(int length) {
        return source.substring( getCarret(), getCarret() + length );
    }


    public int getCarret() {
        return carretStack.peek();
    }

    public void setCarret(int carret) {
        carretStack.pop();
        carretStack.push( carret );
    }

    public void moveCarret(int amount) {
        carretStack.push( carretStack.pop() + amount );
    }

    public Parser pushCarretStack() {
        carretStack.push( carretStack.peek() );
        return this;
    }

    public int popCarretStack() {
        return carretStack.pop();
    }

}
