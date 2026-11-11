package br.fiap.beatriz573765_gs.controller;

import br.fiap.beatriz573765_gs.dto.LeituraDTO;
import br.fiap.beatriz573765_gs.service.LeituraService;
import br.fiap.beatriz573765_gs.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/leituras")
public class LeituraController {
    private final LeituraService service;
    private final TrechoService trechoService;

    public LeituraController(LeituraService service, TrechoService trechoService) {
        this.service = service;
        this.trechoService = trechoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("leituras", service.listar());
        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("leitura",
                new LeituraDTO(null, null, "", LocalDateTime.now().withSecond(0).withNano(0), null, ""));
        model.addAttribute("trechos", trechoService.listar());
        return "leituras/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("leitura", service.buscarPorId(id));
        model.addAttribute("trechos", trechoService.listar());
        return "leituras/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("leitura") LeituraDTO dto,
                         BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            model.addAttribute("trechos", trechoService.listar());
            return "leituras/form";
        }
        try {
            service.salvar(dto);
        } catch (RuntimeException exception) {
            model.addAttribute("erro", exception.getMessage());
            model.addAttribute("trechos", trechoService.listar());
            return "leituras/form";
        }
        return "redirect:/leituras";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/leituras";
    }
}