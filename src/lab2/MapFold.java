package lab2;
// Question number 2 nice array
public class MapFold {
 
	public static int f(int x){
		if(x==3)
			return 1;
		if(x==4)
			return -1;
		else return 0;
					
	}
	
	public static int g(int x, int y){
		if(x==1 || (x==-1 && y==0))
			return x;
		return y;
	}

	
	public static int isNice(int n) {
		if (n < 2) return 0;
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) return 0;
		}
		return 1;
	}	
	
		
	public static void main(String[] args) {
		int[] a = {3, 6, 2, 3, 4}   ; // not nice: {7, 6, 2, 4, 1} // nice: [7, 6, 2, 3, 1]   // nice:[3, 6, 2, 3, 4]   
		int[] b = new int[a.length];
		int[] c = new int[a.length];
		
		for (int i = 0; i < a.length; i++){
			b[i] = f(a[i]);
		}
		
		int x = 0;
		for (int i = 0; i < a.length; i++){
			x = g(x, b[i]);
			c[i] = x;
		}
		for (int i = 0; i < a.length; i++)
			System.out.print("\t"+ a[i] + " ");
		System.out.println();
		for (int i = 0; i < a.length; i++)
			System.out.print("\t"+ b[i] + " ");
		System.out.println();
		for (int i = 0; i < a.length; i++)
			System.out.print("\t"+ c[i] + " ");
		System.out.println();		
	}
}
