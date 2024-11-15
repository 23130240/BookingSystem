package Model;

import java.util.ArrayList;

public class BookingService {
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private String currentUser;

    // Lấy thông tin người dùng hiện tại
    public String getCurrentUser() {
        return currentUser;
    }

    // Cập nhật người dùng hiện tại
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    // Thêm booking mới vào danh sách
    public void addBooking(String phone, String numPeople, String date, String time) {
        if (currentUser != null) {
            Booking newBooking = new Booking(currentUser, phone, numPeople, date, time);
            bookingList.add(newBooking);
        } else {
            System.out.println("Vui lòng đăng nhập để đặt chỗ.");
        }
    }

    // Lấy thông tin booking của người dùng hiện tại
    public Booking getBookingForCurrentUser() {
        for (Booking booking : bookingList) {
            if (booking.getUsername().equals(currentUser)) {
                return booking;
            }
        }
        return null;
    }

    // Kiểm tra trạng thái thanh toán
    public boolean isBookingPaid() {
        Booking booking = getBookingForCurrentUser();
        return booking != null && booking.isPaid();
    }

    // Xử lý thanh toán và cập nhật trạng thái
    public void processPayment() {
        Booking booking = getBookingForCurrentUser();
        if (booking != null && !booking.isPaid()) {
            booking.setPaid(true);
            System.out.println("Thanh toán thành công cho đặt chỗ của " + currentUser);
        } else {
            System.out.println("Không có đặt chỗ hoặc đã thanh toán.");
        }
    }
}

