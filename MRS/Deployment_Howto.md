# Deployment Howto

## Overview

This document contains the general steps required to do an internal
deployment, for testing purposes.  
It will be updated as the process evolves, and will contain proper
external deployment instructions as required.

## Releasing the application

  - Note that deployment is done to the server not being used by testers
    currently. This is shown on http://tmrd-build:8090/Pinger/pinger

### Current process

1.  Build the application for the required environment
2.  Take the deployment artifact and copy it to the target environment
    (eg. "cp MRS-branchesblah.zip /import/admin" on build box)
3.  Login as admin ("su -")
4.  Remove any existing MRS directory ("rm -r MRS")
5.  Unzip the packages ("unzip MRS-branchesblah.zip")
6.  On the target environment, change to the directory that the
    artifacts are in and issue the following commands (as root)
    <div class="code panel pdl" style="border-width: 1px;">
    <div class="codeContent panelContent pdl">
    ``` plain
    pkgrm MRS
    pkgadd -d . MRS
    ```
    </div>
    </div>
7.  Get testers to give quick sanity check
8.  If ok, tell them to finish the work they are doing on the old build
    and tell them to start using the new build
    1.  When notifying testers of a new build, be sure to include the
        changes that have been made in this new version
9.  Increment the build version on the applicable branch or trunk
10. Tell jon/alex to kick the jira version to the new version
11. Disable the build from hudson until they are finished with the old
    build
12. When re-enabling the build- be sure to swich environments to the
    environment not in use by the testers

### Future process (mid July onwards)

1.  When the build is able to deploy (when the testers are not using the
    current target environment)
2.  Hudson will automatically deploy to the target environment (which is
    why the builder will not run if testers are using the target
    environment)
3.  When a build is decided that it will be made available to testers
    1.  The application http://tmrd-build:8090/Pinger/pinger will be
        used to mark the current target environment as stable/inuse/etc
    2.  From hudson, the build for the applicable branch or trunk will
        be disabled to let the testers finish testing (otherwise app
        will be deployed and database dropped at next svn commit)
    3.  The testers will be notified of the new deployment and told to
        finish up testing the existing version
        1.  When notifying testers of a new build, be sure to include
            the changes that have been made in this new version
    4.  The version of the application will be incremented accordingly.
    5.  Update the jira version to the new version
    6.  The target environment is updated to the environment not in use
        by the testers
4.  When the testers have finished with the old version
    1.  The builder will be re-enabled in hudson
    2.  The http://tmrd-build:8090/Pinger/pinger application will be
        used to mark the old target environment as able to be developed
        with (unstable/build/etc)

### Archives

Each build deployed is saved in compressed form (tar.gz) in
/data/deployed\_builds on tmrd-build
