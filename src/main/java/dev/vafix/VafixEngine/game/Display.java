package dev.vafix.VafixEngine.game;

import dev.vafix.VafixEngine.physics.math.Matrix4d;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public abstract class Display {

    protected int width, height, fps;

    private long window, lastFrameTime;

    protected float delta, aspectRatio;

    protected boolean fullscreen, resized, resizable, vSync;

    private GLFWFramebufferSizeCallback sizeCallback;

    //private World loadedWorld

    private String title;

    public Display(int width, int height, String title) {
        this(width, height, title, true);
    }

    public Display(int width, int height, String title, boolean resizable) {
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.title = title;
        aspectRatio = (float) width / height;
        resized = false;
    }

    /**
     * Initialize function for the display
     */
    public void run(){
        //region GLFW Window Setup
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Window could not be created");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);                                  // New windows will not be shown
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GL11.GL_TRUE : GL11.GL_FALSE);     // Set window to resizable
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);                          // Ask for version 3.2
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);           // Ask for the core version of OpenGL
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);                     // Allows deprecated GL functions to run on newer versions

        // If width or height was set to 0 hint to maximize and set values to height and width
        if((width == 0 || height == 0)){
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            fullscreen = true;
        }

        // Create the window
        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL){
            throw new RuntimeException("Failed to create window");
        }

        // Create the callbacks
        createCallbacks();

        // Setup fullscreen, else set window to center of screen
        if(fullscreen){
            GLFW.glfwMaximizeWindow(window);
        }
        else{
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        }

        // Set the window to be the current
        GLFW.glfwMakeContextCurrent(window);

        // Setup vsync
        if(vSync){
            GLFW.glfwSwapInterval(1);
        }

        // Show the window
        GLFW.glfwShowWindow(window);

        // Create capabilities such as depth and face culling
        GL.createCapabilities();

        GL11.glClearColor(0f,0f,0f,0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
        //endregion
    }

    /**
     * Loop for the display
     */
    public void loop(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    /**
     * Destroys the display
     */
    public void cleanUp(){
        GLFW.glfwDestroyWindow(window);
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        aspectRatio = width / height;
        resized = true;
    }

    public void setIconImage(String path){

    }

    /**
     * Sets the color everytime the screen is refreshed
     * @param r Red (0-1)
     * @param b Blue (0-1)
     * @param g Green (0-1)
     * @param a Alpha (0-1)
     */
    public void setClearColor(float r, float b, float g, float a){
        GL11.glClearColor(r, b, g, a);
    }

    private void createCallbacks(){
        // On window resized
         sizeCallback = GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });

        // Input
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

        });
    }

    public boolean isKeyPressed(int keycode){
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }

    public abstract void update();

    public abstract void init();

}
