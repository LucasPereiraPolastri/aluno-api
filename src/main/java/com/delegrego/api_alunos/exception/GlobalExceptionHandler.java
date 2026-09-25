package com.delegrego.api_alunos.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AlunoNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleAlunoNaoEncontradoException(AlunoNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse(404, ex.getMessage(), Instant.now()));
	}

	@ExceptionHandler(EmailCadastradoException.class)
	public ResponseEntity<String> EmailCadastradoException(EmailCadastradoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

}
