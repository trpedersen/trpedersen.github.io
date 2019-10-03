# Import Data Migration Database

### Import Database

  - Transfer received .dmp file to tmrd-db/data2/tmp via ftp
  - logon to tmrd-db
      - su - root
      - su - oracle
  - run sqlplus from shell
      - sqlplus / as sysdba
  - sqlpus commands
      - drop user regoact;
      - drop user datamig;
      - create user regoact identified by regoact;
      - grant connect, rersource to regoact;
      - create user datamig identified by datamig;
      - grant connect, rersource to datamig;
  - exit from sqlplus
  - from shell
  - import database
      - imp oraadmin/oraadmin file=/data2/tmp/\<filename\>.dmp FULL=Y
        IGNORE=Y

### Create new Database Scenario from Imported Database

  - From local PC
      - cd c:\\svn\\trunk\\actrego\\database
      - ant -Ddatabasescenario=datamig extract-database
        <div>
        Check version in REF\_VERSION to prevent any scripts running
        <div>
          - must be greater than largest number for all of the
            UPGRADExxxxx.sql files (found in upgrades directory under
            database dir)
        </div>
        </div>
