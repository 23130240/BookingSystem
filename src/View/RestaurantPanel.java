package View;	

import javax.swing.*;
import java.awt.*;

public class RestaurantPanel extends JPanel {
    public RestaurantPanel() {
        setLayout(new GridLayout(0, 3, 10, 10)); // Hiển thị 3 cột
        // Giả lập dữ liệu cho các nhà hàng
        String[][] restaurants = {
            {"Tám Riêu - Nguyễn Tri Phương", "466 Nguyễn Tri Phương, P.9, Q.10", "Giảm 10%", "Gọi Món Việt", "$$$"},
            {"Nam Phương Lầu - Nguyễn Thị Diệu", "Số 19 - 19B Nguyễn Thị Diệu, Q.3", "Giảm 30%", "Gọi món Á - Âu", "$$$$"},
            {"Rạng Đông - Cách Mạng Tháng 8", "Số 81 - 83 Cách Mạng Tháng 8, Q.1", "Giảm tới 20%", "Gọi món Á - Âu", "$$$"},
            // Thêm các nhà hàng khác
        };

        for (String[] restaurant : restaurants) {
            add(createRestaurantCard(restaurant));
        }
    }

    private JPanel createRestaurantCard(String[] restaurantInfo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Tạo các thành phần hiển thị thông tin nhà hàng
        JLabel nameLabel = new JLabel(restaurantInfo[0]);
        JLabel addressLabel = new JLabel(restaurantInfo[1]);
        JLabel discountLabel = new JLabel(restaurantInfo[2]);
        JLabel typeLabel = new JLabel(restaurantInfo[3]);
        JLabel priceLabel = new JLabel(restaurantInfo[4]);
        JButton bookButton = new JButton("Đặt chỗ");

        // Sắp xếp các thành phần vào panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(addressLabel);
        infoPanel.add(discountLabel);
        infoPanel.add(typeLabel);
        infoPanel.add(priceLabel);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(bookButton, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Danh sách nhà hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new RestaurantPanel());
        frame.setVisible(true);
    }
}
