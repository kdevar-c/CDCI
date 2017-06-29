# Introduction
Design and implement automation test cases for sample scenario provided. Demonstrate results by integrating the test run to any dashboard styled report. Auto update the report every time automated test cases are run and show historic data.

Setup from Github

#Preconditions:
1.	Install Java on your computer
2.	Download and install testNG plugin
-[click here](http://marketplace.eclipse.org/content/testng-eclipse)
3.	Download the Maven zip file apache-maven-3.5.0-bin.zip from below location
-[click here](https://maven.apache.org/download.cgi)
4.	Unzip the Maven to C:\javapps
5.	Ensure that path is set for both Maven and Java to access from any location of command line.
6.	Download the 32 bit Chrome Webdriver and copy in location : C:\Webdrivers
-[click here] (https://chromedriver.storage.googleapis.com/index.html?path=2.30/)

#Steps for executing

1.	Download project source files from github
2.	Unzip the folder to the your working location
3.	C:\Users\<Username>\project name of the folder Yelptest
4.	Go to command prompt
5.	Run the below command
5.1 C: \Users\<username> Yelptest> mvn clean install
5.2 Note: driver location, reports location can be modified through config.properties.
Setup and running using project zip files assuming files are sent in email or ftp.

# Preconditions:
1.	Install Java on your computer
2.	Download and install testNG plugin
-[click here](http://marketplace.eclipse.org/content/testng-eclipse)
3.	Download the Maven zip file apache-maven-3.5.0-bin.zip from below location
-[click here](https://maven.apache.org/download.cgi)
4.	Unzip the Maven to C:\javapps
5.	Ensure that path is set for both Maven and Java to access from any location of command line.
6.	Download the 32 bit Chrome Webdriver and copy in location : C:\Webdrivers
-[click here] (https://chromedriver.storage.googleapis.com/index.html?path=2.30/)

# Instructions for running from command prompt

1.	Create a maven project at command line to user profile directory( like C:\users\<username>)
mvn archetype:generate -DgroupId=com.yelptest   -DartifactId=YelpTest -DinteractiveMode=false
2.	Update pom.xml with source directory tags, output directory tags, required plug-ins (java compiler, surefire) and dependencies.
3.	Create testng.xml and provide test class reference.
4.	Copy the configuration file -  config.properties in project directory(C:\Users\<username>\YelpTest)
5.	Edit the configuration file to point chrome webdriver and report file path.
6.	Go to Eclipse: File->Import->Maven
7.	Select Existing Maven Projects from "Import" dialog box.
8.	Browse for Root Directory and select pom.xml
(Wait till the project is imported and built in Eclipse).
Hint: To look at the code, go to Eclipse. To execute the code, go to command line project directory where you have pom.xml
9.	Go to command prompt and cd to YelpTest.
9.1 C:\ Yelptest>
9.2 C: \ Yelptest> mvn clean install
Note: driver location, reports location can be modified through config.properties.

