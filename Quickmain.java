public class Quickmain {

	public static void main(String args[]) {
		int[] sorted = {0,1,2,3,4,5,6,7,8,9};
		int[] unsorted = {9,8,7,6,5,4,3,2,1};
		boolean bool;
		Quicksort q = new Quicksort();
		printArr(unsorted);
		System.out.println(q.isSorted(unsorted));
		Quicksort.sQsort(unsorted);
		System.out.println(q.isSorted(unsorted));
	       
		printArr(unsorted);
	}
	
	
	
	
	public static void printArr(int[] a) {
		String string = "[" + a[0];
		for (int i = 1; i < a.length; ++i) {
			string += ", " + a[i];
		}
		string += "]";
		System.out.println(string);
	}
    public static void printArr(
}
asdasdasdasd
