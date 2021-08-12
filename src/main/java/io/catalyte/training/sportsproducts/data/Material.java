package io.catalyte.training.sportsproducts.data;

public enum Material {
  COTTON("Cotton"),
  POLYESTER("Polyester"),
  MICROFIBER("Microfiber"),
  SPANDEX("Spandex"),
  ELASTIC("Elastic"),
  LEATHER("Leather"),
  SYNTHETIC_LEATHER("Synthetic Leather"),
  PU_LEATHER("PU leather"),
  WOOL("Wool"),
  CARBON_FIBER("Carbon Fiber"),
  FIBERGLASS("Fiberglass"),
  KEVLAR("Kevlar"),
  NEOPRENE("Neoprene"),
  ACRYLIC("Acrylic"),
  PLASTIC("Plastic"),
  SILICON("Silicone"),
  VINYL("Vinyl"),
  RUBBER("Rubber"),
  METAL("Metal"),
  FOAM("Foam")
  ;
  private String title;

  private Material(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}

