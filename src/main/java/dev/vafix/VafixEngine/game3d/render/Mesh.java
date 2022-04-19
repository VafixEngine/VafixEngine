package dev.vafix.VafixEngine.game3d.render;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/// As explained by rendering documentation
public class Mesh{
    private final int VAO_ID;
    private final int VBO_ID;

    private IntBuffer indices; // TODO

    private FloatBuffer verticies;
    private int vertexCount;

    private FloatBuffer uvs; // TODO

    private FloatBuffer normals; // TODO

    public Mesh(int[] ind, float[] vrt) {
        // Allocate the positions into the buffer
        verticies = MemoryUtil.memAllocFloat(vrt.length);
        verticies.put(vrt).flip();

        vertexCount = vrt.length / 3;

        // Generate VAO and VBO IDs
        VAO_ID = GL30.glGenVertexArrays();
        VBO_ID = GL30.glGenBuffers();

        // Automatically bind?
        bind();
    }

    public void bind(){
        // Bind verts
        GL30.glBindVertexArray(VAO_ID);

        // Bind buffers
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO_ID);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, verticies, GL15.GL_STATIC_DRAW);
        //MemoryUtil.memFree(verticies); //showed in documentation here, but also used later

        // Store data in attrib list
        GL20.glVertexAttribPointer(0, 3, GL15.GL_FLOAT, false, 0, 0);

        // Unbind the VBO & VAO
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        // Free the off-heap memory used by memutil: Java's garbage collector won't do this
        if(verticies != null){
            MemoryUtil.memFree(verticies);
        }
    }

    public void draw(){ // TODO: Pass in worldTranslation (Matrix4f)?
        GL30.glBindVertexArray(VAO_ID);
        GL30.glEnableVertexAttribArray(0);

        GL30.glDrawArrays(GL15.GL_TRIANGLES, 0, vertexCount);

        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void cleanUp(){
        GL20.glDisableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(VBO_ID);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO_ID);
    }
}
