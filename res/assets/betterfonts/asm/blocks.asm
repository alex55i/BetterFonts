list initneedle
RETURN

list inithook
ALOAD 2
INVOKEVIRTUAL net/minecraft/util/ResourceLocation.getResourcePath()Ljava/lang/String;
ALOAD 0
GETFIELD net/minecraft/client/gui/FontRenderer.colorCode:[I
INVOKESTATIC betterfonts/asm/BFHooks.initHook(Ljava/lang/String;[I)V

list gswhook
GETSTATIC betterfonts/asm/BFHooks.betterFontsEnabled:Z
IFEQ L1337
ALOAD 1
INVOKESTATIC betterfonts/asm/BFHooks.gswHook(Ljava/lang/String;)I
IRETURN
L1337

list rsneedle
ALOAD 0
ALOAD 1
ILOAD 5
INVOKESPECIAL net/minecraft/client/gui/FontRenderer.renderStringAtPos(Ljava/lang/String;Z)V

list rshook
GETSTATIC betterfonts/asm/BFHooks.betterFontsEnabled:Z
IFEQ L111
ALOAD 0
DUP
GETFIELD net/minecraft/client/gui/FontRenderer.posX:F
ALOAD 1
ILOAD 2
ILOAD 3
ILOAD 4
ILOAD 5
INVOKESTATIC betterfonts/asm/BFHooks.rsHook(Ljava/lang/String;IIIZ)I
I2F
FADD
PUTFIELD net/minecraft/client/gui/FontRenderer.posX:F
GOTO L222
L111
ALOAD 0
ALOAD 1
ILOAD 5
INVOKESPECIAL net/minecraft/client/gui/FontRenderer.renderStringAtPos(Ljava/lang/String;Z)V
L222

list sstwhook
ALOAD 0
POP
GETSTATIC betterfonts/asm/BFHooks.betterFontsEnabled:Z
IFEQ L333
ALOAD 1
ILOAD 2
INVOKESTATIC betterfonts/asm/BFHooks.sstwHook(Ljava/lang/String;I)I
IRETURN
L333

list brhook
ALOAD 0
POP
GETSTATIC betterfonts/asm/BFHooks.betterFontsEnabled:Z
IFEQ L444
ALOAD 1
ARETURN
L444

list tstwhook
ALOAD 0
POP
GETSTATIC betterfonts/asm/BFHooks.betterFontsEnabled:Z
IFEQ L555
ALOAD 1
ILOAD 2
ILOAD 3
INVOKESTATIC betterfonts/asm/BFHooks.tstwHook(Ljava/lang/String;IZ)Ljava/lang/String;
ARETURN
L555