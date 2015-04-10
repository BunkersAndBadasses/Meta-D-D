package guis;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import entity.*;
import core.CharItem;
import core.CharSkill;
import core.character;

public class Wiz10 {

	private static Composite wiz10;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private int wizPagesSize;

	public Wiz10(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz10 = wizPages.get(9);
		Wiz10.dev = dev;
		Wiz10.WIDTH = WIDTH;
		Wiz10.HEIGHT = HEIGHT;
		Wiz10.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}
	
	private void createPageContent() {
		Label wiz10Label = new Label(wiz10, SWT.NONE);
		wiz10Label.setText("Done!");
		wiz10Label.pack();

		Button wiz10SaveButton = new Button(wiz10, SWT.PUSH);
		wiz10SaveButton.setText("Save");
		wiz10SaveButton.setBounds(WIDTH-117,HEIGHT-90, 100, 50);
		wiz10SaveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// save
				
//				private String name;
//				private int level = 1;
//				private int exp = 0;
//				private ClassEntity charClass;		// change to type Class once refs are added
//				private RaceEntity charRace;				// ^^ ditto for Race
//				private ClassEntity charSecClass;
//				private String alignment;
//				private String deity;
//				private int size; // TODO int? string?
//				private String age; 
//				private String gender;
//				private String height;
//				private String weight;
//				private String eyes;
//				private String hair;
//				private String skin;
//				private String[] appearance = {eyes, hair, skin};
//				private String description;
//				// "STR", "DEX", "CON", "INT", "WIS", "CHA"
//				private int[] abilityScores = new int[6];
//				private int hp; // hitpoints
//				private int remainingHP;
//				private ArrayList<CharSkill> skillsList;
//				private String languages;
//				private int gold;
//				private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
//				private ArrayList<AbilityEntity> abilities = new ArrayList<AbilityEntity>();
//				private ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
//				private ArrayList<SpellEntity> prepSpells = new ArrayList<SpellEntity>();
//				private ArrayList<CharItem> items = new ArrayList<CharItem>();
//				private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
//				private ArrayList<ArmorEntity> armors = new ArrayList<ArmorEntity>();
//				private String notes;
//				
				try {

					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

					// root elements
					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement("character.getName()");
					doc.appendChild(rootElement);

					
						// Character
						Element Character = doc.createElement("CHARACTER");
						rootElement.appendChild(Character);

						// Character name
						Element Name = doc.createElement("NAME");
						Name.appendChild(doc.createTextNode(character.getName()));
						Character.appendChild(Name);

						// Level
						Element Level = doc.createElement("LEVEL");
						Level.appendChild(doc.createTextNode(
								Integer.toString(character.getLevel())));
						Character.appendChild(Level);

						// Exp
						Element Exp = doc.createElement("EXP");
						Exp.appendChild(doc.createTextNode(
								Integer.toString(character.getExp())));
						Character.appendChild(Exp);

						// Class
						Element Class = doc.createElement("CLASS");
						Class.appendChild(doc.createTextNode(character.getCharClass().getName()));
						Character.appendChild(Class);
						
						// Race
						Element Race = doc.createElement("RACE");
						Race.appendChild(doc.createTextNode(character.getCharClass().getName()));
						Character.appendChild(Race);		

					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("favRolls/" + fileName + ".xml"));

					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);

					transformer.transform(source, result);

					System.out.println("File saved!");

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (TransformerException tfe) {
					tfe.printStackTrace();
				}
				return;
				
			}
		});

		Button wiz10BackButton = CharacterWizard.createBackButton(wiz10, panel, layout);
		Button wiz10CancelButton = CharacterWizard.createCancelButton(wiz10, home, homePanel, homeLayout);
		wiz10CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}
	
	public Composite getWiz10() { return wiz10; }
	
	public static void cancelClear() {
		CharacterWizard.reset();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		Wiz4.cancelClear();
		Wiz5.cancelClear();
		Wiz6.cancelClear();
		Wiz7.cancelClear();
		Wiz8.cancelClear();
		Wiz9.cancelClear();
	}
}
