# BeforeUnload Vaadin UI Extension Add On

[![Build Status](https://epic.siika.fi/jenkins/job/BeforeUnload%20(Vaadin)/badge/icon)](https://epic.siika.fi/jenkins/job/BeforeUnload%20(Vaadin)/)

BeforeUnload offers server side access to BeforeUnload API

Demo application: http://app.siika.fi/BeforeUnloadDemo/

Source code: https://github.com/alump/BeforeUnload

Vaadin Directory: https://vaadin.com/directory#addon/beforeunload

License: Apache License 2.0

### Changelog
#### 0.3.1 - 2017-07-12
* disablePermanently API for client side by [Benedek Herold](https://github.com/Hedath)

#### 0.3.0 - 2017-03-13
* API simplified as modern browsers do not anymore show message given by API
* Vaadin 8 support
* Server side can now also access the client side temporary disable feature

#### 0.2.0 - 2014-02-26
* Adds client side API for disabling exit verification. Can be eg. used when
  force reloading page after connection error.
  https://github.com/alump/BeforeUnload/wiki/How-to-disable-verification-dialog-when-connection-error

#### 0.1.0 - 2014-02-25
* Initial release

### Simple Maven tutorials:

### How to compile add on jar package for your project

> cd beforeunload-addon
> mvn package

add on can be found at: picker-addon/target/BeforeUnload-<version>.jar
zip package used at Vaadin directory can be found at:
picker-addon/target/BeforeUnload-<version>.zip

### How to install BeforeUnload to your Maven repository

To install addon to your local repository, run:

> cd beforeunload-addon
> mvn install

### How to run test application

First compile and install addon (if not already installed)
> cd beforeunload-addon
> mvn install

Then compile demo widgetset and start HTTP server
> cd ../beforeunload-demo
> mvn vaadin:compile
> mvn jetty:run

Demo application is running at http://localhost:8080/beforeunload

### How to compile test application WAR

First compile and install addon (if not already installed)
> cd beforeunload-addon
> mvn install

Then construct demo package (this should automatically compile widgetset)
> cd ../beforeunload-demo
> mvn package

War package can be now found at beforeunload-demo/target/BeforeUnloadDemo.war
