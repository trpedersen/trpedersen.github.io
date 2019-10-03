<div id="page">

<div id="main" class="aui-page-panel">

<div id="main-header">

<div id="breadcrumb-section">

1.  <span>[MRS](index.html)</span>
2.  <span>[Build Framework](Build-Framework_327807.html)</span>

</div>

# <span id="title-text">MRS : Project Ant Tasks</span>

</div>

<div id="content" class="view">

<div class="page-metadata">Created by <span class="author">Tim Pedersen</span>, last modified on Oct 03, 2019</div>

<div id="main-content" class="wiki-content group">

## <a name="ProjectAntTasks-MRSEnvironmentConfiguration"></a>MRS Environment Configuration

The MRS environment system is designed to support different target environment settings but building on the same machine.

It is important to realise that this refers to the TARGET environment rather than the current build environment. In theory the build environment should be consistent across different build boxes - but some files e.g. ant, sqlplus etc may need to be added to the PATH before the build works. These will be documented as part of the [Guide to getting the dev environment running](/display/MRS/Guide+to+getting+the+dev+environment+running "Guide to getting the dev environment running").

It consists of three components:

*   An environment.properties file residing in the configuration directory. This points to which environment is currently in operation and any development specific settings e.g. whether to run the application in local or remote mode.
*   A default.environment.properties residing in the configuration directory. This contains default settings that can be overridden by the local.properties file in the environment directory.
*   A directory for each environment containing the deployment files and a local.properties file. By defaul this will ONLY contain the local.properties file - however you can override any standard configuration file by copying it from the templateenvironment folder into here.

A macro called: read-environment-settings reads in properties from the local.properties file and the default.environment.properties file and sets a property: current-environment.dir pointing to the current environment directory is use.

The environment properties file is maintained by

*   a macro: save-or-update-environment.properties - which allows you to create a new environment.properties file and override settings in an existing environment.properties file.
*   tasks within the configuration project - bootstrap (creates a new environment), switch-environment (changes to a different environment, creating it if required) and clean (remove the current environment directory).

If you do not specify the <environment> property on the ant command line then the value is taken from environment.properties - and if that doesn't exist - then it uses the machine name. Of course if the environment.properties file does not exist then read-environment-settings will blow up.

## <a name="ProjectAntTasks-ProjectAntTasks"></a>Project Ant Tasks

The following build files are used to build the new MRS:

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Build File

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

build.xml

</td>

<td class="confluenceTd">

Master Build file - delegates to ant/build.xml

</td>

</tr>

<tr>

<td class="confluenceTd">

ant\build.xml

</td>

<td class="confluenceTd">

Main build file - implements methods on main build.xml

</td>

</tr>

<tr>

<td class="confluenceTd">

ant\external-ant-tasks.xml

</td>

<td class="confluenceTd">

Ant tasks within the separate project files

</td>

</tr>

<tr>

<td class="confluenceTd">

ant\imports.xml

</td>

<td class="confluenceTd">

Includes common ant files - included in all ant build scripts

</td>

</tr>

<tr>

<td class="confluenceTd">

ant\utils.xml

</td>

<td class="confluenceTd">

Non project specific utilities

</td>

</tr>

<tr>

<td class="confluenceTd">

ant\paths.xml

</td>

<td class="confluenceTd">

Project paths - to avoid hardcoding directory structure

</td>

</tr>

<tr>

<td class="confluenceTd">

ant\macros.xml

</td>

<td class="confluenceTd">

Any cross project macros

</td>

</tr>

<tr>

<td class="confluenceTd">

configuration\build.xml

</td>

<td class="confluenceTd">

Configuration tasks

</td>

</tr>

<tr>

<td class="confluenceTd">

database\build.xml

</td>

<td class="confluenceTd">

Database tasks

</td>

</tr>

<tr>

<td class="confluenceTd">

MRSEJB\build.xml

</td>

<td class="confluenceTd">

Build the MRS EJB project

</td>

</tr>

<tr>

<td class="confluenceTd">

MRSWeb\build.xml

</td>

<td class="confluenceTd">

Build the MRS Web project

</td>

</tr>

<tr>

<td class="confluenceTd">

MRSDeployment\build.xml

</td>

<td class="confluenceTd">

Build the deployment artifacts, deploy the application and test the deployment

</td>

</tr>

<tr>

<td class="confluenceTd">

reports\build.xml

</td>

<td class="confluenceTd">

Generates findbugs and checkstyle reports

</td>

</tr>

<tr>

<td class="confluenceTd">

tools\LDAPClient\build.xml

</td>

<td class="confluenceTd">

Sets up user account on ldap server

</td>

</tr>

</tbody>

</table>

As a rule with the tasks - less is more. Or at least with publicly visible tasks - less is more. Create other tasks and prefix them with . so they cannot be called from the command line. The tasks should all be in the form VERB-NOUN. (Noun optional)

## <a name="ProjectAntTasks-build.xml"></a>build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

bootstrap

</td>

<td class="confluenceTd">

Set up a new user to the point where they can start Eclipse, build all the applications and run them

</td>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Clean all the projects - calls clean on all the projects and clears the database

