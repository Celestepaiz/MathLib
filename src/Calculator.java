import math.Matrix;
import math.Fraction;

public class Calculator {
	
	public static void main(String[] args) {
		Fraction b1 = new Fraction(4,4, true);
		Fraction b2 = new Fraction(1,4);
		Fraction result = b1.add(b2).add(b2);
		System.out.println(result);
		
		//Matrix beispiel1:
		int[] zahlen = {1, 0, -1, 2, 0, 2, 1 ,1};
		int[] zahlen2 = {1, 0, 5, 2, 4, -1, 3, 0};
		Matrix a = new Matrix(4,2, zahlen);
		a.print();
		Matrix b = new Matrix(2,4, zahlen2);
		System.out.println("Matrix B:");
		b.print();
		
		b.swapRows(1, 2);
		System.out.println("1: Matrix B 1 und 2 vertauscht:");
		b.print();
		
		b.multiplyRow(1, 2);
		System.out.println("2: Matrix B 1. Zeile x2:");
		b.print();
		
		b.multiplyRow(1, new Fraction(1,2));
		System.out.println("3: Matrix B wie 1):");
		b.print();

		
		int[] zahlenC = {1, 1, 1, 1};
		Matrix c = new Matrix(2,2,zahlenC);
		System.out.println("\nMatrix C:");
		c.print();
		c.addMultipleOfRow(1, 1, 2);
		System.out.println("2. Zeile + 1x 1.Zeile:");
		c.print();
		c.addMultipleOfRow(2, 2, 1);
		System.out.println("1. Zeile + 2x 2.Zeile:");
		c.print();
		
		Matrix cCopy = c.clone();
		System.out.println("1. Zeile - 3x 2.Zeile:");
		c.subtractMultipleOfRow(2, 3, 1);
		cCopy.addMultipleOfRow(2, -3, 1);
		c.print();
		System.out.println("Und 1.Zeile + (-3)x 2.Zeile:");
		cCopy.print();
//		int[] zahlen3 = {1 ,0 ,5 ,2 ,7 ,-2 ,1 ,-2 ,8 ,-2 ,6 ,0 ,5 ,-1 ,8 ,2};
//		int[] zahlen4 = {0, 2, 3, 4, 1, 0, -5, -1};
//		Matrix a2 = new Matrix(4,4, zahlen3);
//		Matrix b2 = new Matrix(4, 2, zahlen4);
//		a2.multiplizieren(b2).darstellen();
	}
}