package io.github.daflamingfox;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandMessage {

    private final ArrayList<String> args = new ArrayList<>();
    private final String caller;
    private final String prefix;

    /**
     * Creates a CommandMessage object.
     * splits the message into arguments
     * @param commandMessage The message from the command.
     * @param prefix The prefix from the command
     */
    public CommandMessage(String commandMessage, String prefix) {
        this.prefix = prefix;
        commandMessage = commandMessage.replaceFirst(prefix, "");
        Arrays.stream(commandMessage.split(" ")).iterator().forEachRemaining(args::add);
        caller = args.get(0);
        args.remove(0);
    }

    /**
     * 
     * @return The caller, the prefix and invocator put together.
     * @author Aurel Ballin
     */
    public final String getCaller() {
        return this.caller;
    }

    /**
     * 
     * @return The prefix.
     */
    public final String getPrefix() {
        return this.prefix;
    }

    /**
     * 
     * @param index The arg you want to get.
     * @return The arg at index, index.
     */
    public final String getArg(int index) {
        return this.args.get(index);
    }

    /**
     * 
     * @return The ArrayList<String> of args.
     */
    public final ArrayList<String> getArgs() {
        return this.args;
    }


}
