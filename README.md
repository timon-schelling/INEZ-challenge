# INEZ / Timon Schelling

### About 
This Project Is my participation in the INEZ-challenge by it-talents.de(https://www.it-talents.de/foerderung/code-competition/edeka-digital-code-competition-08-2019)

### Features
- Written in kotlin
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

### Requirements

#### Run 
Installed JRE(1.8 tested on every System) with javafx (https://www.java.com/de/download/)

#### Build
Installed JDK(1.8 tested on every System) with javafx (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Installation
Download the dist.zib from the current release and extract it on your machine

### Usage
Run the `launch` file depending on your System

Windows:

    .\launch.exe

Linux and MacOS:

    ./launch

or run it directly with Java:

    java -cp "lib/*" Main


### Code example's

#### klang tool
```kotlin
import klang.check
import klang.impl.JLanguageToolRule
import klang.tool
import org.languagetool.language.GermanyGerman

fun main() {
    
    val someRandomGermanTextWithSpellingIssues = "Hallo. Ich ben ein kliner Blindtext. Und zwar schan so longe ich denken kann."

    //create new tool object
    val tool = tool {
        //add all rules here with unaryPlus or add
        +JLanguageToolRule(GermanyGerman())
    }

    //call check method on tool to execute all tool rules on someRandomGermanTextWithSpellingIssues
    val suggestions = tool.check(someRandomGermanTextWithSpellingIssues)

    //read and print suggestion data
    suggestions.forEachIndexed { i, it ->
        println("$i: ${it.original} --> ${it.suggested}")
    }

}
```
output: 

    0: ben --> Ben
    1: ben --> den
    2: ben --> bei
    3: ben --> oben
    4: ben --> per
    5: ben --> bin
    6: ben --> eben
    7: ben --> gen
    8: ben --> wen
    9: ben --> Yen
    10: ben --> peu
    11: ben --> säen  
    ...    

### Build 
The project is built with Gradle. Run Gradle to build the project and to run the tests 
using the following command on Linux/MacOS:

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

## License

[Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License](http://creativecommons.org/licenses/by-nc-nd/3.0/)
