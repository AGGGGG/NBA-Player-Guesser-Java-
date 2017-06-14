import java.io.InputStream;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class Main {
	static View view;
	static Model model;
	static Controller controller;

	public static void main (String [] args) throws ClassNotFoundException {
		init();
	}
	
	public static void init() throws ClassNotFoundException {
		model = new Model();
		//View view = new View (model);
		view = new View(model);
		controller = new Controller (model, view);

		initLookAndFeel();
	}


	public static UIManager uiManager = new UIManager();
	
	public static void initLookAndFeel() {
		SynthLookAndFeel laf = new SynthLookAndFeel();

		try {
			//Class cls = Class.forName("View");
			//ClassLoader clsLoader = cls.getClassLoader();
			//InputStream i = clsLoader.getResourceAsStream("synthStyle.xml");
			//laf.load(i, cls);
			laf.load(View.class.getResourceAsStream("synthStyle.xml"), View.class);
			//laf.load(new URL("file://:synthStyle.xml"));
			UIManager.setLookAndFeel(laf);
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			SwingUtilities.updateComponentTreeUI(view.content);
			//SwingUtilities.updateComponentTreeUI(view.yesButton); ***DOESN'T WORK***
			String test = UIManager.getSystemLookAndFeelClassName();
			System.out.println(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("ERROR: couldn't get look and feel");
			e.printStackTrace();
		}
	}
}

/*
 * TODO:
 * stop execution so don't have to press stop button to end program
 * ***provide way to do maintenance on machineLearning file so don't have to wipe it every time someone makes a mistake
 */




