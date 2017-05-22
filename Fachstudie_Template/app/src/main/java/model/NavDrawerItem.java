package model;

public class NavDrawerItem {
    // Show Notify
    private boolean showNotify;

    // Title
    private String title;


    // First Constructor
    public NavDrawerItem() {
    }

    /**
     * Second Constructor with parameters: showNotify and title.
     * @param showNotify
     * @param title
     */
    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    /**
     * Title is returned.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
