# ⚡ ElektroCraft – Documento de Design Técnico

**Versão base:** Minecraft NeoForge 1.21.10  
**Tema:** Eletrônica e energia aplicadas ao universo técnico do Minecraft  
**Tipo:** Mod tecnológico e progressivo, inspirado em eletrônica real e automação industrial

---

## 🧭 **Visão Geral**

O **ElektroCraft** é um mod técnico que introduz **eletrônica realista**, **energia elétrica** e **automação lógica** no
Minecraft.  
O progresso do jogador avança desde a mineração de metais básicos até a criação de **máquinas inteligentes e fontes de
energia quântica**.

---

## ⚙️ **Itens e Componentes**

### 🔩 Componentes Eletrônicos Passivos

- Resistor
- Capacitor
- Indutor (bobina)
- Diodo
- Potenciômetro (resistor variável)
- Termistor (sensor térmico)
- LDR (sensor de luz)

### ⚡ Fontes e Armazenamento de Energia

- Bateria simples
- Bateria recarregável
- Célula solar
- Gerador manual / dínamo
- Transformador
- Fio condutor (cobre, ferro, prata, ouro)
- Conector elétrico

### 🧠 Componentes Ativos e Lógicos

- Transistor (NPN / PNP)
- LED
- Fotodiodo
- Circuito integrado (IC genérico)
- Porta lógica (AND, OR, NOT, XOR, NAND, NOR)
- Temporizador (Timer 555)
- Relé (chave controlada por corrente)

### 🧰 Materiais e Subcomponentes

- PCB (placa de circuito impresso)
- PCB gravada
- Chip de silício
- Fio esmaltado
- Plástico isolante
- Socket (encaixe)
- Solda (liga de estanho)
- Pasta térmica

### 🔧 Ferramentas

- Ferro de solda
- Alicate de corte
- Multímetro
- Chave de fenda
- Manual técnico (item guia/tutorial)

---

## 🧱 **Tiers Tecnológicos e Progressão**

### 🪓 **TIER 0 – Materiais Brutos**

- Minérios: Cobre, Estanho, Chumbo, Prata
- Recursos naturais: Carvão, Quartzo, Petróleo/Resina
- Produtos processados:
    - Barra de cobre
    - Fio de cobre
    - Plástico bruto

➡️ *Fundação de todos os itens técnicos.*

---

### ⚙️ **TIER 1 – Componentes Básicos**

- Fio condutor
- Resistor
- Capacitor
- Diodo
- Indutor
- LED
- Fonte simples

➡️ *Desbloqueia circuitos simples e energia básica.*

---

### 🧲 **TIER 2 – Circuitos Intermediários**

- PCB
- Transistor
- Circuito básico
- Portas lógicas
- Relé
- Temporizador 555

➡️ *Permite automações elétricas e controle de sinais.*

---

### 🔋 **TIER 3 – Energia e Controle**

- Gerador simples
- Painel solar
- Bateria recarregável
- Transformador
- Controlador elétrico

➡️ *Sistemas elétricos contínuos e gerenciamento de energia.*

---

### 🧲 **TIER 3.5 – Eletromagnetismo Aplicado**

O domínio do **eletromagnetismo** marca a transição entre o controle elétrico e o movimento mecânico.  
O jogador começa a usar a **interação entre corrente elétrica e campos magnéticos** para criar **força, propulsão e
indução de energia**.

#### 🔧 **Componentes e Blocos Principais**

- **Bobina eletromagnética** – núcleo de ferro com fio de cobre enrolado; gera campo magnético ao energizar.
- **Eletroímã** – bloco que atrai ou repele metais quando ligado.
- **Motor elétrico** – converte energia elétrica em rotação (usado em máquinas, esteiras ou geradores).
- **Gerador de indução** – converte rotação em energia elétrica (efeito reverso do motor).
- **Campo magnético de contenção** – estrutura usada para estabilizar o *Reator Nuclear* e sistemas de alta energia.
- **Sensor de movimento magnético** – detecta entidades metálicas próximas.
- **Plataforma magnética / trilho eletromagnético** – base para transporte automatizado (maglevs, elevadores, braços
  robóticos).

