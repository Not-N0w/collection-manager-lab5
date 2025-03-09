# Лабораторная работа #5

[Документация](#https://not-n0w.github.io/lab5/)

## Структура
3 блока: client, common, ticketController(server)

## Дерево проекта
```bash
src/main/java/com/labs/
├── App.java
├── client
│   ├── Client.java
│   ├── CommandDataParser.java
│   ├── Cycle.java
│   ├── DataManager.java
│   ├── FileManager.java
│   ├── Input.java
│   ├── Output.java
│   ├── ValueChecker.java
│   ├── extra
│   │   └── Pair.java
│   ├── gson
│   │   ├── LocalDateAdapter.java
│   │   └── LocalDateTimeAdapter.java
│   └── localCommandManager
│       ├── CommandManager.java
│       ├── Invoker.java
│       └── commands
│           ├── ExecuteScriptCommand.java
│           ├── ExitCommand.java
│           ├── HelpCommand.java
│           └── SaveCommand.java
├── common
│   ├── AbstractInvoker.java
│   ├── Command.java
│   ├── DataContainer.java
│   ├── Executable.java
│   ├── core
│   │   ├── Coordinates.java
│   │   ├── Location.java
│   │   ├── Person.java
│   │   ├── Ticket.java
│   │   └── TicketType.java
│   ├── dataConverter
│   │   ├── Deserializer.java
│   │   └── Serializer.java
│   ├── exeptions
│   │   └── KeyNotFoundExeption.java
│   └── transmitter
│       ├── Request.java
│       └── Response.java
└── ticketController
    ├── CollectionManager.java
    ├── Invoker.java
    ├── TicketController.java
    └── commands
        ├── AddCommand.java
        ├── AddIfMaxCommand.java
        ├── AddIfMinCommand.java
        ├── AddSomeCommand.java
        ├── AverageOfPriceCommand.java
        ├── ClearCommand.java
        ├── CountGreaterThanRefundableCommand.java
        ├── FilterGreaterThanRefundableCommand.java
        ├── InfoCommand.java
        ├── RemoveByIdCommand.java
        ├── RemoveGreaterCommand.java
        ├── ShowCommand.java
        └── UpdateCommand.java
```

## Поправки в процессе:
 - Документация
