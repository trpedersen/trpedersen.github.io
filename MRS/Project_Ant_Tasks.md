# Project Ant Tasks

## MRS Environment Configuration

The MRS environment system is designed to support different target
environment settings but building on the same machine.

It is important to realise that this refers to the TARGET environment
rather than the current build environment. In theory the build
environment should be consistent across different build boxes - but some
files e.g. ant, sqlplus etc may need to be added to the PATH before the
build works. These will be documented as part of the [Guide to getting
the dev environment
running](/display/MRS/Guide+to+getting+the+dev+environment+running "Guide to getting the dev environment running").

It consists of three components:

  - An environment.properties file residing in the configuration
    directory. This points to which environment is currently in
    operation and any development specific settings e.g. whether to run
    the application in local or remote mode.
  - A default.environment.properties residing in the configuration
    directory. This contains default settings that can be overridden by
    the local.properties file in the environment directory.
  - A directory for each environment containing the deployment files and
    a local.properties file. By defaul this will ONLY contain the
    local.properties file - however you can override any standard
    configuration file by copying it from the templateenvironment folder
    into here.

A macro called: read-environment-settings reads in properties from the
local.properties file and the default.environment.properties file and
sets a property: current-environment.dir pointing to the current
environment directory is use.

The environment properties file is maintained by

  - a macro: save-or-update-environment.properties - which allows you to
    create a new environment.properties file and override settings in an
    existing environment.properties file.
  - tasks within the configuration project - bootstrap (creates a new
    environment), switch-environment (changes to a different
    environment, creating it if required) and clean (remove the current
    environment directory).

If you do not specify the \<environment\> property on the ant command
line then the value is taken from environment.properties - and if that
doesn't exist - then it uses the machine name. Of course if the
environment.properties file does not exist then
read-environment-settings will blow up.

## Project Ant Tasks

The following build files are used to build the new MRS:

| Build File                   | Description                                                                    |
| ---------------------------- | ------------------------------------------------------------------------------ |
| build.xml                    | Master Build file - delegates to ant/build.xml                                 |
| ant\\build.xml               | Main build file - implements methods on main build.xml                         |
| ant\\external-ant-tasks.xml  | Ant tasks within the separate project files                                    |
| ant\\imports.xml             | Includes common ant files - included in all ant build scripts                  |
| ant\\utils.xml               | Non project specific utilities                                                 |
| ant\\paths.xml               | Project paths - to avoid hardcoding directory structure                        |
| ant\\macros.xml              | Any cross project macros                                                       |
| configuration\\build.xml     | Configuration tasks                                                            |
| database\\build.xml          | Database tasks                                                                 |
| MRSEJB\\build.xml            | Build the MRS EJB project                                                      |
| MRSWeb\\build.xml            | Build the MRS Web project                                                      |
| MRSDeployment\\build.xml     | Build the deployment artifacts, deploy the application and test the deployment |
| reports\\build.xml           | Generates findbugs and checkstyle reports                                      |
| tools\\LDAPClient\\build.xml | Sets up user account on ldap server                                            |

As a rule with the tasks - less is more. Or at least with publicly
visible tasks - less is more. Create other tasks and prefix them with .
so they cannot be called from the command line. The tasks should all be
in the form VERB-NOUN. (Noun optional)

## build.xml

| Task              | Description                                                                                          |
| ----------------- | ---------------------------------------------------------------------------------------------------- |
| bootstrap         | Set up a new user to the point where they can start Eclipse, build all the applications and run them |
| clean             | Clean all the projects - calls clean on all the projects and clears the database                     |
| populate-database | Populates the database - takes an optional parameter of -Ddatabasescenario=...                       |
| generate          | Calls generate task on all projects                                                                  |
| test              | Calls test task on all projects                                                                      |

(Note running test will not run generate, and running generate will not
run clean)

## ant\\build.xml

| Task              | Description                                                                                          |
| ----------------- | ---------------------------------------------------------------------------------------------------- |
| bootstrap         | Set up a new user to the point where they can start Eclipse, build all the applications and run them |
| clean             | Clean all the projects - calls clean on all the projects and clears the database                     |
| populate-database | Populates the database - takes an optional parameter of -Ddatabasescenario=...                       |
| generate          | Calls generate task on all projects                                                                  |
| test              | Calls test task on all projects                                                                      |

(Note running test will not run generate, and running generate will not
run clean)

## ant\\utils.xml

| Task | Description |
| ---- | ----------- |

## ant\\external-ant-tasks.xml

| Task | Description |
| ---- | ----------- |

## ant\\paths.xml

No tasks - just property definitions for the project directory structure
- and any file paths.

## ant\\macros.xml

