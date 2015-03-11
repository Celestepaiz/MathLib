package math;

public class Fraction implements Comparable<Fraction>, Cloneable{
	private long numerator; //Positive or negative
	private long denominator;
	private boolean autoReduce = false;
	private boolean modify = false; //Determines if the fraction should be modified or return results

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

	/**
	 * Calculates the greates common divisor of a and b
	 * @param a
	 * @param b
	 * @return
	 */
	public static long gcd(long a, long b){
		while (b != 0) {
			if (a > b) {
				a = a - b;
			} else {
				b = b - a;
			}
		}
		return a;
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
		return numerator;
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
		return numerator < 0;
	}
	
	/**
	 * Checks if the fraction is an integer
	 * @return true or false
	 */
	public boolean isInteger(){
		return this.numerator % this.denominator == 0;
	}
	
	/**
	 * Switches numerator and denominator
	 */
	public void invert(){
		long oldDenominator = this.denominator;
		this.denominator = this.numerator;
		if(isNegative()){
			oldDenominator *= -1;
		}
		this.numerator = oldDenominator;
		updateState();
	}

	/**
	 * Überprüft ob der Bruch 0 oder negativ ist und ändert
	 * die Werte dementsprechend. Kürzt den Bruch sofern aktiviert.
	 */
	private void updateState() {
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
		if(this.modify){
			this.numerator = resultNumerator;
			this.denominator = resultDenominator;
			modify = false;
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
		if(this.modify){
			this.numerator = resultNumerator;
			this.denominator = resultDenominator;
			modify = false;
		}
		return new Fraction(resultNumerator, resultDenominator);
	}

	public Fraction multiply(Fraction anotherFraction) {
		long resultNumerator = getNumerator() * anotherFraction.getNumerator();
		long resultDenominator = getDenominator() * anotherFraction.getDenominator();
		if(this.modify){
			this.numerator = resultNumerator;
			this.denominator = resultDenominator;
			modify = false;
		}
		return new Fraction(resultNumerator, resultDenominator);
	}
	
	public Fraction multiply(long scalar) {
		long resultNumerator = getNumerator() * scalar;
		if(this.modify){
			this.numerator = resultNumerator;
			modify = false;
		}
		return new Fraction(resultNumerator, getDenominator());
	}
	
	public Fraction divide(Fraction anotherFraction) throws IllegalArgumentException{
		if(anotherFraction.isZero()){
			throw new IllegalArgumentException("Teilung durch 0 ist unzulässig!");
		}
		long resultNumerator = getNumerator() * anotherFraction.getDenominator();
		long resultDenominator = getDenominator() * anotherFraction.getNumerator();
		if(this.modify){
			this.numerator = resultNumerator;
			this.denominator = resultDenominator;
			modify = false;
		}
		return new Fraction(resultNumerator,resultDenominator);
	}
	
	/**
	 * Dividiert den Bruch durch den gegeben Skalar,
	 * ist der skalar 0 wird nichts berechnet
	 * @param scalar
	 */
	public Fraction divide(long scalar) throws IllegalArgumentException{
		if(scalar == 0){
			throw new IllegalArgumentException("Teilung durch 0 ist unzulässig!");
		}
		long resultNumerator = this.numerator;
		if(scalar < 0){
			resultNumerator *= -1;
		}
		long resultDenominator = getDenominator() * scalar;
		if(this.modify){
			this.numerator = resultNumerator;
			this.denominator = resultDenominator;
			modify = false;
		}
		return new Fraction(resultNumerator,resultDenominator);
	}
	
	/**
	 * @return The exact value of a fraction
	 */
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
			throw new IllegalArgumentException("Ungültiger Wert");
		}

	}
	
	@Override
	public Fraction clone(){
		return new Fraction(getNumerator(), denominator, autoReduce);
	}
	
	/**
	 * Make the fraction change instead of returning the result
	 * @return Same object with modify enabled
	 */
	public Fraction modify(){
		modify = true;
		return this;
	}
	
	@Override
	public String toString() {
		if (numerator == 0) {
			return "0";
		}
		else if(numerator == denominator){
			return (isNegative()? "-": "") + "1";
		}
		else if(numerator % denominator == 0){
			return "" + (getNumerator()/denominator);
		}
		return getNumerator() + "/" + denominator;
	}

	@Override
	public int compareTo(Fraction anotherFraction) {
		return Double.compare(this.getValue(), anotherFraction.getValue());
	}
}