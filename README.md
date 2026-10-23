# Projeto final do Bootcamp DIO: Java com IA - Itaú

Este projeto permite o envio, via API, de áudios em MP3 referentes a despesas que serão
processadas e como resultado será cadastrada uma despesa no banco de dados e depois
retornado um áudio em MP3 descrevendo a operação realizada.

## Para rodar a aplicação

1. Configure a variável de ambiente OPENAI_API_KEY com a chave de API para acesso ao ChatGPT

2. Execute o comando abaixo:

> ./mvnw spring-boot:run

## APIs - Swagger / OpenAPI

http://localhost:8080/swagger-ui.html

## Para testar (via CURL)

* Rodar a aplicação conforme orientações acima
* Abrir um terminal no Linux/Mac ou via Cygwin no Windows
* Navegar até a pasta raíz do projeto
* Executar o comandos abaixo para realizar os testes

### Cadastrar uma despesa via áudio

> curl -X POST -F 'audio=@"src/test/resources/audio/posto_gasolina.mp3"' localhost:8080/api/despesas/audio

### Listar todas as despesas

> curl localhost:8080/api/despesas

## Melhorias realizadas

- O projeto foi totalmente reescrito para entendimento completo dos conceitos apresentados em aula
- Foi implementado um endpoint para consultar todas as despesas no banco de dados
- Foi implementado um endpoint para receber um áudio de uma despesa, cadastrar a despesa no banco de dados e
  retornar um JSON referente com os dados da despesa cadastrada
- Foi implementado para salvar a categoria da despesa no banco de dados
- Foi alterado o banco de dados de MySQL para Postgres
- Foi alterado o envio de áudio para usar MediaType.MULTIPART_FORM_DATA_VALUE
- Foram escritos testes para os endpoints
- Foram escritos testes para os casos de uso
- Foram escritos testes para exercitar as funcionalidades do Spring AI
- Os testes de integração foram realizados usando TestContainers
- A documentação das APIs foi exposta usando Swagger / OpenAPI

## O que foi aprendido

- Transcrição de áudios usando Spring AI e OpenAI
- Geração de aúdio usando Spring AI e OpenAI
- Execução de prompts via ChatModel e ChatClient usando Spring AI e modelos da OpenAI
- Utilização de Tool Calling usando Spring AI
- Utilização de Structured Output usando Spring AI

## Tecnologias utilizadas

- Spring Boot 4
- Spring Data JPA
- Spring Web
- Spring AI / Open AI
- Postgres
- Docker Compose
- TestContainers

## Referências

Repositório Com Todos Os Conteúdos Sobre Spring:
https://github.com/digitalinnovationone/dio-spring-boot-learning-track

Pasta Com Detalhes Sobre O Projeto Final:
https://github.com/digitalinnovationone/dio-spring-boot-learning-track/blob/main/05-spring-ai/README.md


