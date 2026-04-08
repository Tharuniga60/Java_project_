package app.model;

public class Resource {
    private int id;
    private String title;
    private String description;
    private String url;
    private int authorId;


public Resource() {}


public int getId() { return id; }
public void setId(int id) { this.id = id; }
public String getTitle() { return title; }
public void setTitle(String title) { this.title = title; }
public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }
public String getUrl() { return url; }
public void setUrl(String url) { this.url = url; }
public int getAuthorId() { return authorId; }
public void setAuthorId(int authorId) { this.authorId = authorId; }
}