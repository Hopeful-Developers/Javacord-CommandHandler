package io.github.daflamingfox;

import java.awt.Color;
import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

/**
 * @author Aurel Ballin
 * @author Jeffrey Morris
 */
public class CommandBuilder {

    private final ArrayList<Command> commands = new ArrayList<>();
    private final String prefix;
    private final DiscordApi api;
    private ArrayList<ListenerManager<MessageCreateListener>> listenerManagers = new ArrayList<>();

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
        Command command = new Command(commandInitiator, aliases, executor, this.api, this.prefix, description == null ? "No description" : description, usage == null ? (this.prefix + commandInitiator) : usage);
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
        this.commands.forEach(command -> {
             listenerManagers.add(this.api.addMessageCreateListener(new CommandHandler(command, this.commands)));
        });
    }


    /**
     * Default: False
     * A reload command that can only be called by the bot owner; using {prefix}reload
     * removes the previous CommandHandler; then rebuilds it.
     * @param enable
     */
    public final CommandBuilder enableReloadCommand()  {
        api.addMessageCreateListener(event -> {
            if (event.getMessageAuthor().isBotOwner() && event.getMessageContent().startsWith(this.prefix + "reload")) {
                listenerManagers.forEach(lm -> lm.remove());
                build();
                event.getMessage().reply("Successfully reloaded the CommandHandler.");
            }
        });     
        return this;
    }

    public final CommandBuilder enableDefaultHelpCommand() {
        this.addCommand("help", null, new HelpCmd(), "Show a list of all available commands.", (this.prefix + "help"));
        return this;
    }

    private class HelpCmd implements IExecutor {
        
        private boolean isList; // used for checking if its a list or not;

        public void execute(CommandData data, ArrayList<Command> commands) {
            String cmdList = makeCommandList(commands, data.getCommandMessage().getArgs());

            // Create embed to send
            EmbedBuilder embed = new EmbedBuilder().setTitle("Help").setColor(Color.WHITE).setTimestampToNow();

            // checks if it's a list or specific command.
            if(isList) {
                embed.setDescription("List of all available commands.");
                embed.addField("Command(s):", cmdList, false);
                embed.addField("Need command usage?", "Type `"+prefix+"help[command_name]`.", false);
            } else {
                embed.addField("Usage:", cmdList, false);
                embed.addField("Want to see all commands?", "Type "+prefix+"help", false);
            }

            // send the message
            data.getChannel().sendMessage(embed);
        }

        private String makeCommandList(ArrayList<Command> commands, ArrayList<String> args) {
            String string = "";
            
            // Add all commands to a list.
            for (Command command : commands) {
                // checks for needing help on specific command, if not make list
                if (args.isEmpty()) {
                    string += "`" + command.getPrefix() + command.getCommandInitiator() + "` - " + command.getDescription() + "\n";
                } else if (command.getCommandInitiator().equals(args.get(0))) {
                    isList = false;
                    return "`" + command.getUsage() + "`";
                }
            }
            isList = true;
            return string;
        }
    }
}
