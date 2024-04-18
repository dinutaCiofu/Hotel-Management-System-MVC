package org.example.view;

import org.example.model.entities.TipUtilizator;

import javax.swing.*;

public interface IFirstPage {
    JPanel getMainPanel();

    TipUtilizator getSelectedOption();
}
