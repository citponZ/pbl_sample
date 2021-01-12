#dockerでsqlサーバーを立ち上げる
docker run -e ACCEPT_EULA=Y -e SA_PASSWORD=password123@ -p 1460:1433 --name my_sql_server -d microsoft/mssql-server-linux

#dockerが起動するまで待つ
sleep 10

#SQLサーバーにログイン
sqlcmd -S 172.20.10.9,1460 -U SA -P password123@ < /Users/sofpedia/Desktop/pbl_sample/spring-boot-aks/sql/ddl.sql
