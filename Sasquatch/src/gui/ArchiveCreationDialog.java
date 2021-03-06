/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ItemEvent;

import javax.swing.SpinnerDateModel;

import manager.systems.Archive;
import manager.systems.ArchiveType;

/**
 *
 * @author Peter Wolf
 */
public class ArchiveCreationDialog extends javax.swing.JDialog {

	private ArchiveType type = ArchiveType.LOCAL;

	private Archive created = null;
	private boolean pagesOn = true;
	private boolean datesOn = true;


	/**
	 * Creates new form ArchiveCreationDialog
	 */
	public ArchiveCreationDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		initBox();
	}
	
	public ArchiveCreationDialog(javax.swing.JDialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
		initBox();
	}
	
	public void run() {
		this.setLocationRelativeTo(getParent());
		this.setVisible(true);
	}

	private void initBox() {
		typeBox.removeAllItems();
		for (ArchiveType at : ArchiveType.values()) {
			typeBox.addItem(at);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		startSpinner = new javax.swing.JSpinner();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		endSpinner = new javax.swing.JSpinner();
		pageSpinner = new javax.swing.JSpinner();
		jLabel5 = new javax.swing.JLabel();
		btnCancel = new javax.swing.JButton();
		btnCreate = new javax.swing.JButton();
		referenceField = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		typeBox = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		startSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2000), null, Integer.valueOf(2013), Integer.valueOf(1)));

		jLabel3.setText("Start:");

		jLabel4.setText("End:");

		endSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2010), null, Integer.valueOf(2013), Integer.valueOf(1)));

		pageSpinner.setModel(new javax.swing.SpinnerNumberModel());

		jLabel5.setText("Pages:");

		btnCancel.setText("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		btnCreate.setText("Create");
		btnCreate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCreateActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel3)
								.addComponent(jLabel5))
								.addGap(32, 32, 32)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(pageSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(startSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(51, 51, 51)
												.addComponent(jLabel4)
												.addGap(33, 33, 33)
												.addComponent(endSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addContainerGap())
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
														.addGap(0, 0, Short.MAX_VALUE)
														.addComponent(btnCreate)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(btnCancel)
														.addGap(4, 4, 4))
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3)
								.addComponent(startSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel4)
								.addComponent(endSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel5)
										.addComponent(pageSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnCreate)
												.addComponent(btnCancel))
												.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		jLabel1.setText("Reference:");

		jLabel2.setText("Type:");

		typeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		typeBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				typeBoxItemStateChanged(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel1)
												.addComponent(jLabel2))
												.addGap(7, 7, 7)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(referenceField, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGap(0, 95, Short.MAX_VALUE)))
														.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(40, 40, 40)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(referenceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel1))
								.addGap(14, 14, 14)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel2)
										.addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
		created = new Archive(type, referenceField.getText());
		if (pagesOn) {
			created.setPages((int) pageSpinner.getValue());
		}
		if (datesOn) {
			created.setEnd((int) endSpinner.getValue());
			created.setStart((int) startSpinner.getValue());
		}
		this.dispose();
	}//GEN-LAST:event_btnCreateActionPerformed

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		this.created = null;
		this.dispose();
	}//GEN-LAST:event_btnCancelActionPerformed

	private void typeBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeBoxItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			setType((ArchiveType) evt.getItem());
		}
	}//GEN-LAST:event_typeBoxItemStateChanged

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnCreate;
	private javax.swing.JSpinner endSpinner;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JSpinner pageSpinner;
	private javax.swing.JTextField referenceField;
	private javax.swing.JSpinner startSpinner;
	private javax.swing.JComboBox typeBox;
	// End of variables declaration//GEN-END:variables


	

	/**
	 * @return the type
	 */
	public ArchiveType getChosenType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(ArchiveType type) {
		this.type = type;
		enableElements(type);
	}

	private void enableElements(ArchiveType type) {
		datesOn = true;
		pagesOn = true;
		switch(type) {
		case ECLIPSE_LIST:
		case MAIL_ARCHIVE:
		case MARK_MAIL:
		case YAHOO: break;
		case APACHE: 
		case JAVANET:
		case SOURCEFORGE: pagesOn = false; break;
		case APPLE_LIST:
		case PIPERMAIL: datesOn = false; break;
		case LOCAL: pagesOn = false; datesOn = false; break;
		default: break;
		}
		endSpinner.setEnabled(datesOn);
		startSpinner.setEnabled(datesOn);
		pageSpinner.setEnabled(pagesOn);
	}

	/**
	 * @return the created
	 */
	public Archive getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Archive created) {
		this.created = created;
	}
}
