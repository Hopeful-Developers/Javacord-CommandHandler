package me.hopedev.commandhandler;

import org.javacord.api.DiscordApi;

import java.util.ArrayList;

public class CommandBuilder {

    private final ArrayList<Command> commands = new ArrayList<>();
    private final String commandPrefix;
    private final DiscordApi api;
    private CommandExecutor helpCommand;
    private String helpCommandTrigger = "help";


    public CommandBuilder(String commandPrefix, DiscordApi api) {
        this.commandPrefix = commandPrefix;
        this.api = api;
    }

    public CommandBuilder(DiscordApi api) {
        this.commandPrefix = "!";
        this.api = api;
    }

    public final CommandBuilder setHelpCommand(String helpCommand, CommandExecutor helpCommandExecutor) {
        this.helpCommandTrigger = helpCommand;
        this.helpCommand = helpCommandExecutor;
        return this;
    }

    public final CommandBuilder setHelpCommand(String command) {
        this.helpCommandTrigger = command;
        this.helpCommand = new DefaultHelpCommandExecutor();
        return this;
    }


    public final CommandBuilder addCommand(String commandString, CommandExecutor executor) {
        Command command = new Command(commandString, executor, this.api, this.commandPrefix);
        this.commands.add(command);
        return this;
    }

    public final void build() {
        this.commands.forEach(command -> this.api.addMessageCreateListener(new CommandHandler(command, this.commands)));

        // Help command
        this.api.addMessageCreateListener(new CommandHandler(new Command(helpCommandTrigger, this.helpCommand, this.api, this.commandPrefix), this.commands));

    }


}
