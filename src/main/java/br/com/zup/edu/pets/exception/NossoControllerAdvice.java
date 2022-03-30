package br.com.zup.edu.pets.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NossoControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		BindingResult bindResult = ex.getBindingResult();
		
		List<FieldError> fieldErrors = bindResult.getFieldErrors();
		
		List<String> mensagens = new ArrayList<>();
		
		for (FieldError fieldError : fieldErrors) {
			String msg = gerarMensagemDeErro(fieldError.getField(), fieldError.getDefaultMessage());
			mensagens.add(msg);
		}
		
		return ResponseEntity.badRequest().body(mensagens);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> methodHttpMessageNotReadableException(HttpMessageNotReadableException ex){
		
		
		String msg = gerarMensagemDeErro("tipo", "Não possui valores aceitos. Permitidos somente CAO e GATO");
		
		
		return ResponseEntity.badRequest().body(msg);
	}
	
	
	public String gerarMensagemDeErro(String campo, String mensagem) {
		return String.format("Campo %s %s", campo, mensagem);
	}
	
}
