package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.*;

import Model.BookingService;
import Model.User;
import Model.AdminManager;

public class HomePanel extends JPanel {
	private JFrame frame;
	private JPanel mainPanel;
	private CardLayout cardLayout;
	private ArrayList<User> usersList; // Danh sách người dùng
	private AdminManager admin;
	private BookingService bookingService;
	private JMenuItem signUp, loginUp;
	private JButton buttonInfo;
	private AdminPanel adminPanel;

	public HomePanel(JFrame frame, JPanel mainPanel, CardLayout cardLayout) {
		this.frame = frame;
		this.mainPanel = mainPanel;
		this.cardLayout = cardLayout;
		// JPanel topPanel = createMenuBar();
		setLayout(new BorderLayout());
		adminPanel = new AdminPanel();
		usersList = new ArrayList<User>();
		admin = new AdminManager();
		// userManager = new UserManager();
		bookingService = new BookingService();
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		mainPanel.add(this, "Home");
//		mainPanel.add(createRecommendedPanel(), "RestaurantDetail");
		mainPanel.add(adminPanel, "admin");

		JPanel homePanel = createHomePanel();
		add(homePanel);

	}

	// Phương thức tạo nút không có đường viền
	private JButton createBorderlessButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Serif", Font.BOLD, 18)); // Font chữ
		button.setForeground(Color.BLACK); // Màu chữ
		button.setFocusPainted(false); // Tắt khung khi nút được chọn
		button.setBorderPainted(false); // Tắt đường viền
		button.setContentAreaFilled(false); // Nền trong suốt

