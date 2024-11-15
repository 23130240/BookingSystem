package Model;

public class Booking {
    private String username;
    private String phoneNumber;
    private String numPeople;
    private String date;
    private String time;
    private boolean isPaid; // Trạng thái thanh toán

    public Booking(String username, String phoneNumber, String numPeople, String date, String time) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.numPeople = numPeople;
        this.date = date;
        this.time = time;
        this.isPaid = false; // Mặc định là chưa thanh toán
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(String numPeople) {
		this.numPeople = numPeople;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}