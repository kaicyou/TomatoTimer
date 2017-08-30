import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class InputSetting extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldWork;
	private JTextField textFieldRest;
	private JTextField textFieldTime;
	private JSpinner spinner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputSetting frame = new InputSetting();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public InputSetting() {
		setTitle("Setting...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWorkingTime = new JLabel("Working Time:");
		lblWorkingTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkingTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWorkingTime.setBounds(10, 11, 99, 22);
		contentPane.add(lblWorkingTime);
		
		JLabel lblRestTime = new JLabel("Rest Time:");
		lblRestTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRestTime.setBounds(10, 73, 99, 22);
		contentPane.add(lblRestTime);
		
		JSlider sliderWork = new JSlider();
		sliderWork.setMinorTickSpacing(2);
	    sliderWork.setMajorTickSpacing(10);
		sliderWork.setPaintLabels(true);
		sliderWork.setPaintTicks(true);
		sliderWork.setValue(20);
		sliderWork.setMaximum(60);
		sliderWork.setBounds(119, 11, 247, 45);
		sliderWork.setLabelTable(sliderWork.createStandardLabels(10));
		sliderWork.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textFieldWork.setText(String.valueOf(sliderWork.getValue()));
			}
		});
		contentPane.add(sliderWork);
		
		JSlider sliderRest = new JSlider();
		sliderRest.setMinorTickSpacing(1);
	    sliderRest.setMajorTickSpacing(5);
		sliderRest.setPaintLabels(true);
		sliderRest.setPaintTicks(true);
		sliderRest.setValue(5);
		sliderRest.setMaximum(30);
		sliderRest.setBounds(119, 73, 247, 45);
		sliderRest.setLabelTable(sliderRest.createStandardLabels(10));
		sliderRest.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textFieldRest.setText(String.valueOf(sliderRest.getValue()));
			}
		});
		contentPane.add(sliderRest);
		
		JLabel lblWorkingCycle = new JLabel("Working Cycle:");
		lblWorkingCycle.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkingCycle.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWorkingCycle.setBounds(10, 133, 99, 22);
		contentPane.add(lblWorkingCycle);
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 0, 30, 1));
		spinner.setBounds(119, 135, 48, 20);
		contentPane.add(spinner);
		
		textFieldWork = new JTextField();
		textFieldWork.setText("20");
		textFieldWork.setBounds(376, 13, 24, 20);
		textFieldWork.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = textFieldWork.getText();
				sliderWork.setValue(0);
				if (!typed.matches("\\d+") || typed.length() > 3) {
					return;
				}
				int value = Integer.parseInt(typed);
				sliderWork.setValue(value);
			}
		});
		contentPane.add(textFieldWork);
		textFieldWork.setColumns(10);
		
		textFieldRest = new JTextField();
		textFieldRest.setText("5");
		textFieldRest.setColumns(10);
		textFieldRest.setBounds(376, 75, 24, 20);
		textFieldRest.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = textFieldRest.getText();
				sliderRest.setValue(0);
				if (!typed.matches("\\d+") || typed.length() > 3) {
					return;
				}
				int value = Integer.parseInt(typed);
				sliderRest.setValue(value);
			}
		});
		contentPane.add(textFieldRest);
		
		JLabel lblMin = new JLabel(" min");
		lblMin.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMin.setBounds(400, 11, 34, 22);
		contentPane.add(lblMin);
		
		JLabel label = new JLabel(" min");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(400, 73, 34, 22);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("Session will end at:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(210, 131, 131, 26);
		contentPane.add(lblNewLabel);
		
		textFieldTime = new JTextField();
		textFieldTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldTime.setEditable(false);
		textFieldTime.setBounds(351, 132, 73, 26);
		contentPane.add(textFieldTime);
		textFieldTime.setColumns(10);
		
		Timer t = new Timer(0, new Listener());
		t.start();
	}
	
	class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Calendar rightNow = Calendar.getInstance();
			
			int hourNow = rightNow.get(Calendar.HOUR_OF_DAY);
			int minNow = rightNow.get(Calendar.MINUTE);
			int sec = rightNow.get(Calendar.SECOND);
			
			//int workingCycle = (Integer)spinner.getValue();
			int workingCycle = 3;
			int timeAdd = Integer.parseInt(textFieldWork.getText()) * workingCycle + Integer.parseInt(textFieldRest.getText()) * (workingCycle - 1);
			int totalMinFinal = hourNow * 60 + minNow + timeAdd;
			int hour = totalMinFinal / 60;
			int min = totalMinFinal % 60;
			
			textFieldTime.setText(hour + ":" + min + ":" + sec);
		}
	}
}
