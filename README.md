# GitPay

Aplicação que permite ao usuário simular as operações básicas de um aplicativo de Conta Digital.
Este software foi desenvolvido por Kleber Kruger a fins de ensino. Foram utilizadas as linguagens Java e Kotlin,
além do framework Compose Multiplatform UI, usado no desenvolvimento de aplicações multiplataformas.


---- Arquitetura do Projeto ----

A arquitetura deste software foi baseada em conceitos de MVVM + Clean Architecture. No entanto, trata-se de uma versão
simplificada de ambas as arquiteturas. Mais detalhes sobre o desenvolvimento deste software pode ser acompanhado na
disciplina de Linguagens de Programação Orientada a Objetos, do curso de Sistemas de Informação, CPCX-UFMS.


---- Resumo das Funcionalidades ----

O usuário deste sistema pode:

A) Em relação a sua conta:
* Criar uma conta como Pessoa Física ou Pessoa Jurídica
* Editar suas informações pessoais
* Excluir sua própria conta

B) Em relação às operações bancárias:
* Tirar extrato da sua conta conforme um período selecionado
* Simular operações de depósito em sua própria conta
* Simular operações de saque de sua própria conta
* Fazer operações de transferência entre contas internas ou externas
* Realizar diferentes tipos de investimentos

Observações:

1) As operações de saque e depósito correspondem à simulação destes procedimentos em um caixa eletrônico;
2) As contas externas correspondem a contas de outros bancos. A fim de simular as operações entre bancos, estas contas
   foram pré-cadastradas na base de dados do programa.


---- Modelagem e Especificações do Projeto ----

- Há dois tipos de usuário: Pessoa Física ou Pessoa Jurídica;
- Uma Pessoa Física contém ao menos as seguintes informações obrigatórias: nome, cpf, telefone, email, e
  data de nascimento;
- Para uma Pessoa Jurídica, os atributos mínimos são: nome, razão social, cnpj, telefone, email;
- Para ambos, você também pode armazenar o Endereço, assim como outros atributos que considerar relevante;
- Ao criar uma conta, o usuário deve escolher uma senha, sendo o número da conta gerado automaticamente pelo sistema;
- O sistema não pode gerar contas com um mesmo número;
- Não é possível criar mais de uma conta para um mesmo CPF ou CNPJ;
- Cada conta digital deve possuir: número, saldo e limite (caso ache necessário). Observe que por se tratar de um banco
  totalmente digital, o número da agência não é necessário, mas você também pode adicionar caso queira;
- Sua conta deve registrar todas as transações efetuadas, guardando o tipo da transação (Depósito, Saque, Transferência,
  etc), o valor, a data e o horário;
- Você também pode implementar um modo de investimentos. Cada investimento conta com sua taxa de rendimento e demais
  regras individualizadas;
- Os dados da aplicação podem ser persistidos futuramente de diversas formas: arquivos locais no formato json ou xml;
  banco de dados (local ou remoto), ou via web service.


---- Dúvidas ----

Em caso de dúvidas sobre as especificações do projeto ou de sua implementação, mande um email para:
kleberkruger@gmail.com.br
