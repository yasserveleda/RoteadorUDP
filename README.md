# RoteadorUDP

# OBS: Para descrições mais detalhadas, abra o arquivo Descricao Trabalho.pdf na raiz do diretório.

Trabalho Conjunto entre as Disciplinas de Sistemas Operacionais e Fundamentos de Redes de Computadores (PUCRS)

Professores: Cristina Nunes, Carlos R. Moratelli

# Objetivos

1. Utilizar programação multithreading;
2. Praticar técnicas de sincronização de processos;
3. Aprofundar conceitos de endereçamento de rede;
4. Troca de mensagens entre processos;
5. Roteamento de Rede;
6. Explorar o conceito de interoperabilidade.




# Descrição

Desenvolver uma aplicação que simule um roteador de rede e que faça a troca de tabelas
de roteamento, semelhante ao que foi apresentado em aula, utilizando sockets UDP para a
comunicação. A aplicação deve implementar o protocolo descrito na seção 3.
Inicialmente, deverá ser informado, na aplicação, os endereços IPs dos roteadores
vizinhos para que várias topologias diferentes possam ser simuladas. Cada roteador vizinho é
uma instância do roteador implementado executando em outra máquina física. Esses endereços
IPs deverão ser cadastrados em uma tabela de roteamento com métrica 1 e saída direta. Três
campos deverão estar presentes na tabela de roteamento: IP de Destino, Métrica e IP de Saída.
As tabelas de roteamento (apenas os campos IP de Destino e Métrica) deverão ser
trocadas entre os roteadores vizinhos a cada 10 segundos. Ao receber a tabela de roteamento de
seus vizinhos, a aplicação deverá verificar as rotas recebidas e fazer as atualizações necessárias
na tabela de roteamento local. Uma atualização deverá ser feita sempre que:
● for recebido um IP de Destino não presente na tabela local. Neste caso a rota deve
ser adicionada, a Métrica deve ser incrementada em 1 e o IP de Saída deve ser o endereço do
roteador que ensinou esta informação; 
● for recebida uma Métrica menor para um IP Destino presente na tabela local.
Neste caso, a Métrica e o IP de Saída devem ser atualizadas;
● um IP Destino deixar de ser divulgado. Neste caso, a rota deve ser retirada da
tabela de roteamento.
Um roteador pode sair da rede a qualquer momento. Isso significa que seus vizinhos não
receberão mais anúncios de rotas. Assim, depois de 30 segundos sem receber mensagens do
roteador vizinho em questão, as rotas que passam por ele devem ser esquecidas.
Periodicamente, a tabela de roteamento local deverá ser apresentada para o usuário. Além
disso, alterações na tabela de roteamento deverão ser informadas para os usuários (através de
prints na saída padrão).
A aplicação deverá rodar sobre o protocolo UDP



# Protocolo de comunicação

A implementação deve respeitar fielmente o formato de mensagens descrito a seguir. A
aplicação resultante deve ser interoperável, ou seja, implementações de diferentes grupos
devem ser capazes de se comunicar entre si. Desta maneira, poderá ser construída uma topologia
com roteadores implementados por diferentes equipes.
O protocolo consiste em apenas duas mensagens, conforme descrito a seguir.

# Considerações sobre a implementação
A aplicação deve ser multithread, ou seja, devem existir ao menos duas threads: uma para
receber mensagens dos vizinhos e atualizar a tabela de roteamento e outra para enviar a tabela de
roteamento para os vizinhos a cada 10 segundos, ou quando a mesma for alterada. Deve-se evitar
condições de corrida ao acesso (leitura/escrita) na tabela de roteamento.
Os endereços IP dos roteadores vizinho devem ser informados no arquivo denominado
IPVizinhos.txt (um por linha), conforme implementação de referência.
A interoperabilidade é uma questão fundamental em redes de computadores. Ela permite
que equipamentos de diferentes fabricantes operem em harmonia na rede. Para isso, as
especificações dos protocolos devem ser rigidamente implementadas. Espera-se neste trabalho,
que as diversas equipes apresentem implementações coerentes com a especificação e que permita
a interoperabilidade com as implementações das outras equipes.
Como base para o início da implementação, utilize o esqueleto de código fornecido pelos
professores. Tal implementação ilustra a comunicação através de sockets UDP. As mensagens
de anúncio de rotas devem ser enviadas para a porta 5000 do roteador vizinho.
