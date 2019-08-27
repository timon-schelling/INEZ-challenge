# INEZ / Timon Schelling

### About 
This Project Is my participation in the INEZ-challege by it-talents.de(https://www.it-talents.de/foerderung/code-competition/edeka-digital-code-competition-08-2019)

### Features
- Easy to use shopping list
- Auto save
- Add and remove items
- Check items 
- Optional hide checked items
- Reset list(uncheck all item at once)
- Check Spelling of units and products
- Customizable units and product list
- Find and merge duplicates (to example "1 Apple" & "2 Apple" -> "3 Apple")
- Pin/unpin window (blue button)

### Installation
Download the dist.zib from the current release and extract it on your machine

### Usage
Run the `launch` file depending on your OS

Windows:

    .\launch.exe


Unix and macOS:

    ./launch

or run it directly with java:

    java -cp "lib/*" Main


### Build 

The project is built with Gradle. Run Gradle to build the project and to run the tests 
using the following command on Unix/macOS:

    ./gradlew build
    
or the following command on Windows:

    gradlew build

The folder `dist` will contain a ready to use build of INEZ
and (as common for gradle) `build` all other build files.

### Content

#### Modules 
- Core: UI and INEZ specific code 
- Util: not INEZ specific code 
- Test: Unit tests

#### Packages
- amber: parts of my currently not published kotlin utility library Amber  
- klang: tool to suggest text changes in user input (based on rules, also used for autocompletion)
- org.slf4j.impl: contains a dummy StaticLoggerBinder(used to disable slf4j logging) 

### Requirements

#### Run 
Installed JRE(1.8 tested on every OS) with javafx (https://www.java.com/de/download/)

#### Build
Installed JDK(1.8 tested on every OS) with javafx (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## License

[Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License](http://creativecommons.org/licenses/by-nc-nd/3.0/)
