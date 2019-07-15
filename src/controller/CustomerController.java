package com.itheima.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.crm.pojo.BaseDict;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.service.BaseDictService;
import com.itheima.crm.service.CustomerService;
import com.itheima.crm.utils.Page;

/**
 * 客户信息请求处理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private BaseDictService dictService;
	
	@Autowired
	private CustomerService customerService;
	
	@Value("${customer_from_type}")
	private String customer_from_type;
	@Value("${customer_industry_type}")
	private String customer_industry_type;
	@Value("${customer_level_type}")
	private String customer_level_type;
	
//	@RequestMapping("list")
//	public String name() {
//		return "customer";
//	}
	
	@RequestMapping("list")
	public String list(Model model,QueryVo vo) {
		
		List<BaseDict> fromType = dictService.getBaseDictsCode(customer_from_type);
		List<BaseDict> industryType = dictService.getBaseDictsCode(customer_industry_type);
		List<BaseDict> levelType = dictService.getBaseDictsCode(customer_level_type);
		
		model.addAttribute("fromType",fromType);
		model.addAttribute("industryType",industryType);
		model.addAttribute("levelType",levelType);
		//根据查询条件分页查询用户列表
		if(vo.getCustIndustry()!=null||vo.getCustLevel()!=null||vo.getCustName()!=null||vo.getCustSource()!=null) {
			Page<Customer> page = customerService.getCustomerByQueryVo(vo);//这个方法有问题
			model.addAttribute("page",page);
			model.addAttribute("vo",vo);
		}
		
		//设置分页数据返回
		
		return "customer";
	}
}
