package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.components.api.basic.SharedComponent;
import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;

import java.util.HashMap;

public class Team implements UniqueComponent, SharedComponent {
    private static HashMap<Long, Team> teams = new HashMap<Long, Team>();

    public final long id;

    private Team(long id) {
        this.id = id;
    }

    public static Team getById(long id) {
        if (teams.containsKey(id)) {
            return teams.get(id);
        } else {
            Team newTeam = new Team(id);
            teams.put(id, newTeam);
            return newTeam;
        }
    }
}
