# Adding JARs

1.  Place jars inside of MRSEJB/lib, or MRSESB/lib (if needed in esb
    only)
2.  If jar is needed to be distributed with swingclient (ie webstart)
    1.  Add entry to lib.webstart path in /ant/paths.xml
3.  If jar is required by swing application,
    1.  Add \<module\>\<java\>...\</java\>\</module\> to
        configuration/templateenvironment/webstart/application-webstart.xml
    <!-- end list -->
      - This makes the jar available to be downloaded from the jboss
        server
    <!-- end list -->
    1.  add \<jar /\> element to
        configuration/templateenvironment/webstart/mrs.jnlp
    2.  add \<jar /\> element to configuration/\*\*/webstart/mrs.jnlp
        (ie every config that has a webstart/mrs.jnlp)
    <!-- end list -->
      - This makes the webstart process request the jar and put it on
        its classpath
4.  If jar is required by the esb (at all)
    1.  Add \<module\>\<java\>...\</java\>\</module\> to
        configuration/templateenvironment/esb/application.xml
5.  If jar is required by the server (at all)
    1.  Add \<module\>\<java\>...\</java\>\</module\> to
        configuration/templateenvironment/application.xml
    2.  Add \<module\>\<java\>...\</java\>\</module\> to
        configuration/templateenvironment/application-all.xml
6.  If jar is required by the webclient (at all)
    1.  Add \<module\>\<java\>...\</java\>\</module\> to
        configuration/templateenvironment/web/application-web.xml
7.  Dont forget to unlock the .classpath files on your working copy
    before you try to change the eclipse classpath.
    1.  Dont forget to re-lock it afterwards\!
