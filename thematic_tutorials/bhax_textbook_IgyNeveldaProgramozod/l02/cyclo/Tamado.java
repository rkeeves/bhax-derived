package hu.fersml.aranyfc;

public class Tamado extends hu.fersml.aranyfc.Jatekos {

...

  @Override
  protected void jatekbanVezerles() {

    if (latomASzelet && distanceSzele < 7.5) {
      palyanMaradni();
    } else if (latomAFocit) {

      if (distanceFoci < 0.9) {
        logger.info("KOZEL A FOCI "
                + getPlayer().getNumber()
                + " tavolsaga = " + distanceFoci
                + " iranya = " + directionFoci);
        rugdEsFuss();
      } else if (distanceFoci < 35.0) {
        egyuttElaJatekkal(100);
      } else if (distanceFoci < 45.0) {
        egyuttElaJatekkal(30);
      } else {
        egyuttElaJatekkal(10);
      }
    } else {
      teblabol();
    }

  }
  
...
}
