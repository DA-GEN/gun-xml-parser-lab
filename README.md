# Вогнепальна зброя: XML, XSD, XSL

Лабораторна робота №2 з ООП. Консольний додаток на Java, який описує колекцію
вогнепальної зброї у XML, валідує її за XSD-схемою, розбирає парсерами DOM, SAX
та StAX, сортує через `Comparator` і за допомогою XSL групує зброю за країною
виробництва.

## Файли даних

- `data/gun.xml` — колекція зброї, кореневий елемент `Gun`
- `data/gun.xsd` — схема: прості та комплексні типи, переліки (`Handy`,
  `FiringRange`), граничні значення (`SightingRange` 10–3000 м), обов'язковий
  атрибут `id` типу `xs:ID`, шаблон для `Origin`
- `data/gun-by-origin.xsl` — перетворення, що розбиває список `Firearm` на
  підсписки за `Origin`; результат записується в `output/gun-by-origin.xml`

Структура одного елемента:

```xml
<Firearm id="f1">
    <Model>Glock 17</Model>
    <Handy>one-handed</Handy>
    <Origin>Austria</Origin>
    <TTC>
        <FiringRange>close</FiringRange>
        <SightingRange>50</SightingRange>
        <Clip>true</Clip>
        <Optics>false</Optics>
    </TTC>
    <Material>polymer</Material>
</Firearm>
```

`FiringRange`: `close` — 0–500 м, `medium` — 500–1000 м, `far` — від 1000 м.

## Структура пакетів

- `com.dagen.gunxml.model` — `Firearm`, `Ttc` та переліки `Handy`, `FiringRange`,
  що відповідають XSD
- `com.dagen.gunxml.parser` — парсери `DomGunParser`, `SaxGunParser`,
  `StaxGunParser` зі спільним `FirearmBuilder`
- `com.dagen.gunxml.validation` — валідація XML за XSD
- `com.dagen.gunxml.sort` — компаратори `FirearmComparator`
- `com.dagen.gunxml.transform` — XSL-перетворення
- `com.dagen.gunxml.app` — точка входу та налаштування логера

## Збірка та запуск

```
mvn package
java -jar target/gun-xml-parser-lab.jar [xml-файл] [файл-результату]
```

Запускати потрібно з кореня репозиторію, бо файли з `data/` читаються за
відносним шляхом.

## Логування

Використовується `java.util.logging`, конфігурація — у
`src/main/resources/logging.properties`. Повідомлення рівня `INFO` і вище
виводяться в консоль, а рівня `FINE` і вище записуються у `logs/gun-xml-parser.log`.

## Тести

```
mvn test
```

Юніт-тести написані на JUnit 5. Звіт про покриття JaCoCo генерується в
`target/site/jacoco/index.html`.
