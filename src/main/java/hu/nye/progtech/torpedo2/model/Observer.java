package hu.nye.progtech.torpedo2.model;

public abstract class Observer {
    /**
     * Update at call
     *
     * @param fieldState of Field
     */
    public abstract void update(FieldState fieldState);
}
