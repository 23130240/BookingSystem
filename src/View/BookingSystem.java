package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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
	private ArrayList<User> usersList;  // Danh sách người dùng
	private BookingService bookingService;
	private JLabel lblUserInfo; // Label để hiển thị tên người dùng
	  
	public BookingSystem() {
		setTitle("Booking System");
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		usersList = new ArrayList<>(); 
		// Sử dụng CardLayout để chuyển đổi giữa các trang
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		// Thêm các trang (Home, Login, và Booking) vào CardLayout
		mainPanel.add(createHomePanel(), "Home");
		mainPanel.add(createBookingPanel(), "Booking");
		
		bookingService = new BookingService();
		
		// Tạo JLabel để hiển thị tên người dùng
        lblUserInfo = new JLabel("Tài khoản"); // Nội dung mặc định
        lblUserInfo.setFont(new Font("Serif", Font.BOLD, 16));
        lblUserInfo.setHorizontalAlignment(SwingConstants.RIGHT);

        // Thêm lblUserInfo vào thanh tiêu đề hoặc vị trí mong muốn
        add(lblUserInfo, BorderLayout.NORTH); // Thêm vào phần trên của giao diện
		
		add(mainPanel);
		cardLayout.show(mainPanel, "Home"); // Bắt đầu với trang Home

		setVisible(true);
	}

	private JPanel createHomePanel() {
		JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        BackgroundPanel backGround = new BackgroundPanel("C:/Users/admin/Pictures/Gt.jng.png");
        homePanel.add(backGround);

        JLabel lblWelcome = new JLabel("Nhà hàng Fuuqa", JLabel.CENTER);
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel();
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.addActionListener(e -> showLoginDialog());
        
        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.addActionListener(e -> showRegisterDialog());

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        homePanel.add(lblWelcome, BorderLayout.NORTH);
        homePanel.add(buttonPanel, BorderLayout.SOUTH);

        return homePanel;
	}

