package qubole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Friend_manager {

	ArrayList<User_friend> user_datalist;
	User_friend user_data;

	//main method: method calling and file path is included in file
	public static void main(String[] args) throws IOException {
		String file = "C:/Users/gandh/Desktop/test.txt";
		Friend_manager fm = new Friend_manager();
		fm.datagetter(file);
		fm.haveMutualFriends(1234, 1234);
		System.out.println(fm.areFriends(1234, 120));
		System.out.println(fm.getFriends(1234));
		System.out.println(fm.haveMutualFriends(1234, 1234));
		System.out.println(fm.getMutualFriendCount(1234, 23));
		System.out.println(fm.getPotentialFriends(1234, 4));

	}
	
	// in datagetter the file is read and stored in arraylist as objects
	void datagetter(String file) throws IOException {
		File f = new File(file);
		BufferedReader bfr = new BufferedReader(new FileReader(f));
		String s = bfr.readLine();
		user_datalist = new ArrayList<>();
		while (s != null) {

			user_data = new User_friend();
			user_data.friends = new ArrayList<>();
			StringTokenizer st = new StringTokenizer(s, " ");
			user_data.setUser_id(Integer.parseInt(st.nextToken()));
			StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");
			while (st1.hasMoreTokens()) {
				user_data.friends.add(Integer.parseInt(st1.nextToken()));
			}
			System.out.println(user_data.toString());
			user_datalist.add(user_data);
			s = bfr.readLine();
		}
	}

	//in this method from the arraylist, identify the object wrt argument user_id and return stored friend list from object
	List<Integer> getFriends(int user_a) {

		for (User_friend user_friend : user_datalist) {
			if (user_friend.user_id == user_a)
				return user_friend.friends;
		}

		return null;

	}

	//in this method assuming dataset to be complete a validation between first id and checking second id with its friends list
	boolean areFriends(int user_a, int user_b) {
		for (User_friend user_friend : user_datalist) {
			if (user_friend.user_id == user_a) {

				if (user_friend.friends.indexOf(user_b) >= 0) {
					System.out.println("true");
					return true;

				}

			}
		}

		return false;
	}

	//used a hashmap and added friends of both inpur user_ids to it... so if the hashmap size is less than both friends lists then
	//mutual friends exist (validated if user_a = user_b)
	boolean haveMutualFriends(int user_a, int user_b) {
		if (user_a != user_b) {
			HashMap<Integer, Integer> mutualchecker = new HashMap<>();
			int count = 0;
			for (User_friend user_friend : user_datalist) {
				if (user_friend.user_id == user_a || user_friend.user_id == user_b) {
					for (int i : user_friend.friends) {
						mutualchecker.put(i, i);

					}
					count = count + user_friend.friends.size();
				}
			}

			if (mutualchecker.size() < count) {

				return true;
			}

			return false;
		} else
			return true;

	}

	//similar haveMutualFriends but return argument is changed to diff of hasghmap size and sum of both argument friend list size
	int getMutualFriendCount(int user_a, int user_b) {

		HashMap<Integer, Integer> mutualchecker = new HashMap<>();
		int count = 0;

		for (User_friend user_friend : user_datalist) {
			if (user_friend.user_id == user_a || user_friend.user_id == user_b) {
				for (int i : user_friend.friends) {
					mutualchecker.put(i, i);

				}
				count = count + user_friend.friends.size();
			}
		}

		if (user_a != user_b) {
			return count - mutualchecker.size();
		} else
			return count;
	}

	//getMutualFriendCount is run for given user_id and to all other user_ids only if they are !areFriends, haveMutualFriends
	//this count, ids are added to an new arraylist and sorted by collections
	//then based on count argument the list of ids is returned
	List<Integer> getPotentialFriends(int user_a, int count) {
		ArrayList<User_friend> potential_finder = new ArrayList<>();
		ArrayList<Integer> list_return = new ArrayList<>();
		for (User_friend user_friend : user_datalist) {
			if (user_friend.user_id != user_a && !areFriends(user_a, user_friend.user_id)
					&& haveMutualFriends(user_a, user_friend.user_id)) {
				User_friend pot_frnd_finder = new User_friend();
				pot_frnd_finder.user_id = user_friend.user_id;
				pot_frnd_finder.count = getMutualFriendCount(user_a, user_friend.user_id);
				potential_finder.add(pot_frnd_finder);
			}
		}

		Collections.sort(potential_finder, new Comparator<User_friend>() {

			@Override
			public int compare(User_friend o1, User_friend o2) {

				if (o1.count > o2.count)
					return -1;
				else if (o1.count < o2.count)
					return 1;
				else
					return 0;
			}
		});
		if (potential_finder.size() != 0) {
			if (potential_finder.size() >= count) {
				for (int i = 0; i < count; i++) {
					list_return.add(potential_finder.get(i).user_id);
				}
			}
			else{
				for (User_friend potuser_friend : potential_finder) {
					list_return.add(potuser_friend.user_id);
				}
			}
			return list_return;

		} else
			return null;

	}
}
