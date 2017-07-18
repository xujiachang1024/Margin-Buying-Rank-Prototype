package front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import back.EastMoneyDriver;
import back.MarginSecurity;
import util.GraphicSettings;

public class MainGUI extends JFrame {
	
	private EastMoneyDriver driver;
	private Integer mTopNum;
	
	private JTextField mTopNumTextField;
	private JButton mUpdateShanghaiButton;
//	private JButton mUpdateShenzhenButton;
	private JButton mQuitButton;
	private DefaultTableModel mAssetModel;
	private JTable mAssetTable;
	private JScrollPane mScrollPane;
	private JLabel sourceLabel;
	private JButton mChooseFileButton;
	private JFileChooser mFileChooser;
	private File mCurrentFile;
	
	public MainGUI() {
		super("华泰证券融资净买率排序器");
		this.initializeVariables();
		this.createGUI();
		this.addListeners();
	}
	
	private void initializeVariables() {
		this.driver = new EastMoneyDriver();
		this.mTopNum = 20;
		this.mTopNumTextField = new JTextField();
		this.mTopNumTextField.setText("20");
		this.mUpdateShanghaiButton = new JButton("更新融资净买率");
//		this.mUpdateShenzhenButton = new JButton("更新深证融资净买率");
		this.mQuitButton = new JButton("退出");
		this.mAssetModel = new DefaultTableModel() {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
		};
		this.mAssetModel.addColumn("排名");
		this.mAssetModel.addColumn("证券代码");
		this.mAssetModel.addColumn("证券简称");
		this.mAssetModel.addColumn("融资余额（万元）");
		this.mAssetModel.addColumn("融资净买额（万元)");
		this.mAssetModel.addColumn("融资净买率（%）");
		this.mAssetTable = new JTable(mAssetModel);
		this.mScrollPane = new JScrollPane(mAssetTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.sourceLabel = new JLabel("数据来源：");
		this.mChooseFileButton = new JButton("从文件中读取数据");
		this.mFileChooser = new JFileChooser();
		this.mCurrentFile = null;
	}
	
	private void createGUI() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(600, 600);
		this.setLocation((dim.width / 2) - (this.getSize().width / 2), (dim.height / 2) - (this.getSize().height / 2));
		this.add(this.createNorthPanel(), BorderLayout.NORTH);
		this.add(this.createCenterPanel(), BorderLayout.CENTER);
		this.setVisible(true);
		this.toFront();
	}
	
	private JPanel createNorthPanel() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JLabel companyLabel = new JLabel("华泰证券无锡分公司");
		JLabel rankerLabel = new JLabel("融资净买率排序器");
		JLabel presenterLabel = new JLabel("presented by 徐嘉昌");
		GraphicSettings.setTextAlignment(companyLabel, rankerLabel, presenterLabel);
		northPanel.add(companyLabel, BorderLayout.NORTH);
		northPanel.add(rankerLabel, BorderLayout.CENTER);
		northPanel.add(presenterLabel, BorderLayout.SOUTH);
		return northPanel;
	}
	
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		JPanel sourcePanel = new JPanel();
		GraphicSettings.setSize(100, 30, mTopNumTextField);
		GraphicSettings.setTextAlignment(sourceLabel);
		buttonPanel.add(mTopNumTextField);
		buttonPanel.add(mUpdateShanghaiButton);
//		buttonPanel.add(mUpdateShenzhenButton);
		buttonPanel.add(mQuitButton);
		sourcePanel.add(sourceLabel);
		sourcePanel.add(mChooseFileButton);
		centerPanel.add(buttonPanel, BorderLayout.NORTH);
		centerPanel.add(mScrollPane, BorderLayout.CENTER);
		centerPanel.add(sourcePanel, BorderLayout.SOUTH);
		return centerPanel;
	}
	
	private void addListeners() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mUpdateShanghaiButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				while (mAssetModel.getRowCount() > 0) {
					mAssetModel.removeRow(0);
				}
				String topNumStr = mTopNumTextField.getText();
				if (topNumStr != "") {
					try {
						mTopNum = Integer.parseInt(topNumStr);
						if (mTopNum <= 0) {
							mTopNum = 20;
						}
					} catch (Exception exc) {
						mTopNum = 20;
					}
				}
				else {
					mTopNum = 20;
				}
				if (mCurrentFile != null) {
					List<MarginSecurity> list = driver.updateShanghai(mCurrentFile);
					for (int i = 0; i < mTopNum && i < list.size(); i++) {
						MarginSecurity se = list.get(i);
						mAssetModel.addRow(new String[] {
								Integer.toString(i + 1),
								se.getCode(),
								se.getName(),
								Math.round(se.getLongBalance() * 100.00) / 100.00 + "万元",
								Math.round(se.getNetBuying() * 100.00) / 100.00 + "万元",
								Math.round(se.getNetBuyingRatio() * 100.00) / 100.00 + "%"
						});
					}
				}
			}
			
		});
		
//		this.mUpdateShenzhenButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				while (mAssetModel.getRowCount() > 0) {
//					mAssetModel.removeRow(0);
//				}
//				String topNumStr = mTopNumTextField.getText();
//				if (topNumStr != "") {
//					try {
//						mTopNum = Integer.parseInt(topNumStr);
//						if (mTopNum <= 0) {
//							mTopNum = 20;
//						}
//					} catch (Exception exc) {
//						mTopNum = 20;
//					}
//				}
//				else {
//					mTopNum = 20;
//				}
//				if (mCurrentFile != null) {
//					List<MarginSecurity> list = driver.updateShenzhen(mCurrentFile);
//					for (int i = 0; i < mTopNum && i < list.size(); i++) {
//						MarginSecurity se = list.get(i);
//						mAssetModel.addRow(new String[] {
//								Integer.toString(i + 1),
//								se.getCode(),
//								se.getName(),
//								Math.round(se.getLongBalance() * 100.00) / 100.00 + "万元",
//								Math.round(se.getNetBuying() * 100.00) / 100.00 + "万元",
//								Math.round(se.getNetBuyingRatio() * 100.00) / 100.00 + "%"
//						});
//					}
//				}
//			}
//			
//		});
		
		this.mQuitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				if (driver != null) {
//					driver.quit();
//				}
				MainGUI.this.setVisible(false);
				MainGUI.this.dispose();
			}
			
		});
		
		this.mChooseFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int openResult = mFileChooser.showOpenDialog(null);
				if (openResult == JFileChooser.APPROVE_OPTION) {
					mCurrentFile = mFileChooser.getSelectedFile();
					sourceLabel.setText("数据来源：" + mCurrentFile.getName());
				}
			}
			
		});
	}

}
