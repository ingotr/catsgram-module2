package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private int counter = 1;

    public List<Post> findAll() {
        return posts;
    }

    public Post create(Post post) {
        post.setId(getCounter());
        posts.add(post);
        return post;
    }

    public Optional<Post> findById(@PathVariable int postId) {
        return posts.stream()
                .filter(x -> x.getId() == postId)
                .findFirst();
    }

    public int getCounter() {
        return counter++;
    }
}
