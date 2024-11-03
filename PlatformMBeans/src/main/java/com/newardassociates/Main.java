package com.newardassociates;

import javax.management.MBeanServer;
import javax.swing.*;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class Main {
    public static void readKey() {
        System.out.println("Press ENTER to continue the app:");
        try {
            int ignored = System.in.read();
        }
        catch (java.io.IOException ioEx) {
            System.err.println(ioEx);
        }
    }

    public static void main(String[] args) {
        // Get hold of the MBeans we want
        ClassLoadingMXBean clbean = ManagementFactory.getClassLoadingMXBean();
        MemoryMXBean mBean = ManagementFactory.getMemoryMXBean();

        // Now let's ask them to be verbose
        // (For both, the JVM prints messages to the command-line)
        if (clbean.isVerbose() == false)
            clbean.setVerbose(true);
        if (mBean.isVerbose() == false)
            mBean.setVerbose(true);

        // Let's also get some memory statistics
        System.out.println("Memory usage: " + mBean.getHeapMemoryUsage());

        System.out.println("Welcome to PlatformMBeans!");
        readKey();

        // This should trigger a whole lotta classloading
        new JWindow();
        System.out.println("Memory usage: " + mBean.getHeapMemoryUsage());

        // Now try to force a GC
        readKey();
        System.out.println("Memory usage: " + mBean.getHeapMemoryUsage());
        mBean.gc();
        System.out.println("Memory usage: " + mBean.getHeapMemoryUsage());
    }
}