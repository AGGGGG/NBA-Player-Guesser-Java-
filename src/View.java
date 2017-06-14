import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.net.URL;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class View {
	
	private boolean isInitial = true;
	private Model model;
	public JButton yesButton = new JButton("Yes");
	public JButton noButton = new JButton("No");
	public JButton idkButton = new JButton ("I'm not sure");
	public JButton undoButton = new JButton("Undo");
	private JPanel buttons = new JPanel();
	private QuestionDisplay displayPanel = new QuestionDisplay();
	private JTextField inputField = new JTextField("Enter player");
	//private JLayeredPane content = new JLayeredPane(); //NOTE: CHANGED FROM JPANEL CONTENT = NEW JPANEL();
	public JPanel content = new JPanel();
	String answer = "";
	
	
	//TESTING PURPOSES
	UIManager uiManager = new UIManager();

	
	public View (Model m) {
		//initLookAndFeel();
		//SwingUtilities.updateComponentTreeUI(yesButton);
		//System.out.println ("LOOK AND FEEEEELLLL: " + manager.getLookAndFeel());
		
		//set up model initially
		model = m;
		//since intializes with same question every time, don't need to do much with model
		

		
		
		
		
		//QuestionDisplay displayPanel = new QuestionDisplay();
		//JButton yesButton = new JButton("Yes");
	    //JButton noButton = new JButton("No");
	    //ButtonHandlerYes yesListener = new ButtonHandlerYes();
	    //ButtonHandlerNo noListener = new ButtonHandlerNo();
	    
		//yesButton.addActionListener(yesListener);
		//noButton.addActionListener(noListener);
		
		yesButton.setPreferredSize(new Dimension(150, 113));
		//noButton.setPreferredSize(new Dimension(100, 75));
		noButton.setPreferredSize(new Dimension(150, 113));
		idkButton.setPreferredSize(new Dimension(150, 113));
		undoButton.setPreferredSize(new Dimension(150, 113));
		
		

		content.setLayout(new BorderLayout());
//		CardLayout cl = new CardLayout();
//		content.setLayout(cl);
//		
//		content.add(buttons, "buttons");
//		content.add(displayPanel, "display");
//		content.add(inputField, "inputField");
//		content.add(buttons, BorderLayout.SOUTH);
//		content.add(inputField, BorderLayout.SOUTH);
//		content.add(displayPanel, BorderLayout.CENTER);
//		cl.show(content, "buttons");
//		cl.show(content, "display");
		
		
		
		content.add(displayPanel, BorderLayout.CENTER);
		content.add(buttons, BorderLayout.SOUTH);
		content.add(inputField, BorderLayout.EAST);
		
		displayPanel.setQuestion(m.initialQuestions.get(0));

		
//		buttons.setLayout(new BorderLayout());
		
		buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttons.add(yesButton);
		buttons.add(noButton);
		buttons.add(undoButton);
		buttons.add(idkButton);
		
		
//		CardLayout cl = new CardLayout();
//		cl.addLayoutComponent(buttons, BorderLayout.SOUTH);
//		cl.addLayoutComponent(displayPanel, BorderLayout.CENTER);
//		cl.addLayoutComponent(inputField, BorderLayout.SOUTH);
//		cl.show(buttons, "1");
//		cl.show(displayPanel, "1");
//		content.setLayout(cl);
		
		

		inputField.setVisible(false);
		
		
		//inputField.addActionListener(enterPress);
		//content.setLayer(inputField, 2, BorderLayout.SOUTH);
		//content.setLayer(inputField, 1);
		//content.moveToFront(inputField);
		//inputField.setVisible(false);

//		***COMMENTED OUT GOOD CODE***
//		buttons.add(yesButton, BorderLayout.WEST);
//		buttons.add(noButton, BorderLayout.EAST);
//		buttons.add(idkButton, BorderLayout.CENTER);
//		buttons.add(undoButton, BorderLayout.SOUTH);
		
//		buttons.add(yesButton, BorderLayout.WEST);
//		buttons.add(noButton, BorderLayout.EAST);
//		buttons.add(idkButton, BorderLayout.CENTER);
//		buttons.add(undoButton, BorderLayout.SOUTH);
		
		idkButton.setVisible(false);
		//content.setLayer(buttons, 0);
		//content.moveToBack(buttons);

		JFrame window = new JFrame("GUI Test");
		window.setContentPane(content);
		//window.setSize(500,200);
		window.setSize(750, 250);
		window.setLocation(100,100);
		window.setVisible(true);
		
		//initLookAndFeel();
	}
	
	//set the LookAndFeel (UI and style of program) by loading an XML file
	//ISSUE: nothing happens after look and feel is set
	public void initLookAndFeel() {
		SynthLookAndFeel laf = new SynthLookAndFeel();
		
		try {
			//Class cls = Class.forName("View");
			//ClassLoader clsLoader = cls.getClassLoader();
			//InputStream i = clsLoader.getResourceAsStream("synthStyle.xml");
			//laf.load(i, cls);
			laf.load(View.class.getResourceAsStream("synthStyle.xml"), View.class);
			//laf.load(new URL("file://:synthStyle.xml"));
			uiManager.setLookAndFeel(laf);
			//uiManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			System.out.println("ERROR: couldn't get look and feel");
			e.printStackTrace();
		}
	}
	
	public void addYesListener (ActionListener a) {
		yesButton.addActionListener(a);
	}

	public void addNoListener (ActionListener a) {
		noButton.addActionListener(a);
	}
	
	public void addIdkListener (ActionListener a) {
		idkButton.addActionListener(a);
	}
	
	public void addUndoListener (ActionListener a) {
		undoButton.addActionListener(a);
	}
	
	public void addInputListener (ActionListener a) {
		inputField.addActionListener(a);
	}
	
	public Question getCurrentQuestion () {
		return displayPanel.getQuestion();
	}
	
	public void setCurrentQuestion (Question q) {
		displayPanel.setQuestion(q);
	}
	
	public void setIsInitial (boolean b) {
		isInitial = b;
	}
	
	public boolean getIsInitial() {
		return isInitial;
	}
	
	public QuestionDisplay getDisplayPanel() {
		return displayPanel;
	}
	
	public JTextField getInputField() {
		return inputField;
	}
	
	public JPanel getContent() {
		return content;
	}
	
	public JButton getUndoButton() {
		return undoButton;
	}
	
	public JPanel getButtons() {
		return buttons;
	}
	
	//NECESSARY TO REDRAW DISPLAY HERE??
	public void displayInputFieldAnswer() {
//		inputField = new JTextField();
//		inputField.setText("Please enter your player");
		//buttons.setVisible(false);
		//inputField.setVisible(true);
		//content.moveToFront(inputField);
		//inputField.setVisible(true);
		//content.moveToBack(buttons);
		
		
		inputField.setVisible(true);
		
		
//		content.add(inputField, BorderLayout.SOUTH);
//		inputField.setVisible(true);
		
		//buttons.setVisible(false);
		yesButton.setVisible(false);
		noButton.setVisible(false);
		idkButton.setVisible(false);
		undoButton.setVisible(false);
		//String answer = inputField.getText();
		//return answer;
	}
	
	/*
	public String displayInputFieldQuestion() {
		final TextField answerField = new TextField();
		answerField.setText("What is one question that could have been asked which would have allowed me to guess the player?");
		String answer = answerField.getText();
		return answer;
	} */
}
