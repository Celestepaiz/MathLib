package gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MatrixReader extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	JTextArea matrixInput;
	JTextField matrixArrayOutput;
	Scanner inputScanner;
	
	public MatrixReader(){
		super("Matrix Converter");
		setLayout(new BorderLayout());
		matrixInput = new JTextArea(20,30);
		matrixInput.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				convertInput();
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				convertInput();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		add(matrixInput, BorderLayout.CENTER);
		matrixArrayOutput = new JTextField();
		add(matrixArrayOutput, BorderLayout.SOUTH);
	}
	
	public void convertInput(){
		StringBuilder convertedString = new StringBuilder();
		inputScanner = new Scanner(matrixInput.getText());
		while(inputScanner.hasNext()){
			convertedString.append(inputScanner.next() + " ,");
		}
		matrixArrayOutput.setText(convertedString.toString());
	}
	
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MatrixReader reader = new MatrixReader();
				reader.pack();
				reader.setLocationRelativeTo(null);
				reader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				reader.setVisible(true);
			}
		});
	}
}
