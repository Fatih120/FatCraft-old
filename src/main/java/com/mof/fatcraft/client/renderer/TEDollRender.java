package com.mof.fatcraft.client.renderer;

import com.mof.fatcraft.Fatcraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TEDollRender extends TileEntitySpecialRenderer{
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Fatcraft.MODID, "models/doll.obj"));
    ResourceLocation texture = new ResourceLocation(Fatcraft.MODID, "models/doll.png");
    //ModelBiped model = new ModelBiped();

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTicks) {
        bindTexture(texture);
        //model.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        //model.bipedRightArm.rotateAngleZ = (float)Math.toRadians(70.0);

        GL11.glPushMatrix(); //Prepare Conditions
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //Color Overlay
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F); //Sets Pos offset
        //GL11.glTranslatef((float) x + 0.5F, (float) y + 2.0F, (float) z + 0.5F); //Sets Pos offset
        GL11.glRotatef(180, 0, 0, 1); //Sets Rot, deg x y z
        GL11.glScalef(1F, 1F, 1F); //Sets Scale
        //GL11.glScalef(0.05F, 0.05F, 0.05F); //Sets Scale
        //model.renderAll();
        //model.render(null, 0, 0, 0F, 0, 0, 1.0F); //null, y? swing,
        GL11.glPopMatrix(); //Parse Conditions
    }

}
