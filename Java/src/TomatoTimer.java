import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TomatoTimer {

	private JFrame frame;

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
				InputSetting settingFrame = new InputSetting();
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
		
		JLabel lblWorkingTime = new JLabel("Working Time:");
		lblWorkingTime.setForeground(new Color(255, 69, 0));
		lblWorkingTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWorkingTime.setBounds(10, 11, 88, 20);
		panel_1.add(lblWorkingTime);
		
		JLabel lblRestingTime = new JLabel("Resting Time:");
		lblRestingTime.setForeground(new Color(255, 69, 0));
		lblRestingTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRestingTime.setBounds(10, 30, 88, 20);
		panel_1.add(lblRestingTime);
		
		JLabel label = new JLabel("");
		Image backgroundImg = new ImageIcon(this.getClass().getResource("/background.png")).getImage();
		label.setIcon(new ImageIcon(backgroundImg));
		label.setBounds(0, 0, 444, 181);
		frame.getContentPane().add(label);
	}
}
