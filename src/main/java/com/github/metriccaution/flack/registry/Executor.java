package com.github.metriccaution.flack.registry;

public interface Executor <T extends Instruction>{
	public Class<T> getInstructionType();

	public void execute(final T instruction);
}
