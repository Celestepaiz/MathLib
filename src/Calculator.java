import math.Matrix;

public class Calculator {
	
	public static void main(String[] args) {
//		Bruch b1 = new Bruch(0,0, true);
//		Bruch b2 = new Bruch(8,4);
//		b1.addieren(b2);;
//		System.out.println(b1);
		//Matrix beispiel 1:
//		int[] zahlen = {1, 0, -1, 2, 0, 2, 1 ,1};
//		int[] zahlen2 = {1, 0, 5, 2, 4, -1, 3, 0};
//		Bruch[] brüche ={new Bruch(1), new Bruch(1), new Bruch(1) ,new Bruch(2)};
//		Bruch[] brüche2 ={new Bruch(1), new Bruch(3), new Bruch(2) ,new Bruch(1)};
//		Matrix a = new Matrix(4,2, zahlen);
//		a.darstellen();
//		Matrix b = new Matrix(2,4, zahlen2);
//		b.darstellen();
//		System.out.println();
//		a.darstellen();
//		a.multiplizieren(b).darstellen();
//		System.out.println();

		int[] zahlen3 = {1 ,0 ,5 ,2 ,7 ,-2 ,1 ,-2 ,8 ,-2 ,6 ,0 ,5 ,-1 ,8 ,2};
		int[] zahlen4 = {0, 2, 3, 4, 1, 0, -5, -1};
		Matrix a2 = new Matrix(4,4, zahlen3);
		Matrix b2 = new Matrix(4, 2, zahlen4);
		a2.multiplizieren(b2).darstellen();
	}
}