package me.nazima.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nazima.springdeveloper.dao.Article;
import me.nazima.springdeveloper.dto.AddArticleRequest;
import me.nazima.springdeveloper.dto.UpdateArticleRequest;
import me.nazima.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected BlogRepository blogRepository;

    @BeforeEach
    public void deleteAll(){
        blogRepository.deleteAll();
    }



    @DisplayName("addArticle: 블로그 글 추가에 성공한다")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "테스트";
        final String content = "블로그 글 첫번째입니다";

        final AddArticleRequest article = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(article);

        // when
        ResultActions result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        result.andExpect(status().isCreated());
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다")
    @Test
    public void findAllArticles() throws Exception {

        // given
        final String url = "/api/articles";
        blogRepository.save(
                Article.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        // when
        ResultActions resultActions = mockMvc.perform(
                get(url).accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].content").value("content"));
    }
    @DisplayName("findArticle: 블로그 글 조회에 성공한다")
    @Test
    public void findArticle() throws Exception{
        //given(데이터 준비: 블로그글 하나 생성)
        final String url="/api/articles/{id}";
        final String title="블로그 제목";
        final String content="블로그 내용";

        Article savedArticle=blogRepository.save(Article.builder().title(title).content(content).build());
        //when(실행: 위에서 생성된 블로그를 조회)
        final ResultActions resultActions=mockMvc.perform(get(url, savedArticle.getId()));

        //then(검증: status 가 200이고 조회한 블로그글 제목과 내용이 위에서 삽입한 그것과 동일한지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content)); //반환된 JSON 객채의 content 값이 변수 content 와 동일하고
                                //반환된 JSON 객채의 title 값이 변수 title 과 동일한지 확인
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다 ")
    @Test
    public void deleteArticle() throws Exception{
        //given
        final String url="/api/articles/{id}";
        final String title="4월 16일";
        final String contnent="백엔드프로그래밍(2) 수업";
        Article savedArticle=blogRepository.save(Article.builder().title(title).content(contnent).build());

        //when
        mockMvc.perform(delete(url, savedArticle.getId())).andExpect(status().isOk());

        //then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다 ")
    @Test
    public void updateArticle() throws Exception{
        //given: 레코드 생성, 변경내용 작성
        final String url="/api/articles/{id}";
        final String title="JUnit 에서 제목 변경";
        final String content="JUnit에서 내용 변경";
        Article savedArticle=blogRepository.save(Article.builder().title(title).content(content).build());

        final String newTitle="JUnit 에서 제목 변경";
        final String newContent="JUnit 에서 내용 변경";
        UpdateArticleRequest request=new UpdateArticleRequest(newTitle, newContent);
        //when: /api/articles/ 생성된 레코드 if-> put 방식 요청
        ResultActions result=mockMvc.perform(put(url, savedArticle.getId()).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request)));


        //then //then: staus code 가 200, repo 에서 면경된 내용 검증
        result.andExpect(status().isOk());
        Article article=blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

}