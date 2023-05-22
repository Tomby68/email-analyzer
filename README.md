# email-analyzer
 * Email Analyzer: Takes in a file as an argument and reads through all files within the given directory. The program reads
 * through all valid mail files and saves valid email addresses in a HashMap. The program also saves information
 * about each valid mail file. Then, the program asks the user to input a valid email address. If the email is in
 * the list of emails, the program prints out the number of emails that address has sent and received, as well as the
 * number of other members in that addresses team.

listFilesinDirectory: Reads through the given file recursively.
	 * @param f: A file
	 * @param emails: A HashMap of email addresses
	 * @param d: A variable of the type Data which stores useful global information about the emails
   Running time: O(n), where n is the number of files and directories.
readFile: Reads the given file using BufferedReader. Adds valid mail files to the emails HashMap and updates the address' 
	 information
	 * @param fileName: The name of the file
	 * @param emails: A HashMap of email addresses
	 * @param d: A variable of the type Data which stores useful global information about the emails
   Running time: O(n), where n is the number of lines in the file.
 extractEmail: Uses regex to search for a valid email address. Taken from David in the cs slack.
	 * @param input: The string in which to search for an email address
   Running time: O(1), because the function only does one check.
 createTeams: Using a disjoint set, unions any email addresses that send to each other.
	 * @param emails: A HashMap of email addresses
	 * @param djs: A disjoint set storing email addresses
   Running time: O(n^2), because we look through each email, then through the adjacency list for each email.
takeUserInput: Takes in and evaluates user input.
	 * @param emails: A HashMap of email addresses
	 * @param d: A variable of the Data type which stores useful global information about the emails
	 * @param djs: A disjoint set storing email addresses
   Running time: O(n), because the program will search through the HashMap of emails if the user inputs an email address
