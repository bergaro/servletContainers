package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Simple database
public class PostRepository {

  private static final Map<Long, Post> simpleDatabase = new ConcurrentHashMap<>();
  private static final AtomicLong postCounter = new AtomicLong(1L);

  public List<Post> all() {
    return new ArrayList<>(simpleDatabase.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.of(simpleDatabase.get(id));
  }

  public Post save(Post post) {
    final var idPost = post.getId();
    if(idPost == 0 || !mapContainsId(idPost)) {
      post.setId(postCounter.getAndIncrement());
    }
    simpleDatabase.put(post.getId(), post);
    return post;
  }

  private boolean mapContainsId(long id) {
    return simpleDatabase.containsKey(id);
  }

  public void removeById(long id) {
    simpleDatabase.remove(id);
  }
}
