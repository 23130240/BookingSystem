package View;	

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
    
//    private JPanel createRecommendedPanel() {
//        JPanel recommendedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Layout để hiển thị các mục
//        recommendedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Tạo danh sách các mục gợi ý
//        String[][] items = {
//            {"Ưu đãi nhà hàng Lẩu Nướng tại TP.HCM", "data/lau.jpg", "16", "Lẩu Nướng"},
//            {"Ưu đãi nhà hàng Việt Nam tại Tp.HCM", "data/nhieumon.jpg", "38", "Việt Nam"},
//            {"Ưu đãi nhà hàng Chay tại Tp.HCM", "data/chay.jpg", "6", "Chay"},
//            {"Khám phá tất cả Nhà hàng có ưu đãi tại Tp.HCM", "data/khampha.jpg", "152", "Khám phá"},
//            {"Các Nhà hàng Hải Sản Tp.HCM tại nhiều mức giá", "data/buffet.jpg", "5", "Hải sản"}
//        };
//
//        for (String[] item : items) {
//            recommendedPanel.add(createItemPanel(item[0], item[1], item[2], item[3]));
//        }
//
//        return recommendedPanel;
//    }
//
//    private JPanel createItemPanel(String title, String imagePath, String score, String type) {
//        JPanel itemPanel = new JPanel(new BorderLayout());
//        itemPanel.setPreferredSize(new Dimension(200, 250));
//        itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//
//        // Thêm hình ảnh
//        JLabel lblImage = new JLabel();
//        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
//        try {
//            Image img = ImageIO.read(new File(imagePath));
//            ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 120, Image.SCALE_SMOOTH));
//            lblImage.setIcon(icon);
//        } catch (IOException ex) {
//            lblImage.setText("Không tải được ảnh");
//        }
//        lblImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        lblImage.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                showRestaurantDetail(type); // Hiển thị trang chi tiết
//            }
//        });
//
//        // Thêm tiêu đề
//        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
//        lblTitle.setFont(new Font("Serif", Font.BOLD, 14));
//        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        lblTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        lblTitle.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                showRestaurantDetail(type); // Hiển thị trang chi tiết
//            }
//        });
//
//        // Thêm điểm
//        JLabel lblScore = new JLabel("Số điểm đến: " + score, SwingConstants.CENTER);
//        lblScore.setFont(new Font("SansSerif", Font.PLAIN, 12));
//
//        // Sắp xếp các thành phần
//        itemPanel.add(lblImage, BorderLayout.NORTH);
//        itemPanel.add(lblTitle, BorderLayout.CENTER);
//        itemPanel.add(lblScore, BorderLayout.SOUTH);
//
//        return itemPanel;
//    }
//
//    private void showRestaurantDetail(String type) {
//        // Chuyển sang trang chi tiết của nhà hàng tương ứng
//        JOptionPane.showMessageDialog(this, "Xem thông tin chi tiết nhà hàng: " + type, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//
//        // Hoặc chuyển CardLayout sang trang chi tiết:
//        cardLayout.show(mainPanel, "RestaurantDetail"); // Cần thêm panel "RestaurantDetail" trước đó
//    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Danh sách nhà hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new RestaurantPanel());
        frame.setVisible(true);
    }
}
