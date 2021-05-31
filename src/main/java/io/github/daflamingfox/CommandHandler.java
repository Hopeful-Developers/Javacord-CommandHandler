package io.github.daflamingfox;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandHandler implements MessageCreateListener {

    private final Command command;
    private final ArrayList<Command> commands;
    private MessageCreateEvent event;

    /**
     * Creates a commandHandler object.
     * @param command The command to be handled.
     * @param commands The ArrayList<Command> of all commands.
     * @author Aurel Ballin
     */
    public CommandHandler(Command command, ArrayList<Command> commands) {
        this.command = command;
        this.commands = commands;
    }

     /**
     * This method is called every time a message is created.
     * @param event The event that occurs each time.
     * @see {@link MessageCreateListener}
     * @author Aurel Ballin
     * @author Jeffrey Morris
     */
    public void onMessageCreate(MessageCreateEvent event) {

        // ignore bot
        if (event.getMessageAuthor().isBotUser())
            return;

        this.event = event;
        CommandData data = new CommandData(event.getApi(), event, this, this.command);
        CommandMessage message = new CommandMessage(event.getMessageContent(), this.command.getPrefix());

        boolean containsAlias = false;

        // check for alias
        if (this.command.getAliases() != null) { // check that there are aliases available
            if (Arrays.stream(this.command.getAliases()).anyMatch(s -> s.equalsIgnoreCase(message.getCaller()))) { // checks if any are equal
                containsAlias = true;
            }
        }

        if (message.getCaller().equalsIgnoreCase(this.command.getCommandInitiator()) || containsAlias) { // check if the caller is equal to the 
            if (event.getMessageContent().startsWith(message.getPrefix())) {
                this.command.getExecutor().execute(data, this.commands);
            }
        }
    }

    /**
     * 
     * @return The MessageCreateEvent.
     * @author Aurel Ballin
     */
    public final MessageCreateEvent getEvent() {
        return this.event;
    }
}
