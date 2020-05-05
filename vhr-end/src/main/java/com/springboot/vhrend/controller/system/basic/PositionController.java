package com.springboot.vhrend.controller.system.basic;

import com.github.pagehelper.PageInfo;
import com.springboot.vhrend.model.Position;
import com.springboot.vhrend.model.RespBean;
import com.springboot.vhrend.service.system.basic.PositionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Api(value = "职位管理",tags = {"职位管理窗口"})
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;

//    @ApiOperation(value = "获取全部职位信息")
//    @GetMapping("/")
//    public RespBean getAllPosition() {
//        List<Position> positions = positionService.getAllPosition();
//        return RespBean.ok("获取成功", positions);
//    }
    @GetMapping("/")
    @ApiOperation(value = "分页获取职位",notes = "职位信息列表",produces = "application/json")
    public RespBean getPositionByPage(@RequestParam(defaultValue = "1")Integer page,
                                      @RequestParam(defaultValue = "5")Integer size){
        PageInfo<Position> positions=positionService.getPostionByPage(page,size);
        return RespBean.ok("",positions);
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        if(positionService.addPosition(position) == 1 ) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改职位信息",notes = "在所有的职位信息里面，修改信息，并保存")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {
        if(positionService.updatePosition(position) == 1 ) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除职位信息",notes = "根据id删除信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id) {
        if(positionService.deletePosition(id) == 1 ) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除",notes = "将想要删除的id号存在数据里,批量删除")
    @DeleteMapping("/")
    public RespBean deletePosition(Integer[] ids) {
        if(positionService.deletePosition(ids) == ids.length) {
            return RespBean.ok("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }
}