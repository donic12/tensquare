package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     *
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 增加标签
     *
     * @param label
     */
    public void add(Label label) {
        label.setId(String.valueOf(idWorker.nextId()));//设置ID
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 构建查询条件
     *
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Map searchMap) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?>
                    criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                if (searchMap.get("labelname") != null &&
                        !"".equals(searchMap.get("labelname"))) {
                    predicateList.add(cb.like(
                            root.get("labelname").as(String.class), "%" +
                                    (String) searchMap.get("labelname") + "%"));
                }
                if (searchMap.get("state") != null &&
                        !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.equal(
                            root.get("state").as(String.class), (String) searchMap.get("state")));
                }
                if (searchMap.get("recommend") != null &&
                        !"".equals(searchMap.get("recommend"))) {
                    predicateList.add(cb.equal(
                            root.get("recommend").as(String.class),
                            (String) searchMap.get("recommend")));
                }
                return cb.and(predicateList.toArray(new
                        Predicate[predicateList.size()]));
            }
        };
    }

    /**
     * 构建查询条件
     *
     * @param label
     * @return
     */
    private Example<Label> createExample(Label label) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("labelname", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching().ignoreCase())
                .withMatcher("state", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
                .withMatcher("recommend", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
                ;
        return Example.of(label, matcher);
    }

    /**
     * 条件查询
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(createExample(label));
    }

    /**
     * 分页条件查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findSearch(Label label, int page, int size){
        Example<Label> example = createExample(label);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return labelDao.findAll( example ,pageRequest);
    }
}

