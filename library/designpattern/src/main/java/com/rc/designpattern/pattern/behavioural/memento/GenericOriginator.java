package com.rc.designpattern.pattern.behavioural.memento;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class GenericOriginator<T> implements Originator<T> {

	private T state;

	public GenericOriginator(T state) {
		super();
		this.state = state;
	}

	public void setState(T state) {
		this.state = state;
	}

	@Override
	public Memento<T> saveToMemento() {
		return new GenericMemento<T>(state);
	}

	@Override
	public void restoreFromMemento(Memento<T> memento) {
		state = memento.getState();
	}

	@Override
	public T getState() {
		return state;
	}
}