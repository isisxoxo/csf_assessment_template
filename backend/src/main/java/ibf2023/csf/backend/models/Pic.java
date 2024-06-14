package ibf2023.csf.backend.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "travelpics")
public class Pic {

    @Id
    private String id;

    private String title;
    private String comments;
    private Date date;
    private String url;
    private long fileSize;

    public Pic() {
    }

    public Pic(String title, String comments, Date date, String url, long fileSize) {
        this.title = title;
        this.comments = comments;
        this.date = date;
        this.url = url;
        this.fileSize = fileSize;
    }

    public Pic(String id, String title, String comments, Date date, String url, long fileSize) {
        this.id = id;
        this.title = title;
        this.comments = comments;
        this.date = date;
        this.url = url;
        this.fileSize = fileSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}
