package Interfaces;

import java.util.*;
import Stuff.*;

public interface Researcher {
    public abstract void addResearchPaper(ResearchPaper paper);

    public abstract List<ResearchPaper> getResearchPapers();

    public abstract int calculateHIndex();

    public abstract List<ResearchPaper> printPapers(Comparator<ResearchPaper> comparatot);

}