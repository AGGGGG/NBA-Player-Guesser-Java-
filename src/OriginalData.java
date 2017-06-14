import java.util.*;
import java.io.*;

//store it in a file and change that file through machineLearning algorithm
//Make initial questions, actual questions, 
public class OriginalData {
	//ISSUE: QUESTIONS RUNNING OUT FOR SOME REASON; ADD SOME QUESTIONS AND RETEST
	
	//Questions: bald, black, last name starts with [some letter], have they won mvp, do they ever wear a headband, did they go to college, are they an all star, foreign born, all-NBA, are they primarily a guard, left-handed, black hair, doesn't shoot many threes / not outside shooter, have they been in the dunk contest, do they play in the state of california, ever won a championship, won NCAA championship, did they go to kentucky?, did they attend Duke University?, do they play on the warriors?, do they play on the cavs?  
	// Here is where we declare a whole bunch of new question objects
	
	public static ArrayList <Question> questions = new ArrayList <Question> ();
	
	public static void main (String [] args) {
		//bad question?
		Question q1 = new Question("Does he have a bald spot?");	
		q1.putAnswersInHashSet("Manu Ginobili", "Deron Williams");
		questions.add(q1);
		
		Question q2 = new Question("Is he white?");	
		q2.putAnswersInHashSet("Dirk Nowitzki", "Gordon Hayward", "Pau Gasol", "Marc Gasol", "Manu Ginobili", "Tony Parker");
		questions.add(q2);
		
		//Question q3 = new Question("Does his last name start with [?]");	
		//q3.putAnswersInHashSet("Manu Ginobili, Deron Williams, ")
		//questions.add(q3);
		
		Question q4 = new Question("Has he won MVP?");	
		q4.putAnswersInHashSet("LeBron James", "Derrick Rose", "Stephen Curry", "Kevin Durant", "Dirk Nowitzki");
		questions.add(q4);
		
		Question q5 = new Question("Does he ever wear headbands?");	
		q5.putAnswersInHashSet("LeBron James", "Jordan Clarkson", "Carmelo Anthony", "Zach Randolph", "Paul Pierce");
		questions.add(q5);
		
		Question q6 = new Question("Did he not go to college?");	
		q6.putAnswersInHashSet("Dwight Howard", "JR Smith", "LeBron James", "Emmanuel Mudiay", "Thon Maker", "Tyson Chandler", "Shaun Livingston", "Al Jefferson", "Josh Smith", "Monta Ellis", "Louis Williams", "Amir Johnson");
		questions.add(q6);
		
		Question q7 = new Question("Has he been an All-Star?");	
		q7.putAnswersInHashSet("DeMar Derozan", "Kyle Lowry", "LeBron James", "Dwight Howard", "Dirk Nowitzki", "Russell Westbrook", "Kevin Durant", "Stephen Curry", "Klay Thompson", "Draymond Green");
		questions.add(q7);
		
		Question q8 = new Question("Was he born outside the U.S.?");	
		q8.putAnswersInHashSet("Dirk Nowitzki", "Tony Parker", "Emmanuel Mudiay", "Luol Deng", "Thon Maker");
		questions.add(q8);
		
		Question q9 = new Question("Has he been All-NBA?");	
		q9.putAnswersInHashSet("LeBron James", "Rusell Westbrook", "Dwight Howard", "Stephen Curry", "Chris Paul", "Blake Griffin", "Deandre Jordan");
		questions.add(q9);
		
		Question q10 = new Question("Is he primarily a guard?");	
		q10.putAnswersInHashSet("DeMar Derozan", "Kyle Lowry", "Chris Paul", "JJ Redick", "Kyrie Irving", "Jordan Clarkson");
		questions.add(q10);
		
		Question q11 = new Question("Is he left-handed?");	
		q11.putAnswersInHashSet("James Harden", "Manu Ginobili", "Greg Monroe", "Josh Smith", "Zach Randolph");
		questions.add(q11);
		
		Question q12 = new Question("Does he have non-black hair?");	
		q12.putAnswersInHashSet("Dirk Nowitzki", "Pau Gasol", "Marc Gasol", "Gordon Hayward", "Kyle Singler");
		questions.add(q12);
		
		//bad question?
		Question q13 = new Question("Is he not primarily an outside shooter?");	
		q13.putAnswersInHashSet("Dwight Howard", "Deandre Jordan", "Blake Griffin", "Julius Randle", "Marcin Gortat"); 
		questions.add(q13);
		
		Question q14 = new Question("Has he been in the dunk contest?");	
		q14.putAnswersInHashSet("Aaron Gordon", "Zach Lavine", "Dwight Howard", "Nate Robinson", "Derrick Jones Jr", "Will Barton", "Andre Drummond");
		questions.add(q14);
		
		Question q15 = new Question("Do they play on a team in California?");	
		q15.putAnswersInHashSet("Stephen Curry", "Klay Thompson", "Draymond Green", "Kevin Durant", "D'Angelo Russel", "Julius Randle", "Jordan Clarkson", "Chris Paul", "Blake Griffin", "Deandre Jordan");
		questions.add(q15);
		
		Question q16 = new Question("Has he won a championship?");	
		q16.putAnswersInHashSet("Kyrie Irving", "LeBron James", "Stephen Curry", "Klay Thompson", "Draymond Green", "Shaun Livingston", "Andre Iguodala", "Kawhi Leonard", "Tony Parker", "Manu Ginobili", "Dwayne Wade");
		questions.add(q16);
		
		Question q17 = new Question("Has he won an NCAA championship?");	
		q17.putAnswersInHashSet("Carmelo Anthony", "Raymond Felton", "Al Horford", "Corey Brewer", "Mario Chalmers", "Brandon Rush", "Darrell Arthur", "Ty Lawson", "Danny Green", "Kemba Walker", "Anthony Davis");
		questions.add(q17);
		
		Question q18 = new Question("Did he go to Kentucky?");	
		q18.putAnswersInHashSet("Anthony Davis", "Eric Bledsoe", "Devin Booker", "DeMarcus Cousins", "Brandon Knight", "John Wall", "Tyler Ulis");
		questions.add(q18);
		
		Question q19 = new Question("Did he go to Duke?");	
		q19.putAnswersInHashSet("Kyrie Irving", "Brandon Ingram", "Justise Winslow", "Jahlil Okafor", "Rodney Hood");
		questions.add(q19);
		
		Question q20 = new Question("Does he play on the Warriors?");	
		q20.putAnswersInHashSet("Stephen Curry", "Draymond Green", "Klay Thompson", "Kevin Durant", "Andre Iguodala");
		questions.add(q20);
		
		Question q21 = new Question("Does he play on the Cavaliers?");	
		q21.putAnswersInHashSet("LeBron James", "Kyrie Irving", "Kevin Love", "Tristan Thompson", "Deandre Liggins");
		questions.add(q21);
		
		Question q22 = new Question("Does he play on the Spurs?");	
		q21.putAnswersInHashSet("Kawhi Leonard", "Tony Parker", "Patty Mills", "Manu Ginobili", "Danny Green");
		questions.add(q22);
		
		Question q23 = new Question("Does he play on the Celtics?");	
		q21.putAnswersInHashSet("Isaiah Thomas", "Amir Johnson", "Marcus Smart");
		questions.add(q23);
		
		OriginalData.serializeAndWriteToFile("questionsList.ser");
		
	}
	
	public static void serializeAndWriteToFile(String fileOutName) {
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
	
	
	
//	
//	public void writeToFile(ArrayList <Question> questions)
//	{
//		
//	}
}
