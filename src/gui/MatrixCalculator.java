package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.util.Vector;

import math.Matrix;
import math.Fraction;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MatrixCalculator extends JFrame {

	private static final long serialVersionUID = -5111396894528359139L;
	
	JTextArea matrix1Input;
	JComboBox<String> methodChooser;
	JTextArea matrix2Input;
	JTextArea matrixResult;
	JButton calculateButton;
	
	public MatrixCalculator(){
		super("Matrix Rechner");
		setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel(new FlowLayout());
		matrix1Input = new JTextArea(10,20);
		matrix1Input.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					//TODO
					//getCollumnCount();
				}
			}
		
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		String[] operations = {"+","-","*"};
		methodChooser = new JComboBox<String>(operations);
		matrix2Input = new JTextArea(10, 20);
		inputPanel.add(matrix1Input);
		inputPanel.add(methodChooser);
		inputPanel.add(matrix2Input);
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
		resultPanel.add(matrixResult, BorderLayout.CENTER);
		add(resultPanel, BorderLayout.SOUTH);
	}
	
	public void calculateResultMatrix(){
		Scanner inputReader = new Scanner(matrix1Input.getText());
		Vector<Integer> matrix1Values = new Vector<Integer>();
		while(inputReader.hasNext()){
			matrix1Values.add(Integer.parseInt(inputReader.next()));
		}
		inputReader.close();
		int matrix1Rows = matrix1Input.getLineCount();
		int matrix1Collums = matrix1Values.size() / matrix1Rows;
	
		inputReader = new Scanner(matrix2Input.getText());
		Vector<Integer> matrix2Values = new Vector<Integer>();
		while(inputReader.hasNext()){
			matrix2Values.add(Integer.parseInt(inputReader.next()));
		}
		inputReader.close();
		int matrix2Rows = matrix2Input.getLineCount();
		int matrix2Collums = matrix2Values.size() / matrix1Rows;
		Matrix a = new Matrix(matrix1Rows, matrix1Collums, matrix1Values);
		Matrix b = new Matrix(matrix2Rows, matrix2Collums, matrix2Values);
		switch((String)methodChooser.getSelectedItem()){
		case "+":
			a.addieren(b);
			matrixResult.setText(a.toString());
			break;
		case "-":
			a.subtrahieren(b);
			matrixResult.setText(a.toString());
			break;
		case "*":
			matrixResult.setText(a.multiplizieren(b).toString());
			break;
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
