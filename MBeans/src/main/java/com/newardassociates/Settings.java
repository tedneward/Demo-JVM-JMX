package com.newardassociates;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Settings
        extends NotificationBroadcasterSupport
        implements SettingsMBean {
    private boolean switchValue = false;
    private String stringValue = "(empty)";

    /* This is the "sequence number" of the notification. */
    private long sequenceNumber = 0L;

    @Override
    public boolean getSwitch() {
        return switchValue;
    }

    @Override
    public void setSwitch(boolean newValue) {
        boolean oldValue = switchValue;
        switchValue = newValue;

        System.out.println("Switch set to " + switchValue);

        notify("switch", oldValue, newValue);
    }

    @Override
    public String getString() {
        return stringValue;
    }

    @Override
    public void setString(String newValue) {
        String oldValue = stringValue;
        stringValue = newValue;
        System.out.println("String set to " + newValue);
        notify("string", oldValue, newValue);
    }

    private void notify(String attrName, Object oldValue, Object newValue) {
        /* Construct a notification that describes the change.  The
           "source" of a notification is the ObjectName of the MBean
           that emitted it.  But an MBean can put a reference to
           itself ("this") in the source, and the MBean server will
           replace this with the ObjectName before sending the
           notification on to its clients.

           For good measure, we maintain a sequence number for each
           notification emitted by this MBean.

           The oldValue and newValue parameters to the constructor are
           of type Object, so we are relying on Java's autoboxing
           here.
        */
        Notification n =
                new AttributeChangeNotification(this,
                        sequenceNumber++,
                        System.currentTimeMillis(),
                        attrName + " changed",
                        attrName,
                        attrName.equals("switch") ? "java.lang.Boolean" : "java.lang.String",
                        oldValue,
                        newValue);

        /* Now send the notification using the sendNotification method
           inherited from the parent class
           NotificationBroadcasterSupport.  */
        sendNotification(n);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] {
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        return new MBeanNotificationInfo[] {
            new MBeanNotificationInfo(types, AttributeChangeNotification.class.getCanonicalName(), "The Switch attribute has changed")
        };
    }}
