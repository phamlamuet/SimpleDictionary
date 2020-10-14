package sample;

import TrieUtil.Trie;
import UtilGUI.ConfirmationBox;
import UtilGUI.MessageBox;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.*;


public class DictionaryManagement extends Application {
    Stage stageTemp;
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
    Button ggTranslate;

    Button addAWord;
    Button deleteAWord;


    @Override
    public void start(Stage primaryStage) throws Exception {

        stageTemp=primaryStage;

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

        ggTranslate = new Button();
        ggTranslate.setText("UseAPI");
        ggTranslate.setOnAction(event -> {
            try {
                useAPIButton();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox input = new HBox(10);
        addAWord = new Button();
        addAWord.setText("Add");
        addAWord.setOnAction(event -> addAWord_Go());
        deleteAWord = new Button();
        deleteAWord.setText("Delete");
        deleteAWord.setOnAction(event -> deleteAWord_Go());
        input.getChildren().addAll(wordField, searchButton, speakButton, addAWord, deleteAWord, ggTranslate);

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
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dictionary");
        primaryStage.setOnCloseRequest( e ->
        {
            e.consume();
            btnClose_Click();
        } );
        primaryStage.show();
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
                meaningLabel.setText("Not found,maybe you wrote it wrong,see recommend list beside or use API button");
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

    private void useAPIButton() throws IOException {
        String s = Translator.translate("en", "vi", wordField.getText());
        meaningLabel.setText(s);
    }

    private void addAWord_Go() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add a word");

        Label inputWordLabel = new Label();
        inputWordLabel.setText("Enter a word");

        TextField inputWord = new TextField();
        inputWord.setPromptText("Enter a word you want to add");

        Label phonetic = new Label();
        phonetic.setText("Phonetics");

        TextField phoneticInput = new TextField();
        phoneticInput.setPromptText("Enter the word's phonetic");

        Label detailLabel = new Label();
        detailLabel.setText("Detail");

        TextArea wordDetail = new TextArea();

        Button addConfirm = new Button();
        addConfirm.setText("Add");

        VBox addPane = new VBox();
        addPane.getChildren().addAll(inputWordLabel, inputWord, phonetic, phoneticInput, detailLabel, wordDetail, addConfirm);

        addConfirm.setOnAction(event -> {
            String s = inputWord.getText();
            int result = Collections.binarySearch(dictionary.getWords(), new Word(s));
            if (result <= 0) {
                StringBuilder s2 = new StringBuilder(wordDetail.getText());
                String s3 = phoneticInput.getText();
                Word newWord = new Word(s, new Meaning(s2), s3);
                dictionary.getWords().add(newWord);
                dictionary.sortWords();
                MessageBox.show("Word added succesfully", "");
            } else {
                MessageBox.show("Word already exist", "");
            }
        });
        Scene scene = new Scene(addPane, 300, 500);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.show();
    }

    private void deleteAWord_Go() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Delete A Word");
        stage.setMinWidth(300);

        TextField inputField = new TextField();
        Label input = new Label();
        input.setText("Enter A Word");
        inputField.setPromptText("Enter A Word You Want To Delete");
        Label output = new Label();
        output.setText("Delete successfully");
        Button confirm = new Button();
        confirm.setText("Delete");
        confirm.setOnAction(event -> {
            String s = inputField.getText();
            int result = Collections.binarySearch(dictionary.getWords(), new Word(s));
            if (result > 0) {
                dictionary.getWords().remove(result);
                MessageBox.show("Delete successfully " + result, "");
            } else {
                MessageBox.show("This word is no longer in the dictionary", "");
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(confirm);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(input, inputField, confirm);
        Scene scene = new Scene(vBox, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void btnClose_Click() {
        boolean confirm = false;
        confirm = ConfirmationBox.show(
                "Are you sure you want to quit?", "Confirmation",
                "Yes", "No");
        if (confirm)
        {
            stageTemp.close();
        }
    }
}
