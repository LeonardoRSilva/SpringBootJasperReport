package br.fpu.guilherme.entities;

public class Filtro<T, E> {

	public T parametro1;
	public E parametro2;
	public T parametro3;

	public Filtro() {

	}

	public Filtro(T parametro1, E parametro2, T parametro3) {
		super();
		this.parametro1 = parametro1;
		this.parametro2 = parametro2;
		this.parametro3 = parametro3;
	}

	public T getParametro1() {
		return parametro1;
	}

	public void setParametro1(T parametro1) {
		this.parametro1 = parametro1;
	}

	public E getParametro2() {
		return parametro2;
	}

	public void setParametro2(E parametro2) {
		this.parametro2 = parametro2;
	}

	public T getParametro3() {
		return parametro3;
	}

	public void setParametro3(T parametro3) {
		this.parametro3 = parametro3;
	}

}
