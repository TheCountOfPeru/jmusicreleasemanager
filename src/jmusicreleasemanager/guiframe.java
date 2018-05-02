package jmusicreleasemanager;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import org.jdatepicker.JDatePicker;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;

public class GUIframe extends JFrame{
	private JTextField artistfield;
	private JTextField releasenamefield;
	private JTextField labelfield;
	private JTextField catalogfield;
	private JTextField urlfield;
	private JTextField musicbrainzfield;
	private JTextField discogsfield;
	private JComboBox<?> comboBoxReleaseType;
	private JComboBox<?> comboBoxEdition;
	private JComboBox<?> comboBoxEditionType;
	private sqlfunctions mydatabase;
	private JDatePicker datepicker;
	private JTextField datefield;
	
	public GUIframe(sqlfunctions mydatabase) {
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
		
		JPanel addtodbpanel = new JPanel();
		tabbedPane.addTab("Add to the database", null, addtodbpanel, null);
		GridBagLayout gbl_addtodbpanel = new GridBagLayout();
		gbl_addtodbpanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_addtodbpanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_addtodbpanel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_addtodbpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		addtodbpanel.setLayout(gbl_addtodbpanel);
		
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				artistfield.setText("");
				releasenamefield.setText("");
				labelfield.setText("");
				catalogfield.setText("");
				urlfield.setText("");
				musicbrainzfield.setText("");
				discogsfield.setText("");
				comboBoxReleaseType.setSelectedIndex(-1);
				comboBoxEdition.setSelectedItem(-1); 
				comboBoxEditionType.setSelectedItem(-1); 
				//datepicker.
			}
		});
		GridBagConstraints gbc_btnClearAll = new GridBagConstraints();
		gbc_btnClearAll.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearAll.gridx = 0;
		gbc_btnClearAll.gridy = 0;
		addtodbpanel.add(btnClearAll, gbc_btnClearAll);
		
		JToggleButton tglbtnSubmit = new JToggleButton("Submit");
		tglbtnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean exists;
				//make sure the primary keys are included
				if(artistfield.getText().equals("") || releasenamefield.getText().equals("") 
						|| comboBoxReleaseType.getSelectedIndex() == -1
						|| datepicker.getFormattedTextField().getText().equals("")) {
					JOptionPane.showMessageDialog(null,
			    		    "A new release must have an artist, name, and type, and date!",
			    		    "Error",
			    		    JOptionPane.WARNING_MESSAGE);
						return;
				}
				java.util.GregorianCalendar selectedDate = (GregorianCalendar) datepicker.getModel().getValue();
				Calendar calendar = (Calendar)selectedDate;
				java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
				System.out.println(date.toString());
				exists = mydatabase.check_dup(mydatabase, artistfield.getText(), 
						releasenamefield.getText(), (String)comboBoxReleaseType.getSelectedItem(), 
						(String)comboBoxEdition.getSelectedItem()+(String)comboBoxEditionType.getSelectedItem(), date);
				if(exists) {
					mydatabase.insertData(mydatabase, artistfield.getText(), 
						releasenamefield.getText(), (String)comboBoxReleaseType.getSelectedItem(),
						date, urlfield.getText(),
						(String)comboBoxEdition.getSelectedItem()+(String)comboBoxEditionType.getSelectedItem(), 
						labelfield.getText(), catalogfield.getText(), musicbrainzfield.getText(), discogsfield.getText());	
				}else if(!exists){
					JOptionPane.showMessageDialog(null,
			    		    "This release already exists!",
			    		    "Error",
			    		    JOptionPane.WARNING_MESSAGE);
				}
									
			}
		});
		GridBagConstraints gbc_tglbtnSubmit = new GridBagConstraints();
		gbc_tglbtnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnSubmit.gridx = 1;
		gbc_tglbtnSubmit.gridy = 0;
		addtodbpanel.add(tglbtnSubmit, gbc_tglbtnSubmit);
		
		JLabel lblartist = new JLabel("Artist");
		GridBagConstraints gbc_lblartist = new GridBagConstraints();
		gbc_lblartist.insets = new Insets(0, 0, 5, 5);
		gbc_lblartist.gridx = 0;
		gbc_lblartist.gridy = 1;
		addtodbpanel.add(lblartist, gbc_lblartist);
		
		artistfield = new JTextField();
		GridBagConstraints gbc_releasenamefield = new GridBagConstraints();
		gbc_releasenamefield.gridwidth = 2;
		gbc_releasenamefield.insets = new Insets(0, 0, 5, 0);
		gbc_releasenamefield.fill = GridBagConstraints.HORIZONTAL;
		gbc_releasenamefield.gridx = 1;
		gbc_releasenamefield.gridy = 1;
		addtodbpanel.add(artistfield, gbc_releasenamefield);
		artistfield.setColumns(10);
		
		JLabel lblReleaseName = new JLabel("Release Name");
		GridBagConstraints gbc_lblReleaseName = new GridBagConstraints();
		gbc_lblReleaseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseName.gridx = 0;
		gbc_lblReleaseName.gridy = 2;
		addtodbpanel.add(lblReleaseName, gbc_lblReleaseName);
		
		releasenamefield = new JTextField();
		GridBagConstraints gbc_releasenamefield1 = new GridBagConstraints();
		gbc_releasenamefield1.gridwidth = 2;
		gbc_releasenamefield1.insets = new Insets(0, 0, 5, 0);
		gbc_releasenamefield1.fill = GridBagConstraints.HORIZONTAL;
		gbc_releasenamefield1.gridx = 1;
		gbc_releasenamefield1.gridy = 2;
		addtodbpanel.add(releasenamefield, gbc_releasenamefield1);
		releasenamefield.setColumns(10);
		
		JLabel lblreleasetype = new JLabel("Release Type");
		GridBagConstraints gbc_lblreleasetype = new GridBagConstraints();
		gbc_lblreleasetype.insets = new Insets(0, 0, 5, 5);
		gbc_lblreleasetype.gridx = 0;
		gbc_lblreleasetype.gridy = 3;
		addtodbpanel.add(lblreleasetype, gbc_lblreleasetype);
		
		comboBoxReleaseType = new JComboBox();
		comboBoxReleaseType.setModel(new DefaultComboBoxModel(new String[] {"Album", "Single", "EP", "Compilation"}));
		comboBoxReleaseType.setSelectedIndex(-1);
		GridBagConstraints gbc_comboBoxReleaseType = new GridBagConstraints();
		gbc_comboBoxReleaseType.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxReleaseType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxReleaseType.gridx = 1;
		gbc_comboBoxReleaseType.gridy = 3;
		addtodbpanel.add(comboBoxReleaseType, gbc_comboBoxReleaseType);
		
		JLabel lblReleaseDate = new JLabel("Release Date");
		GridBagConstraints gbc_lblReleaseDate = new GridBagConstraints();
		gbc_lblReleaseDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseDate.gridx = 0;
		gbc_lblReleaseDate.gridy = 4;
		addtodbpanel.add(lblReleaseDate, gbc_lblReleaseDate);
		
		datepicker = new JDatePicker();
		//datepicker.getDateFormat().
		GridBagConstraints gbc_btnNewDatePicker = new GridBagConstraints();
		gbc_btnNewDatePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewDatePicker.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewDatePicker.gridx = 1;
		gbc_btnNewDatePicker.gridy = 4;
		addtodbpanel.add(datepicker, gbc_btnNewDatePicker);
		
		JLabel lblEdition = new JLabel("Edition");
		GridBagConstraints gbc_lblEdition = new GridBagConstraints();
		gbc_lblEdition.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdition.gridx = 0;
		gbc_lblEdition.gridy = 5;
		addtodbpanel.add(lblEdition, gbc_lblEdition);
		
		comboBoxEdition = new JComboBox();
		comboBoxEdition.setModel(new DefaultComboBoxModel(new String[] {"Regular", "Limited"}));
		comboBoxEdition.setSelectedIndex(0);
		GridBagConstraints gbc_comboBoxEdition = new GridBagConstraints();
		gbc_comboBoxEdition.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdition.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdition.gridx = 1;
		gbc_comboBoxEdition.gridy = 5;
		addtodbpanel.add(comboBoxEdition, gbc_comboBoxEdition);
		
		comboBoxEditionType = new JComboBox();
		comboBoxEditionType.setModel(new DefaultComboBoxModel(new String[] {"Type A", "Type B", "Type C"}));
		comboBoxEditionType.setSelectedIndex(-1);
		GridBagConstraints gbc_comboBoxEditionType = new GridBagConstraints();
		gbc_comboBoxEditionType.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxEditionType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEditionType.gridx = 2;
		gbc_comboBoxEditionType.gridy = 5;
		addtodbpanel.add(comboBoxEditionType, gbc_comboBoxEditionType);
		
		JLabel lblLabel = new JLabel("Label");
		GridBagConstraints gbc_lblLabel = new GridBagConstraints();
		gbc_lblLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel.gridx = 0;
		gbc_lblLabel.gridy = 6;
		addtodbpanel.add(lblLabel, gbc_lblLabel);
		
		labelfield = new JTextField();
		GridBagConstraints gbc_labelfield = new GridBagConstraints();
		gbc_labelfield.gridwidth = 2;
		gbc_labelfield.insets = new Insets(0, 0, 5, 0);
		gbc_labelfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelfield.gridx = 1;
		gbc_labelfield.gridy = 6;
		addtodbpanel.add(labelfield, gbc_labelfield);
		labelfield.setColumns(10);
		
		JLabel lblCatalogNumber = new JLabel("Catalog Number");
		GridBagConstraints gbc_lblCatalogNumber = new GridBagConstraints();
		gbc_lblCatalogNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblCatalogNumber.gridx = 0;
		gbc_lblCatalogNumber.gridy = 7;
		addtodbpanel.add(lblCatalogNumber, gbc_lblCatalogNumber);
		
		catalogfield = new JTextField();
		GridBagConstraints gbc_catalogfield = new GridBagConstraints();
		gbc_catalogfield.gridwidth = 2;
		gbc_catalogfield.insets = new Insets(0, 0, 5, 0);
		gbc_catalogfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_catalogfield.gridx = 1;
		gbc_catalogfield.gridy = 7;
		addtodbpanel.add(catalogfield, gbc_catalogfield);
		catalogfield.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 8;
		addtodbpanel.add(lblUrl, gbc_lblUrl);
		
		urlfield = new JTextField();
		GridBagConstraints gbc_urlfield = new GridBagConstraints();
		gbc_urlfield.gridwidth = 2;
		gbc_urlfield.insets = new Insets(0, 0, 5, 0);
		gbc_urlfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_urlfield.gridx = 1;
		gbc_urlfield.gridy = 8;
		addtodbpanel.add(urlfield, gbc_urlfield);
		urlfield.setColumns(10);
		
		JLabel lblMusicbrainz = new JLabel("musicbrainz");
		GridBagConstraints gbc_lblMusicbrainz = new GridBagConstraints();
		gbc_lblMusicbrainz.insets = new Insets(0, 0, 5, 5);
		gbc_lblMusicbrainz.gridx = 0;
		gbc_lblMusicbrainz.gridy = 9;
		addtodbpanel.add(lblMusicbrainz, gbc_lblMusicbrainz);
		
		musicbrainzfield = new JTextField();
		GridBagConstraints gbc_musicbrainzfield = new GridBagConstraints();
		gbc_musicbrainzfield.gridwidth = 2;
		gbc_musicbrainzfield.insets = new Insets(0, 0, 5, 0);
		gbc_musicbrainzfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_musicbrainzfield.gridx = 1;
		gbc_musicbrainzfield.gridy = 9;
		addtodbpanel.add(musicbrainzfield, gbc_musicbrainzfield);
		musicbrainzfield.setColumns(10);
		
		JLabel lblDiscogs = new JLabel("discogs");
		GridBagConstraints gbc_lblDiscogs = new GridBagConstraints();
		gbc_lblDiscogs.insets = new Insets(0, 0, 0, 5);
		gbc_lblDiscogs.gridx = 0;
		gbc_lblDiscogs.gridy = 10;
		addtodbpanel.add(lblDiscogs, gbc_lblDiscogs);
		
		discogsfield = new JTextField();
		GridBagConstraints gbc_discogsfield = new GridBagConstraints();
		gbc_discogsfield.gridwidth = 2;
		gbc_discogsfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_discogsfield.gridx = 1;
		gbc_discogsfield.gridy = 10;
		addtodbpanel.add(discogsfield, gbc_discogsfield);
		discogsfield.setColumns(10);
		
		JPanel searchdbpanel = new JPanel();
		tabbedPane.addTab("Search the database", null, searchdbpanel, null);
		GridBagLayout gbl_searchdbpanel = new GridBagLayout();
		gbl_searchdbpanel.columnWidths = new int[]{0};
		gbl_searchdbpanel.rowHeights = new int[]{0};
		gbl_searchdbpanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_searchdbpanel.rowWeights = new double[]{Double.MIN_VALUE};
		searchdbpanel.setLayout(gbl_searchdbpanel);
		
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
	
}
