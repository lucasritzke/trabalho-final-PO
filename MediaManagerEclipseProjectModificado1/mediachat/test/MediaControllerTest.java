
import org.junit.*;
import static org.junit.Assert.*;
import controller.MediaController;
import model.*;
import java.io.File;
import java.util.List;

public class MediaControllerTest {
    private MediaController controller;
    private static final String STORAGE = "testdata";

    @Before
    public void setUp() {
        // limpar pasta de teste
        File f = new File(STORAGE);
        if (f.exists()) {
            for (File c : f.listFiles()) c.delete();
        } else {
            f.mkdirs();
        }
        controller = new MediaController(STORAGE);
    }

    @Test
    public void testAddAndList() throws Exception {
        Music m = new Music("/tmp/song.mp3", 1234, "MinhaMusica", "Rock", 240, "Artista X");
        controller.addMedia(m);
        List list = controller.listByType(Music.class);
        assertEquals(1, list.size());
    }

    @Test
    public void testEditAndRemove() throws Exception {
        Music m = new Music("/tmp/song.mp3", 1234, "Musica2", "Pop", 200, "Artista Y");
        controller.addMedia(m);
        Music m2 = new Music("/tmp/song.mp3", 1234, "Musica2-Edited", "Pop", 200, "Artista Y");
        controller.editMedia(m, m2);
        assertEquals(1, controller.listByType(Music.class).size());
        controller.removeMedia(m2);
        assertEquals(0, controller.listByType(Music.class).size());
    }
}
