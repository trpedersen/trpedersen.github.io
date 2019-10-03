# Project Directory Structure

## Project Directory Structure

This document describes the existing directory structure for Rego.ACT
and the new directory structure for developing MRS. This page concerns
itself with the directory structure from the point of view of
development and excludes design artifacts and documentation and the
details of subversion branching etc. It is the contents of 'actrego'
underneath trunk.

## Existing Directory Structure

  - environment (system setup stuff e.g. for LDAP - not integrated
    with  
    build)
  - implementation
      - build (overall build scripts - e.g. build for a web deployment)
      - jiver (primary build directory)
      - lib (java library files)
      - tomcat (would not be used here as we will use JBoss instead)
      - toolslib (code generation stuff - probably not used by us)
      - website (used I believe - replaced by jiver/web)
  - model (rational rose stuff - redundant)
  - testing (empty)

Jiver breaks down into

  - build (build each layer of the system)
  - config (configuration for different servers)
  - documentation (developer documentation - moved into confluence)
  - earsco (iona ear framework - redundant)
  - idealtest (redundant as we are not using the Ideal Client - we are
    using LICS instead)
  - j2ee (jboss ear framework)
  - local (used for developers having a local copy of code - redundant)
  - nonears (WAP proxy - redundant)
  - sql (big file containing additional SQL for populating reference
    tables etc - being replaced by a slightly more friendly version)
  - src (Java source)
  - system (empty - therefore redundant)
  - templates (templates for code generation - redundant)
  - web (images and static html)
  - webstart (build for webstart deployment)
  - wfs (reports)

## Issues with existing directory structure

The build environment for Rego.ACT is

  - confusing and cluttered - contains a large number of redundant
    components e.g. all the Iona build components are still in there
  - does not contain all the build functions a developer needs e.g. to
    create a database schema for development work
  - full of hard coded system references (it assumes a UNIX environment)
    and machine references (e.g. Mega)
  - contains Perl scripts which few developers would be able to
    understand or maintain.

## New Directory Structure

This directory structure is based on an existing project structure which
should allow some code reuse.

  - build.xml (Master build file - delegates to ant/build.xml)
  - ant (contains master ant scripts which will delegate to the
    appropriate sub-ant scripts to perform the actual work. Also
    contains common macros and utilities)
      - build.xml - Master build file which delegates tasks to
        appropriate sub build files
      - utils.xml - Utilities e.g. kill-hung-process
      - paths.xml - Paths for directory structure
      - macros.xml - Macrodefs
  - configuration (configuration)
      - build.xml (environment related tasks e.g. setupjboss)
      - environment.properties (specifies which property file to use for
        the build. Easier than passing a reference between all the ant
        build scripts)
      - default.properties (contains default properties)
      - templateenvironment (directory containing template environment
        for a specific machine e.g. application.cfg)
      - \<buildenvironment\> (directory containing environment for a
        specific machine e.g. application.cfg)
  - database (database tasks)
      - build.xml (database tasks - clean, load-database)
      - ddl (database DDL)
      - data (database data)
          - default
          - \<scenario x\> - data specific to a particular testing
            scenario
  - MRS (Overall MyEclipse J2EE Project)
      - build.xml (clean, generate, test, dist)
      - META-INF
      - dist (distribution directory - contains Ear after running dist
        target)
  - MRSEJB (EJB Project)
      - build.xml (clean, generate, test, dist)
      - src (Java source)
      - testsrc (test for Swing tests)
          - META-INF
      - classes
      - lib
      - dist (distribution directory - contains EJB Jar after running
        dist target)
  - MRSWEB (Web Project)
      - build.xml (clean, generate, test, dist)
      - testsrc (source for web tests)
      - web
          - images
          - statichtml
          - META-INF
          - WEB-INF
              - classes
      - dist (distribution directory - contains War after running dist
        target)
  - MRSDeployment (Deployment project - to generate distribution files,
    deploy them and test the deployment)
      - build.xml (generateearejbwar, generateearejb, generateearwar,
        generatewebstartear, deploy, testdeployment)
      - testsrc (source for deploymenttests)
      - webstart (web start support files .jnlp and keystore)
  - tools (Any third party tools e.g. Marathon, or any tools we have
    written to support the build e.g. custom ant tasks)
