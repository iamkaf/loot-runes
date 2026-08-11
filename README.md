![Loot Runes banner placeholder](docs/banner-placeholder.png)

# Loot Runes

[![Modrinth](https://img.shields.io/badge/Modrinth-jVtBevly-1bd96a?style=for-the-badge&logo=modrinth&logoColor=white)](https://modrinth.com/mod/jVtBevly)
[![Requires Amber](https://img.shields.io/badge/Requires-Amber-ebb134?style=for-the-badge)](https://modrinth.com/mod/amber)
[![Issues](https://img.shields.io/github/issues/iamkaf/mod-issues?style=for-the-badge&color=eeeeee)](https://github.com/iamkaf/mod-issues)
[![Discord](https://img.shields.io/discord/1207469438719492176?style=for-the-badge&logo=discord&label=Discord&color=5865F2)](https://discord.gg/HV5WgTksaB)

Loot Runes is a work-in-progress mod about changing mob drops with a small loadout. You'll unlock runes as you play, then use a Rune Tablet to keep up to three of them active at once. The combination you pick can change which mobs you hunt, where you fight them, and what you do with their drops.

**The rune system isn't playable yet.** The repository is currently being modernized before feature work begins.

## How it will work

Runes are permanent unlocks, not another set of items to keep in a chest. Using the Rune Tablet opens a small Minecraft-style menu where you can choose three unlocked runes. You can come back and change them whenever you want a different setup.

![Rune Tablet UI placeholder](docs/tablet-ui-placeholder.png)

The first planned runes are:

- **Plenty** gives mobs another natural loot roll, but makes them harder to fight.
- **Sacrifice** eats some common drops in exchange for better odds at something unusual.
- **Echoes** carries the last drop into a kill against a different kind of mob.
- **Ascendance** builds up danger and rewards while you keep a kill streak alive.
- **Migration** gives alternate rewards for hunting mobs outside their usual biomes.
- **Improvisation** rewards you for changing the weapon you use to finish each fight.

These are planned features. The effects and numbers may change once they're in the game and can actually be tested.

## What's in the mod right now?

This repo is in the awkward middle of a rewrite. Extended Golf has been renamed to Loot Runes, but its old skeleton-assisted drop mechanic is still the only implemented gameplay. If you're here for the Rune Tablet and loadouts, wait for a later build.

The old creature reward tags are still present for now. Datapacks using them need to move from `data/extendedgolf/` to `data/lootrunes/`. There isn't a public datapack format for runes yet, so pack makers shouldn't build against the planned system.

The current source targets Fabric and NeoForge and requires [Amber](https://modrinth.com/mod/amber). The build setup is also due for modernization before the rune rewrite starts.

## Modpacks

You can include Loot Runes in modpacks. You don't need to ask first.

## Help

Found a bug or compatibility problem? Use the shared [issue tracker](https://github.com/iamkaf/mod-issues/issues). You can also join the [Discord](https://discord.gg/HV5WgTksaB) if you want to talk through an idea.

## Credits

- Everyone who's helped shape the new idea.
- **Aris**, for always being there for me.

Loot Runes is available under the [MIT License](LICENSE).
