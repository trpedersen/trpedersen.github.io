# Build Process

## Other resources

|                           |                                                                               |
| ------------------------- | ----------------------------------------------------------------------------- |
| Ant file overview         | [Project Ant Tasks](/display/MRS/Project+Ant+Tasks "Project Ant Tasks")       |
| Integration build         | winscp tmrd-build:22 /data2/deployed\_builds                                  |
| Managing the build server | [Managing MRS Hudson](/display/MRS/Managing+MRS+Hudson "Managing MRS Hudson") |

## Overview

This document is a high level overview of the [Project Ant
Tasks](/display/MRS/Project+Ant+Tasks "Project Ant Tasks") file  
—  
The build has been designed to make developers life easy.  
There are basically three phases run in the following order.

1.  Configuration
2.  Building
3.  Deployment

## Configuration

The build system works on a templated configuration mechanism.  
The folder /configuration/templateenvironment contains all the property
files required by the application.

1.  When the bootstrap target is invoked files are copied from this
    folder to a folder (named based on your computername) in the same
    folder
2.  This folder is looked up by other targets in the build; and
    configuration files are copied to various places throughout the
    tree.
3.  To specify configuration parameters for your installation, add them
    to the local.properties file
    1.  When copying the template files to your config directory, the
        files will have values from this environment.properties file
        pushed into it.

<!-- end list -->

1.  \#\* Properties are overridden by those in local.properties
      - This means when changing parameters, you *should* be changing
        the relevant files and generating again
2.  Your database will then be dropped and recreated

To configure your build;

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` plain
ant bootstrap
```

</div>

</div>

### Summary; bootstrap target

  - Deletes and recreates your configuration folder (from the templates
    provided in /configuration/templateenvironment)
  - Drops and recreates your database
  - Cleans the build folders to make sure old property files are no
    longer used

## Building

As the code has been separated into several eclipse projects, we need a
way to tie them all together into a single object.  
The actual artifacts generated depend on build parameters
(environment.properties), ie which ears/jars/etc to create

To clean the build (delete all the build products)

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` plain
ant clean
```

</div>

</div>

  - Note that this will drop your database; so you will have to create
    it again

To re/create your database

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` plain
ant populate-database
```

</div>

</div>

To build a fresh the application

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` plain
ant generate
```

</div>

</div>

### Summary

  - Use the generate target to build your source. Use
    clean/populate-database as required
      - Builds all the source
      - Copies specific configuration files into specific places

## Deployment

First you must ensure the following have been done as described above.

1.  run **ant bootstrap** in the
    directory  **C:\\svn\\mrs\\trunk\\actrego\\configuration**
2.  run **ant generate** in the directory
    **C:\\svn\\mrs\\trunk\\actrego**

If this is the first time you have run the application from ant then

1.  goto **C:\\svn\\mrs\\trunk\\actrego\\MRSDeployment** folder.
2.  type **ant setup-jboss**  
    This will setup libraries needed for jboss to run mrs.

Each time you will need to do the following.

1.  type **ant generate-web, generate-webstart or generate-all** to
    build the required jars and ear. generate-web will war the web pages
    available. If you enter generate-webstart will war the swing gui
    client.
2.  run **ant deploy** to deploy your generated ear to the jboss deploy
    folder
3.  run **ant start-jboss** to start the server (Note: if JBoss fails to
    start for any reason, you may need to kill the process with the task
    manager or process explorer. Running "ant stop-jboss" will not work)
4.  to stop the server do **ctrl-c** to get the console back then run
    **ant stop-jboss**. If you do not run stop-jboss then the server
    keeps running in the background.

Running

  - If you ran **generate-web** or **generate-all** then goto the
    address: http://localhost:8080/mrs/regoact to see the login page for
    the web page.
  - If you ran **generate-webstart** or **generate-all** then run the
    jnlp at the address: http://localhost:8080/mrsclient/mrs.jnlp \\\\-
    This will launch webstart and download the client war presenting you
    with the login screen.
  - obviously if you did **generate-all** then both of the above
    addresses should work.

All going well you should be able to log in and run the app.

### On Solaris

  - type **ant generate-web, generate-webstart or generate-all** to
    build the required jars and ear.
  - enter **ant package** to create a solaris package - Note only throws
    everthing in a package folder at the moment needs to finished after
    dave has done packaging
