package hu.nye.progtech.torpedo2;

import hu.nye.progtech.torpedo2.service.Controller;
import hu.nye.progtech.torpedo2.service.ControllerFacade;

public class Game {
    public static void main(String[] args) {
            ControllerFacade controller = new ControllerFacade(new Controller());
            //Controller muxik
            controller.setupGame();
            controller.play();
            controller.kudosToWinner();
    }
}