//	private JPanel createLoginPanel() {
//		
//		JPanel loginPanel = new JPanel(new BorderLayout());
//		loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//		loginPanel.setBackground(new Color(255, 255, 255));
//
//		JLabel lblLoginTitle = new JLabel("Đăng nhập", JLabel.CENTER);
//		lblLoginTitle.setFont(new Font("Serif", Font.BOLD, 22));
//
//		JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
//		formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//
//		JLabel lblUsername = new JLabel("Tên đăng nhập:");
//		JTextField txtUsername = new JTextField();
//
//		JLabel lblPassword = new JLabel("Mật khẩu:");
//		JPasswordField txtPassword = new JPasswordField();
//
//		formPanel.add(lblUsername);
//		formPanel.add(txtUsername);
//		formPanel.add(lblPassword);
//		formPanel.add(txtPassword);
//
//		JButton btnSubmit = new JButton("Đăng nhập");
//		btnSubmit.addActionListener(e -> {
//			String username = txtUsername.getText();
//			String password = new String(txtPassword.getPassword());
//
//			if (validateLogin(username, password)) {
//				JOptionPane.showMessageDialog(loginPanel, "Đăng nhập thành công!", "Thông báo",
//						JOptionPane.INFORMATION_MESSAGE);
//				cardLayout.show(mainPanel, "Booking");
//			} else {
//				JOptionPane.showMessageDialog(loginPanel, "Sai tên đăng nhập hoặc mật khẩu.", "Lỗi",
//						JOptionPane.ERROR_MESSAGE);
//			}
//		});
//
//		loginPanel.add(lblLoginTitle, BorderLayout.NORTH);
//		loginPanel.add(formPanel, BorderLayout.CENTER);
//		loginPanel.add(btnSubmit, BorderLayout.SOUTH);
//
//		return loginPanel;
//	}
	// Hiển thị màn hình đăng nhập đè lên
    private void showLoginDialog() {
        JDialog loginDialog = new JDialog(this, "Đăng nhập", true);
        loginDialog.setSize(300, 200);
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setLayout(new BorderLayout());

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
                JOptionPane.showMessageDialog(loginDialog, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loginDialog.dispose(); // Đóng cửa sổ đăng nhập
                bookingService.setCurrentUser(username);
                lblUserInfo.setText("Xin chào, " + username); // Cập nhật tên người dùng
                cardLayout.show(mainPanel, "Booking");
                // Chuyển đến màn hình tiếp theo sau khi đăng nhập thành công (nếu cần)
                
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Sai tên đăng nhập hoặc mật khẩu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginDialog.add(lblLoginTitle, BorderLayout.NORTH);
        loginDialog.add(formPanel, BorderLayout.CENTER);
        loginDialog.add(btnSubmit, BorderLayout.SOUTH);

        loginDialog.setVisible(true);
    }

    // Hiển thị màn hình đăng ký đè lên
    private void showRegisterDialog() {
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
                JOptionPane.showMessageDialog(registerDialog, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
                 JOptionPane.showMessageDialog(bookingPanel, "Vui lòng điền đầy đủ thông tin.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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

//	private JPanel createRegisterPanel() {
//		JPanel registerPanel = new JPanel(new BorderLayout());
//		registerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//		registerPanel.setBackground(new Color(255, 255, 255));
//
//		JLabel lblRegisterTitle = new JLabel("Đăng ký", JLabel.CENTER);
//		lblRegisterTitle.setFont(new Font("Serif", Font.BOLD, 22));
//
//		JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
//		formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//
//		JLabel lblUsername = new JLabel("Tên đăng nhập:");
//		JTextField txtUsername = new JTextField();
//
//		JLabel lblEmail = new JLabel("Email:");
//		JTextField txtEmail = new JTextField();
//
//		JLabel lblPassword = new JLabel("Mật khẩu:");
//		JPasswordField txtPassword = new JPasswordField();
//
//		JLabel lblConfirmPassword = new JLabel("Xác nhận mật khẩu:");
//		JPasswordField txtConfirmPassword = new JPasswordField();
//
//		formPanel.add(lblUsername);
//		formPanel.add(txtUsername);
//		formPanel.add(lblEmail);
//		formPanel.add(txtEmail);
//		formPanel.add(lblPassword);
//		formPanel.add(txtPassword);
//		formPanel.add(lblConfirmPassword);
//		formPanel.add(txtConfirmPassword);
//
//		JButton btnSubmit = new JButton("Đăng ký");
//		btnSubmit.addActionListener(e -> {
//			String username = txtUsername.getText();
//			String email = txtEmail.getText();
//			String password = new String(txtPassword.getPassword());
//			String confirmPassword = new String(txtConfirmPassword.getPassword());
//
//			if (password.equals(confirmPassword)) {
//				usersList.add(new User(username, email, password));
//				JOptionPane.showMessageDialog(registerPanel, "Đăng ký thành công!", "Thông báo",
//						JOptionPane.INFORMATION_MESSAGE);
//				cardLayout.show(mainPanel, "Login");
//			} else {
//				JOptionPane.showMessageDialog(registerPanel, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//			}
//		});
//
//		registerPanel.add(lblRegisterTitle, BorderLayout.NORTH);
//		registerPanel.add(formPanel, BorderLayout.CENTER);
//		registerPanel.add(btnSubmit, BorderLayout.SOUTH);
//
//		return registerPanel;
//	}

	// Lớp BackgroundPanel để hiển thị ảnh nền
	class BackgroundPanel extends JPanel {
		private Image backgroundImage;

		public BackgroundPanel(String filePath) {
			try {
				backgroundImage = ImageIO.read(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}
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
            Image qrImage = ImageIO.read(new File("C:/Users/admin/Pictures/Qr.png"));
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
            JOptionPane.showMessageDialog(paymentDialog, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            paymentDialog.dispose(); // Đóng cửa sổ thanh toán sau khi thanh toán thành công
            JOptionPane.showMessageDialog(this, "Đặt chỗ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
        qrPanel.add(btnConfirmPayment, BorderLayout.SOUTH);

        paymentDialog.add(qrPanel);
        paymentDialog.setVisible(true);
    }

	
}
