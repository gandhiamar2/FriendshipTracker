package qubole;

import java.util.ArrayList;

public class User_friend {

	int user_id,count;
	ArrayList<Integer> friends;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public ArrayList<Integer> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<Integer> friends) {
		this.friends = friends;
	}
	@Override
	public String toString() {
		return "User_friend [user_id=" + user_id + ", friends=" + friends + "]";
	}
	
	
	
}
