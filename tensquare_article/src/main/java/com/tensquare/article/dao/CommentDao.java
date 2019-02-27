package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 评论Dao
 *
 * @author xFar
 */

public interface CommentDao extends MongoRepository<Comment, String> {
    /**
     * 根据文章ID查询评论列表
     *
     * @param articleid
     * @return
     */
    public Page<Comment> findByArticleidOrderByPublishdateDesc(String articleid, Pageable pageable);

}
