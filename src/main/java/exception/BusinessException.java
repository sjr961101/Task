package exception;


public class BusinessException extends Exception {


	private Integer code;

	private String message;

	public BusinessException() {
		super();
	}

	public BusinessException(Integer code) {
		super();
		this.code = code;
	}

	public BusinessException(String message) {
		super(message);
		this.message = message;
	}

	public BusinessException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

}
