![Loot Runes banner placeholder](docs/banner-placeholder.png)

# Loot Runes

[![Modrinth](https://img.shields.io/badge/Modrinth-jVtBevly-1bd96a?style=for-the-badge&logo=modrinth&logoColor=white)](https://modrinth.com/mod/jVtBevly)
[![Requires Amber](https://img.shields.io/badge/Requires-Amber-ebb134?style=for-the-badge)](https://modrinth.com/mod/amber)
[![Issues](https://img.shields.io/github/issues/iamkaf/mod-issues?style=for-the-badge&color=eeeeee)](https://github.com/iamkaf/mod-issues)
[![Discord](https://img.shields.io/discord/1207469438719492176?style=for-the-badge&logo=discord&label=Discord&color=5865F2)](https://discord.gg/HV5WgTksaB)

> Monsters have loot tables. You have editing rights.

**Loot Runes** is a build-making loot mod about unlocking magical rules and choosing how creatures reward you. The destination is simple: open one Rune Tablet, inscribe up to three runes, and turn an ordinary night of combat into a hunt with its own strategy.

The right loadout might double common drops while empowering your prey. Another might consume the clutter and push every kill toward a jackpot. A third might reward weapon variety, strange biomes, or a streak you cannot afford to break.

Loot stops being a percentage. It becomes a build.

## One tablet. Three runes. A different hunt.

The Rune Tablet is the center of the planned experience:

1. **Discover** runes by completing unusual feats in the world.
2. **Inscribe** up to three unlocked runes in a compact, vanilla-style screen.
3. **Hunt** under the benefits, conditions, and risks you chose.
4. **Rebuild** your loadout when the next goal demands a different answer.

![Rune Tablet UI placeholder](docs/tablet-ui-placeholder.png)

Three slots are enough to create combinations and few enough to make every exclusion matter. There is no rune currency, no passive skill tree, and no universal best loadout.

## The rune language

The first planned roster establishes six distinct ways to change a hunt:

| Rune | What it asks of you |
| --- | --- |
| **Plenty** | Roll natural loot twice, but face tougher creatures. |
| **Sacrifice** | Consume common drops to improve exceptional-drop odds. |
| **Echoes** | Carry the last natural drop into the next different creature. |
| **Ascendance** | Raise danger and reward through an uninterrupted kill streak. |
| **Migration** | Hunt creatures outside their native biome for alternate rewards. |
| **Improvisation** | Change finishing weapons instead of repeating the safest answer. |

Each rune must change what you hunt, how you fight, or what happens to the spoils. A silent “+10% drops” is not enough.

![Rune hunt placeholder](docs/rune-hunt-placeholder.png)

## Current development state

The complete rename from **Extended Golf** to **Loot Runes** is finished in this repository. The gameplay rework comes next.

Current builds still contain the legacy skeleton-assisted loot mechanic inherited from the previous mod. The Rune Tablet, permanent rune unlocks, three-slot loadouts, and the roster above are the target design and are **not implemented yet**.

That distinction will remain explicit until the new loop is playable.

## Datapacks and compatibility

The mod ID and resource namespace are now `lootrunes`. Datapacks written for the old namespace must move their files to `data/lootrunes/`.

The existing creature reward tags remain available during the transition, but the future rune data surface is not stable yet. Pack makers should wait for the gameplay release before building deep integrations against the new system.

Loot Runes is a multiloader project for Fabric and NeoForge and requires [Amber](https://modrinth.com/mod/amber).

## Modpacks

You may include Loot Runes in modpacks. No permission request is required.

## Support

Report bugs and compatibility problems through the shared [issue tracker](https://github.com/iamkaf/mod-issues/issues), or join the [Discord](https://discord.gg/HV5WgTksaB) to talk about rune ideas and strange loot builds.

## Credits

- The community members helping shape the new identity.
- **Aris**, for always being there.

Loot Runes is licensed under the [MIT License](LICENSE).
