# SelfUpdateRoutine
### 自己成長補助アプリ  
内省化やインプットを強制させることを目的とした補助アプリ

## 機能
・内省化強制機能  
・過去の振り返り観覧機能  
〜〜追加予定〜〜  
・デイリー閲覧URL表示機能

## 言語
Kotlin

## アーキテクチャ
MVVM(+ 一部Fluxもどき) + Clean Architecture

## 主な使用技術
 #### ネイティブ(ライブラリ)
* DI
  * Koin
* 非同期
  * Coroutine
  * Coroutine Flow
* Database
  * Room
* UI
  * LiveData
  * DataBinding
  * Navigation
  * ViewModel
  * CardView
  * Groupie
  * ViewPager2
#### バックエンド
なし　

## 画面イメージ
<img src="https://github.com/hayashitoshiki/SelfUpdateRoutine/blob/master/picture/home.png" width="200">  

## 主なソース

#### アプリ基盤(app)
https://github.com/hayashitoshiki/SelfUpdateRoutine/tree/master/app/src/main

#### 画面関連(presentasion)
https://github.com/hayashitoshiki/SelfUpdateRoutine/tree/master/presentation/src/main

#### 業務ロジック(domain)
https://github.com/hayashitoshiki/SelfUpdateRoutine/tree/master/domain/src/main/java/com/myapp/domain

#### データアクセス(data)
https://github.com/hayashitoshiki/SelfUpdateRoutine/tree/master/data/src/main/java/com/myapp/data

#### 共通処理(commone)
https://github.com/hayashitoshiki/SelfUpdateRoutine/tree/master/common/src/main/java/com/myapp/common
