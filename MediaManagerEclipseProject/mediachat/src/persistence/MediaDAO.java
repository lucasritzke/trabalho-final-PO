
package persistence;

import java.io.*;
import java.util.*;
import model.Media;

/**
 * MediaDAO - responsável por persistir cada mídia em um arquivo .tpoo individual
 * e por recuperar todos os arquivos .tpoo de um diretório.
 */
public class MediaDAO {
    private final File folder;

    public MediaDAO(String folderPath) {
        this.folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();
    }

    public void saveMedia(Media media) throws IOException {
        // nome do arquivo: titulo_sanitizado.tpoo
        String safe = media.getTitle().replaceAll("[^a-zA-Z0-9_\-]", "_");
        File out = new File(folder, safe + ".tpoo");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(out))) {
            oos.writeObject(media);
        }
    }

    public void deleteMedia(Media media) {
        String safe = media.getTitle().replaceAll("[^a-zA-Z0-9_\-]", "_");
        File out = new File(folder, safe + ".tpoo");
        if (out.exists()) out.delete();
    }

    public List<Media> loadAll() {
        List<Media> list = new ArrayList<>();
        File[] files = folder.listFiles((d, name) -> name.toLowerCase().endsWith(".tpoo"));
        if (files == null) return list;
        for (File f : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object obj = ois.readObject();
                if (obj instanceof Media) list.add((Media) obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
