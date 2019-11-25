package telegram_bot;

import java.util.HashMap;

public class PlayerStorage {
    private HashMap<Long, Player> players;

    public PlayerStorage() {
        players = new HashMap<>();
    }

    public void addPlayer(ChatId chatId, Player player) {
        players.put(chatId.getChatId(), player);
    }

    public boolean containsPlayer(ChatId chatId) {
        return players.containsKey(chatId.getChatId());
    }

    public void deletePlayer(ChatId chatId) {
        players.remove(chatId.getChatId());
    }

    public Player getPlayer(ChatId chatId) {
        return players.get(chatId.getChatId());
    }
}
