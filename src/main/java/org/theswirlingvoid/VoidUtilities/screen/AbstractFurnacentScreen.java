package org.theswirlingvoid.VoidUtilities.screen;

import org.theswirlingvoid.VoidUtilities.tileentities.AbstractFurnacentContainer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractFurnacentScreen<T extends AbstractFurnacentContainer> extends ContainerScreen<T> implements IRecipeShownListener {
   private static final ResourceLocation field_214089_l = new ResourceLocation("textures/gui/recipe_button.png");
   private boolean field_214090_m;
   private final ResourceLocation field_214091_n;

   public AbstractFurnacentScreen(T p_i51104_1_, AbstractRecipeBookGui p_i51104_2_, PlayerInventory p_i51104_3_, ITextComponent p_i51104_4_, ResourceLocation p_i51104_5_) {
      super(p_i51104_1_, p_i51104_3_, p_i51104_4_);
      this.field_214091_n = p_i51104_5_;
   }

   public void init() {
      super.init();
      this.field_214090_m = this.width < 379;
   }

   public void tick() {
      super.tick();
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      super.render(p_render_1_, p_render_2_, p_render_3_);
     }

   /**
    * Draw the foreground layer for the GuiContainer (everything in front of the items)
    */
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.title.getFormattedText();
      this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
      this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
   }

   /**
    * Draws the background layer of this container (behind the items).
    */
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(this.field_214091_n);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.blit(i, j, 0, 0, this.xSize, this.ySize);
      if (((AbstractFurnacentContainer)this.container).func_217061_l()) {
         int k = ((AbstractFurnacentContainer)this.container).getBurnLeftScaled();
         this.blit(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
      }

      int l = ((AbstractFurnacentContainer)this.container).getCookProgressionScaled();
      this.blit(i + 79, j + 34, 176, 14, l + 1, 16);
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
     
         return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
      
   }

   /**
    * Called when the mouse is clicked over a slot or outside the gui.
    */
   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.handleMouseClick(slotIn, slotId, mouseButton, type);
   }

   

   protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
      boolean flag = p_195361_1_ < (double)p_195361_5_ || p_195361_3_ < (double)p_195361_6_ || p_195361_1_ >= (double)(p_195361_5_ + this.xSize) || p_195361_3_ >= (double)(p_195361_6_ + this.ySize);
      return flag;
   }



   public void removed() {
      super.removed();
   }
}