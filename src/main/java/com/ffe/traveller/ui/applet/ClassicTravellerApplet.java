package com.ffe.traveller.ui.applet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ClassicTravellerApplet extends JApplet implements ChangeListener, ActionListener {
	private String[] testPages = {"Planet Decoder", "Classic Trade"}; 
	private JTabbedPane tabs = new JTabbedPane();
	private JTextField txt = new JTextField(20);
	private JTextField name, hex, starport, planSize, planAtmosphere, hydro, pop, planGov, lawLev, techLev;
	private JLabel p1Name, p1Hex, p1Starport, p1PlanSize, p1PlanAtmos, p1Hydro, p1Pop, p1PlanGov, p1LawLev, p1TechLev, fill1, 
			fill2, fill3, fill4, fill5, zoned;
	private ButtonGroup zones;
	private JRadioButton amber, red, green;
	private JCheckBox navy, scout, gas;
	private JPanel planetCreate, classTrade, p1Create, p1Modify, p1View, p1ButtonLeft, p1MainPanel, p1Radio;
	private JButton random, save, saveChange, createNew;

	public void init() {
		buildPanels();
		for (int i = 0; i < testPages.length; i++)
	    	if(i == 0){
	    		tabs.addTab(testPages[i], planetCreate);
	    	}
	    	else{
	    		tabs.addTab(testPages[i], new JButton("Tabbed pane " + i));
	    	}
    	tabs.addChangeListener(this);
	    Container cp = getContentPane();
	    cp.add(BorderLayout.SOUTH, txt);
	    cp.add(tabs);
	}
	
	/**
	 * 
	 */
	private void buildPanels() {
		//Panel builds
		planetCreate = new JPanel(new GridLayout(1, 3));
		p1Create = new JPanel(new BorderLayout());
		p1MainPanel = new JPanel(new GridLayout(16, 2));
		p1ButtonLeft = new JPanel(new GridLayout(1, 3));
		
		//set up radio buttons
		zones = new ButtonGroup();
		
		green = new JRadioButton("Green", true);
		green.setMnemonic(KeyEvent.VK_G);
		green.addActionListener(this);
		amber = new JRadioButton("Amber");
		amber.setMnemonic(KeyEvent.VK_A);
		amber.addActionListener(this);
		red = new JRadioButton("Red");
		red.setMnemonic(KeyEvent.VK_R);
		red.addActionListener(this);
		
		zones.add(green);
		zones.add(amber);
		zones.add(red);
		
		//set up buttons on bottom of left most panel
		random = new JButton("Random Planet");
		random.addActionListener(this);
		save = new JButton("Save Planet");
		save.addActionListener(this);
		createNew = new JButton("Create New");
		createNew.addActionListener(this);
		
		p1ButtonLeft.add(createNew);
		p1ButtonLeft.add(random);
		p1ButtonLeft.add(save);
		
		p1Create.add("South", p1ButtonLeft);
		
		//Set up grid on main panel
		//JCheckBoxes
		navy = new JCheckBox("Naval Base");
		scout = new JCheckBox("Scout Base");
		gas = new JCheckBox("Gas Giant");
		
		//JLabels
		p1Name = new JLabel("Name");
		p1Hex = new JLabel("Hex Location");
		p1Starport = new JLabel("Starport type");
		p1PlanSize = new JLabel("Planet Size");
		p1PlanAtmos = new JLabel("Atmosphere");
		p1Hydro = new JLabel("Hydrography");
		p1Pop = new JLabel("Population");
		p1PlanGov = new JLabel("Government");
		p1LawLev = new JLabel("Law Level");
		p1TechLev = new JLabel("Tech Level");
		zoned = new JLabel("Zones");
		fill1 = new JLabel(" ");
		fill2 = new JLabel(" ");
		fill3 = new JLabel(" ");
		fill4 = new JLabel(" ");
		fill5 = new JLabel(" ");
		
		//JTextFields
		name = new JTextField();
		hex = new JTextField();
		starport = new JTextField();
		planSize = new JTextField();
		planAtmosphere = new JTextField();
		hydro = new JTextField();
		pop = new JTextField();
		planGov = new JTextField();
		lawLev = new JTextField();
		techLev = new JTextField();
		
		//add to p1Main panel
		p1MainPanel.add(p1Name);
		p1MainPanel.add(name);
		p1MainPanel.add(p1Hex);
		p1MainPanel.add(hex);
		p1MainPanel.add(zoned);
		p1MainPanel.add(green);
		p1MainPanel.add(fill1);
		p1MainPanel.add(amber);
		p1MainPanel.add(fill2);
		p1MainPanel.add(red);
		p1MainPanel.add(p1Starport);
		p1MainPanel.add(starport);
		p1MainPanel.add(p1PlanSize);
		p1MainPanel.add(planSize);
		p1MainPanel.add(p1PlanAtmos);
		p1MainPanel.add(planAtmosphere);
		p1MainPanel.add(p1Hydro);
		p1MainPanel.add(hydro);
		p1MainPanel.add(p1Pop);
		p1MainPanel.add(pop);
		p1MainPanel.add(p1PlanGov);
		p1MainPanel.add(planGov);
		p1MainPanel.add(p1LawLev);
		p1MainPanel.add(lawLev);
		p1MainPanel.add(p1TechLev);
		p1MainPanel.add(techLev);
		p1MainPanel.add(fill3);
		p1MainPanel.add(navy);
		p1MainPanel.add(fill4);
		p1MainPanel.add(scout);
		p1MainPanel.add(fill5);
		p1MainPanel.add(gas);
		
		//add to p1Create panel
		p1Create.add("Center", p1MainPanel);
		
		//add p1Create panel to planetCreate panel
		planetCreate.add(p1Create);
		
	}

	public static void main(String[] args){
		run(new ClassicTravellerApplet(), 800, 600);
	}
	
	public static void run(JApplet trav, int width, int height){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(trav);
	    frame.setSize(width, height);
	    trav.init();
	    trav.start();
	    frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		txt.setText("" + tabs.getSelectedIndex());
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
} ///:~
