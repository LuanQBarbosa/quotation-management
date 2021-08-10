package br.inatel.quotationmanagement.config.validation;

public class ErrorFormDto {

	private String field;
	private String error;
	
	public ErrorFormDto(String field, String error) {
		this.field = field;
		this.error = error;
	}

	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}
	
}
