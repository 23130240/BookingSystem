package Model;

import java.util.ArrayList;

public class UserList {
	private ArrayList<User> usersList; // Danh sách người dùng

	public UserList(ArrayList<User> usersList) {
		super();
		this.usersList = usersList;
	}

	public ArrayList<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(ArrayList<User> usersList) {
		this.usersList = usersList;
	}

	public void add(User user) {
		this.usersList.add(user);
	}

	public void remove(User user) {
		this.usersList.remove(user);
	}
}
