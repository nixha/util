package de.nixha.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Hans Nix
 * 
 */
public class LoopTest {

	@Test
	public void testAdd() {
		Loop<String> l = new Loop<String>();

		String[] strings = new String[] { "A", "B", "C" };

		for (String string : strings) {
			l.add(string);

			Assert.assertTrue("Does not contain added string: string=" + string, l.contains(string));
			Assert.assertTrue(string == l.get());
		}
	}

	@Test
	public void testAddThrowingNullPointerException() {
		try {
			Loop<String> l = new Loop<String>();
			l.add(null);

			Assert.fail("Should have thrown a NPE");
		} catch (NullPointerException npe) {
			;
		}
	}

	@Test
	public void testAddAll() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("A");
		strings.add("B");
		strings.add("C");

		Loop<String> l = new Loop<String>();
		l.addAll(strings);

		for (String string : strings) {
			Assert.assertTrue("Must contain " + string, l.contains(string));
		}
	}

	@Test
	public void testClear() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.clear();

		Assert.assertTrue("Must be empty", l.isEmpty());
		Assert.assertTrue("Must have zero elements", l.size() == 0);
	}

	@Test
	public void testContains() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Assert.assertTrue("Does not contain A", l.contains("A"));
		Assert.assertTrue("Does not contain B", l.contains("B"));
		Assert.assertTrue("Does not contain C", l.contains("C"));
		Assert.assertFalse("Does contain D", l.contains("D"));
	}

	@Test
	public void testContainsThrowingNullPointerException() {
		try {
			Loop<String> l = new Loop<String>();
			l.contains(null);

			Assert.fail("Should have thrown a NPE");
		} catch (NullPointerException npe) {
			;
		}
	}

	@Test
	public void testContainsAll() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Collection<String> col = new ArrayList<String>();

		col.add("A");
		col.add("C");
		Assert.assertTrue("Does not contain all: col=" + Arrays.toString(col.toArray()), l.containsAll(col));

		col.add("B");
		Assert.assertTrue("Does not contain all: col=" + Arrays.toString(col.toArray()), l.containsAll(col));

		col.add("D");
		Assert.assertFalse("Does contain all: col=" + Arrays.toString(col.toArray()), l.containsAll(col));

		col.clear();
		Assert.assertTrue("Does not contain all: col=" + Arrays.toString(col.toArray()), l.containsAll(col));
	}

	@Test
	public void testFocus() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.focus("B");
		Assert.assertTrue("B should be focussed", l.get().equals("B"));

		l.focus("A");
		Assert.assertTrue("A should be focussed", l.get().equals("A"));

		l.focus("C");
		Assert.assertTrue("C should be focussed", l.get().equals("C"));
	}

	@Test
	public void testFocusReturnsObject() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Assert.assertTrue("B should be returned", l.focus("B").equals("B"));
		Assert.assertTrue("A should be returned", l.focus("A").equals("A"));
		Assert.assertTrue("C should be returned", l.focus("C").equals("C"));
	}

	@Test
	public void testFocusThrowsNullPointerException() {
		Loop<String> l = new Loop<String>();

		try {
			l.focus(null);

			Assert.fail("Should have thrown NullPointerException");
		} catch (NullPointerException npe) {
			;
		}
	}

	@Test
	public void testFocusThrowsIllegalArgumentException() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		try {
			l.focus("D");

			Assert.fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException npe) {
			;
		}
	}

	@Test
	public void testGetAfterNext() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.focus("B");
		l.next();

		Assert.assertEquals("Should return next focussed element 'C'", "C", l.get());
	}

	@Test
	public void testGetFocussed() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.focus("B");

		Assert.assertEquals("Should return focussed element 'B'", "B", l.get());
	}

	@Test
	public void testGetLastAdded() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Assert.assertEquals("Should return last added element 'C'", "C", l.get());
	}

	@Test
	public void testIsEmptyOnClearedLoop() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");
		l.clear();

		Assert.assertTrue("Cleared Loop must be empty", l.isEmpty());
	}

	@Test
	public void testIsEmptyOnEmptiedLoop() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.remove("A");
		l.remove("B");
		l.remove("C");

		Assert.assertTrue("Emptied Loop must be empty", l.isEmpty());
	}

	@Test
	public void testIsEmptyOnNewLoop() {
		Loop<String> l = new Loop<String>();

		Assert.assertTrue("New Loop must be empty", l.isEmpty());
	}

	@Test
	public void testIterator() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.focus("B");

		Iterator<String> iterator = l.iterator();
		Assert.assertNotNull("Iterator must be set: iterator=" + iterator, iterator);

		Assert.assertTrue("Iterator should have next", iterator.hasNext());
		Assert.assertTrue("Element iterator returns by next() should be B", iterator.next().equals("B"));

		Assert.assertTrue("Iterator should have next", iterator.hasNext());
		Assert.assertTrue("Element iterator returns by next() should be C", iterator.next().equals("C"));

		Assert.assertTrue("Iterator should have next", iterator.hasNext());
		Assert.assertTrue("Element iterator returns by next() should be A", iterator.next().equals("A"));
	}

	@Test
	public void testNextOnNewLoop() {
		Loop<String> l = new Loop<String>();

		String next = l.next();
		Assert.assertNull("next() on new Loop must be null: next=" + next, next);
	}

	@Test
	public void testNext() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");

		String current = null;
		for (int i = 0; i < 10; i++) {
			current = l.get();

			if (current.equals("A")) {
				Assert.assertEquals("B", l.next());
			} else

			if (current.equals("B")) {
				Assert.assertEquals("A", l.next());
			}

			else {
				Assert.fail("Unknown: current=" + current);
			}
		}
	}

	@Test
	public void testRemove() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		l.remove("A");
		Assert.assertFalse("Should not contain A", l.contains("A"));

		Assert.assertTrue("Should contain B", l.contains("B"));

		l.remove("C");
		Assert.assertFalse("Should not contain C", l.contains("C"));
	}

	@Test
	public void testRemoveReturnsBoolean() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Assert.assertTrue("Should return true", l.remove("B"));
		Assert.assertFalse("Should return false", l.remove("D"));
	}

	@Test
	public void testRemoveThrowsNullPointerException() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		try {
			l.remove(null);

			Assert.fail("Should have thrown NullPointerException");
		} catch (NullPointerException e) {
			;
		}
	}

	@Test
	public void testRemoveAll() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Collection<String> removeAll = new ArrayList<String>();
		removeAll.add("A");
		removeAll.add("C");

		l.removeAll(removeAll);
		Assert.assertFalse("Should not contain A", l.contains("A"));
		Assert.assertTrue("Should contain B", l.contains("B"));
		Assert.assertFalse("Should not contain C", l.contains("C"));
	}

	@Test
	public void testRetainAll() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Collection<String> retainAll = new ArrayList<String>();
		retainAll.add("A");
		retainAll.add("C");

		l.retainAll(retainAll);
		Assert.assertTrue("Should contain A", l.contains("A"));
		Assert.assertFalse("Should not contain B", l.contains("B"));
		Assert.assertTrue("Should contain C", l.contains("C"));
	}

	@Test
	public void testRoundtrip() {
		String[] strings = new String[] { "A", "B", "C", "D" };
		String[] expected = new String[] { "C", "D", "A", "B" };

		Loop<String> l = new Loop<String>();

		for (String string : strings) {
			l.add(string);
		}

		l.focus("C");

		List<String> roundtrip = l.roundtrip();
		for (int i = 0; i < roundtrip.size(); i++) {
			Assert.assertEquals(expected[i], roundtrip.get(i));
		}
	}

	@Test
	public void testSize() {
		Loop<String> l = new Loop<String>();

		Assert.assertEquals("Size should be 0: l.size=" + l.size(), 0, l.size());
		l.add("A");
		Assert.assertEquals("Size should be 1: l.size=" + l.size(), 1, l.size());
		l.add("B");
		Assert.assertEquals("Size should be 2: l.size=" + l.size(), 2, l.size());
		l.add("C");
		Assert.assertEquals("Size should be 3: l.size=" + l.size(), 3, l.size());
		l.remove("C");
		Assert.assertEquals("Size should be 2: l.size=" + l.size(), 2, l.size());
		l.clear();
		Assert.assertEquals("Size should be 0: l.size=" + l.size(), 0, l.size());
	}

	@Test
	public void testToArray() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		Object[] array = l.toArray();
		Assert.assertEquals("Array should have length of 3: array.length=" + array.length, 3, array.length);
		Assert.assertEquals("Array element 0 should be 'C': array[0]=" + array[0], "C", array[0]);
		Assert.assertEquals("Array element 1 should be 'A': array[1]=" + array[1], "A", array[1]);
		Assert.assertEquals("Array element 2 should be 'B': array[2]=" + array[2], "B", array[2]);
	}

	@Test
	public void testToArrayWithType() {
		Loop<String> l = new Loop<String>();
		l.add("A");
		l.add("B");
		l.add("C");

		String[] array = (String[]) l.toArray(new String[3]);
		Assert.assertEquals("Array should have length of 3: array.length=" + array.length, 3, array.length);
		Assert.assertEquals("Array element 0 should be 'C': array[0]=" + array[0], "C", array[0]);
		Assert.assertEquals("Array element 1 should be 'A': array[1]=" + array[1], "A", array[1]);
		Assert.assertEquals("Array element 2 should be 'B': array[2]=" + array[2], "B", array[2]);
	}

	// TODO: testToString()
}
