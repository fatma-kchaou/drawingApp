package com.example.demo1.command;

import java.util.Stack;

public class CmdStack {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /** Push a new command (clears redo history) */
    public void push(Command cmd) {
        undoStack.push(cmd);
        redoStack.clear();
    }

    public Command popUndo() {
        return undoStack.pop();
    }

    public Command popRedo() {
        return redoStack.pop();
    }

    public void pushRedo(Command cmd) {
        redoStack.push(cmd);
    }

    /** After redo, push back to undoStack (not redoStack) */
    public void pushRedo_internal(Command cmd) {
        undoStack.push(cmd);
    }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }

    // Legacy compat
    public Command pop()     { return popUndo(); }
    public boolean isEmpty() { return !canUndo(); }
}