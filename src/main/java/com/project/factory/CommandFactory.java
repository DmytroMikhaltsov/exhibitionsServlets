package com.project.factory;

import com.project.web.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commandMap = new HashMap<>();
    private static Command defaultCommand = new HomeCommand();

    static {
        commandMap.put("login", new LoginCommand());
        commandMap.put("registration", new RegistrationCommand());
        commandMap.put("", new HomeCommand());
        commandMap.put("exhibitions", new ExhibitionCommand());
        commandMap.put("exhibition", new SingleExhibitionCommand());
        commandMap.put("tickets", new TicketsCommand());
        commandMap.put("logout", new LogoutCommand());
    }

    private CommandFactory() {
    }

    public static Command getCommand(String path) {
        return commandMap.getOrDefault(path, defaultCommand);}

}
