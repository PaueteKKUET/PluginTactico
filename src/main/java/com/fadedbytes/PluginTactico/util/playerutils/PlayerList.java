package com.fadedbytes.PluginTactico.util.playerutils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PlayerList implements Iterable<Player> {

    private static final ArrayList<PlayerList> PLAYER_LIST_COLLECTOR = new ArrayList<>();

    private final ArrayList<Player> PLAYERS;
    private final NamespacedKey NAME;

    private PlayerList(NamespacedKey name) {
        this.PLAYERS = new ArrayList<>();
        this.NAME = name;
        PLAYER_LIST_COLLECTOR.add(this);
    }

    public void addPlayer(Player p) {
        if (p == null) throw new IllegalArgumentException("Player added can not be null");
        this.PLAYERS.add(p);
    }

    public void removePlayer(Player p) {
        this.PLAYERS.remove(p);
    }

    public boolean includes(Player p) {
        return this.PLAYERS.contains(p);
    }

    public static PlayerList getOrCreateList(NamespacedKey name) {
        if (name == null)
            throw new IllegalArgumentException("Namespace can not be null");

        PlayerList[] targetList = new PlayerList[] { null };

        PLAYER_LIST_COLLECTOR
                .stream()
                .filter(list -> list.NAME.equals(name))
                .forEach((list) -> targetList[0] = list);
        return targetList[0] == null ? new PlayerList(name) : targetList[0];
    }


    @Override
    public Iterator<Player> iterator() {
        return new PlayerListIterator(this);
    }

    class PlayerListIterator implements Iterator<Player> {

        private int index;
        private final PlayerList list;

        protected PlayerListIterator(PlayerList list) {
            this.index = 0;
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return this.list.PLAYERS.size() > index;
        }

        @Override
        public Player next() {
            this.index++;
            return this.list.PLAYERS.get(index - 1);
        }
    }

}
