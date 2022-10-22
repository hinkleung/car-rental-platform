package com.prudential.assignment.controller.bg;

import com.prudential.assignment.common.model.request.CarAddRequest;
import com.prudential.assignment.common.model.request.CarUpdateRequest;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.CarVO;
import com.prudential.assignment.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "后台——车型管理")
@RestController
@RequestMapping("/bg/car")
public class BgCarController {

    @Autowired
    private CarService carService;

    @ApiOperation("列表")
    @GetMapping("/list")
    public Response<List<CarVO>> list() {
        return carService.list();
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public Response<Void> add(@RequestBody @Valid CarAddRequest request) {
        carService.add(request);
        return Response.success();
    }

    @ApiOperation("更新")
    @PutMapping("/update")
    public Response<Void> update(@RequestBody @Valid CarUpdateRequest request) {
        carService.update(request);
        return Response.success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public Response<Void> delete(@PathVariable("id") Long id) {
        carService.delete(id);
        return Response.success();
    }

}
