# Лабораторна робота №2 — Gradle та Java Collections Framework

## Опис

Частотний аналізатор тексту: клас `WordCounter` рахує, скільки разів кожне слово
зустрічається в тексті, і повертає множину унікальних слів.

Правила:
- слово — послідовність латинських букв і цифр (`[a-zA-Z0-9]+`), усе інше — роздільник;
- усі слова приводяться до нижнього регістру;
- порожні слова ігноруються;
- для `null` замість списку кидається `IllegalArgumentException`.

## Розрахунок варіанта

```
V = N mod 3 = 5 mod 3 = 2
```

N = 5 — номер у списку групи. Варіант 2 — **Частотний аналізатор тексту**.

## Структура

```
gradle/libs.versions.toml                     # Version Catalog (версія JUnit)
build.gradle.kts
src/main/java/ua/kpi/comsys/collections/
├── WordCounter.java    # countWords(), getUniqueWords()
└── Main.java           # демонстрація
src/test/java/ua/kpi/comsys/collections/
└── WordCounterTest.java
```

Використані колекції (тільки `java.util`):
- `HashMap<String, Integer>` для підрахунку — додавання і пошук за O(1), лічильник оновлюється через `merge()`;
- `HashSet<String>` для унікальних слів.

## Запуск

Потрібен JDK 17+ (Gradle сам завантажить JDK 21 через toolchain).

Збірка і тести:

```bash
./gradlew build
```

Тільки тести:

```bash
./gradlew test
```

Демо:

```bash
./gradlew run
```

На Windows — `gradlew.bat` замість `./gradlew`.
