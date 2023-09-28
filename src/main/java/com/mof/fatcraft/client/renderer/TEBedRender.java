package com.mof.fatcraft.client.renderer;

import com.mof.fatcraft.Fatcraft;
import com.mof.fatcraft.block.BlockColoredBed;
import com.mof.fatcraft.client.model.ModelBed;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static com.mof.fatcraft.item.ItemColoredBed.bedNames;

public class TEBedRender extends TileEntitySpecialRenderer {
//    private static final ResourceLocation[] bedColor;
    private final ModelBed model = new ModelBed();
    ResourceLocation texture = new ResourceLocation(Fatcraft.MODID, "textures/entity/bed/red.png");
    //            bedColor = new ResourceLocation("textures/entity/bed/" + bedName + ".png");

    //    public TEBedRender()
 //   {
//        this.getModelVer = this.field_193849_d.func_193770_a();
 //   }
    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float p_147500_8_) {
        bindTexture(texture);
//        if (this.field_193850_e != this.field_193849_d.func_193770_a())
//        {
//            this.field_193849_d = new ModelBed();
//            this.field_193850_e = this.field_193849_d.func_193770_a();
//        }

        //boolean flag = entity.getWorld() != null;
        //boolean flag1 = flag ? BlockColoredBed.isBlockHeadOfBed() : true;
        //EnumDyeColor enumdyecolor = entity != null ? entity.getColor() : EnumDyeColor.RED;
        //int i = flag ? entity.getBlockMetadata() & 3 : 0;

//        if (destroyStage >= 0)
//        {
//            this.bindTexture(DESTROY_STAGES[destroyStage]);
            //GL11.glMatrixMode(5890);
            //GL11.glPushMatrix();
            //GL11.glScalef(4.0F, 4.0F, 1.0F);
            //GL11.glTranslatef(0.0625F, 0.0625F, 0.0625F);
            //GL11.glMatrixMode(5888);
            //bindTexture(bedColor);
//        }
//        else
////        {
//            ResourceLocation texture = bedColor
//
//            if (bedColor != null)
//            {
//                this.bindTexture(bedColor);
//            }
//        }

//        if (flag)
//        {
//            this.renderPiece(flag1, x, y, z, i, alpha);
//        }
//        else
//        {
//            GL11.glPushMatrix();
//            this.renderPiece(true, x, y, z, i, alpha);
//            this.renderPiece(false, x, y, z - 1.0D, i, alpha);
//            GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef(90F, 0F, 0F, 0F);
        GL11.glTranslatef((float) x + 0.0F, (float) y + 0.5F, (float) z + 0.0F);
        model.headPiece.render(0.0625F);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glRotatef(90F, 1F, 0F, 0F);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 1.5F);
        model.footPiece.render(0.0625F);
        GL11.glPopMatrix();
//        }

//        if (destroyStage >= 0)
//        {
//            GL11.glMatrixMode(5890);
//            GL11.glPopMatrix();
//            GL11.glMatrixMode(5888);
//        }
//    }

//    static
//    {
//        bedColor = new ResourceLocation[bedNames.length];
//
//        for (String bedName : bedNames) {
//            bedColor = new ResourceLocation("textures/entity/bed/" + bedName + ".png");
//        }
    }
}
