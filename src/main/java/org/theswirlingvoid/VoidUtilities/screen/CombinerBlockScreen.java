package org.theswirlingvoid.VoidUtilities.screen;

import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.tileentities.AbstractFurnacentContainer;
import org.theswirlingvoid.VoidUtilities.tileentities.CombinerBlockContainer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CombinerBlockScreen extends ContainerScreen<CombinerBlockContainer>
{

	private ResourceLocation GUI = new ResourceLocation(Main.MODID, "textures/gui/container/combiner.png");
	public CombinerBlockScreen(CombinerBlockContainer container, PlayerInventory playerinv, ITextComponent Itc) {
		// TODO Auto-generated constructor stub
		super(container,playerinv,Itc);
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		String s = this.title.getFormattedText();
	      this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
	      this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), (float)(this.ySize - 96 + 2), 4210752);
	   //this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
	}
	public void tick() {
	      super.tick();
	   }
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int i = this.guiLeft;
	      int j = this.guiTop;
		int relX = (this.width - this.xSize) /2;
		int relY = (this.height - this.ySize) /2;
		this.blit(relX,relY,0,0,this.xSize,this.ySize);

	         int k = ((CombinerBlockContainer)this.container).fuelProgressionScaled();
	         if (k!=0) {
	         this.blit(i+48, j+60 - k, 179, 58 - k, 10, k + 1);
	         }
	      
		int l = ((CombinerBlockContainer)this.container).getCombineProgressionScaled();
		this.blit(i + 79, j + 34, 176, 14, l + 1, 16);
	}
	
}
