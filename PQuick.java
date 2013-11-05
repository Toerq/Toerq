import java.util.concurrent.RecursiveAction;

public class PQuick extends RecursiveAction {
	int counter = 0;
	int[] arr;
	int left;
	int right;
	final int THRESHOLD = 20;

	PQuick(int[] arr, int start, int end) {
		this.arr = arr;
		this.left = start;
		this.right = end;
	}

	@Override
	protected void compute() {
		// We simply pick the first element as pivot..
		final int start = left;
		final int end = right + 1;
		int tmp;
		int pivot = arr[start];
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
				assert(right > 0);
				assert(right < arr.length);
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
		counter++;
		printAll(start, end, pivot, left, right, counter);
		invokeAll(new PQuick(arr, start, pivot - 1),
				new PQuick(arr, pivot + 1, end - 1));
		
		
		
		
		// We have now "split" the range arr[start, end] into
		// two parts around the pivot value. We recurse to
		// sort those parts.
		// boolean parFlag = false;
		// if (left < right) {
		// if (right - left > THRESHOLD && left < right) {
		// parFlag = true;
		// invokeAll(new PQuick(arr, left, right), new PQuick(arr, right,
		// right));
		// } else
		// invokeAll(new PQuick(arr, left, right));
		// }
		//
		// if (left < right && !parFlag) {
		// invokeAll(new PQuick(arr, left, right));
		// }
	}
	
	public static void printArr(int[] a) {
		String string = "[" + a[0];
		for (int i = 1; i < a.length; ++i) {
			string += ", " + a[i];
		}
		string += "]";
		System.out.println(string);
	}
	
	public static void printAll(int start, int end, int pivot, int left, int right, int counter) {
		System.out.printf("Counter: %d\nStart: %d\nEnd: %d\nLeft: %d\nRight: %d\nPivot: %d\n--------------------\n", counter, start, end, left, right, pivot);
	}
}
