docker run --name DBSistemaSupermercado -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 -v volumeSistemaSupermercado:/var/lib/mysql -d mysql:latest
