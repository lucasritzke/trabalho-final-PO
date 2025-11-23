package controller;

import java.io.IOException;
import java.util.*;
import model.Media;
import model.Film;
import model.Music;
import model.Book;
import persistence.MediaDAO;

public class MediaController {

    private List<Media> medias;
    private MediaDAO dao;

    public MediaController(String storageFolder) {
        this.dao = new MediaDAO(storageFolder);
        this.medias = dao.loadAll();
    }

    public void addMedia(Media media) throws IOException {
        medias.add(media);
        dao.saveMedia(media);
    }

    public void removeMedia(Media media) {
        medias.remove(media);
        dao.deleteMedia(media);
    }

    public List<Media> listByType(Class<?> type) {
        List<Media> filtered = new ArrayList<>();
        for (Media media : medias) {
            if (type.isInstance(media)) filtered.add(media);
        }
        return filtered;
    }

    public List<Media> listByCategory(String category) {
        List<Media> filtered = new ArrayList<>();
        for (Media media : medias) {
            if (media.getCategory().equalsIgnoreCase(category)) filtered.add(media);
        }
        return filtered;
    }

    public List<Media> listAll() {
        return new ArrayList<>(medias);
    }

    public void editMedia(Media original, Media updated) throws IOException {
        medias.remove(original);
        dao.deleteMedia(original);
        medias.add(updated);
        dao.saveMedia(updated);
    }

    public void moveMedia(Media media, String newPath) throws IOException {
        media.setLocalPath(newPath);
        dao.saveMedia(media);
    }

    public void renameMedia(Media media, String newTitle) throws IOException {
        dao.deleteMedia(media);
        media.setTitle(newTitle);
        dao.saveMedia(media);
    }

    public List<Media> sortByTitle() {
        List<Media> sorted = listAll();
        Collections.sort(sorted);
        return sorted;
    }

    public List<Media> sortByDuration() {
        List<Media> sorted = listAll();
        sorted.sort(Comparator.comparingLong(Media::getDurationValue));
        return sorted;
    }
}
