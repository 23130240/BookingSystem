package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PaymentDialog extends JDialog {
    private JButton btnConfirmPayment;
    private JButton btnCancelPayment;
    private JLabel lblPaymentInfo;

    public PaymentDialog(JFrame parent) {
        super(parent, "Thanh Toán", true);
        setLayout(new BorderLayout());

        lblPaymentInfo = new JLabel("Chọn phương thức thanh toán và xác nhận:");
        add(lblPaymentInfo, BorderLayout.NORTH);

        JPanel paymentOptions = new JPanel();
        JRadioButton rbCreditCard = new JRadioButton("Thẻ tín dụng");
        JRadioButton rbBankTransfer = new JRadioButton("Chuyển khoản ngân hàng");
        rbBankTransfer.addActionListener(e -> showPaymentQRCode());
        JRadioButton rbCash = new JRadioButton("Tiền mặt khi đến nhà hàng");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(rbCreditCard);
        paymentGroup.add(rbBankTransfer);
        paymentGroup.add(rbCash);
        paymentOptions.add(rbCreditCard);
        paymentOptions.add(rbBankTransfer);
        paymentOptions.add(rbCash);
        add(paymentOptions, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnConfirmPayment = new JButton("Xác nhận thanh toán");
        btnCancelPayment = new JButton("Hủy bỏ");
        btnPanel.add(btnConfirmPayment);
        btnPanel.add(btnCancelPayment);
        add(btnPanel, BorderLayout.SOUTH);

        btnConfirmPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PaymentDialog.this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Đóng dialog thanh toán
                // Gọi hàm xử lý đặt chỗ sau khi thanh toán thành công (nếu có)
            }
        });

        btnCancelPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng dialog thanh toán
            }
        });

        setSize(400, 200);
        setLocationRelativeTo(parent);
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
