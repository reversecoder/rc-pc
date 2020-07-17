package com.rc.designpattern.pattern.behavioural.memento;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class GenericMemento<T> implements Memento<T> {

	private final T state;
	public boolean undone;
	
	public GenericMemento(T state) {
		super();
		this.state = state;
		this.undone = false;
	}
	
	@Override
	public T getState() {
		return state;
	}

	public boolean isUndone() {
		return undone;
	}

	public void switchUndone(){
		undone = !undone;
	}
}