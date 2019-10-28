package com.yusys.quartz.mapper;

import com.yusys.quartz.entity.TaskEntity;
import com.yusys.quartz.vo.TaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by huyang on 2019/10/28.
 */
@Mapper
public interface TaskMapper {

    TaskEntity get(Long id);

    List<TaskEntity> list();

    List<TaskVO> listTaskVoByDesc(@Param("desc") String desc);

    int save(TaskEntity task);

    int update(TaskEntity task);

    int remove(Long id);

    int removeBatch(Long[] ids);
}
