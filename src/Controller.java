import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class Controller {
	//KEY: create action listeners as INTERNAL class, pass in model and view!! then action listeners will have access to model and view
	
	private Model model;
	private View view;
	
	//when equals 20, make guess
	private int questionCounter = 0;
	
	//to check when done with initial questions
	private int initialQuestionCounter = 1;
	
	//used to ensure that model.removeQuestionsYes/No happens AFTER first non-initial question is stated (NOT after last initial question is stated)
	private int skipCounter = 0;
	
	//used to know when user pressing enter for second time (corresponds to asking user what question could have been asked)
	private int enterCounter = 0;
	
	//update if get answer wrong and need to perform machine learning algorithm; answer = actual player, answerQuestion = question could have asked to get it right
	private String answer;
	private String answerQuestion;
	
	//used to communicate with button and tell it if: 1. the guess is displayed; 2. the next response is responding to the guess (i.e. telling whether the guess is correct)
	private boolean guessResponse;
	
	//tells if guess is correct
	private boolean answerWrong = false;
	
	//to trigger ML algorithm after answer to question is yes
	private boolean doMachLearningYes = false;
	
	//tells if program asking user for question (so that idk button can have different functionality)
	private boolean inputQuestionIdk = false;
	
	//tells if program asking about play again
	private boolean askingForReplay = false;
	
	public Controller (Model m, View v) {
		model = m;
		view = v;
		
		//add listeners to JButtons
		view.addNoListener(new NoListener());
		view.addYesListener(new YesListener());
		view.addIdkListener(new IdkListener());
		view.addUndoListener(new UndoListener());
		view.addInputListener(enterPress);
		
		//create the first question (hard coded in); isInitial = true
		Question firstQ = new Question ("Does your player play in the Western Conference?");
	}
	
	//for inputTextField
	Action enterPress = new AbstractAction()
	{
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
	    	if (enterCounter == 0) {
	    		answer = view.getInputField().getText();
		        System.out.println("ENTER PRESSEDDDDDDDD");
		        System.out.println(answer);
		        view.getDisplayPanel().setDisplayAnswerStr(true, "What question could have been used so that your player could have been guessed?");
		        view.idkButton.setVisible(true);
		        view.getDisplayPanel().redrawDisplay();
		        view.getInputField().setText("Enter question");
		        inputQuestionIdk = true;
		        enterCounter++;
	    	} else {
	    		answerQuestion = view.getInputField().getText();
	    		System.out.println("ANSWER QUESTION: " + answerQuestion);
	    		model.machineLearning(answer, answerQuestion);
	    		view.getDisplayPanel().setDisplayAnswerStr(true, "Play again?");
				view.idkButton.setVisible(false);
				view.yesButton.setVisible(true);
				view.noButton.setVisible(true);
				view.getInputField().setVisible(false);
				askingForReplay = true;
	    	}
	        
	    }
	};
	
	//check which file to read in
	//read in file and change model to indicate this
	public void checkAndInitializeAnswers() throws FileNotFoundException {
		File file;
		
		if (model.responses.get(0) == "y") { //yes
			if (model.responses.get(1) == "y") { //yes yes
				if (model.responses.get(2) == "y") { //yes yes yes
					file = new File ("yesyesyes");
				} else { //yes yes no
					file = new File ("yesyesno");
				}
			} else { //yes no
				if (model.responses.get(2) == "y") { //yes no yes
					file = new File ("yesnoyes");
				} else { //yes no no
					file = new File ("yesnono");
				}
			}
		} else {
			if (model.responses.get(1) == "y") { //no yes
				if (model.responses.get(2) == "y") { //no yes yes
					file = new File ("noyesyes");
				} else { //no yes no
					file = new File ("noyesno");
				}
			} else { //no no
				if (model.responses.get(2) == "y") { // no no yes
					file = new File ("nonoyes");
				} else { //no no no
					file = new File ("nonono");
				}
			}
		}
		
		//read file
		Scanner scan = new Scanner (file);
		while (scan.hasNextLine()) {
			model.overallAnswers.add(scan.nextLine());
		}
	}
	
	/*
	 * FOR UNDO BUTTON:
	 * decrements questionsAsked (and intialQuestionsAsked if necessary)
	 * sets view question to previous questions
	 * removes last element of responses and questionsAsked
	 * NOTE: BUG -- forgot to change overallAnswers back to original state BEFORE question was asked
	 */
	class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setCurrentQuestion(model.questionsAsked.get(model.questionsAsked.size()-1));
			model.responses.remove(model.responses.size()-1);
			model.questionsAsked.remove(model.questionsAsked.size()-1);
			questionCounter--;
			if (initialQuestionCounter < 4) {
				initialQuestionCounter--;
			}
			view.getDisplayPanel().redrawDisplay();
		}
		
	}
	
	class IdkListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//done when user has no question to input; simply executes machineLearning without a question, ends program
			if (inputQuestionIdk) {
				model.machineLearning(answer);
				//view.getContent().setVisible(false);
				view.getDisplayPanel().setDisplayAnswerStr(true, "Play again?");
				view.idkButton.setVisible(false);
				view.yesButton.setVisible(true);
				view.noButton.setVisible(true);
				view.getInputField().setVisible(false);
				askingForReplay = true;
			}
			// TODO Auto-generated method stub
			if (guessResponse) {
				//FIGURE OUT WHAT TO DO
			} else {
				model.overallAnswers = model.previousOverallAnswers;
				model.responses.add("i");
				model.questionsAsked.add(view.getCurrentQuestion());
				Question nextQ;
				if (initialQuestionCounter == 3) {
					view.setIsInitial(false);
					try {
						checkAndInitializeAnswers();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					initialQuestionCounter++;
				}
				if (!view.getIsInitial()) {
					if (skipCounter == 0) {
						skipCounter++;
					}
					nextQ = model.getNextQuestion(model.overallAnswers);	
					questionCounter++;
				} else {
					nextQ = model.initialQuestions.get(initialQuestionCounter);
					initialQuestionCounter++;
					questionCounter++;
				}
				view.setCurrentQuestion(nextQ);
			}
			
			if (model.overallAnswers.size() == 1 && !inputQuestionIdk) {
				//view.setCurrentQuestion();
				view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ model.overallAnswers.get(0) + "?");
				guessResponse = true;
			} else if (!view.getIsInitial() && questionCounter != 0 && (model.overallAnswers.size() == 0 || answerWrong) && !inputQuestionIdk) {
				view.getDisplayPanel().setDisplayAnswerStr(true, "No answers left in database. Who is your player?");
				//answer = view.displayInputFieldAnswer();
				view.displayInputFieldAnswer();
				//view.getDisplayPanel().setDisplayAnswerStr(true, "What is one question that could have been asked that could have helped guess your player?");
				//answerQuestion = view.displayInputFieldQuestion();
			} else if (questionCounter == 20) {
				//***NOTE: TRY TO IMPROVE ON THIS, MAKE IT NOT RANDOM (WEIGHT IT SOMEHOW)!!!
				view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ model.overallAnswers.get((int) (Math.random() * model.overallAnswers.size())) + "?");
				guessResponse = true;
			}
			view.getDisplayPanel().redrawDisplay();
		}		
	}
	
	class YesListener implements ActionListener {
		@Override
		//register that yes pressed (so add it to the responses arraylist)
		//update answers
		//can do both of these by calling methods in model
		//then output next question
		public void actionPerformed(ActionEvent e) {
			if (askingForReplay) {
				try {
					Main.init();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (guessResponse) {
				view.getDisplayPanel().setDisplayAnswerStr(true, "Yay! Play Again?");
				//view.getButtons().setVisible(false);
				view.idkButton.setVisible(false);
				view.undoButton.setVisible(false);
				//view.getDisplayPanel().redrawDisplay();
				answerWrong = false;
				doMachLearningYes = true;
				askingForReplay = true;
			}
			else {
				//model.previousOverallAnswers = model.overallAnswers;
				model.responses.add("y");
				model.questionsAsked.add(view.getCurrentQuestion());
				Question nextQ;
				if (initialQuestionCounter == 3) {
					view.setIsInitial(false);
					view.idkButton.setVisible(true);
					try {
						checkAndInitializeAnswers();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					initialQuestionCounter++;
				}
				if (!view.getIsInitial()) {
					if (skipCounter == 0) {
						skipCounter++;
					} else {
						model.removeAnswersYes(view.getCurrentQuestion());
					}
					nextQ = model.getNextQuestion(model.overallAnswers);	
					questionCounter++;
				} else {
					nextQ = model.initialQuestions.get(initialQuestionCounter);
					initialQuestionCounter++;
					questionCounter++;
				}
				view.setCurrentQuestion(nextQ);
			}
			/*
			 * CHECK IF READY TO ANSWER
			 */
			//FIX THIS!!!!!!!!!!!!!!!!!!!!
			if (!answerWrong && doMachLearningYes) {
				//view.getDisplayPanel().setDisplayAnswerStr(true, "Yay!");
				model.machineLearning(answer);
				int x = 1; //testing
			}
			if (model.overallAnswers.size() == 1 && !guessResponse) {
				//view.setCurrentQuestion();
				answer = model.overallAnswers.get(0);
				view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ model.overallAnswers.get(0) + "?");
				guessResponse = true;
			} else if (!view.getIsInitial() && questionCounter != 0 && (model.overallAnswers.size() == 0 || answerWrong)) {
				view.getDisplayPanel().setDisplayAnswerStr(true, "No answers left in database. Who is your player?");
				//answer = view.displayInputFieldAnswer();
				view.displayInputFieldAnswer();
				//view.getDisplayPanel().setDisplayAnswerStr(true, "What is one question that could have been asked that could have helped guess your player?");
				//answerQuestion = view.displayInputFieldQuestion();
			} else if (questionCounter == 20) {
				//***NOTE: TRY TO IMPROVE ON THIS, MAKE IT NOT RANDOM (WEIGHT IT SOMEHOW)!!!
				answer = model.overallAnswers.get((int) (Math.random() * model.overallAnswers.size()));
				view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ answer + "?");
				//view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ model.overallAnswers.get((int) (Math.random() * model.overallAnswers.size())) + "?");
				guessResponse = true;
			}
			//TESTING PURPOSES
			for (int i = 0; i < model.overallAnswers.size(); i++) {
				System.out.println(model.overallAnswers.get(i));
			}
			//
			view.getDisplayPanel().redrawDisplay();
		} 
		
	}
	
	class NoListener implements ActionListener {
		@Override
		//register that no pressed (so add it to the responses arraylist)
		//update answers
		//can do both of these by calling methods in model
		public void actionPerformed(ActionEvent e) {
			if (askingForReplay) {
				System.exit(0);
			}
			if (guessResponse) {
				//view.getDisplayPanel().setDisplayAnswerStr(true, "Oh no!");
				answerWrong = true;
			} else {
				//model.previousOverallAnswers = model.overallAnswers;
				model.responses.add("n");
				model.questionsAsked.add((view.getCurrentQuestion()));
				Question nextQ;
				if (initialQuestionCounter == 3) {
					view.setIsInitial(false);
					view.idkButton.setVisible(true);
					try {
						checkAndInitializeAnswers();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					initialQuestionCounter++;
				}
				if (!view.getIsInitial()) {
					if (skipCounter == 0) {
						skipCounter++;
					} else {
						model.removeAnswersNo(view.getCurrentQuestion());
					}
					nextQ = model.getNextQuestion(model.overallAnswers);
					questionCounter++;
				} else {
					nextQ = model.initialQuestions.get(initialQuestionCounter);
					initialQuestionCounter++;
					questionCounter++;
				}
				view.setCurrentQuestion(nextQ);
				
			}
			//check if ready to answer
			if (model.overallAnswers.size() == 1 && !answerWrong) {
				answer = model.overallAnswers.get(0);
				view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ model.overallAnswers.get(0) + "?");
				guessResponse = true;
			} else if (!view.getIsInitial() && questionCounter != 0 && (model.overallAnswers.size() == 0 || answerWrong)) {
				view.getDisplayPanel().setDisplayAnswerStr(true, "No answers left in database. Who is your player?");
				//answer = view.displayInputFieldAnswer();
				view.displayInputFieldAnswer();
				//view.getDisplayPanel().setDisplayAnswerStr(true, "What question could have been used so that your player could have been guessed?");
				//answerQuestion = view.displayInputFieldQuestion();
			} else if (questionCounter == 20) {
				answer = model.overallAnswers.get((int) (Math.random() * model.overallAnswers.size()));
				view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ answer + "?");
				//view.getDisplayPanel().setDisplayAnswerStr(true, "Is your player "+ model.overallAnswers.get((int) (Math.random() * model.overallAnswers.size())) + "?");
				guessResponse = true;
			}
			//TESTING PURPOSES
			for (int i = 0; i < model.overallAnswers.size(); i++) {
				System.out.println(model.overallAnswers.get(i));
			}
			//
			view.getDisplayPanel().redrawDisplay();
		} 
	}
}
