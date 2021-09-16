[document](../../index.md) / [com.myapp.chatmemo.di](../index.md) / [DataModule](./index.md)

# DataModule

`@Module object DataModule`

### Functions

| Name | Summary |
|---|---|
| [provideAppDatabase](provide-app-database.md) | `fun provideAppDatabase(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`): `[`AppDatabase`](../../com.myapp.chatmemo.database/-app-database/index.md) |
| [provideCommentDao](provide-comment-dao.md) | `fun provideCommentDao(db: `[`AppDatabase`](../../com.myapp.chatmemo.database/-app-database/index.md)`): `[`CommentDao`](../../com.myapp.chatmemo.data.database.dao/-comment-dao/index.md) |
| [provideCoroutineDispatcher](provide-coroutine-dispatcher.md) | `fun provideCoroutineDispatcher(): CoroutineDispatcher` |
| [provideCoroutineScope](provide-coroutine-scope.md) | `fun provideCoroutineScope(): CoroutineScope` |
| [providePhrasetDao](provide-phraset-dao.md) | `fun providePhrasetDao(db: `[`AppDatabase`](../../com.myapp.chatmemo.database/-app-database/index.md)`): `[`PhraseDao`](../../com.myapp.chatmemo.data.database.dao/-phrase-dao/index.md) |
| [provideRoomDao](provide-room-dao.md) | `fun provideRoomDao(db: `[`AppDatabase`](../../com.myapp.chatmemo.database/-app-database/index.md)`): `[`RoomDao`](../../com.myapp.chatmemo.data.database.dao/-room-dao/index.md) |
| [provideTemplateDao](provide-template-dao.md) | `fun provideTemplateDao(db: `[`AppDatabase`](../../com.myapp.chatmemo.database/-app-database/index.md)`): `[`TemplateDao`](../../com.myapp.chatmemo.data.database.dao/-template-dao/index.md) |
