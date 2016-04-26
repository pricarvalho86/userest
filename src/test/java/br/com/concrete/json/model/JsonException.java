package br.com.concrete.json.model;

import java.util.Date;

//@JsonIgnoreProperties(value={"timestamp","status", "error", "exception", "message", "path"})

public class JsonException {
	
	private Date timestamp;
	
	private String status;
	
	private String error;
	
	private String exception;
	
	private String message;
	
	private String path;

	public Date getTimestamp() {
		return timestamp;
	}

	public String getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getException() {
		return exception;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

}
