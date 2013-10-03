import sys
import os
import fnmatch

srcdir = os.path.abspath(sys.argv[1])

def main():
    for path, dirs, files in os.walk(srcdir):
        for filename in files:
            if filename.endswith(('.java', '.asm')):
                filepath = os.path.join(path, filename)
                print "Patching: {}".format(filepath)
                with open(filepath) as f:
                    s = f.read()
                s = s.replace("net/minecraft/client/gui/FontRenderer", "avi")
                s = s.replace("net/minecraft/util/ResourceLocation", "bjo")
                s = s.replace("getResourcePath", "a")
                s = s.replace("colorCode", "f")
                s = s.replace("renderStringAtPos", "a")
                s = s.replace("getStringWidth", "a")
                s = s.replace("renderString", "b")
                s = s.replace("sizeStringToWidth", "e")
                s = s.replace("bidiReorder", "c")
                s = s.replace("trimStringToWidth", "a")
                s = s.replace("posX", "i")
                s = s.replace("net/minecraft/client/settings/GameSettings", "aul")
                s = s.replace("net/minecraft/client/renderer/texture/TextureManager", "bim")
                with open(filepath, "w") as f:
                    f.write(s)

if __name__ == '__main__':
    main()
