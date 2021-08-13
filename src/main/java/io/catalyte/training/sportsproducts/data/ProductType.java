package io.catalyte.training.sportsproducts.data;

import java.util.List;
import java.util.Random;

/**
 * Assign to each of product type list of material associated with
 */
public enum ProductType {
  BELT("Belt",
      List.of(Material.SYNTHETIC_LEATHER, Material.LEATHER, Material.PU_LEATHER),
      "https://m.media-amazon.com/images/I/81tjDxwjErS._AC_UX466_.jpg"),
  ELBOW_PAD("Elbow Pad",
      List.of(Material.PLASTIC, Material.NEOPRENE, Material.FOAM),
      "https://m.media-amazon.com/images/I/81wiqyEka0S._AC_SL1500_.jpg"),
  FLIP_FLOP("Flip Flop",
      List.of(Material.RUBBER, Material.VINYL, Material.SILICON),
      "https://m.media-amazon.com/images/I/61Sh6GpKroL._AC_UY500_.jpg"),
  GLOVE("Glove",
      List.of(Material.LEATHER, Material.SYNTHETIC_LEATHER, Material.PU_LEATHER, Material.NEOPRENE,
          Material.COTTON),"https://m.media-amazon.com/images/I/81blOUiYdaL._AC_SX679_.jpg"),
  HAT("Hat",
      List.of(Material.COTTON, Material.POLYESTER, Material.SPANDEX),
      "https://m.media-amazon.com/images/I/71LPDt9QbAL._AC_SX466._SX._UX._SY._UY_.jpg"),
  HEADBAND("Headband",
      List.of(Material.ELASTIC, Material.COTTON, Material.SPANDEX),
      "https://m.media-amazon.com/images/I/61az40d6MGL._AC_SX679_.jpg"),
  HELMET("Helmet",
      List.of(Material.FOAM, Material.CARBON_FIBER, Material.FIBERGLASS, Material.KEVLAR),
      "https://m.media-amazon.com/images/I/711+u8SbLnL._AC_SX679_.jpg"),
  HOODIE("Hoodie",
      List.of(Material.COTTON, Material.MICROFIBER, Material.POLYESTER, Material.SPANDEX),
      "https://m.media-amazon.com/images/I/81rc97tuOhL._AC_UX569_.jpg"),
  JACKET("Jacket",
      List.of(Material.LEATHER, Material.SYNTHETIC_LEATHER, Material.PU_LEATHER, Material.COTTON,
          Material.POLYESTER, Material.PU_LEATHER),
      "https://m.media-amazon.com/images/I/61ZNBEQpQ0L._AC_UX679_.jpg"),
  PANTS("Pant",
      List.of(Material.COTTON, Material.MICROFIBER, Material.COTTON, Material.POLYESTER,
          Material.SPANDEX, Material.WOOL),
      "https://m.media-amazon.com/images/I/51cxynn5yIL._AC_UX569_.jpg"),
  POOL_NOODLE("Pool Noodle",
      List.of(Material.FOAM),
      "https://m.media-amazon.com/images/I/81t6UzFSWQL._AC_SY679_.jpg"),
  SHIN_GUARDS("Shin Guard",
      List.of(Material.PLASTIC, Material.CARBON_FIBER, Material.FIBERGLASS, Material.KEVLAR),
      "https://m.media-amazon.com/images/I/41tDyH7SZPL._AC_.jpg"),
  SHOE("Shoe",
      List.of(Material.LEATHER, Material.SYNTHETIC_LEATHER, Material.PU_LEATHER, Material.VINYL),
      "https://m.media-amazon.com/images/I/41huVYB16VL._AC_.jpg"),
  SHORTS("Shorts",
      List.of(Material.COTTON, Material.MICROFIBER, Material.POLYESTER, Material.SPANDEX),
      "https://m.media-amazon.com/images/I/71c0K11eDeS._AC_UX569_.jpg"),
  SOCK("Sock",
      List.of(Material.MICROFIBER, Material.COTTON, Material.ELASTIC, Material.SPANDEX),
      "https://m.media-amazon.com/images/I/81+Yg21pxJL._AC_UX679_.jpg"),
  SUNGLASSES("Sunglasses",
      List.of(Material.METAL, Material.ACRYLIC, Material.PLASTIC),
      "https://m.media-amazon.com/images/I/61tta3M0geS._AC_SX522_.jpg"),
  TANK_TOP("Tank Top",
      List.of(Material.ELASTIC, Material.MICROFIBER, Material.COTTON, Material.POLYESTER,
          Material.SPANDEX),"https://m.media-amazon.com/images/I/81WMOaI0zbL._AC_UX466_.jpg"),
  VISOR("Visor",
      List.of(Material.PLASTIC, Material.POLYESTER, Material.COTTON, Material.SPANDEX),
      "https://m.media-amazon.com/images/I/81kQfSNbNAL._AC_UY445_.jpg"),
  WRISTBAND("Wristband",
      List.of(Material.RUBBER, Material.VINYL, Material.SILICON),
      "https://m.media-amazon.com/images/I/91j6AUptoLL._AC_UX466_.jpg");

  private final String title;
  private final List<Material> materials;
  private final String url;

  ProductType(String title, List<Material> materials, String url) {
    this.title = title;
    this.materials = materials;
    this.url = url;
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
   * @return materials
   */
  public List<Material> getMaterials() {
    return materials;
  }

  public String getUrl() {
    return url;
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
