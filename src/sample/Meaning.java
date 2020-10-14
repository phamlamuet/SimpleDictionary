package sample;

public class Meaning {
    private StringBuilder meaning;

    public Meaning() {
        this.meaning=new StringBuilder("");
    }

    public Meaning(StringBuilder meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return meaning+"";
    }

    public void Add(String s){
        meaning.append(s+"\n");
    }
}
