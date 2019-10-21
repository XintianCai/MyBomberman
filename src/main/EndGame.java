package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class EndGame implements ActionListener{
	JPanel panel1=new JPanel();
	JPanel panel2=new JPanel();
	JPanel panel3=new JPanel();
	JLabel label1=new JLabel();
	JFrame frame =new JFrame();
	private JButton button1,button2;
	StartGame startGame;
	boolean restart=false;
	
	public void run(boolean gameResult){
		frame.setTitle("Bomberman Version 1");
		panel1.setLayout(new GridLayout(2,1));
		panel2.setLayout(null);
		
		if(gameResult==false){
			label1.setText("You were killed!");
			label1.setFont(new Font("",1,38));
		}
		else{
			label1.setText("Congratulations! You killed all the monsters.");
			label1.setFont(new Font("",1,38));
		}
		JLabel label2=new JLabel("Play again?",JLabel.CENTER);
		label2.setFont(new Font("",1,38));
		
	    button1=new JButton("OK");
	    button1.addActionListener(this);
	    button1.setBounds(180, 130, 500, 80);
	    button1.setFont(new Font("",1,24));
	    	
	    button2=new JButton("Exit");
	    button2.addActionListener(this);
	    button2.setBounds(180, 280, 500, 80);
	    button2.setFont(new Font("",1,24));
	    
	    panel1.add(label1);
	    panel3.add(label2);
	    panel2.add(button1);
	    panel2.add(button2);
	    
	    frame.add(panel1,BorderLayout.NORTH);
	    frame.add(panel2,BorderLayout.CENTER);
	    frame.add(panel3,BorderLayout.SOUTH);
	    frame.pack();
		frame.setSize(900,900);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton eventSource =(JButton)e.getSource();
		if(eventSource.equals(button1)){
			frame.dispose();
			panel1.removeAll();
			panel2.removeAll();
			restart=true;
		}
		else if(eventSource.equals(button2)){
			System.exit(0);
		}
	}
}
