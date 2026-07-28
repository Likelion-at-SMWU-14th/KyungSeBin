package com.likelion;

import com.likelion.bean.Lion;
import com.likelion.bean.Person;
import com.likelion.config.ProjectConfig;
import com.likelion.model.Comment;
import com.likelion.service.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        var context=new AnnotationConfigApplicationContext(ProjectConfig.class);

        Comment comment=new Comment();
        comment.setAuthor("Sebin");
        comment.setText("I'm hungry");

        CommentService commentService=context.getBean(CommentService.class);

        commentService.publishComment(comment);

        context.close();
    }
}