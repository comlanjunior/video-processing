package test;

import java.util.ArrayList;

public class HeapList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int maxCapacity;

	public HeapList(int maxCapacity) {
		super();
		this.maxCapacity = maxCapacity;
		// TODO Auto-generated constructor stub
	}

	public void queue(E e) {
		super.add(0, e);
		
		while (super.size() > maxCapacity) {
			super.remove(maxCapacity);
		}
	}

}