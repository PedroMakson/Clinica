<h1 align="center">Sistema de Gerenciamento de Consultas para Clínicas👨🏼‍⚕️</h1>

## ❓ Sobre o projeto
   <p align="justify">Em um cenário dinâmico e desafiador no setor de saúde, onde a eficiência no agendamento e gerenciamento de consultas desempenha um papel crucial para assegurar um atendimento de qualidade aos pacientes, apresentamos o projeto de desenvolvimento de um "Sistema de Gerenciamento de Agendamentos para Clínicas" como parte avaliativa da disciplina "Teste de Software".
</p>
   <p align="justify">Reconhecendo a importância de integrar tecnologia moderna às necessidades práticas das clínicas, nosso objetivo é oferecer uma solução abrangente que traga benefícios tanto para os pacientes quanto para os profissionais de saúde. Dessa forma, o sistema será desenvolvido na linguagem Java, seguindo uma abordagem orientada a objetos e adotando o modelo MVC (Model-View-Controller). Essas escolhas tecnológicas visam garantir robustez, flexibilidade e fácil manutenção. O sistema busca otimizar o agendamento, melhorar a eficácia operacional das clínicas e proporcionar uma experiência aprimorada aos pacientes, adaptando-se às demandas dinâmicas do ambiente clínico.
</p> 
   <p align="justify">Neste contexto, acreditamos que a implementação deste sistema não apenas facilitará a marcação de consultas, mas também contribuirá para a eficácia operacional da clínica, melhorando a experiência do paciente e permitindo uma alocação mais eficiente dos recursos clínicos. Com uma abordagem centrada no usuário e funcionalidades que abrangem desde o agendamento até a gestão de recursos, o sistema se propõe a ser uma solução inovadora e adaptável às demandas dinâmicas do ambiente clínico.</p> 

## 📋 Requisitos
</t><h3 align="justify">🔹 Requisitos Funcionais</h3>

<h4>[RF001] - Sistema de login</h4> 
    <p align="justify">- O sistema deve permitir que o usuário tenha acesso a aplicação e suas funcionalidades quando informado os dados/credenciais necessários para logar. Caso seja informado um acesso inexistente, e-mail ou senha inválidos, o sistema deve retornar um aviso detalhando o problema.</p>

<h4>[RF002] - Cadastro de funcionários</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar o cadastro de novos funcionários, informando os seus respectivos dados (Nome Completo, CPF, Data de Nascimento, E-mail, Telefone, etc), após isso, quando confirmado o sistema exibe uma mensagem de que o cliente foi cadastrado com sucesso.</p>

<h4>[RF003] - Editar cadastro de funcionários</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar a edição de dados dos funcionários. Após a edição, o sistema exibe uma mensagem de sucesso.</p>

<h4>[RF004] - Cadastro de pacientes</h4> 
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar o cadastro de novos pacientes, informando os seus respectivos dados (Nome Completo, CPF, Data de Nascimento, E-mail, Telefone, etc), após isso, quando confirmado o sistema exibe uma mensagem de que o cliente foi cadastrado com sucesso.</p>

<h4>[RF005] - Editar cadastro de paciente</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar a edição de dados dos clientes. Após a edição, o sistema exibe uma mensagem de sucesso.</p>

<h4>[RF006] - Cadastro de serviços</h4> 
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar o cadastro de serviços, informando os seus respectivos dados (Nome do Serviço, Descrição, Valores, etc), após isso, quando confirmado o sistema exibe uma mensagem de que o serviço foi cadastrado com sucesso.</p>

<h4>[RF007] - Editar cadastro de serviços</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar edições nas informações dos serviços, como por exemplo, alterar o nome, descrição e valor.</p>

<h4>[RF008] - Agendamento de consultas</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar o agendamento de consultas. Serão informados os respectivos dados (Paciente, Serviço, Local, Data, Horário da consulta, etc), caso não tenha outra consulta na mesma data/horário o sistema faz o novo agendamento com sucesso.</p>
    
<h4>[RF009] - Editar consultas</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar a edição de algumas informações das consultas marcadas, como por exemplo, a data, horário e local da consulta.</p>

<h4>[RF010] - Visualizar consultas</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar a visualização das consultas agendadas para os seus pacientes.</p>

