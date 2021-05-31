package io.github.daflamingfox;

import java.util.ArrayList;

public interface IExecutor {

    /**
     * 
     * @param data The data collected from CreateMessageEvent.
     * @param commands the ArrayList<Command> of commands, which can be used for a help command.
     */
    void execute(final CommandData data, ArrayList<Command> commands);
    
}