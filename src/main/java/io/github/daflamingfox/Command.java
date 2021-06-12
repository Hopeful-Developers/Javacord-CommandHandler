package io.github.daflamingfox;

import org.javacord.api.DiscordApi;

/**
 * 
 * @author Aurel Ballin
 * @author Jeffrey Morris
 */
public class Command {

    private final String commandInitiator;
    private final IExecutor executor;
    private final DiscordApi api;
    private final String prefix;
    private final String description;
    private final String usage;
    private final String[] aliases;

    /**
     * Creates a new Command object.
     * @param commandInitiator The keyword to initiate the command, what will be used to call the command. <blockquote><pre> "ping" </pre></blockquote>
     * @param aliases A static array of other names that can be used to call the command. <blockquote><pre> new String[] {"delay", "beep", "time"} </pre></blockquote>
     * @param executor The IExecutor that will be executed when the command is called. <blockquote><pre> new Ping() </pre></blockquote>
     * @param api Your bots DiscordApi. 
     * @param prefix The prefix that will come before the initiator to call the command <blockquote><pre> "-" </pre></blockquote>
     * @param description A description of what the command does. <blockquote><pre> "shows the bots current delay in ms." </pre></blockquote>
     * @param usage An example of how to use the command, in general {@code &lt;&gt;} mean a required item, {@code []} means an optional item, and {@code ()} is info. <blockquote><pre> "-tip &lt;@user&gt; [amount (default 1)]" </pre></blockquote>
     * @author Aurel Ballin
     * @see io.github.daflamingfox.IExecutor
     */
    public Command(final String commandInitiator, final String[] aliases, final IExecutor executor, final DiscordApi api, final String prefix, final String description, final String usage) {
        this.api = api;
        this.commandInitiator = commandInitiator;
        this.executor = executor;
        this.prefix = prefix;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    /**
     * 
     * @return The IExecutor.
     * @author Aurel Ballin
     */
    public final IExecutor getExecutor() {
        return this.executor;
    }

    /**
     * 
     * @return The commandInitiator.
     * @author Aurel Ballin
     */
    public final String getCommandInitiator() {
        return this.commandInitiator;
    }

    /**
     * 
     * @return The DiscordApi.
     * @author Aurel Ballin
     */
    public DiscordApi getApi() {
        return this.api;
    }

    /**
     * 
     * @return The prefix.
     * @author Aurel Ballin
     */
    public final String getPrefix() {
        return this.prefix;
    }

    /**
     * 
     * @return The description.
     * @author Aurel Ballin
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * 
     * @return The usage.
     * @author Aurel Ballin
     */
    public final String getUsage() {
        return this.usage;
    }

    /**
     * 
     * @return The static array of aliases
     * @author Aurel Ballin
     */
    public final String[] getAliases() { 
        return this.aliases; 
    }

    @Override
    public final String toString() {
        return getPrefix()+getCommandInitiator();
    }

}

