# Alternate Mixin Utilities

---
This is a fork of [Twelvefold Booter by Twelvefold Studios](https://github.com/twelvefold/TwelvefoldBooter)

The mod is MIT licence, so this is fine.

The main concern has been confirmed removed, and I built the jar myself.

### Current features includes:

*   Enqueue mixins to be applied both early and late, to allow for for modifying Vanilla/Forge classes, as well as mod classes
*   Enable/disable enqueued mixins prior to application through the use of a Supplier, to allow developer control over optional mixins
*   Disallow other TwelvefoldBooter enqueued mixins from applying, for testing or tweaking purposes
*   Shadows Mixin 0.8.7 and MixinExtras 0.4.1

### Additional features added in this fork:

*   An alternative config system
*   Workaround of [the infamous RANDAR exploit](https://www.youtube.com/watch?v=maMpMOnIJDE)
*   Alternative health bar curve from [TFBC](https://github.com/twelvefold/TwelvefoldBetterCombat) ~~adapted with First Aid integration~~(removed in 199f2e2c8c1)