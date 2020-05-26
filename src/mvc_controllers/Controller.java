package mvc_controllers;

/**
 * Kontroller interface som kräver att varje kontroller måste ha updateView funktion som uppdaterar vilken användare som är
 * inloggad för tillfället.
 */

public interface Controller
{
    void updateView();
}
