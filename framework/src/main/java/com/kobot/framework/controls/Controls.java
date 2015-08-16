package com.kobot.framework.controls;

import com.kobot.framework.Command;
import com.kobot.framework.NullCommand;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;
import org.lwjgl.opengl.Display;

import java.util.HashMap;
import java.util.Map;

public class Controls {
    KeyMapper keyMapper = new KeyMapper();

    Map<Integer, Command> keyBinding = new HashMap<Integer, Command>();
    Map<Integer, Boolean> isPressed = new HashMap<Integer, Boolean>();
    Command mouseOnMoveCommand = new NullCommand();

    boolean shouldQuit = false;

    public boolean shouldQuit() {
        return shouldQuit;
    }

    public void processInput() {
        pollControls();
        executeCommands();
    }

    private void pollControls() {

        KeyState keyState = null;
        while ((keyState = keyMapper.poll()) != KeyState.NONE) {
            isPressed.replace(keyState.getKeyCode(), keyState.getState());
        }

        if (Display.isCloseRequested()) {
            shouldQuit = true;
        }
    }

    private void executeCommands() {
        for (Integer key : keyBinding.keySet()) {
            if (isPressed.get(key)) {
                Command command = keyBinding.get(key);
                command.execute();
            }
        }

        mouseOnMoveCommand.execute();
    }

    public void bindKey(int keyCode, Command command) {
        if (keyBinding.containsKey(keyCode)) {
            keyBinding.replace(keyCode, command);
            isPressed.replace(keyCode, false);
        } else {
            keyBinding.put(keyCode, command);
            isPressed.put(keyCode, false);
        }
    }

    public void bindMouseOnMove(Command command) {
        mouseOnMoveCommand = command;
    }
}
