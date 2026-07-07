# sistema_supermercado_DSC

Um sistema de supermercado desenvolvido em java para a disciplina de Desenvolvimento de Sistemas corporativos do IFNMG campus Januária

---

Autores:

- Victor Vinicius Figueiredo Silva

- Eric Lopes Martins Guimarães

---

## ferramentas utilizadas

 ![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
 ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
 ![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white) 
 ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
 ![NetBeans IDE](https://img.shields.io/badge/NetBeansIDE-1B6AC6.svg?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
 ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

![Jaspersoft Studio](https://img.shields.io/badge/Jaspersoft%20Studio-005A9C?style=for-the-badge&logo=tibco&logoColor=white)

![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
 ![BCrypt](https://img.shields.io/badge/BCrypt%20%2F%20jBCrypt-4A4A4A?style=for-the-badge&logo=security&logoColor=white)

---

## 1. Clonar repositório

```bash
git clone https://github.com/VictorVinicius2004/sistema_supermercado_DSC.git
cd sistema_supermercado_DSC/
```

## 2. Banco de dados

Caso não tenha o docker instalado, instale:

**Windows e macOS:** Baixe e instale o Docker Desktop: https://www.docker.com/products/docker-desktop 

**Linux (Ubuntu/Debian):** 

```bash
curl -fsSL https://get.docker.com | sh
sudo usermod -aG docker $USER
```

Com o docker instalado, suba o conteiner contendo o banco de dados da aplicação(o banco de dados irá utilizar a porta 3306, tenha certeza de não ter dois bancos de dados utilizando essa porta ao mesmo tempo):

```bash
docker run --name DBSistemaSupermercado -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 -v volumeSistemaSupermercado:/var/lib/mysql -d mysql:latest
```

Espere o mysql no conteiner carregar e execute o .sql para criar o banco de dados:

```bash
docker exec -i DBSistemaSupermercado mysql -u root --default-character-set=utf8mb4 < BD.sql
```

## 3. Iniciando aplicação

Com o banco de dados criado basta apenas rodar o sistema Java, abrindo o projeto no Netbeans e rodando ou executando o .jar com o comando:

```bash
java -jar sistemaSupermercado-1.0-SNAPSHOT-jar-with-dependencies.jar
```

A aplicação começa com dois usuários:

| Usuário  | Senha    | Nível de Acesso                               |
|:-------- |:-------- |:--------------------------------------------- |
| `admin`  | `admin`  | **ADMINISTRADOR** (Acesso total + Relatórios) |
| `padrao` | `padrao` | **PADRÃO / CAIXA** (Acesso limitado à venda)  |








