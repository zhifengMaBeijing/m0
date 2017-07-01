package com.bfd.control;

import java.io.Serializable;

public class ResultInfoObject<T> extends ResultInfo implements Serializable{

	private static final long serialVersionUID = 6300337442870893052L;

	private T object = null;

	public T getObject() {

		return this.object;
	}
	
	public void setObject(T object) {

		this.object = object;
	}
	
	public ResultInfoObject() {

	}
	
	public ResultInfoObject(int retcode, String retdesc) {

		super(retcode, retdesc);
	}
	
	public ResultInfoObject(int retcode, String retdesc, T object) {

		super(retcode, retdesc);
		this.object = object;
	}
}
