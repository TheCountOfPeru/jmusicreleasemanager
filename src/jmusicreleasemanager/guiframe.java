package jmusicreleasemanager;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import org.jdatepicker.JDatePicker;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class guiframe extends JFrame{
	private JTextField artistfield;
	private JTextField releasenamefield;
	private JTextField urlfield;
	private JComboBox<?> comboBoxReleaseType;
	private sqlfunctions mydatabase;
	private JDatePicker newReleaseDate;
	private JDatePicker startReleaseDate;
	private JDatePicker endReleaseDate;
	private JTextField datefield;
	private JTextPane textPane;
	private JTextArea textAreaDisplayReleases;
	
	public guiframe(sqlfunctions mydatabase) {
		setResizable(false);
		
		this.setDriver(mydatabase);
		
		setTitle("JMusicReleaseManager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{434, 0};
		gridBagLayout.rowHeights = new int[]{261, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel searchReleases = new JPanel();
		tabbedPane.addTab("Search the database", null, searchReleases, null);
		GridBagLayout gbl_searchReleases = new GridBagLayout();
		gbl_searchReleases.columnWidths = new int[]{0, 0, 0, 0};
		gbl_searchReleases.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_searchReleases.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_searchReleases.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		searchReleases.setLayout(gbl_searchReleases);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaDisplayReleases.setText("");
				
				java.util.GregorianCalendar Date = (GregorianCalendar) startReleaseDate.getModel().getValue();
				Calendar calendar = (Calendar)Date;
				java.sql.Date afterdate = new java.sql.Date(calendar.getTimeInMillis());
				java.sql.Date beforedate = null; 
				if(endReleaseDate.getModel().getValue()!=null) {
					Date = (GregorianCalendar) endReleaseDate.getModel().getValue();
					calendar = (Calendar)Date;
					beforedate = new java.sql.Date(calendar.getTimeInMillis());
				}
				ResultSet rs = mydatabase.retrieveReleases(mydatabase, afterdate, beforedate);
				try {
					while(rs.next()) {
						textAreaDisplayReleases.append(rs.getString("name")+" "+rs.getString("artist")+" "+rs.getDate("date").toString()+" "+rs.getString("type")+"\n");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 0;
		searchReleases.add(btnSubmit, gbc_btnSubmit);
		
		JButton btnClearAll_1 = new JButton("Clear All");
		btnClearAll_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaDisplayReleases.setText("");
				startReleaseDate.getFormattedTextField().setText("");
				endReleaseDate.getFormattedTextField().setText("");
			}
		});
		GridBagConstraints gbc_btnClearAll_1 = new GridBagConstraints();
		gbc_btnClearAll_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearAll_1.gridx = 1;
		gbc_btnClearAll_1.gridy = 0;
		searchReleases.add(btnClearAll_1, gbc_btnClearAll_1);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileWriter out = null;
				try {
					out = new FileWriter("output.txt");
					
					java.util.GregorianCalendar Date = (GregorianCalendar) startReleaseDate.getModel().getValue();
					Calendar calendar = (Calendar)Date;
					java.sql.Date afterdate = new java.sql.Date(calendar.getTimeInMillis());
					java.sql.Date beforedate = null; 
					if(endReleaseDate.getModel().getValue()!=null) {
						Date = (GregorianCalendar) endReleaseDate.getModel().getValue();
						calendar = (Calendar)Date;
						beforedate = new java.sql.Date(calendar.getTimeInMillis());
					}
					ResultSet rs = mydatabase.retrieveReleasesLarge(mydatabase, afterdate, beforedate);
					try {
						while(rs.next()) {
							out.append("[url="+rs.getString("url")+"]"+
									rs.getString("artist")+" "+rs.getString("name")+" "+rs.getDate("date").toString()+" "+rs.getString("type")+"[/url]"+
									System.getProperty( "line.separator" ));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnPrint = new GridBagConstraints();
		gbc_btnPrint.insets = new Insets(0, 0, 5, 0);
		gbc_btnPrint.gridx = 2;
		gbc_btnPrint.gridy = 0;
		searchReleases.add(btnPrint, gbc_btnPrint);
		
		JLabel lblDatesAfter = new JLabel("Dates After");
		GridBagConstraints gbc_lblDatesAfter = new GridBagConstraints();
		gbc_lblDatesAfter.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatesAfter.gridx = 0;
		gbc_lblDatesAfter.gridy = 1;
		searchReleases.add(lblDatesAfter, gbc_lblDatesAfter);
		
		JLabel lblDatesBefore = new JLabel("Dates Before");
		GridBagConstraints gbc_lblDatesBefore = new GridBagConstraints();
		gbc_lblDatesBefore.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatesBefore.gridx = 1;
		gbc_lblDatesBefore.gridy = 1;
		searchReleases.add(lblDatesBefore, gbc_lblDatesBefore);
		
		startReleaseDate = new JDatePicker();
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		searchReleases.add(startReleaseDate, gbc_lblNewLabel);
		
		endReleaseDate = new JDatePicker();
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		searchReleases.add(endReleaseDate, gbc_lblNewLabel_1);
		
		textAreaDisplayReleases = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 3;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 3;
		searchReleases.add(textAreaDisplayReleases, gbc_textArea);
		
		JPanel addNewRelease = new JPanel();
		tabbedPane.addTab("Add to the database", null, addNewRelease, null);
		GridBagLayout gbl_addtodbpanel = new GridBagLayout();
		gbl_addtodbpanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_addtodbpanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_addtodbpanel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_addtodbpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		addNewRelease.setLayout(gbl_addtodbpanel);
		
		
		JLabel lblartist = new JLabel("Artist");
		GridBagConstraints gbc_lblartist = new GridBagConstraints();
		gbc_lblartist.insets = new Insets(0, 0, 5, 5);
		gbc_lblartist.gridx = 0;
		gbc_lblartist.gridy = 1;
		addNewRelease.add(lblartist, gbc_lblartist);
		
		artistfield = new JTextField();
		GridBagConstraints gbc_releasenamefield = new GridBagConstraints();
		gbc_releasenamefield.gridwidth = 2;
		gbc_releasenamefield.insets = new Insets(0, 0, 5, 0);
		gbc_releasenamefield.fill = GridBagConstraints.HORIZONTAL;
		gbc_releasenamefield.gridx = 1;
		gbc_releasenamefield.gridy = 1;
		addNewRelease.add(artistfield, gbc_releasenamefield);
		artistfield.setColumns(10);
		
		JLabel lblReleaseName = new JLabel("Release Name");
		GridBagConstraints gbc_lblReleaseName = new GridBagConstraints();
		gbc_lblReleaseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseName.gridx = 0;
		gbc_lblReleaseName.gridy = 2;
		addNewRelease.add(lblReleaseName, gbc_lblReleaseName);
		
		releasenamefield = new JTextField();
		GridBagConstraints gbc_releasenamefield1 = new GridBagConstraints();
		gbc_releasenamefield1.gridwidth = 2;
		gbc_releasenamefield1.insets = new Insets(0, 0, 5, 0);
		gbc_releasenamefield1.fill = GridBagConstraints.HORIZONTAL;
		gbc_releasenamefield1.gridx = 1;
		gbc_releasenamefield1.gridy = 2;
		addNewRelease.add(releasenamefield, gbc_releasenamefield1);
		releasenamefield.setColumns(10);
		
		JLabel lblreleasetype = new JLabel("Release Type");
		GridBagConstraints gbc_lblreleasetype = new GridBagConstraints();
		gbc_lblreleasetype.insets = new Insets(0, 0, 5, 5);
		gbc_lblreleasetype.gridx = 0;
		gbc_lblreleasetype.gridy = 3;
		addNewRelease.add(lblreleasetype, gbc_lblreleasetype);
		
		comboBoxReleaseType = new JComboBox();
		comboBoxReleaseType.setModel(new DefaultComboBoxModel(new String[] {"album", "single", "ep", "compilation", "minialbum"}));
		comboBoxReleaseType.setSelectedIndex(-1);
		GridBagConstraints gbc_comboBoxReleaseType = new GridBagConstraints();
		gbc_comboBoxReleaseType.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxReleaseType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxReleaseType.gridx = 1;
		gbc_comboBoxReleaseType.gridy = 3;
		addNewRelease.add(comboBoxReleaseType, gbc_comboBoxReleaseType);
		
		JLabel lblReleaseDate = new JLabel("Release Date");
		GridBagConstraints gbc_lblReleaseDate = new GridBagConstraints();
		gbc_lblReleaseDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseDate.gridx = 0;
		gbc_lblReleaseDate.gridy = 4;
		addNewRelease.add(lblReleaseDate, gbc_lblReleaseDate);
		
		newReleaseDate = new JDatePicker();
		GridBagConstraints gbc_btnNewDatePicker = new GridBagConstraints();
		gbc_btnNewDatePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewDatePicker.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewDatePicker.gridx = 1;
		gbc_btnNewDatePicker.gridy = 4;
		addNewRelease.add(newReleaseDate, gbc_btnNewDatePicker);
		
		JLabel lblUrl = new JLabel("URL");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 5;
		addNewRelease.add(lblUrl, gbc_lblUrl);
		
		urlfield = new JTextField();
		GridBagConstraints gbc_urlfield = new GridBagConstraints();
		gbc_urlfield.gridwidth = 2;
		gbc_urlfield.insets = new Insets(0, 0, 5, 0);
		gbc_urlfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_urlfield.gridx = 1;
		gbc_urlfield.gridy = 5;
		addNewRelease.add(urlfield, gbc_urlfield);
		urlfield.setColumns(10);
		
		textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridwidth = 2;
		gbc_textPane.gridheight = 5;
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 6;
		addNewRelease.add(textPane, gbc_textPane);
		
		JToggleButton tglbtnSubmit = new JToggleButton("Submit");
		tglbtnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result;
				//make sure the primary keys are included when searching
				if(artistfield.getText().equals("") || releasenamefield.getText().equals("") 
						|| comboBoxReleaseType.getSelectedIndex() == -1
						|| newReleaseDate.getFormattedTextField().getText().equals("")) {
						textPane.setText("A new release must have an artist, name, and type, and date!");
						return;
				}
				java.util.GregorianCalendar selectedDate = (GregorianCalendar) newReleaseDate.getModel().getValue();
				Calendar calendar = (Calendar)selectedDate;
				java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
				
				//java.util.Date date = java.sql.Date.valueOf("2018-03-21");
				java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
				System.out.println(sqlStartDate);
				result = mydatabase.insertData(mydatabase, artistfield.getText(), releasenamefield.getText(), (String)comboBoxReleaseType.getSelectedItem(),
						date, urlfield.getText());
				//result = mydatabase.insertData(mydatabase, "MONKEY MAJIK", "enigma", "album",
				//		sqlStartDate, "");
				if(result == 1) {
					clearInputs();
					textPane.setText("New release added to the database successfully!");
				}
				else if(result == 0) {
					textPane.setText("New release could not be added to the database! It already exists.");
				}
			}
		});
		GridBagConstraints gbc_tglbtnSubmit = new GridBagConstraints();
		gbc_tglbtnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnSubmit.gridx = 1;
		gbc_tglbtnSubmit.gridy = 0;
		addNewRelease.add(tglbtnSubmit, gbc_tglbtnSubmit);
		
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearInputs();
			}
		});
		GridBagConstraints gbc_btnClearAll = new GridBagConstraints();
		gbc_btnClearAll.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearAll.gridx = 0;
		gbc_btnClearAll.gridy = 0;
		addNewRelease.add(btnClearAll, gbc_btnClearAll);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	public sqlfunctions getDriver() {
		return mydatabase;
	}
	public void setDriver(sqlfunctions mydatabase) {
		this.mydatabase = mydatabase;
	}
	public void clearInputs() {
		artistfield.setText("");
		releasenamefield.setText("");
		comboBoxReleaseType.setSelectedIndex(-1);
		urlfield.setText("");
		textPane.setText("");
		newReleaseDate.getFormattedTextField().setText("");
	}
	
}
