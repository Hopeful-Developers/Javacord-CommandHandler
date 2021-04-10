package me.hopedev.commandhandler;

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


    public CommandData(DiscordApi api, MessageCreateEvent messageCreateEvent, CommandHandler handler, Command command) {
        this.api = api;
        this.messageCreateEvent = messageCreateEvent;
        this.handler = handler;
        this.command = command;
    }


    public final DiscordApi getApi() {
        return this.api;
    }

    public final Message getMessage() { return this.getEvent().getMessage(); }

    public final MessageCreateEvent getEvent() {
        return this.messageCreateEvent;
    }

    public final User getUser() { return this.getMessageAuthor().asUser().get(); }

    public final MessageAuthor getMessageAuthor() {
        return this.getEvent().getMessageAuthor();
    }

    public final CommandHandler getHandler() {
        return this.handler;
    }

    public final CommandMessage getCommandMessage() {
        return new CommandMessage(this.getEvent().getMessageContent(), this.command.getPrefix());
    }

    public final ServerTextChannel getChannel() {
        return this.getEvent().getServerTextChannel().get();
    }


    public final void respond(EmbedBuilder eb) {
        this.getMessage().reply(eb);
    }

    public final void respond(String message) {
        this.getMessage().reply(message);
    }




}
