package com.example.model;

import lombok.Data;

@Data
public class Response {
	
	
	private boolean status;
	private String message;
	
	public Response(boolean status, String message) {
			this.status=status;
			this.message=message;
		}
	
	

}


