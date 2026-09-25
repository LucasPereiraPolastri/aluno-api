package com.delegrego.api_alunos.exception;

import java.time.Instant;

public record ErroResponse(
		int status,
		String mensagem,
		Instant timestamp
) {}
