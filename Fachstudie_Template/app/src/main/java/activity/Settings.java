package activity;

/**
 *  Class for the Settings.
 */
public class Settings {
    // Active-Fragment.
    private int activeFragment = 0;
    // Settings.
    private static Settings settings = new Settings();

    /**
     * Returns the settings.
     * @return
     */
    public static Settings getInstance() {
        return settings;
    }

    /**
     * Returns the active fragment.
     * @return
     */
    public int getActiveFragment() {
        return activeFragment;
    }

    /**
     * Sets the active fragment.
     * @param activeFragment
     */
    public void setActiveFragment(int activeFragment) {
        this.activeFragment = activeFragment;
    }
}
