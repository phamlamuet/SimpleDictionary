import javax.swing.*;

public class BuildGUI {
    JFrame jFrame;

    public void go() {
        jFrame=new JFrame("Dictionary");
        jFrame.setSize(500,700);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
