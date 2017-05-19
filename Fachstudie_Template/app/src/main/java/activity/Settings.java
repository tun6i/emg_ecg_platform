package activity;

public class Settings {
    private int activeFragment = 0;
    private static Settings settings = new Settings();

    public static Settings getInstance() {
        return settings;
    }

    public int getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(int activeFragment) {
        this.activeFragment = activeFragment;
    }
}
