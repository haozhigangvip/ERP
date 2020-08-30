package com.targetmol.system.controller;

import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.Department;
import com.targetmol.system.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/depa")
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //查找所有部门
    @GetMapping()
    public ResponseEntity<ResultMsg> findByAll(
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc
    ){
        return ResponseEntity.ok(ResultMsg.success(departmentService.findByAll(softBy,desc)));
    }



    //添加部门
    @PostMapping
    public  ResponseEntity<ResultMsg>addDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(ResultMsg.success(departmentService.addDepartment(department)));
    }


    //按ID查询部门
    @GetMapping("{depaid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("depaid") Integer depaid) {
        return ResponseEntity.ok(ResultMsg.success(departmentService.findById(depaid)));
    }

    //修改部门
    @PutMapping("{depaid}")
    public ResponseEntity<ResultMsg>updateDepartment(@PathVariable("depaid") Integer depaid,
                                                     @RequestBody Department department){
        department.setDepaid(depaid);
        return ResponseEntity.ok(ResultMsg.success(departmentService.updateDepartment(department)));
    }

    //按ID删除部门
    @DeleteMapping("{depaid}")
    public ResponseEntity<ResultMsg>deleteDepartment(@PathVariable("depaid") Integer depaid){
        departmentService.delDepartment(depaid);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
