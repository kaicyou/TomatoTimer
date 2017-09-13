import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;

public class TomatoTimer {

	private JFrame frame;
	private JLabel lblWorkingTime;
	private JLabel lblRestingTime;
	private JLabel lblWorkingCycle;
	private JLabel lblEndTime;
	private JLabel lblWorkTimeLeft;
	private JLabel lblRestTimeLeft;
	private JLabel lblCycleLeft;
	
	private final int defaultWorkingTime = 20;
	private final int defaultRestingTime = 5;
	private final int defaultWorkingCycle = 3;
	
	//private boolean isWorking = false;
	private int workingSec;
	private int restingSec;
	private int workingCycle;
	private boolean switchWorkRest;	// true for work; false for rest
	private boolean startTiming;	// only start timer when it's true
	int timeRemaining = 0;

	
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
		this.lblEndTime.setText("End Time: " + SetTimeFormat.setTimeFormat(updatedNewTime[0], updatedNewTime[1], 0, false));
		this.workingSec = Integer.parseInt(settingData[0]) * 60;
		this.restingSec = Integer.parseInt(settingData[1]) * 60;
		this.workingCycle = Integer.parseInt(settingData[2]);
		this.switchWorkRest = true;
	}
	
	// set frame visible
	public void setMeVisible () {
		frame.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		workingSec = defaultWorkingTime * 60;
		restingSec = defaultRestingTime * 60;
		workingCycle = defaultWorkingCycle;
		switchWorkRest = true;
		startTiming = true;
		
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
				frame.setVisible(false);
			}
		});
		btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSetting.setHorizontalAlignment(SwingConstants.LEFT);
		Image settingImg = new ImageIcon(this.getClass().getResource("/setting.png")).getImage();
		btnSetting.setIcon(new ImageIcon(settingImg));
		btnSetting.setBounds(345, 11, 89, 23);
		frame.getContentPane().add(btnSetting);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Timer timerWorkRest = new Timer();
				TimerTask taskWorkRest = new TimerTask() {
					int counter = switchWorkRest ? workingSec : restingSec;
					public void run() {
						if (workingCycle > 0) {
							if (timeRemaining != 0) {
								counter = timeRemaining;
								timeRemaining = 0;
							}
							int minRem = counter / 60;
							int secRem = counter % 60;
							if (switchWorkRest) {
								lblWorkTimeLeft.setText(SetTimeFormat.setCountDownFormat(minRem, secRem));
								lblRestTimeLeft.setText(SetTimeFormat.setCountDownFormat(restingSec / 60, restingSec % 60));
							} else {
								lblWorkTimeLeft.setText(SetTimeFormat.setCountDownFormat(workingSec / 60, workingSec % 60));
								lblRestTimeLeft.setText(SetTimeFormat.setCountDownFormat(minRem, secRem));
							}
							lblCycleLeft.setText(Integer.toString(workingCycle));
							counter--;
							if (counter == -1) {
								//timerWorkRest.cancel();
								if (switchWorkRest) {
									workingCycle--;
									if (workingCycle > 0) {
										Object[] optionsWork = {"Yes, get some rest",
					                    "No, I'll quit"};
										int workContinue = JOptionPane.showOptionDialog(frame,
										    "You've finished one working cycle, \n"
											+"Would you like to continue?",
										    "Working cycle finished",
										    JOptionPane.YES_NO_OPTION,
										    JOptionPane.QUESTION_MESSAGE,
										    null,     //do not use a custom Icon
										    optionsWork,  //the titles of buttons
										    optionsWork[0]); //default button title
										if (workContinue == 1) {
											timerWorkRest.cancel();
											lblWorkTimeLeft.setText(SetTimeFormat.setCountDownFormat(0, 0));
											lblRestTimeLeft.setText(SetTimeFormat.setCountDownFormat(0, 0));
											lblCycleLeft.setText(Integer.toString(0));
										} else {
											counter = restingSec;
											switchWorkRest = false;
										}
									}
								} else {
									Object[] optionsRest = {"Yes, get back to work",
				                    "No, I'll quit"};
									int restContinue = JOptionPane.showOptionDialog(frame,
									    "You've finished one Resting cycle, \n"
										+"Would you like to continue?",
									    "Resting cycle finished",
									    JOptionPane.YES_NO_OPTION,
									    JOptionPane.QUESTION_MESSAGE,
									    null,     //do not use a custom Icon
									    optionsRest,  //the titles of buttons
									    optionsRest[0]); //default button title
									if (restContinue == 1) {
										timerWorkRest.cancel();
										lblWorkTimeLeft.setText(SetTimeFormat.setCountDownFormat(0, 0));
										lblRestTimeLeft.setText(SetTimeFormat.setCountDownFormat(0, 0));
										lblCycleLeft.setText(Integer.toString(0));
									} else {
										counter = workingSec;
										switchWorkRest = true;
									}
								}
							} else if (!startTiming) {
								timeRemaining = counter;
								timerWorkRest.cancel();
								startTiming = true;
							}
						} else {
							lblWorkTimeLeft.setText(SetTimeFormat.setCountDownFormat(0, 0));
							lblRestTimeLeft.setText(SetTimeFormat.setCountDownFormat(0, 0));
							lblCycleLeft.setText(Integer.toString(0));
							timerWorkRest.cancel();
							Icon finishDialog = new ImageIcon(this.getClass().getResource("/finishDia.png"));
							JOptionPane.showMessageDialog(frame,
								    "You've finished your working journey!",
								    "Congratulations!",
								    JOptionPane.INFORMATION_MESSAGE,
								    finishDialog);
						}
					}
				};
				timerWorkRest.scheduleAtFixedRate(taskWorkRest, 0, 1000);
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnStart.setHorizontalAlignment(SwingConstants.LEFT);
		Image startImg = new ImageIcon(this.getClass().getResource("/start.png")).getImage();
		btnStart.setIcon(new ImageIcon(startImg));
		btnStart.setBounds(345, 79, 89, 23);
		frame.getContentPane().add(btnStart);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWorkingTime.setText("Working Time: " + defaultWorkingTime + " min");
				lblRestingTime.setText("Resting Time: " + defaultRestingTime + " min");
				lblWorkingCycle.setText("Working Cycle: " + defaultWorkingCycle + " times");
				int[] resetTime = getUpdatedTime(defaultWorkingTime, defaultRestingTime, defaultWorkingCycle);
				lblEndTime.setText("End Time: " + SetTimeFormat.setTimeFormat(resetTime[0], resetTime[1], 0, false));
				
				workingSec = defaultWorkingTime * 60;
				restingSec = defaultRestingTime * 60;
				workingCycle = defaultWorkingCycle;
				switchWorkRest = true;
				startTiming = true;
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnReset.setHorizontalAlignment(SwingConstants.LEFT);
		Image resetImg = new ImageIcon(this.getClass().getResource("/reset.png")).getImage();
		btnReset.setIcon(new ImageIcon(resetImg));
		btnReset.setBounds(345, 45, 89, 23);
		frame.getContentPane().add(btnReset);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (switchWorkRest) {
					startTiming = false;
				} else {
					JOptionPane.showMessageDialog(frame,
						    "You cannot pause while resting.",
						    "Cannot pause...",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPause.setHorizontalAlignment(SwingConstants.LEFT);
		Image pauseImg = new ImageIcon(this.getClass().getResource("/pause.png")).getImage();
		btnPause.setIcon(new ImageIcon(pauseImg));
		btnPause.setBounds(345, 113, 89, 23);
		frame.getContentPane().add(btnPause);
		
		String[] options = {"Yes, I want to quit", "No, I will stay"};
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Icon quitDialog = new ImageIcon(this.getClass().getResource("/quitDia.png"));
				int n = JOptionPane.showOptionDialog(frame, "Do you really want to quit?", "Quit?", 
													JOptionPane.YES_NO_CANCEL_OPTION, 
													JOptionPane.QUESTION_MESSAGE, quitDialog, options, options[1]);
				if (n == 0) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
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
		
		JLabel lblWorkingTimeRemaining = new JLabel("Working Time:");
		lblWorkingTimeRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkingTimeRemaining.setForeground(Color.RED);
		lblWorkingTimeRemaining.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWorkingTimeRemaining.setBounds(10, 11, 148, 29);
		panel.add(lblWorkingTimeRemaining);
		
		JLabel lblRestingTimeRemaining = new JLabel("Resting Time:");
		lblRestingTimeRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestingTimeRemaining.setForeground(new Color(34, 139, 34));
		lblRestingTimeRemaining.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRestingTimeRemaining.setBounds(10, 51, 148, 29);
		panel.add(lblRestingTimeRemaining);
		
		JLabel lblCycle = new JLabel("Cycle:");
		lblCycle.setHorizontalAlignment(SwingConstants.CENTER);
		lblCycle.setForeground(new Color(0, 0, 205));
		lblCycle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCycle.setBounds(253, 12, 62, 29);
		panel.add(lblCycle);
		
		lblWorkTimeLeft = new JLabel("00:00");
		lblWorkTimeLeft.setForeground(Color.RED);
		lblWorkTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWorkTimeLeft.setBounds(168, 11, 62, 29);
		panel.add(lblWorkTimeLeft);
		
		lblRestTimeLeft = new JLabel("00:00");
		lblRestTimeLeft.setForeground(new Color(34, 139, 34));
		lblRestTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRestTimeLeft.setBounds(168, 51, 62, 29);
		panel.add(lblRestTimeLeft);
		
		lblCycleLeft = new JLabel("0");
		lblCycleLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblCycleLeft.setForeground(new Color(0, 0, 205));
		lblCycleLeft.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCycleLeft.setBounds(253, 35, 62, 45);
		panel.add(lblCycleLeft);
		
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
		
		lblWorkingCycle = new JLabel("Working Cycle: " + defaultWorkingCycle + " times");
		lblWorkingCycle.setForeground(new Color(255, 69, 0));
		lblWorkingCycle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWorkingCycle.setBounds(161, 11, 158, 20);
		panel_1.add(lblWorkingCycle);
		
		int[] newTime = getUpdatedTime(defaultWorkingTime, defaultRestingTime, defaultWorkingCycle);
		
		lblEndTime = new JLabel("End Time: " + SetTimeFormat.setTimeFormat(newTime[0], newTime[1], 0, false));
		lblEndTime.setForeground(new Color(255, 69, 0));
		lblEndTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEndTime.setBounds(161, 30, 158, 20);
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
