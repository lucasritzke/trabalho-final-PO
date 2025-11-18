
package model;

import java.io.Serializable;

/**
 * Classe abstrata Media - representa atributos comuns a todas as mídias.
 * Usa Serializable para persistência em arquivos .tpoo
 */
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

    public String getLocalPath() { return localPath; }
    public void setLocalPath(String localPath) { this.localPath = localPath; }

    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    /** Retorna a duração específica da mídia como número (minutos, segundos ou páginas) */
    public abstract long getDurationValue();

    /** Exibe apenas atributos específicos da mídia */
    public abstract String displaySpecificAttributes();

    @Override
    public int compareTo(Media other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", title, this.getClass().getSimpleName(), category);
    }
}
