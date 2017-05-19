package activity;

public class Settings {
    private int activeFragment = 0;
    private int numChannels = 1;
    private static Settings settings = new Settings();

    public static Settings getInstance() {
        return settings;
    }

    public void setNumChannels(int number) {
        numChannels = number;
    }

    public int getNumChannels() {
        return numChannels;
    }

    public int getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(int activeFragment) {
        this.activeFragment = activeFragment;
    }
}
