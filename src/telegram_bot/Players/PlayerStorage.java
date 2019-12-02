package telegram_bot.Players;

import telegram_bot.bot.ChatId;

import java.util.HashMap;

public class PlayerStorage {
    private HashMap<ChatId, Player> players;

    public PlayerStorage() {
        players = new HashMap<>();
    }

    public void addPlayer(ChatId chatId, Player player) {
        players.put(chatId, player);
    }

    public boolean containsPlayer(ChatId chatId) {
        return players.containsKey(chatId);
    }

    public void deletePlayer(ChatId chatId) {
        players.remove(chatId);
    }

    public Player getPlayer(ChatId chatId) {
        return players.get(chatId);
    }
}
