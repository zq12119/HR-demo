package com.springboot.vhrend.controller.system.basic;

import com.github.pagehelper.PageInfo;
import com.springboot.vhrend.model.Position;
import com.springboot.vhrend.model.RespBean;
import com.springboot.vhrend.service.system.basic.PositionService;
import com.springboot.vhrend.utils.PUtils;
import com.springboot.vhrend.utils.PoiUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/system/basic/pos")
@Api(value = "PositionController", tags = {"职位管理"})
public class PositionController {
    @Autowired
    PositionService positionService;

//    @GetMapping("/")
//    @ApiOperation(value = "获取职位",notes = "获取数据库里的所有职位")
//    public RespBean getAllPosition() {
//        List<Position> positions = positionService.getAllPosition();
//        return RespBean.ok("获取成功", positions);
//    }

    @GetMapping("/")
    @ApiOperation(value = "分页获取职位",notes = "职位信息列表")
    public RespBean getPositionByPage(@RequestParam(defaultValue = "1")Integer page,
                                      @RequestParam(defaultValue = "5")Integer size){
        PageInfo<Position> positions = positionService.getPositionByPage(page,size);
        return RespBean.ok("",positions);

    }

    @PostMapping("/")
    @ApiOperation(value = "添加职位",notes = "添加用户输入的新的职位")
    public RespBean addPosition(@RequestBody Position position) {
        if(positionService.addPosition(position) == 1 ) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    @ApiOperation(value = "修改职位",notes = "根据传入的职位信息修改职位")
    public RespBean updatePosition(@RequestBody Position position) {
        if(positionService.updatePosition(position) == 1 ) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除职位",notes = "根据id 删除职位")
    public RespBean deletePosition(@PathVariable Integer id) {
        if(positionService.deletePosition(id) == 1 ) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    @ApiOperation(value = "批量删除职位",notes = "根据ids 批量删除职位")
    public RespBean deletePosition(Integer[] ids) {
        if(positionService.deletePosition(ids) == ids.length) {
            return RespBean.ok("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }
    @GetMapping("/export")
    @ApiOperation(value = "导出数据", notes = "将所有职位导出到excel")
    public ResponseEntity<byte[]> exportData() {
        List<Position> positions = positionService.getAllPosition();
        return PUtils.exportData(positions);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入数据", notes = "导入excel数据")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Position> positions = PUtils.importData(file);
        if(positionService.addPositions(positions) == positions.size()) {
            return RespBean.ok("导入成功");
        }
        return RespBean.ok("导入失败");
    }
}