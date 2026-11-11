package br.fiap.beatriz573765_gs.controller;

import br.fiap.beatriz573765_gs.dto.BairroDTO;
import br.fiap.beatriz573765_gs.dto.LeituraDTO;
import br.fiap.beatriz573765_gs.dto.TrechoDTO;
import br.fiap.beatriz573765_gs.service.BairroService;
import br.fiap.beatriz573765_gs.service.LeituraService;
import br.fiap.beatriz573765_gs.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/leituras")
public class LeituraController {
    private final LeituraService service;
    private final TrechoService trechoService;
    private final BairroService bairroService;

    public LeituraController(LeituraService service, TrechoService trechoService,
                             BairroService bairroService) {
        this.service = service;
        this.trechoService = trechoService;
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {
        carregarPagina(model, new BairroDTO(null, "", "", false),
                new TrechoDTO(null, "", "", null, null, ""), novaLeitura());
        return "index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        return listar(model);
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        carregarPagina(model, new BairroDTO(null, "", "", false),
                new TrechoDTO(null, "", "", null, null, ""), service.buscarPorId(id));
        return "index";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("leitura") LeituraDTO dto,
                         BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            carregarPagina(model, new BairroDTO(null, "", "", false),
                    new TrechoDTO(null, "", "", null, null, ""), dto);
            return "index";
        }
        try {
            service.salvar(dto);
        } catch (RuntimeException exception) {
            resultado.reject("erro", exception.getMessage());
            carregarPagina(model, new BairroDTO(null, "", "", false),
                    new TrechoDTO(null, "", "", null, null, ""), dto);
            return "index";
        }
        return "redirect:/leituras";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/leituras";
    }

    private void carregarPagina(Model model, BairroDTO bairro, TrechoDTO trecho, LeituraDTO leitura) {
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("trechos", trechoService.listar());
        model.addAttribute("leituras", service.listar());
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
