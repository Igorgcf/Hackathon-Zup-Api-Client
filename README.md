# api_client

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

A api_client é uma aplicação web desenvolvida em Java utilizando o Spring Boot, que oferece um conjunto de 5 APIs RESTful para gerenciar entidades relacionadas a famílias, pessoas, filhos, papéis (roles) e usuários. O sistema implementa operações de CRUD (Create, Read, Update, Delete) completas e permite uma busca detalhada de dados específicos para cada entidade.

APIs Disponíveis
1. Family API
Esta API gerencia as famílias e permite:

- Criar novas famílias.
- Listar famílias com paginação.
- Atualizar dados de uma família existente.
- Deletar uma família.
- Buscar famílias por critérios específicos.

2. Person API
A API de Person gerencia os dados pessoais e permite:

- Adicionar novas pessoas a uma família.
- Listar pessoas associadas a uma família.
- Atualizar informações de uma pessoa.
- Remover uma pessoa da família.
- Realizar buscas detalhadas por first_name, CPF e outros atributos.

3. Children API
A API de Children gerencia os dados de filhos e permite:

- Criar novos registros de filhos associadas a uma família.
- Listar filhos associados ou não a uma familia.
- Atualizar informações de filhos.
- Remover filhos de uma família.
- Buscar filhos por age, first_name e outros atributos.

4. Role API
Gerencia os papéis (roles) no sistema e permite:

- Criar novos roles.
- Listar roles disponíveis.
- Atualizar informações de um role.
- Deletar um role.
- Buscar roles por id e authority.

5. User API
A API de User gerencia os usuários do sistema e permite:

