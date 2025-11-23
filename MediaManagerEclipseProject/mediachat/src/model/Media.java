package model;

import java.io.Serializable;

public abstract class Media implements Serializable, Comparable<Media> {

    private static final long serialVersionUID = 1L;

    protected String localPath;
    protected long sizeBytes;
    protected String title;
    protected String category;

    public Media(String localPath, long sizeBytes, String title, String category) {
        this.localPath = localPath;
        this.sizeBytes = sizeBytes;
        this.title = title;
        this.category = category;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public abstract long getDurationValue();

    public abstract String displaySpecificAttributes();

    @Override
    public int compareTo(Media other) {
        return title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", title, getClass().getSimpleName(), category);
    }
}
