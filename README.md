# Demo-JVM-JMX
This repository contains some demos around JMX (Java Management Extensions): how to define managed beans, and how to monitor them both internally and externally.

Each of these immediate subdirectories is its own Gradle project, though some/most can work together.

## MBeans
This is a sample project demonstrating the definition (and registration) of some custom simple MBeans:

* **CounterMBean.** A simple read-only "counter" MBean that increments itself every 1-3 seconds or so.
* **RandomMBean.** A simple read-only "value" MBean that sets itself to a random value every 1-3 seconds or so.
* **SettingsMBean.** A MBean that has "settings" that can be viewed or changed. This bean also makes use of "notifications", so that it notifies any bound listeners when a setting is changed.

To see this in action, run the project, then fire up JConsole or VisualVM and find the "com.newardassociates.Main" process in the list of local processes to connect to. You will likely have to click "Insecure connection" in the error dialog that comes up--it's not a risk here, but JConsole or VisualVM wants to make sure you're OK with people eavesdropping on our demo.

> NOTE: JConsole ships with the SDK, even as of OpenJDK 23. VisualVM is a standalone download, and is also available as an IDE plugin for a variety of IDEs, including VSCode.

Once connected, you should be able to see our "Counter", "Random" and "Settings" objects under the "MBeans" button.

> NOTE: For VisualVM, according to https://visualvm.github.io/gettingstarted.html you will need to install a plugin ("VisualVM-MBeans") to get the MBeans tab in the VisualVM display that will allow VisualVM to display the MBeans exposed in this project to you. (They're still there, but VisualVM by default is a profiler, and so doesn't see the need to offer the additional observability option.)

If you want to run this application such that it can be monitored remotely, you will need to run it with the following properties (typically provided on the command line):

```
com.sun.management.jmxremote
com.sun.management.jmxremote.port=1617
com.sun.management.jmxremote.authenticate=false
com.sun.management.jmxremote.ssl=false
```

Note that the first property does not have a value; simply being set (existing) tells the JMXRemote functionality to start listening for remote connections.

(There's probably some more research to be done around notifications.)

## PlatformMBeans
This is a sample project that leverages the PlatformMBeans to display some information about the local VM. It obtains a reference to the ClassLoader and Memory beans, then sets the verbosity flag on each to be true and does some additional work just to demonstrate the verbosity flag being on.



