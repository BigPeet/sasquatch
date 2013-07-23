/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.results.ShowResultsDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;

import analyzer.SentimentAnalyzer;
import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.sentiwordnet.SentiPolarityAnalyzer;

import main.Client;
import manager.systems.SoftwareSystem;

/**
 *
 * @author Peter Wolf
 */
public class MainFrame extends javax.swing.JFrame {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private SentimentAnalyzer[] analyzer;
	private SoftwareSystem[] systems;

	/**
	 * Creates new form MainFrame
	 */
	public MainFrame() {
		initComponents();
		initSlider();
		initDate();
	}

	private void initDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -3);
		dateField.setText(convertDate(cal.getTime()));
	}

	private void initSlider() {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 0 ), new JLabel("0.0") );
		labelTable.put( new Integer( 50 ), new JLabel("0.5") );
		labelTable.put( new Integer( 100 ), new JLabel("1.0") );
		thresholdSlider.setLabelTable( labelTable );
		thresholdSlider.setPaintLabels(true);
		thresholdSlider.setValue(0);
	}

	public void run() {
		this.setLocationRelativeTo(getParent());
		this.setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        analysePanel = new javax.swing.JPanel();
        analyseBox = new javax.swing.JComboBox();
        sentiPolarityPanel = new javax.swing.JPanel();
        maxCountSpinner = new javax.swing.JSpinner();
        dateField = new javax.swing.JTextField();
        thresholdSlider = new javax.swing.JSlider();
        maxSourcesLabel = new javax.swing.JLabel();
        maxDateLabel = new javax.swing.JLabel();
        thresholdLabel = new javax.swing.JLabel();
        thresholdValueLabel = new javax.swing.JLabel();
        saveBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        systemList = new javax.swing.JList();
        systemListLabel = new javax.swing.JLabel();
        btnResults = new javax.swing.JButton();
        btnInfo = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();
        btnHistory = new javax.swing.JButton();
        mainMenuBar = new javax.swing.JMenuBar();
        startMenu = new javax.swing.JMenu();
        runItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        systemMenu = new javax.swing.JMenu();
        systemOverview = new javax.swing.JMenuItem();
        systemAdd = new javax.swing.JMenuItem();
        systemRemove = new javax.swing.JMenuItem();
        systemEdit = new javax.swing.JMenuItem();
        analyseMenu = new javax.swing.JMenu();
        analyseManager = new javax.swing.JMenuItem();
        crawlMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        analyseBox.setModel(new DefaultComboBoxModel());

        maxCountSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(500), null, null, Integer.valueOf(1)));

        thresholdSlider.setMajorTickSpacing(10);
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thresholdSliderStateChanged(evt);
            }
        });

        maxSourcesLabel.setText("max. # of sources used:");

        maxDateLabel.setText("max. date of source:");

        thresholdLabel.setText("Polarity Threshold:");

        thresholdValueLabel.setText("0.0");

        javax.swing.GroupLayout sentiPolarityPanelLayout = new javax.swing.GroupLayout(sentiPolarityPanel);
        sentiPolarityPanel.setLayout(sentiPolarityPanelLayout);
        sentiPolarityPanelLayout.setHorizontalGroup(
            sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sentiPolarityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(thresholdSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sentiPolarityPanelLayout.createSequentialGroup()
                        .addComponent(maxSourcesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addComponent(maxCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sentiPolarityPanelLayout.createSequentialGroup()
                        .addGroup(sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(thresholdLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(maxDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thresholdValueLabel))))
                .addContainerGap())
        );
        sentiPolarityPanelLayout.setVerticalGroup(
            sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sentiPolarityPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(maxCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxSourcesLabel))
                .addGap(18, 18, 18)
                .addGroup(sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxDateLabel))
                .addGap(18, 18, 18)
                .addGroup(sentiPolarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thresholdLabel)
                    .addComponent(thresholdValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(thresholdSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveBox.setText("Save Results");

        javax.swing.GroupLayout analysePanelLayout = new javax.swing.GroupLayout(analysePanel);
        analysePanel.setLayout(analysePanelLayout);
        analysePanelLayout.setHorizontalGroup(
            analysePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, analysePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(analysePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(analysePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveBox))
                    .addComponent(sentiPolarityPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(analyseBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        analysePanelLayout.setVerticalGroup(
            analysePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analysePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(analyseBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sentiPolarityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveBox)
                .addContainerGap())
        );

        systemList.setModel(new DefaultListModel<String>());
        jScrollPane1.setViewportView(systemList);

        systemListLabel.setText("Software Systems");

        btnResults.setText("Show Results");

        btnInfo.setText("Info");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnResults))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(systemListLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(systemListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResults)
                    .addComponent(btnInfo))
                .addContainerGap())
        );

        btnRun.setText("Run");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        btnHistory.setText("Run History");
        btnHistory.setEnabled(false);
        btnHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(analysePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHistory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRun)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(analysePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRun)
                            .addComponent(btnHistory))))
                .addContainerGap())
        );

        startMenu.setText("Start");

        runItem.setText("Run");
        runItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runItemActionPerformed(evt);
            }
        });
        startMenu.add(runItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        startMenu.add(exitItem);

        mainMenuBar.add(startMenu);

        systemMenu.setText("System");

        systemOverview.setText("Overview");
        systemOverview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                systemOverviewActionPerformed(evt);
            }
        });
        systemMenu.add(systemOverview);

        systemAdd.setText("Add System");
        systemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                systemAddActionPerformed(evt);
            }
        });
        systemMenu.add(systemAdd);

        systemRemove.setText("Remove System");
        systemRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                systemRemoveActionPerformed(evt);
            }
        });
        systemMenu.add(systemRemove);

        systemEdit.setText("Edit System");
        systemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                systemEditActionPerformed(evt);
            }
        });
        systemMenu.add(systemEdit);

        mainMenuBar.add(systemMenu);

        analyseMenu.setText("Analyse");

        analyseManager.setText("Manager");
        analyseManager.setEnabled(false);
        analyseManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analyseManagerActionPerformed(evt);
            }
        });
        analyseMenu.add(analyseManager);

        mainMenuBar.add(analyseMenu);

        crawlMenu.setText("Crawling");
        mainMenuBar.add(crawlMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
		cancel();
	}//GEN-LAST:event_exitItemActionPerformed

	private void runItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runItemActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_runItemActionPerformed

	private void systemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_systemAddActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_systemAddActionPerformed

	private void systemRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_systemRemoveActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_systemRemoveActionPerformed

	private void systemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_systemEditActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_systemEditActionPerformed

	private void systemOverviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_systemOverviewActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_systemOverviewActionPerformed

	private void analyseManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analyseManagerActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_analyseManagerActionPerformed

	private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
		int index = analyseBox.getSelectedIndex();
		SentimentAnalyzer sa = analyzer[index];
		int[] indeces = systemList.getSelectedIndices();
		SoftwareSystem[] chosenSystems = new SoftwareSystem[indeces.length];
		for (int i = 0; i < indeces.length; i++) {
			chosenSystems[i] = systems[indeces[i]];
		}
		runAnalyzer(sa, chosenSystems);

	}//GEN-LAST:event_btnRunActionPerformed

	private void btnHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_btnHistoryActionPerformed

	private void thresholdSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thresholdSliderStateChanged
		double threshold = (double)(thresholdSlider.getValue()) / 100.0;
		thresholdValueLabel.setText(threshold + "");
	}//GEN-LAST:event_thresholdSliderStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox analyseBox;
    private javax.swing.JMenuItem analyseManager;
    private javax.swing.JMenu analyseMenu;
    private javax.swing.JPanel analysePanel;
    private javax.swing.JButton btnHistory;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnResults;
    private javax.swing.JButton btnRun;
    private javax.swing.JMenu crawlMenu;
    private javax.swing.JTextField dateField;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSpinner maxCountSpinner;
    private javax.swing.JLabel maxDateLabel;
    private javax.swing.JLabel maxSourcesLabel;
    private javax.swing.JMenuItem runItem;
    private javax.swing.JCheckBox saveBox;
    private javax.swing.JPanel sentiPolarityPanel;
    private javax.swing.JMenu startMenu;
    private javax.swing.JMenuItem systemAdd;
    private javax.swing.JMenuItem systemEdit;
    private javax.swing.JList systemList;
    private javax.swing.JLabel systemListLabel;
    private javax.swing.JMenu systemMenu;
    private javax.swing.JMenuItem systemOverview;
    private javax.swing.JMenuItem systemRemove;
    private javax.swing.JLabel thresholdLabel;
    private javax.swing.JSlider thresholdSlider;
    private javax.swing.JLabel thresholdValueLabel;
    // End of variables declaration//GEN-END:variables

	private void runAnalyzer(SentimentAnalyzer sa, SoftwareSystem[] systems) {
		setParameter(sa);
		IAnalysisResult[] results = new IAnalysisResult[systems.length];
		for (int i = 0; i < systems.length; i++) {
			results[i] = sa.analyze(systems[i]);
			systems[i].addResult(results[i]);
//			results[i].show();
		}
//		System.out.println("Done.");
		if (saveBox.isSelected()) {
			Client.getInstance().saveResults(systems, results);
		}
		openShowResults(systems, results);
	}

	private void openShowResults(SoftwareSystem[] chosenSystems,
			IAnalysisResult[] results) {
		ShowResultsDialog dialog = new ShowResultsDialog(this, true, chosenSystems, results);
		dialog.run();
	}

	private void cancel() {
		this.dispose();
	}

	private void setParameter(SentimentAnalyzer sa) {
		if (sa instanceof SentiPolarityAnalyzer) {
			SentiPolarityAnalyzer spa = (SentiPolarityAnalyzer) sa;
			int maxSources = (int) maxCountSpinner.getValue();
			spa.setMaxSources(maxSources);
			Date limit = convertDate(dateField.getText());
			if (limit != null) {
				spa.setLimit(limit);
			}
			double threshold = (double)(thresholdSlider.getValue()) / 100.0;
			spa.setThreshold(threshold);
		}
	}

	/**
	 * @return the systems
	 */
	public SoftwareSystem[] getSystems() {
		return systems;
	}

	/**
	 * @param systems the systems to set
	 */
	public void setSystems(SoftwareSystem[] systems) {
		this.systems = systems;
		updateSystemLists();
	}

	private void updateSystemLists() {
		if (systems != null) {
			DefaultListModel<String> model = (DefaultListModel<String>) systemList.getModel();
			model.clear();
			for (SoftwareSystem ss : systems) {
				model.addElement(ss.getName());
			}
		}
	}

	/**
	 * @return the analyzer
	 */
	public SentimentAnalyzer[] getAnalyzer() {
		return analyzer;
	}

	/**
	 * @param analyzer the analyzer to set
	 */
	public void setAnalyzer(SentimentAnalyzer[] analyzer) {
		this.analyzer = analyzer;
		updateAnalyzerLists();
	}

	private Date convertDate(String childText) {
		Date date = null;
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
		try {
			date = formatter.parse(childText);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	private String convertDate(Date date) {
		DateFormat formatter  = new SimpleDateFormat(DATE_FORMAT); 
		return formatter.format(date);
	}

	private void updateAnalyzerLists() {
		if (analyzer != null) {
			DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) analyseBox.getModel();
			model.removeAllElements();
			for (SentimentAnalyzer sa : analyzer) {
				model.addElement(sa.getClass().getSimpleName());
			}
		}
	}
}
