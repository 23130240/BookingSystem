package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Model.BookingService;
import Model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookingSystem2 extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BookingService bookingService;
    private JLabel lblUserInfo; // Label để hiển thị tên người dùng
	private ArrayList<User> userList;

    public BookingSystem2() {
        bookingService = new BookingService();
        
        setTitle("Booking System");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 5)); // Đường viền chính
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        userList = new ArrayList<>(); 
        

        // Thêm các trang vào CardLayout
        mainPanel.add(createLoginPanel(), "Login");
		mainPanel.add(createRegisterPanel(), "Register");
        mainPanel.add(createBookingPanel(), "Booking");
        
     // Thanh điều hướng
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navBar.setBackground(new Color(41, 128, 185));
        navBar.setPreferredSize(new Dimension(1200, 50));
        
        JButton btnLoginNav = createNavButton("Đăng nhập");
        JButton btnRegisterNav = createNavButton("Đăng ký");
        JButton btnBookingNav = createNavButton("Đặt chỗ");
        btnLoginNav.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        btnRegisterNav.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        btnBookingNav.addActionListener(e -> cardLayout.show(mainPanel, "Booking"));
        
        navBar.add(btnLoginNav);
        navBar.add(btnRegisterNav);
        navBar.add(btnBookingNav);
        
        // Tạo JLabel để hiển thị tên người dùng
        lblUserInfo = new JLabel(""); // Nội dung mặc định
        lblUserInfo.setFont(new Font("Serif", Font.BOLD, 16));
        lblUserInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        add(navBar, BorderLayout.NORTH);
        // Thêm lblUserInfo vào thanh tiêu đề hoặc vị trí mong muốn
        add(lblUserInfo, BorderLayout.EAST); // Thêm vào phần trên của giao diện

        add(mainPanel);
        cardLayout.show(mainPanel, "Register"); // Bắt đầu với trang đăng nhập

        setVisible(true);
    }
    // Tạo nút điều hướng
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        return button;
    }
    // Panel đăng nhập
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        JLabel lblLoginTitle = new JLabel("Đăng nhập", JLabel.CENTER);
        lblLoginTitle.setFont(new Font("Serif", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        JTextField txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Mật khẩu:");
        JPasswordField txtPassword = new JPasswordField();

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                // Kiểm tra đăng nhập hợp lệ
                if (isValidUser(username, password)) {
                    bookingService.setCurrentUser(username);
                    lblUserInfo.setText("Xin chào, " + username); // Cập nhật tên người dùng
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Booking");
                } else {
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Sai thông tin đăng nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(lblLoginTitle, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(btnLogin, BorderLayout.SOUTH);

        return loginPanel;
    }

 // Panel đăng ký
    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new BorderLayout());
        JLabel lblRegisterTitle = new JLabel("Đăng ký", JLabel.CENTER);
        lblRegisterTitle.setFont(new Font("Serif", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        JTextField txtUsername = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
		JTextField txtEmail = new JTextField();
        JLabel lblPassword = new JLabel("Mật khẩu:");
        JPasswordField txtPassword = new JPasswordField();

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
		formPanel.add(lblEmail);
		formPanel.add(txtEmail);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Vui lòng nhập đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    userList.add(new User(username, email,password));
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Login"); // Quay lại trang đăng nhập
                }
            }
        });

        registerPanel.add(lblRegisterTitle, BorderLayout.NORTH);
        registerPanel.add(formPanel, BorderLayout.CENTER);
        registerPanel.add(btnRegister, BorderLayout.SOUTH);

        return registerPanel;
    }
 // Kiểm tra tài khoản hợp lệ
    private boolean isValidUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra tên đăng nhập đã tồn tại chưa
    private boolean isUsernameTaken(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    // Panel đặt chỗ
    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel(new BorderLayout());
        JLabel lblBookingTitle = new JLabel("Đặt chỗ", JLabel.CENTER);
        lblBookingTitle.setFont(new Font("Serif", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel lblPhone = new JLabel("Số điện thoại:");
        JTextField txtPhone = new JTextField();
        JLabel lblNumPeople = new JLabel("Số lượng người:");
        JTextField txtNumPeople = new JTextField();
        JLabel lblDate = new JLabel("Ngày đặt:");
        JTextField txtDate = new JTextField();
        JLabel lblTime = new JLabel("Thời gian:");
        JTextField txtTime = new JTextField();

        formPanel.add(lblPhone);
        formPanel.add(txtPhone);
        formPanel.add(lblNumPeople);
        formPanel.add(txtNumPeople);
        formPanel.add(lblDate);
        formPanel.add(txtDate);
        formPanel.add(lblTime);
        formPanel.add(txtTime);

        JButton btnBook = new JButton("Đặt chỗ");
        btnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thêm booking vào danh sách
                bookingService.addBooking(txtPhone.getText(), txtNumPeople.getText(), txtDate.getText(), txtTime.getText());
                JOptionPane.showMessageDialog(BookingSystem2.this, "Đặt chỗ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton btnPayment = new JButton("Thanh toán");
        btnPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra thanh toán
                if (bookingService.isBookingPaid()) {
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Đặt chỗ đã thanh toán!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    bookingService.processPayment();
                    JOptionPane.showMessageDialog(BookingSystem2.this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnBook);
        buttonPanel.add(btnPayment);

        bookingPanel.add(lblBookingTitle, BorderLayout.NORTH);
        bookingPanel.add(formPanel, BorderLayout.CENTER);
        bookingPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bookingPanel;
    }

    public static void main(String[] args) {
        new BookingSystem2();
    }
}
