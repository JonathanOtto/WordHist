
/**
 * WordsHistogram reads in words from "input.txt" and generates a histogram of
 * the words used (ignoring standard punctuation, and differences in case), then outputs to "output.txt"
 * 
 * @author Jonathan Otto
 * 5/19/2018
 */
import java.util.Scanner;
import java.util.ArrayList;
public class WordsHistogram
{
    public static void main(String[] args) throws Exception
    {
        //Files and variable setup
        java.io.File infile = new java.io.File("input.txt");
        java.io.File outfile = new java.io.File("output.txt");
        java.io.PrintWriter output = new java.io.PrintWriter(outfile);
        Scanner input = new Scanner(infile);                      
        ArrayList<String> wordList = new ArrayList<>();         //List that contains the words
        ArrayList<String> occurrenceList = new ArrayList<>();   //List that contains the number of word occurrences
        int wordListSize;                                       //Will keep the wordList original size value
        int tempIndexValue;                                     //Used to hold the index location from one list to the other
        int longestWord = 0;                                    //Contains the length of the longest word. Used for shifting
        int mostOccurrenceIndex = 0;                            //Defaults as first word occuring the most.
        String readWord;                                        //Will hold current input word

        //Input section
        //Read the file until it contains no additional words
        while(input.hasNext())
        {
            readWord = input.next();

            //Make sure the word is lowercase
            readWord = readWord.toLowerCase();

            //Check to see if the readWord has a punctuation ending (a non-letter/non-number ending)
            boolean punctuationCleared = false;
            while(!punctuationCleared)              //used in case of multiple punctuations (ex - until... or wow!!)
            {
                if (!Character.isLetterOrDigit(readWord.charAt(readWord.length()-1))) //if the last character in readWord is not a letter
                {
                    readWord = readWord.substring(0,readWord.length()-1);      //Remove the last character
                }
                else
                {
                    punctuationCleared = true;
                }
            }

            //If the word is not on wordList, add it and add an "=" to occurrenceList
            if (!wordList.contains(readWord))
            {
                wordList.add(readWord);
                occurrenceList.add("=");
            }
            else   //Otherwise, increase the occurrence count of the word by adding another "="
            {
                tempIndexValue = wordList.indexOf(readWord);
                occurrenceList.set(tempIndexValue,occurrenceList.get(tempIndexValue)+"=");
            }
        }

        //Output section
        //Set wordListSize
        wordListSize = wordList.size();
        
        //Find the longest word
        for (int i = 0; i < wordListSize; i++)
        {
            if (wordList.get(i).length() > longestWord)
            {
                longestWord = wordList.get(i).length();
            }
        }

        //For all words on wordList
        for (int i = 0; i < wordListSize; i++)
        {
            //Find the most occurring word
            for (int j = 1; j < occurrenceList.size(); j++)
            {
                if (occurrenceList.get(j).length() > occurrenceList.get(mostOccurrenceIndex).length())
                {
                    mostOccurrenceIndex = j;
                }
            }
            
            //Add the most occurring word | "=" occurring (occurrence amount) to the output and remove them from both lists
            //Using longestWord to shift the "|" to center
            for (int j = wordList.get(mostOccurrenceIndex).length(); j < longestWord; j++)
            {
                output.print(" ");
            }
            output.println(wordList.get(mostOccurrenceIndex) + " | " + occurrenceList.get(mostOccurrenceIndex) + " ("
            + occurrenceList.get(mostOccurrenceIndex).length() + ")");
            occurrenceList.remove(mostOccurrenceIndex);
            wordList.remove(mostOccurrenceIndex);
            
            //Reset mostOccurrenceIndex
            mostOccurrenceIndex = 0;
        }
        //Close the files
        input.close();
        output.close();
    }
}
