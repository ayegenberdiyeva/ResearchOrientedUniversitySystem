package src.Stuff;

import src.Enums.ResearcherPaperFormat;
import src.Utils.IdGenerator;
import src.Utils.LanguageManager;

import java.util.*;

public class ResearchPaper {
    public ResearchPaper(String title, String author, int citations, int pages, Date publicationDate, String doi, ResearcherPaperFormat format) {
        this.title = title;
        this.author = author;
        this.citations = citations;
        this.pages = pages;
        this.publicationDate = publicationDate;
        this.doi = doi;
        this.format = format;
        this.id = IdGenerator.generateID("RES-P-");
    }

    public ResearchPaper(){
        this.id = IdGenerator.generateID("RES-P-");
    }

    private String id;
    private String title;
    private String author;
    private int citations;
    private int pages;
    private Date publicationDate;
    private String doi;
    private ResearcherPaperFormat format;

    public String getCitation(ResearcherPaperFormat format) {
        switch (format) {
            case PLAIN_TEXT:
                return String.format(
                        "%s. \"%s.\" %d pages. Published on %s. DOI: %s",
                        author,
                        title,
                        pages,
                        publicationDate.toString(),
                        doi
                );
            case BIBTEX:
                return String.format(
                        "@article{%s,\n author = {%s},\n title = {%s},\n pages = {%d},\n year = {%d},\n doi = {%s}\n",
                        id,
                        author,
                        title,
                        pages,
                        publicationDate.getYear() + 1900,
                        doi
                );
                default:
                    throw new IllegalArgumentException(LanguageManager.getMessage("research_p_format_fl") + format);
        }
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public int getCitations(){
        return citations;
    }

    public void setCitations(int citations){
        this.citations = citations;
    }

    public int getPages(){
        return pages;
    }

    public void setPages(int pages){
        this.pages = pages;
    }

    public Date getPublicationDate(){
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate){
        this.publicationDate = publicationDate;
    }

    public String getDoi(){
        return doi;
    }

    public void setDoi(String doi){
        this.doi = doi;
    }

    public ResearcherPaperFormat getFormat(){
        return format;
    }

    public void setFormat(ResearcherPaperFormat format){
        this.format = format;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", citations=").append(citations);
        sb.append(", pages=").append(pages);
        sb.append(", publicationDate=").append(publicationDate);
        sb.append(", doi='").append(doi).append('\'');
        sb.append(", format=").append(format);
        sb.append('}');
        return sb.toString();
    }
}