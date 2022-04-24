package dev.vafix.VafixEngine.game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Engine {
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    public static int fps;
    public static float frameTime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private Display display;
    private GLFWErrorCallback errorCallback;

    private void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        display = new Display(480, 270, "test") {
            @Override
            public void update() {
                display.loop();
            }

            @Override
            public void init() {
                display.run();
            }
        };
    }

    public void start() throws Exception {
        init();
        if(isRunning){
            return;
        }

        run();
    }

    public void run(){
        isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while(isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            //input();

            while (unprocessedTime > frameTime) {
                render = true;
                unprocessedTime -= frameTime;

                if (display.displayShouldClose()) {
                    stop();
                }

                if (frameCounter >= NANOSECOND) {
                    setFPS(frames);

                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render){
                update();
                render();
                frames++;
            }
        }

        cleanUp();
    }

    private void setFPS(int _fps){
        fps = _fps;
        display.setFPS(_fps);
    }

    private void stop(){
        if(!isRunning){
            return;
        }

        isRunning = false;
    }

    private void render(){
        display.update();
    }

    private void update(){}

    private void cleanUp(){
        display.cleanUp();
        errorCallback.free();
        GLFW.glfwTerminate();
    }
}