<h4>[RF011] - Cancelamento de consultas</h4>
    <p align="justify">- O sistema deve permitir que o usuário  com nível de acesso profissional possa realizar o cancelamento/exclusão da consulta marcada para o paciente. Após o cancelamento, o sistema exibe uma mensagem de sucesso.</p>

<h4>[RF012] - Prescrições médicas</h4>
    <p align="justify">- O sistema deve permitir que o usuário com nível de acesso profissional possa realizar prescrições médicas para os seus pacientes. No momento de realizar as prescrições será informado os respectivos dados (Paciente, Prescritor, Data, Medicamento, Instruções de Uso, etc).</p>

<h4>[RF013] - Visualização de prontuário</h4>
    <p align="justify">- O sistema deve manter um registro eletrônico das consultas, tratamentos/serviços realizados, prescrições e exames, garantindo a fácil recuperação das informações dos pacientes. Os dados serão exibidos a partir do período informado pelo usuário.</p>

<h4>[RF014] - Relatório financeiro</h4>
    <p align="justify">- O sistema deve manter e gerar um histórico financeiro detalhado, incluindo pagamentos e valores pendentes. Os dados serão exibidos a partir do período informado.</p>

<h4>[RF015] - Sistema de logout</h4>
    <p align="justify">- O sistema deve permitir que o usuário deslogue da aplicação, deixando de ter acesso às suas funcionalidade.<p>
	    
##
<h3 align="justify">🔸 Requisitos Não Funcionais</h3>

<h4>[RNF001] - Validação de login</h4> 
    <p align="justify">- O sistema deve validar se as credenciais informadas estão corretas ou cadastradas na base de dados, caso contrário, deve emitir uma mensagem informando que o login/senha estão incorretos e/ou usuário inexistente.</p>

<h4>[RNF002] - Validação de dados de entradas</h4>
    <p align="justify">- O sistema deve conter validação de dados de entrada no sistema, fazendo a verificação de: campos obrigatórios, formatos aceitáveis, comprimento adequado, caracteres especiais e restrições.</p>

<h4>[RNF003] - Linguagem Java</h4>
<p align="justify">- O sistema deve ser implementado em linguagem Java.</p>

<p align="right">
   Documentação dos <a href="https://docs.google.com/document/d/1LuUPISf02Jz1BWatXY0dPWpjax62CK_r2kCl8-Ks2nA/edit?usp=sharing" target="_blank">Requisitos</a>
</p>

## 📊 Diagramas
<h3 align="justify">📑 Diagrama de Classes</h3>

![Diagrama de Classe](https://github.com/PedroMakson/Clinica/blob/main/Diagramas/Diagrama%20de%20Classe.png?raw=true)
<p align="right">
   Figura 01: <a href="https://drive.google.com/file/d/1knjeE-tAWnVb2sQfxavC_-nNRNZfMtOv/view?usp=sharing" target="_blank">Diagrama de Classes</a>
</p>

## 👨‍🎓 Discentes
* [Arthur Kellyson Pinheiro de Negreiros](https://github.com/Arthurkellysonp)</br>
* [Luis Davi da Silva Sarmento](https://github.com/lluisdavi)</br>
* [Pedro Makson Fontes da Costa](https://github.com/PedroMakson)</br>

 ## 👨‍🏫 Professor
 * [Alysson Filgueira Milanez](https://github.com/alyssonfm)<br/>

 ##  🛠 Feito com
* [Astah UML](https://astah.net/downloads/)
    * Criação do diagrama de classe;
* [Visual Studio Code](https://code.visualstudio.com/)
    * Criação do código;
* [Git e Github](https://github.com/liviabeatrizml/GradeHoraria)
    * Controle de versão e hospedagem do código;
* [PostgreSQL](https://www.postgresql.org/)
   * Banco de Dados;
* [Meet](https://meet.google.com/)
	 * Chamada via meet (Compartilhamento de tela ao vivo e reuniões);
* [Documentos Google](https://docs.google.com/document/d/1LuUPISf02Jz1BWatXY0dPWpjax62CK_r2kCl8-Ks2nA/edit?usp=sharing)
    * Processador de texto on-line;
##

<p align="right">
   Feito por <a href="https://github.com/Arthurkellysonp" target="_blank">Arthur Kellyson</a>,
   <a href="https://github.com/lluisdavi" target="_blank"> Luis Davi</a> e
   <a href="https://github.com/PedroMakson" target="_blank"> Pedro Makson</a>
</p>