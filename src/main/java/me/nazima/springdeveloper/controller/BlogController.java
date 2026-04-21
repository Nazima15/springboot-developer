package me.nazima.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.nazima.springdeveloper.dao.Article;
import me.nazima.springdeveloper.dto.AddArticleRequest;
import me.nazima.springdeveloper.dto.ArticleResponse;
import me.nazima.springdeveloper.dto.UpdateArticleRequest;
import me.nazima.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //응답으로 데이터를 반환한다
@RequiredArgsConstructor //(아래 처럼 자동 해줌)
//public BlogController (BlogService service){
//    this.blogService=service;
//}
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest articleRequest) {
        Article article = blogService.save(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<Article> articles=blogService.findAll();
        List<ArticleResponse> result=articles.stream().map(ArticleResponse::new).toList();
//        List<ArticleResponse> result=new ArrayList<>();
//        for(Article a:articles){
//            result.add(new ArticleResponse(a));
//        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article=blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle=blogService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }
}
