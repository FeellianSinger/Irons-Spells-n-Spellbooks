package com.example.testmod.gui.overlays;

import com.example.testmod.TestMod;
import com.example.testmod.item.SpellBook;
import com.example.testmod.player.ClientMagicData;
import com.example.testmod.spells.AbstractSpell;
import com.example.testmod.util.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ImbuedSpellOverlay extends GuiComponent {
    protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
    public final static ResourceLocation TEXTURE = new ResourceLocation(TestMod.MODID, "textures/gui/icons.png");

    public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        Player player = Minecraft.getInstance().player;

        //This might be expensive
        ItemStack imbuedSword = Utils.getHeldImbuedSword(player);
        if(imbuedSword==null)
            return;

        int centerX, centerY;
        //GUI:522 (offhand slot location)
        centerX = screenWidth / 2  + 91 + 9;// + 29;
        centerY = screenHeight - 23;


        //
        //  Render Spells
        //
        var spellData =Utils.getScrollData(imbuedSword);
        AbstractSpell spell = spellData.getSpell();

        //Slot Border
        setTranslucentTexture(WIDGETS_LOCATION);
        gui.blit(poseStack, centerX,centerY, 24, 22, 29, 24);
        //Spell Icon
        setOpaqueTexture(spell.getSpellType().getResourceLocation());
        gui.blit(poseStack, centerX + 3 , centerY + 4, 0, 0, 16, 16, 16, 16);
        //Border + Cooldowns
        setTranslucentTexture(TEXTURE);
        float f = ClientMagicData.getCooldownPercent(spell.getSpellType());
        if (f > 0) {
            int pixels = (int) (16 * f + 1f);
            gui.blit(poseStack, centerX + 3, centerY + 20 - pixels, 47, 87, 16, pixels);
        }
    }

    private static void setOpaqueTexture(ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, texture);
    }

    private static void setTranslucentTexture(ResourceLocation texture) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getRendertypeTranslucentShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, texture);
    }
}