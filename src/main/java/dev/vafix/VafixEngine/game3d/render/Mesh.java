package dev.vafix.VafixEngine.game3d.render;

import dev.vafix.VafixEngine.physics.math.Matrix4d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

//As explained by rendering documentation
public class Mesh{
    private final int VAO_ID;
    private final int VBO_ID;
    private final int IBO_ID;

    private IntBuffer indices;

    private FloatBuffer verticies;
    private int vertexCount;

    private FloatBuffer uvs; // TODO

    private FloatBuffer normals; // TODO

    public Mesh(int[] ind, Vertex[] vrt) {
        // Allocate the positions into the buffer
        verticies = MemoryUtil.memAllocFloat(vrt.length * 3);
        float[] vertData = new float[vrt.length * 3];
        vertexCount = vrt.length;

        for(int i = 0; i < vrt.length; i++){
            vertData[i*3] = (float)vrt[i].getPosition().getX();
            vertData[i*3+1] = (float)vrt[i].getPosition().getY();
            vertData[i*3+2] = (float)vrt[i].getPosition().getZ();
        }
        verticies.put(vertData).flip();

        // Allocate indices into a buffer
        indices = MemoryUtil.memAllocInt(ind.length);
        indices.put(ind).flip();

        // Generate IDs
        VAO_ID = GL30.glGenVertexArrays();
        VBO_ID = GL15.glGenBuffers();
        IBO_ID = GL15.glGenBuffers();
    }

    public void bind(){
        // Bind verts
        GL30.glBindVertexArray(VAO_ID);

        // Bind buffers
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO_ID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticies, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER , IBO_ID);

        // Store data in attrib list
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
    }

    public void unbind() {
        // Unbind the VBO & VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void draw(Matrix4d worldTranslation){
        GL30.glBindVertexArray(VAO_ID);
        GL30.glEnableVertexAttribArray(0);

        GL30.glDrawArrays(GL15.GL_TRIANGLES, 0, vertexCount);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);

        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void cleanUp(){
        GL20.glDisableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(VBO_ID);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO_ID);

        cleanMem();
    }

    private void cleanMem(){
        // Verticies buffer clean
        if(verticies != null){
            MemoryUtil.memFree(verticies);
        }

        // Indices buffer clean
        if(indices != null){
            MemoryUtil.memFree(indices);
        }
    }
}
