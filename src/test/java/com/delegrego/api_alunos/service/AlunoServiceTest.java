package com.delegrego.api_alunos.service;

import java.time.LocalDate;

import com.delegrego.api_alunos.exception.AlunoNaoEncontradoException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.delegrego.api_alunos.dto.AlunoRequest;
import com.delegrego.api_alunos.dto.AlunoResponse;
import com.delegrego.api_alunos.exception.EmailCadastradoException;

public class AlunoServiceTest {

	private AlunoService service;
	
	@BeforeEach
	void setUp() {
		service = new AlunoService();
	}

	@Test
	void deveCadastrarAlunoQuandoDadosForemValidos() {
		// Arrange
		// service = new AlunoService();

		AlunoRequest request = new AlunoRequest("Henrique", "henrique@email.com", "senha", LocalDate.of(2001, 10, 11),
				8);

		// Act
		AlunoResponse response = service.cadastrarAluno(request);

		// Assert
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getId()).isGreaterThan(0);

	}

	@Test
	void deveLancarExecaoAoCadastrarEmailDuplicado() {

		// service = new AlunoService();

		AlunoRequest request = new AlunoRequest("Henrique", "henrique@email.com", "senha", LocalDate.of(2001, 10, 11),
				8);

		AlunoRequest request2 = new AlunoRequest("Henrique", "henrique@email.com", "senha", LocalDate.of(2001, 10, 11),
				8);

		service.cadastrarAluno(request);

		Assertions.assertThatThrownBy(() -> 
		service.cadastrarAluno(request2))
		.isInstanceOf(EmailCadastradoException.class)
		.hasMessage("Email já cadastrado");

	}
	@Test
	void deveExcluirAlunoQuandoIdExistir(){

		AlunoRequest aluno = new AlunoRequest("Henrique", "henrique@gmial.com","senha",LocalDate.of(2001,10,11),8);
		AlunoResponse alunoCadastrado = service.cadastrarAluno(aluno);

		service.excluirAluno(alunoCadastrado.getId());

		Assertions.assertThatThrownBy(() ->
				service.obterAlunoPorId(alunoCadastrado.getId())).isInstanceOf(AlunoNaoEncontradoException.class);
	}
	@Test
	void deveAtualizarAlunoQuandoDadosForemValidos(){

		AlunoRequest aluno = new AlunoRequest("Henrique", "henrique@gmial.com","senha",LocalDate.of(2001,10,11),8);
		AlunoResponse alunoCadastrado = service.cadastrarAluno(aluno);

		AlunoRequest alunoAtualizado = new AlunoRequest("Henrique Novo", "henriqueNovo@gmial.com","123456",LocalDate.of(1999,11,30),10);

		AlunoResponse response = service.atualizarAluno(alunoCadastrado.getId(),alunoAtualizado);

		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getNome()).isEqualTo("Henrique Novo");
		Assertions.assertThat(response.getEmail()).isEqualTo("henriqueNovo@gmial.com");
		Assertions.assertThat(response.getMedia()).isEqualTo(10);
	}
	@Test
	void deveRetornarExcecaoQuandoAtualizarAlunoNaoEncotrado(){

		AlunoRequest aluno = new AlunoRequest("Henrique", "henrique@gmial.com","senha",LocalDate.of(2001,10,11),8);

		Assertions.assertThatThrownBy(() -> service.atualizarAluno(1,aluno))
				.isInstanceOf(AlunoNaoEncontradoException.class);

	}

}
