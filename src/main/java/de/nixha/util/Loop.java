package de.nixha.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * 
 * @author Hans Nix
 * 
 * @param <E>
 */
public class Loop<E> implements Collection<E>, Serializable {

	private static final long serialVersionUID = -2597518399040551254L;

	private int index;
	private Vector<E> elements;
	private E focElement;

	public Loop() {
		elements = null;
		focElement = null;
		init();
	}

	private synchronized void init() {
		index = -1;
		elements = new Vector<E>();
		focElement = null;
	}

	public synchronized boolean add(E e) {
		if (e == null) {
			throw new NullPointerException(Loop.class.getSimpleName() + " does not support null elements: e=" + e);
		}

		if (contains(e)) {
			return false;
		} else {
			int oldSize = size();

			Object oldElement = get();

			elements.add(index + 1, e);

			next();

			return oldSize != size() || oldElement != get();
		}
	}

	public synchronized boolean addAll(Collection<? extends E> c) {
		boolean changed = false;
		Iterator<? extends E> i = c.iterator();
		do {
			if (!i.hasNext()) {
				break;
			}

			E e = i.next();

			Object old = get();

			add(e);

			if (old != get()) {
				changed = true;
			}
		} while (true);

		return changed;
	}

	public synchronized E get() {
		return focElement;
	}

	public synchronized E next() {
		if (elements.isEmpty()) {
			index = -1;
			focElement = null;
		} else if (elements.size() == 1) {
			index = 0;
			focElement = elements.get(index);
		} else {
			if (index == elements.size() - 1) {
				index = 0;
			} else {
				index++;
			}

			focElement = elements.get(index);
		}

		return focElement;
	}

	public synchronized List<E> roundtrip() {
		List<E> roundtrip = new ArrayList<E>();

		if (isEmpty()) {
			return roundtrip;
		}

		Object start = get();
		do {
			if (get() != null)
				roundtrip.add(get());
		} while (next() != start);

		return roundtrip;
	}

	public synchronized Object focus(E e) {
		if (e == null) {
			throw new NullPointerException(Loop.class.getSimpleName() + " does not support null elements: e=" + e);
		}

		if (!contains(e)) {
			throw new IllegalArgumentException("Element not in loop: e=" + e);
		}

		int newIndex = elements.indexOf(e);
		if (newIndex < 0) {
			return get();
		} else {
			index = newIndex;

			focElement = elements.get(index);

			return focElement;
		}
	}

	public synchronized int size() {
		return elements.size();
	}

	public synchronized void clear() {
		index = -1;
		elements.clear();
		focElement = null;
	}

	public synchronized boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException(Loop.class.getSimpleName() + " does not support null elements: o=" + o);
		} else {
			return elements.contains(o);
		}
	}

	public synchronized boolean containsAll(Collection<?> col) {
		boolean contains = true;

		for (Object o : col) {
			contains = !contains ? contains : contains(o);
		}

		return contains;
	}

	public synchronized boolean isEmpty() {
		return elements.isEmpty();
	}

	public synchronized String toString() {
		return Loop.class.getSimpleName() + " " + Arrays.toString(toArray());
	}

	public synchronized Object[] toArray() {
		return roundtrip().toArray();
	}

	@SuppressWarnings("unchecked")
	public synchronized Object[] toArray(Object[] a) {
		return roundtrip().toArray(a);
	}

	public Iterator<E> iterator() {
		return roundtrip().iterator();
	}

	public synchronized boolean remove(Object o) {
		if (o == null) {
			throw new NullPointerException(Loop.class.getSimpleName() + " does not support null elements: o=" + o);
		}

		if (get() == o) {
			next();
		}

		boolean changed = elements.remove(o);
		if (changed) {
			if (elements.isEmpty()) {
				index = -1;
				focElement = null;
			} else if (elements.size() == 1) {
				index = 0;
				focElement = elements.get(index);
			} else {
				index = elements.indexOf(focElement);
			}
		}

		return changed;
	}

	public boolean removeAll(Collection<?> c) {
		boolean changed = false;

		for (Object o : c) {
			changed = remove(o) ? true : changed;
		}

		return changed;
	}

	public boolean retainAll(Collection<?> c) {
		boolean changed = false;

		Iterator<E> iterator = iterator();
		E e;
		while (iterator.hasNext()) {
			e = iterator.next();

			if (!c.contains(e)) {
				changed = remove(e) ? true : changed;
			}
		}

		return changed;
	}

}
