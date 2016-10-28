package seedu.unburden.ui;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import seedu.unburden.commons.core.LogsCenter;
import seedu.unburden.commons.util.FxViewUtil;
import seedu.unburden.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * The Summary Panel of the App.
 * @@author A0143095H
 */
public class SummaryPanel extends UiPart{

    private static Logger logger = LogsCenter.getLogger(SummaryPanel.class);
    private WebView browser;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a SummaryPanel.
     */
    private SummaryPanel() {

    }

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return null; //not applicable
    }

    /**
     * Factory method for creating a Browser Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public static SummaryPanel load(AnchorPane placeholder){
        logger.info("Initializing SummaryPanel");
        SummaryPanel browserPanel = new SummaryPanel();
        browserPanel.browser = new WebView();
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browserPanel.browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browserPanel.browser);
        return browserPanel;
    }

    public void loadPersonPage(ReadOnlyTask person) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
    }

    public void loadPage(String url){
        browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}