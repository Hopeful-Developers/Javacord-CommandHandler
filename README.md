# Javacord Command Handler

## Basic Usage
The following example is a very simple bot that uses the command handler to activate the ping command.
```java
public class Main {
    public static void main(String args[]) {
        DiscordApi api = new DiscordApiBuilder().setToken("token").login().join();

        CommandBuilder cmdBuilder = new CommandBuilder("-", api);

        cmdBuilder.addCommand("ping", new String[] {"beep"}, new Ping(), "Shows the bot's current ping.", "-ping");

        cmdBuilder.build();
        
    }
}

pubic class Ping implements IExecutor{
    public void execute(CommandData data, ArrayList<Command> commands) {
        long ping1, ping2, ms;
        ping1 = System.currentTimeMillis();
        Message msg = data.respond("Pinging...");
        ping2 = System.currentTimeMillis();
        
        ms = ping2 - ping1;

        msg.edit("Current Ping: ~" + ms + "ms");
    }
}
```

## Download / Installation
[![](https://jitpack.io/v/DAflamingFOX/Javacord-CommandHandler.svg)](https://jitpack.io/#DAflamingFOX/Javacord-CommandHandler)
To download and use the CommandHandler, we recommend a build manager such as Gradle or Maven.

to install using **Maven**:
* Add the jitpack repo to your pom:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
* Then add the dependency:
```xml
<dependency>
    <groupId>com.github.DAflamingFOX</groupId>
    <artifactId>Javacord-CommandHandler</artifactId>
    <version>v1.0.5</version>
</dependency>
```

to install using **Gradle**:
* Add the jitpack repo to your build.gradle:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
* Then add the dependecy:
```groovy
dependencies {
        implementation 'com.github.DAflamingFOX:Javacord-CommandHandler:v1.0.5'
}
```

