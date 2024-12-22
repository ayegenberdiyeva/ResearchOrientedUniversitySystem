package src.Stuff;

import src.Utils.IdGenerator;
import src.Utils.LanguageManager;
import src.Enums.MessageType;
import src.Enums.UrgencyLevel;

import java.util.Date;
import java.io.Serializable;

public class Message implements Serializable {
    public Message(String senderId, String receiverId, String content, MessageType type, UrgencyLevel urgency) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.type = type;
        this.urgencyLevel = urgency;
        this.isRead = false;
        this.id = IdGenerator.generateID("MSG-");
    }

    private String id;
    private String senderId;
    private String receiverId;
    private String content;
    private Date sentDate;
    private boolean isRead;
    private MessageType type;
    private UrgencyLevel urgencyLevel;

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

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
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