		// Hiệu ứng hover: đổi màu hoặc thêm gạch chân
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(new Color(255, 0, 0)); // Đổi màu khi hover
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(Color.BLACK); // Đặt lại màu mặc định
			}
		});

		return button;
	}

	public JPanel createMenuBar() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelCenter.add(createBorderlessButton("Gần bạn"));
		panelCenter.add(createBorderlessButton("Bộ sưu tập"));
		panelCenter.add(createBorderlessButton("Ăn uống"));
		panelCenter.add(createBorderlessButton("Nhà hàng uy tín"));
		panelCenter.add(createBorderlessButton("Ưu đãi hot"));

		ImageIcon originalIcon = new ImageIcon("data/Nhom8.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel imageLabel = new JLabel(scaledIcon);
		imageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(mainPanel, "Home");
			}
		});
		topPanel.add(imageLabel, BorderLayout.WEST);

		buttonInfo = new JButton("Tài khoản");
		buttonInfo.setFont(new Font("Arial", Font.BOLD, 14));
		JPopupMenu popupMenu = new JPopupMenu();

		signUp = new JMenuItem("Đăng ký", new ImageIcon(""));
		signUp.addActionListener(e -> showRegisterDialog());
		signUp.setFont(new Font("Arial", Font.BOLD, 14));

		loginUp = new JMenuItem("Đăng nhập");
		loginUp.addActionListener(e -> showLoginDialog());
		loginUp.setFont(new Font("Arial", Font.BOLD, 14));
		popupMenu.add(signUp);
		popupMenu.add(loginUp);

		buttonInfo.addActionListener(e -> {
			popupMenu.show(buttonInfo, 0, buttonInfo.getHeight());
		});
		topPanel.add(buttonInfo, BorderLayout.EAST);

		topPanel.add(panelCenter, BorderLayout.CENTER);
		return topPanel;
	}

	private JPanel createHomePanel() {
		JPanel homePanel = new JPanel();
		homePanel.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelCenter.add(createBorderlessButton("Gần bạn"));
		panelCenter.add(createBorderlessButton("Bộ sưu tập"));
		panelCenter.add(createBorderlessButton("Ăn uống"));
		panelCenter.add(createBorderlessButton("Nhà hàng uy tín"));
		panelCenter.add(createBorderlessButton("Ưu đãi hot"));

		buttonPanel.add(panelCenter, BorderLayout.CENTER);

		// Panel tìm kiếm
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // FlowLayout, khoảng cách giữa các thành phần
																			// là 0
		searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Thêm khoảng cách trên dưới, hai bên

		// ComboBox chọn địa điểm
		String[] locations = { "Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ" };
		JComboBox<String> locationComboBox = new JComboBox<>(locations);
		locationComboBox.setPreferredSize(new Dimension(150, 30));

		// Trường nhập thông tin tìm kiếm
		JTextField searchField = new JTextField("Bạn muốn đặt chỗ đến đâu");
		searchField.setPreferredSize(new Dimension(250, 30));
		searchField.setForeground(Color.GRAY); // Màu chữ mặc định (placeholder)

		// Panel chứa các gợi ý tìm kiếm
		JPopupMenu suggestionPopup = new JPopupMenu(); // Menu chứa danh sách gợi ý

		// Dữ liệu mẫu cho các gợi ý
		String[] suggestions = { "Nhà hàng A", "Nhà hàng B", "Nhà hàng C", "Quán ăn ngon Hồ Chí Minh",
				"Nhà hàng lãng mạn Hà Nội" };

		// Thêm FocusListener để xử lý sự kiện focus
		searchField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Khi người dùng nhấn vào TextField
				if (searchField.getText().equals("Bạn muốn đặt chỗ đến đâu")) {
					searchField.setText(""); // Xóa placeholder
					searchField.setForeground(Color.BLACK); // Đổi màu chữ về bình thường
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Khi người dùng rời khỏi TextField
				if (searchField.getText().isEmpty()) {
					searchField.setText("Bạn muốn đặt chỗ đến đâu"); // Khôi phục placeholder
					searchField.setForeground(Color.GRAY); // Đổi lại màu placeholder
				}
			}
		});

		// Thêm DocumentListener để hiển thị gợi ý khi nhập liệu
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			private void updateSuggestions() {
				suggestionPopup.removeAll(); // Xóa các gợi ý cũ
				String input = searchField.getText().toLowerCase();

				if (!input.isEmpty()) {
					for (String suggestion : suggestions) {
						if (suggestion.toLowerCase().contains(input)) { // Gợi ý có chứa chuỗi nhập vào
							JMenuItem suggestionItem = new JMenuItem(suggestion);
							suggestionItem.addActionListener(e -> {
								searchField.setText(suggestion); // Điền gợi ý vào TextField
								suggestionPopup.setVisible(false); // Ẩn menu gợi ý
							});
							suggestionPopup.add(suggestionItem);
						}
					}
				}

				if (suggestionPopup.getComponentCount() > 0) {
					suggestionPopup.show(searchField, 0, searchField.getHeight()); // Hiển thị gợi ý
				} else {
					suggestionPopup.setVisible(false); // Ẩn menu nếu không có gợi ý
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSuggestions();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSuggestions();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSuggestions();
			}
		});

		// Nút tìm kiếm
		JButton searchButton = new JButton("Tìm kiếm");
		searchButton.setPreferredSize(new Dimension(100, 30));
		searchButton.setBackground(Color.RED);
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Arial", Font.BOLD, 14));

		// Layout và khoảng cách giữa các thành phần
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần: trên, trái, dưới, phải

		// Thêm ComboBox vào searchPanel
		gbc.gridx = 0; // Vị trí cột
		gbc.gridy = 0; // Vị trí hàng
		gbc.anchor = GridBagConstraints.WEST; // Căn về phía trái
		searchPanel.add(locationComboBox, gbc);

		// Thêm TextField vào searchPanel
		gbc.gridx = 1; // Sang cột thứ 2
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL; // Căn chỉnh TextField cho vừa
		searchPanel.add(searchField, gbc);

		// Thêm Button vào searchPanel
		gbc.gridx = 2; // Sang cột thứ 3
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST; // Căn về phía phải
		searchPanel.add(searchButton, gbc);

		// Xử lý sự kiện tìm kiếm
		searchButton.addActionListener(e -> {
			String selectedLocation = (String) locationComboBox.getSelectedItem();
			String searchText = searchField.getText().trim();

			if (searchText.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập địa điểm bạn muốn tìm!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Đang tìm kiếm: " + searchText + " tại " + selectedLocation,
						"Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Thêm các thành phần vào panel tìm kiếm
		searchPanel.add(locationComboBox, BorderLayout.WEST);
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(searchButton, BorderLayout.EAST);
		searchPanel.setBackground(new Color(210, 230, 240));

		// Thêm panel tìm kiếm vào Home Panel (ở trên nút tài khoản)
		buttonPanel.add(searchPanel, BorderLayout.SOUTH);

		buttonInfo = new JButton("Tài khoản");
		buttonInfo.setFont(new Font("Arial", Font.BOLD, 14));

		ImageIcon originalIcon = new ImageIcon("data/Nhom8.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel imageLabel = new JLabel(scaledIcon);
		imageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(mainPanel, "Home");
			}
		});
		buttonPanel.add(imageLabel, BorderLayout.WEST);

		JPopupMenu popupMenu = new JPopupMenu();

		signUp = new JMenuItem("Đăng ký", new ImageIcon(""));
		signUp.addActionListener(e -> showRegisterDialog());
		signUp.setFont(new Font("Arial", Font.BOLD, 14));

		loginUp = new JMenuItem("Đăng nhập");
		loginUp.addActionListener(e -> showLoginDialog());
		loginUp.setFont(new Font("Arial", Font.BOLD, 14));
		popupMenu.add(signUp);
		popupMenu.add(loginUp);

		buttonInfo.addActionListener(e -> {
			popupMenu.show(buttonInfo, 0, buttonInfo.getHeight());
		});
		buttonPanel.add(buttonInfo, BorderLayout.EAST);
		homePanel.add(createRecommendedPanel(), BorderLayout.CENTER);
		homePanel.add(buttonPanel, BorderLayout.NORTH);

		return homePanel;
	}

	private JPanel createRecommendedPanel() {
		JPanel recommendedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Layout để hiển thị các mục
		recommendedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitle = new JLabel("Top Nhà Hàng Ưu Đãi Hot", JLabel.CENTER);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
		lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

		JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));

		// Tạo danh sách các mục gợi ý
		String[][] items = { { "Ưu đãi nhà hàng Lẩu Nướng tại TP.HCM", "data/lau.png", "16", "Lẩu Nướng" },
				{ "Ưu đãi nhà hàng Việt Nam tại Tp.HCM", "data/nhieumon.png", "38", "Việt Nam" },
				{ "Ưu đãi nhà hàng Chay tại Tp.HCM", "data/chay.png", "6", "Chay" },
				{ "Khám phá tất cả Nhà hàng có ưu đãi tại Tp.HCM", "data/khampha.png", "152", "Khám phá" },
				{ "Các Nhà hàng Hải Sản Tp.HCM tại nhiều mức giá", "data/buffet.png", "5", "Hải sản" } };

		for (String[] item : items) {
			itemsPanel.add(createItemPanel(item[0], item[1], item[2], item[3]));
		}
		recommendedPanel.add(lblTitle, BorderLayout.NORTH);
		recommendedPanel.add(itemsPanel, BorderLayout.SOUTH);
		return recommendedPanel;
	}

	private JPanel createItemPanel(String title, String imagePath, String score, String type) {
		JPanel itemPanel = new JPanel(new BorderLayout());
		itemPanel.setPreferredSize(new Dimension(200, 250));
		itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		// Thêm hình ảnh
		JLabel lblImage = new JLabel();
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			Image img = ImageIO.read(new File(imagePath));
			ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 120, Image.SCALE_SMOOTH));
			lblImage.setIcon(icon);
		} catch (IOException ex) {
			lblImage.setText("Không tải được ảnh");
		}
		lblImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showRestaurantDetail(type); // Hiển thị trang chi tiết
			}
		});

		// Thêm tiêu đề
		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 14));
		lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lblTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showRestaurantDetail(type); // Hiển thị trang chi tiết
			}
		});

		// Thêm điểm
		JLabel lblScore = new JLabel("Số điểm đến: " + score, SwingConstants.CENTER);
		lblScore.setFont(new Font("SansSerif", Font.PLAIN, 12));

		// Sắp xếp các thành phần
		itemPanel.add(lblImage, BorderLayout.NORTH);
		itemPanel.add(lblTitle, BorderLayout.CENTER);
		itemPanel.add(lblScore, BorderLayout.SOUTH);

		return itemPanel;
	}

	private void showRestaurantDetail(String type) {
		// Chuyển sang trang chi tiết của nhà hàng tương ứng
		JOptionPane.showMessageDialog(this, "Xem thông tin chi tiết nhà hàng: " + type, "Thông báo",
				JOptionPane.INFORMATION_MESSAGE);

		// Hoặc chuyển CardLayout sang trang chi tiết:
		cardLayout.show(mainPanel, "RestaurantDetail"); // Cần thêm panel "RestaurantDetail" trước đó
	}

	private void showLoginDialog() {
		JDialog loginDialog = new JDialog(frame, "Đăng nhập", true);
		loginDialog.setSize(300, 200);
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setLayout(new BorderLayout());

		// Image background = Toolkit.getDefaultToolkit().createImage("Background.png");
		loginDialog.update(getGraphics());

		JLabel lblLoginTitle = new JLabel("Đăng nhập", JLabel.CENTER);
		lblLoginTitle.setFont(new Font("Serif", Font.BOLD, 20));

		JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblUsername = new JLabel("Tên đăng nhập:");
		JTextField txtUsername = new JTextField();

		JLabel lblPassword = new JLabel("Mật khẩu:");
		JPasswordField txtPassword = new JPasswordField();

		formPanel.add(lblUsername);
		formPanel.add(txtUsername);
		formPanel.add(lblPassword);
		formPanel.add(txtPassword);

		JButton btnSubmit = new JButton("Đăng nhập");
		btnSubmit.addActionListener(e -> {
			String username = txtUsername.getText();
			String password = new String(txtPassword.getPassword());

			if (validateLogin(username, password)) {
				JOptionPane.showMessageDialog(loginDialog, "Đăng nhập thành công!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				loginDialog.dispose(); // Đóng cửa sổ đăng nhập
				bookingService.setCurrentUser(username);
				updateMenuBar(username, password);
				cardLayout.show(mainPanel, "Home");
				// Chuyển đến màn hình tiếp theo sau khi đăng nhập thành công (nếu cần)
			} else if (admin.checkAdmin(username, password)) {
				JOptionPane.showMessageDialog(loginDialog, "Đăng nhập admin thành công!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				loginDialog.dispose(); // Đóng cửa sổ đăng nhập
				updateMenuBar(username, password);
			} else {
				JOptionPane.showMessageDialog(loginDialog, "Sai tên đăng nhập hoặc mật khẩu.", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		loginDialog.add(lblLoginTitle, BorderLayout.NORTH);
		loginDialog.add(formPanel, BorderLayout.CENTER);
		loginDialog.add(btnSubmit, BorderLayout.SOUTH);

		loginDialog.setVisible(true);
	}

	// Kiểm tra đăng nhập
	private boolean validateLogin(String username, String password) {
		for (User user : usersList) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)
					&& user.getRole().equals("user")) {
				return true;
			}
		}
		return false;
	}

	private void resetButtonToDefault() {
		JPopupMenu popupMenu = new JPopupMenu();

		// Tạo menu "Đăng ký"
		JMenuItem signUp = new JMenuItem("Đăng ký");
		signUp.addActionListener(e -> showRegisterDialog());
		popupMenu.add(signUp);

		// Tạo menu "Đăng nhập"
		JMenuItem loginUp = new JMenuItem("Đăng nhập");
		loginUp.addActionListener(e -> showLoginDialog());
		popupMenu.add(loginUp);

		// Cập nhật nút
		buttonInfo.setText("Tài khoản");
		buttonInfo.removeActionListener(buttonInfo.getActionListeners()[0]);
		buttonInfo.addActionListener(e -> popupMenu.show(buttonInfo, 0, buttonInfo.getHeight()));

		buttonInfo.revalidate();
		buttonInfo.repaint();
	}

	private void updateMenuBar(String username, String password) {
		JPopupMenu popupMenu = new JPopupMenu();

		if (admin.checkAdmin(username, password)) { // Kiểm tra nếu là admin
			JMenuItem menuItemAdminDashboard = new JMenuItem("Quản trị viên");
			menuItemAdminDashboard.addActionListener(e -> {
				cardLayout.show(mainPanel, "admin"); // Hiển thị AdminPanel
			});
			popupMenu.add(menuItemAdminDashboard);
		} else {
			// Tạo menu "Thông tin tài khoản"
			JMenuItem menuItemProfile = new JMenuItem("Xem thông tin cá nhân");
			menuItemProfile.addActionListener(e -> {
				JOptionPane.showMessageDialog(this, "Tên tài khoản: " + username + "\nCác thông tin khác...",
						"Thông tin tài khoản", JOptionPane.INFORMATION_MESSAGE);
			});
			popupMenu.add(menuItemProfile);
		}

		// Tạo menu "Đăng xuất"
		JMenuItem menuItemLogout = new JMenuItem("Đăng xuất");
		menuItemLogout.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Đăng xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			// Quay lại trạng thái trước đăng nhập
			buttonInfo.setText("Tài khoản");
			buttonInfo.removeActionListener(buttonInfo.getActionListeners()[0]); // Xóa hành động hiện tại
			buttonInfo.addActionListener(evt -> popupMenu.show(buttonInfo, 0, buttonInfo.getHeight()));
			resetButtonToDefault();
			cardLayout.show(mainPanel, "Home");
		});
		popupMenu.add(menuItemLogout);

		// Cập nhật nút "buttonInfo" cho người dùng
		buttonInfo.setText("Xin chào, " + username);
		buttonInfo.removeActionListener(buttonInfo.getActionListeners()[0]); // Xóa hành động cũ
		buttonInfo.addActionListener(e -> popupMenu.show(buttonInfo, 0, buttonInfo.getHeight())); // Gắn menu mới

		buttonInfo.revalidate();
		buttonInfo.repaint();
	}

	// Hiển thị màn hình đăng ký đè lên
	private void showRegisterDialog() {
		JDialog registerDialog = new JDialog(frame, "Đăng ký", true);
		registerDialog.setSize(350, 250);
		registerDialog.setLocationRelativeTo(this);
		registerDialog.setLayout(new BorderLayout());

		JLabel lblRegisterTitle = new JLabel("Đăng ký", JLabel.CENTER);
		lblRegisterTitle.setFont(new Font("Serif", Font.BOLD, 20));

		JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblUsername = new JLabel("Tên đăng nhập:");
		JTextField txtUsername = new JTextField();

		JLabel lblEmail = new JLabel("Email:");
		JTextField txtEmail = new JTextField();

		JLabel lblPassword = new JLabel("Mật khẩu:");
		JPasswordField txtPassword = new JPasswordField();

		JLabel lblConfirmPassword = new JLabel("Xác nhận mật khẩu:");
		JPasswordField txtConfirmPassword = new JPasswordField();

		formPanel.add(lblUsername);
		formPanel.add(txtUsername);
		formPanel.add(lblEmail);
		formPanel.add(txtEmail);
		formPanel.add(lblPassword);
		formPanel.add(txtPassword);
		formPanel.add(lblConfirmPassword);
		formPanel.add(txtConfirmPassword);

		JButton btnSubmit = new JButton("Đăng ký");
		btnSubmit.addActionListener(e -> {
			String username = txtUsername.getText();
			String email = txtEmail.getText();
			String password = new String(txtPassword.getPassword());
			String confirmPassword = new String(txtConfirmPassword.getPassword());

			if (password.equals(confirmPassword)) {
				usersList.add(new User(username, email, password));
				JOptionPane.showMessageDialog(registerDialog, "Đăng ký thành công!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				registerDialog.dispose();
			} else {
				JOptionPane.showMessageDialog(registerDialog, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		});

		registerDialog.add(lblRegisterTitle, BorderLayout.NORTH);
		registerDialog.add(formPanel, BorderLayout.CENTER);
		registerDialog.add(btnSubmit, BorderLayout.SOUTH);

		registerDialog.setVisible(true);
	}
}
