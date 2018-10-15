package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class MoreDetailsPanelHandle extends NodeHandle<Node> {

    public static final String DETAILS_PANEL_ID = "#detailsPanel";
    public static final String DEFAULT = "Select a student to display his/her details.";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    public MoreDetailsPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(BROWSER_ID));
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /** FIX COMMENTS
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isDetailsChanged() {
        //return !lastRememberedDetails.equals(getDetails()); // fix
        return false;
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }

    public void rememberDetails() {
    }

    public String getOwner() {
        return "";
    }
}
