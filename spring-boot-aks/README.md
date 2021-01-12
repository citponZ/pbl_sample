# アプリケーション起動方法
## 事前準備
必要な資材のインストール
dockerアカウントの作成
git clone

## SQL Serverを起動するシェルを編集

''' sh:pbl_sample/spring-boot-aks/sql.sh


#SQLサーバーにログイン 172.20.10.9　と　sql.shのパス　の部分は各自変更
sqlcmd -S 172.20.10.9,1433 -U SA -P password123@ < /Users/sofpedia/Desktop/pbl_sample/spring-boot-aks/sql/ddl.sql

'''


## SQLServerを起動
ターミナル上でsql.shを実行

## ソフぺディアコンテナを立ち上げる場合以下を実行(ローカルで起動する場合は飛ばす)
> cd /pbl_sample/spring-boot-aks/

dockerにログインしていない場合はログイン
> docker login

dockerイメージをBild[]は入力しない
> docker build . -t [自分のdocker　user name]/[任意の名前（推奨：sofpedia）]：[varsion]
docker psでビルドしたコンテナIDを確認
> docker run -p 8080:8080 -d [ID]

http://[自分のPCのIPアドレス]:8080/
にアクセスし起動できているか確認
※接続できない場合はhttp://[自分のPCのIPアドレス]:8080/aaa　に接続し404画面が表示るか確認




## ソフぺディアコンテナを立ち上げない場合
' /Users/sofpedia/Desktop/pbl_sample/spring-boot-aks/src/main/java/jp/co/softbank/fy20/springbootaks/SampleApplication.java '
を実行

http/localhost:8080/にアクセスし接続できていれば成功