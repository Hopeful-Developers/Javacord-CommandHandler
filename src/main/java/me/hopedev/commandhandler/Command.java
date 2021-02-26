package me.hopedev.commandhandler;

import org.javacord.api.DiscordApi;

public class Command {

    private final String command;
    private final CommandExecutor executor;
    private final DiscordApi api;
    private final String associatedPrefix;
    private final String description;

    public Command(final String command, final CommandExecutor executor, DiscordApi api, String associatedPrefix, String description) {
        this.api = api;
        this.command = command;
        this.executor = executor;
        this.associatedPrefix = associatedPrefix;
        this.description = description;
    }


    public final CommandExecutor getExecutor() {
        return this.executor;
    }

    public final String getCommand() {
        return this.command;
    }

    public DiscordApi getApi() {
        return this.api;
    }

    public final String getPrefix() {
        return this.associatedPrefix;
    }

    public final String getDescription() {
        return this.description;
    }
}
