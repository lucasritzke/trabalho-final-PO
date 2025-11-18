
package controller;

import java.io.IOException;
import java.util.*;
import model.Media;
import model.Film;
import model.Music;
import model.Book;
import persistence.MediaDAO;

/**
 * Controlador principal (camada de negócios). Implementa operações solicitadas no enunciado.
 */
public class MediaController {
    private List<Media> medias;
    private MediaDAO dao;

    public MediaController(String storageFolder) {
        this.dao = new MediaDAO(storageFolder);
        this.medias = dao.loadAll();
    }

    public void addMedia(Media m) throws IOException {
        medias.add(m);
        dao.saveMedia(m);
    }

    public void removeMedia(Media m) {
        medias.remove(m);
        dao.deleteMedia(m);
    }

    public List<Media> listByType(Class<?> cls) {
        List<Media> out = new ArrayList<>();
        for (Media m : medias) if (cls.isInstance(m)) out.add(m);
        return out;
    }

    public List<Media> listByCategory(String category) {
        List<Media> out = new ArrayList<>();
        for (Media m : medias) if (m.getCategory().equalsIgnoreCase(category)) out.add(m);
        return out;
    }

    public List<Media> listAll() {
        return new ArrayList<>(medias);
    }

    public void editMedia(Media original, Media updated) throws IOException {
        // replace in memory and re-save (delete old file if title changed)
        medias.remove(original);
        dao.deleteMedia(original);
        medias.add(updated);
        dao.saveMedia(updated);
    }

    public void moveMedia(Media m, String newPath) throws IOException {
        m.setLocalPath(newPath);
        dao.saveMedia(m);
    }

    public void renameMedia(Media m, String newTitle) throws IOException {
        dao.deleteMedia(m);
        m.setTitle(newTitle);
        dao.saveMedia(m);
    }

    public List<Media> sortByTitle() {
        List<Media> out = listAll();
        Collections.sort(out);
        return out;
    }

    public List<Media> sortByDuration() {
        List<Media> out = listAll();
        out.sort(Comparator.comparingLong(Media::getDurationValue));
        return out;
    }
}
