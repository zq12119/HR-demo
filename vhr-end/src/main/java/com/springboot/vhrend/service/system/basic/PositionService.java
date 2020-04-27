package com.springboot.vhrend.service.system.basic;
import com.springboot.vhrend.mapper.PositionMapper;
import com.springboot.vhrend.model.Position;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PositionService {

    @Resource
    PositionMapper positionMapper;
    public List<Position> getAllPosition() {
        return positionMapper.selectAllPosition();
    }

    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insertSelective(position);
    }

    public Integer updatePosition(Position position) {

        return positionMapper.updateByPrimaryKeySelective(position);
    }
    public Integer deletePosition(Integer id) {

        return positionMapper.deleteByPrimaryKey(id);
    }
    public Integer deletePosition(Integer[] ids) {

        return positionMapper.deleteByIds(ids);
    }
}