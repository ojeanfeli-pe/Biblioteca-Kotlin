# Controle de Biblioteca

Um aplicativo Android que permite aos funcionários gerenciar o acervo da biblioteca, incluindo livros, empréstimos e devoluções.

## Funcionalidades Principais

### Tela Principal
- Apresenta um resumo e visão geral do acervo da biblioteca.
- Fornece botões para acessar rapidamente as principais funcionalidades:
  - Gerenciar livros.
  - Gerenciar empréstimos.
  - Acessar relatórios.

### Tela de Gerenciamento de Livros
- Exibe a lista de livros cadastrados.
- Permite:
  - Adicionar novos livros.
  - Editar livros existentes.
  - Remover livros.
- Inclui uma função de busca para localizar livros por **título** ou **autor**.

### Tela de Empréstimos
- Mostra todos os empréstimos ativos.
- Permite:
  - Registrar novos empréstimos (associando um livro a um usuário).
  - Registrar a devolução de um livro.

### Tela de Relatórios
- Exibe estatísticas e informações sobre o uso da biblioteca, como:
  - Livros mais emprestados.
  - Relatórios detalhados para análise e acompanhamento do acervo.

## Navegação entre Activities

O esquema de navegação do aplicativo segue o seguinte fluxo:
Tela Principal → Tela de Gerenciamento de Livros → Tela de Empréstimos → Tela de Relatórios

- **Tela Principal**: Direciona o usuário para as demais funcionalidades.
- **Tela de Gerenciamento de Livros**: Permite visualizar e editar o acervo.
- **Tela de Empréstimos**: Gerencia os empréstimos ativos.
- **Tela de Relatórios**: Exibe dados estatísticos sobre o uso da biblioteca.

## Planejamento do Banco de Dados com Room

### Entidades

#### **Livro**
- `id`: Int (Chave primária, auto-incremento)
- `titulo`: String
- `autor`: String
- `anoPublicacao`: Int
- `disponivel`: Boolean (Indica se o livro está disponível para empréstimo)

#### **Usuario**
- `id`: Int (Chave primária, auto-incremento)
- `nome`: String
- `email`: String

#### **Emprestimo**
- `id`: Int (Chave primária, auto-incremento)
- `dataEmprestimo`: Long (Timestamp)
- `dataDevolucao`: Long (Timestamp)
- `livroId`: Int (Chave estrangeira referenciando **Livro**)
- `usuarioId`: Int (Chave estrangeira referenciando **Usuario**)

### Relacionamentos
- **Livro e Empréstimo**: Relacionamento 1:N (Um livro pode estar associado a vários empréstimos, mas cada empréstimo pertence a um único livro).
- **Usuário e Empréstimo**: Relacionamento 1:N (Um usuário pode realizar múltiplos empréstimos, mas cada empréstimo pertence a um único usuário).

Esses relacionamentos permitem:
- Controlar quem emprestou qual livro e quando.
- Facilitar a gestão do acervo e do histórico de empréstimos.

## Tecnologias Utilizadas
- **Kotlin**: Linguagem de programação principal.
- **Room**: Para persistência de dados.
- **Android Jetpack**: Para a arquitetura e navegação.
- **Material Design**: Para a interface do usuário.

- ## Para você que deseja Executar, siga esses passos:
1. Clone este repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
