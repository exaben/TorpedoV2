package hu.nye.progtech.torpedo2.ui;

public class UI {
    /**
     * ANSI szinkodok
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Kiiratas a konzolra sortores nelkul
     */
    public static void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Kiiratas a konzolra sortoressel
     */
    public static void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Szovegformazas, hogy jol nezzen ki
     *
     * @param obj  formazatlan objektum
     * @param vars ertekek
     */
    public static void printf(Object obj, Object... vars) {
        System.out.printf((String) obj, vars);
    }

    /**
     * Konzoltorles
     */
    public static void clear() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear"); //unix-like
            }
        } catch (final Exception e) {
            println("Hiba: " + e);
        }
    }
}
