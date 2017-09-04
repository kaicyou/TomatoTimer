import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class TomatoTimer {

	private JFrame frame;
	private JLabel lblWorkingTime;
	private JLabel lblRestingTime;
	private JLabel lblWorkingCycle;
	private JLabel lblEndTime;
	
	private int defaultWorkingTime = 20;
	private int defaultRestingTime = 5;
	private int defaultWorkingCycle = 3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TomatoTimer window = new TomatoTimer();
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
	public TomatoTimer() {
		initialize();
	}
	
	// save the setting data for print
	// data[0] : working time
	// data[1] : rest time
	// data[2] : working cycle
	public String[] settingData = new String[3];
	
	// updating the labels with setting value
	public void postData () {
		this.lblWorkingTime.setText("Working Time: " + settingData[0] + " min");
		this.lblRestingTime.setText("Resting Time: " + settingData[1] + " min");
		this.lblWorkingCycle.setText("Working Cycle: " + settingData[2] + " times");
		int[] updatedNewTime = getUpdatedTime(Integer.parseInt(settingData[0]), Integer.parseInt(settingData[1]), Integer.parseInt(settingData[2]));
		this.lblEndTime.setText("End Time: " + updatedNewTime[0] + ":" + updatedNewTime[0]);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("TomotoTimer");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 210);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSetting = new JButton("Setting");
		btnSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InputSetting settingFrame = new InputSetting(TomatoTimer.this);
				settingFrame.setVisible(true);
			}
		});
		btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSetting.setHorizontalAlignment(SwingConstants.LEFT);
		Image settingImg = new ImageIcon(this.getClass().getResource("/setting.png")).getImage();
		btnSetting.setIcon(new ImageIcon(settingImg));
		btnSetting.setBounds(345, 11, 89, 23);
		frame.getContentPane().add(btnSetting);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnStart.setHorizontalAlignment(SwingConstants.LEFT);
		Image startImg = new ImageIcon(this.getClass().getResource("/start.png")).getImage();
		btnStart.setIcon(new ImageIcon(startImg));
		btnStart.setBounds(345, 79, 89, 23);
		frame.getContentPane().add(btnStart);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnReset.setHorizontalAlignment(SwingConstants.LEFT);
		Image resetImg = new ImageIcon(this.getClass().getResource("/reset.png")).getImage();
		btnReset.setIcon(new ImageIcon(resetImg));
		btnReset.setBounds(345, 45, 89, 23);
		frame.getContentPane().add(btnReset);
		
		JButton btnPause = new JButton("Pause");
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPause.setHorizontalAlignment(SwingConstants.LEFT);
		Image pauseImg = new ImageIcon(this.getClass().getResource("/pause.png")).getImage();
		btnPause.setIcon(new ImageIcon(pauseImg));
		btnPause.setBounds(345, 113, 89, 23);
		frame.getContentPane().add(btnPause);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnQuit.setHorizontalAlignment(SwingConstants.LEFT);
		Image quitImg = new ImageIcon(this.getClass().getResource("/quit.png")).getImage();
		btnQuit.setIcon(new ImageIcon(quitImg));
		btnQuit.setBounds(345, 147, 89, 23);
		frame.getContentPane().add(btnQuit);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 325, 91);
		panel.setOpaque(false);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.BLACK);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 69, 0)));
		panel_1.setBounds(10, 117, 325, 57);
		panel_1.setOpaque(false);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblWorkingTime = new JLabel("Working Time: " + defaultWorkingTime + " min");
		lblWorkingTime.setForeground(new Color(255, 69, 0));
		lblWorkingTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWorkingTime.setBounds(10, 11, 152, 20);
		panel_1.add(lblWorkingTime);
		
		lblRestingTime = new JLabel("Resting Time: " + defaultRestingTime + " min");
		lblRestingTime.setForeground(new Color(255, 69, 0));
		lblRestingTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRestingTime.setBounds(10, 30, 152, 20);
		panel_1.add(lblRestingTime);
		
		lblWorkingCycle = new JLabel("Working Cycel: " + defaultWorkingCycle + " times");
		lblWorkingCycle.setForeground(new Color(255, 69, 0));
		lblWorkingCycle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWorkingCycle.setBounds(174, 11, 145, 20);
		panel_1.add(lblWorkingCycle);
		
		int[] newTime = getUpdatedTime(defaultWorkingTime, defaultRestingTime, defaultWorkingCycle);
		
		lblEndTime = new JLabel("End Time: " + newTime[0] + ":" + newTime[1]);
		lblEndTime.setForeground(new Color(255, 69, 0));
		lblEndTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEndTime.setBounds(174, 30, 145, 20);
		panel_1.add(lblEndTime);
		
		JLabel background = new JLabel("");
		Image backgroundImg = new ImageIcon(this.getClass().getResource("/background.png")).getImage();
		background.setIcon(new ImageIcon(backgroundImg));
		background.setBounds(0, 0, 444, 181);
		frame.getContentPane().add(background);
	}
	
	private int[] getUpdatedTime(int workingTime, int restingTime, int workingCycle) {
		// returnTime[0] : hour
		// returnTime[1] : minute
		int[] returnTime = new int[2];
		int hourNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minNow = Calendar.getInstance().get(Calendar.MINUTE);
		int timeAdd = workingTime * workingCycle + restingTime * (workingCycle - 1);
		int totalMinFinal = hourNow * 60 + minNow + timeAdd;
		returnTime[0] = (totalMinFinal / 60) % 24;
		returnTime[1] = totalMinFinal % 60;
		return returnTime;
	}
}
