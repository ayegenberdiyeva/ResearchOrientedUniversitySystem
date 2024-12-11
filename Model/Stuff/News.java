package Stuff;

import Enums.UrgencyLevel;
import Users.User;
import Utils.IdGenerator;
import Utils.LanguageManager;

import java.util.*;
import java.util.Date;
import java.io.Serializable;

public class News {
    public News(String topic, String title, String content, String senderId) {
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.senderId = senderId;
        this.date = new Date();
        this.id = IdGenerator.generateID("NEW-");
        this.isPinned = false;
        this.comments = new HashMap<>();
    }

    private String id;
    private String topic;
    private String title;
    private String content;
    private String senderId;
    private Date date;
    private boolean isPinned;
    private UrgencyLevel priority = UrgencyLevel.LOW;
    private Map<User, String> comments;

    public void prioritize(UrgencyLevel priority) {
        if (priority == UrgencyLevel.LOW) this.priority = UrgencyLevel.MEDIUM;
        else if (priority == UrgencyLevel.MEDIUM) this.priority = UrgencyLevel.HIGH;
    }

    public void unpin() {
        this.isPinned = false;
    }

    public void addComment(User user, String comment) {
        if (user == null || comment == null || comment.trim().isEmpty()){
            throw new IllegalArgumentException(LanguageManager.getMessage("comment_empty_params"));
        }
        comments.put(user, comment);
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public UrgencyLevel getPriority() {
        return priority;
    }

    public void setPriority(UrgencyLevel priority) {
        this.priority = priority;
    }

    public Map<User, String> getComments() {
        return comments;
    }

    public void setComments(Map<User, String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", topic='").append(topic).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", senderId='").append(senderId).append('\'');
        sb.append(", date=").append(date);
        sb.append(", isPinned=").append(isPinned);
        sb.append(", priority=").append(priority);
        sb.append(", comments=").append(comments);
        sb.append('}');
        return sb.toString();
    }
}
