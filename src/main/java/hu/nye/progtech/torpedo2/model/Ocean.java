package hu.nye.progtech.torpedo2.model;

import hu.nye.progtech.torpedo2.ui.UI;

public class Ocean implements Field{
    /**
     * Mezok
     */
    private FieldState fieldState = new IntactFIeldState();

    @Override
    public String hit() {
        if (!fieldState.isBombed()) {
            setFieldState(new NukedFieldState());
            return UI.ANSI_CYAN + "Melle" + UI.ANSI_RESET;
        }
        return UI.ANSI_CYAN + "Talald el ujra! :D" + UI.ANSI_RESET;
    }

    @Override
    public void setFieldState(FieldState fieldState) {

        this.fieldState = fieldState;
    }

    @Override
    public FieldState getFieldState() {

        return fieldState;
    }
}
