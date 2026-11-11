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
@RequestMapping("/trechos")
public class TrechoController {
    private static final List<String> TIPOS = List.of("Regular", "Irregular", "Escadaria", "Sem iluminação");

    private final TrechoService service;
    private final BairroService bairroService;

    public TrechoController(TrechoService service, BairroService bairroService) {
        this.service = service;
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("trechos", service.listar());
        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("trecho", new TrechoDTO(null, "", "", null, null, ""));
        carregarFormulario(model);
        return "trechos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("trecho", service.buscarPorId(id));
        carregarFormulario(model);
        return "trechos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("trecho") TrechoDTO dto,
                         BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            carregarFormulario(model);
            return "trechos/form";
        }
        service.salvar(dto);
        return "redirect:/trechos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, Model model) {
        try {
            service.excluir(id);
            return "redirect:/trechos";
        } catch (RuntimeException exception) {
            model.addAttribute("trechos", service.listar());
            model.addAttribute("erro", exception.getMessage());
            return "trechos/lista";
        }
    }

    private void carregarFormulario(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("tipos", TIPOS);
    }
}