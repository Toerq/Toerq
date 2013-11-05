import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

public class Quickmain {
	static int qq;

	public static void main(String args[]) throws InterruptedException {
		
		Quicksort q = new Quicksort();
		int[] qq = randomArray(3);
		q.pQsort(qq);
		System.out.println(q.isSorted(qq));
	}

	
	public static void printArr(int[] a) {
		String string = "[" + a[0];
		for (int i = 1; i < a.length; ++i) {
			string += ", " + a[i];
		}
		string += "]";
		System.out.println(string);
	}

	public static int[] randomArray(int n) {
		int arr[] = new int[n];
		for (int i = 0; i < n; ++i) {
			arr[i] = (int) (Math.random() * 10);
		}
		return arr;
	}

	public static void add(int[] n) {
		for (int element : n) {
			qq += element;
		}
	}
}
