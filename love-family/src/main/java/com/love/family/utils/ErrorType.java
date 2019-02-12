package com.love.family.utils;

public enum ErrorType {
	UNKNOW("未知错误"),

	DATA("数据错误"),

	VALIDATE("校验错误");

	private String message;// 异常信息

	private ErrorType(String str) {
		this.message = str;
	}

	public String getMessage() {
		return message;
	}

}
