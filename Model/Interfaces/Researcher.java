package Interfaces;

import java.util.*;

import Enums.ResearchSortCriteria;
import Stuff.*;

public interface Researcher {
    void addResearchPaper(ResearchPaper paper);

    List<ResearchPaper> getResearchPapers();

    void addResearchProject(ResearchProject paper);

    List<ResearchProject> getResearchProjects();

    List<ResearchPaper> printPapers(ResearchSortCriteria criteria);

    int calculateHIndex();
}