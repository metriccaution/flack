package com.github.metriccaution.flack.mouse.executor;

import com.github.metriccaution.flack.mouse.Mouse;
import com.github.metriccaution.flack.registry.Executor;

public class MouseExecutor implements Executor<MouseInstruction> {

	private final Mouse mouse;

	public MouseExecutor(final Mouse mouse) {
		this.mouse = mouse;
	}

	@Override
	public Class<MouseInstruction> getInstructionType() {
		return MouseInstruction.class;
	}

	@Override
	public void execute(final MouseInstruction instruction) {
	}
}
