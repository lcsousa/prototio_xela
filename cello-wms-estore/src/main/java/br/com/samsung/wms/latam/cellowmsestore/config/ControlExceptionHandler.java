package br.com.samsung.wms.latam.cellowmsestore.config;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.samsung.wms.latam.cellowmsestore.exception.BusinessException;
import br.com.samsung.wms.latam.cellowmsestore.exception.ExceptionResolver;
import br.com.samsung.wms.latam.cellowmsestore.exception.SecurityLocalException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@ControllerAdvice
public class ControlExceptionHandler {

	public static final String CONSTRAINT_VALIDATION_FAILED = "Constraint validation failed";

	@ExceptionHandler(value = { BusinessException.class})
	protected ResponseEntity<Object> handleConflict(BusinessException ex, WebRequest request) {
		HttpHeaders responseHeaders = new HttpHeaders();
		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}
	
	@ExceptionHandler(value = { AccessDeniedException.class})
	protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException eThrowable, WebRequest request) {
		
		SecurityLocalException ex = SecurityLocalException.builder()
				.httpStatusCode(HttpStatus.FORBIDDEN)
				.code(String.valueOf(HttpStatus.FORBIDDEN.value()))
				.message("Falha na Autorização.")
				.description("Usuário não possui permissão para acessar a URL solicitada" )
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
		
		/*
		 * HttpHeaders responseHeaders = new HttpHeaders(); return
		 * ResponseEntity.status(HttpStatus.FORBIDDEN).headers(responseHeaders).body(new
		 * BusinessExceptionBody(HttpStatus.FORBIDDEN.toString(), "Acesso Negado",
		 * CONSTRAINT_VALIDATION_FAILED));
		 */
	}

	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<Object> handleException(Throwable eThrowable) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.message(Optional.ofNullable(eThrowable.getMessage()).orElse(eThrowable.toString()))
				.description(ExceptionResolver.getRootException(eThrowable))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exMethod,
																   WebRequest request) {

		String error = exMethod.getName() + " should be " + exMethod.getRequiredType().getName();

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(error)
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	/**
	 *
	 * @param exMethod
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exMethod, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : exMethod.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(errors.toString())
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	/**
	 *
	 * @param exMethod
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> validationError(MethodArgumentNotValidException exMethod) {

		BindingResult bindingResult = exMethod.getBindingResult();

		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		List<String> fieldErrorDtos = fieldErrors.stream()
				.map(f -> f.getField().concat(":").concat(f.getDefaultMessage())).map(String::new)
				.collect(Collectors.toList());

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(fieldErrorDtos.toString())
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	/**
	 *
	 * @param exMethod
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> validationError(HttpMessageNotReadableException exMethod) {
		String message = "";
		try {
			message = "Valor inválido no campo ".concat(exMethod.getMessage()
					.substring(exMethod.getMessage().lastIndexOf("[\"") + 2, exMethod.getMessage().lastIndexOf("\"]")));
		} catch (StringIndexOutOfBoundsException e) {
			message = "JSON inválido";
		}

		BusinessException ex = BusinessException.builder().httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED).description(message).build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Object> handleException(MissingServletRequestParameterException e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.description(ExceptionResolver.getRootException(e))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}

	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException  e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.description(ExceptionResolver.getRootException(e))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	
	   @Data
	    @Builder
	    @NoArgsConstructor
	    @AllArgsConstructor
	    @JsonInclude(JsonInclude.Include.NON_NULL)
	    public static class BusinessExceptionBody {
	        private String code;

	        private String message;

	        private String description;

	    }

}
