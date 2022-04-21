package dev.vafix.VafixEngine.game3d.render;

import dev.vafix.VafixEngine.physics.Vertex;
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

    private FloatBuffer uvs;

    private FloatBuffer normals;

    public Mesh(int[] _ind, Vertex[] _vrt, float[] _uv, float[] _norm) {
        // Allocate the positions into the buffer
        verticies = MemoryUtil.memAllocFloat(_vrt.length * 3);
        float[] vertData = new float[_vrt.length * 3];
        vertexCount = _vrt.length;

        for(int i = 0; i < _vrt.length; i++){
            vertData[i*3] = (float)_vrt[i].getPosition().getX();
            vertData[i*3+1] = (float)_vrt[i].getPosition().getY();
            vertData[i*3+2] = (float)_vrt[i].getPosition().getZ();
        }
        verticies.put(vertData).flip();

        // Allocate indices into a buffer
        indices = MemoryUtil.memAllocInt(_ind.length);
        indices.put(_ind).flip();

        // Allocate uvs into the buffer
        uvs = MemoryUtil.memAllocFloat(_uv.length);
        uvs.put(_uv).flip();

        // Allocate normals into buffer
        normals = MemoryUtil.memAllocFloat(_norm.length);
        normals.put(_norm).flip();

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
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);

        GL30.glDrawArrays(GL15.GL_TRIANGLES, 0, vertexCount);
        GL30.glDrawElements(GL15.GL_TRIANGLES, vertexCount, GL15.GL_UNSIGNED_INT, 0);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        GL20.glBindTexture(GL15.GL_TEXTURE_2D, 0);
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

        if(uvs != null){
            MemoryUtil.memFree(uvs);
        }

        if(normals != null){
            MemoryUtil.memFree(normals);
        }
    }
}
