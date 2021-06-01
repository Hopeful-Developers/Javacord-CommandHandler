package io.github.daflamingfox;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class CommandData {

    public final DiscordApi api;
    private final MessageCreateEvent messageCreateEvent;
    private final CommandHandler handler;
    private final Command command;


    /**
     * Creates a CommandData object.
     * @param api Your bots DiscordApi.
     * @param messageCreateEvent The messageCreateEvent where the command was called.
     * @param handler The CommandHandler.
     * @param command The Command that was called.
     */
    public CommandData(DiscordApi api, MessageCreateEvent messageCreateEvent, CommandHandler handler, Command command) {
        this.api = api;
        this.messageCreateEvent = messageCreateEvent;
        this.handler = handler;
        this.command = command;
    }

    /**
     * 
     * @return The DiscordApi.
     * @author Aurel Ballin
     */
    public final DiscordApi getApi() {
        return this.api;
    }

    /**
     * 
     * @return The Message from the MessageCreateEvent.
     * @author Aurel Ballin
     */
    public final Message getMessage() {
        return this.getEvent().getMessage();
    }

    /**
     * 
     * @return The MessageCreateEvent.
     * @author Aurel Ballin
     */
    public final MessageCreateEvent getEvent() {
        return this.messageCreateEvent;
    }

    /**
     * 
     * @return The User from the MessageAuthor from the MessageCreateEvent.
     * @author Aurel Ballin
     */
    public final User getUser() {
        return this.getMessageAuthor().asUser().get();
    }

    /**
     * 
     * @return The MessageAuthor from the MessageCreateEvent.
     * @author Aurel Ballin
     */
    public final MessageAuthor getMessageAuthor() {
        return this.getEvent().getMessageAuthor();
    }

    /**
     * 
     * @return The CommandHandler.
     * @author Aurel Ballin
     */
    public final CommandHandler getHandler() {
        return this.handler;
    }

    /**
     * 
     * @return A CommandMessage, from the CreateMessageEvent.
     * @author Aurel Ballin
     */
    public final CommandMessage getCommandMessage() {
        return new CommandMessage(this.getEvent().getMessageContent(), this.command.getPrefix());
    }

    /**
     * 
     * @return The ServerTextChannel from the CreateMessageEvent.
     * @author Aurel Ballin
     */
    public final ServerTextChannel getChannel() {
        return this.getEvent().getServerTextChannel().get();
    }

    /**
     * 
     * @param eb an EmbedBuilder to send as a reply to the message that invocated the command. <blockquote><pre> new EmbedBuilder().setTitle("I am responding to your invocation.") </pre></blockquote>
     * @return The Message that is sent by the bot.
     * @author Aurel Ballin
     * @author Jeffrey Morris
     */
    public final Message respond(EmbedBuilder eb) {
        return this.getMessage().reply(eb).join();
    }

    /**
     * 
     * @param message The message to send as a reply to the message that invocated the command. <blockquote><pre> "I am responding to your invocation." </pre></blockquote>
     * @return The Message that is sent by the bot.
     * @author Aurel Ballin
     * @author Jeffrey Morris
     */
    public final Message respond(String message) {
        return this.getMessage().reply(message).join();
    }
}