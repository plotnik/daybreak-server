package io.plotnik.daybreak_server.model;

import java.util.List;

public class PoemCollection {
    private String sectionTitle;
    private List<Poem> poems;

    public PoemCollection(String sectionTitle, List<Poem> poems) {
        this.sectionTitle = sectionTitle;
        this.poems = poems;
    }

    // Getters and Setters
    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<Poem> getPoems() {
        return poems;
    }

    public void setPoems(List<Poem> poems) {
        this.poems = poems;
    }
}
