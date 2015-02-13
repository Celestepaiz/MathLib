package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import math.Fraction;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BruchRechner extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField inputField;
	private JTextField outputField;
	private JButton calculateButton;
	private JCheckBox cancelCheckBox;
	
	public BruchRechner(){
		super("Bruchrechner");
		JPanel inputPanel = new JPanel(new GridLayout(3,1));
		inputField = new JTextField();
		outputField = new JTextField();
		calculateButton = new JButton("Berechnen");
		calculateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				processInput();
			}
		});
		inputPanel.add(inputField);
		inputPanel.add(calculateButton);
		inputPanel.add(outputField);
		cancelCheckBox = new JCheckBox("Kürzen");
		add(inputPanel, BorderLayout.CENTER);
		add(cancelCheckBox, BorderLayout.SOUTH);
	}
	
	private void processInput(){
		String input = inputField.getText();
		Scanner sc = new Scanner(input);
		List<String> equation = new ArrayList<String>();
		while(sc.hasNext()){
			String item = sc.next();
			equation.add(item);
		}
		sc.close();
		int counter = 1;
		for(String item: equation){
			if(counter % 2 != 0){
				if(item.matches("[*/+-]{1}")){
					outputField.setText("Falsches Format!");
					return;
				}
			}
			counter++;
		}
		Fraction result;
		try{
			result = Fraction.parseFraction(equation.get(0));
		}
		catch(IllegalArgumentException e){
			outputField.setText(e.getMessage());
			return;
		}
		for(int i = 1; i < equation.size(); i = i+2){
			if(i != equation.size()-1){
				switch(equation.get(i)){
				case "*":
					Fraction multiplicator;
					try{
						multiplicator = Fraction.parseFraction(equation.get(i+1));
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					result = result.multiply(multiplicator);
					break;
				case "+":
					Fraction adder;
					try{
						adder = Fraction.parseFraction(equation.get(i+1));
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					result = result.add(adder);
					break;
				case "-":
					Fraction subtractor;
					try{
						subtractor = Fraction.parseFraction(equation.get(i+1));
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					result = result.subtract(subtractor);
					break;
				case "/":
					Fraction divider;
					try{
						divider = Fraction.parseFraction(equation.get(i+1));
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					try{
						result = result.divide(divider);
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					break;
				}
			}
		}
		if(cancelCheckBox.isSelected()){
			result.reduce();
		}
		outputField.setText(result.toString());
	}
	
	public static void main(String[] args) {
		BruchRechner rechner = new BruchRechner();
		rechner.pack();
		rechner.setLocationRelativeTo(null);
		rechner.setSize(200,200);
		rechner.setVisible(true);
	}
}
