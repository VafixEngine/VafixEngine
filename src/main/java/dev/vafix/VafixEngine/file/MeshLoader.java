package dev.vafix.VafixEngine.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MeshLoader {
    private static final Logger logger = LogManager.getLogger(MeshLoader.class);

    /*
    public static Mesh load(String path){
        URL url = TextLoader.class.getClassLoader().getResource(path);

        if (url == null) {
            logger.error("File not found: " + path);
            return null;
        }

        try {
            InputStream inputStream = new FileInputStream(new File(url.toURI()));
            Obj data = ObjUtils.convertToRenderable(ObjReader.read(inputStream));
            return new Mesh(ObjData.getFaceVertexIndices(data), ObjData.getVertices(data), ObjData.getTexCoords(data, 2), ObjData.getNormals(data),ObjData.getTotalNumFaceVertices(data));
        } catch (URISyntaxException | IOException e) {
            logger.error("Failed to load file: " + path);
            return null;
        }
    }
    */
}