</td>

</tr>

<tr>

<td class="confluenceTd">

populate-database

</td>

<td class="confluenceTd">

Populates the database - takes an optional parameter of -Ddatabasescenario=...

</td>

</tr>

<tr>

<td class="confluenceTd">

generate

</td>

<td class="confluenceTd">

Calls generate task on all projects

</td>

</tr>

<tr>

<td class="confluenceTd">

test

</td>

<td class="confluenceTd">

Calls test task on all projects

</td>

</tr>

</tbody>

</table>

(Note running test will not run generate, and running generate will not run clean)

## <a name="ProjectAntTasks-ant\build.xml"></a>ant\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

bootstrap

</td>

<td class="confluenceTd">

Set up a new user to the point where they can start Eclipse, build all the applications and run them

</td>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Clean all the projects - calls clean on all the projects and clears the database

</td>

</tr>

<tr>

<td class="confluenceTd">

populate-database

</td>

<td class="confluenceTd">

Populates the database - takes an optional parameter of -Ddatabasescenario=...

</td>

</tr>

<tr>

<td class="confluenceTd">

generate

</td>

<td class="confluenceTd">

Calls generate task on all projects

</td>

</tr>

<tr>

<td class="confluenceTd">

test

</td>

<td class="confluenceTd">

Calls test task on all projects

</td>

</tr>

</tbody>

</table>

(Note running test will not run generate, and running generate will not run clean)

## <a name="ProjectAntTasks-ant\utils.xml"></a>ant\utils.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-ant\external-ant-tasks.xml"></a>ant\external-ant-tasks.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-ant\paths.xml"></a>ant\paths.xml

No tasks - just property definitions for the project directory structure - and any file paths.

## <a name="ProjectAntTasks-ant\macros.xml"></a>ant\macros.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Macro

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

print-usage-message

</td>

<td class="confluenceTd">

Displays how to display ant targets

</td>

</tr>

<tr>

<td class="confluenceTd">

read-environment-settings

</td>

<td class="confluenceTd">

Checks for existence and loads property files

</td>

</tr>

<tr>

<td class="confluenceTd">

save-or-update-environment.properties

</td>

<td class="confluenceTd">

Modify environment.properties file

</td>

</tr>

<tr>

<td class="confluenceTd">

sqlplus

</td>

<td class="confluenceTd">

Run SQL script

</td>

</tr>

<tr>

<td class="confluenceTd">

echo-path

</td>

<td class="confluenceTd"></td>

</tr>

<tr>

<td class="confluenceTd">

echo-fileset

</td>

</tr>

<tr>

<td class="confluenceTd">

clear-dir

</td>

<td class="confluenceTd">

Clear directory

</td>

</tr>

<tr>

<td class="confluenceTd">

run-ant-build

</td>

<td class="confluenceTd">

Run ant build scripts

</td>

</tr>

<tr>

<td class="confluenceTd">

get-path-for-environment-file

</td>

<td class="confluenceTd">

Resolve path for environment file - either to the template directory or to the environment folder

</td>

</tr>

<tr>

<td class="confluenceTd">

copy-environment-file

</td>

<td class="confluenceTd">

Copy file from the environment folders into a specific target directory - applying standard filtersets

</td>

</tr>

<tr>

<td class="confluenceTd">

read-svn-info

</td>

<td class="confluenceTd">

Sets properties svn.revision and svn.location, uses macros svn-revision and svn-location

</td>

</tr>

<tr>

<td class="confluenceTd">

svn-revision

</td>

<td class="confluenceTd">

Gets svn revision from _svn info_

</td>

</tr>

<tr>

<td class="confluenceTd">

svn-location

</td>

<td class="confluenceTd">

Gets svn location from _svn info_

</td>

</tr>

<tr>

<td class="confluenceTd">

generateCoverageJar

</td>

<td class="confluenceTd">

Generate a new jar file with code coverage stuff

</td>

</tr>

<tr>

<td class="confluenceTd">

generateCoverageReport

</td>

<td class="confluenceTd">

Generates a coverage report from emma files

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-configuration\build.xml"></a>configuration\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

bootstrap

</td>

<td class="confluenceTd">

Removes and recreates a local configuration directory for the user with the name <environment>. (this parameter defaults to the local machine name if not specified on the command line). This will contain local.properties file - and an application.cfg file. (This is copied from a template configuration directory).

</td>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

removes the template directory for the local machine

</td>

</tr>

<tr>

<td class="confluenceTd">

switch-environment

</td>

<td class="confluenceTd">

Changes the contents of environment.properties - but unlike bootstrap does not remove the configuration directory if it already exists

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-database\build.xml"></a>database\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Drops and recreates the database user

</td>

</tr>

<tr>

<td class="confluenceTd">

load-database

</td>

<td class="confluenceTd">

Populates the database - takes an optional parameter of -Ddatabasescenario=...(has a dependency on clean). Runs the database DDL from the DDL folder and then loads the data from the appropriate directory depending on the value of databasescenario. If databasescenario is not specified then the default directory is used.

</td>

</tr>

<tr>

<td class="confluenceTd">

extract-database

</td>

