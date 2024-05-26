package pl.bpiatek.mutators;

import org.pitest.mutationtest.engine.gregor.MutationContext;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.reloc.asm.MethodVisitor;
import org.pitest.reloc.asm.Opcodes;
import org.pitest.reloc.asm.Type;


public class NullParameterMutator implements MethodMutatorFactory {

    @Override
    public MethodVisitor create(final MutationContext context,
                                final MethodInfo methodInfo,
                                final MethodVisitor methodVisitor) {
        return new NullParameterMethodVisitor(methodVisitor, methodInfo);
    }

    @Override
    public String getGloballyUniqueId() {
        return this.getClass().getName();
    }

    @Override
    public String getName() {
        return "NULL_PARAMETER_MUTATOR";
    }

    private static class NullParameterMethodVisitor extends MethodVisitor {

        private final MethodInfo methodInfo;

        NullParameterMethodVisitor(final MethodVisitor methodVisitor,
                                   final MethodInfo methodInfo) {
            super(Opcodes.ASM7, methodVisitor);
            this.methodInfo = methodInfo;
        }

        @Override
        public void visitVarInsn(final int opcode, final int var) {
            if (opcode == Opcodes.ALOAD) {
                Type[] argumentTypes = Type.getArgumentTypes(methodInfo.getMethodDescriptor());
                if (var >= 0 && var < argumentTypes.length) {
                    // Generate mutation to replace parameter with null
                    super.visitInsn(Opcodes.ACONST_NULL);
                }
            }
            super.visitVarInsn(opcode, var);
        }
    }
}