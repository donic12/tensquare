package com.tensquare.user.dao;

import io.lettuce.core.cluster.api.reactive.RedisClusterReactiveCommands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.Admin;
import org.springframework.data.redis.repository.cdi.RedisRepositoryBean;

/**
 * admin数据访问接口
 *
 * @author Administrator
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    public Admin findByLoginname(String loginname);
}
