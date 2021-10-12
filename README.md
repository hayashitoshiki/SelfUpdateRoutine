# SelfUpdateRoutine
### 自己成長補助アプリ  
内省資質型による内省資質型のための内省化強化補助アプリ  
~~内省化やインプットを強制させることを目的とした補助アプリ~~

## 機能
・振り返り記録機能  
・過去の振り返り観覧機能  
・ミッションステートメント設定機能  
〜〜追加予定〜〜  
・デイリー閲覧URL表示機能  
・深掘りメモ機能

## 言語
Kotlin

## アーキテクチャ
全体  
・Clean Architecture  
Presentation層  
・（擬似）MVI ＋ 一部疑似Flux(Dispatcher)  
<img src="https://github.com/hayashitoshiki/SelfUpdateRoutine/blob/master/picture/SelfUpdateRoutine_%E3%83%A2%E3%82%B8%E3%83%A5%E3%83%BC%E3%83%AB%E6%A7%8B%E6%88%90.png" width="400">　
### アーキテクチャ構成図
<img src="https://github.com/hayashitoshiki/SelfUpdateRoutine/blob/master/picture/SelfUpdateRoutine_%E3%82%A2%E3%83%BC%E3%82%AD%E3%83%86%E3%82%AF%E3%83%81%E3%83%A3.png" >  
（擬似）MVIの詳細フロー  

https://github.com/hayashitoshiki/ComposeSample/blob/master/README.md  

## 主な使用技術
 #### ネイティブ(ライブラリ)
* DI
  * Hilt
* 非同期
  * Coroutine
  * Coroutine Flow
* Database
  * Room
* UI
  * Compose
  * LiveData
  * DataBinding
  * Navigation
  * ViewModel
  * CardView
  * Groupie
  * ViewPager2
* 開発補助
  * Timber
  * LeakCanary
  * ktLint
#### バックエンド
なし　

## 画面イメージ
<img src="https://github.com/hayashitoshiki/SelfUpdateRoutine/blob/master/picture/home.png" width="200">　
<img src="https://github.com/hayashitoshiki/SelfUpdateRoutine/blob/master/picture/setting.png" width="200">  

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

## ソースドキュメント
https://github.com/hayashitoshiki/SelfUpdateRoutine/blob/master/document/document/index.md