- Criar novos usuários e associá-los a roles.
- Listar usuários com filtros e paginação.
- Atualizar dados de usuários.
- Remover usuários do sistema.
- Buscar usuários por id, username, email e cpf.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Technologies Used](#technologies-used)
- [Observation](#observation)
- [Contributing](#contributing)


## Installation

1. Clone the repository:

```bash
git clone https://github.com/IgorTecnologia/Hackathon-Zup-API-Client.git
```

2. Instale as dependências com o Maven

## Usage

1. Inicie a aplicação com o Maven
2. As API's estarão acessível em http://localhost:8080

## Collection Postman

Baixe esses arquivos e importe-os para o seu Postman para utilizar os métodos HTTP prontos junto com as variáveis ​​de ambiente já configuradas, para realizar as solicitações/respostas:

[Download Collections](https://github.com/IgorTecnologia/Hackathon-Zup-API-Client/blob/docs-postman/Hack-api-client.postman_collection.json)

[Download Environment variables](https://github.com/IgorTecnologia/Hackathon-Zup-API-Client/blob/docs-postman/Local-host.postman_environment.json)

## API Endpoints
Exemplo de end-points de User:

**GET USERS**
```markdown
GET /users - Recupera uma pagina com todos os usuários.
```
```json
{
    "content": [
        {
            "id": "6e850569-6a6f-4f54-83fc-3275eb6ee443",
            "username": "IgorTechnology",
            "email": "igor@gmail.com",
            "fullName": "Igor Gonçalves",
            "phoneNumber": "+55 19 98765-4321",
            "cpf": "123.456.789-0",
            "imageUrl": "www.image.com",
            "collectionDate": "2024-09-11T01:18:09.017543",
            "lastUpdateDate": "2024-09-11T01:18:09.017543",
            "roles": [
                {
                    "id": "67ee887a-bd3c-4d84-a0c6-05453e83b51c",
                    "authority": "Boss",
                    "links": []
                }
            ],
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/users/6e850569-6a6f-4f54-83fc-3275eb6ee443"
                }
            ]
        }
```
**GET USERS**
```markdown
GET /users/username/{username} - Recupera uma lista de usuários filtrados por username.
Exemple: GET /users/username/Igor
```
```json
   [
    {
        "id": "6e850569-6a6f-4f54-83fc-3275eb6ee443",
        "username": "IgorTechnology",
        "email": "igor@gmail.com",
        "fullName": "Igor Gonçalves",
        "phoneNumber": "+55 19 98765-4321",
        "cpf": "123.456.789-0",
        "imageUrl": "www.image.com",
        "collectionDate": "2024-09-11T01:18:09.017543",
        "lastUpdateDate": "2024-09-11T01:18:09.017543",
        "roles": [
            {
                "id": "67ee887a-bd3c-4d84-a0c6-05453e83b51c",
                "authority": "Boss",
                "links": []
            }
        ],
        "links": []
    }
]

```
**GET USERS**
```markdown
GET /users/email/{email} - Recuperar uma lista de usuários filtrados por e-mail ignorando maiúsculas e minúsculas.
Exemple: GET /users/email/Igor
```
```json
   [
    {
        "id": "6e850569-6a6f-4f54-83fc-3275eb6ee443",
        "username": "IgorTechnology",
        "email": "igor@gmail.com",
        "fullName": "Igor Gonçalves",
        "phoneNumber": "+55 19 98765-4321",
        "cpf": "123.456.789-0",
        "imageUrl": "www.image.com",
        "collectionDate": "2024-09-11T01:18:09.017543",
        "lastUpdateDate": "2024-09-11T01:18:09.017543",
        "roles": [
            {
                "id": "67ee887a-bd3c-4d84-a0c6-05453e83b51c",
                "authority": "Boss",
                "links": []
            }
        ],
        "links": []
    }
]

```
**GET USERS**
```markdown
GET /users/cpf/{cpf} - Recupera uma lista de usuários filtrados por cpf contendo algum dado do cpf sem a necessidade do cpf por completo.
Exemple: GET /users/cpf/123.456
```
```json
   [
    {
        "id": "6e850569-6a6f-4f54-83fc-3275eb6ee443",
        "username": "IgorTechnology",
        "email": "igor@gmail.com",
        "fullName": "Igor Gonçalves",
        "phoneNumber": "+55 19 98765-4321",
        "cpf": "123.456.789-0",
        "imageUrl": "www.image.com",
        "collectionDate": "2024-09-11T01:18:09.017543",
        "lastUpdateDate": "2024-09-11T01:18:09.017543",
        "roles": [
            {
                "id": "67ee887a-bd3c-4d84-a0c6-05453e83b51c",
                "authority": "Boss",
                "links": []
            }
        ],
        "links": []
    }
]
```
**GET USERS/ID**
```markdown
GET /users/id - Recupera um unico usuario por id.
```

```json
{
    "id": "6e850569-6a6f-4f54-83fc-3275eb6ee443",
    "username": "IgorTechnology",
    "email": "igor@gmail.com",
    "fullName": "Igor Gonçalves",
    "phoneNumber": "+55 19 98765-4321",
    "cpf": "123.456.789-0",
    "imageUrl": "www.image.com",
    "collectionDate": "2024-09-11T01:18:09.017543",
    "lastUpdateDate": "2024-09-11T01:18:09.017543",
    "roles": [
        {
            "id": "67ee887a-bd3c-4d84-a0c6-05453e83b51c",
            "authority": "Boss"
        }
    ]
}
```
**POST USERS**
```markdown
POST /users - Registra um novo usuario.
```
```json
{
    "username" : "igorTecnologia",
    "email": "igor@hotmail.com",
    "password" : "1234567",
    "fullName" : "Igor Gonçalves",
    "phoneNumber" : "+55 19 997541011",
    "cpf" : "123.456.789.0",
    "imageUrl" : "www.img.com"
}
```
**PUT USERS**
```markdown
PUT/users/id - Atualiza dados de usuário por id.
```
```json
{
    "fullName": "Igor Freitas",
    "phoneNumber": "+55 19 99123-4567",
    "cpf": "987.654.321.0",
    "imageUrl": "www.image.com"
}
```
**PUT USERS-PASSWORD**
```markdown
PUT/users/password/{id} - Atualiza senha por id de usuário.
```
```json
{
    "oldPassword" : "1234567",
    "password" : "12345678"
}
```
**DELETE USERS**
```markdown
DELETE/users/id - Apaga um usuário por id.
return HTTP OK e body: "User deleted successfully."

```
## Database
A aplicação utiliza de [PostgreSQL](https://www.postgresql.org/docs/) como banco de dados.

## Technologies Used

- Java Version 17
- Spring Boot
- Maven
- PostgreSQL
- Postman

## Observation
Existem outras API's dentro da aplicação localizada na camada de resources, como:

/families

/persons

/children

/roles

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.

Ao contribuir para este projeto, siga o estilo de código existente, [convenções de commit](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), e envie suas alterações em uma branch separado.

![image](https://w0.peakpx.com/wallpaper/281/257/HD-wallpaper-java-logo.jpg)
