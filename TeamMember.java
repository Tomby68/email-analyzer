package for_assignment3;

import java.util.LinkedList;

public class TeamMember {
	String email;
	LinkedList<String> adjacency;
	int received;
	int index;
	
	public TeamMember(String theEmail, int theIndex) {
		email = theEmail;
		adjacency = new LinkedList<String>();
		received = 0;
		index = theIndex;
	}
}
