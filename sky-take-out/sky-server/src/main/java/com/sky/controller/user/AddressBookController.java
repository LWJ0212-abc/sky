package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: AddressBookController
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 13:38
 * @version: 1.0
 */
@RestController
@Api(tags = "用户地址簿")
@Slf4j
@RequestMapping("/user/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result<String> addAddress(@RequestBody AddressBook  addressBook){
            log.info("新增地址：{}",addressBook);
            addressBookService.save(addressBook);
            return Result.success();
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result<String> updateAddress(@RequestBody AddressBook  addressBook){
        log.info("修改地址信息：{}",addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result<String> deleteAddress(@RequestParam("id") Long id){
        log.info("要删除的地址id：{}",id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询用户所有地址")
    public Result<List<AddressBook>> list(){
        log.info("查询用户所有地址");
        AddressBook addressBook = AddressBook.builder().userId(BaseContext.getCurrentId()).build();
        List<AddressBook> list=addressBookService.list(addressBook);
        return Result.success(list);
    }

    @ApiOperation("查询默认地址")
    @GetMapping("/default")
    public Result<AddressBook> getDefault(){
        AddressBook addressBook = AddressBook.builder().userId(BaseContext.getCurrentId()).isDefault(1).build();
        List<AddressBook> list=addressBookService.list(addressBook);
        if(list!=null&&list.size()==1){
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }

    @ApiOperation("根据id查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> getAddress(@PathVariable("id") Long id){
        log.info("根据id查询地址：{}",id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result<String> updateDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefalut(addressBook);
        return Result.success();
    }
}
