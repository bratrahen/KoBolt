import com.kobot.framework.ModelLoader;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.Sphere;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class JpctMonsterFactory extends MonstersFactory{
    private final ModelLoader loader = new ModelLoader("F:\\1.ProgramowanieProjekty\\_resources\\models");

    public JpctMonsterFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected RendererComponent createSphereRenderer(float radiusInMeters, Color color) {
        Object3D object3d = Primitives.getSphere(radiusInMeters);
        object3d.setName(Sphere.class.getSimpleName() + "@" + object3d.getID());
        applyColorTexture(object3d, color);
        return new JpctRendererComponent(object3d);
    }

    protected void applyColorTexture(@NotNull Object3D object, @NotNull Color color) {
        String textureId = "#" + color.getRGB() + "#" + color.getAlpha();
        boolean textureExists = TextureManager.getInstance().containsTexture(textureId);
        if (!textureExists) {
            TextureManager.getInstance().addTexture(textureId, new Texture(10, 10, color));
        }
        object.setTexture(textureId);
    }

    @Override
    @NotNull
    protected RendererComponent createCubeRenderer(float sideInMeters, @NotNull Color color) {
        float halfExtend = sideInMeters / 2.0f;
        Object3D object3d = Primitives.getCube(halfExtend);
        object3d.setName(Box.class.getSimpleName() + "@" + object3d.getID());
        applyColorTexture(object3d, color);

        return new JpctRendererComponent(object3d);
    }

    protected RendererComponent createKushanCarrierRenderer(){
        Object3D object3D = loader.load("Homeworld\\Kushan\\Carrier\\lod0", "Carrier", 1.0f);
        return new JpctRendererComponent(object3D);
    }

    @Override
    protected RendererComponent createTaiidanCarrierRenderer() {
        Object3D object3D = loader.load("Homeworld\\Taiidan\\Carrier-T\\lod0", "Carrier", 1.0f);
        return new JpctRendererComponent(object3D);
    }
}
