package model;

import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class Coordinator extends User {

    public Coordinator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem1 = new MenuItem("Create/read/update/delete quiz");
        MenuItem menuItem2 = new MenuItem("Show quiz dashboard");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        menuItem2.setOnAction(actionEvent -> Main.getSceneManager().showCoordinatorDashboard());
        return menuItems;
    }

    //maak een vraag

    /*public Question createQuestion(){

    }*/

    //maak een goede antwoord aan de vraag

    //maak een aantwoord array gebaseerd aan

    //methode maken die voegd een nieuw vraag met een 4 aantwoorden
    // maak een nieuwe vraag aan en voeg antwoord bij deze vraag
   /* public Question makeNewQuestion(Question question, String answer, String answer2, String answer3){
        question.voegAntwoordAanVraag(answer);
        question.voegAntwoordAanVraag(answer2);
        question.voegAntwoordAanVraag(answer3);
        return question;

    }*/

    //op basis van deze vraag maak een lijks van 4 antwoorden, waaronder eentje is de goede



    //een aantwoord is de goede

    //methode die antoord toevogd aan een bestaande quiz

    //quiz Crud





}
