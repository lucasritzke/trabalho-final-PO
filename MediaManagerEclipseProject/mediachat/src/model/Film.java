
package model;

/**
 * Classe Filme - duração em minutos e idioma do áudio.
 */
public class Film extends Media {
    private static final long serialVersionUID = 1L;
    private int durationMinutes;
    private String audioLanguage;

    public Film(String localPath, long sizeBytes, String title, String category, int durationMinutes, String audioLanguage) {
        super(localPath, sizeBytes, title, category);
        this.durationMinutes = durationMinutes;
        this.audioLanguage = audioLanguage;
    }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getAudioLanguage() { return audioLanguage; }
    public void setAudioLanguage(String audioLanguage) { this.audioLanguage = audioLanguage; }

    @Override
    public long getDurationValue() { return durationMinutes; }

    @Override
    public String displaySpecificAttributes() {
        return String.format("Duração: %d minutos; Idioma: %s", durationMinutes, audioLanguage);
    }
}
