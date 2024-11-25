package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class AdminPanel extends JPanel {
	private JTabbedPane tabbedPane;
	private JPanel mainPanel; // Panel chính dùng với CardLayout
	private CardLayout cardLayout; // CardLayout để điều hướng

	public AdminPanel() {
		// Set up the main panel
		setLayout(new BorderLayout());

		// Create a tabbed pane for different sections
		tabbedPane = new JTabbedPane();

		// Add tabs for different functionalities
		tabbedPane.addTab("Dashboard", createDashboardPanel());
		tabbedPane.addTab("Quản lý Nhà Hàng", createRestaurantPanel());
		tabbedPane.addTab("Quản lý Người Dùng", createUserManagementPanel());
		tabbedPane.addTab("Khuyến Mãi", createPromotionPanel());
		tabbedPane.addTab("Quay lại", null); // Tab đặc biệt để quay lại

		// Add a listener to handle the "Quay lại" tab selection
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1) {
					// Nếu tab "Quay lại" được chọn
					if (mainPanel != null && cardLayout != null) {
						tabbedPane.requestFocus();	
						cardLayout.show(mainPanel, "Home"); // Quay lại màn hình Home
					}
				}
			}
		});

		// Add the tabbed pane to the panel
		add(tabbedPane, BorderLayout.CENTER);
	}

	// Create a dashboard panel with basic info
	private JPanel createDashboardPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Tổng quan về hệ thống"));
		// Add some basic stats (dummy example)
		panel.add(new JLabel("Số nhà hàng: 100"));
		panel.add(new JLabel("Số người dùng: 5000"));
		return panel;
	}

	// Create a panel for managing restaurants
	private JPanel createRestaurantPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// JTable for displaying restaurant info
		String[] columnNames = { "Tên Nhà Hàng", "Loại Hình", "Địa Chỉ" };
		Object[][] data = { { "Nhà hàng 1", "Buffet", "Quận 1" }, { "Nhà hàng 2", "Rooftop", "Quận 2" } };
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);

		// Buttons to add, edit, delete restaurants
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Thêm Nhà Hàng"));
		buttonPanel.add(new JButton("Chỉnh Sửa"));
		buttonPanel.add(new JButton("Xóa"));

		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}

	// Create a panel for managing users
	private JPanel createUserManagementPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		String[] columnNames = { "Tên Người Dùng", "Email", "Tình Trạng" };
		Object[][] data = { { "Nguyễn Văn A", "a@example.com", "Hoạt động" },
				{ "Trần Thị B", "b@example.com", "Bị khóa" } };
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Chỉnh Sửa"));
		buttonPanel.add(new JButton("Khóa Tài Khoản"));
		buttonPanel.add(new JButton("Xóa"));

		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}

	// Create a panel for managing promotions
	private JPanel createPromotionPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Danh sách khuyến mãi"));
		// Add form to add or edit promotions
		JTextField promotionField = new JTextField(20);
		panel.add(promotionField);
		panel.add(new JButton("Thêm Khuyến Mãi"));
		return panel;
	}

	public void setMainPanelAndLayout(JPanel mainPanel, CardLayout cardLayout) {
		this.mainPanel = mainPanel;
		this.cardLayout = cardLayout;
	}

//    public static void main(String[] args) {
//        // Create a JFrame to host the AdminPanel
//        JFrame frame = new JFrame("Admin Panel - Quản lý Nhà Hàng");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//
//        // Add AdminPanel to the frame
//        AdminPanel adminPanel = new AdminPanel();
//        frame.add(adminPanel);
//
//        frame.setVisible(true);
//    }
}
