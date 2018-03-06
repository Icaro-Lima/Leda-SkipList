import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import adt.skipList.SkipList;
import adt.skipList.SkipListImpl;
import adt.skipList.SkipListNode;

public class SkipListTest {

	@Test
	public void testHeight() {
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			int maxHeight = 1 + rand.nextInt(20);

			Map<Integer, Integer> list = new HashMap<>();
			SkipList<Integer> skip = new SkipListImpl<>(maxHeight);
			List<Integer> heights = new ArrayList<>();
			for (int j = 0; j < 100; j++) {
				int ch = rand.nextInt(4);
				if (ch == 0) {
					int key = rand.nextInt(100);
					int value = rand.nextInt(200);
					int height = 1 + rand.nextInt(maxHeight);

					System.out.println(String.format("Add: key = %d, value = %d, height = %d.", key, value, height));

					if (!list.containsKey(key)) {
						heights.add(height);
						heights.sort(null);
					}

					list.put(key, value);
					skip.insert(key, value, height);
				} else if (ch == 1) {
					int key = rand.nextInt(100);

					System.out.println(String.format("Remove: key = %d.", key));

					if (list.containsKey(key)) {
						heights.remove((Integer) skip.search(key).height());
					}

					list.remove((Integer) key);
					skip.remove(key);
				} else if (ch == 2) {
					int key = rand.nextInt(100);

					if (list.containsKey(key)) {
						assertEquals(list.get(key), skip.search(key).getValue());
					} else {
						assertNull(skip.search(key));
					}
				} else {
					if (heights.size() > 0) {
						assertEquals((int) heights.get(heights.size() - 1), skip.height());
					} else {
						assertEquals(0, skip.height());
					}
				}
			}

			System.out.println("//////////////////////////////////////////////////////////");
		}
	}

}
