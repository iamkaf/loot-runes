package com.iamkaf.lootrunes.client;

import com.iamkaf.lootrunes.Constants;
import com.iamkaf.lootrunes.domain.RuneCatalog;
import com.iamkaf.lootrunes.domain.RuneDefinition;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.network.C2SToggleRunePacket;
import com.iamkaf.lootrunes.network.LootRunesNetwork;
import com.iamkaf.lootrunes.runtime.RuneTabletSnapshot;
//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;*/
//?}
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

/** A compact vanilla container-shaped UI. It intentionally uses native buttons, font, slots, and tooltips. */
public final class RuneTabletScreen extends Screen {
    private static final int IMAGE_WIDTH = 176;
    private static final int IMAGE_HEIGHT = 166;
    private static final Identifier BACKGROUND = Constants.resource("textures/gui/rune_tablet.png");

    private RuneTabletSnapshot snapshot;

    public RuneTabletScreen(RuneTabletSnapshot snapshot) {
        super(Component.translatable("screen.lootrunes.rune_tablet"));
        this.snapshot = snapshot;
    }

    public void update(RuneTabletSnapshot snapshot) {
        this.snapshot = snapshot;
        rebuildWidgets();
    }

    @Override
    protected void init() {
        int left = (width - IMAGE_WIDTH) / 2;
        int top = (height - IMAGE_HEIGHT) / 2;
        for (int index = 0; index < snapshot.runes().size(); index++) {
            RuneTabletSnapshot.Entry entry = snapshot.runes().get(index);
            RuneDefinition definition = RuneCatalog.get(entry.id());
            int column = index % 2;
            int row = index / 2;
            int x = left + 8 + column * 81;
            int y = top + 57 + row * 23;
            Component label = Component.translatable(definition.nameKey());
            if (entry.active()) {
                label = label.copy().append(" *");
            }
            Button button = Button.builder(label, ignored -> LootRunesNetwork.sendToServer(new C2SToggleRunePacket(entry.id())))
                    .bounds(x, y, 79, 20)
                    .tooltip(Tooltip.create(tooltip(entry, definition)))
                    .build();
            button.active = entry.unlocked() && (entry.active() || snapshot.activeCount() < snapshot.activeLimit());
            addRenderableWidget(button);
        }
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), ignored -> onClose())
                .bounds(left + 58, top + 139, 60, 20)
                .build());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean isInGameUi() {
        return true;
    }

    //? if >=26.1 {
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int left = (width - IMAGE_WIDTH) / 2;
        int top = (height - IMAGE_HEIGHT) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, left, top, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        drawContent(graphics);
    }

    private void drawContent(GuiGraphicsExtractor graphics) {
        int left = (width - IMAGE_WIDTH) / 2;
        int top = (height - IMAGE_HEIGHT) / 2;
        graphics.text(font, title, left + 8, top + 6, 0x404040, false);
        graphics.text(
                font,
                Component.translatable("screen.lootrunes.active", snapshot.activeCount(), snapshot.activeLimit()),
                left + 8,
                top + 18,
                0x404040,
                false
        );

        List<RuneTabletSnapshot.Entry> active = snapshot.runes().stream().filter(RuneTabletSnapshot.Entry::active).toList();
        for (int slotIndex = 0; slotIndex < snapshot.activeLimit(); slotIndex++) {
            int x = left + 58 + slotIndex * 20;
            int y = top + 29;
            if (slotIndex < active.size()) {
                drawRuneIcon(graphics, active.get(slotIndex).id(), x + 1, y + 1);
            }
        }

        for (int index = 0; index < snapshot.runes().size(); index++) {
            RuneTabletSnapshot.Entry entry = snapshot.runes().get(index);
            int x = left + 10 + (index % 2) * 81;
            int y = top + 59 + (index / 2) * 23;
            drawRuneIcon(graphics, entry.unlocked() ? entry.id() : null, x, y);
        }
    }
    //?} else {
    /*@Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        int left = (width - IMAGE_WIDTH) / 2;
        int top = (height - IMAGE_HEIGHT) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, left, top, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        int left = (width - IMAGE_WIDTH) / 2;
        int top = (height - IMAGE_HEIGHT) / 2;
        graphics.drawString(font, title, left + 8, top + 6, 0x404040, false);
        graphics.drawString(
                font,
                Component.translatable("screen.lootrunes.active", snapshot.activeCount(), snapshot.activeLimit()),
                left + 8,
                top + 18,
                0x404040,
                false
        );

        List<RuneTabletSnapshot.Entry> active = snapshot.runes().stream().filter(RuneTabletSnapshot.Entry::active).toList();
        for (int slotIndex = 0; slotIndex < snapshot.activeLimit(); slotIndex++) {
            int x = left + 58 + slotIndex * 20;
            int y = top + 29;
            if (slotIndex < active.size()) {
                drawRuneIcon(graphics, active.get(slotIndex).id(), x + 1, y + 1);
            }
        }

        for (int index = 0; index < snapshot.runes().size(); index++) {
            RuneTabletSnapshot.Entry entry = snapshot.runes().get(index);
            int x = left + 10 + (index % 2) * 81;
            int y = top + 59 + (index / 2) * 23;
            drawRuneIcon(graphics, entry.unlocked() ? entry.id() : null, x, y);
        }
    }*/
    //?}

    private static Component tooltip(RuneTabletSnapshot.Entry entry, RuneDefinition definition) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable(definition.descriptionKey()));
        if (!entry.unlocked()) {
            lines.add(Component.translatable(definition.unlockKey(), entry.progress(), entry.target()));
        } else if (entry.active()) {
            lines.add(Component.translatable("screen.lootrunes.click_deactivate"));
        } else {
            lines.add(Component.translatable("screen.lootrunes.click_activate"));
        }
        MutableComponent tooltip = Component.empty();
        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) {
                tooltip.append("\n");
            }
            tooltip.append(lines.get(i));
        }
        return tooltip;
    }

    //? if >=26.1
    private static void drawRuneIcon(GuiGraphicsExtractor graphics, RuneId id, int x, int y) {
    //? if <26.1
    /*private static void drawRuneIcon(GuiGraphics graphics, RuneId id, int x, int y) {*/
        String path = id == null ? "locked" : id.value();
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Constants.resource("textures/gui/runes/" + path + ".png"),
                x,
                y,
                0,
                0,
                16,
                16,
                16,
                16
        );
    }
}
