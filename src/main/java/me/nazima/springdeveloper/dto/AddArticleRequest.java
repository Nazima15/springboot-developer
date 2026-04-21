package me.nazima.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.nazima.springdeveloper.dao.Article;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(){
        //return new Article(title, content);
        return Article.builder().title(title).content(content).build();
    }
    //new AddArticleRequest("제목", "내용");
    //new AddArticleRequest();



}
