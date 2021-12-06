package hu.nye.progtech.torpedo2.service.ships;

import hu.nye.progtech.torpedo2.model.FieldState;
import hu.nye.progtech.torpedo2.service.Ship;
import hu.nye.progtech.torpedo2.ui.UI;

public class Destroyer extends Ship {
    private final String NAME = "Rombolo";
    private final int LENGTH = 2;
    private int health = LENGTH;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void update(FieldState fieldState) {
        if (!fieldState.isBombed() && health > 0) {
            health--;
            UI.printf(UI.ANSI_RED + "%s BOOM %n" + UI.ANSI_RESET, NAME);
        }
    }
}
