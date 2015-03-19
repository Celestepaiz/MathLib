package math;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

public class Matrix implements Comparable<Matrix>, Cloneable{
	
	/**
	 * Erstellt eine n x m matrix
	 * @param n anzahl Zeilen
	 * @param m anzahl Spalten
	 */
	private int columns;
	private int rows;
	private Fraction[][] content;
	
	public Matrix(int m, int n){
		this.rows = m;
		this.columns = n;
		this.content = new Fraction[m][n];
		fillWithZero();
	}
	
	public <T> Matrix(int m, int n, Vector<T> content){
		this.rows = m;
		this.columns = n;
		this.content = new Fraction[m][n];
		if(!content.isEmpty() && (content.firstElement() instanceof Integer || content.firstElement() instanceof Fraction)){
			if(content.size() != m*n){
				fillWithZero();
			}
			else{
				int insertIndex = 0;
				for(int i = 0 ; i < rows; i++){
					for(int j = 0; j < columns; j++){
						T element = content.get(insertIndex);
						if(element instanceof Integer){
							this.content[i][j] = new Fraction((Integer)element);
						}
						else{
							this.content[i][j] = (Fraction)element;
						}
						insertIndex++;
					}
				}
			}
		}
		else{
			fillWithZero();
		}
	}
	
	public Matrix(int m, int n, int[] content){
		this.rows = m;
		this.columns = n;
		this.content = new Fraction[m][n];
		if(content.length != m*n){
			fillWithZero();
		}
		else{
			int insertIndex = 0;
			for(int i = 0 ; i < this.rows; i++){
				for(int t = 0; t < this.columns; t++){
					this.content[i][t] = new Fraction(content[insertIndex]);
					insertIndex++;
				}
			}
		}
	}
	
	public Matrix(int m, int n, Fraction[] content){
		this.rows = m;
		this.columns = n;
		this.content = new Fraction[m][n];
		if(content.length != m*n){
			fillWithZero();
		}
		else{
			int insertIndex = 0;
			for(int i = 0 ; i < this.rows; i++){
				for(int t = 0; t < this.columns; t++){
					this.content[i][t] = content[insertIndex];
					insertIndex++;
				}
			}
		}
	}
	
	public Matrix(Fraction[][] inhalt){
		this.rows = inhalt.length;
		this.columns = inhalt[0].length;
		this.content = inhalt;
	}
	
	public int getColumnCount(){
		return this.columns;
	}
	
	public int getRowCount(){
		return this.rows;
	}
	
	public Fraction[][] getContent(){
		return this.content;
	}
	
	/**
	 * Returns the fraction at the given row and column index
	 * @param row
	 * @param column
	 * @return Fraction
	 * @throws IllegalArgumentException If row or column are not valid
	 */
	public Fraction getElementAt(int row, int column) throws IllegalArgumentException{
		if(rowInRange(row) && columnInRange(column)){
			return this.content[row-1][column-1].clone();
		}
		else{
			throw new IllegalArgumentException("Zeile oder Spalte außerhalb des Gültigen bereichs!");
		}
	}
	
