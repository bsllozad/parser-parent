package com.ef.config.exception;

public class WalletHubException extends RuntimeException {

	private static final long serialVersionUID = 3051651502864601190L;
	
	public WalletHubException() {
		
	}
	
	public WalletHubException(String message) {
		super(message);
	}
	
	public WalletHubException(Throwable cause) {
		super(cause);
	}
	
	public WalletHubException(String message, Throwable cause) {
		super(message, cause);
	}

}
