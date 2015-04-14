/*
 * CHOOSE FEATS
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import core.character;
import core.Main;
import entity.DNDEntity;
import entity.FeatEntity;

public class Wiz6 {

	private Composite wiz6;
	private CharacterWizard cw;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	private int numFeats;
	private String charClass;
	private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
	private ArrayList<FeatEntity> charFeats = new ArrayList<FeatEntity>();
	List charFeatsList;
	
	private Label numFeatsLabel;

	public Wiz6(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz6 = wizPages.get(5);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(6);
		this.wizPagesSize = wizPages.size();
		
		// get feats from references 
		Collection<DNDEntity> featsCol =  Main.gameState.feats.values();
		Iterator<DNDEntity> itr = featsCol.iterator();
		while (itr.hasNext()) {
			feats.add((FeatEntity) itr.next());
		}
		
		createPageContent();
		charClass = character.getCharClass().getName();
	}

	private void createPageContent() {
		Label wiz6Label = new Label(wiz6, SWT.NONE);
		wiz6Label.setText("Choose Feats");
		wiz6Label.pack();
		
		// "number of feats remaining: " label
		Label featsLabel = new Label(wiz6, SWT.NONE);
		featsLabel.setLocation(240,30);
		featsLabel.setText("Number of Feats Remaining:");
		featsLabel.pack();

		// number of remaining feats
		numFeats = 1;
		if (cw.getCharacter().getCharRace().equals("Human"))
			numFeats += 1;
		
		// number of remaining feats label
		numFeatsLabel = new Label(wiz6, SWT.NONE);
		numFeatsLabel.setLocation(435, 30);
		numFeatsLabel.setText(Integer.toString(numFeats));
		numFeatsLabel.pack();
		
		// grid layout for both available and selected feat lists
		FillLayout featLayout = new FillLayout();
		
		// create scrollable list of feats
		final ScrolledComposite featScreenScroll = new ScrolledComposite(wiz6, SWT.V_SCROLL | SWT.BORDER);
		featScreenScroll.setBounds(10, 110, WIDTH/2 - 65, HEIGHT - 210);
	    featScreenScroll.setExpandHorizontal(true);
	    featScreenScroll.setExpandVertical(true);
	    featScreenScroll.setMinWidth(WIDTH);
		final Composite featListScreen = new Composite(featScreenScroll, SWT.NONE);
		featScreenScroll.setContent(featListScreen);
		featListScreen.setSize(featListScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		featListScreen.setLayout(featLayout);
				
		// TODO scroll not working, okay because for now, only 1-2 feats can be added anyways
		// create scrollable list of selected feats
		final ScrolledComposite charFeatScreenScroll = new ScrolledComposite(wiz6, SWT.V_SCROLL | SWT.BORDER);
		charFeatScreenScroll.setBounds(WIDTH/2 + 55, 110, WIDTH/2 - 75, HEIGHT - 210);
	    charFeatScreenScroll.setExpandHorizontal(true);
	    charFeatScreenScroll.setExpandVertical(true);
	    charFeatScreenScroll.setMinWidth(WIDTH);
		final Composite charFeatScreen = new Composite (charFeatScreenScroll, SWT.BORDER);
		charFeatScreenScroll.setContent(charFeatScreen);
		charFeatScreen.setSize(charFeatScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		charFeatScreen.setLayout(featLayout);
		
		// available feats list
		List featsList = new List(featListScreen, SWT.NONE);
		for (int i = 0; i < feats.size(); i++) {
			featsList.add(feats.get(i).getName());
		}
		featsList.pack();
		featScreenScroll.setMinHeight(featsList.getBounds().height);
	    	
		// selected feats list
		charFeatsList = new List(charFeatScreen, SWT.NONE);
		for (int i = 0; i < charFeats.size(); i++)
			charFeatsList.add(charFeats.get(i).getName());
		charFeatsList.pack();
		
		// error message
		Label errorMsg = new Label(wiz6, SWT.NONE);
		errorMsg.setLocation(WIDTH/2 - 150, HEIGHT - 75);
		errorMsg.setForeground(new Color(dev, 255, 0, 0));
		errorMsg.setText("You cannot remove a class bonus feat!");
		errorMsg.setVisible(false);
		errorMsg.pack();                                                              
		
		// add feat button

		Button addButton = new Button(wiz6, SWT.PUSH);
		addButton.setText("Add >");
		addButton.setLocation(WIDTH/2 - 25, HEIGHT/2 - 50);
		addButton.pack();
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				boolean error = false;
				if (numFeats == 0)
					error = true;
				int index = featsList.getSelectionIndex();
				if (index == -1)
					error = true;
				else {
					for(int i = 0; i < charFeats.size(); i++) {
						if (charFeats.get(i).getName().equals(featsList.getItem(index)))
							error = true;
					}
				}
				if (error)
					return;
				// TODO check prerequisites
				charFeatsList.add(featsList.getItem(index));
				charFeats.add(feats.get(index));
				numFeats--;
				numFeatsLabel.setText(Integer.toString(numFeats));
				numFeatsLabel.setBackground(null);
				numFeatsLabel.pack();
				charFeatsList.pack();
				charFeatScreenScroll.setMinHeight(charFeatsList.getBounds().height);
				charFeatScreen.layout();
				charFeatScreenScroll.layout();
			}
		});
		
		// remove feat button
		Button removeButton = new Button(wiz6, SWT.PUSH);
		removeButton.setText("< Remove");
		removeButton.setLocation(WIDTH/2 - 38, HEIGHT/2);
		removeButton.pack();
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				if (charFeats.isEmpty())
					return;
				int index = charFeatsList.getSelectionIndex();
				if (index == -1)
					return;
				if ((charClass.equalsIgnoreCase("Fighter")
						| charClass.equalsIgnoreCase("Monk") 
						| charClass.equalsIgnoreCase("Ranger")
						| charClass.equalsIgnoreCase("Wizard")) 
						&& charFeatsList.getSelectionIndex() == 0) {
					errorMsg.setVisible(true);
					return;
				}
				charFeatsList.remove(index);
				charFeats.remove(index);
				numFeats++;
				numFeatsLabel.setText(Integer.toString(numFeats));
				numFeatsLabel.setBackground(null);
				numFeatsLabel.pack();
				charFeatsList.pack();
				charFeatScreenScroll.setMinHeight(charFeatsList.getBounds().height);
				charFeatScreen.layout();
				charFeatScreenScroll.layout();
			}
		});
		
		featListScreen.pack();
		charFeatScreen.pack();

		Button wiz6NextButton = cw.createNextButton(wiz6);
		wiz6NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking
				if (numFeats > 0) {
					numFeatsLabel.setBackground(new Color(dev, 255, 100, 100));
					return;
				}
				
				// if all is good, save to character
				for (int i = 0; i < charFeats.size(); i++)
					character.addFeat(charFeats.get(i));
				
				// switch to next page
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[6])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});
		
		//Button wiz6BackButton = cw.createBackButton(wiz5, panel, layout);
		Button wiz6CancelButton = cw.createCancelButton(wiz6, home, homePanel, homeLayout);
		wiz6CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}
	
	void createBonusPopUp() {
		// get lists of bonus feats
		ArrayList<FeatEntity> bonusFeats = new ArrayList<FeatEntity>();
		if (charClass.equalsIgnoreCase("Fighter")){
			for (int i = 0; i < feats.size(); i++){
				if (feats.get(i).getFighterBonus() != null)
					bonusFeats.add(feats.get(i));
			}
		} else if (charClass.equalsIgnoreCase("Monk")){
			bonusFeats.add((FeatEntity)Main.gameState.feats.get("Improved Grapple"));
			bonusFeats.add((FeatEntity)Main.gameState.feats.get("Stunning Fist"));
		} else if (charClass.equalsIgnoreCase("Ranger")){
			charFeats.add(0, (FeatEntity)Main.gameState.feats.get("Track"));
			charFeatsList.add(charFeats.get(0).getName());
			return;
		} else if (charClass.equalsIgnoreCase("Wizard")){
			charFeats.add(0, (FeatEntity)Main.gameState.feats.get("Scribe Scroll"));
			charFeatsList.add(charFeats.get(0).getName());
			return;
		} else
			return;
		
		// create shell
		Display display = wiz6.getDisplay();
		final Shell bonusFeatShell = new Shell(display);
		bonusFeatShell.setText("Select Bonus Feat");
		GridLayout gridLayout = new GridLayout(2, true);
		bonusFeatShell.setLayout(gridLayout);
		bonusFeatShell.addListener(SWT.Close, new Listener() {
	        public void handleEvent(Event event) {
	            event.doit = false;
	        	return;
	        }
	    });

		// label - select a bonus feat
		Label selectBonusFeat = new Label(bonusFeatShell, SWT.WRAP);
		selectBonusFeat.setText("Select A Bonus Feat");
		GridData selectGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		selectGD.horizontalSpan = 2;
		selectBonusFeat.setLayoutData(selectGD);
		selectBonusFeat.pack();
		
		// drop down menu containing bonus feat options
		CCombo bonusFeatCombo = new CCombo(bonusFeatShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < bonusFeats.size(); i++)
			bonusFeatCombo.add(bonusFeats.get(i).getName());
		GridData featsGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		featsGD.horizontalSpan = 2;
		bonusFeatCombo.setLayoutData(featsGD);
		bonusFeatCombo.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				bonusFeatCombo.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		bonusFeatCombo.pack();
		
		// done button
		Button done = new Button(bonusFeatShell, SWT.PUSH);
		done.setText("Done");
		GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		doneGD.horizontalSpan = 2;
		done.setLayoutData(doneGD);
		done.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (bonusFeatCombo.getSelectionIndex() == -1) {
					bonusFeatCombo.setBackground(new Color(dev, 255, 100, 100));
					return;
				}
				charFeats.add(0, bonusFeats.get(bonusFeatCombo.getSelectionIndex()));
				charFeatsList.add(charFeats.get(0).getName());
				bonusFeatShell.dispose();
			}
		});
		done.pack();

		// open shell
		bonusFeatShell.pack();
		cw.center(bonusFeatShell);
		bonusFeatShell.open();
		
		// check if disposed
		while (!bonusFeatShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createNextPage() {
		cw.wizPageCreated[6] = true;
		cw.wizs.add(new Wiz7(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz6() { return wiz6; }
}
