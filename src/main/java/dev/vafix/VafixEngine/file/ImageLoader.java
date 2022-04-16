package dev.vafix.VafixEngine.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;
import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.stb.STBImage.stbi_load;

public class ImageLoader {
    static Logger logger = LogManager.getLogger(ImageLoader.class);

    public ByteBuffer getIMAGE() {
        return IMAGE;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    private final ByteBuffer IMAGE;
    private final int WIDTH, HEIGHT;

    ImageLoader(int width, int height, ByteBuffer image) {
        this.IMAGE = image;
        this.HEIGHT = height;
        this.WIDTH = width;
    }
    public static ImageLoader loadImage(String path) {
        ByteBuffer image;
        int width, height;
        URL url = ImageLoader.class.getClassLoader().getResource(path);

        if (url == null || !(new File(url.getPath()).isFile())){
            logger.error( "\"" + path + "\" is an invalid file path");
            path = Objects.requireNonNull(ImageLoader.class.getClassLoader().getResource("assets/missing-texture.png")).getPath();
        } else {
            path = url.getPath();
        }

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            path = path.substring(1);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
               throw new RuntimeException("Could not load image resources.");
            }

            width = w.get();
            height = h.get();
        }
        return new ImageLoader(width, height, image);
    }
}
