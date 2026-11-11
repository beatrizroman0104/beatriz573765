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
    private static final List<String> ZONAS = List.of("Centro", "Norte", "Sul", "Leste", "Oeste");

    private final BairroService service;
    private final PainelService painelService;

    public BairroController(BairroService service, PainelService painelService) {
        this.service = service;
        this.painelService = painelService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("bairros", service.listar());
        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("bairro", new BairroDTO(null, "", "", false));
        model.addAttribute("zonas", ZONAS);
        return "bairros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("bairro", service.buscarPorId(id));
        model.addAttribute("zonas", ZONAS);
        return "bairros/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("bairro") BairroDTO dto,
                         BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            model.addAttribute("zonas", ZONAS);
            return "bairros/form";
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
            model.addAttribute("bairros", service.listar());
            model.addAttribute("erro", exception.getMessage());
            return "bairros/lista";
        }
    }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model) {
        List<PainelItemDTO> itens = painelService.montarPainel(id);
        model.addAttribute("bairroNome", service.buscarPorId(id).nome());
        model.addAttribute("itens", itens);
        model.addAttribute("qtdNormal", painelService.contarPorStatus(itens, "NORMAL"));
        model.addAttribute("qtdAtencao", painelService.contarPorStatus(itens, "ATENCAO"));
        model.addAttribute("qtdAlerta", painelService.contarPorStatus(itens, "ALERTA"));
        return "bairros/painel";
    }
}