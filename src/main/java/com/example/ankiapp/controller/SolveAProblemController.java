package com.example.ankiapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
@Controller
public class SolveAProblemController {
    
    /**
     * 画面の初期表示を行います。
     * 
     * @param model モデル
     * @param form 入力情報
     * @return ログイン画面
     */
    @GetMapping(UrlConst.SOLVE_A_PROBLEM)
    public String view(Model model) {
        return ViewNameConst.SOLVE_A_PROBLEM;
    }
}