	/**
	 * Füllt alle Einträge in der Matrix mit 0
	 */
	public void fillWithZero(){
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				this.content[i][j] = new Fraction(0);
			}
		}
	}
	
	public Matrix add(Matrix anotherMatrix) throws IllegalArgumentException{
		if(this.columns == anotherMatrix.columns && this.rows == anotherMatrix.rows){
			Matrix resultMatrix = new Matrix(this.rows,this.columns);
			for(int i = 0; i < this.rows; i++){
				for(int j = 0; j < this.columns; j++){
					resultMatrix.content[i][j] = this.content[i][j].add(anotherMatrix.content[i][j]);
				}
			}
			return resultMatrix;
		}
		else{
			throw new IllegalArgumentException("Matrizen müssen gleiche Zeilen-/Spaltenzahl haben!");
		}
	}
	
	public Matrix subtract(Matrix anotherMatrix) throws IllegalArgumentException{
		if(this.columns == anotherMatrix.columns && this.rows == anotherMatrix.rows){
			Matrix resultMatrix = new Matrix(this.rows,this.columns);
			for(int i = 0; i < this.rows; i++){
				for(int j = 0; j < this.columns; j++){
					resultMatrix.content[i][j] = this.content[i][j].subtract(anotherMatrix.content[i][j]);
				}
			}
			return resultMatrix;
		}
		else{
			throw new IllegalArgumentException("Matrizen müssen gleiche Zeilen-/Spaltenzahl haben!");
		}
	}
	
	public Matrix multiply(Matrix anotherMatrix) throws IllegalArgumentException{
		if(this.columns == anotherMatrix.rows){
			Fraction[][] resultMatrix = new Fraction[this.rows][anotherMatrix.columns];
			for(int i = 0; i < this.rows; i++){
				for(int j = 0; j < anotherMatrix.columns; j++){
					Fraction result = new Fraction(0);
					for(int z = 0; z < anotherMatrix.rows; z++){
						Fraction multi = content[i][z].clone();
						//System.out.println("Multipliziere " + i + "," + z + "| " + multi + " mit " + z + "," + j + "| " + andereMatrix.inhalt[z][j]);
						multi = multi.multiply(anotherMatrix.content[z][j]);
						//System.out.println("Addiere " + multi + " auf " + result);
						result = result.add(multi);
					}
					resultMatrix[i][j] = result;
					//System.out.println("Schreibe " + result + " in " + i + "," + j);
					//System.out.println();
				}
				
			}
			return new Matrix(resultMatrix);
		}
		else{
			throw new IllegalArgumentException("Matrtix A muss genausoviele Zeilen haben wie B Spalten hat!");
		}
		
	}
	
	/**
	 * Swaps two rows of a matrix
	 * @param row
	 * @param anotherRow
	 * @throws IllegalArgumentException
	 */
	public void swapRows(int row, int anotherRow) throws IllegalArgumentException{
		if(row != anotherRow){
			if(rowInRange(row) && rowInRange(anotherRow)){
				//Convert to real index
				row--;
				anotherRow--;
				for(int i=0; i < this.columns; i++){
					Fraction element = this.content[row][i].clone();
					this.content[row][i] = this.content[anotherRow][i];
					this.content[anotherRow][i] = element;
				}
			}
			else{
				throw new IllegalArgumentException("Zeilenzahl ist auserhalb des gültigen Bereichs");
			}
		}
	}
	
	public void multiplyRow(int row, int scalar) throws IllegalArgumentException{
		if(rowInRange(row)){
			if(scalar != 0){
				row--;
				for(int i = 0; i < this.columns; i++){
					this.content[row][i].modify().multiply(scalar);
				}
			}
			else{
				throw new IllegalArgumentException("Skalar muss ungleich 0 sein!");
			}
		}
		else{
			throw new IllegalArgumentException("Zeilenzahl ist auserhalb des gültigen Bereichs!");
		}
	}
	
	public void multiplyRow(int row, Fraction scalar) throws IllegalArgumentException{
		if(rowInRange(row)){
			if(!scalar.isZero()){
				row--;
				for(int i = 0; i < this.columns; i++){
					this.content[row][i].modify().multiply(scalar);
				}
			}
			else{
				throw new IllegalArgumentException("Skalar muss ungleich 0 sein!");
			}
		}
		else{
			throw new IllegalArgumentException("Zeilenzahl ist auserhalb des gültigen Bereichs!");
		}
	}
	
	public void addMultipleOfRow(int row, int scalar, int anotherRow) throws IllegalArgumentException{
		if(rowInRange(row) && rowInRange(anotherRow)){
			row--;
			anotherRow--;
			Vector<Fraction> rowValues = new Vector<Fraction>();
			for(int i = 0; i < this.columns; i++){
				rowValues.add(this.content[row][i]);
			}
			for(int j = 0; j < this.columns; j++){
				this.content[anotherRow][j].modify().add(rowValues.get(j).multiply(scalar));
			}
		}
		else{
			throw new IllegalArgumentException("Zeilenzahl ist auserhalb des gültigen Bereichs!");
		}
	}
	
	/**
	 * Subtracts a multiple of row from anotherRow
	 * @param row
	 * @param scalar
	 * @param anotherRow
	 * @throws IllegalArgumentException
	 */
	public void subtractMultipleOfRow(int row, int scalar, int anotherRow) throws IllegalArgumentException{
		if(rowInRange(row) && rowInRange(anotherRow)){
			row--;
			anotherRow--;
			Vector<Fraction> rowValues = new Vector<Fraction>();
			for(int i = 0; i < this.columns; i++){
				rowValues.add(this.content[row][i]);
			}
			for(int j = 0; j < this.columns; j++){
				this.content[anotherRow][j].modify().subtract(rowValues.get(j).multiply(scalar));
			}
		}
		else{
			throw new IllegalArgumentException("Zeilenzahl ist auserhalb des gültigen Bereichs!");
		}
	}
	
	public void subtractMultipleOfRow(int row, Fraction scalar, int anotherRow) throws IllegalArgumentException{
		if(rowInRange(row) && rowInRange(anotherRow)){
			anotherRow--;
			Vector<Fraction> rowValues = getRow(row);
			for(int j = 0; j < this.columns; j++){
				this.content[anotherRow][j].modify().subtract(rowValues.get(j).multiply(scalar));
			}
		}
		else{
			throw new IllegalArgumentException("Zeilenzahl ist auserhalb des gültigen Bereichs!");
		}
	}
	
	/**
	 * Checks if a given row index is in range of the matrix rows
	 * The index must be greater than 0 and smaller or equal to the row count
	 * @param row
	 * @return true or false
	 */
	private boolean rowInRange(int row){
		if(row > 0  && row <= this.rows){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * Checks if a given column index is in range of the matrix columns
	 * The index must be greater than 0 and smaller or equal to the column count
	 * @param column
	 * @return true or false
	 */
	private boolean columnInRange(int column){
		if(column > 0 && column <= this.columns){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks if the matrix is in triangular form
	 * @return true or false
	 */
	public boolean isTriangularForm(){
		int lastElementIndex = -1;
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				if(!content[i][j].isZero()){
					if(lastElementIndex < j){
						lastElementIndex = j;
						break;
					}
					else{
						return false;
					}
				}
				else if(j == this.columns-1){
					lastElementIndex = j;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns the first element in a row that is different from zero
	 * If theres no such element a zero fraction will be returned
	 * @param row Row of the pivot element
	 * @return
	 */
	public Fraction getPivotElement(int row) throws IllegalArgumentException{
		int pivotIndex = getPivotIndex(row);
		if(pivotIndex == -1){
			return new Fraction(0);
		}
		Fraction pivotElement = getElementAt(row,pivotIndex);
		return pivotElement;
	}
	
	public int getPivotIndex(int row){
		if(rowInRange(row)){
			row--;
			for(int i = 0; i < this.columns; i++){
				if(!content[row][i].isZero()){
					return i+1;
				}
			}
			return -1;
		}
		else{
			throw new IllegalArgumentException("Zeile ist außerhalb des gültigen Bereichs!");
		}
	}
	
	/**
	 * Retruns a vector with all pivot element indexes in order of rows
	 * Index is -1 if there is no pivot element in a row
	 * @return Vector with indexes
	 */
	public Vector<Integer> getPivotIndexes(){
		Vector<Integer> indexList = new Vector<Integer>();
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				if(!content[i][j].isZero()){
					indexList.add(j+1);
					break;
				}
				else if(j == this.columns-1){ //If theres no pivot element in this row
					indexList.add(-1);
				}
			}
		}
		return indexList;
	}
	
	/**
	 * Creates a map where each avaiable pivot index is used as a key 
	 * @return
	 */
	public Map<Integer,Vector<Integer>> getPivotIndexesAsMap(){
		Vector<Integer> pivotIndexes = getPivotIndexes();
		Map<Integer,Vector<Integer>> duplicateIndexes = new TreeMap<Integer,Vector<Integer>>();
		//Add all pivot Indexes to a map with their row number
		for(int i = 0; i < pivotIndexes.size(); i++){
			int currentIndex = pivotIndexes.get(i);
			if(!duplicateIndexes.containsKey(currentIndex)){
				Vector<Integer> rowNumbers = new Vector<Integer>();
				rowNumbers.add(i+1);
				duplicateIndexes.put(currentIndex, rowNumbers);
			}
			else{
				duplicateIndexes.get(currentIndex).add(i+1);
			}
		}
		return duplicateIndexes;
	}
	
	/**
	 * Overloaded method wich accepts a comparator for the map
	 * @param comparator
	 * @return
	 */
	public Map<Integer,Vector<Integer>> getPivotIndexesAsMap(Comparator<Integer> comparator){
		Vector<Integer> pivotIndexes = getPivotIndexes();
		Map<Integer,Vector<Integer>> duplicateIndexes = new TreeMap<Integer,Vector<Integer>>(comparator);
		//Add all pivot Indexes to a map with their row number
		for(int i = 0; i < pivotIndexes.size(); i++){
			int currentIndex = pivotIndexes.get(i);
			if(!duplicateIndexes.containsKey(currentIndex)){
				Vector<Integer> rowNumbers = new Vector<Integer>();
				rowNumbers.add(i+1);
				duplicateIndexes.put(currentIndex, rowNumbers);
			}
			else{
				duplicateIndexes.get(currentIndex).add(i+1);
			}
		}
		return duplicateIndexes;
	}
	
	/**
	 * Checks if 2 rows are multiple of each other and returns a scalar
	 * different from 0 if they are multiple. If you multiply this scalar with
	 * the row it will be the same as anotherRow.
	 * @param row
	 * @param anotherRow
	 * @return Fraction
	 */
	public Fraction getScalarMultiplier(int row, int anotherRow){
		if(rowInRange(row) && rowInRange(anotherRow)){
			int pivotIndexRow = getPivotIndex(row);
			int pivotIndexAnotherRow = getPivotIndex(anotherRow);
			//If the pivot indexes arent the same theres no multiplier
			if(pivotIndexRow != pivotIndexAnotherRow || (pivotIndexRow == -1 || pivotIndexAnotherRow == -1)){
				return new Fraction(0);
			}
			//Determine the multiplier for the first pivot elements to be equal to the second
			Fraction rowElement = getPivotElement(row);
			Fraction anotherRowElement = getPivotElement(anotherRow);
			Fraction scalar = anotherRowElement.divide(rowElement);
			
			Vector<Fraction> rowContent = getRow(row);
			Vector<Fraction> anotherRowContent = getRow(anotherRow);
			
			/*
			 * Multiply each element in row with the scalar and check if it's
			 * the same as in anotherRow.
			 */
			for(int i = pivotIndexRow; i < rowContent.size(); i++){
				Fraction target = anotherRowContent.get(i);
				if(target.isZero()){
					return new Fraction(0);
				}
				Fraction result = rowContent.get(i).multiply(scalar);
				if(result.compareTo(target) != 0){
					return new Fraction(0);
				}
			}
			return scalar;
		}
		else{
			throw new IllegalArgumentException("Zeile ist außerhalb des gültigen Bereichs!");
		}
	}
	
	public void eliminateMultipleRows(){
		Map<Integer,Vector<Integer>> duplicateIndexes = getPivotIndexesAsMap();
		for(int key : duplicateIndexes.keySet()){
			if(key == -1){ //Cant eliminate rows with no pivot element
				continue;
			}
			if(duplicateIndexes.get(key).size() > 1){
				Vector<Integer> rows = duplicateIndexes.get(key);
				//Compare each row of the current pivot index with the other rows of this index
				for(int fistElement = 0; fistElement < rows.size(); fistElement++){
					int row = rows.elementAt(fistElement);
					for(int nextElement = fistElement+1; nextElement < rows.size(); nextElement++){
						int compareToRow = rows.elementAt(nextElement);
						Fraction scalar = getScalarMultiplier(row, compareToRow);
						if(!scalar.isZero()){
							System.out.println("Subtrahiere von Zeile " + compareToRow + " das " + scalar + "-fache von Zeile " + row);
							subtractMultipleOfRow(row, scalar, compareToRow);
						}
					}
				}
				
			}
		}
	}
	
	/**
	 * Sorts matrix rows so that pivot element indexes are in ascending order.
	 * Zero rows are ignored by this method and will be always last.
	 */
	public void sortRows(){
		//TODO: Improve sorting if possible
		Vector<Integer> indexList = getPivotIndexes();
		int smallestElement = Integer.MAX_VALUE;
		int smallestElementIndex = -1;
		int sortedIndex = 0;
		boolean sorted = false;
		while(!sorted){
			//Find smallest element
			for(int i = sortedIndex; i < indexList.size(); i++){
				if(indexList.get(i) == -1){ //Skip rows with no pivot element
					continue;
				}
				else if(indexList.get(i) < smallestElement){
					smallestElement = indexList.get(i);
					smallestElementIndex = i;
				}
			}
			//Was there no row with pivot element?
			if(smallestElementIndex == -1){
				break;
			}
			//Is the element is already at the right position?
			else if(smallestElementIndex == sortedIndex){
				sortedIndex++;
				smallestElement = Integer.MAX_VALUE;
				smallestElementIndex = -1;
			}
			//Swap smallest element with the current sorted index 
			else{
				Collections.swap(indexList, smallestElementIndex, sortedIndex);
				//System.out.println("Tausche Zeile " + (smallestElementIndex+1) + " mit " + (sortedIndex+1));
				swapRows(smallestElementIndex+1,sortedIndex+1);
				sortedIndex++;
				smallestElement = Integer.MAX_VALUE;
				smallestElementIndex = -1;
			}
			if(sortedIndex == indexList.size()-1){
				sorted = true;
			}
		}
	}
	
	/**
	 * Converts the matrix to triangular form. The method starts at the row with the highest
	 * pivot index and eliminates all other pivot elements with the same pivot index.
	 * This continues until each pivot index is unique. The matrix is sorted afterwards
	 * with the sortRows method.
	 */
	public void toTriangularForm(){
		eliminateMultipleRows();
		TreeMap<Integer,Vector<Integer>> duplicateIndexes = (TreeMap<Integer,Vector<Integer>>)getPivotIndexesAsMap();
		duplicateIndexes.remove(-1); //Rows with no pivot element
		boolean isTriangular = false;
		int currentIndex = 0;
		try{
			currentIndex = duplicateIndexes.firstKey();
		}
		catch(NoSuchElementException e){
			return;
		}
		while(!isTriangular){
			if(duplicateIndexes.get(currentIndex).size() > 1){
				Vector<Integer> rowVector = duplicateIndexes.get(currentIndex);
				int eliminatorRowIndex = rowVector.get(0);
				Fraction eliminatorElement = getPivotElement(eliminatorRowIndex);
				for(int i = 1; i < rowVector.size(); i++){
					int eliminatedRowIndex = rowVector.get(i);
					Fraction eliminatedElement = getPivotElement(eliminatedRowIndex);
					Fraction scalar = eliminatedElement.divide(eliminatorElement);
					//System.out.println("Subtrahiere von Zeile " + eliminatedRowIndex + " das " + scalar + "-fache von Zeile" + eliminatorRowIndex);
					subtractMultipleOfRow(eliminatorRowIndex, scalar, eliminatedRowIndex);
					int newPivotIndex = getPivotIndex(eliminatedRowIndex);
					if(duplicateIndexes.containsKey(newPivotIndex)){
						duplicateIndexes.get(newPivotIndex).add(eliminatedRowIndex);
					}
					else{
						Vector<Integer> rows = new Vector<Integer>();
						rows.add(eliminatedRowIndex);
						duplicateIndexes.put(newPivotIndex, rows);
					}
				}
			}
			if(duplicateIndexes.higherKey(currentIndex) == null){
				isTriangular = true;
			}
			else{
				currentIndex = duplicateIndexes.higherKey(currentIndex);
			}
		}
		sortRows();
	}
	
	public String toString(){
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < rows; i++){
			for(int t = 0; t < columns; t++){
				output.append(content[i][t]);
				if(t != columns-1){
					output.append("\t");
				}
			}
			output.append("\n");
		}
		return output.toString();
	}
	
	/**
	 * Prints matrix in console
	 */
	public void print(){
		for(int i = 0; i < this.rows; i++){
			for(int t = 0; t < this.columns; t++){
				System.out.print(this.content[i][t]);
				if(t != this.columns-1){
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}
	
	public int getElementCount(){
		return columns * rows;
	}
	
	public Vector<Fraction> getRow(int row){
		if(rowInRange(row)){
			Vector<Fraction> rowContent = new Vector<Fraction>();
			row--;
			for(int i = 0; i < this.columns; i++){
				rowContent.add(content[row][i].clone());
			}
			return rowContent;
		}
		else{
			throw new IllegalArgumentException("Zeile ist nicht im gültigen Bereich");
		}
	}
	
	public Vector<Fraction> getContentAsVector(){
		Vector<Fraction> values = new Vector<Fraction>();
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				values.add(this.content[i][j].clone());
			}
		}
		return values;
	}

	/**
	 * Creates an exact copy of a matrix which is not the same object
	 * @return
	 */
	@Override
	public Matrix clone(){
		return new Matrix(this.columns, this.rows, this.getContentAsVector());
	}
	
	/**
	 * Checks if two Matrices are the same
	 * @return 0 if they are the same, -1 if they are different;
	 */
	@Override
	public int compareTo(Matrix anotherMatrix) {
		if(this.rows == anotherMatrix.rows && this.columns == anotherMatrix.columns){
			for(int i = 0; i < this.rows; i++){
				for(int j = 0; j < this.columns; j++){
					if(this.content[i][j].compareTo(anotherMatrix.content[i][j]) != 0){
						return -1;
					}
				}
			}
		}
		else{
			return -1;
		}
		return 0;
	}
	
	/**
	 * Generates a random matrix for 1-5 rows and 1-5 columns with values between 0-10
	 * @return Randomly generated matrix
	 */
	public static Matrix generateRandomMatrix(){
		Random rng = new Random();
		int rowCount = 5 - rng.nextInt(4);
		int columnCount = 5 - rng.nextInt(4);
		Vector<Integer> numbers = new Vector<Integer>();
		for(int i = 0; i < rowCount * columnCount; i++){
			numbers.add(rng.nextInt(10));
		}
		return new Matrix(rowCount, columnCount, numbers);
	}
	
	/**
	 * Overloaded method to provide some options
	 * @param rowCount
	 * @param columnCount
	 * @param valueRange from 0 to
	 * @return
	 */
	public static Matrix generateRandomMatrix(int rowCount, int columnCount, int valueRange){
		Random rng = new Random();
		Vector<Integer> numbers = new Vector<Integer>();
		for(int i = 0; i < rowCount * columnCount; i++){
			numbers.add(rng.nextInt(valueRange));
		}
		return new Matrix(rowCount, columnCount, numbers);
	}
}