<td class="confluenceTd">

Extracts the contents of the database - takes an optional parameter of -Ddatabasescenario. Extracts the database contents to a directory depending on the value of databasescenario. If databasescenario is not specified then the default directory is used.

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-MRSEJB\build.xml"></a>MRSEJB\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Remove any compilation and dist artifacts

</td>

</tr>

<tr>

<td class="confluenceTd">

generate

</td>

<td class="confluenceTd">

Compile the project and copy the application.xml file from the environment folder into the source directory

</td>

</tr>

<tr>

<td class="confluenceTd">

test

</td>

<td class="confluenceTd">

Run any unit tests and Swing tests

</td>

</tr>

<tr>

<td class="confluenceTd">

config-local

</td>

<td class="confluenceTd">

Run the application in local mode - triggers rebuild

</td>

</tr>

<tr>

<td class="confluenceTd">

config-remote

</td>

<td class="confluenceTd">

Run the application in remote mode - triggers rebuild

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-MRSWeb\build.xml"></a>MRSWeb\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Remove any compilation and dist artifacts

</td>

</tr>

<tr>

<td class="confluenceTd">

generate

</td>

<td class="confluenceTd">

Compile the project

</td>

</tr>

<tr>

<td class="confluenceTd">

test

</td>

<td class="confluenceTd">

Run any webtests

</td>

</tr>

<tr>

<td class="confluenceTd">

test-sanity

</td>

<td class="confluenceTd">

Run sanity tests only

</td>

</tr>

<tr>

<td class="confluenceTd">

test-functional

</td>

<td class="confluenceTd">

Run functional **and** sanity tests

</td>

</tr>

<tr>

<td class="confluenceTd">

test-navigation

</td>

<td class="confluenceTd">

Run navigation **and** sanity tests

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-MRSDeployment\build.xml"></a>MRSDeployment\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

init

</td>

<td class="confluenceTd">

Makes directories for deployment build,package,dist and temp

</td>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Clean any created ears from the dist directory

</td>

</tr>

<tr>

<td class="confluenceTd">

jar-ejb

</td>

<td class="confluenceTd">

Generate resources and collects them together. Only for the main source files

</td>

</tr>

<tr>

<td class="confluenceTd">

generate-ear

</td>

<td class="confluenceTd">

Generate an ear containing the jar of EJBs and war for web and webstart if required.

</td>

</tr>

<tr>

<td class="confluenceTd">

generate-web

</td>

<td class="confluenceTd">

Generate an ear containing just the WAR

</td>

</tr>

<tr>

<td class="confluenceTd">

generate-webstart

</td>

<td class="confluenceTd">

Generate webstart war

</td>

</tr>

<tr>

<td class="confluenceTd">

generate-all

</td>

<td class="confluenceTd">

Generate an ear containing the EJBs, web WAR and Webstart WAR

</td>

</tr>

<tr>

<td class="confluenceTd">

deploy

</td>

<td class="confluenceTd">

Deploy the created ears to the application server specified in the environment properties

</td>

</tr>

<tr>

<td class="confluenceTd">

start-jboss

</td>

<td class="confluenceTd">

Run the server with the deployed ear.

</td>

</tr>

<tr>

<td class="confluenceTd">

stop-jboss

</td>

<td class="confluenceTd">

Stop the server with the deployed ear.

</td>

</tr>

<tr>

<td class="confluenceTd">

setup-jboss

</td>

<td class="confluenceTd">

Setup the Jboss server for running MRS (deploys the datasource and any Jboss config)

</td>

</tr>

<tr>

<td class="confluenceTd">

test-deployment

</td>

<td class="confluenceTd">

Run any sanity tests on the deployed application

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-reports\build.xml"></a>reports\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

clean

</td>

<td class="confluenceTd">

Removes generated report files

</td>

</tr>

<tr>

<td class="confluenceTd">

create-checkstyle-report

</td>

<td class="confluenceTd">

Creates a checkstyle report based on the current source code

</td>

</tr>

<tr>

<td class="confluenceTd">

create-findbugs-report

</td>

<td class="confluenceTd">

Creates a findbugs report based on the current source code

</td>

</tr>

<tr>

<td class="confluenceTd">

create-reports

</td>

<td class="confluenceTd">

Creates both findbugs and checkstyle reports

</td>

</tr>

</tbody>

</table>

## <a name="ProjectAntTasks-tools\LDAPClient\build.xml"></a>tools\LDAPClient\build.xml

<table class="confluenceTable">

<tbody>

<tr>

<th class="confluenceTh">

Task

</th>

<th class="confluenceTh">

Description

</th>

</tr>

<tr>

<td class="confluenceTd">

setup-current-user-on-ldap

</td>

<td class="confluenceTd">

Adds the current user to the ldap server (if they don't already exist). Also adds the user to certain groups.

</td>

</tr>

</tbody>

</table>

</div>

</div>

</div>

<div id="footer" role="contentinfo">

<section class="footer-body">

Document generated by Confluence on Oct 03, 2019 19:08

<div id="footer-logo">[Atlassian](http://www.atlassian.com/)</div>

</section>

</div>

</div>
