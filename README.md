# Transactions API

API Rest Java com SpringBoot, Docker, documentação com Swagger e cache com Caffeine.

### Conteúdo
- [Projeto e Arquitetura](#projeto-e-arquitetura)
  - [Explicação da Arquitetura](#explicação-da-arquitetura)
  - [Instalação e Execução](#instalação-e-execução)
- [Tecnologias](#tecnologias)
- [Infraestrutura](#infraestrutura)
  - [Swagger](#swagger)
- [Testes](#testes)
  - [Testes Unitários](#testes-unitários)
- [TODO](#todo)

## Projeto e Arquitetura
### Explicação da Arquitetura

Para a criação da API, foi utilizado o padrão arquitetural REST, que pode ser definido como um conjunto de recursos, esses recursos são identificados na requisição. Um recurso pode ter diversas ações, que são métodos disponíveis pelo protocolo HTTP;

Essa API possui apenas um recurso que é o 'transacoes'.

A ação disponível para esse recurso é:
- GET:
  - Por Id: http://localhost:8080/{id}/transacoes/{ano}/{mes}

### Instalação e Execução

#### Pré Requisitos
- Docker 
- Docker Compose
- Java 
- Gradle 

#### Etapas

1º Clone o repositório através do comando  `git clone `

2º Entre no diretório 

3º Execute o comando `make run` 

4º Ao final do processo o console deve ficar igual a imagem abaixo: 

![terminal](https://user-images.githubusercontent.com/25140680/91764694-24a74400-ebae-11ea-95fe-7cae4a254aeb.png)

#### Observações

1. Para o processo de geração de imagem, não foi utilizado um dockerfile como padrão. Para esta tarefa foi utilizado um framework Open Source chamado Jib. 
2. Caso alguma das portas utilizada nessa aplicação esteja sendo utilizada no seu computador, será necessário trocar a porta em uso por outra no arquivo docker-compose.yml


## Tecnologias
- Docker
- Java com SpringBoot
- Caffeine para armazenamento de dados em cache na aplicação
- Swagger para auxiliar a documentação da API
- Teste Unitário com JUnite e Mockito

## Infraestrutura
### Swagger
Visando seguir a especificação OpenAPI, que permite o melhor entendimento dos recursos do serviço, foi utilizado o Swagger para documentação do mesmo, podendo ser acessado pelo caminho http://localhost:8080/ após execução da aplicação.

## Testes

### Testes Unitários
Foram escritos testes unitários para a aplicação, onde foram utilizandos o framework JUnit e a biblioteca Mockito. As classes com testes escritos foram: TransactionController e TransactionService.


#### Exemplo de um código de teste


```
    @Test
    public void testSuccessFindTransactions() throws Exception{

        int id =1;
        String year = "2020";
        String month = "2";
        Object[] params = new Object[]{id, year, month};
        String url = MessageFormat.format("/{0}/transacoes/{1}/{2}", params);

        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction t = Transaction.builder()
                        .description("TESTE")
                        .value(1000)
                        .date(10000l)
                        .duplicated(false)
                        .build();
        transactions.add(t);

        when(mockService.findTransactions(id,year,month)).thenReturn(transactions);

        MockHttpServletResponse response = mockMvc.perform(
                get(url)
                 .accept(MediaType.APPLICATION_JSON))
                 .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"description\":\"TESTE\",\"date\":10000,\"value\":1000,\"duplicated\":false}]",
                                response.getContentAsString());
    }
```

## TODO
- Maior cobertura de código com Testes unitários
- Melhorias na escrita dos testes
- Deploy da aplicação no Heroku
- Testes de carga
- Refinamento das regras de negócio
