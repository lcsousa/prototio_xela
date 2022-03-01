package br.com.samsung.wms.latam.cellowmsestore.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum MessageBusiness {
    FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "01", "Campo obrigatório não informado", "%s"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"01","Erro interno.","%s"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"01","Not Found","Registro(s) não encontrado(s)."),
    NUMBER_0_REQUIRED(HttpStatus.BAD_REQUEST, "01", "Erro de Validação", "Campo  nome deve conter ao menos um caractere 0"),
	REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "01", "Token Inválido", "O Token não é um refresh token válido."),
	LOGIN_OR_PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "01", "Usuário Inválido", "Login e/ou senha inválidos.");

    MessageBusiness(HttpStatus s, String c, String m, String d){
        this.status = s;
        this.code = c;
        this.message = m;
        this.description = d;
    }

    private HttpStatus status;
    private String code;
    private String message;
    private String description;

    public String formatDescription(String ... strings){
        return String.format(this.description, strings);
    }

    public void validNull(Object obj, String ... datas){
        Optional.ofNullable(obj).orElseThrow(() -> this.createException(datas));
    }


    public BusinessException createException(String ... strings){
        log.info("Classe {},  mensagem={}", BusinessException.class.getName(), this.message);

        return BusinessException.builder()
                .httpStatusCode(this.getStatus())
                .code(this.code)
                .message(this.getMessage())
                .description(this.formatDescription(strings)).build();
    }

}
