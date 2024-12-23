package src.Stuff;

import src.Utils.IdGenerator;
import src.Utils.LanguageManager;
import src.Enums.MessageType;
import src.Enums.UrgencyLevel;

import java.util.Date;
import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    public Message(UUID senderId, UUID receiverId, String content, MessageType type, UrgencyLevel urgency) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.type = type;
        this.urgencyLevel = urgency;
        this.isRead = false;
        this.id = generateId();
    }

    private UUID id;
    private UUID senderId;
    private UUID receiverId;
    private String content;
    private Date sentDate;
    private boolean isRead;
    private MessageType type;
    private UrgencyLevel urgencyLevel;

    private UUID generateId(){
        return UUID.randomUUID();
    }

    public void send() {
        this.sentDate = new Date();
        System.out.println(LanguageManager.getMessage("message_sent", this.id, this.senderId, this.receiverId, this.sentDate));
    }

    public void markAsRead() {
        if (!this.isRead) {
            this.isRead = true;
            System.out.println(LanguageManager.getMessage("message_read", this.id));
        } else {
            System.out.println(LanguageManager.getMessage("message_been_read", this.id));
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", senderId='").append(senderId).append('\'');
        sb.append(", receiverId='").append(receiverId).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", sentDate=").append(sentDate);
        sb.append(", isRead=").append(isRead);
        sb.append(", type=").append(type);
        sb.append(", urgencyLevel=").append(urgencyLevel);
        sb.append('}');
        return sb.toString();
    }
}