#### ⚙️ **Mecânicas introdutórias**

- **Corrente e polaridade:** a direção da corrente define o sentido da força magnética.
- **Aquecimento:** componentes magnéticos geram calor sob uso intenso (requer refrigeração).
- **Eficiência energética:** motores e geradores têm perda por atrito, ajustável com materiais melhores (ferro → aço →
  supercondutor).
- **Integração com circuitos:** sensores e controladores elétricos podem ativar ou modular a força magnética em tempo
  real.

#### 🧩 **Crafts e Interações**

| Item / Bloco           | Requisitos                                        | Função                                             |
|------------------------|---------------------------------------------------|----------------------------------------------------|
| **Eletroímã**          | Bobina + Ferro + Circuito básico                  | Atrai ou repele blocos metálicos quando energizado |
| **Motor elétrico**     | Bobina + Eixo + Transformador                     | Gera rotação constante a partir de energia         |
| **Gerador de indução** | Motor + Bobina inversa                            | Gera energia a partir de movimento                 |
| **Campo de contenção** | Eletroímã + Refrigeração + Circuito intermediário | Mantém plasma ou energia confinada                 |
| **Sensor magnético**   | Termistor + LDR + Bobina                          | Detecta movimento ou campo variável                |

#### ⚡ **Desbloqueios futuros**

A introdução do eletromagnetismo abre novas tecnologias nos tiers seguintes:

- **Tier 4:** microcontroladores passam a gerenciar a intensidade e polaridade dos campos magnéticos.
- **Tier 5:** uso de **materiais supercondutores** e **campos quânticos** para manipulação de energia pura.

---

### 💾 **TIER 4 – Computação e Automação**

- Microcontrolador
- EEPROM / Memória
- Sensores (térmico, luz, proximidade)
- Display (LED / LCD)
- Módulo lógico programável

➡️ *Controle digital e automação complexa.*

---

### ⚡ **TIER 5 – Eletrônica Avançada**

- Nanochip / Circuito quântico
- IA Module (ElektroCore)
- Cabos ópticos
- Master Controller
- Máquinas inteligentes

➡️ *Domínio total da energia e automação.*

---

## 🎯 **Objetivos Finais (Endgame)**

### 1. ⚡ **ElektroSuit**

Uma armadura modular de alta tecnologia que utiliza energia elétrica e módulos integrados (propulsão, escudo, radar,
invisibilidade).
> *Sensação de “engenharia pessoal” – poder portátil.*

---

### 2. ☢️ **Reator Nuclear / Fusion Core**

Reator multibloco controlável que gera energia massiva, exigindo sistemas eletrônicos para estabilização.
> *Sensação de “potência e perigo” – controle absoluto da energia.*

---

### 3. 🧠 **IA Central – ElektroCore**

Uma inteligência artificial que gerencia todos os sistemas elétricos e máquinas do jogador, permitindo automação total.
> *Sensação de “mundo inteligente” – máquinas com consciência.*

---

### 4. 🔮 **Reator Quântico**

Fonte de energia suprema capaz de converter energia em matéria pura, distorcendo o espaço-tempo.
> *Sensação de “ciência transcendendo a realidade” – domínio da física.*

---

### 5. 🪐 **Colônia Elétrica**

Projeto cooperativo para construir uma cidade 100% automatizada e eletrificada com sistemas de energia, defesa e IA.
> *Sensação de “civilização tecnológica” – engenharia em escala mundial.*

---

## 🧩 **Exemplo de Fluxo de Progressão (Resumo)**

```
Minérios → Materiais processados → Componentes básicos
→ Circuitos intermediários → Energia e controle
→ Computação e automação → Eletrônica avançada
→ Objetivo final (ElektroSuit, Reator, IA, etc.)
```
