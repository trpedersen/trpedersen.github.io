# marathon

Marathon is an automated test tool targeted at Swing user interfaces.
http://www.marathontesting.com/ Installation of Marathon is dead simple,
extract the zip and add the root to your PATH. Marathon has separate
'project' files for each application that you want to test (just 1 in
our case). The project has information regarding classpath, main class
etc. and holds all the generated Python scripts. To be able to playback
tests you do not need to have Python installed. When playing back
scripts Marathon highlights the current executed line in code so you can
see exactly what it is doing and makes debugging easier. Recording tests
is straight-forward although the rego.act application reacts a little
slowly when doing this. Marathon records actions based on control label,
not x,y coordinates. Hence, when I renamed the User ID label on the
login form, the script was not able to run. Note also that the script
just hung and eventually timed out after about a minute and a reasonably
descriptive error was eventually thrown. Marathon ignores mouse clicks
on disabled buttons as well as on menu bars, but I believe this is
configurable and shouldn't be an issue anyway. Marathon has the ability
to add assertions by right clicking a text field when recording and
select an option from the dropdown list (eg Assert Text = ?). Alex
worked out how to do this in rego.act (Ctrl-F8) When selecting nested
menu items, only the final selection is registered eg. this is the code
generated: select\_menu('File\>\>New\>\>Registration\>\>Establish
Registration') This seems to sometimes be a bad thing, as when played
back at normal speed the application throws an error as the menu hasn't
had time to expand yet. You have to use the 'Slow Play' option to get it
to work correctly. Another issue I had with playing back the application
was that the script ran successfully and did as expected but become
stuck 2 lines before the end because the application had closed (which
it was supposed to do) and the script had 2 more lines of close()
remaining. It times out after a minute or so and then fails the test.
Marathon seems to work well enough (I've not used anything else) but
given the "flakeyness" of the rego.act application (or our configuration
of it) I couldn't really test creating a registration etc.
