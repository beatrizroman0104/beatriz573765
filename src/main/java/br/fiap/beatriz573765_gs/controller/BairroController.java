package br.fiap.beatriz573765_gs.controller;

import br.fiap.beatriz573765_gs.dto.BairroDTO;
import br.fiap.beatriz573765_gs.dto.LeituraDTO;
import br.fiap.beatriz573765_gs.dto.PainelItemDTO;
import br.fiap.beatriz573765_gs.dto.TrechoDTO;
import br.fiap.beatriz573765_gs.service.BairroService;
import br.fiap.beatriz573765_gs.service.LeituraService;
import br.fiap.beatriz573765_gs.service.PainelService;
import br.fiap.beatriz573765_gs.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/bairros")
public class BairroController {
    private final BairroService service;
    private final TrechoService trechoService;
    private final LeituraService leituraService;
    private final PainelService painelService;

    public BairroController(BairroService service, TrechoService trechoService,
                            LeituraService leituraService, PainelService painelService) {
        this.service = service;
        this.trechoService = trechoService;
        this.leituraService = leituraService;
        this.painelService = painelService;
    }

    @GetMapping
    public String listar(Model model) {
        carregarPagina(model, new BairroDTO(null, "", "", false),
                new TrechoDTO(null, "", "", null, null, ""),
                novaLeitura());
        return "index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        return listar(model);
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        carregarPagina(model, service.buscarPorId(id),
                new TrechoDTO(null, "", "", null, null, ""), novaLeitura());
        return "index";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("bairro") BairroDTO dto,
                         BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            carregarPagina(model, dto,
                    new TrechoDTO(null, "", "", null, null, ""), novaLeitura());
            return "index";
        }
        service.salvar(dto);
        return "redirect:/bairros";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, Model model) {
        try {
            service.excluir(id);
            return "redirect:/bairros";
        } catch (RuntimeException exception) {
            carregarPagina(model, new BairroDTO(null, "", "", false),
                    new TrechoDTO(null, "", "", null, null, ""), novaLeitura());
            model.addAttribute("erro", exception.getMessage());
            return "index";
        }
    }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model) {
        carregarPagina(model, new BairroDTO(null, "", "", false),
                new TrechoDTO(null, "", "", null, null, ""), novaLeitura());
        List<PainelItemDTO> itens = painelService.montarPainel(id);
        model.addAttribute("bairroNome", service.buscarEntidade(id).getNome());
        model.addAttribute("itens", itens);
        model.addAttribute("qtdNormal", itens.stream().filter(i -> "NORMAL".equals(i.ultimoStatus())).count());
        model.addAttribute("qtdAtencao", itens.stream().filter(i -> "ATENCAO".equals(i.ultimoStatus())).count());
        model.addAttribute("qtdAlerta", itens.stream().filter(i -> "ALERTA".equals(i.ultimoStatus())).count());
        return "index";
    }

    private void carregarPagina(Model model, BairroDTO bairro, TrechoDTO trecho, LeituraDTO leitura) {
        model.addAttribute("bairros", service.listar());
        model.addAttribute("trechos", trechoService.listar());
        model.addAttribute("leituras", leituraService.listar());
        model.addAttribute("bairro", bairro);
        model.addAttribute("trecho", trecho);
        model.addAttribute("leitura", leitura);
        model.addAttribute("zonas", List.of("Centro", "Norte", "Sul", "Leste", "Oeste"));
        model.addAttribute("tipos", List.of("Regular", "Irregular", "Escadaria", "Sem iluminação"));
        model.addAttribute("itens", List.of());
        model.addAttribute("qtdNormal", 0);
        model.addAttribute("qtdAtencao", 0);
        model.addAttribute("qtdAlerta", 0);
    }

    private LeituraDTO novaLeitura() {
        return new LeituraDTO(null, null, "", LocalDateTime.now().withSecond(0).withNano(0), null, "");
    }
}
