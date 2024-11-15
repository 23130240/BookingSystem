package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Model.BookingService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StylishBookingSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BookingService bookingService;
    private JLabel lblUserInfo;
    private ArrayList<User> userList = new ArrayList<>();

    public StylishBookingSystem() {
        bookingService = new BookingService();

        setTitle("Stylish Booking System");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
      //  setUndecorated(true); // Tắt viền mặc định của cửa sổ
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 5)); // Đường viền chính

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

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

        // Thông tin người dùng góc phải
        lblUserInfo = new JLabel("Tài khoản");
        lblUserInfo.setFont(new Font("Serif", Font.BOLD, 16));
        lblUserInfo.setForeground(Color.WHITE);
        navBar.add(lblUserInfo);

        // Thêm thành phần vào cửa sổ
        add(navBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "Login");

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

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(236, 240, 241));

        JLabel lblLoginTitle = new JLabel("Đăng nhập", JLabel.CENTER);
        lblLoginTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblLoginTitle.setForeground(new Color(52, 73, 94));
        loginPanel.add(lblLoginTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 100, 20, 100));
        formPanel.setBackground(new Color(236, 240, 241));

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setForeground(new Color(52, 73, 94));
        JTextField txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setForeground(new Color(52, 73, 94));
        JPasswordField txtPassword = new JPasswordField();

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setBorder(new EmptyBorder(10, 30, 10, 30));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                if (isValidUser(username, password)) {
                    bookingService.setCurrentUser(username);
                    lblUserInfo.setText("Xin chào, " + username);
                    JOptionPane.showMessageDialog(StylishBookingSystem.this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Booking");
                } else {
                    JOptionPane.showMessageDialog(StylishBookingSystem.this, "Sai thông tin đăng nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(formPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.add(btnLogin);
        loginPanel.add(buttonPanel, BorderLayout.SOUTH);

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new BorderLayout());
        registerPanel.setBackground(new Color(236, 240, 241));

        JLabel lblRegisterTitle = new JLabel("Đăng ký", JLabel.CENTER);
        lblRegisterTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblRegisterTitle.setForeground(new Color(52, 73, 94));
        registerPanel.add(lblRegisterTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 100, 20, 100));
        formPanel.setBackground(new Color(236, 240, 241));

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setForeground(new Color(52, 73, 94));
        JTextField txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setForeground(new Color(52, 73, 94));
        JPasswordField txtPassword = new JPasswordField();

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBackground(new Color(52, 152, 219));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRegister.setBorder(new EmptyBorder(10, 30, 10, 30));
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(StylishBookingSystem.this, "Vui lòng nhập đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(StylishBookingSystem.this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    userList.add(new User(username, password));
                    JOptionPane.showMessageDialog(StylishBookingSystem.this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        registerPanel.add(formPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.add(btnRegister);
        registerPanel.add(buttonPanel, BorderLayout.SOUTH);

        return registerPanel;
    }

    private boolean isValidUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameTaken(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.setBackground(new Color(236, 240, 241));

        JLabel lblBookingTitle = new JLabel("Trang Đặt Chỗ", JLabel.CENTER);
        lblBookingTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblBookingTitle.setForeground(new Color(52, 73, 94));

        bookingPanel.add(lblBookingTitle, BorderLayout.NORTH);
        return bookingPanel;
    }

    public static void main(String[] args) {
        new StylishBookingSystem();
    }
}

// Class User để lưu thông tin người dùng
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

