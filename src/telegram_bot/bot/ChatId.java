package telegram_bot.bot;

public class ChatId {
    private Long chatId;

    public ChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ChatId someChatId = (ChatId) obj;
        return chatId.equals(someChatId.chatId);
    }

    @Override
    public int hashCode() {
        return chatId.hashCode();
    }
}
