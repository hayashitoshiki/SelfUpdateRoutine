[document](../../index.md) / [com.myapp.presentation.ui.diary](../index.md) / [DiaryDispatcher](./index.md)

# DiaryDispatcher

`object DiaryDispatcher`

振替入り画面用ディスパッチャー

### Properties

| Name | Summary |
|---|---|
| [assessmentTextFlow](assessment-text-flow.md) | `val assessmentTextFlow: StateFlow<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [factTextFlow](fact-text-flow.md) | `val factTextFlow: StateFlow<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [findTextFlow](find-text-flow.md) | `val findTextFlow: StateFlow<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [improveTextFlow](improve-text-flow.md) | `val improveTextFlow: StateFlow<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [learnTextFlow](learn-text-flow.md) | `val learnTextFlow: StateFlow<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [reasonTextFlow](reason-text-flow.md) | `val reasonTextFlow: StateFlow<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [statementTextFlow](statement-text-flow.md) | `val statementTextFlow: StateFlow<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [changeAssessment](change-assessment.md) | `suspend fun changeAssessment(inputNumber: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [changeFact](change-fact.md) | `suspend fun changeFact(inputText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [changeFind](change-find.md) | `suspend fun changeFind(inputText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [changeImprove](change-improve.md) | `suspend fun changeImprove(inputText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [changeLesson](change-lesson.md) | `suspend fun changeLesson(inputText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [changeReason](change-reason.md) | `suspend fun changeReason(inputText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [changeStatement](change-statement.md) | `suspend fun changeStatement(inputText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
