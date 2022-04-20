package dev.vafix.VafixEngine.game3d.render;

import dev.vafix.VafixEngine.physics.math.Vector3;

public class Vertex {
    private Vector3 position;

    public Vertex(Vector3 position){
        this.position = position;
    }

    public Vertex(float x, float y, float z){
        this.position = new Vector3(x, y, z);
    }

    public Vertex() {
        position = new Vector3();
    }

    public Vector3 getPosition(){
        return position;
    }
}
