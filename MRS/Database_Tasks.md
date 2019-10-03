# Database Tasks

## Database tasks independent of the standard build

Any SQL here should be run using the SQLDeveloper tool supplied with
Devenv.

## Initial set up of the database

The following tablespaces will need to be created:

  - aud\_data
  - clt\_data
  - clt\_indx
  - clt\_blob
  - cq\_live\_spc
  - fin\_data
  - fin\_indx
  - inf\_data
  - inf\_indx
  - reg\_data
  - reg\_indx
  - ins\_indx
  - ins\_data
  - prk\_data
  - prk\_indx
  - plt\_data
  - plt\_indx
  - lic\_data
  - lic\_indx
  - ref\_data
  - ref\_indx

This can be done when the database is created using the wizard, or added
using the SQL:

CREATE TABLESPACE XXXXXX DATAFILE
'C:\\Oracle\\product\\10.2.0\\oradata\\mrs\\XXXXXX.dbf'  
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M ;

(changing the path as appropriate). If Oracle Managed Files has been
enabled only - CREATE TABLESPACE XXXXXX; is required.

## Starting and Logging on to the dbconsole

1.  Log on to tmrd-db
2.  su -
3.  \<Enter root password\>
4.  su - oracle
5.  cd $ORACLE\_HOME/bin
6.  emctl start dbconsole
7.  Open a browser to URL http://tmrd-db:1158/em/console/logon/logon
8.  Log in as user SYS, password \<Same as root password\>, AS SYSDBA

For the lazy, here is the complete SQL. Just find and replace the base
file path before using

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` java
CREATE TABLESPACE aud_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\aud_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE clt_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\clt_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE clt_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\clt_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE clt_blob DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\clt_blob.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE cq_live_spc DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\cq_live_spc.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE fin_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\fin_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE fin_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\fin_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE inf_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\inf_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE inf_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\inf_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE reg_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\reg_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE reg_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\reg_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE ins_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\ins_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE ins_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\ins_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE prk_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\prk_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE prk_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\prk_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE plt_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\plt_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE plt_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\plt_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE lic_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\lic_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE lic_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\lic_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE ref_data DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\ref_data.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
CREATE TABLESPACE ref_indx DATAFILE 'c:\Oracle\product\10.2.0\oradata\mrstas\ref_indx.dbf'
SIZE 10485760 AUTOEXTEND ON NEXT 327680 MAXSIZE 32767M;
COMMIT;
```

</div>

</div>

(to do add any more DB specific parameters required on top of the IMB
standard database).

## Create the master user

Run the SQL:

CREATE USER ORAADMIN IDENTIFIED BY ORAADMIN;  
GRANT DBA TO ORAADMIN;

We can probably restrict the privileges on ORAADMIN to CREATE USER, DROP
USER, CREATE SESSION and SELECT ANY DICTIONARY. But will need to test.
DBA is fine for dev.

## Run imports

Oracle databases can be extracted into a platform independent format
called a DUMP (.DMP) file. These files are of two types:

  - System wide
  - User specific  
    Which one is created depends on whether the export was done using
    the full=y option or not. System wide imports create users and
    roles, User specific imports require the user already created and
    with the correct privileges. Imports are done using the Oracle
    client - IMP command. To find out the contents of a .DMP file you
    can run the import with the option - show=y which will just show the
    SQL it is going to run. That will allow you to see the users and
    data within the dump file. It is best to a) have an empty schema and
    b) have created all the required tablespaces in advance. See the
    above section to create the tablespaces. A tablespace is an Oracle
    object for managing storage. Objects are mapped to tablespaces, and
    tablespaces are mapped to one or more physical datafiles. You can
    import just the data for a specific user using the FROMUSER clause
    in imp.

Imports use OCI so you will need to set up the infamous tnsnames.ora
file with a service name pointing to your DB. If you are running on the
same box as the DB you can just set the environment variable ORACLE\_SID
instead - and leave off the '@SERVICE\_NAME' bit of the connection
string.

An example imp statement is: imp username/password@service\_name
file=\<myfile\>.dmp fromuser=\<user\> buffer=100000

So you give it a user with sufficient privileges to do the import. Tell
it what DB to connect to, what file to use, whose data to load from the
file, and give it a large buffer size so it doesn't conk out half way
through.
