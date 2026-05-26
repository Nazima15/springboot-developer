package me.nazima.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.nazima.springdeveloper.dao.Article;
import me.nazima.springdeveloper.dto.ArticleViewResponse;
import me.nazima.springdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    // 전체 글 목록
    @GetMapping("/articles")
    public String getArticles(Model model) {

        List<Article> articles = blogService.findAll();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    // 글 상세 조회
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {

        Article article = blogService.findById(id);

        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    // 글 등록 / 수정 페이지
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id,
                             Model model) {

        // 수정
        if (id != null) {

            Article article = blogService.findById(id);

            model.addAttribute("article",
                    new ArticleViewResponse(article));

        } else {

            // 등록
            model.addAttribute("article",
                    new ArticleViewResponse());
        }

        return "newArticle";
    }
}