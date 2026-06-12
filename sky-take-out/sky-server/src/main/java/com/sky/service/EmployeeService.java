package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
    /*
     * @param employeeDTO:
      * @return void
     * @author lwj
     * @description 添加员工
     * @date 2026/6/12 19:09
     */
    void addEmployee(EmployeeDTO employeeDTO);
    /*
     * @param employeeDTO:
      * @return PageResult
     * @author lwj
     * @description 分页查询
     * @date 2026/6/12 19:10
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, Long id);
}
