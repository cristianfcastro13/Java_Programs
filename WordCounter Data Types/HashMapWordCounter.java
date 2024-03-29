package DataStructures;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;

/**
 * WordCounterMain class
 * @author TMyatt & CCastro
 *
 */
public class HashMapCounter {
	static HashMap<String, Integer> wordsHash = new HashMap<String, Integer>();
	
	/**
	 * addWordToHashMap - add word to list or increment counter
	 * @param inWord - the word to add to the list
	 * @return number of words in the list
	 */
	static int addWordToHashMap(String inWord) {
		
		//If word found in hash list, add 1 to the count
		if(wordsHash.containsKey(inWord) == true) {
			wordsHash.replace(inWord, wordsHash.get(inWord) + 1);
		}
		
		//If Word is not in hash list, add inWord to hash list with value (count) of 1
		wordsHash.putIfAbsent(inWord, 1); 
		
		return wordsHash.size();
	}

	/**
	 * printWordList - dump the list if count > inMinimum
	 */
	@SuppressWarnings("rawtypes")
	static void printWordList(int inMinimum) {
		
		if (wordsHash.size() > 0) {
			System.out.println("Total words: " + wordsHash.size());
			Iterator hashIterator = wordsHash.entrySet().iterator();  //Create iterator for hash list
			
			//Iterator loop that prints an element if the value present in it is equal or more than inMinimum
			while (hashIterator.hasNext()) {
				Entry n = (Entry)hashIterator.next();
				int count = (int) (n.getValue());
				if (count >= inMinimum) {
					System.out.println(n.getKey() + ":" + count);
				}
			}//END while
		}//END if
	}

	/**
	 * getVerse - Parse and return only the Psalms verse
	 * @param inLine - the line to parse on tab delimiter
	 * @return right half of tab delimited string
	 */
	static String getVerse(String inLine) {
		String[] ver = inLine.split("\t");
		return ver[1];
	}

	/**
	 * WordCounter main()
	 * @param args
	 */
	public static void main(String[] args) {
		Instant start = Instant.now(); //Timer starts here
		try {
			// Open the required text file for sequential read
			Scanner inputFile = new Scanner (new File(args[0]));
			// Check for EOF, read the next line, and display it
			while (inputFile.hasNextLine()) {
				String inLine, verse;
				String[] verseParsed;
				
				inLine = inputFile.nextLine();
				verse = getVerse(inLine);
				verseParsed = verse.split("[ :;,.'!?()-]+");
				for (String s: verseParsed) {
					addWordToHashMap(s.toLowerCase());
				}
			}		
			// Close the required file when EOF is reached
			inputFile.close();
			printWordList(100000);
		} // END try
		catch (Exception e) {
			// All Exceptions come here for graceful termination
			System.out.println("PsalmsReaderMain Error: " + e);
		} // END catch
		Instant stop = Instant.now(); //Timer stops here
		long timeElapsed = Duration.between(start, stop).toMillis(); //The time is converted to milliseconds and then assigned to a variable for output 
		System.out.println("Time(ms): " + timeElapsed);
	} // END main
} // END class
