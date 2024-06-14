package ibf2023.csf.backend.models;

public class UploadInfo {
    private long fileSize;
    private String url;

    public UploadInfo(long fileSize, String url) {
        this.fileSize = fileSize;
        this.url = url;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
