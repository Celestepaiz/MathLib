package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import math.Bruch;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BruchRechner extends JFrame{
	
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
		Bruch result;
		try{
			result = Bruch.parseBruch(equation.get(0));
			if(cancelCheckBox.isSelected()){
				result.automatischKuerzen(true);
			}
		}
		catch(IllegalArgumentException e){
			outputField.setText(e.getMessage());
			return;
		}
		for(int i = 1; i < equation.size(); i = i+2){
			if(i != equation.size()-1){
				switch(equation.get(i)){
				case "*":
					Bruch multiplicator;
					try{
						multiplicator = Bruch.parseBruch(equation.get(i+1));
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					result.multiplizieren(multiplicator);
				case "+":
					Bruch adder;
					try{
						adder = Bruch.parseBruch(equation.get(i+1));
					}
					catch(IllegalArgumentException e){
						outputField.setText(e.getMessage());
						return;
					}
					result.addieren(adder);
				}
			}
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
