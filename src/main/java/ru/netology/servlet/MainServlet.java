package ru.netology.servlet;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
  private final PostController controller = context.getBean(PostController.class);
  private String requestPath;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      requestPath = req.getRequestURI();
      if(requestPath.equals("/api/posts")) {
        controller.all(resp);
      } else if(requestPath.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(requestPath.substring(requestPath.lastIndexOf("/")));
        controller.getById(id, resp);
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception ex) {
      ex.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      requestPath = req.getRequestURI();
      if(requestPath.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception ex) {
      ex.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    try {
      requestPath = req.getRequestURI();
      final var id = Long.parseLong(requestPath.substring(requestPath.lastIndexOf("/")));
      controller.removeById(id, resp);
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception ex) {
      ex.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

