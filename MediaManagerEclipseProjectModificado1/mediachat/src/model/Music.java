package model;

public class Music extends Media {

    private static final long serialVersionUID = 1L;

    private int durationSeconds;
    private String artist;

    public Music(String localPath, long sizeBytes, String title, String category, int durationSeconds, String artist) {
        super(localPath, sizeBytes, title, category);
        this.durationSeconds = durationSeconds;
        this.artist = artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public long getDurationValue() {
        return durationSeconds;
    }

    @Override
    public String displaySpecificAttributes() {
        return String.format("Duração: %d segundos; Artista: %s", durationSeconds, artist);
    }
}
