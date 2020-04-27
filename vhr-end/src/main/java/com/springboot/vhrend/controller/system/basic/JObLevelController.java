package com.springboot.vhrend.controller.system.basic;

import com.springboot.vhrend.model.JObLevel;
import com.springboot.vhrend.model.RespBean;
import com.springboot.vhrend.service.system.basic.JObLevelService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "JObLevelController", tags = {"基础数据管理"})
@RequestMapping("/system/basic/joblevel")
public class JObLevelController {

    @Autowired
    JObLevelService jObLevelService;

    @GetMapping("/")
    @ApiOperation(value = "获取所有职称", notes = "所有职称信息列表", produces = "application/json")
    public RespBean getAllJObLevel() {
        List<JObLevel> jObLevels = jObLevelService.getAllJObLevel();
        return RespBean.ok("获取成功", jObLevels);
    }

    @PostMapping("/")
    @ApiOperation(value = "新增职称", notes = "根据传入的职称添加一个新职称")
    @ApiImplicitParam(name = "joblevel", value = "joblevel对象", required = true)
    public RespBean addJObLevel(@RequestBody JObLevel jObLevel) {
        if(jObLevelService.addJObLevel(jObLevel) == 1 ) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    @ApiOperation(value = "修改职称", notes = "传入职称信息进行更新修改" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功！"),
            @ApiResponse(code = 500, message = "更新失败！")
    })
    public RespBean updateJObLevel(@RequestBody JObLevel jObLevel) {
        if(jObLevelService.updateJObLevel(jObLevel) == 1 ) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除职称", notes = "根据 id 删除职称")
    @ApiImplicitParam(name = "id", value = "职称 id", required = true)
    public RespBean deleteJObLevel(@PathVariable Integer id) {
        if(jObLevelService.deleteJObLevel(id) == 1 ) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    @ApiOperation(value = "批量删除职称", notes = "根据 id 数组删除职称" )
    @ApiImplicitParam(name = "ids", value = "id数组", required = true)
    public RespBean deleteJObLevel(Integer[] ids) {
        if(jObLevelService.deleteJObLevel(ids) == ids.length) {
            return RespBean.ok("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }
}