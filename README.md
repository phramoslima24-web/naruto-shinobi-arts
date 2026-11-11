# Narutoto Shinobi Arts (0.1.0)

Addon independente para Minecraft 1.20.1 / Forge 47.4.10 / Narutoto 0.4.1.

## Conteúdo inicial
- Rasengan: avanço curto, dano em área próxima e impacto visual.
- Chidori: investida com faíscas elétricas e dano.
- Katon: Gōkakyū: explosão visual de fogo e dano em área.
- Suiton: Dragão de Água: golpe em área com partículas de água.
- Doton: Dragão de Terra: golpe em área com partículas de pedra.
- Modo Taijutsu: velocidade, força e salto temporários.

Os itens são obtidos pelo inventário criativo (aba de ingredientes/itens). Use com botão direito. Cada técnica tem recarga própria.

## Compilar
Use Java 17 e execute `gradle build --no-daemon`. O JAR do Narutoto 0.4.1 deve estar em `libs/narutoto-0.4.1.jar`.

## Nota de compatibilidade
Este primeiro protótipo usa efeitos e partículas vanilla para executar as técnicas sem depender das APIs internas do Ninshu Origins. As imagens foram selecionadas do pacote de fontes fornecido e mantidas como recursos do addon. A compatibilidade final deve ser confirmada com build e teste no jogo.
