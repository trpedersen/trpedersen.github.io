# Building Application as Non-Root User on a Solaris Zone

It was discovered that the build scripts as they were from svn did not
work on Solaris  
Not all of the environment properties are supported namely machine name
and user name  
Ant scripts were changed to accomdate both windows and solaris
deployments

  - login to zone with non-root user
  - login as root
      - su - root
      - cd /etc
      - edit with vi profile and zprofile with the following
        <div class="code panel pdl" style="border-width: 1px;">
        <div class="codeContent panelContent pdl">
        ``` java
        PATH=$PATH:/opt/subversion-client/bin:/opt/oracle/product/10.2.0/client/bin
        export LOGNAME PATH
        
        JAVA_HOME=/opt/j2sdk/jdk1.5.0_11
        JRE_HOME=/opt/j2sdk/jdk1.5.0_11/jre
        HUDSON_HOME=/data/hudson_home
        #MRS-51 set JBOSS_HOME all users
        JBOSS_HOME=/opt/jboss
        ORACLE_HOME=/opt/oracle/product/10.2.0/client
        CLASSPATH=/export/home/dnewman/test_build_package/trunk/actrego/MRSEJB/lib/testtime/junit.jar
        export JAVA_HOME JRE_HOME HUDSON_HOME JBOSS_HOME ORACLE_HOME CLASSPATH
        ```
        </div>
        </div>

<!-- end list -->

  -   - Add yourself to groups 14(systemadmin,100(dba),601(jboss)
        <div class="code panel pdl" style="border-width: 1px;">
        <div class="codeContent panelContent pdl">
        ``` java
        usrmod -G 14,100,601 <username>
        ```
        </div>
        </div>
      - Give group jboss write access to jboss default directory and
        below
        <div class="code panel pdl" style="border-width: 1px;">
        <div class="codeContent panelContent pdl">
        ``` java
        chmod -R g+w /opt/jboss/server/default
        ```
        </div>
        </div>
      - exit (log off as root should put you you back in your login
        shell)

  - run profile file to set you shell variables (so you dont have to log
    off and then on)
    
      - . ./etc/zprofile

  - Get code
    
      - cd $HOME
      - mkdir test\_build\_package
      - cd test\_build\_package
      - svn co svn://svn/mrs/trunk/
          - this should get the latest revision of the build (HEAD)in a
            subdirectory called trunk
          - to update
            <div class="code panel pdl" style="border-width: 1px;">
            <div class="codeContent panelContent pdl">
            ``` java
            cd trunk;svn up
            ```
            </div>
            </div>
      - To build
        <div class="code panel pdl" style="border-width: 1px;">
        <div class="codeContent panelContent pdl">
        ``` java
        cd $HOME/test_build_package/trunk/actrego/ant
        sh /usr/sfw/bin/ant bootstrap
        sh /usr/sfw/bin/ant full-build
        ```
        </div>
        </div>

  - Should be able to browse to http://\<zone name\>:8080/mrs/regoact

<!-- end list -->

  - NOTE: in addition need vnc installed and set DISPLAY environment var
    to DISPLAY=:\<x\> where x is an instance of Xvnc (get from ps
    -ef|grep Xvnc)
      - vnc is a package in /import/admin/applications install with
        pkgadd -D vnc
