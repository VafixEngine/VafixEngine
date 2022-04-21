package dev.vafix.VafixEngine.game3d.render;

import dev.vafix.VafixEngine.physics.math.Matrix4d;
import dev.vafix.VafixEngine.physics.math.Vector2;
import dev.vafix.VafixEngine.physics.math.Vector3;
import dev.vafix.VafixEngine.physics.math.Vector4;

import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class Shader {
    private final int PROGRAM_ID;
    private int vertexID, fragmentID;

    private String vertexCode, fragmentCode;

    private final Map<String, Integer> uniforms;

    /**
     * Create a shader using a vertex and fragment file
     * @param vertPath Path to vertex file
     * @param fragPath Path to fragment file
     * @throws Exception Shader could not be created
     */
    public Shader(String vertPath, String fragPath) throws Exception{
        PROGRAM_ID = GL20.glCreateProgram();
        if(PROGRAM_ID == 0){
            throw new Exception("Shader could not be created");
        }

        uniforms = new HashMap<>();
        // TODO: Load vertex and fragment shader files
    }

    /**
     * Create a new uniform for this shader
     * @param uniformName Name of the uniform
     * @throws Exception Uniform does not exist
     */
    public void createUniform(String uniformName) throws Exception{
        int uniformLocation = GL20.glGetUniformLocation(PROGRAM_ID, uniformName);

        if(uniformLocation < 0){
            throw new Exception("Could not locate uniform");
        }

        uniforms.put(uniformName, uniformLocation);
    }

    /**
     * Set a Matrix4d uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, Matrix4d value){
        GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, matrix4dToFloatBuffer(value));
    }

    /**
     * Converts matrix double to a float buffer
     * @param value Matrix4d
     * @return FloatBuffer containing a flat matrix
     */
    private FloatBuffer matrix4dToFloatBuffer(Matrix4d value){
        FloatBuffer rtn = MemoryUtil.memAllocFloat(16);
        double[] flatMatrix = value.flatten();

        float[] newMatrix = new float[flatMatrix.length];
        for(int i = 0; i < flatMatrix.length; i++){
            newMatrix[i] = (float)flatMatrix[i];
        }

        return rtn.put(newMatrix);
    }

    /**
     * Set a int uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, int value){
        GL20.glUniform1i(uniforms.get(uniformName), value);
    }

    /**
     * Set a float uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, float value){
        GL20.glUniform1f(uniforms.get(uniformName), value);
    }

    /**
     * Set a Vector4 uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, Vector4 value){
        GL20.glUniform4f(uniforms.get(uniformName), (float)value.getX(), (float)value.getY(), (float)value.getZ(), (float)value.getW());
    }

    /**
     * Set a Vector3 uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, Vector3 value){
        GL20.glUniform3f(uniforms.get(uniformName), (float)value.getX(), (float)value.getY(), (float)value.getZ());
    }

    /**
     * Set a Vector2 uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, Vector2 value){
        GL20.glUniform2f(uniforms.get(uniformName), (float)value.getX(), (float)value.getY());
    }

    /**
     * Set a boolean uniform
     * @param uniformName Name of the uniform
     * @param value
     */
    public void setUniform(String uniformName, boolean value){
        float res = 0;
        if(value){
            res = 1;
        }

        GL20.glUniform1f(uniforms.get(uniformName), res);
    }

    /**
     * Creates vertex shader
     * @param code Vertex shader code
     * @throws Exception Couldn't create vertex shader
     */
    public void createVertexShader(String code) throws Exception{
        vertexID = createShader(code, GL20.GL_VERTEX_SHADER);
    }

    /**
     * Creates fragment shader
     * @param code Fragment shader code
     * @throws Exception Couldn't create fragment shader
     */
    public void createFragmentShader(String code) throws Exception{
        fragmentID = createShader(code, GL20.GL_FRAGMENT_SHADER);
    }

    /**
     * Creates a shader using code and returns the ID of the initialized shader
     * @param code Shader code
     * @param type Type of shader (FRAGMENT/VERTEX)
     * @return ID for the created shader
     * @throws Exception Could not create shader
     */
    private int createShader(String code, int type) throws Exception {
        int shaderID = GL20.glCreateShader(type);
        if (shaderID == 0) {
            throw new Exception("Couldn't create shader type: " + type);
        }

        GL20.glShaderSource(shaderID, code);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
            throw new Exception("Shader code with shader type: " + type + "\n" + GL20.glGetShaderInfoLog(shaderID, 1024));
        }

        GL20.glAttachShader(PROGRAM_ID, shaderID);

        return shaderID;
    }

    /**
     * Links the shader to the application
     * @throws Exception Shader code could not link
     */
    public void linkShader() throws Exception{
        GL20.glLinkProgram(PROGRAM_ID);

        if(GL20.glGetProgrami(PROGRAM_ID, GL20.GL_LINK_STATUS) == 0){
            throw new Exception("Shader code could not link.\n" + GL20.glGetProgramInfoLog(PROGRAM_ID, 1024));
        }

        if(vertexID != 0){
            GL20.glDetachShader(PROGRAM_ID, vertexID);
        }

        if(fragmentID != 0){
            GL20.glDetachShader(PROGRAM_ID, fragmentID);
        }

        GL20.glValidateProgram(PROGRAM_ID);
        if(GL20.glGetProgrami(PROGRAM_ID, GL20.GL_VALIDATE_STATUS) == 0){
            throw new Exception("Unable to validate shader code\n" + GL20.glGetProgramInfoLog(PROGRAM_ID, 1024));
        }
    }

    /**
     * Binds shader
     */
    public void bind(){
        GL20.glUseProgram(PROGRAM_ID);
    }

    /**
     * Unbinds shader
     */
    public void unbind(){
        GL20.glUseProgram(0);
    }

    /**
     * Removes shader from the program
     */
    public void cleanUp(){
        unbind();

        if(PROGRAM_ID != 0){
            GL20.glDeleteProgram(PROGRAM_ID);
        }
    }
}
