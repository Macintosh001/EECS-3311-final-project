# The Inventory Manager (TIM)
A very cool and classy project that will get us an A+

## Documentation
All relevant documentation can be found in the [wiki](https://github.com/Macintosh001/EECS-3311-final-project/wiki).

## Installation / Run Instructions
You can find our most recent releases [here](https://github.com/Macintosh001/EECS-3311-final-project/releases). 
Download the ZIP archive onto your machine.

### SQL Database

Our product required a local SQL database to work. So make sure you have one installed.
In the first window of the application, you will be asked to enter a username and password. Here, you must enter the username and password for your
local host server. 
If running the persistence integration tests, which should only be done by the dev team or admin, you must have the username root (the default username) and the password root1234. 
To change your password (which you will need to do only for integration testing), you can run the following SQL query.
```roomsql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root1234';
```

This will change your password to `root1234`.

### Running the Application

The ZIP archive contains a `bin` folder. This folder has three different way to run the app.

- If you are on windows, you can run `app.bat`
- If you are on Linux/Mac OS, you can run `app` since it's just a shell script
- On any platform, you can run `app.jar` which is a runnable jar

If non of these options work, maybe the troubleshooting info below will help.

## Unit / Integration Tests

We use gradle as out build manager. All our unit tests are stored in `app/src/test/java/unit/`. In order to run said tests, simple run `gradle test` in a CLI. This requires you to have gradle installed of course.

Also, for the integration test to work, the username and password of the local SQL database had to be hard-coded into the tests. So, to run them, you have two options:

1. Set the password of `root`@`localhost` to `root1234`. (This can be done using the SQL query above.)
2. Change the username and password in the tests manually. 

Integration tests are in `app/src/test/java/integration/` if you need to make this change.

## Troubleshooting

### MacOS
If you are using MacOS then here are some instructions you should follow
1. click on the app, you should see a pop up that says this file can't be opened
2. open up System Settings and navigate to Privacy & Security
3. scroll down close to the bottom, you should see an option to allow app.jar to be open, allow it
4. afterwards you should be able to open the file

### Linux
If you're on linux, just open a terminal and run `java -jar app.jar`. If it doesn't work, you might have an outdated java version. Use  `java -version` to check, and you can install the right version of java using the right command for your distribution:

Debian/Ubuntu `sudo apt install openjdk-17-jre`
Feodra: `sudo dnf install java-17-openjdk`

### Windows
If you are using windows, and the instructions above do not work for running the app:
1. If opening the .jar file throws an error, try installing the newest version of java by following this link's instructions: https://phoenixnap.com/kb/install-java-windows Then try opening the .jar again, or restarting the command line, navigating to the directory of app.jar, and running `java -jar app.jar`.

If your .jar is still not working, these steps might help:
1.  Go to the following location in your file explorer: C:\ProgramFiles\Java then open the folder corresponding to your java version, for example, C:\Program Files\Java\jdk-19 and go to the bin folder C:\Program Files\Java\jdk-19\bin. Copy the adress of this folder.
2. Go to your windows settings. Click System > about > Advandced System Settings. 
3. In the pop up go to Environment Variables. 
4. In the second pop up, under system variables click on Path > Edit > New. 
5. Paste the address of the bin folder from earlier with a semi-colon. For example, C:\Program Files\Java\jdk-19\bin; would be pasted. Press ok.
6. Try to open the .jar file again. If working in the command line, you must restart the terminal before running `java -jar app.jar`again.


