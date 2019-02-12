package com.love.family.utils;

public class MyBusinessException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
	
	private ErrorType  errorType = ErrorType.UNKNOW;
	
	private String errorMessage = null;
	
	/**自定义异常构造器，传入错误类型和源异常*/
	public MyBusinessException(ErrorType errorType, Throwable t){
		super(t);
		this.errorType = errorType;
		this.errorMessage = "["+errorType.getMessage()+ "]";
	}
	
	
	/**自定义异常构造器，传入错误信息和源异常*/
	public MyBusinessException(String errorMessage, Throwable t){
		super(t);
		this.errorMessage = errorMessage;
	}
	
	
	/**自定义异常构造器，传入异常类型、错误信息、源异常*/
	public MyBusinessException(ErrorType errorType,String errorMessage, Throwable t){
		super(t);
		this.errorType = errorType;
		this.errorMessage = "[" +errorType.getMessage()+"]-" + errorMessage;
	}
	
	/**自定义异常构造器，传入异常类型、错误信息*/
	public MyBusinessException(ErrorType errorType,String errorMessage){
		this.errorType = errorType;
		this.errorMessage = "[" +errorType.getMessage()+ "]-" + errorMessage;
	}
	
	/**自定义异常构造器，传入错误信息*/
	public MyBusinessException(String errorMessage){
		this.errorMessage = errorMessage;
	}
 
 
	public ErrorType getErrorType() {
		return errorType;
	}
 
 
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
 
 
	public String getErrorMessage() {
		return errorMessage;
	}
 
 
	/**
	 * 返回异常信息
	 */
	public String getMessage(){
		if(this.errorMessage != null){
			return this.errorMessage;
		}else{
			return "未知的错误";
		}
	}
	
	public String toString(){
		String s = getClass().getName();
		String message = getMessage();
		return (message != null) ? s + ":" + message : s;
	}
	
 
	
}
