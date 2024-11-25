package View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Test extends JFrame {
	private JPanel mainPanel;
	private CardLayout cardLayout;
	private AdminPanel adminPanel;
	private HomePanel home;

	public Test() {
		setTitle("Booking System");
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Sử dụng CardLayout để chuyển đổi giữa các trang
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		mainPanel.setPreferredSize(new Dimension(1000, 800));
		home = new HomePanel(this, mainPanel, cardLayout);
		adminPanel = new AdminPanel();
		adminPanel.setMainPanelAndLayout(mainPanel, cardLayout);

		mainPanel.add(home, "Home");
		mainPanel.add(adminPanel, "admin");
//		mainPanel.add(createRecommendedPanel(), "RestaurantDetail");
		mainPanel.add(createBookingPanel(), "Booking");

		JScrollPane scrollPane = new JScrollPane(mainPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Thanh cuộn dọc
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Thanh cuộn ngang

		add(scrollPane);
		cardLayout.show(mainPanel, "Home"); // Bắt đầu với trang Home

		setVisible(true);
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
