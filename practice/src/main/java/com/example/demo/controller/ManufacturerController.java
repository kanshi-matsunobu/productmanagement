package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Manufacturer;
import com.example.demo.service.ManufacturerService;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    // 一覧
    @GetMapping
    public String list(Model model) {
        List<Manufacturer> list = manufacturerService.findAll();
        model.addAttribute("manufacturers", list);
        return "manufacturer_list";
    }

    // 新規作成画面表示
 // 新規作成画面表示
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        model.addAttribute("submitUrl", "/manufacturer/create"); // ←追加
        return "manufacturer_form";
    }

    // 新規作成処理
    @PostMapping("/create")
    public String createManufacturer(@Valid @ModelAttribute Manufacturer manufacturer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "manufacturer_form"; // エラー時に戻る
        }
        manufacturerService.save(manufacturer);
        return "redirect:/manufacturer";
    }

    // 詳細表示
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer_detail";
    }

    // 編集画面表示
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("submitUrl", "/manufacturer/edit/" + id); // ←追加
        return "manufacturer_form";
    }

    // 編集処理
    @PostMapping("/edit/{id}")
    public String updateManufacturer(@PathVariable Integer id, @Valid @ModelAttribute Manufacturer manufacturer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "manufacturer_form";
        }
        manufacturer.setId(id); // 念のためセット
        manufacturerService.update(manufacturer);
        return "redirect:/manufacturer";
    }
    
    //削除機能
    @PreAuthorize("hasAuthority('管理者')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        manufacturerService.delete(id);
        return "redirect:/manufacturer";
    }
}
