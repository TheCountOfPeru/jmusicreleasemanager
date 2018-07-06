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
import javax.swing.JTextPane;

public class guiframe extends JFrame{
	private JTextField artistfield;
	private JTextField releasenamefield;
	private JTextField urlfield;
	private JComboBox<?> comboBoxReleaseType;
	private sqlfunctions mydatabase;
	private JDatePicker datepicker;
	private JTextField datefield;
	
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
		
		JPanel searchdbpanel = new JPanel();
		tabbedPane.addTab("Search the database", null, searchdbpanel, null);
		GridBagLayout gbl_searchdbpanel = new GridBagLayout();
		gbl_searchdbpanel.columnWidths = new int[]{0};
		gbl_searchdbpanel.rowHeights = new int[]{0};
		gbl_searchdbpanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_searchdbpanel.rowWeights = new double[]{Double.MIN_VALUE};
		searchdbpanel.setLayout(gbl_searchdbpanel);
		
		JPanel addtodbpanel = new JPanel();
		tabbedPane.addTab("Add to the database", null, addtodbpanel, null);
		GridBagLayout gbl_addtodbpanel = new GridBagLayout();
		gbl_addtodbpanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_addtodbpanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_addtodbpanel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_addtodbpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		addtodbpanel.setLayout(gbl_addtodbpanel);
		
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				artistfield.setText("");
				releasenamefield.setText("");
				comboBoxReleaseType.setSelectedIndex(-1);
				urlfield.setText("");
				//reset the date box??
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
		comboBoxReleaseType.setModel(new DefaultComboBoxModel(new String[] {"album", "single", "ep", "compilation", "minialbum"}));
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
		
		JLabel lblUrl = new JLabel("URL");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 5;
		addtodbpanel.add(lblUrl, gbc_lblUrl);
		
		urlfield = new JTextField();
		GridBagConstraints gbc_urlfield = new GridBagConstraints();
		gbc_urlfield.gridwidth = 2;
		gbc_urlfield.insets = new Insets(0, 0, 5, 0);
		gbc_urlfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_urlfield.gridx = 1;
		gbc_urlfield.gridy = 5;
		addtodbpanel.add(urlfield, gbc_urlfield);
		urlfield.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridwidth = 2;
		gbc_textPane.gridheight = 5;
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 6;
		addtodbpanel.add(textPane, gbc_textPane);
		
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
