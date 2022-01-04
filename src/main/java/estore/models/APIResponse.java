package estore.models;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {

	protected LocalDateTime timestamp;
	protected int statusCode;
	protected HttpStatus status;
	protected String reason;
	protected String clientMessage;
	protected String developerMessage;
	protected Map<?, ?> data;
	
}