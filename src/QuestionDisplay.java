import java.awt.Graphics;
import java.awt.TextField;

import javax.swing.JPanel;

public class QuestionDisplay extends JPanel {
	private Question question;
	private boolean displayAnswerStr;
	private String answer;
	private TextField inputField;
	
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         //access model to output question
         if (displayAnswerStr) {
        	 g.drawString(answer, 20, 30);
         } else {
        	 g.drawString(question.getQString(), 20, 30 );
         }
      }
      
      public Question getQuestion() {
    	  return question;
      }
      
      public void setQuestion (Question q) {
    	  question = q;
      }
      
      public void setDisplayAnswerStr (boolean b, String str) {
    	  displayAnswerStr = b;
    	  answer = str;
      }
      
      public void redrawDisplay() {
    	  repaint();
      }
      
      public void addTextField(TextField tf) {
    	  inputField = tf; 
      }
   }