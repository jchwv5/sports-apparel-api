package io.catalyte.training.sportsproducts.data;

import java.util.List;
import java.util.Random;

/**
 * Assign to each of product type list of material associated with
 */
public enum ProductType {
  BELT("Belt", List.of(Material.SYNTHETIC_LEATHER, Material.LEATHER, Material.PU_LEATHER)),
  ELBOW_PAD("Elbow Pad", List.of(Material.PLASTIC, Material.NEOPRENE, Material.FOAM)),
  FLIP_FLOP("Flip Flop", List.of(Material.RUBBER, Material.VINYL, Material.SILICON)),
  GLOVE("Glove",
      List.of(Material.LEATHER, Material.SYNTHETIC_LEATHER, Material.PU_LEATHER, Material.NEOPRENE,
          Material.COTTON)),
  HAT("Hat", List.of(Material.COTTON, Material.POLYESTER, Material.SPANDEX)),
  HEADBAND("Headband", List.of(Material.ELASTIC, Material.COTTON, Material.SPANDEX)),
  HELMET("Helmet",
      List.of(Material.FOAM, Material.CARBON_FIBER, Material.FIBERGLASS, Material.KEVLAR)),
  HOODIE("Hoodie",
      List.of(Material.COTTON, Material.MICROFIBER, Material.POLYESTER, Material.SPANDEX)),
  JACKET("Jacket",
      List.of(Material.LEATHER, Material.SYNTHETIC_LEATHER, Material.PU_LEATHER, Material.COTTON,
          Material.POLYESTER, Material.PU_LEATHER)),
  PANTS("Pant", List.of(Material.COTTON, Material.MICROFIBER, Material.COTTON, Material.POLYESTER,
      Material.SPANDEX, Material.WOOL)),
  POOL_NOODLE("Pool Noodle", List.of(Material.FOAM)),
  SHIN_GUARDS("Shin Guard",
      List.of(Material.PLASTIC, Material.CARBON_FIBER, Material.FIBERGLASS, Material.KEVLAR)),
  SHOE("Shoe",
      List.of(Material.LEATHER, Material.SYNTHETIC_LEATHER, Material.PU_LEATHER, Material.VINYL)),
  SHORTS("Shorts",
      List.of(Material.COTTON, Material.MICROFIBER, Material.POLYESTER, Material.SPANDEX)),
  SOCK("Sock", List.of(Material.MICROFIBER, Material.COTTON, Material.ELASTIC, Material.SPANDEX)),
  SUNGLASSES("Sunglasses", List.of(Material.METAL, Material.ACRYLIC, Material.PLASTIC)),
  TANK_TOP("Tank Top",
      List.of(Material.ELASTIC, Material.MICROFIBER, Material.COTTON, Material.POLYESTER,
          Material.SPANDEX)),
  VISOR("Visor", List.of(Material.PLASTIC, Material.POLYESTER, Material.COTTON, Material.SPANDEX)),
  WRISTBAND("Wristband", List.of(Material.RUBBER, Material.VINYL, Material.SILICON));

  private final String title;
  private final List<Material> materials;

  ProductType(String title, List<Material> materials) {
    this.title = title;
    this.materials = materials;
  }

  /**
   *
   * @return title of product type
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the list of materials.
   * @return
   */
  public List<Material> getMaterials() {
    return materials;
  }

  /**
   *   Generates a number of random materials based on type.
   *
   *   @return - a list of random materials
   *
   */
  public Material getRandomMaterial() {
    return materials.get(new Random().nextInt(materials.size()));
  }
}
