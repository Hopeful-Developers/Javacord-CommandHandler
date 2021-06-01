package io.github.daflamingfox;

import org.javacord.api.DiscordApi;

import java.util.ArrayList;

/**
 * @author Aurel Ballin
 * @author Jeffrey Morris
 */
public class CommandBuilder {

    private final ArrayList<Command> commands = new ArrayList<>();
    private final String prefix;
    private final DiscordApi api;

    /**
     * Creates a new CommandBuilder Object, allows for setting the prefix.
     * @param prefix The prefix that will be used when activating commands.
     * @param api Your bots DiscordApi.
     * @author Aurel Ballin
     */
    public CommandBuilder(String prefix, DiscordApi api) {
        this.prefix = prefix;
        this.api = api;
    }

    /**
     * Creates a new CommandBuilder Object, sets the prefix to {@code !}.
     * @param api Your bots DiscordApi.
     * @author Aurel Ballin
     */
    public CommandBuilder(DiscordApi api) {
        this.prefix = "!";
        this.api = api;
    }

    /**
     * Allows for easily making and adding a new command to the builder.
     * @param commandInitiator The keyword to initiate the command, what will be used to call the command. <blockquote><pre> "ping" </pre></blockquote>
     * @param aliases A static array of other names that can be used to call the command. <blockquote><pre> new String[] {"delay", "beep", "time"} </pre></blockquote>
     * @param executor The IExecutor that will be executed when the command is called. <blockquote><pre> new Ping() </pre></blockquote>
     * @param description A description of what the command does. If set to null, the description will be set to {@code No description} <blockquote><pre> "shows the bots current delay in ms." </pre></blockquote>
     * @param usage An example of how to use the command, in general {@code &lt;&gt;} mean a required item, {@code []} means an optional item, and {@code ()} is info. If set to null, the usage will be set to {@code No usage} <blockquote><pre> "-tip &lt;@user&gt; [amount (default 1)]" </pre></blockquote>
     * @return itself so that commands can be stacked or built off of one line.
     * @author Aurel Ballin
     * @see io.github.daflamingfox.IExecutor
     */
    public final CommandBuilder addCommand(String commandInitiator, String[] aliases, IExecutor executor, String description, String usage) {
        Command command = new Command(commandInitiator, aliases, executor, this.api, this.prefix, description == null ? "No description" : description, usage == null ? "No usage" : usage);
        this.commands.add(command);
        return this;
    }

    /**
     * A less simple way to add a command to the builder, but more powerful.
     * @param command the Command to add to the builder
     * @return itself so that commands can be stacked or built off of one line.
     * @author Jeffrey Morris
     * @see io.github.daflamingfox.Command
     */
    public final CommandBuilder addCommand(Command command) {
        this.commands.add(command);
        return this;
    }

    /**
     * creates a new messageCreateListener for each command.
     * @author Aurel Ballin
     */
    public final void build() {
        this.commands.forEach(command -> this.api.addMessageCreateListener(new CommandHandler(command, this.commands)));
    }

}