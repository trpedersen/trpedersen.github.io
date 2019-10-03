# Solaris Packages

## Solaris Packages Overview

Q: What is a package?  
A: A package is a mechanism for bunding software components together
with some installation scripts into a single package which can be easily
managed and deployed with a single command. Solaris packages are
registered in a package repository maintained by the OS. It is a similar
concept to the familiar Windows Installer executables.

## MRS Packaging Strategy

The outcome of the MRSDeployment project is the creation and possible
deployment of a Solaris Package. Each package is created for a specific
target environment.

The package contains a cut down version of the build environment
combined with:

  - apache ant - required to run the build scripts
  - pkginfo file - metadata about the package
  - preinstall script - script run prior to the installation of the
    package
  - postinstall script - script run after the package files have been
    copied into location. This
      - Stops JBOSS server
      - Backs up the DB to $BASEDIR/MRS/backup
      - Upgrades the DB
      - Deploys the application ear files
      - Restarts the JBOSS Server

## Required OS Users for packaging

  - oracle - with ORACLE\_HOME set in the .profile for the user
  - jboss - with JBOSS\_HOME set in the .profile for the user
  - mrs - application owner

The primary group for mrs must be included as a secondary group for both
jboss and oracle.
