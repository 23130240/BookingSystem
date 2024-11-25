package View;

import javax.swing.*;
import javax.swing.event.*;

import Controller.BookingSystemController;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Model.BookingService;
import Model.User;

public class BookingSystem extends JFrame {
	private JPanel mainPanel;
	private CardLayout cardLayout;
	private ArrayList<User> usersList; // Danh sách người dùng
	private BookingService bookingService;
	private JMenu accountMenu, userMenu;
	private JMenuItem signUp, loginUp, menuItemProfile, menuItemLogout;
	private JButton buttonInfo;

	public BookingSystem() {
		setTitle("Booking System");
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		ActionListener aL = new BookingSystemController(this);
		usersList = new ArrayList<>();
		// Sử dụng CardLayout để chuyển đổi giữa các trang
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		// Thêm các trang (Home, Login, và Booking) vào CardLayout
		mainPanel.add(createHomePanel(), "Home");
		mainPanel.add(createBookingPanel(), "Booking");
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Thanh cuộn dọc
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Thanh cuộn ngang

		bookingService = new BookingService();

		add(mainPanel);
		cardLayout.show(mainPanel, "Home"); // Bắt đầu với trang Home

		setVisible(true);
	}

	private JPanel createHomePanel() {
		JPanel homePanel = new JPanel();
		homePanel.setLayout(new BorderLayout());
		BackgroundPanel backGround = new BackgroundPanel("data/nhahang.png");
		homePanel.add(backGround);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		JButton buttonGanBan = new JButton("Gần bạn");
		JButton buttonBoSuuTap = new JButton("Bộ sưu tập");
		JButton buttonAnUong = new JButton("Ăn uống");
		JButton buttonNhahanguytin = new JButton("Nhà hàng uy tín");
		JButton buttonUudaihot = new JButton("Ưu đãi hot");
		
		// Panel tìm kiếm
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // FlowLayout, khoảng cách giữa các thành phần là 0
		searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Thêm khoảng cách trên dưới, hai bên

		// ComboBox chọn địa điểm
		String[] locations = {"Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ"};
		JComboBox<String> locationComboBox = new JComboBox<>(locations);
		locationComboBox.setPreferredSize(new Dimension(150, 30));

		// Trường nhập thông tin tìm kiếm
		JTextField searchField = new JTextField("Bạn muốn đặt chỗ đến đâu");
		searchField.setPreferredSize(new Dimension(250, 30));
		searchField.setForeground(Color.GRAY); // Màu chữ mặc định (placeholder)
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

		    if (searchText.isEmpty() || searchText.equals("Bạn muốn đặt chỗ đến đâu")) {
		        JOptionPane.showMessageDialog(this, "Vui lòng nhập địa điểm bạn muốn tìm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		    } else {
		        // Xử lý logic tìm kiếm, ví dụ:
		        JOptionPane.showMessageDialog(this, "Đang tìm kiếm: " + searchText + " tại " + selectedLocation, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		        // Bạn có thể gọi các phương thức khác hoặc cập nhật giao diện ở đây.
		    }
		});

		// Thêm các thành phần vào panel tìm kiếm
		searchPanel.add(locationComboBox, BorderLayout.WEST);
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(searchButton, BorderLayout.EAST);

		// Thêm panel tìm kiếm vào Home Panel (ở trên nút tài khoản)
		buttonPanel.add(searchPanel, BorderLayout.SOUTH);
		
		JLabel lblWelcome = new JLabel("Nhà hàng Fuuqa", JLabel.CENTER);
		lblWelcome.setFont(new Font("Serif", Font.BOLD, 24));
		
		buttonInfo = new JButton("Tài khoản");
		buttonInfo.setFont(new Font("Arial", Font.BOLD, 14));

		ImageIcon originalIcon = new ImageIcon("data/Nhom8.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH); // Tùy chỉnh kích thước
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel imageLabel = new JLabel(scaledIcon);
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
		buttonPanel.add(lblWelcome, BorderLayout.CENTER);
		buttonPanel.add(buttonInfo, BorderLayout.EAST);
		
		homePanel.add(buttonPanel, BorderLayout.NORTH);

		return homePanel;
	}

	// Hiển thị màn hình đăng nhập đè lên
	private void showLoginDialog() {
		JDialog loginDialog = new JDialog(this, "Đăng nhập", true);
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
				updateMenuBar(username);
				cardLayout.show(mainPanel, "Home");
				// Chuyển đến màn hình tiếp theo sau khi đăng nhập thành công (nếu cần)

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

	private void updateMenuBar(String username) {
		JPopupMenu popupMenu = new JPopupMenu();

		// Tạo menu "Thông tin tài khoản"
		JMenuItem menuItemProfile = new JMenuItem("Xem thông tin cá nhân");
		menuItemProfile.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Tên tài khoản: " + username + "\nCác thông tin khác...",
					"Thông tin tài khoản", JOptionPane.INFORMATION_MESSAGE);
		});
		popupMenu.add(menuItemProfile);

