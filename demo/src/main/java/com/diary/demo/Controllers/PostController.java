package com.diary.demo.Controllers;

import com.diary.demo.Models.Tracker;
import com.diary.demo.Models.Post;
import com.diary.demo.Repository.PostRepository;
import com.diary.demo.Repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TrackerRepository trackerRepository;

    @GetMapping("/diary")
    public String handlePost(Model model) {
        Iterable<Post> posts = postRepository.findAll();
         model.addAttribute("posts", posts);
        Iterable<Tracker> trackers = trackerRepository.findAll();
        model.addAttribute("trackers", trackers);
        return "diary";
    }

    @GetMapping("/diary/add")
    public String handleUserHome() {
        return "diary-add";
    }

    @PostMapping("/diary/add")
    public String diaryPostAdd(@RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,
                               Model model){
        Post post = new Post(title,anons,fullText);
        postRepository.save(post);
        return "redirect:/diary";
    }

        @GetMapping("/diary/{id}/edit")
    public String postEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return  "redirect:/diary";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "diary-edit";
    }

    @PostMapping("/diary/{id}/edit")
    public String diaryPostUpdate(@PathVariable(value = "id") long id,
                                  @RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,
                               Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postRepository.save(post);
        return "redirect:/diary";
    }
    @PostMapping("/diary/{id}/remove")
    public String diaryPostDelete(@PathVariable(value = "id") long id,
                                  Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/diary";
    }
    @GetMapping("/article")
    public String handleArticle() {
        return "emotion-diary";
    }

    @GetMapping("/diary/tracker")
    public String handleTracker() {
        return "diary-tracker";
    }

    @PostMapping("/diary/tracker")
    public String diaryEmotionTracker(@RequestParam("emotion") String emotion,
                                      @RequestParam("date") String date,
                                      Model model) {
        LocalDate localDate = LocalDate.parse(date);
        Tracker tracker = new Tracker(localDate, emotion);
        trackerRepository.save(tracker);
        return "redirect:/diary";
    }

    @PostMapping("/diary/tracker/{id}/remove")
    public String diaryTrackerDelete(@PathVariable(value = "id") long id,
                                  Model model){
        Tracker tracker = trackerRepository.findById(id).orElseThrow();
        trackerRepository.delete(tracker);
        return "redirect:/diary";
    }
}
