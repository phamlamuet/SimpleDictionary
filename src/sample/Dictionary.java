package sample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class Dictionary {
    private Vector<Word> words;

    public Vector<Word> getWords() {
        return words;
    }

    public void loadFromFile() {
        try {
            words = new Vector<>();
            Path path = Paths.get("E:\\Dictionary_JavaFx\\src\\sample\\dictionary.txt");
            List<String> dataList = Files.readAllLines(path);
            ListIterator<String> itr = dataList.listIterator();
            //code to read data from file to Vector
            Word word = new Word();
            int count = 0;
            while (itr.hasNext()) {
                String p = itr.next();

                if (p.startsWith("@")) {
                    count++;
                    word = new Word();
                    String[] part = p.split("/", 2);

                    String s2 = part[0].substring(1).trim();
                    if (s2.startsWith("'") || s2.startsWith("-") || s2.startsWith("(")) {
                        s2 = s2.substring(1, s2.length());
                    }
                    word.setWord(s2);

                    if (part.length < 2) {
                        word.setPhonetics("");
                    } else
                        word.setPhonetics("/" + part[1]);
                    while (itr.hasNext()) {
                        String p1 = itr.next();
                        //System.out.println(p1);
                        if (!p1.startsWith("@")) {
                            word.addToMeaning(p1);
                        } else {
                            words.add(word);
                            itr.previous();
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sortWords() {
        Collections.sort(words);
    }

    int search(String s) {
        Word toSearch = new Word(s.trim());
        int i = Collections.binarySearch(words, toSearch);
        return i;
    }
}