		// Tạo menu "Đăng xuất"
		JMenuItem menuItemLogout = new JMenuItem("Đăng xuất");
		menuItemLogout.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Đăng xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			// Quay lại trạng thái trước đăng nhập
			buttonInfo.setText("Tài khoản");
			buttonInfo.removeActionListener(buttonInfo.getActionListeners()[0]); // Xóa hành động hiện tại
			buttonInfo.addActionListener(evt -> popupMenu.show(buttonInfo, 0, buttonInfo.getHeight()));
			resetButtonToDefault();
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
	public void showRegisterDialog() {
		JDialog registerDialog = new JDialog(this, "Đăng ký", true);
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

	private JPanel createBookingPanel() {

		JPanel bookingPanel = new JPanel(new BorderLayout());
		bookingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitle = new JLabel("Đặt chỗ", JLabel.CENTER);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 22));

		JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		JLabel lblNumPeople = new JLabel("Số lượng người:");
		JTextField txtNumPeople = new JTextField();

		JLabel lblDate = new JLabel("Ngày đặt:");
		JTextField txtDate = new JTextField();

		JLabel lblTime = new JLabel("Thời gian:");
		JTextField txtTime = new JTextField();

		JLabel lblPhone = new JLabel("Số điện thoại:");
		JTextField txtPhone = new JTextField();

		formPanel.add(lblNumPeople);
		formPanel.add(txtNumPeople);
		formPanel.add(lblDate);
		formPanel.add(txtDate);
		formPanel.add(lblTime);
		formPanel.add(txtTime);
		formPanel.add(lblPhone);
		formPanel.add(txtPhone);

		JButton btnBook = new JButton("Đặt chỗ");
		btnBook.addActionListener(e -> {
			if (isFormComplete(txtNumPeople, txtDate, txtTime, txtPhone)) {
				// Sau khi đặt chỗ thành công, chuyển sang trang thanh toán
				showPaymentQRCode();
			} else {
				JOptionPane.showMessageDialog(bookingPanel, "Vui lòng điền đầy đủ thông tin.", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		bookingPanel.add(lblTitle, BorderLayout.NORTH);
		bookingPanel.add(formPanel, BorderLayout.CENTER);
		bookingPanel.add(btnBook, BorderLayout.SOUTH);
		return bookingPanel;
	}

	// Kiểm tra đăng nhập
	private boolean validateLogin(String username, String password) {
		for (User user : usersList) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	// Hàm kiểm tra các trường thông tin
	private boolean isFormComplete(JTextField... fields) {
		for (JTextField field : fields) {
			if (field.getText().trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	// Hiển thị cửa sổ thanh toán qua QR
	private void showPaymentQRCode() {
		JDialog paymentDialog = new JDialog(this, "Thanh toán qua QR", true);
		paymentDialog.setSize(400, 400);
		paymentDialog.setLocationRelativeTo(this);

		JPanel qrPanel = new JPanel();
		qrPanel.setLayout(new BorderLayout());

		JLabel lblPaymentText = new JLabel("Quét mã QR để thanh toán", JLabel.CENTER);
		lblPaymentText.setFont(new Font("Serif", Font.BOLD, 16));
		qrPanel.add(lblPaymentText, BorderLayout.NORTH);

		try {
			// Tải hình ảnh mã QR từ file
			Image qrImage = ImageIO.read(new File("data/Qr.png"));
			ImageIcon qrIcon = new ImageIcon(qrImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
			JLabel lblQRCode = new JLabel(qrIcon);
			qrPanel.add(lblQRCode, BorderLayout.CENTER);
		} catch (IOException ex) {
			ex.printStackTrace();
			JLabel errorLabel = new JLabel("Không thể tải mã QR", JLabel.CENTER);
			qrPanel.add(errorLabel, BorderLayout.CENTER);
		}

		// Thêm nút "Xác nhận thanh toán"
		JButton btnConfirmPayment = new JButton("Xác nhận thanh toán");
		btnConfirmPayment.addActionListener(e -> {
			JOptionPane.showMessageDialog(paymentDialog, "Thanh toán thành công!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			paymentDialog.dispose(); // Đóng cửa sổ thanh toán sau khi thanh toán thành công
			JOptionPane.showMessageDialog(this, "Đặt chỗ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		});
		qrPanel.add(btnConfirmPayment, BorderLayout.SOUTH);

		paymentDialog.add(qrPanel);
		paymentDialog.setVisible(true);
	}

}
