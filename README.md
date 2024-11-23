# Student Management System
### Project for CS157A Introduction to Database Management Systems - Fall 2024
<hr>

### Tools used:
- Apache Tomcat webserver
- Maven build system
- Jakarta EE package
- Postgresql
- HTMX frontend javascript library

## Setup:
Instructions are listed here for Microsoft Windows 11, similar steps should work on other platforms.
1. Start by installing the latest java development kit. 
    You can [download an installer](https://www.oracle.com/java/technologies/downloads/#jdk23-windows) from oracle.
    This should install somewhere like `C:\Program Files\Java\jdk-23`. Make note of this location.
    1. Add the jdk folder to the system environment variable JAVA_HOME. __This is an important step needed later.__
        1. Press Windows key and type `env`, click Edit the System Environment Variables.
       2. Select `Environment Variables`
       3. Under System Variables click `New` and specify: varable= `JAVA_HOME`, value= `C:\Program Files\Java\jdk-xx` (replace xx with your java version)
2. Install Apache Tomcat from their [downloads page](https://tomcat.apache.org/download-10.cgi).
   1. Extract this folder to your desired location.
   2. Open a command prompt in the /bin/ directory within the Tomcat installation
   3. Run `./startup.bat`
   4. Open a webbrowser and navigate to `localhost:8080`
   5. If everything worked correctly you should see an Apache Tomcat home page that says "If you're seeing this, you've successfully installed Tomcat. Congratulations!"
   6. Additional configuration may be needed to get this working, I would suggest making sure this runs before proceeding.
3. Install [Postgresql](https://www.postgresql.org/download/) database management server
    1. After installing you will need to set the postgres user password, make this secure
   2. It is always best practice to create a new user for each application that is connecting to the database.
   3. In `psql` run `CREATE USER smsdeveloper CREATEDB CREATEROLE WITH PASSWORD <your password here>;`
4. Clone this repo somewhere (not inside of Tomcat folder)
 