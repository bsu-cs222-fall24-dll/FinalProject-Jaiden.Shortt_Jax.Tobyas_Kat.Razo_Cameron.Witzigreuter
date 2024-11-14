---
# Final Project: Speedrun.com Leaderboard Changes
This project shows a user recent changes to a leaderboard in any category of any game. Results can be filtered by top position and sorted by a few methods.

The **first iteration** allows users to lookup the top 10-20 speedrunners on a given leaderboard. It runs in the Command-Line Interface.  
The **second iteration** will allow users to see time splits on a given speedrun. It will run in a JavaFX GUI.  
The **third iteration** will provide users a timer to record their own time splits. It will run in a JavaFX GUI.  

---
## Build Instructions
***Must be on JDK 21. Using an earlier version will cause compiling errors and break the program.***


### Getting Set Up
To run this code, simply clone the code or download the latest release and open it in your IDE. Detailed cloning instructions can be seen below.
<details>
   <summary><h3>Step-by-Step Instructions</h3></summary>
       <p>
       1. <a href="https://www.jetbrains.com/idea/download/" itemprop="Download IntelliJ">Install IntelliJ IDEA</a> if you haven't already, and make sure you have <a href="https://github.com/git-guides/install-git">the latest version of Git installed</a>. 
       <p>
       2. Once IntelliJ is open, navigate to <b>File > New > Project from Version Control</b>.
       <p>
       3. In the <kbd>URL</kbd> field, paste the link to this repository's code, which you can acquire by going to <kbd><> Code</kbd>.
       <p>
       4. Once Gradle finishes building the project, you can run your code.
</details>

### To Run The Command Line Interface
Load the code into your IDE, then run `Main.java`.  

### To Run The JavaFX Graphical Interface
Load the code into your IDE, then run `LeaderboardApplication.java`

---
## Attribution
| Contributor          | Username                                                       | Role      | 
|----------------------|----------------------------------------------------------------|-----------|
| Jaiden Shortt        | <kbd>[☍](https://github.com/JaidenShortt)</kbd> `JaidenShortt` | Co-author |
| Jax Tobyas           | <kbd>[☍](https://github.com/JaxT05)</kbd>       `JaxT05`       | Co-author |
| Kat Razo             | <kbd>[☍](https://github.com/katrazo)</kbd>      `katrazo`      | Co-author | 
| Cameron Witzigreuter | <kbd>[☍](https://github.com/Cameron311)</kbd>   `Cameron311`   | Co-author |
| Mr. David L. Largent | <kbd>[☍](https://github.com/dllargent)</kbd>    `dllargent`    | Professor |
       
| Dependency       | Source                                                                               | Usage                                                  |
|------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------|
| SLF4J-NOP Logger | [Maven Repository](https://mvnrepository.com/artifact/org.slf4j/slf4j-nop)           | Removes logging warning                                |
| Jayway JsonPath  | [Maven Repository](https://mvnrepository.com/artifact/com.jayway.jsonpath/json-path) | Utilities for parsing JSON data                        |
| Apache IOUtils   | [Maven Repository](https://mvnrepository.com/artifact/commons-io/commons-io)         | Streamlines the process of retrieving data from a link |

| External Resource   | Link/Credit                                                                                   | Usage                                                         |
|---------------------|-----------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| Speedrun.com Backup | [GitHub Repository](https://github.com/xwmtp/src-backup) by [xwmtp](https://github.com/xwmtp) | Gather preliminary json data from games for testing purposes. |

---
