[document](../../index.md) / [com.myapp.chatmemo.database](../index.md) / [AppDatabase](./index.md)

# AppDatabase

`abstract class AppDatabase : RoomDatabase`

DB定義

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AppDatabase()`<br>DB定義 |

### Functions

| Name | Summary |
|---|---|
| [commentDao](comment-dao.md) | `abstract fun commentDao(): `[`CommentDao`](../../com.myapp.chatmemo.data.database.dao/-comment-dao/index.md) |
| [phraseDao](phrase-dao.md) | `abstract fun phraseDao(): `[`PhraseDao`](../../com.myapp.chatmemo.data.database.dao/-phrase-dao/index.md) |
| [roomDao](room-dao.md) | `abstract fun roomDao(): `[`RoomDao`](../../com.myapp.chatmemo.data.database.dao/-room-dao/index.md) |
| [templateDao](template-dao.md) | `abstract fun templateDao(): `[`TemplateDao`](../../com.myapp.chatmemo.data.database.dao/-template-dao/index.md) |
