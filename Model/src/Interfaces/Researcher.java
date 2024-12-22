package src.Interfaces;

import java.util.*;

import src.Enums.ResearchSortCriteria;
import src.Stuff.*;

public interface Researcher {
    void addResearchPaper(ResearchPaper paper);

    List<ResearchPaper> getResearchPapers();

    void addResearchProject(ResearchProject paper);

    List<ResearchProject> getResearchProjects();

    List<ResearchPaper> printPapers(ResearchSortCriteria criteria);

    int calculateHIndex();
}