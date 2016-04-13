package com.aziis98.math;

// Copyright 2016 Antonio De Lucreziis

public class Vector2Java {

    private double x, y;

    public Vector2Java(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Java() {
        this( 0, 0 );
    }


    public double getX() {
        return x;
    }

    public double component1() {
        return getX();
    }

    public double component2() {
        return getY();
    }

    public double getY() {
        return y;
    }


    public Vector2Java plus(Vector2Java other) {
        return new Vector2Java( x + other.x, y + other.y );
    }

    public Vector2Java minus(Vector2Java other) {
        return new Vector2Java( x - other.x, y - other.y );
    }

    public Vector2Java times(double scalar) {
        return new Vector2Java( x * scalar, y * scalar );
    }

    public double times(Vector2Java other) {
        return x * other.x + y * other.y;
    }

    public double lengthSq() {
        return this.times( this );
    }

    public double length() {
        return Math.sqrt( lengthSq() );
    }

    @Override
    public String toString() {
        return "( " + x + ", " + y + " )";
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Vector2Java vector2 = (Vector2Java) o;

        return Double.compare( vector2.x, x ) == 0 && Double.compare( vector2.y, y ) == 0;
    }

    @Override
    public int hashCode() {
        int  result;
        long temp;
        temp = Double.doubleToLongBits( x );
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits( y );
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
