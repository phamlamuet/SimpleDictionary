import java.util.ArrayList;

public class Meaning {
    ArrayList<String> meaningList=new ArrayList<>();

    public void getMeaningList(){
        for (String s:meaningList){
            System.out.println(s);
        }
    }

    public void Add(String s){
        this.meaningList.add(s);
    }

}
