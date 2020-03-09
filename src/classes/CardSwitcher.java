package classes;

import java.awt.*;

/**
 * En klass som hanterar byte av de olika sidorna
 */
public class CardSwitcher {
    CardLayout layout;
    Container container;

    public CardSwitcher(Container container, CardLayout layout) {
        this.layout = layout;
        this.container = container;
    }

    public void switchTo(String card) {
        layout.show(container, card);
    }

}
