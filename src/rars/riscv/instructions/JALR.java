package rars.riscv.instructions;

import rars.ProgramStatement;
import rars.riscv.hardware.RegisterFile;
import rars.riscv.BasicInstruction;
import rars.riscv.BasicInstructionFormat;
import rars.riscv.InstructionSet;
import rars.Globals;
import rars.Settings;

public class JALR extends BasicInstruction {
    public JALR() {
        super("jalr t1, t2, -100", "Jump and link register: Set t1 to Program Counter (return address) then jump to statement at t2 + immediate",
                BasicInstructionFormat.I_FORMAT, "tttttttttttt sssss 000 fffff 1100111");
    }

    public void simulate(ProgramStatement statement) {
        int[] operands = statement.getOperands();
        int target = RegisterFile.getValue(operands[1]);
        InstructionSet.processReturnAddress(operands[0]);
        // Set PC = $t2 + immediate with the last bit set to 0
        InstructionSet.processJump((target + ((operands[2]<<20)>>20)) & 0xFFFFFFFE);

        if (Globals.getSettings().getBooleanSetting(Settings.Bool.CALLING_CONVENTIONS_ENABLED)) {
            // update all temporary registers with a random number between 1 and 2^31 - 1
            RegisterFile.updateRegister("t0", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("t1", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("t2", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("t3", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("t4", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("t5", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("t6", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a1", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a2", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a3", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a4", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a5", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a6", (int) (Math.random() * Integer.MAX_VALUE) + 1);
            RegisterFile.updateRegister("a7", (int) (Math.random() * Integer.MAX_VALUE) + 1);
        }
    }
}