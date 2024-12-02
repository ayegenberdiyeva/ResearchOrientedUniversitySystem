import Enums.ResearcherPaperFormat;

import java.util.*;

public class ResearchPaper {
    public ResearchPaper() {
    }

    private String title;
    private String author;
    private int citations;
    private int pages;
    private Date publicationDate;
    private String doi;
    private ResearcherPaperFormat format;

    public String getCitation(ResearcherPaperFormat format) {
        // TODO implement here
        return "";
    }
}