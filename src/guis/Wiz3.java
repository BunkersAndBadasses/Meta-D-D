package src.guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;


public class Wiz3 {

	private static Composite wiz3;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static Character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	
	private static List strList;
	private static List dexList;
	private static List conList;
	private static List intList;
	private static List wisList;
	private static List chaList;

	private int[] abilityScoresBefore;
	private int[] abilityScoresAfter = new int[6];
	private static Label errorLabel;
	private static String charRace;
	private static String charClass;
	private static String charSecClass;
	private static Label choiceLabel2;

	public Wiz3(Device dev, int WIDTH, int HEIGHT, final Character character, final Composite panel, Composite home, Composite homePanel, final StackLayout layout, final StackLayout homeLayout, final ArrayList<Composite> wizPages, int[] abilityScoresIn) {
		// initialization
		wiz3 = wizPages.get(2);
		Wiz3.dev = dev;
		Wiz3.WIDTH = WIDTH;
		Wiz3.HEIGHT = HEIGHT;
		Wiz3.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(3);
		this.wizPagesSize = wizPages.size();
		abilityScoresBefore = abilityScoresIn;
		choiceLabel2 = new Label(wiz3, SWT.NONE);
		charRace = CharacterWizard.character.getCharRace();
		charClass = CharacterWizard.character.getCharClass();
		

		createPageContent();
	}

	private void createPageContent() {
		Label wiz3Label = new Label(wiz3, SWT.NONE);
		wiz3Label.setText("Apply Ability Scores");
		wiz3Label.pack();

		// labels for user choice of character race and class
		Label choiceLabel1 = new Label(wiz3, SWT.NONE);
		choiceLabel1.setText("You chose:");
		choiceLabel1.setLocation(20, 75);
		choiceLabel1.pack();
		

		choiceLabel2.setText(charRace + " " + charClass);
		choiceLabel2.setFont(new Font(dev, new FontData("Arial", 18,
				SWT.BOLD)));
		choiceLabel2.setBounds(100, 70, 200, 50);
		choiceLabel2.pack();
		
		// labels for ability scores

		// strength label
		Label strLabel = new Label(wiz3, SWT.NONE);
		strLabel.setLocation(20, 150);
		strLabel.setText("Strength");
		strLabel.pack();
		
		// dexterity label
		Label dexLabel = new Label(wiz3, SWT.NONE);
		dexLabel.setLocation(WIDTH/6 + 20, 150);
		dexLabel.setText("Dexterity");
		dexLabel.pack();

		// constitution label
		Label conLabel = new Label(wiz3, SWT.NONE);
		conLabel.setLocation(2*(WIDTH/6) + 20, 150);
		conLabel.setText("Constitution");
		conLabel.pack();

		// intelligence label
		Label intLabel = new Label(wiz3, SWT.NONE);
		intLabel.setLocation(3*(WIDTH/6) + 20, 150);
		intLabel.setText("Intelligence");
		intLabel.pack();

		// wisdom label
		Label wisLabel = new Label(wiz3, SWT.NONE);
		wisLabel.setLocation(4*(WIDTH/6) + 20, 150);
		wisLabel.setText("Wisdom");
		wisLabel.pack();

		// charisma label
		Label chaLabel = new Label(wiz3, SWT.NONE);
		chaLabel.setLocation(5*(WIDTH/6) + 20, 150);
		chaLabel.setText("Charisma");
		chaLabel.pack();

		
		// error label
		
		errorLabel = new Label(wiz3, SWT.NONE);
		errorLabel.setLocation(WIDTH/2 - 130, 360);
		errorLabel.setForeground(new Color(dev, 255, 0, 0));
		errorLabel.setText("You must select a value for each ability!");
		errorLabel.setVisible(false);
		errorLabel.pack();

		
		// ability score lists
		
		// strength list
		strList = new List(wiz3, SWT.BORDER); 
		strList.setBounds(35, 185, 27, 140);
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			strList.add(Integer.toString(abilityScoresBefore[i]));
		}
		strList.setBackground(new Color(dev, 85,200,120));
		
