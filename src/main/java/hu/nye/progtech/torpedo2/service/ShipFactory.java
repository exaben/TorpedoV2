package hu.nye.progtech.torpedo2.service;

import hu.nye.progtech.torpedo2.model.FieldState;
import hu.nye.progtech.torpedo2.service.ships.*;

public class ShipFactory {
    /**
     * Creating ship according to type
     *
     * @param type of ship
     * @return Ship of given type
     */
    public Ship makeShip(ShipType type) {
        switch (type) {
            case CARRIER:
                return new Carrier();
            case BATTLESHIP:
                return new BattleShip();
            case SUBMARINE:
                return new Submarine();
            case DESTROYER:
                return new Destroyer();
            case CRUISER:
                return new Cruiser();
            default:
                return new Ship() {
                    @Override
                    public String getName() {
                        return "Stealthy McStealthFace";
                    }

                    @Override
                    public int getLength() {
                        return 0;
                    }

                    @Override
                    public int getHealth() {
                        return 0;
                    }

                    @Override
                    public void update(FieldState fieldState) {
                    }
                };
        }
    }
}
