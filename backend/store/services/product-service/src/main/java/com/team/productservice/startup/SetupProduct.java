package com.team.productservice.startup;

import com.team.productservice.model.Category;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public enum SetupProduct {
  DEMIX_MAGUS_3_0_B(
    "Кроссовки для мальчиков Demix Magus 3.0 B",
    "Воздухопроницаемые кроссовки Demix Magus 3.0 идеально подойдут для занятий спортом. ДОПОЛНИТЕЛЬНАЯ ВЕНТИЛЯЦИЯ: Трикотажный верх с особым плетением отлично пропускает воздух, поддерживая оптимальный микроклимат. АМОРТИЗАЦИЯ: Упругая подошва эффективно гасит ударные нагрузки. НАДЕЖНАЯ ФИКСАЦИЯ: Застежка-липучка для удобной фиксации обуви на ноге. ЛЕГКОСТЬ: Подошва из ЭВА и ультралегкие материалы верха обеспечивают низкий вес модели.",
    new BigDecimal("2999"),
    100L,
    List.of(
      "demix-magus-3.0-B/0.jpg",
      "demix-magus-3.0-B/1.jpg"
    ),
    SetupCategory.by("All.Male.Children.Shoes")
  ),
  BUTTON_DOWN_DENIM_SHIRT_JACKET(
    "Джинсовая куртка-рубашка на кнопках",
    "Джинсовая куртка-рубашка на кнопках. Свободный крой со спущенной линией плеча. Дизайн с 6 карманами: накладные, внутренние, в боковых швах. Застёжка на кнопки. Структурный деним. Кислотная стирка в голубом цвете.",
    new BigDecimal("3999"),
    60L,
    List.of(
      "button-down-denim-shirt-jacket/0.jpg",
      "button-down-denim-shirt-jacket/1.jpg",
      "button-down-denim-shirt-jacket/2.jpg",
      "button-down-denim-shirt-jacket/3.jpg",
      "button-down-denim-shirt-jacket/4.jpg",
      "button-down-denim-shirt-jacket/5.jpg",
      "button-down-denim-shirt-jacket/6.jpg"
    ),
    SetupCategory.by("All.Male.Adult.Outerwear")
  ),
  BASIC_5_POCKET_TWILL_TROUSERS(
    "Базовые брюки «5 карманов» из твила",
    "Базовые брюки «5 карманов» из твила. Дизайн «5 карманов». Узкий силуэт. Задние накладные карманы. Маленький часовой карман.",
    new BigDecimal("2599"),
    70L,
    List.of(
      "basic-5-pocket-twill-trousers/0.jpg",
      "basic-5-pocket-twill-trousers/1.jpg",
      "basic-5-pocket-twill-trousers/2.jpg",
      "basic-5-pocket-twill-trousers/3.jpg",
      "basic-5-pocket-twill-trousers/4.jpg",
      "basic-5-pocket-twill-trousers/5.jpg",
      "basic-5-pocket-twill-trousers/6.jpg"
    ),
    SetupCategory.by("All.Male.Adult.Trousers")
  ),
  PADDED_BASIC_JACKET_WITH_HOOD(
    "Утеплённая базовая куртка с капюшоном",
    "Утеплённая базовая куртка с капюшоном. Прямая горизонтальная стёжка. Эластичный низ куртки и рукавов. Воротник-стойка и несъёмный капюшон. Застёжка на крупную пластиковую молнию с кантом и внутренней ветрозащитной планкой. Боковые карманы с застёжкой на потайные кнопки. Внутренний карман на молнии.",
    new BigDecimal("7999"),
    20L,
    List.of(
      "padded-basic-jacket-with-hood/0.jpg",
      "padded-basic-jacket-with-hood/1.jpg",
      "padded-basic-jacket-with-hood/2.jpg",
      "padded-basic-jacket-with-hood/3.jpg",
      "padded-basic-jacket-with-hood/4.jpg",
      "padded-basic-jacket-with-hood/5.jpg",
      "padded-basic-jacket-with-hood/6.jpg",
      "padded-basic-jacket-with-hood/7.jpg"
    ),
    SetupCategory.by("All.Male.Adult.Outerwear")
  ),
  COTTON_DRESS_WITH_VOLUMINOUS_SLEEVES(
    "Хлопковое платье с объёмными рукавами",
    "Хлопковое платье с объёмными рукавами. А-силуэт. Квадратный вырез горловины. Объёмный рукав. Имитация топа и юбки на спинке. Длина платья от плеча спереди: 120 см (для размера S).",
    new BigDecimal("2999"),
    46L,
    List.of(
      "cotton-dress-with-voluminous-sleeves/0.jpg",
      "cotton-dress-with-voluminous-sleeves/1.jpg",
      "cotton-dress-with-voluminous-sleeves/2.jpg",
      "cotton-dress-with-voluminous-sleeves/3.jpg",
      "cotton-dress-with-voluminous-sleeves/4.jpg",
      "cotton-dress-with-voluminous-sleeves/5.jpg",
      "cotton-dress-with-voluminous-sleeves/6.jpg"
    ),
    SetupCategory.by("All.Female.Adult.Dresses")
  ),
  COTTON_DRESS_WITH_CUTOUT_BACK(
    "Хлопковое платье с вырезом на спине",
    "Хлопковое платье с вырезом на спине. А-силуэт. Круглый вырез горловины. Объёмный рукав. Открытая спинка. Завязки по талии сзади. Объёмная юбка. Длина платья от плеча спереди: 91,7 см (для размера S).",
    new BigDecimal("1999"),
    75L,
    List.of(
      "cotton-dress-with-cutout-back/0.jpg",
      "cotton-dress-with-cutout-back/1.jpg",
      "cotton-dress-with-cutout-back/2.jpg",
      "cotton-dress-with-cutout-back/3.jpg",
      "cotton-dress-with-cutout-back/4.jpg",
      "cotton-dress-with-cutout-back/5.jpg",
      "cotton-dress-with-cutout-back/6.jpg"
    ),
    SetupCategory.by("All.Female.Adult.Dresses")
  ),
  T_SHIRT_WITH_RHINESTONE(
    "Футболка со стразами",
    "Футболка со стразами. Короткий рукав. Круглый вырез горловины. Свободный силуэт. Принт из страз.",
    new BigDecimal("999"),
    164L,
    List.of(
      "t-shirt-with-rhinestones/0.jpg",
      "t-shirt-with-rhinestones/1.jpg",
      "t-shirt-with-rhinestones/2.jpg",
      "t-shirt-with-rhinestones/3.jpg",
      "t-shirt-with-rhinestones/4.jpg",
      "t-shirt-with-rhinestones/5.jpg",
      "t-shirt-with-rhinestones/6.jpg"
    ),
    SetupCategory.by("All.Female.Adult.T-shirts")
  ),
  BASEBALL_CAP_WITH_EMBROIDERY(
    "Бейсболка с вышивкой",
    "Бейсболка с вышивкой.",
    new BigDecimal("799"),
    178L,
    List.of(
      "baseball-cap-with-embroidery/0.jpg",
      "baseball-cap-with-embroidery/1.jpg",
      "baseball-cap-with-embroidery/2.jpg",
      "baseball-cap-with-embroidery/3.jpg"
    ),
    SetupCategory.by("All.Male.Adult.Accessories")
  ),
  ELASTIC_STRAP(
    "Эластичный ремень",
    "Эластичный ремень. Эластичный ремень с отделкой из искусственной кожи.",
    new BigDecimal("799"),
    100L,
    List.of(
      "elastic-strap/0.jpg",
      "elastic-strap/1.jpg",
      "elastic-strap/2.jpg",
      "elastic-strap/3.jpg",
      "elastic-strap/4.jpg"
    ),
    SetupCategory.by("All.Male.Adult.Accessories")
  );

  private final String imageDir = "/images";
  private final String name;
  private final String description;
  private final BigDecimal cost;
  private final Long countInStock;
  private final List<String> imagePaths;
  private final Category category;

  SetupProduct(String name,
               String description,
               BigDecimal cost,
               Long countInStock,
               List<String> imagePaths,
               Category category) {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.countInStock = countInStock;
    this.imagePaths = imagePaths.stream().map(path -> imageDir + "/" + path).toList();
    this.category = category;
  }
}