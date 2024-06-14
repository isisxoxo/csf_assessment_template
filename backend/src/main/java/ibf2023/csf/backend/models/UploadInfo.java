package ibf2023.csf.backend.models;

public class UploadInfo {
    private Long fileSize;
    private String url;

    public UploadInfo(Long fileSize, String url) {
        this.fileSize = fileSize;
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
