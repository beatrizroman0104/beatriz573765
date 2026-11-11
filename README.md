# Cidade em Dia de Chuva - Global Solution 2026.2

POO com Java e Web - Prof. Dr. Antonio Marcos Selmini

## Versão
Versão web (HTML com Thymeleaf).

## Integrantes
- RM 573765
- RM 566448
- RM 562463

**RM da equipe:** 573765 + 566448 + 562463 = 1702676

## Parâmetros
| Parâmetro | Cálculo | Valor |
|---|---|---|
| P1 - prefixo das tabelas | "GS" + 1702676 % 1000 | GS676 |
| P2 - limiar de alerta L | 10 + 2 * (1702676 % 4) = 10 + 2 * 0 | L = 10 (atenção a partir de 5 cm) |
| P3 - atributo extra de Bairro | (1702676 / 10) % 3 = 170267 % 3 = 2 | possuiPiscinao (Boolean, caixa de seleção) |
| P4 - ordenação dos trechos | (1702676 / 100) % 3 = 17026 % 3 = 1 | altitude e depois logradouro |
| P5 - exclusão de trecho com leituras | (1702676 / 1000) % 2 = 1702 % 2 = 0 | bloquear, com mensagem de erro |

## DTOs
Um DTO por entidade (BairroDTO, TrechoDTO, LeituraDTO), usando `record`, mais o PainelItemDTO.

## Como executar
Defina as variáveis de ambiente `DB_USER` e `DB_PASSWORD` (IntelliJ: Run > Edit Configurations > Environment variables) e execute a classe principal. As tabelas GS676_BAIRRO, GS676_TRECHO e GS676_LEITURA são criadas na primeira execução.
