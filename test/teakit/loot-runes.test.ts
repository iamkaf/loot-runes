import { Capability, Readiness, describe, expect, pos, test } from "@teakit/test";
import type { ClientScreen, TeaKitTestContext } from "@teakit/test";

const arena = pos(4.5, 70, 0.5);
const runeTabletScreen = "com.iamkaf.lootrunes.client.RuneTabletScreen";

describe.configure({
  timeout: "6m",
  readiness: [Readiness.World, Readiness.Player],
  capabilities: [
    Capability.ClientInput,
    Capability.ClientScreens,
    Capability.ClientScreenshot,
    Capability.PlayerInventory,
    Capability.RuntimeTiming,
    Capability.ServerCommands,
    Capability.WorldEntities,
    Capability.WorldLoot,
    Capability.WorldRecipes,
  ],
});

describe("Loot Runes", () => {
  test("crafts the tablet, activates Plenty, and changes a real mob drop", async (ctx) => {
    await cleanup(ctx);
    try {
      await ctx.recipes.assertCrafting(
        3,
        3,
        [
          "minecraft:amethyst_shard", "minecraft:copper_ingot", "minecraft:amethyst_shard",
          "minecraft:copper_ingot", "minecraft:stone", "minecraft:copper_ingot",
          "minecraft:amethyst_shard", "minecraft:copper_ingot", "minecraft:amethyst_shard",
        ],
        "lootrunes:rune_tablet",
        { resultCount: 1 },
      );

      await ctx.commands.batch([
        "/gamemode survival @s",
        "/tp @s 0.5 70 0.5",
        "/fill -2 69 -2 7 69 2 minecraft:stone replace",
        "/fill -2 70 -2 7 74 2 minecraft:air replace",
        "/item replace entity @s weapon.mainhand with lootrunes:rune_tablet",
      ]);

      await ctx.player.inventory().waitForItem("lootrunes:rune_tablet", {
        selected: true,
        timeout: "10s",
      });
      await ctx.player.holdUse(true);
      await ctx.player.holdUse(false);
      let screen = await waitForRuneTablet(ctx);
      const plenty = screen.widgets().all().find((widget) => widget.label.startsWith("Plenty"));
      expect(plenty).toBeDefined();
      if (plenty?.label.endsWith(" *")) {
        screen = await screen.widgets().activate("Plenty *");
        await ctx.runtime.wait(500);
        screen = await ctx.client.waitForScreen(runeTabletScreen, { timeout: "15s" });
      }
      screen = await screen.widgets().activate("Plenty");
      await ctx.runtime.wait(500);
      screen = await ctx.client.waitForScreen(runeTabletScreen, { timeout: "15s" });
      expect(screen.widgets().all().some((widget) => widget.label === "Plenty *")).toBe(true);
      await ctx.client.screenshot("loot-runes-tablet-plenty-active", {
        hideOverlay: true,
        hideWindowDecoration: true,
      });
      await ctx.client.closeMenus();

      await ctx.commands.batch([
        "/item replace entity @s hotbar.1 with minecraft:iron_sword",
        `/summon minecraft:chicken ${arena.x} ${arena.y} ${arena.z} {NoAI:1b,Health:1.0f}`,
        "/damage @e[type=minecraft:chicken,limit=1,sort=nearest] 100 minecraft:player_attack by @s",
      ]);

      const drops = ctx.loot.near(arena, { item: "minecraft:chicken", radius: 6 });
      await drops.waitForCountAtLeast(1, { timeout: "15s" });
      const rawChicken = (await drops.list()).reduce((count, drop) => count + (drop.count ?? 0), 0);
      expect(rawChicken).toBeGreaterThanOrEqual(2);
    } finally {
      await cleanup(ctx);
    }
  });
});

async function waitForRuneTablet(ctx: TeaKitTestContext): Promise<ClientScreen> {
  const deadline = Date.now() + 15_000;
  while (Date.now() < deadline) {
    const screen = await ctx.client.screen();
    if (screen.screenClass === runeTabletScreen) {
      return screen;
    }
    await ctx.runtime.wait(250);
  }
  throw new Error(`Timed out waiting for ${runeTabletScreen}`);
}

async function cleanup(ctx: TeaKitTestContext) {
  await ctx.client.closeMenus();
  await ctx.commands.batch([
    "/gamemode creative @s",
    "/clear @s",
    "/kill @e[type=minecraft:chicken,distance=..16]",
    "/kill @e[type=minecraft:item,distance=..16]",
    "/fill -2 69 -2 7 74 2 minecraft:air replace",
  ]);
}
