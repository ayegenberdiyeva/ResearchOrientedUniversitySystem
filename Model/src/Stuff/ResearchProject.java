package src.Stuff;

import src.Interfaces.Researcher;
import src.Utils.IdGenerator;
import src.Utils.LanguageManager;

import java.util.*;

public class ResearchProject {
    public ResearchProject(String topic) {
        this.topic = topic;
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.id = IdGenerator.generateID("RES-PR-");
    }

    private String id;
    private String topic;
    private List<ResearchPaper> publishedPapers;
    private List<Researcher> participants;

    public void addParticipant(Researcher researcher) {
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void removeParticipant(Researcher researcher) {
        if (participants.contains(researcher)) {
            participants.remove(researcher);
        }
    }

    public void addResearcherPaper(ResearchPaper paper) {
        if (paper == null) {
            throw new IllegalArgumentException(LanguageManager.getMessage("invalid_research_project"));
        }
        if (!publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
            System.out.println(LanguageManager.getMessage("paper_added", paper.getTitle(), topic));
        } else {
            System.out.println(LanguageManager.getMessage("paper_already_added", paper.getTitle(), topic));
        }
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

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public void setPublishedPapers(List<ResearchPaper> publishedPapers) {
        this.publishedPapers = publishedPapers;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Researcher> participants) {
        this.participants = participants;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", topic='").append(topic).append('\'');
        sb.append(", publishedPapers=").append(publishedPapers);
        sb.append(", participants=").append(participants);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}