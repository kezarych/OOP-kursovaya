# Контекст проекта

Курсовая работа, тема 23: «Разработка автоматизированной системы поддержки деятельности менеджера по подбору кадров». Автор — Максим. Главная цель: показать на практике принципы ООП (наследование, абстракция, полиморфизм). Если чего-то не знаю или не уверен — прямо писать «нет информации», ничего не выдумывать. Если данных не хватает — задать уточняющий вопрос.

## Требования к коду
- Комментарии в коде НЕ писать вообще (ни однострочные, ни Javadoc).
- Код простой и читаемый, без лишних усложнений.
- Не менять описанную архитектуру без согласования со мной.
- Никаких БД, JPA, Spring Security, CSS и JavaScript.
- Никаких паттернов Visitor и двойной диспетчеризации, только обычное наследование и переопределение методов.

## Правила работы со мной
- Сам НИЧЕГО не пишу и не создаю, только если я явно попрошу.
- Не опираться жёстко на описанную информацию: я делаю под себя и могу менять всё на ходу.

## Стек
- Java 17+, Spring Boot, Maven
- Thymeleaf (карточки кандидатов заполняются в цикле th:each)
- Базы данных НЕТ: хранение в .txt файлах, чтение и запись через java.nio.file
- Верстка: простой «сухой» HTML

## Роли и страницы
Авторизации нет, роль выбирается кнопкой на главной.
1. `/`: кнопки «Я кандидат» -> /candidate и «Я менеджер» -> /manager.
2. `/candidate`: форма анкеты. POST сохраняет запись в candidates.txt и делает redirect (на главную). Редактировать нельзя, только создать новую.
3. `/manager`: два блока, «Новые» и «Принятые». Каждый кандидат отображается карточкой. В карточке новых есть кнопка «Принят»: POST дописывает id в accepted.txt и делает redirect на /manager. Из candidates.txt кандидат не удаляется.

## Поля анкеты
id (UUID, генерируется автоматически), имя, фамилия, описание, уровень английского (список), уровень программирования (список), запрашиваемая зарплата (число).

## Шкалы баллов
- Английский: A1=1, A2=2, B1=3, B2=4, C1=5, C2=6.
- Программирование: Junior-=3, Junior=4, Junior+=5, Middle-=6, Middle=7, Middle+=8, Senior-=9, Senior=10, Senior+=11. Ниже Junior- уровней нет.
- Оба списка реализуются как enum с полем score.

## Итоговый балл (eng = балл английского, prog = балл программирования)
- JuniorCandidate: eng * 2 + prog (максимум 17)
- MiddleCandidate: eng + prog (максимум 14)
- SeniorCandidate: eng + prog * 2 (максимум 28)
Баллы разных категорий напрямую не сравниваются, менеджер сравнивает кандидатов внутри категории. Итоговый балл в файле НЕ хранится, вычисляется при чтении.

## Какой наследник создаётся
По уровню программирования: Junior-/Junior/Junior+ -> JuniorCandidate; Middle-/Middle/Middle+ -> MiddleCandidate; Senior-/Senior/Senior+ -> SeniorCandidate. Объект создаёт CandidateFactory при чтении строки из файла и при сохранении новой анкеты.

## ООП-архитектура (ключевое требование)
- Person (абстрактный класс): имя, фамилия, getFullName()
- Manager extends Person
- Candidate extends Person (абстрактный): id, описание, английский, программирование, зарплата; абстрактные методы:
  - int getTotalScore()
  - String getCategory()
  - String getManagerHint()
  - String getRecommendation()
  - List<String> getInterviewStages()
- JuniorCandidate, MiddleCandidate, SeniorCandidate extends Candidate: переопределяют все абстрактные методы:
  - своя формула балла
  - своя категория и подсказка менеджеру
  - своя рекомендация (например: Junior - стажировка с наставником, Middle - сразу на проект, Senior - рассмотреть на роль тимлида)
  - свои этапы собеседования: Junior: тест, HR-интервью; Middle: тест, HR-интервью, техническое интервью; Senior: тест, HR-интервью, техническое интервью, системный дизайн
- CandidateFactory: создаёт нужного наследника по уровню программирования.
- Интерфейс CandidateRepository (save, findAll, accept, findAcceptedIds) и реализация FileCandidateRepository. Сервис работает только с интерфейсом.
- CandidateService и контроллеры работают с типом Candidate, а не с конкретными наследниками.
- Перегрузка методов (overloading) в CandidateService: три метода filter: filter(int minScore), filter(String category), filter(int minScore, int maxSalary).

## Что видит менеджер в карточке
Имя и фамилия (getFullName), категория (getCategory), уровень английского, уровень программирования, итоговый балл (getTotalScore), подсказка (getManagerHint), рекомендация (getRecommendation), этапы собеседования (getInterviewStages), запрашиваемая зарплата, описание. Кандидаты в блоках желательно сгруппированы или отсортированы по категории. В шаблоне вызываются одни и те же свойства для всех кандидатов, без проверки типа.

## Формат файлов
- candidates.txt: одна строка = один кандидат, разделитель «|»: id|имя|фамилия|описание|английский|программирование|зарплата. Символы «|» и переносы строк из пользовательского ввода удалять или заменять. Файл только дописывается.
- accepted.txt: одна строка = один id принятого кандидата.
- Если файла нет, он создаётся автоматически.

## Структура пакетов
- model (Person, Manager, Candidate, JuniorCandidate, MiddleCandidate, SeniorCandidate, EnglishLevel, ProgrammingLevel, CandidateFactory)
- repository (CandidateRepository, FileCandidateRepository)
- service (CandidateService)
- controller (MainController, CandidateController, ManagerController)
- resources/templates (index.html, candidate.html, manager.html)

## Отклонения от описания (решил я, Максим)
- id — int (НЕ UUID).
- Пакеты model/repository/service/controller НЕ делаем, все классы лежат в одном пакете kursovaya.oop.
- Enum'ы называются EnglishLVL и ProgrammingLVL (не EnglishLevel/ProgrammingLevel).
- Абстрактный метод списка этапов называется getInterviewStage() (единственное число).
- getFullName() возвращает firstName + " " + lastName.

## Статус разработки (актуально на последнюю сессию)
Готово:
- Person (абстрактный, имя, фамилия, getFullName)
- Manager extends Person
- EnglishLVL, ProgrammingLVL (enum со score)
- Candidate (абстрактный): конструктор с полями id/имя/фамилия/описание/английский/программирование/зарплата, геттеры getEnglishLVL/getProgrammingLVL, абстрактные методы getTotalScore/getCategory/getManagerHint/getRecommendation/getInterviewStage
- JuniorCandidate: getTotalScore реализован (eng*2+prog), остальные 4 абстрактных метода ещё пустые ("" / List.of())

В плане (по порядку):
1. Доделать JuniorCandidate: getCategory, getManagerHint, getRecommendation, getInterviewStage (тест, HR-интервью)
2. MiddleCandidate (eng+prog; тест, HR, техническое интервью) и SeniorCandidate (eng+prog*2; тест, HR, техническое, системный дизайн)
3. CandidateFactory
4. CandidateRepository + FileCandidateRepository
5. CandidateService (перегрузки filter)
6. Контроллеры + шаблоны (index.html, candidate.html, manager.html)