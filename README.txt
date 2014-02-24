BeforeUnload Vaadin UI Extension Add On

Vibrare offers server side access to BeforeUnload API

Demo application: http://siika.fi:8080/BeforeUnloadDemo/
Source code: https://github.com/alump/BeforeUnload
Vaadin Directory: https://vaadin.com/directory#addon/beforeunload
License: Apache License 2.0

This project can be imported to Eclipse with m2e.

Simple Maven tutorials:


***** How to compile add on jar package for your project *****

> cd beforeunload-addon
> mvn package

add on can be found at: picker-addon/target/BeforeUnload-<version>.jar
zip package used at Vaadin directory can be found at:
picker-addon/target/BeforeUnload-<version>.zip

***** How to install BeforeUnload to your Maven repository *****

To install addon to your local repository, run:

> cd beforeunload-addon
> mvn install


***** How to run test application *****

First compile and install addon (if not already installed)
> cd beforeunload-addon
> mvn install

Then compile demo widgetset and start HTTP server
> cd ../beforeunload-demo
> mvn vaadin:compile
> mvn jetty:run

Demo application is running at http://localhost:8080/beforeunload



***** How to compile test application WAR *****

First compile and install addon (if not already installed)
> cd beforeunload-addon
> mvn install

Then construct demo package (this should automatically compile widgetset)
> cd ../beforeunload-demo
> mvn package

War package can be now found at beforeunload-demo/target/BeforeUnloadDemo.war
