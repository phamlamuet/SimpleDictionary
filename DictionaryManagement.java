import java.util.Iterator;
import java.util.Scanner;

public class DictionaryManagement {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.loadWordFromFile();
        System.out.println("You are good to go :)),enter a word to translate");
        BuildGUI gui=new BuildGUI();
        gui.go();

        Iterator<Word> value = dictionary.words.iterator();
        Scanner input = new Scanner(System.in);
        String wordToTranslate = input.nextLine();
        System.out.println(dictionary.words.size());
        while (value.hasNext()){
            Word p=value.next();
            if(p.getWord().equals(wordToTranslate)){
                p.getMeaning().getMeaningList();
            }
        }
    }
}
