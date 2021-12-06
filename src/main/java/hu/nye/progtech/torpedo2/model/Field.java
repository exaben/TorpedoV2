package hu.nye.progtech.torpedo2.model;

public interface Field {
    /**
     * Durr
     *
     * @return hit message
     */
    String hit();

    /**
     * Mezo allapotanak beallitasa
     *
     * @param fieldState
     */
    void setFieldState(FieldState fieldState);

    /**
     * Mezo allapotanak lekerdezese
     *
     * @return state
     */
    FieldState getFieldState();
}
