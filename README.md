# _📚 Sistema de Aplicação e Correção de Provas_
Este sistema tem como objetivo informatizar o processo de criação, aplicação e correção de provas em uma escola. 
O sistema deve permitir o cadastro de turmas, professores, alunos, disciplinas e provas. Cada prova é associada 
a uma turma e uma disciplina, e contém um conjunto de questões objetivas (com gabarito) ou dissertativas (com
pontuação atribuída manualmente).

Os professores devem ser responsáveis por cadastrar as provas e aplicar as correções. Os alunos devem estar 
vinculados a turmas, podendo realizar provas atribuídas a eles e consultar suas notas. O sistema deve oferecer 
funcionalidades de geração de relatórios com desempenho por aluno, turma ou disciplina, além de permitir 
exportação em PDF ou CSV com formatação adequada.

## 🎲 Requisitos Funcionais
### 1. _Gerenciamento de Alunos, Professores e Disciplinas_
   - REQ01: Permitir o gerenciamento de alunos, com matrícula, nome completo, e-mail e turma associada.
   - REQ02: Permitir o gerenciamento de professores, com dados pessoais e disciplinas ministradas.
   - REQ03: Permitir o gerenciamento de disciplinas, incluindo nome, descrição e carga horária.
### 2. _Gerenciamento de Turmas_
   - REQ04: Permitir o gerenciamento de turmas, com identificação única, período (ex: 1º semestre/2025), e lista de alunos.
   - REQ05: Cada turma deve estar vinculada a uma ou mais disciplinas e professores responsáveis.
### 3. Criação de Provas
   - REQ06: Permitir que professores cadastrem provas para turmas específicas, associadas a uma disciplina.
   - REQ07: Permitir a inclusão de questões do tipo objetiva (com alternativas e gabarito) e dissertativa (com valor de pontuação).
   - REQ08: Definir para cada prova uma data de aplicação e tempo de duração.
   - REQ09: Não permitir a edição de provas após o início do período de aplicação.
### 4. Aplicação de Provas
   - REQ10: Permitir que alunos visualizem e realizem provas disponíveis dentro do prazo definido.
   - REQ11: Controlar o tempo de prova individual por aluno a partir do momento de início.
   - REQ12: Não permitir envio da prova após o prazo ou tempo esgotado.
   - REQ13: Salvar automaticamente as respostas enquanto o aluno realiza a prova.
### 5. Correção de Provas
   - REQ14: Corrigir automaticamente questões objetivas com base no gabarito definido.
   - REQ15: Permitir que o professor atribua notas manuais às questões dissertativas.
   - REQ16: Calcular a nota total da prova com base na soma das questões.
   - REQ17: Permitir reavaliação de questões dissertativas com justificativa do professor.
### 6. Consulta de Notas e Provas
   - REQ18: Permitir que alunos consultem notas e feedbacks das provas realizadas.
   - REQ19: Permitir que professores consultem o desempenho por aluno, por turma ou por prova.
### 7. Relatórios e Estatísticas
   - REQ20: Gerar relatório de desempenho por disciplina, por aluno e por turma, com médias e distribuição de notas.
   - REQ21: Permitir a exportação dos relatórios em PDF ou CSV, com cabeçalhos, filtros e agrupamentos por critério selecionado.
### 8. Regras e Restrições
   - REQ22: Um aluno não pode realizar a mesma prova mais de uma vez.
   - REQ23: Um professor não pode corrigir provas de turmas que ele não ministra.
   - REQ24: Alunos só podem ver o conteúdo das questões após o término do período de aplicação.

## 👩‍💻 Integrantes do grupo com nome completo
* Arthur Gabriel Souza Araujo - gsaraujo.arthur@gmail.com
* Evelin Paula Dionizio Da Silva - evelin.dionizio@ufrpe.br
* Everton Luan Gomes - evertonluan486@gmail.com
* Maria Fernanda Trevizane Buonafina - maria.buonafina@ufrpe.br

## 🌟 Funcionalidades Principais
- Cadastro de turmas, professores, alunos, disciplinas e provas.
- Cadastro de questões objetivas e dissertativas.
- Correção automática de questões objetivas com gabarito.
- Correção manual de questões dissertativas com atribuição de pontuação.
- Geração de relatórios de desempenho por aluno, turma ou disciplina.
- Exportação de relatórios em PDF ou CSV com formatação adequada.


