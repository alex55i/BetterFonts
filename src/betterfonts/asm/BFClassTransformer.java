package betterfonts.asm;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.FADD;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.I2F;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.PUTFIELD;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import codechicken.core.asm.ClassOverrider;
import codechicken.lib.asm.ASMHelper;
import codechicken.lib.asm.ASMReader;
import codechicken.lib.asm.ASMReader.ASMBlock;
import codechicken.lib.asm.CC_ClassWriter;
import codechicken.lib.asm.InstructionComparator;
import codechicken.lib.asm.ObfMapping;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;

public class BFClassTransformer implements IClassTransformer
{
    private Map<String, ASMBlock> asmblocks = ASMReader.loadResource("/assets/betterfonts/asm/blocks.asm");
    private ObfMapping m_init = new ObfMapping("net/minecraft/client/gui/FontRenderer", "<init>", "(Lnet/minecraft/client/settings/GameSettings;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/TextureManager;Z)V");
    private ObfMapping m_gsw = new ObfMapping("net/minecraft/client/gui/FontRenderer", "getStringWidth", "(Ljava/lang/String;)I");
    private ObfMapping m_rs = new ObfMapping("net/minecraft/client/gui/FontRenderer", "renderString", "(Ljava/lang/String;IIIZ)I");
    private ObfMapping m_sstw = new ObfMapping("net/minecraft/client/gui/FontRenderer", "sizeStringToWidth", "(Ljava/lang/String;I)I");
    private ObfMapping m_br = new ObfMapping("net/minecraft/client/gui/FontRenderer", "bidiReorder", "(Ljava/lang/String;)Ljava/lang/String;");
    private ObfMapping m_tstw = new ObfMapping("net/minecraft/client/gui/FontRenderer", "trimStringToWidth", "(Ljava/lang/String;IZ)Ljava/lang/String;");

    @Override
    public byte[] transform(String classname, String deobfuscatedClassname, byte[] bytecode)
    {
        if (bytecode == null)
            return null;

        if (FMLLaunchHandler.side() != Side.CLIENT)
            return bytecode;

        try
        {
            if (m_init.isClass(classname))
            {
                ClassReader reader = new ClassReader(bytecode);
                ClassNode cnode = new ClassNode();
                reader.accept(cnode, ClassReader.SKIP_FRAMES);

                {
                    MethodNode m = ASMHelper.findMethod(m_init, cnode);
                    AbstractInsnNode start = InstructionComparator.insnListFindStart(m.instructions, asmblocks.get("initneedle").insns).get(0);
                    m.instructions.insertBefore(start, asmblocks.get("inithook").insns);
                }

                {
                    MethodNode m = ASMHelper.findMethod(m_gsw, cnode);
                    m.instructions.insert(asmblocks.get("gswhook").insns);
                }

                {
                    MethodNode m = ASMHelper.findMethod(m_sstw, cnode);
                    m.instructions.insert(asmblocks.get("sstwhook").insns);
                }

                {
                    MethodNode m = ASMHelper.findMethod(m_br, cnode);
                    m.instructions.insert(asmblocks.get("brhook").insns);
                }

                {
                    MethodNode m = ASMHelper.findMethod(m_tstw, cnode);
                    m.instructions.insert(asmblocks.get("tstwhook").insns);
                }

                {
                    MethodNode m = ASMHelper.findMethod(m_rs, cnode);
                    AbstractInsnNode start = InstructionComparator.insnListFindStart(m.instructions, asmblocks.get("rsneedle").insns).get(0);
                    m.instructions.insertBefore(start, asmblocks.get("rshook").insns);
                    m.instructions.remove(start.getNext().getNext().getNext());
                    m.instructions.remove(start.getNext().getNext());
                    m.instructions.remove(start.getNext());
                    m.instructions.remove(start);
                }

                ClassWriter writer = new CC_ClassWriter(COMPUTE_MAXS | COMPUTE_FRAMES);
                cnode.accept(writer);
                bytecode = writer.toByteArray();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bytecode;
    }
}
