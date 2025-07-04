package io.plotnik.daybreak_server.model;

public class Poem {
    private String title;
    private String text;

    public Poem(String title, String text) {
        this.title = title;
        this.text = text;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}