package io.catalyte.training.sportsproducts.data;

public enum Material {
  ACRYLIC("Acrylic"),
  CARBON_FIBER("Carbon Fiber"),
  COTTON("Cotton"),
  ELASTIC("Elastic"),
  FIBERGLASS("Fiberglass"),
  FOAM("Foam"),
  KEVLAR("Kevlar"),
  LEATHER("Leather"),
  METAL("Metal"),
  MICROFIBER("Microfiber"),
  NEOPRENE("Neoprene"),
  PLASTIC("Plastic"),
  POLYESTER("Polyester"),
  PU_LEATHER("PU leather"),
  RUBBER("Rubber"),
  SILICON("Silicone"),
  SPANDEX("Spandex"),
  SYNTHETIC_LEATHER("Synthetic Leather"),
  VINYL("Vinyl"),
  WOOL("Wool");
  private final String title;

  Material(String title) {
    this.title = title;
  }

  /**
   * Returns a title from the list of types.
   * @return title
   */
  public String getTitle() {
    return title;
  }
}

