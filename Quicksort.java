import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.lang.Thread;

import org.omg.PortableServer.THREAD_POLICY_ID;

public class Quicksort extends RecursiveAction {

	private static long insertionSort(final int[] arr, final int start,
			final int end) {
		long startTime = System.currentTimeMillis();
		for (int i = start + 1; i < end; ++i) {
			int j = i;
			int tmp = arr[i];
			while (j > 0 && arr[j - 1] > tmp) {
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = tmp;
		}
		long estimateTime = System.currentTimeMillis() - startTime;
		return estimateTime;
	}

	// Quicksort (in pseudo code):
	// fun qs(arr):
	// pivot = some element in arr (e.g., arr[0])
	// left = { x | x <- arr if x < pivot}
	// right = { x | x <- arr if x > pivot}
	// return qs(left) + {pivot} + qs(right)
	//
	// NOTE: numbers equal to pivot may be included in either left or right.
	//

	private static long sQsort(final int[] arr, final int start, final int end) {
		long startTime = System.currentTimeMillis();
		int left = start;
		int right = end + 1;
		// We simply pick the first element as pivot..
		final int pivot = arr[start];
		int tmp;

		// Rearranging the elements around the pivot, so that
		// elements smaller than the pivot end up to the left
		// and elements bigger than the pivot end up to the
		// right.
		do {

			// As long as elements to the left are less than
			// the pivot we just continue.
			do {
				left++;
			} while (left <= end && arr[left] < pivot);

			// As long as the elements to the right are
			// greater than the pivot we just continue.
			do {
				right--;
			} while (arr[right] > pivot);

			// If left is less than right we have values on
			// the wrong side of the pivot, so we swap them.
			if (left < right) {
				tmp = arr[left];
				arr[left] = arr[right];
				arr[right] = tmp;
			}

			// We continue doing this until all elements are
			// arranged correctly around the pivot.
		} while (left <= right);

		// Now put the pivot in the right place.
		tmp = arr[start];
		arr[start] = arr[right];
		arr[right] = tmp;

		// We have now "split" the range arr[start, end] into
		// two parts around the pivot value. We recurse to
		// sort those parts.
		if (start < right) {
			sQsort(arr, start, right);
		}
		if (left < end) {
			sQsort(arr, left, end);
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
		return estimatedTime;
	}

	public static long sQsort(final int[] arr) {
		if (arr.length > 100)
			return sQsort(arr, 0, arr.length - 1);
		else
			return insertionSort(arr, 0, arr.length - 1); // Ta reda på när det
															// ena är bättre än
															// det andra.
	}

	public static long insertionsort(final int[] arr) {
		return insertionSort(arr, 0, arr.length - 1);
	}

	public boolean isSorted(final int[] arr) {
		if (arr.length == 1) {
			return true;
		}

		int index = 0;
		while (index != arr.length - 1) {
			if (arr[index] > arr[index + 1]) {
				return false;
			}
			index++;
		}
		return true;
	}

	public static void pQsort(final int[] arr) {
		PQuick sort = new PQuick(arr, 0, arr.length - 1);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(sort);
	}

	@Override
	protected void compute() {
		// TODO Auto-generated method stub

	}

}
