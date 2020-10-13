package sample;

import TrieUtil.Trie;
import com.gtranslate.Audio;
import com.gtranslate.Language;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.time.Duration;
import java.util.*;


public class DictionaryManagement extends Application {
    VBox mainPanel;
    TextField wordField;
    Button searchButton;
    Button speakButton;
    ListView<String> recommendWordsList;
    Label meaningLabel;
    Trie trieSearch;
    List<String> wordList;
    ObservableList<String> observableList;
    Dictionary dictionary;
    VoiceManager vm;
    Voice voice;

    Button addAWord;
    Button deleteAWord;

    int selecedIndex = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        dictionary = new Dictionary();
        dictionary.loadFromFile();
        dictionary.sortWords();
        trieSearch = new Trie();
        wordList = new ArrayList<>();
        vm = VoiceManager.getInstance();
        voice = vm.getVoice("kevin16");
        voice.allocate();


        for (Word word : dictionary.getWords()) {
            trieSearch.insert(word.getWord());
            wordList.add(word.getWord());
        }

        //Create the input textfield
        wordField = new TextField();

        wordField.setPromptText("Enter a word to search");

        wordField.setOnKeyReleased(e -> textField_Go());


        searchButton = new Button("Go");

        searchButton.setOnAction(e -> btn_Go());

        speakButton = new Button();
        speakButton.setText("Speak");
        speakButton.setOnAction(event -> speakBtn_go());

        HBox input = new HBox(10);
        addAWord=new Button();
        addAWord.setText("Add");
        deleteAWord=new Button();
        deleteAWord.setText("Delete");
        input.getChildren().addAll(wordField, searchButton, speakButton,addAWord,deleteAWord);

        //Create meaning field


        meaningLabel = new Label("Meaning field");
        meaningLabel.setAlignment(Pos.CENTER);
        meaningLabel.setPrefWidth(550);

        ScrollPane sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setContent(meaningLabel);

        //Create recommendList


        observableList = FXCollections.observableList(wordList);
        System.out.println(observableList.size());
        recommendWordsList = new ListView<String>(observableList);
        recommendWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String str = recommendWordsList.getSelectionModel().getSelectedItems().toString();
                str = str.substring(1, str.length() - 1);
                //str = str.substring(1, str.length());
                wordField.setText(str);
            }
        });

        HBox output = new HBox(10);
        output.getChildren().addAll(recommendWordsList, sp);


        //Add all to the mainPanel
        mainPanel = new VBox();
        mainPanel.getChildren().addAll(input, output);

        Scene scene = new Scene(mainPanel, 700, 440);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();
        //-----------Gui is done-----------------//


    }

    private void btn_Go() {
        String s = wordField.getText();
        if (!s.equals("")) {
            int result = dictionary.search(s);
            System.out.println(s);

            if (result > 0) {
                meaningLabel.setText(dictionary.getWords().get(result).getPhonetics() + "\n" + dictionary.getWords().get(result).getMeaning());

            } else {
                List<String> youMeanList = new ArrayList<>();
                meaningLabel.setText("Not found,maybe you wrote it wrong,see recommend list beside");
                String sdx = Soundex.soundex(s);
                for (Word word : dictionary.getWords()) {
                    if (word.getWord().length() > 0)
                        if (Soundex.soundex(word.getWord()).equals(sdx)) {
                            youMeanList.add(word.getWord());
                        }
                }
                observableList.clear();
                if (youMeanList.size() > 15) {
                    observableList.addAll(youMeanList.subList(0, 15));
                } else observableList.addAll(youMeanList);
            }
        }
    }

    private void textField_Go() {
        String s = wordField.getText();
        List<String> a = trieSearch.autocomplete(s);
        observableList.clear();
        observableList.addAll(a);
    }

    private void speakBtn_go() {
        voice.speak(wordField.getText());
    }

}
