Площадка по поиску фитнес тренеров – это сервис, благодаря которому люди могут найти себе фитнес тренера, который составит им персональное расписание и персональный режим питания в зависимости от физиологических особенностей человека.

Человек, который хочет заниматься фитнесом, заходит в соответствующий раздел с тренерами. У каждого тренера прикреплена фотография, написано ФИО, номер телефона, достижения, пол, дату рождения, рейтинг и сфера спорта. Также каждый тренер указывает свою цену, за которую готов работать. Благодаря фильтрации показателей клиент может выбрать оптимальный для себя вариант.

После того, как тренер составил персональное расписание и режим питания, у пользователя появляется в личном кабинете календарь с тренировками и отдельно календарь на каждый день с расписанным питанием. Каждое утро в день тренировок пользователю приходит напоминание по почте. Также каждое утро по почте приходит напоминание о питании на сегодняшний день.

Тренировки могут проводиться без тренера, то есть тренер расписал набор упражнений, человек их выполняет, но также могут проводиться и с тренером, например занятия по йоге. Такие занятия могут быть как персональными, так и групповыми, где тренер запускает видеоконференцию, а люди к ней подключаются и занимаются вместе с тренером.

У каждого тренера хранится информация обо всех людях, которые записались на его занятия. Клиент должен указывать ФИО, номер телефона, дату рождения, пол, а также фотографию. Вся эта информация доступна тренерам.

Оплата производится при помощи интернет-платежей. Причем не работает оплата по переводам, так как это не в рамках закона. При регистрации тренер должен зарегистрировать ИП.

Тренер может создать группу, в которую пользователи смогут добавляться. Занятия в группе будут дешевле. Если группа заполнилась или нет других свободных групп, то у пользователя нет возможности оплатить занятия, как групповые.

Существует возможность общения между тренером и клиентом при помощи встроенных личных сообщений.

Тренер может указать примерную область занятий и примерные упражнения, которые он будет требовать от своих клиентов. Таким образом клиент может точно определить, какой тренер ему подойдет наилучшим образом.

Сущности: тренера, клиенты, календарь тренировок, календарь питания, группа людей для тренировок, упражнение, деньги, личный счет, роль, занятие, еда, инвентарь.

### Этапы разработки
1. Первый этап
   - Исправить REST API:
     - Изменить типы и url запросов согласно принципа REST
     - Добавить валидацию для данных, передаваемых в http запросах
     - Добавить ExceptionApiHandler