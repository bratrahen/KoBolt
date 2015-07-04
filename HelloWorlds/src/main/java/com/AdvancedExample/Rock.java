package com.AdvancedExample;

import com.kobot.objects.StaticObject;
import com.threed.jpct.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Rock extends StaticObject implements Serializable{

    public Rock(@NotNull String path) {
        setModel(Loader.load3DS(path + "example/rock.3ds", 15f)[0]);

        TextureManager.getInstance().addTexture("rock", new Texture(path + "example/rock.jpg"));
        TextureManager.getInstance().addTexture("normals", new Texture(path + "example/normals.jpg"));
        TextureInfo stoneTex = new TextureInfo(TextureManager.getInstance().getTextureID("rock"));
        stoneTex.add(TextureManager.getInstance().getTextureID("normals"), TextureInfo.MODE_MODULATE);
        getModel().setTexture(stoneTex);
        getModel().setSpecularLighting(true);

        getModel().translate(0, 0, -90);
        getModel().rotateX(-(float) Math.PI / 2);

        // Setup shaders for the rock
        String fragmentShader = Loader.loadTextFile(path + "example/shader/fragmentshader.glsl");
        String vertexShader = Loader.loadTextFile(path + "example/shader/vertexshader.glsl");

        GLSLShader shader = new GLSLShader(vertexShader, fragmentShader);
        // shader.setShadowHelper(sh);
        shader.setStaticUniform("colorMap", 0);
        shader.setStaticUniform("normalMap", 1);
        shader.setStaticUniform("invRadius", 0.0005f);
        getModel().setRenderHook(shader);

        getModel().build();
        getModel().compileAndStrip();
    }
}
