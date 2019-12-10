package com.example.serverdemo.base.entity;

import java.io.Serializable;

public interface Entity extends Serializable, Cloneable{
     boolean equals(Object object);

     int hashCode();

     String toString();

     String toXml();

     String toJSON();

     Object clone();
}
