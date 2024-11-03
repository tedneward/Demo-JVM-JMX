package com.newardassociates;

public class Counter implements CounterMBean {
    private int count = 0;

    @Override
    public int getCount() {
        return count;
    }

    public void bumpCount() {
        count += 1;
        System.out.println("Counter bumped to " + count);
    }
}
