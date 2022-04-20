package dev.vafix.VafixEngine.game3d.render;

import org.lwjgl.opengl.GL20;

import java.util.HashMap;
import java.util.Map;

public class Shader {
    private final int PROGRAM_ID;
    private int vertexID, fragmentID;

    private String vertexCode, fragmentCode;

    private final Map<String, Integer> uniforms;

    public Shader(String vertPath, String fragPath) throws Exception{
        PROGRAM_ID = GL20.glCreateProgram();
        if(PROGRAM_ID == 0){
            throw new Exception("Shader could not be created");
        }

        uniforms = new HashMap<>();
        // TODO: Load vertex and fragment shader files
    }

    public void createVertexShader(String code) throws Exception{
        vertexID = createShader(code, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String code) throws Exception{
        fragmentID = createShader(code, GL20.GL_FRAGMENT_SHADER);
    }

    private int createShader(String code, int type) throws Exception{
        int shaderID = GL20.glCreateShader(type);
        if(shaderID == 0){
            throw new Exception("Couldn't create shader type: " + type);
        }

        GL20.glShaderSource(shaderID, code);
        GL20.glCompileShader(shaderID);

        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0){
            throw new Exception("Shader code with shader type: " + type + "\n" + GL20.glGetShaderInfoLog(shaderID, 1024));
        }

        GL20.glAttachShader(PROGRAM_ID, shaderID);

        return shaderID;
    }

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

    public void bind(){
        GL20.glUseProgram(PROGRAM_ID);
    }

    public void unbind(){
        GL20.glUseProgram(0);
    }

    public void cleanUp(){
        unbind();

        if(PROGRAM_ID != 0){
            GL20.glDeleteProgram(PROGRAM_ID);
        }
    }
}