		// dexterity list
		dexList = new List(wiz3, SWT.BORDER); 
		dexList.setBounds((WIDTH/6) + 37, 185, 27, 140);
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			dexList.add(Integer.toString(abilityScoresBefore[i]));
		}
		dexList.setBackground(new Color(dev, 85,200,120));

		// constitution list
		conList = new List(wiz3, SWT.BORDER); 
		conList.setBounds(2*(WIDTH/6) + 48, 185, 27, 140);
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			conList.add(Integer.toString(abilityScoresBefore[i]));
		}
		conList.setBackground(new Color(dev, 85,200,120));

		// intelligence list
		intList = new List(wiz3, SWT.BORDER); 
		intList.setBounds(3*(WIDTH/6) + 45, 185, 27, 140);
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			intList.add(Integer.toString(abilityScoresBefore[i]));
		}
		intList.setBackground(new Color(dev, 85,200,120));

		// wisdom list		
		wisList = new List(wiz3, SWT.BORDER); 
		wisList.setBounds(4*(WIDTH/6) + 35, 185, 27, 140);
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			wisList.add(Integer.toString(abilityScoresBefore[i]));
		}
		wisList.setBackground(new Color(dev, 85,200,120));

		// charisma list
		chaList = new List(wiz3, SWT.BORDER); 
		chaList.setBounds(5*(WIDTH/6) + 37, 185, 27, 140);
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			chaList.add(Integer.toString(abilityScoresBefore[i]));
		}
		chaList.setBackground(new Color(dev, 85,200,120));

		
		// selection listeners for ability score lists
		
		// strength
		strList.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {
		    	errorLabel.setVisible(false);
		    	String[] items = strList.getSelection();
		    	int index = strList.getSelectionIndex();
		    	dexList.deselect(index);
		    	conList.deselect(index);
		    	intList.deselect(index);
		    	wisList.deselect(index);
		    	chaList.deselect(index);
		    	abilityScoresAfter[0] = Integer.parseInt(items[0]);
		    	
		    }
		});
		
		// dexterity
		dexList.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {
		    	errorLabel.setVisible(false);
		    	String[] items = dexList.getSelection();
		    	int index = dexList.getSelectionIndex();
		    	strList.deselect(index);
		    	conList.deselect(index);
		    	intList.deselect(index);
		    	wisList.deselect(index);
		    	chaList.deselect(index);
		    	abilityScoresAfter[1] = Integer.parseInt(items[0]);
		    }
		});
		
		// constitution
		conList.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {
		    	errorLabel.setVisible(false);
		    	String[] items = conList.getSelection();
		    	int index = conList.getSelectionIndex();
		    	strList.deselect(index);
		    	dexList.deselect(index);
		    	intList.deselect(index);
		    	wisList.deselect(index);
		    	chaList.deselect(index);
		    	abilityScoresAfter[2] = Integer.parseInt(items[0]);
		    }
		});
		
		// intelligence
		intList.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {
		    	errorLabel.setVisible(false);
		    	String[] items = intList.getSelection();
		    	int index = intList.getSelectionIndex();
		    	strList.deselect(index);
		    	dexList.deselect(index);
		    	conList.deselect(index);
		    	wisList.deselect(index);
		    	chaList.deselect(index);
		    	abilityScoresAfter[3] = Integer.parseInt(items[0]);
		    }
		});
		
		// wisdom
		wisList.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {
		    	errorLabel.setVisible(false);
		    	String[] items = wisList.getSelection();
		    	int index = wisList.getSelectionIndex();
		    	strList.deselect(index);
		    	dexList.deselect(index);
		    	conList.deselect(index);
		    	intList.deselect(index);
		    	chaList.deselect(index);
		    	abilityScoresAfter[4] = Integer.parseInt(items[0]);
		    }
		});
		
		// charisma
		chaList.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {
		    	errorLabel.setVisible(false);
		    	String[] items = chaList.getSelection();
		    	int index = chaList.getSelectionIndex();
		    	strList.deselect(index);
		    	dexList.deselect(index);
		    	conList.deselect(index);
		    	intList.deselect(index);
		    	wisList.deselect(index);
		    	abilityScoresAfter[5] = Integer.parseInt(items[0]);
		    }
		});
		
		
		// TODO
		// add additional ability points section - level/4 rounded down extra points
		
		// next button
		Button wiz3NextButton = CharacterWizard.createNextButton(wiz3);
		wiz3NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking - make sure each list has something selected
				boolean error = false;
