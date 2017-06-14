import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Question implements java.io.Serializable {
	private HashSet <String> qAnswers = new HashSet<String> ();
	private String q;
	
	//needed to ensure that serial version here matches stream classdesc serial version, so can serialize and deserialize Questions in questions ArrayList
	private static final long serialVersionUID = -4665416826061285872L;
	//private boolean isInitial;
//	
//	public Question (String q1, boolean i) {
//		q = q1;
//		isInitial = i;
//	}
	
	public Question (String q1) {
		q = q1;
	}
	
	public Question (String q1, HashSet<String> ans) {
		q = q1;
		qAnswers = ans;
	}
	
	//return just the actual, written question
	public String getQString() {
		return q;
	}
	
	public void setQAnswers(HashSet<String> qA) {
		qAnswers = qA;
	}
	
	public HashSet <String> getQAnswers() {
		return qAnswers;
	}
	
	public void addAnswer(String answer) {
		qAnswers.add(answer);
	}
	
	public void removeAnswer(String answer) {
		qAnswers.remove(answer);
	}
	
	public void putAnswersInHashSet (String... answers) {
		for (String a : answers) {
			qAnswers.add(a);
		}
	}
	
	public void setQString(String newQ) {
		q = newQ;
	}
	
	public void writeObjectOverride(java.io.ObjectOutputStream stream)
	//public void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(qAnswers);
        stream.writeObject(q);
    }

	public void readObjectOverride(java.io.ObjectInputStream stream)
	//public void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        qAnswers = (HashSet <String>) stream.readObject();
        q = (String) stream.readObject();
    }
	
//	public boolean getIsInitial() {
//		return isInitial;
//	}
}
