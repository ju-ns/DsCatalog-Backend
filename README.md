# 🛒 E-commerce API (Backend)

Backend de uma API RESTful para sistema de e-commerce, desenvolvida com Spring Boot.  
O projeto implementa autenticação OAuth2 com JWT, controle de acesso por roles, recuperação de senha por e-mail e operações completas de CRUD.

🚧 **Projeto em desenvolvimento**

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Authorization Server
- OAuth2 + JWT
- Spring Data JPA
- Hibernate
- Bean Validation
- H2 (test)
- PostgreSQL (produção)
- Maven

---

## 🔐 Segurança

O projeto utiliza:

- OAuth2 Authorization Server
- Custom Password Grant
- JWT auto-contido (Self-contained)
- Claims personalizadas no token:
  - `authorities`
  - `username`
- Controle de acesso via `@PreAuthorize`
- Criptografia de senha com BCrypt

---

## 👤 Funcionalidades Implementadas

### Usuários
- Cadastro
- Atualização
- Exclusão
- Paginação
- Consulta do usuário autenticado (`/users/profile`)
- Validação customizada de e-mail único

### Autenticação
- Geração de token via `/oauth2/token`
- Login com email e senha
- Recuperação de senha via token temporário
- Envio de e-mail para redefinição

### Categorias
- CRUD completo
- Paginação

### Produtos
- CRUD completo
- Paginação
- Filtro por nome
- Filtro por múltiplas categorias
- Consulta otimizada com projection

---

## 🧠 Arquitetura

O projeto segue o padrão:

Controller → Service → Repository → Database


Organização em camadas:
- `config`
- `controllers`
- `services`
- `repositories`
- `entities`
- `dto`
- `validation`
- `exceptions`

---

## 📬 Recuperação de Senha

Fluxo:

1. Usuário informa e-mail
2. Sistema gera token UUID com tempo de expiração
3. Token é salvo no banco
4. E-mail é enviado com link
5. Usuário redefine senha

---

## 🔎 Validações Customizadas

- `@UserInsertValid`
- `@UserUpdateValid`

Garantem:
- Unicidade de e-mail
- Integridade de atualização

---

## ⚙️ Configuração

Variáveis importantes no `application.properties`:

security.client-id
security.client-secret
security.jwt.duration
email.password-recover.token.minutes
cors.origins

📌 Status do Projeto

✅ Autenticação OAuth2 + JWT
✅ Authorization Server
✅ CRUD de Usuários, Produtos e Categorias
✅ Recuperação de senha
🔄 Testes automatizados (em evolução)
🔄 Melhorias de segurança
🔄 Documentação (Swagger/OpenAPI)

🧑‍💻 Autor

Projeto desenvolvido para fins de estudo e evolução profissional em backend Java.
