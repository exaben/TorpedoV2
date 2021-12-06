package hu.nye.progtech.torpedo2.service;

import hu.nye.progtech.torpedo2.model.Observer;

public abstract class Ship extends Observer {
    /**
     * Komponens hozzaadasa
     *
     * @param subject component of ship
     */
    public void addComponent(ShipComponent subject) {
        subject.attach(this);
    }

    /**
     * Hajo neve
     *
     * @return name
     */
    public abstract String getName();

    /**
     * A hajo hosszanak lekerdezese
     *
     * @return length
     */
    public abstract int getLength();

    /**
     * Hajo epsege
     *
     * @return health
     */
    public abstract int getHealth();
}
