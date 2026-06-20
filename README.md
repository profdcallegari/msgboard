# msgboard

Uma aplicação web que permite aos usuários enviar e receber mensagens curtas (máximo 50 caracteres).

(c) Prof. Daniel Callegari, 2026

## Funcionalidades

- **Criar conta**: Cadastro de usuários com email e senha
- **Fazer login**: Autenticação no sistema
- **Enviar mensagem**: Enviar mensagens para usuários específicos (escolher dentre usuários cadastrados e digitar mensagem)
- **Ler mensagens**: Visualizar mensagens enviadas por outros usuários (página padrão ao logar)
- **Marcar como lida**: O receptor pode marcar uma mensagem como lida (toggle)
- **Ver status de leitura**: O emissor pode ver mensagens enviadas e se já foram lidas ou não
- **Logout**: Sair do sistema

## Tecnologias

- **Java**: Linguagem de programação
- **Vaadin**: Framework web para a interface do usuário

## Detalhes de Implementação

- O sistema não utiliza banco de dados; as informações são armazenadas em memória
- As mensagens são armazenadas em uma estrutura de dados adequada, com data/hora de envio e data/hora de leitura
- O sistema inicia com 5 usuários pré-criados:
  - demo1 (senha: 123)
  - demo2 (senha: 123)
  - demo3 (senha: 123)
  - demo4 (senha: 123)
  - demo5 (senha: 123)
- Não utiliza dependências além do Vaadin
- Uso obrigatório de lambda expressions

## Requisitos

- Java JDK instalado
- Maven (para gerenciamento de dependências)

## Como Executar

1. Clone o repositório
2. Navegue até o diretório do projeto
3. Execute o projeto com Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse a aplicação no navegador (geralmente em `http://localhost:8080`)

## Licença

Este projeto foi desenvolvido para fins educacionais.
