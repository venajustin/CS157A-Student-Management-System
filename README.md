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
   1. During install set the postgres user password to something secure and **do not share this.** You can leave the port as default, it should be `5432`.
   2. It is always best practice to create a new user for each application that is connecting to the database. Heres how to do that:
        1. Open powershell or some cmd prompt.
      2. Execute `psql -U postgres`, enter the password assigned at install
      3. Execute this and set the password to **something other than your postgres user password**
      `CREATE USER smsdeveloper WITH PASSWORD '<password>;' `
      4. Create the database for this project with `CREATE DATABASE smsdb WITH OWNER smsdeveloper;`.
      5. Exit the psql console with `\q`
      6. Log in as the new user with `psql -U smsdeveloper -d smsdb`
      7. Run database schema creation scripts found in `create_schema.sql`. You can either copy and paste this file into the console, or run it through a database connection tool in your ide.
      8. Run example data scripts found in `initialize_data.sql`. All student and professor accounts added here will have a password set to `12345qwert`. You can use this to log into their accounts.
4. Clone this repo somewhere (not inside of Tomcat folder)
5. On visual studio code, install two extensions
   1. **Extension Pack for Java** from Microsoft
   2. **Community Server Connectors** from Red Hat
6. Download the [Postgresql JDBC driver](https://jdbc.postgresql.org/download/). You should probably select the Java 8 driver which will work on versions newer than 8.
7. Under `/src/main/webapp/` you will need to create a directory `META-INF` containing a `lib` directory and `context.xml`.
    This will contain the database configuration and driver that you wish to use. 
    The driver jar that was downloaded in step 6 will be placed in the `lib` directory, the structure should look like this:
    ```
    src/
        main/
            java/
            webapp/
                META-INF/
                    lib/
                        postgresql-42.7.4.jar
                    context.xml
                WEB-INF/
                index.jsp
        target/
    ...
    ```
    The `context.xml` file will contain the connection information for your local postgresql database. The following context should work as long as `<password>` is replaced with the password given to the `smsdeveloper` user.
    ```xml title="context.xml"
    <Context>
        <Resource
            name="jdbc/MyPostgresDB"
            auth="Container"
            type="javax.sql.DataSource"
            factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
            driverClassName="org.postgresql.Driver"
            url="jdbc:postgresql://localhost:5432/smsdb"
            username="smsdeveloper"
            password="<password>"
            maxActive="20"
            maxIdle="10"
            minIdle="2"
            initialSize="5"
        />
    </Context>
    ```
8. From a console in the root of this repo, run the following `./mvnw clean compile install` which will compile the project with maven.
9. Under the `SERVERS` tab of visual studio ( located in the bottom left ), the Community Server Connector can be set up.
    1. Right click `Community Server Connector` and select `Create New Server...`
   2. Choose `No, use server on disk`
   3. Navigate to the location of your Apache Tomcat installation
   4. Scroll down and click `Finish`, these settings should be automatically populated correctly.
10. In the `target/` directory you should find `CS157-StudentManagement-System-1.0-SNAPSHOT.war`
    If this isn't there, try running `./mvnw clean install` from a terminal in the project folder.
11. Right-click on `CS157A-StudentManagement-System-1.0-SNAPSHOT.war`. Select `Run on Server`. Choose `Tomcat 10.x`, and `No` to edit parameters.
12. The server can be started, stopped, and restarted through the `servers` menu on visual studio code.
13. To update changes the easiest way is through two steps
    1. Run `./mvnw clean compile install` in the command line
    2. Right-click the tomcat server under `SERVERS` menu and click `Restart in Run Mode`
    
### Testing Features
To see the application working with the example data we can log in as example students or professors. All example accounts
are created with an email of `<name>@school.edu` and a password of `12345qwert`. So we could log in as
student Benjamin with `benjamin@school.edu` and `12345qwert` or professor Smith with `smith@school.edu` and `12345qwert`

If we log into the system as professor Smith, we can go to the current schedule page from 
the navigation bar on the left side of the screen. On this page, all the sections that Smith
teaches are displayed. We can select a section to view enrolled students with the form below
the table. Then, a student's grade can be updated for that class by entering their student id
and the new grade on a scale of 0 to 4. 

The `initialize_data.sql` file will populate the student enrollments and grades randomly, so 
a random set of students will be on this list. We can validate that their grade was changed
successfully by logging out of Professor Smith's account in the top right corner of the page.
Then we can log back in as `<student>@school.edu` and go to the current schedule page again.

Here, the student's class will be displayed, along with their current grade.
From the student account, we can go to the home page and update their account info such as their major.
( This is available for Professors as well ) We can go to the class search page to view all departments, classes, 
and their available sections. This page is the same for both students and professors,
however students have the option to enroll in a section from this page.

Professor accounts have access to an aditional page, which allows them to add classes and
sections. They can add a class to any available department, and can add sections to any classes.
The professor that adds a section will be set as the instructor of that section.
