package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Scanner;
import java.util.Vector;

import math.Matrix;
import math.Fraction;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class MatrixCalculator extends JFrame {

	private static final long serialVersionUID = -5111396894528359139L;
	
	JTextArea matrixAInput;
	JComboBox<String> methodChooser;
	JTextArea matrixBInput;
	JTextArea matrixResult;
	JButton calculateButton;
	JLabel matrixAErrorLabel;
	
	public MatrixCalculator(){
		super("Matrix Rechner");
		setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel(new FlowLayout());
		JPanel matrixAPanel = new JPanel(new BorderLayout());
		JLabel matrixALabel = new JLabel("Matrix A:");
		matrixAPanel.add(matrixALabel, BorderLayout.NORTH);
		matrixAErrorLabel = new JLabel("");
		matrixAPanel.add(matrixAErrorLabel, BorderLayout.SOUTH);
		matrixAInput = new JTextArea(10,20);
		matrixAInput.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				try{
					readMatrix(matrixAInput.getText());
				}
				catch(IllegalArgumentException ex){
					matrixAInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					matrixAErrorLabel.setText(ex.getMessage());
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				matrixAInput.setBorder(null);
				matrixAErrorLabel.setText("");
			}
		});
		matrixAPanel.add(matrixAInput, BorderLayout.CENTER);
		String[] operations = {"+","-","*"};
		methodChooser = new JComboBox<String>(operations);
		matrixBInput = new JTextArea(10, 20);
		matrixBInput.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				try{
					readMatrix(matrixBInput.getText());
				}
				catch(IllegalArgumentException ex){
					matrixBInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 2), ex.getMessage(), TitledBorder.LEFT, TitledBorder.BELOW_BOTTOM));
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				matrixBInput.setBorder(null);
				
			}
		});
		inputPanel.add(matrixAPanel);
		inputPanel.add(methodChooser);
		inputPanel.add(matrixBInput);
		add(inputPanel, BorderLayout.CENTER);
		JPanel resultPanel = new JPanel(new BorderLayout());
		calculateButton = new JButton("Berechnen!");
		calculateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				calculateResultMatrix();
				
			}
		});
		resultPanel.add(calculateButton, BorderLayout.NORTH);
		matrixResult = new JTextArea(10,20);
		matrixResult.setTabSize(1);
		resultPanel.add(matrixResult, BorderLayout.CENTER);
		add(resultPanel, BorderLayout.SOUTH);
	}
	
	public Matrix readMatrix(String input) throws IllegalArgumentException{
		Scanner inputReader = new Scanner(input);
		int rowCount = 0;
		int columnCount = 0;
		Vector<Fraction> matrixValues = new Vector<Fraction>();
		while(inputReader.hasNextLine()){
			rowCount++;
			int currentColumnCount = 0;
			Scanner lineReader = new Scanner(inputReader.nextLine());
			while(lineReader.hasNext()){
				currentColumnCount++;
				matrixValues.add(Fraction.parseFraction(lineReader.next()));
			}
			if(columnCount == 0){
				columnCount = currentColumnCount;
			}
			else if(currentColumnCount != columnCount && currentColumnCount != 0){
				lineReader.close();
				throw new IllegalArgumentException("Zeile " + rowCount + " hat " + (currentColumnCount < columnCount? "zu wenig ": "zu viele ") + "Spalten");
			}
			lineReader.close();
		}
		inputReader.close();
		return new Matrix(rowCount, columnCount, matrixValues);
	}
	
	public void calculateResultMatrix(){
		try{
			Matrix matrixA = readMatrix(matrixAInput.getText());
			Matrix matrixB = readMatrix(matrixBInput.getText());
			Matrix result = null;
			switch((String)methodChooser.getSelectedItem()){
			case "+":
				result = matrixA.add(matrixB);
				break;
			case "-":
				result = matrixA.subtract(matrixB);
				break;
			case "*":
				result = matrixA.multiply(matrixB);
				break;
			}
			if(result == null){
				matrixResult.setText("Unbekannte Operation!");
			}
			else{
				matrixResult.setText(result.toString());
			}
		}
		catch(IllegalArgumentException e){
			matrixResult.setText(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			MatrixCalculator calculator = new MatrixCalculator();
			calculator.pack();
			calculator.setLocationRelativeTo(null);
			calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			calculator.setVisible(true);
		}
	});
}
}
