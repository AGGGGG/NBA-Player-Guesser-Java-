import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class Model {
	//set of user responses (y for yes, n for no)
	public ArrayList<String> responses = new ArrayList<String>();

	//has UNALTERED list of questions; used for machine learning algorithm so can ensure no questions lost as modify machinelearning file
	public ArrayList<Question> overallQuestions = new ArrayList<Question>();

	//list of all of questions (to be altered as questions removed as asked)
	public ArrayList<Question> questions = new ArrayList<Question>();

	//initial questions -- hard-coded in
	public ArrayList<Question> initialQuestions = new ArrayList<Question>();

	//list of the overall answers; will be changed as it goes along
	public ArrayList<String> overallAnswers = new ArrayList<String>();
	
	//used to ensure undo button can return to overallAnswers before other question was asked
	public ArrayList<String> previousOverallAnswers = new ArrayList <String>();

	//list of questions that have been asked (so can increment responses in ML algorithm)
	public ArrayList <Question> questionsAsked = new ArrayList<Question>();

	//to error check
	public int errorCheckCounter = 0;

	public Model() throws ClassNotFoundException {
		reset();
	}

	//reset Model to original set of data
	//reset questions to questions in file
	public void reset() throws ClassNotFoundException {
		try {
			//FileInputStream fileIn = new FileInputStream("questionsList.ser");
			FileInputStream fileIn = new FileInputStream("machineLearningList.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			questions = (ArrayList<Question>) in.readObject();
			for (int i = 0; i < questions.size(); i++) {
				overallQuestions.add(questions.get(i));
			}
			in.close();
			fileIn.close();
			System.out.printf("Serialized data read in");
		}catch(IOException i) {
			i.printStackTrace();
		}

		/*
		responses = new ArrayList<String>(); //MIGHT NOT BE NECESSARY
		questionsAsked = new ArrayList<Question>(); //MIGHT NOT BE NECESSARY
		overallAnswers = new ArrayList<String>(); //MIGHT NOT BE NECESSARY
		 */

		//Initialize intialquestions -- order may need to be switched slightly so matches yesyesyes files, etc
		initialQuestions.add(new Question ("Does he play in the Western Conference?"));
		initialQuestions.add(new Question ("Is he 6 foot 8 inches or taller?"));
		initialQuestions.add(new Question ("Is he 27 years or older?"));
	}

	//get next question by checking entropy and finding one with entropy closes to 0.5 (so either yes or no answer should eliminate a bunch of answers)
	public Question getNextQuestion(ArrayList<String> overallAnswers) {
		Question retQ = questions.get(0);
		double retEntropy = Math.abs(overallAnswers.size()/2 - getEntropy(retQ));
		for (Question q : questions) {
			if (Math.abs(overallAnswers.size()/2 - getEntropy(q)) < retEntropy) {
				retEntropy = Math.abs(overallAnswers.size()/2 - getEntropy(q));
				retQ = q;
			}
		}
		//questionsAsked.add(retQ);
		questions.remove(retQ);


		//TESTING
		if (errorCheckCounter == 19) {
			int x = 1;
		}
		errorCheckCounter++; // TESTING
		System.out.println("Question Removed: " + errorCheckCounter); //TESTING
		return retQ;
	}


	//remove answers if answer is yes
	//TIME: O(overallAnswers)
	public void removeAnswersYes(Question q) {
		for (int i = 0; i < overallAnswers.size(); i++) {
			if (!q.getQAnswers().contains(overallAnswers.get(i))) {
				overallAnswers.remove(i);
				i--;
			}
		}
	}

	//remove answers if answer is no
	//TIME: O(overallAnswers)
	public void removeAnswersNo(Question q) {
		for (int i = 0; i < overallAnswers.size(); i++) {
			if (q.getQAnswers().contains(overallAnswers.get(i))) {
				overallAnswers.remove(i);
				i--;
			}
		}
	}

	//returns how many answers can be eliminated
	//TIME: O(oA * qA) [essentially O(n^2)]
	public int getEntropy(Question q) {
		int entropyCounter = 0;
		for (int i = 0; i < overallAnswers.size(); i++) {
			if (!q.getQAnswers().contains(overallAnswers.get(i))) {
				entropyCounter++;
			}
		}
		return entropyCounter;
	}

	//pass question and answer and response string to OriginalData from the Model
	//REMEMBER TO BACK UP
	public void machineLearning(String answer, String answerQuestion) {
		System.out.println("MACHINE LEARNING CALLED");
		for (int i = 3; i < responses.size(); i++) {
			if (responses.get(i).equals("y")) {

				if (!questionsAsked.get(i).getQAnswers().contains(answer)) {
					for (int j = 0; j < overallQuestions.size(); j++) {
						if (questionsAsked.get(i).getQString().equals(overallQuestions.get(j).getQString())) {
							overallQuestions.get(j).addAnswer(answer);
							break;
						}
					}

					//adds new question with nba player user was thinking of as answer to OriginalData
					HashSet<String> answers = new HashSet<String>();
					answers.add(answer);
					overallQuestions.add(new Question (answerQuestion, answers));
				}

			}
			OriginalData.questions = overallQuestions; 
			ArrayList<Question> tester = OriginalData.questions;
			OriginalData.serializeAndWriteToFile("machineLearningList.ser");
		}
	}
	
	public void machineLearning(String answer) {
		System.out.println("MACHINE LEARNING CALLED");
		for (int i = 3; i < responses.size(); i++) {
			if (responses.get(i).equals("y")) {

				if (!questionsAsked.get(i).getQAnswers().contains(answer)) {
					for (int j = 0; j < overallQuestions.size(); j++) {
						if (questionsAsked.get(i).getQString().equals(overallQuestions.get(j).getQString())) {
							overallQuestions.get(j).addAnswer(answer);
							break;
						}
					}
				}

			}
			OriginalData.questions = overallQuestions; 
			ArrayList<Question> tester = OriginalData.questions;
			OriginalData.serializeAndWriteToFile("machineLearningList.ser");
		}
	}





	/*

	//changes question in OriginalData to add the answers
	if (!questionsAsked.get(i).getQAnswers().contains(answer)) {
		for (int j = 0; j < OriginalData.questions.size(); j++) {
			if (questionsAsked.get(i).getQString().equals(OriginalData.questions.get(i).getQString())) {
				OriginalData.questions.get(i).addAnswer(answer);
			}
		}

		//adds new question with nba player user was thinking of as answer to OriginalData
		HashSet<String> answers = new HashSet<String>();
		answers.add(answer);
		OriginalData.questions.add(new Question (answerQuestion, answers));
	}
}
ArrayList<Question> tester = OriginalData.questions; */
	//make sure this is from original data
	//CAN JUST MODIFY FILE TO HAVE EXTRA QUESTION, EXTRA ANSWERS	
	/*
if (!originalDataInstance.questions.contains(answerQuestion) {
	HashSet String qAnswers = new HashSet<String>();
	qAnswers.add(answer);
	originalDataInstance.questions.add(new Question (answerQuestion, qAnswers);
}
	 */



	/*//get a question from the hashset containing the questions with the string that represents the question
	public Question getQuestionFromString (String q) {

	}*/

}