//				{
//					String[] selection = strList.getSelection();
//					if (selection.length == 0) {
//						errorLabel.setVisible(true);
//						error = true;
//					} else 
//						abilityScoresAfter[0] = Integer.parseInt(selection[0]);
//				}
//				{
//					String[] selection = dexList.getSelection();
//					if (selection.length == 0) {
//						errorLabel.setVisible(true);
//						error = true;
//					} else 
//						abilityScoresAfter[1] = Integer.parseInt(selection[0]);
//				}
//				{
//					String[] selection = conList.getSelection();
//					if (selection.length == 0) {
//						errorLabel.setVisible(true);
//						error = true;
//					} else 
//						abilityScoresAfter[2] = Integer.parseInt(selection[0]);
//				}
//				{
//					String[] selection = intList.getSelection();
//					if (selection.length == 0) {
//						errorLabel.setVisible(true);
//						error = true;
//					} else
//						abilityScoresAfter[3] = Integer.parseInt(selection[0]);
//				}
//				{
//					String[] selection = wisList.getSelection();
//					if (selection.length == 0) {
//						errorLabel.setVisible(true);
//						error = true;
//					} else 
//						abilityScoresAfter[4] = Integer.parseInt(selection[0]);
//				}
//				{
//					String[] selection = chaList.getSelection();
//					if (selection.length == 0) {
//						errorLabel.setVisible(true);
//						error = true;
//					} else 
//						abilityScoresAfter[5] = Integer.parseInt(selection[0]);
//				}
//				
//				if (error) return;
//				
//				// if all is good, save to character
//				CharacterWizard.character.setAbilityScores(abilityScoresAfter[0], 
//						abilityScoresAfter[1], abilityScoresAfter[2], 
//						abilityScoresAfter[3], abilityScoresAfter[4], 
//						abilityScoresAfter[5]);
				// TODO uncomment when done testing
				
				
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[3])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});


		// back button
		Button wiz3BackButton = CharacterWizard.createBackButton(wiz3, panel, layout);


		// cancel button
		Button wiz3CancelButton = CharacterWizard.createCancelButton(wiz3, home, homePanel, homeLayout);
		wiz3CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});	
	}
	
	private void createNextPage() {
		CharacterWizard.wizPageCreated[3] = true;
		new Wiz4(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages);
	}
	
	public static void updateCharRace() {
		charRace = CharacterWizard.character.getCharRace();
		choiceLabel2.setText(charRace + " " + charClass);
		choiceLabel2.pack();
	}
	
	public static void updateCharClass() {
		charClass = CharacterWizard.character.getCharClass();
		choiceLabel2.setText(charRace + " " + charClass);
		choiceLabel2.pack();
	}
	
	public static void updateCharSecClass() {
		charSecClass = CharacterWizard.character.getCharSecClass();
		if (charSecClass == null)
			choiceLabel2.setText(charRace + " " + charClass);
		else if (charSecClass.equals(""))
			choiceLabel2.setText(charRace + " " + charClass);
		else
			choiceLabel2.setText(charRace + " " + charClass + "-" + charSecClass);
		choiceLabel2.pack();
	}

	public Composite getWiz3() { return wiz3; }

	public static void cancelClear() {
		CharacterWizard.character = new Character();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		errorLabel.setVisible(false);
		strList.deselectAll();
		dexList.deselectAll();
		conList.deselectAll();
		intList.deselectAll();
		wisList.deselectAll();
		chaList.deselectAll();
	
	}

}