package src.guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;


public class Wiz9{

	private static Composite wiz9;
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

	public Wiz9(Device dev, int WIDTH, int HEIGHT, final Character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz9 = wizPages.get(8);
		Wiz9.dev = dev;
		Wiz9.WIDTH = WIDTH;
		Wiz9.HEIGHT = HEIGHT;
		Wiz9.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(9);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}
	
	private void createPageContent() {
		Label wiz9Label = new Label(wiz9, SWT.NONE);
		wiz9Label.setText("Select Known Spells");
		wiz9Label.pack();

		Button wiz9NextButton = CharacterWizard.createNextButton(wiz9);
		wiz9NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[9])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		Button wiz9BackButton = CharacterWizard.createBackButton(wiz9, panel, layout);
		Button wiz9CancelButton = CharacterWizard.createCancelButton(wiz9, home, homePanel, homeLayout);
		wiz9CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}
	
	private void createNextPage() {
		CharacterWizard.wizPageCreated[9] = true;
		new Wiz10(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages);
	}
	
	public Composite getWiz9() { return wiz9; }
	
	public static void cancelClear() {
		CharacterWizard.character = new Character();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		Wiz4.cancelClear();
		Wiz5.cancelClear();
		Wiz6.cancelClear();
		Wiz7.cancelClear();
		Wiz8.cancelClear();
	}
}