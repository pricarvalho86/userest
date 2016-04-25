# Userest

API Rest de login simplificada sem manter dados na sessão do usuário

## Cadastro

* POST - http://userest.herokuapp.com/user/ 

```json
    {
        "name": "João da Silva",
        "email": "joao@silva.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "987654321",
                "ddd": "21"
            }
        ]
    }
```
## Login

* POST - http://userest.herokuapp.com/login

```json
  {
    "email": "joao@silva.org",
    "password": "hunter2"
  }
```
## Perfil do Usuário
  Deve setar a variável 'x-auth-token' no Header com o token gerado no cadastro ou no login

* POST - http://userest.herokuapp.com/user/address

```json
  {
    "street": "Rua da Concrete",
    "number": "123",
    "neighborhood": "Joana",
    "city": "Sao Paulo",
    "state": "SP",
    "zipcode": "03344-123"
  }
```

* GET - http://userest.herokuapp.com/user/address

```json
  {}
```


