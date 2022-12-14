package org.example.repositiry;

import org.example.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private Map<Long, Post> posts = new ConcurrentHashMap<>();
    private AtomicLong idCount = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        if (posts.containsKey(id)) {
            return Optional.of(posts.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idCount.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
        } else {
            if (getById(post.getId()).isPresent()) {
                posts.put(post.getId(), post);
            } else {
                return null;
            }
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
