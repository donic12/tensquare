package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页结果类
 * @param <T>
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;
}
