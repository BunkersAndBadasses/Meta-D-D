package guis;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;

import core.character;



/*
 * TODO: 
 * 
 * fix modifier logic (not rounding up)
 * launch wizards (add custom)
 * add search pop-up ("Coming soon!")
 * check feat prerequisites
 * save items
 * set hp
 * set size
 * monk - add wis to ac
 * 
 * make it so only one instance of char wiz can be open at one time
 */

/*
 * iteration 2: 
 * 
 * second class
 * searching
 * back button
 * starting at level > 1
 * adding custom skills
 * saving character image
 */

public class CharacterWizard {

	int pageNum = -1;

	private Device dev;
	private Display display;
	private Shell shell;
	private StackLayout wizLayout;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	public int wizPageNum = -1;
	public boolean cancel = false;
	public boolean[] wizPageCreated = { false, false, false, false,
		false, false, false, false, false, false };
	
	private Composite wizPanel;

	private ArrayList<Composite> wizPages;

	public ArrayList<Object> wizs = new ArrayList<Object>();

	private character character;
	private CharacterWizard cw = this;

	public int[] baseAbilityScores = new int[6];

	public CharacterWizard(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Character");
		shell.setSize(WIDTH, HEIGHT);
		character = new character();
		wizPages = new ArrayList<Composite>();

		createPageContent();

		run();
	}

