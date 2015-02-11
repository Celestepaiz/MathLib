package math;

public class Bruch {
	private long zaehler;
	private long nenner;
	private boolean negativ = false;
	private boolean autoKuerzen = false;

	public Bruch(long zaehler, long nenner) {
		this.zaehler = zaehler;
		this.nenner = nenner;
		updateState();
	}
	
	/**
	 * Erstellt einen Bruch und kürzt wenn gewünscht
	 * @param zaehler
	 * @param nenner
	 * @param automatischKuerzen
	 */
	public Bruch(long zaehler, long nenner, boolean automatischKuerzen) {
		this.zaehler = zaehler;
		this.nenner = nenner;
		autoKuerzen = automatischKuerzen;
		updateState();
	}
	
	public Bruch(long ganzzahl) {
		this.zaehler = ganzzahl;
		this.nenner = 1;
		updateState();
	}
	
	public Bruch(long ganzzahl, boolean automatischKuerzen) {
		this.zaehler = ganzzahl;
		this.nenner = 1;
		autoKuerzen = automatischKuerzen;
		updateState();
	}
	
	/**
	 * Brüche nach Berechnungen automatisch kürzen aktivieren/deaktivieren
	 * @param kuerzen
	 */
	public void automatischKuerzen(boolean kuerzen){
		autoKuerzen = kuerzen;
		updateState();
	}

	private long ggTBerechnen() {
		long a1 = zaehler;
		long b1 = nenner;
		while (b1 != 0) {
			if (a1 > b1) {
				a1 = a1 - b1;
			} else {
				b1 = b1 - a1;
			}
		}
		return a1;
	}

	public void kuerzen() {
		long ggT;
		ggT = ggTBerechnen();
		if (ggT == 1) {
			return;
		} 
		else if(ggT == 0){
			return;
		}
		else {
			zaehler = zaehler / ggT;
			nenner = nenner / ggT;
		}
	}

	public long getZaehler() {
		return zaehler;
	}
	
	private long getNumerator(){
		return zaehler*makeNegative();
	}

	public long getNenner() {
		return nenner;
	}
	
	public boolean istNull(){
		if (zaehler == 0) {
			return true;
		}
		else if(nenner == 0){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean istNegativ() {
		return negativ;
	}
	
	private String printNegative(){
		if(negativ){
			return "-";
		}
		else{
			return "";
		}
	}
	
	private int makeNegative(){
		if(negativ){
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
		if(istNull()){
			zaehler = 0;
			nenner = 0;
		}
		
		if(zaehler < 0){
			negativ = true;
			zaehler *= -1;
		}
		else{
			negativ = false;
		}
		
		if(nenner < 0){
			nenner = nenner * -1;
		}
		
		if(autoKuerzen){
			this.kuerzen();
		}
	}
	
	public void addieren(Bruch andererBruch) {
		//Erweitern
		if(nenner != andererBruch.getNenner() && !andererBruch.istNull() && !this.istNull()){
			long bruchNenner = nenner * andererBruch.getNenner();
			long zaehler1 = getNumerator() * andererBruch.getNenner();
			long zaehler2 = andererBruch.getNumerator() * nenner;

			zaehler = zaehler1 + zaehler2;
			nenner = bruchNenner;
			updateState();
		}
		else if(this.istNull()){
			zaehler = andererBruch.getNumerator();
			nenner = andererBruch.getNenner();
			updateState();
		}
		else{
			zaehler = getNumerator() + andererBruch.getNumerator();
			updateState();
		}
	}
	
	public void subtrahieren(Bruch andererBruch){
		if(nenner != andererBruch.getNenner() && !this.istNull() && !andererBruch.istNull()){
			long bruchNenner = nenner * andererBruch.getNenner();
			long zaehler1 = getNumerator() * andererBruch.getNenner();
			long zaehler2 = andererBruch.getNumerator() * nenner;

			zaehler = zaehler1 - zaehler2;
			nenner = bruchNenner;
			updateState();
		}
		else{
			zaehler = getNumerator() - andererBruch.getNumerator();
			updateState();
		}
	}

	public void multiplizieren(Bruch andererBruch) {
		this.zaehler = getNumerator() * andererBruch.getNumerator();
		this.nenner = nenner * andererBruch.getNenner();
		updateState();
	}
	
	public void multiplizieren(long skalar) {
		this.zaehler = getNumerator() * skalar;
		updateState();
	}
	
	public void dividieren(Bruch andererBruch) {
		if(andererBruch.istNull()){
			return; //Exception hier
		}
		zaehler = getNumerator() * andererBruch.getNenner() * andererBruch.makeNegative();
		nenner *= andererBruch.getNumerator();
		updateState();
	}
	
	/**
	 * Dividiert den Bruch durch den gegeben Skalar,
	 * ist der skalar 0 wird nichts berechnet
	 * @param skalar
	 */
	public void dividieren(long skalar) {
		if(skalar == 0){
			return; //Exception hier
		}
		if(skalar < 0){
			zaehler *= -1;
		}
		nenner *= skalar;
		updateState();
	}
	
	public static Bruch parseBruch(String input) throws IllegalArgumentException{
		if(input.matches("[0-9]+[/]{1}[1-9]{1}[0-9]*")){ //Bruch
			String[] bruchInput = input.split("/");
			long zaehler = Long.parseLong(bruchInput[0]);
			long nenner = Long.parseLong(bruchInput[1]);
			return new Bruch(zaehler, nenner);
		}
		else if(input.matches("[1-9]{1}[0-9]*")){ //Ganzzahl
			return new Bruch(Long.parseLong(input));
		}
		else{
			throw new IllegalArgumentException("Muss im Format Zähler/Nenner(!=0) sein!");
		}

	}
	
	public Bruch copy(){
		return new Bruch(getNumerator(), nenner, autoKuerzen);
	}
	
	@Override
	public String toString() {
		if (zaehler == 0) {
			return "0";
		}
		else if(zaehler == nenner){
			return printNegative() + "1";
		}
		else if(zaehler > nenner){
			if(zaehler % nenner == 0){
				return printNegative() + zaehler/nenner;
			}
		}
		return printNegative() + zaehler + "/" + nenner;
	}
}