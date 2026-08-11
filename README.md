![Loot Runes banner placeholder](docs/banner-placeholder.png)

# Loot Runes

Loot Runes lets you change mob drops with a Rune Tablet. Carry the tablet while hunting, use it to open the rune menu, and keep up to three unlocked runes active. One rune might add another roll from a mob's normal loot table. Another might reward you for changing weapons or moving to a new biome between kills.

This is the first playable version of the idea. The rune effects and unlock requirements are deliberately easy to replace after playtesting.

## The Rune Tablet

Craft the tablet with amethyst shards, copper ingots, and stone. Use it from either hand to open its menu.

![Rune Tablet UI placeholder](docs/tablet-ui-placeholder.png)

Plenty is available from the start. The rest unlock as the tablet records what you do while carrying it: mobs defeated, kinds of mob hunted, biomes visited, finishing weapons used, and your best kill streak. Progress and your active loadout are saved per player in the world.

The current rune roster is:

- **Plenty** adds one extra roll from the mob's own loot table.
- **Sacrifice** throws away the normal drops and makes two fresh rolls instead.
- **Echoes** repeats one drop remembered from your previous kill.
- **Ascendance** adds more rolls as you keep a kill streak going.
- **Migration** adds a roll when you move to a different biome between kills.
- **Improvisation** adds a roll when you change finishing weapons between kills.

Runes combine in the order listed above, so a three-rune loadout can behave differently from any one rune on its own. The current numbers are playtest rules, not promises about the final design.

## Current support

The development build targets Minecraft 26.2 on Fabric, Forge, and NeoForge. [Amber](https://modrinth.com/mod/amber) is required.

The textures are programmer art on purpose. See the [art guide](docs/art-guide.md) if you want to replace them.

## Modpacks and help

You can include Loot Runes in modpacks without asking first.

Found a bug or a bad interaction with another mod? Use the shared [issue tracker](https://github.com/iamkaf/mod-issues/issues). There is also a [Discord](https://discord.gg/HV5WgTksaB) for questions and ideas.

## Credits

- Everyone who's helped shape the idea.
- **Aris**, for always being there for me.

Loot Runes is available under the [MIT License](LICENSE).
