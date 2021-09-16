# Module app
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
*  Domain --  Domain層
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

# Package com.myapp
アプリベース設定管理

# Package com.myapp.di

# Package com.myapp.common
共通拡張クラス管理

# Package com.myapp.data.local.database
データベース設定管理

# Package com.myapp.data.local.database.dao.mission_statement
ミッションステートメントテーブルへのクエリ管理

# Package com.myapp.data.local.database.dao.report
レポートテーブルへのクエリ管理

# Package com.myapp.data.local.database.entity.mission_statement
ミッションステートメントテーブル定義管理

# Package com.myapp.data.local.database.entity.report
レポートテーブル定義管理

# Package com.myapp.data.local
Localデータへのへのアクセス管理

# Package com.myapp.data.local.preferences
SharedPreference管理

# Package com.myapp.domain.dto
InputDataオブジェクト管理

# Package com.myapp.domain.model.entity
エンティティ管理

# Package com.myapp.domain.repository
リポジトリ定義管理

# Package com.myapp.domain.model.value
値オブジェクト管理

# Package com.myapp.domain.usecase
ビジネスロジック管理

# Package com.myapp.presentation.ui
UIベース画面管理

# Package com.myapp.presentation.ui.diary
デイリーレポート入力関連画面管理

# Package com.myapp.presentation.ui.home
ホーム関連画面管理

# Package com.myapp.presentation.ui.mission_statement
ミッションステートメント関連画面管理

# Package com.myapp.presentation.ui.remember
過去のレポート関連画面管理

# Package com.myapp.presentation.ui.setting
設定関連画面管理

# Package com.myapp.presentation.chat
チャットタブ画面管理

# Package com.myapp.presentation.template
テンプレート文設定タブ画面管理

# Package com.myapp.presentation.utils
UI用拡張クラス定義

# Package com.myapp.presentation.utils.transition
UI用アニメーションクラス管理

# Package com.myapp.selfupdateroutine
アプリ基盤管理