package guiapp.main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;

import guiapp.tools.Checksum;
import java.awt.Font;
import java.awt.Color;

public class MainWindow {

	private JFrame frame;
	private JTextPane fileTextPane;
	private JComboBox<String> comboBox;
	private JTextArea checksumTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		 * Typical set of look and Feel options:
		 * Metal
		 * Nimbus (Default in Java Swing)
		 * CDE/Motif
		 * Windows
		 * Windows Classic
		 */
		
		for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			//System.out.println(info.getName());
			if( "Windows".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
					//custom title, warning icon
					JOptionPane.showMessageDialog(null,
					    "Unable to change Look and Feel!",
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
				}
				break;
			}
		}
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 779, 382);
		frame.setTitle("Checksum Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblChooseFile = new JLabel("Choose File:");
		lblChooseFile.setBounds(25, 25, 100, 16);
		frame.getContentPane().add(lblChooseFile);
		
		fileTextPane = new JTextPane();
		fileTextPane.setBounds(25, 45, 657, 25);
		fileTextPane.setPreferredSize(new Dimension(330, 25));
		
		frame.getContentPane().add(fileTextPane);
		
		JButton chooserButton = new JButton("...");
		chooserButton.setBounds(697, 45, 45, 25);
		chooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(frame); // jfc.showSaveDialog(frame);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					
					fileTextPane.setText(selectedFile.getAbsolutePath());
					//System.out.println(selectedFile.getAbsolutePath());
				}
			}
		});
		frame.getContentPane().add(chooserButton);
		
		
		JLabel lblChecksum = new JLabel("Checksum(in hex format):");
		lblChecksum.setBounds(25, 125, 190, 16);
		frame.getContentPane().add(lblChecksum);
		
		checksumTextArea = new JTextArea();
		checksumTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		checksumTextArea.setLineWrap(true);
		checksumTextArea.setBounds(25, 145, 657, 109);
		frame.getContentPane().add(checksumTextArea);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(25, 86, 148, 29);
		comboBox.addItem("MD5");
		comboBox.addItem("SHA-1");
		comboBox.addItem("SHA-256");
		frame.getContentPane().add(comboBox);

		JButton btnCalculateChecksum = new JButton("Calculate Checksum");
		btnCalculateChecksum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				Checksum checksum = new Checksum((String)comboBox.getSelectedItem());
				
				checksumTextArea.setText(
						checksum.forFile(fileTextPane.getText())
				);
			}
		});
		btnCalculateChecksum.setBounds(231, 86, 249, 30);
		frame.getContentPane().add(btnCalculateChecksum);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBackground(new Color(244, 164, 96));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//System.exit(0);
			}
		});
		btnClose.setBounds(25, 270, 124, 40);
		frame.getContentPane().add(btnClose);
		
		frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	                // Ask for confirmation before terminating the program.
	                int option = JOptionPane.showConfirmDialog(
	                        frame, 
	                        "Are you sure you want to close the application?",
	                        "Close Confirmation", 
	                        JOptionPane.YES_NO_OPTION, 
	                        JOptionPane.QUESTION_MESSAGE);
	                if (option == JOptionPane.YES_OPTION) {
	                        System.exit(0);
	                }
	        }
	});
		
	}
}
