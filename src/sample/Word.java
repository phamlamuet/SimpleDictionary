package sample;

import java.util.Objects;

public class Word implements Comparable<Word> {
    String word;
    Meaning meaning;
    String phonetics;


    public Word() {
        this.meaning = new Meaning();
    }

    public Word(String word) {
        this.meaning = new Meaning();
        this.word = word;


    }

    public Word(String word, Meaning meaning, String phonetics) {
        this.word = word;
        this.meaning = meaning;
        this.phonetics = phonetics;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning.toString();
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = new Meaning();
    }

    public String getPhonetics() {
        return phonetics;
    }



    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
    }

    public void addToMeaning(String s) {
        this.meaning.Add(s);
    }

    @Override
    public int compareTo(Word word) {
        // return word.getWord().compareTo(this.getWord());
        return this.getWord().compareTo(word.getWord());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(getWord(), word1.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord());
    }

    @Override
    public String toString() {
        return this.word;
    }
}
