package com.diary.demo.Controllers;

import com.diary.demo.Models.Post;
import com.diary.demo.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;


    @GetMapping("/diary/main")
    public String handlePost(Model model) {
        Iterable<Post> posts = postRepository.findAll();
         model.addAttribute("posts", posts);
        return "dairy-main";
    }

    @GetMapping("/diary/add")
    public String handleUserHome() {
        return "post_add";
    }

    @PostMapping("/diary/add")
    public String diaryPostAdd(@RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,
                               Model model){
        Post post = new Post(title,anons,fullText);
        postRepository.save(post);
        return "redirect:/diary/main";
    }


}
