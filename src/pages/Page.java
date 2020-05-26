package pages;

import classes.CardSwitcher;

/**
 * Page interface som kräver att varje sida ska ha funktionaliteten ha en övergång mellan andra sidor
 */
public interface Page
{
    void switchPage(CardSwitcher switcher, String newPage);
}
