import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.TreeSet;

public class Dictionary {
    TreeSet<Word> words;

    public void loadWordFromFile() {
        try {
            words = new TreeSet<>();
            Path path = Paths.get("src/main/java/dictionary.txt");
            List<String> dataList = Files.readAllLines(path);
            ListIterator<String> itr = dataList.listIterator();
            //code to read data from file to Tree
            Word word = new Word();
            int count=0;
            while (itr.hasNext()) {
                String p = itr.next();
               // System.out.println(p);
                if (p.startsWith("@")) {
                    count++;
                    word = new Word();
                    String[] part = p.split("/", 2);

                    word.setWord(part[0].substring(1).trim());
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
          //  System.out.println(count);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public void insertFromCommandLine(String s) {
        Scanner input = new Scanner(System.in);

    }
}
