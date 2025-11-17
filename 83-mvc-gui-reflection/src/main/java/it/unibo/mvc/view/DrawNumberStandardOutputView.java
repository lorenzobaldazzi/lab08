package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * implementazione di DrawNumberView.
 */
public class DrawNumberStandardOutputView implements DrawNumberView {

    @Override
    public void start() { }

    @Override
    public void setController(final DrawNumberController observer) { }

    /**
     * Mostra il disegno.
     * 
     * @param res il risultato del disegno
     */
    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); //NOPMD
    }
}
