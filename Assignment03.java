import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Assignment03 {
	public static void main(String[] args) {
		
		HashMap<String, TeamMember> emails = new HashMap<String, TeamMember>();
		Data d = new Data();
		File file = new File(args[0]);
		listFilesinDirectory(file, 0, emails, d);
		
		DisjointSet djs = new DisjointSet();
		djs.createSets(emails.size());
		
		createTeams(emails, djs);
		
		takeUserInput(emails, d, djs);
	}
	
	public static void listFilesinDirectory(File f, int tester, HashMap<String, TeamMember> emails, Data d) {
		for (File entry : f.listFiles()) {
			if (entry.isDirectory()) {
				listFilesinDirectory(entry, tester, emails, d);
			} else {
				readFile(entry, emails, d);
			}
		}
	}
	
	
	private static void readFile(File fileName, HashMap<String, TeamMember> emails, Data d) {
		try {
			Reader r = new FileReader(fileName);
			BufferedReader br = new BufferedReader(r);
			String line = br.readLine();
			boolean hasFrom = false;
			boolean hasTo = false;
			
			while (line != null) {
				String[] next = line.split(" ");
				if (next.length > 0) {
					if (next[0].equals("From:")) {
						hasFrom = true;
						String email = extractEmail(line);
						if (email != null) {
							TeamMember individual;
							if (emails.get(email) == null) {
								emails.put(email, new TeamMember(email, d.currIndex++));
							} 
							individual = emails.get(email);
							line = br.readLine();
							next = line.split(" ");
							
							if (next[0].equals("To:")) {
								hasTo = true;
								boolean moreEmails = true;
								while (moreEmails) {
									for (int i = 0; i < next.length; i++) {
										email = extractEmail(next[i]);
										if (email != null) {
											if (emails.get(email) == null) {
												emails.put(email, new TeamMember(email, d.currIndex++));
											}
											emails.get(email).received++;
											individual.adjacency.add(email);
										}
									}
									
									if (next[next.length - 1].charAt(next[next.length - 1].length() - 1) != ',') {
										moreEmails = false;
									} else {
										line = br.readLine();
										next = line.split(" ");
									}
								}
							}
						}
					}
				}
				line = br.readLine();
			}
			br.close();
			if (hasFrom && hasTo) {
				d.numEmails++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static String extractEmail(String input) {
	    Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(input);
	    if (matcher.find()) {
	        return matcher.group(1);
	    }
	    return null;
	}
	
	public static void createTeams(HashMap<String, TeamMember> emails, DisjointSet djs) {
		Set<String> set = emails.keySet();
		
		for (String email : set) {
			LinkedList<String> adjacency = emails.get(email).adjacency;
			for (int i = 0; i < adjacency.size(); i++) {
				if (djs.find(emails.get(email).index) != djs.find(emails.get(adjacency.get(i)).index)) {
					djs.union(emails.get(email).index, emails.get(adjacency.get(i)).index);
				}
			}
		}
	}
	
	public static void takeUserInput(HashMap<String, TeamMember> emails, Data d, DisjointSet djs) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Email address of the individual (or EXIT to quit) ");
		String input = scnr.nextLine();
		Object[] addresses = emails.keySet().toArray();
		while (!input.equals("EXIT")) {
			boolean found = false;
			for (int i = 0; i < addresses.length; i++) {
				if (addresses[i].equals(input)) {
					found = true;
					System.out.println(input + " has sent messages to " + emails.get(addresses[i]).adjacency.size() + " others");
					System.out.println(input + " has received messages from " + emails.get(addresses[i]).received + " others");
					System.out.println(input + " is in a team with " + djs.getTeamSize(emails.get(input).index) + " individuals" );
				}
			}
			
			if (!found) {
				System.out.println("Email address (" + input + ") not found in the dataset");
			}
			System.out.println("Email address of the individual (or EXIT to quit)");
			input = scnr.nextLine();
		}
		scnr.close();
	}
}
