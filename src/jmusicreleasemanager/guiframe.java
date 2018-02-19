package jmusicreleasemanager;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

public class guiframe extends JFrame{
	public guiframe() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}
	
	
}
