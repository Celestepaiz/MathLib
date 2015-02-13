package math;

public class Fraction implements Comparable<Fraction>{
	private long numerator;
	private long denominator;
	private boolean negative = false;
	private boolean autoReduce = false;

	public Fraction(long numerator, long denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		updateState();
	}
	
	/**
	 * Erstellt einen Bruch und kürzt wenn gewünscht
	 * @param numerator
	 * @param denominator
	 * @param autoReduce
	 */
	public Fraction(long numerator, long denominator, boolean autoReduce) {
		this.numerator = numerator;
		this.denominator = denominator;
		this.autoReduce = autoReduce;
		updateState();
	}
	
	public Fraction(long number) {
		this.numerator = number;
		this.denominator = 1;
		updateState();
	}
	
	public Fraction(long ganzzahl, boolean automatischKuerzen) {
		this.numerator = ganzzahl;
		this.denominator = 1;
		autoReduce = automatischKuerzen;
		updateState();
	}
	
	/**
	 * Brüche nach Berechnungen automatisch kürzen aktivieren/deaktivieren
	 * @param reduce
	 */
	public void autoReduce(boolean reduce){
		autoReduce = reduce;
		updateState();
	}

	//Greates common divisor
	private long ggTBerechnen() {
		long a1 = numerator;
		long b1 = denominator;
		while (b1 != 0) {
			if (a1 > b1) {
				a1 = a1 - b1;
			} else {
				b1 = b1 - a1;
			}
		}
		return a1;
	}

	public void reduce() {
		long ggT;
		ggT = ggTBerechnen();
		if (ggT == 1) {
			return;
		} 
		else if(ggT == 0){
			return;
		}
		else {
			numerator = numerator / ggT;
			denominator = denominator / ggT;
		}
	}
	
	public long getNumerator(){
		return numerator*makeNegative();
	}

	public long getDenominator() {
		return denominator;
	}
	
	public boolean isZero(){
		if (numerator == 0) {
			return true;
		}
		else if(denominator == 0){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean isNegative() {
		return negative;
	}
	
	private String returnNegative(){
		if(negative){
			return "-";
		}
		else{
			return "";
		}
	}
	
	private int makeNegative(){
		if(negative){
			return -1;
		}
		else{
			return 1;
		}
	}

	/**
	 * Überprüft ob der Bruch 0 oder negativ ist und ändert
	 * die Werte dementsprechend. Kürzt den Bruch sofern aktiviert.
	 */
	private void updateState() {
		if(numerator < 0){
			negative = true;
			numerator *= -1;
		}
		else{
			negative = false;
		}
		
		if(isZero()){
			numerator = 0;
			denominator = 0;
		}
		
		if(denominator < 0){
			denominator = denominator * -1;
		}
		
		if(autoReduce){
			this.reduce();
		}
	}
	
	public Fraction add(Fraction anotherFraction) {
		long resultNumerator = 0;
		long resultDenominator = 1;
		//Erweitern wenn nenner nicht gleich sind
		if(denominator != anotherFraction.getDenominator() && !anotherFraction.isZero() && !this.isZero()){
			long fractionDenominator = getDenominator() * anotherFraction.getDenominator();
			long numerator1 = getNumerator() * anotherFraction.getDenominator();
			long numerator2 = anotherFraction.getNumerator() * this.denominator;

			resultNumerator = numerator1 + numerator2;
			resultDenominator = fractionDenominator;
		}
		//Von auf 0 hinzuaddieren
		else if(this.isZero()){
			resultNumerator = anotherFraction.getNumerator();
			resultDenominator = anotherFraction.getDenominator();
		}
		else{
			resultNumerator = getNumerator() + anotherFraction.getNumerator();
			resultDenominator = getDenominator();
		}
		return new Fraction(resultNumerator, resultDenominator);
	}
	
	public Fraction subtract(Fraction anotherFraction){
		long resultNumerator = 0;
		long resultDenominator = 1;
		if(denominator != anotherFraction.getDenominator() && !this.isZero() && !anotherFraction.isZero()){
			long fractionDenominator = denominator * anotherFraction.getDenominator();
			long numerator1 = getNumerator() * anotherFraction.getDenominator();
			long numerator2 = anotherFraction.getNumerator() * denominator;

			resultNumerator = numerator1 - numerator2;
			resultDenominator = fractionDenominator;
			
		}
		else{
			resultNumerator = getNumerator() - anotherFraction.getNumerator();
			resultDenominator = getDenominator();
		}
		return new Fraction(resultNumerator, resultDenominator);
	}

	public Fraction multiply(Fraction anotherFraction) {
		long resultNumerator = getNumerator() * anotherFraction.getNumerator();
		long resultDenominator = getDenominator() * anotherFraction.getDenominator();
		return new Fraction(resultNumerator, resultDenominator);
	}
	
	public Fraction multiply(long skalar) {
		long resultNumerator = getNumerator() * skalar;
		return new Fraction(resultNumerator, getDenominator());
	}
	
	public Fraction divide(Fraction anotherFraction) throws IllegalArgumentException{
		if(anotherFraction.isZero()){
			throw new IllegalArgumentException("Teilung durch 0 ist unzulässig!");
		}
		long resultNumerator = getNumerator() * anotherFraction.getDenominator() * anotherFraction.makeNegative();
		long resultDenominator = getDenominator() * anotherFraction.getNumerator();
		return new Fraction(resultNumerator,resultDenominator);
	}
	
	/**
	 * Dividiert den Bruch durch den gegeben Skalar,
	 * ist der skalar 0 wird nichts berechnet
	 * @param skalar
	 */
	public Fraction divide(long skalar) throws IllegalArgumentException{
		if(skalar == 0){
			throw new IllegalArgumentException("Teilung durch 0 ist unzulässig!");
		}
		long resultNumerator = this.numerator;
		if(skalar < 0){
			resultNumerator *= -1;
		}
		long resultDenominator = getDenominator() * skalar;
		return new Fraction(resultNumerator,resultDenominator);
	}
	
	public double getValue(){
		return this.numerator/(double)this.denominator;
	}
	
	public static Fraction parseFraction(String input) throws IllegalArgumentException{
		if(input.matches("-?\\d+/{1}\\d+")){ //Fraction has to be positive or negative digit/digit
			String[] bruchInput = input.split("/");
			long zaehler = Long.parseLong(bruchInput[0]);
			long nenner = Long.parseLong(bruchInput[1]);
			return new Fraction(zaehler, nenner);
		}
		else if(input.matches("-?\\d+")){ //Normal positive or negative integer
			return new Fraction(Long.parseLong(input));
		}
		else{
			throw new IllegalArgumentException("Muss im Format Zähler/Nenner(!=0) sein!");
		}

	}
	
	@Override
	public Fraction clone(){
		return new Fraction(getNumerator(), denominator, autoReduce);
	}
	
	@Override
	public String toString() {
		if (numerator == 0) {
			return "0";
		}
		else if(numerator == denominator){
			return returnNegative() + "1";
		}
		else if(numerator > denominator){
			if(numerator % denominator == 0){
				return returnNegative() + numerator/denominator;
			}
		}
		return returnNegative() + numerator + "/" + denominator;
	}

	@Override
	public int compareTo(Fraction anotherFraction) {
		if(this.numerator == anotherFraction.getNumerator() && this.denominator == anotherFraction.getDenominator()){
			return 0;
		}
		return 1;
	}
}