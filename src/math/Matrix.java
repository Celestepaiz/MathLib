package math;

import java.util.Vector;

public class Matrix {
	
	/**
	 * Erstellt eine n x k matrix
	 * @param n anzahl Zeilen
	 * @param k anzahl Spalten
	 */
	private int spalten;
	private int zeilen;
	private Fraction[][] inhalt;
	
	public Matrix(int n, int k){
		spalten = n;
		zeilen = k;
		inhalt = new Fraction[n][k];
		mitNullFüllen();
	}
	
	public Matrix(int n, int k, Vector<Integer> inhalt){
		zeilen = n;
		spalten = k;
		this.inhalt = new Fraction[n][k];
		if(inhalt.size() != n*k){
			mitNullFüllen();
		}
		else{
			int insertIndex = 0;
			for(int i = 0 ; i < zeilen; i++){
				for(int t = 0; t < spalten; t++){
					this.inhalt[i][t] = new Fraction(inhalt.get(insertIndex));
					insertIndex++;
				}
			}
		}
	}
	
	public Matrix(int n, int k, int[] inhalt){
		zeilen = n;
		spalten = k;
		this.inhalt = new Fraction[n][k];
		if(inhalt.length != n*k){
			mitNullFüllen();
		}
		else{
			int insertIndex = 0;
			for(int i = 0 ; i < zeilen; i++){
				for(int t = 0; t < spalten; t++){
					this.inhalt[i][t] = new Fraction(inhalt[insertIndex]);
					insertIndex++;
				}
			}
		}
	}
	
	public Matrix(int n, int k, Fraction[] inhalt){
		zeilen = n;
		spalten = k;
		this.inhalt = new Fraction[n][k];
		if(inhalt.length != n*k){
			mitNullFüllen();
		}
		else{
			int insertIndex = 0;
			for(int i = 0 ; i < zeilen; i++){
				for(int t = 0; t < spalten; t++){
					this.inhalt[i][t] = inhalt[insertIndex];
					insertIndex++;
				}
			}
		}
	}
	
	public Matrix(Fraction[][] inhalt){
		this.zeilen = inhalt.length;
		this.spalten = inhalt[1].length;
		this.inhalt = inhalt;
	}
	
	public int getAnzahlSpalten(){
		return spalten;
	}
	
	public int getAnzahlZeilen(){
		return zeilen;
	}
	
	public Fraction[][] getInhalt(){
		return inhalt;
	}
	
	/**
	 * Füllt alle Einträge in der Matrix mit 0
	 */
	public void mitNullFüllen(){
		for(int i = 0; i < zeilen; i++){
			for(int t = 0; t < spalten; t++){
				this.inhalt[i][t] = new Fraction(0);
			}
		}
	}
	
	public void addieren(Matrix andereMatrix){
		if(spalten == andereMatrix.spalten && zeilen == andereMatrix.zeilen){
			for(int i = 0; i < zeilen; i++){
				for(int t = 0; t < spalten; t++){
					this.inhalt[i][t].add(andereMatrix.inhalt[i][t]); ;
				}
			}
		}
		else{
			return; //Exception hier
		}
	}
	
	public void subtrahieren(Matrix andereMatrix){
		if(spalten == andereMatrix.spalten && zeilen == andereMatrix.zeilen){
			for(int i = 0; i < zeilen; i++){
				for(int t = 0; t < spalten; t++){
					this.inhalt[i][t].subtract(andereMatrix.inhalt[i][t]);
				}
			}
		}
		else{
			return; //Exception hier
		}
	}
	
	public Matrix multiplizieren(Matrix andereMatrix){
		if(spalten == andereMatrix.zeilen){
			Fraction[][] resultMatrix = new Fraction[zeilen][andereMatrix.spalten];
			for(int i = 0; i < zeilen; i++){
				for(int j = 0; j < andereMatrix.spalten; j++){
					Fraction result = new Fraction(0);
					for(int z = 0; z < andereMatrix.zeilen; z++){
						Fraction multi = inhalt[i][z].clone();
						//System.out.println("Multipliziere " + i + "," + z + "| " + multi + " mit " + z + "," + j + "| " + andereMatrix.inhalt[z][j]);
						multi.multiply(andereMatrix.inhalt[z][j]);
						//System.out.println("Addiere " + multi + " auf " + result);
						result.add(multi);
					}
					resultMatrix[i][j] = result;
					//System.out.println("Schreibe " + result + " in " + i + "," + j);
					//System.out.println();
				}
				
			}
			return new Matrix(resultMatrix);
		}
		else{
			return new Matrix(1,1);
		}
		
	}
	
	public String toString(){
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < zeilen; i++){
			for(int t = 0; t < spalten; t++){
				output.append(inhalt[i][t]);
				if(t != spalten-1){
					output.append("\t");
				}
			}
			output.append("\n");
		}
		return output.toString();
	}
	
	public void darstellen(){
		for(int i = 0; i < zeilen; i++){
			for(int t = 0; t < spalten; t++){
				System.out.print(inhalt[i][t]);
				if(t != spalten-1){
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}
	
	public int anzahlElemente(){
		return spalten * zeilen;
	}
	
	public void nullSetzen(){
		
	}
}
