package persistence;

import java.io.*;
import java.util.*;
import model.Media;

public class MediaDAO {

    private final File folder;

    public MediaDAO(String folderPath) {
        this.folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void saveMedia(Media media) throws IOException {
        String safeName = media.getTitle().replaceAll("[^a-zA-Z0-9_\\-]", "_");
        File outFile = new File(folder, safeName + ".tpoo");

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(outFile))) {
            output.writeObject(media);
        }
    }

    public void deleteMedia(Media media) {
        String safeName = media.getTitle().replaceAll("[^a-zA-Z0-9_\\-]", "_");
        File outFile = new File(folder, safeName + ".tpoo");

        if (outFile.exists()) {
            outFile.delete();
        }
    }

    public List<Media> loadAll() {
        List<Media> items = new ArrayList<>();

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".tpoo"));
        if (files == null) {
            return items;
        }

        for (File file : files) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = input.readObject();
                if (obj instanceof Media) {
                    items.add((Media) obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return items;
    }
}
