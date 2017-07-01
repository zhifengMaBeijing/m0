package com.bfd.control;

import java.io.Serializable;

public class ResultData<T> extends ResultInfo implements Serializable{

	private static final long serialVersionUID = 6300337442870893234L;

	private T data = null;

	public T getData() {

		return this.data;
	}
	
	public void setData(T data) {

		this.data = data;
	}
	
	public ResultData() {

	}
	
	public ResultData(int retcode, String retdesc) {

		super(retcode, retdesc);
	}
	
	public ResultData(int retcode, String retdesc, T data) {

		super(retcode, retdesc);
		this.data = data;
	}
}
