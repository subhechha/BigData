package lab3;

public class Main {

	public static void main(String[] args) {
		String[] files = new String[3];
		files[0] = "C:/Bigdata/labs/src/lab3/testinput1.txt";
		files[1] = "C:/Bigdata/labs/src/lab3/testinput2.txt";
		files[2] = "C:/Bigdata/labs/src/lab3/testinput3.txt";
		try{
			WordCount wc = new WordCount(files, 2);
			wc.start();
		}catch(Exception ex){
			System.out.println("error: "+ex.getMessage());
		}
}

}
