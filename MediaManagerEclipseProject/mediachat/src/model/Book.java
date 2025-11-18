
package model;

import java.util.List;

/**
 * Classe Book - duração em páginas e autores.
 */
public class Book extends Media {
    private static final long serialVersionUID = 1L;
    private int pages;
    private String authors; // lista simplificada

    public Book(String localPath, long sizeBytes, String title, String category, int pages, String authors) {
        super(localPath, sizeBytes, title, category);
        this.pages = pages;
        this.authors = authors;
    }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }

    @Override
    public long getDurationValue() { return pages; }

    @Override
    public String displaySpecificAttributes() {
        return String.format("Páginas: %d; Autores: %s", pages, authors);
    }
}
