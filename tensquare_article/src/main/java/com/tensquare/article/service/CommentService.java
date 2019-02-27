package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment) {
        comment.set_id(String.valueOf(idWorker.nextId()));
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    public Page<Comment> findByArticleid(String articleid, int page, int pagesize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pagesize);
        return commentDao.findByArticleidOrderByPublishdateDesc(articleid, pageRequest);
    }

    public void deleteById(String id){
        commentDao.deleteById(id);
    }
}
