package com.github.metriccaution.flack.registry;

import java.util.HashMap;
import java.util.Map;

public class ExecutorRegistry {
	private final Map<Class<? extends Instruction>, Executor<?>> executors;

	public ExecutorRegistry() {
		executors = new HashMap<>();
	}

	public <T extends Instruction> void register(final Executor<T> executor) {
		if (executors.containsKey(executor.getInstructionType())) {
			final Executor<?> alreadyRegistered = executors.get(executor.getInstructionType());

			throw new IllegalArgumentException("Instruction type " + executor.getInstructionType()
			+ " is already registered to " + alreadyRegistered.getClass());
		}

		executors.put(executor.getInstructionType(), executor);
	}

	public <T extends Instruction> void execute(final T instruction) {
		@SuppressWarnings("unchecked")
		final Executor<T> executor = (Executor<T>)executors.get(instruction.getClass());
		if (executor == null) {
			throw new IllegalArgumentException("No executor registered for class " + instruction.getClass());
		}

		executor.execute(instruction);
	}
}
