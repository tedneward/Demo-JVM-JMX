package com.newardassociates;

public class Random implements RandomMBean {
    private int current = 0;

    public void setNewValue() {
        current = new java.util.Random().nextInt(0,100);
        System.out.println("Random new value is now " + current);
    }

    @Override
    public int getCurrent() {
        return current;
    }
}
