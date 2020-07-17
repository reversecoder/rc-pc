package com.rc.designpattern.pattern.behavioural.command;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CommandExecutor {

    private static CommandStack commandStack;
    private static CommandExecutor commandExecutor;
    private static final int DEFAULT_UNDO_BUFFER_SIZE = 20;

    public static CommandExecutor getInstance(){
        if(commandExecutor == null){
            commandExecutor = new CommandExecutor(DEFAULT_UNDO_BUFFER_SIZE);
        }
        return commandExecutor;
    }

    private CommandExecutor(int undoBufferSize) {
        commandStack = new CommandStack(undoBufferSize);
    }

    public void executeCommand(Command cmd) {
        commandStack.push(cmd);
        cmd.doIt();
    }

    public Command undoLastCommand() {
        Command cmd = commandStack.getLastCommand();
        if (cmd != null) {
            cmd.undoIt();
        }
        return cmd;
    }

    public Command redoLastUndoedCommand() {
        Command cmd = commandStack.recoverLastGettedCommand();
        if (cmd != null) {
            cmd.doIt();
        }
        return cmd;
    }
}