package dev.vafix.VafixEngine.game;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

public abstract class Display {

    protected int width, height, fps;

    private long window, lastFrameTime;

    protected float delta, aspectRatio;

    protected boolean fullscreen, resized, resizable;

    private GLFWWindowSizeCallback sizeCallback;

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
        aspectRatio = width / height;
        resized = false;
    }

    public void run(){

    }

    public void loop(){

    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        aspectRatio = width / height;
        resized = true;
    }

    public void setIconImage(String path){

    }

    public void createCallbacks(){

    }

    public abstract void update();

    public abstract void init();

}
