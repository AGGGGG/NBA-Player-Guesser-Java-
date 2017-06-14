
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*
 * USE THIS CLASS TO MANUALLY ADJUST DATA IF NECESSARY
 * TYPE METHODS NEEDED AND EXECUTE AS NEEDED (similar to OriginalData)
 */
public class DataMaintenance {
	
	private static ArrayList<Question> questions = new ArrayList<Question>();
	
	public static void main (String [] args) throws ClassNotFoundException {
		readInQuestions();
		
		//HERE, do manual changes; examples:
		/*
		 * wipeQuestion("Is he the worst player in the league?");
		 * wipeAnswer ("John Wall", 5, 2, 6, 3);
		 */
		
		//writeToFile("BACKUP_DATA_FILE.txt");
		//correctQuestion("Did he forgo college?", "Did he not go to college?");
		//writeToFile("machineLearningList.ser");
	}
	
	public static void readInQuestions() throws ClassNotFoundException {
		try {
			//FileInputStream fileIn = new FileInputStream("questionsList.ser");
			FileInputStream fileIn = new FileInputStream("machineLearningList.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			questions = (ArrayList<Question>) in.readObject();
			in.close();
			fileIn.close();
			System.out.printf("Serialized data read in");
		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	public static void writeToFile(String fileOutName) {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(fileOutName);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(questions);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved in " + fileOutName);
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	}
	
	//take answer which is incorrect and wipe it from all questions input as parameters
//	public static void wipeAnswer(String answer, Question... badAnswerQuestions) {
//		for (Question q : badAnswerQuestions) {
//			q.removeAnswer(answer);
//		}
//	}
	
	//take answer which is incorrect and wipe it from all questions input as parameters (in the form of indices in questions array)
	//NOTE: must manually find relevant question indices so that method can find them and remove relevant answer
	public static void wipeAnswer(String answer, int... questionIndices) {
		for (int i : questionIndices) {
			questions.get(i).removeAnswer(answer);
		}
}
	
	//delete bad question (and thus delete qString and answers associated with said question)
	public static void wipeQuestion(String qString) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getQString().equals(qString)) {
				questions.remove(i);
			}
		}
	}
	
	//parameter 'name' should be the correctly capitalized name
	//NOTE: correct is used as a verb in title of method
	public static void correctName (String name, Question q) {
		for (String n : q.getQAnswers()) {
			if (n.toLowerCase().equals(name.toLowerCase())) {
				n = name;
			}
		}
	}
	
	public static void correctQuestion (String newQ, String oldQ) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getQString().equals(oldQ)) {
				questions.get(i).setQString(newQ);
				int x = 1;
			}
		}
	}
}
