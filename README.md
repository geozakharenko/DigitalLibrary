# Проект "Электронная библиотека"
Проект представляет из себя реализацию web-приложения библиотеки, переходящей на цифровой учёт книг.

## Стек
<div>
  <img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original.svg" title="Java-8" alt="Java" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original.svg" title="Spring-5-2-25" alt="Spring" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/maven/maven-original.svg" title="Maven" alt="Maven" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/postgresql/postgresql-original.svg" title="Postgresql" alt="Postgresql" width="40" height="40"/>&nbsp;
  <img src="https://github.com/geozakharenko/DigitalLibrary/assets/160642323/16563eb7-1fb6-406d-934e-80f7f75d151b" title="Thymeleaf" width="48" height="40">
  <img src="https://github.com/devicons/devicon/blob/master/icons/tomcat/tomcat-original.svg" title="Tomcat 9" alt="Tomcat" width="40" height="40"/>&nbsp;
</div>

## Использование
Приложение подразумевает взаимодействие со следующими URL:
```
http://localhost:8080/people
http://localhost:8080/books
http://localhost:8080/search ~ v1.2.0+
http://localhost:8080/books?sort_by_year=true  ~ v1.2.0+
http://localhost:8080/books?page=0&books_per_page=6  ~ v1.2.0+
http://localhost:8080/books?page=0&books_per_page=6&sort_by_year=true  ~ v1.2.0+
```
## Функционал
- Выполнение CRUD-функций над читателями и книгами
- Назначение книг читателям
- Освобождение книг у читателей
- Просмотр имеющихся у читателей книг
- Пагинация ```~ v1.2.0+```
- Сортировка книг по дате выпуска ```~ v1.2.0+```
- Поиск всех имеющихся книг в библиотеке ```~ v1.2.0+```
- Визуальное представление пагинации  ```~ v1.3.0+```
- Контроль просроченности по времянахождению у читателя взятой книги ```~ v1.3.0+```
    
## Инструкция по сборке и запуску решения
- Сделать `git clone` репозитория
- Скачать [Tomcat Server](https://tomcat.apache.org/) и подключить его в разделе `Run` -> `Edit configurations` -> `Add new ...` -> `Tomcat Server` -> `Local`

  В этом же разделе: `Fix` -> `...war exploded`. Далее, во вкладке `Deployment` очистить поле `Application context`
- Подключить к проекту базу данных. Раздел `Database`->`New`->`Data Source`->`Postgresql`

#### Для версии v0.1.0
- В разделе `resources` добавить в файл `database.properties.origin` значение ключей. Убрать ```.origin```
<img src="https://github.com/geozakharenko/DigitalLibrary/assets/160642323/06f9ba40-8e7e-49ca-978c-15a0d4213813" width="600" height="120"/>&nbsp;

#### Для версии v1.2.0+
- В разделе `resources` добавить в файл `hibernate.properties.origin` значение ключей. Убрать ```.origin```
![img.png](img.png)

## Операции с базой данных
- Создать в базе данных 2 таблицы:
```
CREATE TABLE Person
(
    person_id     int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name     varchar(120) UNIQUE                NOT NULL,
    year_of_birth int CHECK ( year_of_birth > 1900 ) NOT NULL
);
CREATE TABLE Book
(
    book_id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    person_id int          REFERENCES Person (person_id) ON DELETE SET NULL,
    title     varchar(240) NOT NULL,
    author    varchar(120) NOT NULL,
    year_of_release int NOT NULL
);
```
#### Для версии v1.3.0+
- Дополнительно выполнить:
```
ALTER TABLE book ADD COLUMN taken_at TIMESTAMP
```
