package Model;

import java.util.ArrayList;
import java.util.List;

public class AdminManager {
	private List<Admin> list = new ArrayList<>();

	public AdminManager() {
		Admin ad1 = new Admin("admin1", "123");
		list.add(ad1);
	}

	public List<Admin> getList() {
		return list;
	}

	public void setList(List<Admin> list) {
		this.list = list;
	}

	public boolean checkAdmin(String username, String password) {
		for (Admin admin : list) {
			if (admin.getUsername().equals(username) && admin.getPassword().equals(password)
					&& admin.getRole().equals("admin")) {
				return true;
			}
		}
		return false;
	}
}
