[document](./index.md)

SelfUpdateRoutine

### アーキテクチャ

MVVM + Clean Architecture + DDD

## 構成

* Data -- Data層
  * Local -- Localデータアクセス関連
      * DataBase -- データベース関連
          * Dao -- データベースへのクエリ管理
          * Entity -- データベースのテーブル管理
      * Preference -- SharedPreference関連
      * Repository -- LocalデータへのCRUD処理管理
* Domain --  Domain層
   * Dto -- InputDataオブジェクト管理
   * Model -- ドメインモデル管理
      * Entity -- エンティティ管理
      * Value -- 値オブジェクト管理
   * UseCase -- ビジネスロジック管理
   * Translator -- ドメインモデル変換管理
   * Repository -- Repositoryインターフェース管理
* UI -- Presentation層
  * Utils 共通部分管理
  * ○○　○○タブ内の画面管理

### Packages

| Name | Summary |
|---|---|
| [com.myapp.common](com.myapp.common/index.md) | 共通拡張クラス管理 |
| [com.myapp.data.local](com.myapp.data.local/index.md) | Localデータへのへのアクセス管理 |
| [com.myapp.data.local.database](com.myapp.data.local.database/index.md) | データベース設定管理 |
| [com.myapp.data.local.database.dao.mission_statement](com.myapp.data.local.database.dao.mission_statement/index.md) | ミッションステートメントテーブルへのクエリ管理 |
| [com.myapp.data.local.database.dao.report](com.myapp.data.local.database.dao.report/index.md) | レポートテーブルへのクエリ管理 |
| [com.myapp.data.local.database.entity.mission_statement](com.myapp.data.local.database.entity.mission_statement/index.md) | ミッションステートメントテーブル定義管理 |
| [com.myapp.data.local.database.entity.report](com.myapp.data.local.database.entity.report/index.md) | レポートテーブル定義管理 |
| [com.myapp.data.local.preferences](com.myapp.data.local.preferences/index.md) | SharedPreference管理 |
| [com.myapp.domain.dto](com.myapp.domain.dto/index.md) | InputDataオブジェクト管理 |
| [com.myapp.domain.model.entity](com.myapp.domain.model.entity/index.md) | エンティティ管理 |
| [com.myapp.domain.model.value](com.myapp.domain.model.value/index.md) | 値オブジェクト管理 |
| [com.myapp.domain.repository](com.myapp.domain.repository/index.md) | リポジトリ定義管理 |
| [com.myapp.domain.usecase](com.myapp.domain.usecase/index.md) | ビジネスロジック管理 |
| [com.myapp.presentation.ui](com.myapp.presentation.ui/index.md) | UIベース画面管理 |
| [com.myapp.presentation.ui.diary](com.myapp.presentation.ui.diary/index.md) | 日記関連画面管理 |
| [com.myapp.presentation.ui.home](com.myapp.presentation.ui.home/index.md) | ホーム関連画面管理 |
| [com.myapp.presentation.ui.mission_statement](com.myapp.presentation.ui.mission_statement/index.md) | ミッションステートメント関連画面管理 |
| [com.myapp.presentation.ui.remember](com.myapp.presentation.ui.remember/index.md) | 過去のレポート関連画面管理 |
| [com.myapp.presentation.ui.setting](com.myapp.presentation.ui.setting/index.md) | 設定関連画面管理 |
| [com.myapp.presentation.utils](com.myapp.presentation.utils/index.md) | UI用拡張クラス定義 |
| [com.myapp.selfupdateroutine](com.myapp.selfupdateroutine/index.md) | アプリ基盤管理 |

### Index

[All Types](alltypes/index.md)