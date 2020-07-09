package handlers;

import java.awt.*;

/**
 * En klass som hanterar övergången men de olika grafiska sidorna.
 */
public class CardSwitcher
{
    private CardLayout layout;
    private Container container;

    public CardSwitcher(Container container, CardLayout layout) {
	this.layout = layout;
	this.container = container;
    }

    public void switchTo(String card) {
	layout.show(container, card);
    }

}
