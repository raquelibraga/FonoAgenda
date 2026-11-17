# FonoAgenda

## Status do projeto
Em desenvolvimento

## Objetivo do software
O FonoAgenda é um sistema desktop para o agendamento de consultas em uma clínica de fonoaudiologia. Ele serve a duas pessoas: a recepcionista, que cadastra pacientes e marca consultas e precisa de telas simples e de confirmação na tela após cada ação, e a fonoaudióloga, que consulta a agenda entre um atendimento e outro e precisa encontrar os horários do dia rapidamente.

## Funcionalidades do sistema (requisitos)
- Cadastrar paciente (nome, idade, queixa, telefone, e-mail e endereço).
- Cadastrar fonoaudiólogo (nome e especialidade).
- Marcar consulta escolhendo paciente, fonoaudiólogo, data e hora.
- Não permitir que o mesmo fonoaudiólogo tenha duas consultas no mesmo horário. A validação é feita por profissional: dois fonoaudiólogos diferentes podem atender no mesmo horário.
- Mostrar a grade de horários do dia, com os horários ocupados destacados em cinza.
- Listar o que já foi cadastrado, com busca por nome, edição e exclusão (com confirmação antes de excluir).
- Avisar claramente quando algum campo estiver errado e confirmar na tela quando uma ação for concluída.
- Guardar todos os dados em banco de dados MySQL.
- Interface pensada para acessibilidade, com base na WCAG 2.1 nível AA (cores com contraste alto, botões grandes e uso pelo teclado).

## Telas
- T01 - Menu Principal
- T02 - Cadastro de Paciente
- T03 - Cadastro de Fonoaudiólogo
- T04 - Agenda
- T05 - Cadastrados

## Tecnologias
- Java
- NetBeans
- Java Swing (interface desktop)
- MySQL e MySQL Workbench
- JDBC (MySQL Connector/J)
- Git e GitHub
- draw.io (projeto das telas)

## Time de desenvolvedores
- Raquel Braga de Souza ([@raquelibraga](https://github.com/raquelibraga))

## Como executar
1. Crie o banco de dados no MySQL Workbench executando o script fonoagenda.sql.
2. Abra o projeto no NetBeans.
3. Adicione o MySQL Connector/J (arquivo .jar) em Libraries.
4. Confira o usuário e a senha do MySQL na classe Conexao.java.
5. Execute a classe principal fonoagenda.
