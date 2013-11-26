package sk.kapusta.enums;

public enum UserStatus {
	
	PENDING("PENDING"), ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), SYSTEM("SYSTEM"), DELETED("DELETED");
 
	private String statusCode;
 
	private UserStatus(String s) {
		statusCode = s;
	}
 
	public String getStatusCode() {
		return statusCode;
	}
 
}