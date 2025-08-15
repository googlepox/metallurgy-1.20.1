package net.googlepox.metallurgy.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.entity.CrusherEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrusherScreen extends AbstractContainerScreen<CrusherMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Metallurgy.MODID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, leftPos, topPos - 31, 0, 0, 175, 196);

        renderProgressArrow(guiGraphics, leftPos + 128, topPos + 46);


        int l = this.getCrushProgressScaled(33);
        if (l > 0)
        {
            guiGraphics.blit(TEXTURE, leftPos+ 93, topPos + 66 - l, 176, 32 - l, 7, l);
            guiGraphics.blit(TEXTURE, leftPos + 60, topPos + 14, 177, 33, 21, 18);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int k = this.getBurnLeftScaled(16);
            guiGraphics.blit(TEXTURE, leftPos + 128, topPos + 46 - k, 176, 77 - k, 17, k + 1);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private int getBurnLeftScaled(int pixels)
    {
        return this.menu.blockEntity.data.get(0) * pixels / this.menu.blockEntity.data.get(1);
    }

    /**
     * @param pixels Texture height
     */
    private int getCrushProgressScaled(int pixels)
    {
        int crushingTime = this.menu.blockEntity.data.get(2);
        return crushingTime != 0 ? crushingTime * pixels / CrusherEntity.TOTAL_CRUSHING_TIME : 0;
    }
}