| Macro                                 | Description                                                                                            |
| ------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| print-usage-message                   | Displays how to display ant targets                                                                    |
| read-environment-settings             | Checks for existence and loads property files                                                          |
| save-or-update-environment.properties | Modify environment.properties file                                                                     |
| sqlplus                               | Run SQL script                                                                                         |
| echo-path                             |                                                                                                        |
| echo-fileset                          |                                                                                                        |
| clear-dir                             | Clear directory                                                                                        |
| run-ant-build                         | Run ant build scripts                                                                                  |
| get-path-for-environment-file         | Resolve path for environment file - either to the template directory or to the environment folder      |
| copy-environment-file                 | Copy file from the environment folders into a specific target directory - applying standard filtersets |
| read-svn-info                         | Sets properties svn.revision and svn.location, uses macros svn-revision and svn-location               |
| svn-revision                          | Gets svn revision from *svn info*                                                                      |
| svn-location                          | Gets svn location from *svn info*                                                                      |
| generateCoverageJar                   | Generate a new jar file with code coverage stuff                                                       |
| generateCoverageReport                | Generates a coverage report from emma files                                                            |

## configuration\\build.xml

| Task               | Description                                                                                                                                                                                                                                                                                                                  |
| ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| bootstrap          | Removes and recreates a local configuration directory for the user with the name \<environment\>. (this parameter defaults to the local machine name if not specified on the command line). This will contain local.properties file - and an application.cfg file. (This is copied from a template configuration directory). |
| clean              | removes the template directory for the local machine                                                                                                                                                                                                                                                                         |
| switch-environment | Changes the contents of environment.properties - but unlike bootstrap does not remove the configuration directory if it already exists                                                                                                                                                                                       |

## database\\build.xml

| Task             | Description                                                                                                                                                                                                                                                                                                                      |
| ---------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| clean            | Drops and recreates the database user                                                                                                                                                                                                                                                                                            |
| load-database    | Populates the database - takes an optional parameter of -Ddatabasescenario=...(has a dependency on clean). Runs the database DDL from the DDL folder and then loads the data from the appropriate directory depending on the value of databasescenario. If databasescenario is not specified then the default directory is used. |
| extract-database | Extracts the contents of the database - takes an optional parameter of -Ddatabasescenario. Extracts the database contents to a directory depending on the value of databasescenario. If databasescenario is not specified then the default directory is used.                                                                    |

## MRSEJB\\build.xml

| Task          | Description                                                                                                 |
| ------------- | ----------------------------------------------------------------------------------------------------------- |
| clean         | Remove any compilation and dist artifacts                                                                   |
| generate      | Compile the project and copy the application.xml file from the environment folder into the source directory |
| test          | Run any unit tests and Swing tests                                                                          |
| config-local  | Run the application in local mode - triggers rebuild                                                        |
| config-remote | Run the application in remote mode - triggers rebuild                                                       |

## MRSWeb\\build.xml

| Task            | Description                               |
| --------------- | ----------------------------------------- |
| clean           | Remove any compilation and dist artifacts |
| generate        | Compile the project                       |
| test            | Run any webtests                          |
| test-sanity     | Run sanity tests only                     |
| test-functional | Run functional **and** sanity tests       |
| test-navigation | Run navigation **and** sanity tests       |

## MRSDeployment\\build.xml

| Task              | Description                                                                               |
| ----------------- | ----------------------------------------------------------------------------------------- |
| init              | Makes directories for deployment build,package,dist and temp                              |
| clean             | Clean any created ears from the dist directory                                            |
| jar-ejb           | Generate resources and collects them together. Only for the main source files             |
| generate-ear      | Generate an ear containing the jar of EJBs and war for web and webstart if required.      |
| generate-web      | Generate an ear containing just the WAR                                                   |
| generate-webstart | Generate webstart war                                                                     |
| generate-all      | Generate an ear containing the EJBs, web WAR and Webstart WAR                             |
| deploy            | Deploy the created ears to the application server specified in the environment properties |
| start-jboss       | Run the server with the deployed ear.                                                     |
| stop-jboss        | Stop the server with the deployed ear.                                                    |
| setup-jboss       | Setup the Jboss server for running MRS (deploys the datasource and any Jboss config)      |
| test-deployment   | Run any sanity tests on the deployed application                                          |

## reports\\build.xml

| Task                     | Description                                                  |
| ------------------------ | ------------------------------------------------------------ |
| clean                    | Removes generated report files                               |
| create-checkstyle-report | Creates a checkstyle report based on the current source code |
| create-findbugs-report   | Creates a findbugs report based on the current source code   |
| create-reports           | Creates both findbugs and checkstyle reports                 |

## tools\\LDAPClient\\build.xml

| Task                       | Description                                                                                                   |
| -------------------------- | ------------------------------------------------------------------------------------------------------------- |
| setup-current-user-on-ldap | Adds the current user to the ldap server (if they don't already exist). Also adds the user to certain groups. |
