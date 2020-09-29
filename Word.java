import org.jetbrains.annotations.NotNull;

import java.util.ListIterator;

public class Word implements Comparable<Word> {
    String word;
    Meaning meaning;
    String phonetics;

    public Word(String word, Meaning meaning, String phonetics) {
        this.word = word;
        this.meaning = meaning;
        this.phonetics = phonetics;
    }

    public Word(String word) {
        this.word = word;
    }

    public Word() {
        this.meaning=new Meaning();
    }

    public String getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = new Meaning();
    }
    public void addToMeaning(String s){
        this.meaning.Add(s);
    }

    @Override
    public int compareTo(@NotNull Word word) {
        return word.getWord().compareTo(this.getWord());
    }

    @Override
    public String toString() {
       return this.word;
    }
}
