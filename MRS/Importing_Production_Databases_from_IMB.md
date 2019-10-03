# Importing Production Databases from IMB

# Premable

From time to time UXC developement require and updated version of the
database that is in existence at IMB. To this end IMB supplies a data
pump file from a particular deployment (Oracle data pump supersedes the
previous export database function) upon request.  
The best way to document this is to present a case study of an actual
import.

# Case Study

## Import Data Pump file from TMRT2-APP schema TMR from IMB

### Background.

The development server at UXC, smithers, was showing signs of maxing out
on CPU utilisation (100%\!\!\!) due to the number of jboss deployments
and Oracle co-existing albiet on different zones. It was decided to
utilise a windows server (pemsdb) to contain the imported Oracle
database in an attempt to reduce the load; it should be noted that user
databases still reside on smithers (zone tmrd-db). To this end an extra
disk was added to pemsdb and the data pump file imported.

another page has been made for the detail viz Database import impdp
