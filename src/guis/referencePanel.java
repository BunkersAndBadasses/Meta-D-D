package guis;

import java.util.ArrayList;

import javax.swing.JList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import core.character;

public class referencePanel {

	private Device dev;
	private Composite refPanel;
	private Composite view;
	private Composite list;
	private Composite info;
	private StackLayout stackLayout;

	private Text searchBar;
	private Text infoText;

	public referencePanel(Composite page) {
		refPanel = new Composite(page, SWT.NONE);
		stackLayout = new StackLayout();
		createPageContent();
		
	}
	
	public Composite getRefPanel(){ return refPanel; }

	private void createPageContent() {

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.horizontalSpacing = 3;
		layout.numColumns = 1;
		refPanel.setLayout(layout);

		searchBar = new Text(refPanel, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		searchBar.setText("Search");
		searchBar.setLayoutData(gridData);
		searchBar.addSelectionListener( new SelectionAdapter() { 
			public void widgetDefaultSelected( SelectionEvent e ) { 
				//TODO populate the search list
				//feed method in the gamestate this string
				// populate the list with results
			}
		});

		// generate new dungeon
		Button backButton = new Button(refPanel, SWT.PUSH);
		backButton.setText("Back");
		backButton.setVisible(false);
		backButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				backButton.setVisible(false);
				stackLayout.topControl = list;
			}
		});

		view = new Composite(refPanel, SWT.NONE);
		list = new Composite(view, SWT.NONE);
		info = new Composite(view, SWT.NONE);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		view.setLayoutData(gridData);
		view.setLayout(stackLayout);


		//LIST COMPOSITE
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		list.setLayout(fillLayout);

		List searchList = new List(list, SWT.V_SCROLL);
		//searchList.add("empty");
		searchList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				//TODO populate the info text box 
				backButton.setVisible(true);
				stackLayout.topControl = info;
			}

			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});

		list.layout();

		//INFO COMPOSITE
		fillLayout = new FillLayout(SWT.VERTICAL);
		info.setLayout(fillLayout);

		infoText = new Text(info, SWT.MULTI | 
				SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		infoText.setText("INFO");

		info.layout();

		stackLayout.topControl = list;
		view.layout();

	}


}