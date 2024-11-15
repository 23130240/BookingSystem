package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public static void main(String[] args) {
		new PaymentDialog(null);
	}
}
