package com.newardassociates;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) {
        // Spin up a Counter, a Settings, and a Random
        final Counter c1 = new Counter();
        final Settings s1 = new Settings();
        final Random r1 = new Random();

        // Now register them with the MBeanServer that is in every JVM
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(c1, new ObjectName("com.newardassociates:type=counter"));
            server.registerMBean(r1, new ObjectName("com.newardassociates:type=random"));
            server.registerMBean(s1, new ObjectName("com.newardassociates:type=settings,name=simple"));
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                 MBeanRegistrationException | NotCompliantMBeanException e) {
            // handle exceptions
            System.err.println("Error while registering the MBean");
            System.err.println(e);
        }

        // Let's spin up a thread to adjust Counter and Random every so often
        Thread t = new Thread(() -> {
            java.util.Random timerRandom = new java.util.Random();
            while (true) {
                try {
                    Thread.sleep(timerRandom.nextLong(1,3) * 1000);

                    if (timerRandom.nextInt(0, 100) < 50)
                        c1.bumpCount();
                    else
                        r1.setNewValue();
                }
                catch (InterruptedException intEx) {
                    // This is kinda expected, so just exit the loop
                    break;
                }
            }
        });
        t.setDaemon(true);  // Don't keep the JVM alive with our simulated work
        t.start();

        // We're all set up, so go ahead and hang the program
        // until the user wants to quit
        System.out.println("Welcome to MBean!");
        System.out.println("Press ENTER to terminate the app:");
        try {
            int ignored = System.in.read();
        }
        catch (java.io.IOException ioEx) {
            System.err.println(ioEx);
        }
    }
}