	public void run() {
		center(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void center(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	private void createPageContent() {

		// the first thing that shows up is homePanel, which has a layout that
		// includes the home, wizPanel, manualPanel, and randomPanel pages
		// it rests on the home page
		// 'panel' in the name implies it holds a collection of pages
		// clicking on 'interactive character wizard' button changes the top
		// control of homePanel to wizPanel. the top control of wizPanel is
		// wiz1, which is the first page of the character wizard. clicking
		// back/next only changes the top control of wizPanel.
		// clicking cancel changes the top control of homePanel back to home
		// and resets the character wizard

		//////////////////// HOME PANEL SETUP ////////////////////////////

		final Composite homePanel = new Composite(shell, SWT.NONE);
		homePanel.setBounds(0, 0, WIDTH, HEIGHT);
		final StackLayout homeLayout = new StackLayout();
		homePanel.setLayout(homeLayout);

		//////////////////// HOME SCREEN SETUP ////////////////////////////

		// this screen is what is first seen when the window opens. 
		// it contains the buttons that link to the character wizard, the manual
		// character entering, and the random character generation
		final Composite home = new Composite(homePanel, SWT.NONE);
		home.setBounds(0, 0, WIDTH, HEIGHT);

		Label homeLabel = new Label(home, SWT.NONE);
		homeLabel.setText("Let's create a character!");
		Font font1 = new Font(homeLabel.getDisplay(), new FontData("Arial", 24,
				SWT.BOLD));
		homeLabel.setFont(font1);
		homeLabel.setBounds(WIDTH / 2 - 180, 40, 100, 100);
		homeLabel.pack();

		Label homeLabel2 = new Label(home, SWT.NONE);
		homeLabel2.setText("\nChoose a method:");
		Font font2 = new Font(homeLabel.getDisplay(), new FontData("Arial", 18,
				SWT.BOLD));
		homeLabel2.setFont(font2);
		homeLabel2.setBounds(WIDTH / 2 - 100, 65, 100, 100);
		homeLabel2.pack();

		Button wizardButton = new Button(home, SWT.PUSH);
		wizardButton.setText("Interactive\n Character Wizard");
		wizardButton.setFont(font2);
		wizardButton.setBounds(WIDTH / 2 - 150, 150, 300, 150);

		Button manualButton = new Button(home, SWT.PUSH);
		manualButton.setText("Manual");
		Font font3 = new Font(homeLabel.getDisplay(), new FontData("Arial", 18,
				SWT.NONE));
		manualButton.setFont(font3);
		manualButton.setBounds(WIDTH / 2 - 150, 310, 145, 75);

		Button randomButton = new Button(home, SWT.PUSH);
		randomButton.setText("Random");
		randomButton.setFont(font3);
		randomButton.setBounds(WIDTH / 2 + 5, 310, 145, 75);

		// set home as the first screen viewed when new character window  is launched
		homeLayout.topControl = home;


		// ///////////////// WIZARD PANEL SETUP ///////////////////////////

		wizPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, HEIGHT);
		wizPanel.setBackground(new Color(dev, 255, 0, 0));
		wizLayout = new StackLayout();
		wizPanel.setLayout(wizLayout);


		// ///////////////// MANUAL PANEL SETUP ///////////////////////////

		final Composite manualPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, (int) (HEIGHT * (.75)));
		final StackLayout manualLayout = new StackLayout();
		manualPanel.setLayout(manualLayout);
		final Composite manualPage1 = new Composite(manualPanel, SWT.NONE);
		manualPage1.setSize(manualPanel.getSize());
		manualLayout.topControl = manualPage1;
		manualPanel.layout();
		Label csManual = new Label(manualPage1, SWT.BOLD);
		csManual.setLocation(WIDTH/2-50, HEIGHT/2);
		csManual.setText("Coming Soon!");
		csManual.pack();
		createCancelButton(manualPage1, home, homePanel, homeLayout);


		// ////////////////// RANDOM PANEL SETUP //////////////////////////

		final Composite randomPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, (int) (HEIGHT * (.75)));
		final StackLayout randomLayout = new StackLayout();
		randomPanel.setLayout(randomLayout);
		final Composite randomPage1 = new Composite(randomPanel, SWT.NONE);
		randomPage1.setSize(manualPanel.getSize());
		randomLayout.topControl = randomPage1;
		randomPanel.layout();
		Label csRandom = new Label(randomPage1, SWT.BOLD);
		csRandom.setLocation(WIDTH/2-50, HEIGHT/2);
		csRandom.setText("Coming Soon!");
		csRandom.pack();
		createCancelButton(randomPage1, home, homePanel, homeLayout);


		// ////////////////// HOME BUTTON LISTENERS ///////////////////////
		
		wizardButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// create the first page (creates next pages at runtime)
				instantiateWizPages();
				Wiz1 wiz1 = new Wiz1(cw, dev, WIDTH, HEIGHT, wizPanel, home,
						homePanel, wizLayout, homeLayout, wizPages);
				wizs.add(wiz1);
				wizLayout.topControl = wizPages.get(0);
				wizPanel.layout();
				homeLayout.topControl = wizPanel;
				homePanel.layout();
			}
		});
		manualButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = manualPanel;
				homePanel.layout();
			}
		});
		randomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = randomPanel;
				homePanel.layout();
			}
		});

	}


	/**
	 * creates a next button on composite c in the bottom right corner.
	 * this does NOT set the listener! (each one is different, that is set 
	 * after this method is called)
	 * @param c
	 * @return
	 */
	public Button createNextButton(Composite c) {
		Button nextButton = new Button(c, SWT.PUSH);
		nextButton.setText("Next");
		nextButton.setBounds(WIDTH - 117, HEIGHT - 90, 100, 50);
		return nextButton;
	}

	/**
	 * creates a back button on composite c in the bottom right corner.
	 * also sets the listener for the created button that changes the top 
	 * control page of the layout of the panel to be the previous page
	 * @param c
	 * @param panel
	 * @param layout
	 * @return
	 */
	public Button createBackButton(Composite c, final Composite panel,
			final StackLayout layout) {
		Button backButton = new Button(c, SWT.PUSH);
		backButton.setText("Back");
		backButton.setBounds(WIDTH - 220, HEIGHT - 90, 100, 50);
		backButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (wizPageNum > 0)
					wizPageNum--;
				layout.topControl = wizPages.get(wizPageNum);
				panel.layout();
			}
		});
		return backButton;
	}

	/**
	 * creates a cancel button on composite c in bottom left corner.
	 * also sets the listener for the created button that changes the homePanel
	 * top control to be home and resets the wizard page counter wizPageNum
	 * @param c
	 * @param home
	 * @param panel
	 * @param layout
	 * @return
	 */
	public Button createCancelButton(Composite c, final Composite home,
			final Composite panel, final StackLayout layout) {
		Button cancelButton = new Button(c, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setBounds(10, HEIGHT - 90, 100, 50);
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				cancel = false;
				
				// create shell
				final Shell areYouSureShell = new Shell(display);
				areYouSureShell.setText("Cancel");
				GridLayout gridLayout = new GridLayout(2, true);
				areYouSureShell.setLayout(gridLayout);

				// label - Are you sure? 
				GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				gd1.horizontalSpan = 2;
				Label areYouSure = new Label(areYouSureShell, SWT.WRAP);
				areYouSure.setText("Are you sure you want to cancel?");
				areYouSure.setLayoutData(gd1);
				areYouSure.pack();
				
				// yes button
				Button yes = new Button(areYouSureShell, SWT.PUSH);
				yes.setText("Yes, Cancel");
				yes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				yes.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = true;
						areYouSureShell.dispose();
					}
				});
				yes.pack();

				// no button
				Button no = new Button(areYouSureShell, SWT.PUSH);
				no.setText("No, Don't Cancel");
				no.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				no.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = false;
						areYouSureShell.dispose();
					}
				});
				no.pack();

				// open shell
				areYouSureShell.pack();
				center(areYouSureShell);
				areYouSureShell.open();
				
				// check if disposed
				while (!areYouSureShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				
				// if user clicks yes, return to new character home
				if (cancel) {
					wizPageNum = -1;
					layout.topControl = home;
					panel.layout();
				}
			}
		});
		return cancelButton;
	}

	public character getCharacter() {
		return character;
	}
	
	private void instantiateWizPages() {
		// initialize all pages
		wizPages = new ArrayList<Composite>();
		final Composite wiz1 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz1);
		final Composite wiz2 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz2);
		final Composite wiz3 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz3);
		final Composite wiz4 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz4);
		final Composite wiz5 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz5);
		final Composite wiz6 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz6);
		final Composite wiz7 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz7);
		final Composite wiz8 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz8);
		final Composite wiz9 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz9);
		final Composite wiz10 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz10);
	}
	
	public CharacterWizard getThis() { return cw; }
	
	public void disposeShell() { shell.dispose(); }
	
	public int[] getBaseAbilityScores() { return baseAbilityScores; }
	public void setBaseAbilityScores(int[] a) { baseAbilityScores = a; }
	
	public void reset() {
		character = new character();
		for (int i = 0; i < wizPageCreated.length; i++) {
			wizPageCreated[i] = false;
		}
		wizs = new ArrayList<Object>();
		for (int i = 0; i < wizPages.size(); i++)
			wizPages.get(i).dispose();
	}
}
