# TestAppForYandexMobilization
Тестовое задание, для поступления на проект [Мобилизация 2017](https://yandex.ru/mobilization/)  от компании Яндекс
## Основные фичи приложения
-Перевод текста (реализовано с помощью [API Яндекс Переводчика](https://tech.yandex.ru/translate/))</br>
-Поддержка языков,которые [поддерживает API Яндекс Переводчика](https://tech.yandex.ru/translate/doc/dg/concepts/api-overview-docpage/#languages)</br>
-Поддержка вариантов перевода (реализовано с помощью [API Словаря Яндекс](https://tech.yandex.ru/dictionary/))</br>
-Возможность добавления и удаления слов в разделах Избранное, История</br>
-Перемещение по Истории с помощью свайпов на главном экране приложения</br>
-Поиск по Истории и Избранному</br>
-Возможность включить/отключить, в настройках, показ вариантов перевода </br>
-Обработка ошибок при отсутствии интернета, с возможностью повторить запрос на перевод</br>
-Представлена информация об авторе и указаны ссылки, открывающиеся с помощью диалога с WebView</br>
## Архитектура и стек технологий
-В проекте используется своя реализация многослойной архитектуры (разбита по отдельным модулям)</br>
-Все custom view и custom layout, необходимые в проекте, вынесены в отдельный модуль </br>
-А так же RXJava, Retrofit, Picasso
## Скриншоты
### Главный экран
<img src="http://mastermetala.ru/git/Main_Screen_Empty.png" width="180" height="360"> <img src="http://mastermetala.ru/git/Main_Screen_Full.png" width="180" height="360">
### Избранное, История и поиск по ним
<img src="http://mastermetala.ru/git/Favorite.png" width="180" height="360"> <img src="http://mastermetala.ru/git/History.png" width="180" height="360"> <img src="http://mastermetala.ru/git/Search.png" width="180" height="360">
### Настройки, О программе, Об Авторе
<img src="http://mastermetala.ru/git/Settings.png" width="180" height="360"> <img src="http://mastermetala.ru/git/About.png" width="180" height="360"> <img src="http://mastermetala.ru/git/Author.png" width="180" height="360">
